package top.eviltuzki.plugin.es;

import top.eviltuzki.plugin.es.annotation.DateFormat;
import top.eviltuzki.plugin.es.annotation.Keyword;
import top.eviltuzki.plugin.es.annotation.Nested;
import top.eviltuzki.plugin.es.annotation.Text;
import top.eviltuzki.plugin.es.annotation.process.AnnotationProcess;
import top.eviltuzki.plugin.es.annotation.process.DateFormatAnnotationProcess;
import top.eviltuzki.plugin.es.annotation.process.KeywordAnnotationProcess;
import top.eviltuzki.plugin.es.annotation.process.TextAnnotationProcess;
import top.eviltuzki.plugin.es.enums.ReturnEnum;
import top.eviltuzki.plugin.es.exception.NotSupportException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

class AnnotationUtil {
    //Es Type映射到对应注解集合
    private static Map<String,List<Class<? extends Annotation>>> typeMap = new HashMap<>();
    //对应的注解处理对应方法
    private static Map<Class<? extends Annotation>,AnnotationProcess> annotationMap = new HashMap<>();
    static {
        //typeMap 初始化
        typeMap.put("date", Collections.singletonList(DateFormat.class));
        typeMap.put("string", Arrays.asList( Keyword.class,Text.class));
        //annotationMap 初始化
        annotationMap.put(Keyword.class,new KeywordAnnotationProcess());
        annotationMap.put(Text.class,new TextAnnotationProcess());
        annotationMap.put(DateFormat.class,new DateFormatAnnotationProcess());
    }



    static ReturnEnum processAnnotation(StringBuilder mappingBuilder, Field field) {
        //获取EsType类型
        String type = getType(field);
        if ("object".equals(type)){
            Nested nested = field.getDeclaredAnnotation(Nested.class);
            if (nested == null ){
                return ReturnEnum.NonNested;
            }else {
                return ReturnEnum.Nested;
            }
        }
        boolean hasAnnotation = false;
        List<Class<? extends Annotation>> annotations = typeMap.getOrDefault(type,new ArrayList<>());
        for (Class<? extends Annotation> annotationClz : annotations) {
            Annotation annotation = field.getDeclaredAnnotation(annotationClz);
            if (annotation != null){
                Class<? extends Annotation> clazz = annotation.annotationType();
                AnnotationProcess process = annotationMap.get(clazz);
                String value = process.process(annotation);
                mappingBuilder.append(value);
                hasAnnotation = true;
            }
        }
        if (!hasAnnotation){
            if ("string".equals(type)){
                mappingBuilder.append(annotationMap.get(Keyword.class).process(null));
            }else if("date".equals(type)){
                mappingBuilder.append(annotationMap.get(DateFormat.class).process(null));
            }
            else {
                mappingBuilder.append("\"type\": \"").append(type).append("\"");
            }
        }

        return ReturnEnum.Standard;
    }

    private static String getType(Field field){
        //如果genericType包含<>,则为泛型，否则为普通类型
        Type genericType = field.getGenericType();
        if (genericType.toString().contains("<") && genericType.toString().contains(">") ){
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            if (parameterizedType.getActualTypeArguments().length>1){
                throw new NotSupportException("不支持多个泛型参数的字段，请加入@MappingIgnore注解忽略该字段，Field：" +field.toString());
            }
            Type realType = parameterizedType.getActualTypeArguments()[0];
            return Java2EsTypeMapping.getName(realType.getTypeName());
        }else{
            //将数组的L去除，ES中数组和普通Object公用Mapping
            String typeName = field.getType().getName().replace("[L","").replace(";","");
            return  Java2EsTypeMapping.getName(typeName);
        }
    }
}
