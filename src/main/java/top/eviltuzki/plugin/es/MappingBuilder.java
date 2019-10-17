package top.eviltuzki.plugin.es;

import top.eviltuzki.plugin.es.annotation.MappingIgnore;
import top.eviltuzki.plugin.es.enums.ReturnEnum;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MappingBuilder {
    public static <T> String builder(Class<T> clz) {
        StringBuilder mappingBuilder = new StringBuilder("{");
        subObject(clz, mappingBuilder, false);
        return mappingBuilder.append("}").toString().replace("\n", "").replace("\r", "");
    }

    private static <T> void subObject(Class<T> clz, StringBuilder mappingBuilder, boolean isNested) {
        //判断是否为Nested类型
        if (isNested)
            mappingBuilder.append("\"type\": \"nested\",");
        mappingBuilder.append("\"properties\": {");
        //提取所有Field（当前class以及父class）
        List<Field> fieldList = new ArrayList<>();
        Class current = clz;
        while (current != null) {
            fieldList.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        //过滤掉MappingIgnore
        fieldList.removeIf(x -> x.isAnnotationPresent(MappingIgnore.class));
        Field[] fields = fieldList.toArray(new Field[0]);
        //开始处理各个字段
        for (int i = 0; i < fields.length; i++) {
            //拼接FieldName
            mappingBuilder.append("\"").append(fields[i].getName()).append("\":{");
            //处理内部属性，如果是Object则进行递归处理(也就是返回Nested或者NonNested)
            ReturnEnum returnEnum = AnnotationUtil.processAnnotation(mappingBuilder, fields[i]);
            switch (returnEnum) {
                case Nested: {
                    subObject(getFieldType(fields[i]), mappingBuilder, true);
                    break;
                }
                case NonNested: {
                    subObject(getFieldType(fields[i]), mappingBuilder, false);
                    break;
                }
                default:
                    break;

            }
            mappingBuilder.append("}");
            if (fields.length - 1 != i)
                mappingBuilder.append(",");
        }
        mappingBuilder.append("}");

    }

    private static Class<?> getFieldType(Field field) {
        Type genericType = field.getGenericType();
        Class<?> type = field.getType();
        try {
            if(genericType.toString().contains("<") && genericType.toString().contains(">")){
                return  Class.forName(((ParameterizedType) genericType).getActualTypeArguments()[0].getTypeName());

            }else {
                String typeName = field.getType().getName().replace("[L","").replace(";","");
                return Class.forName(typeName);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
