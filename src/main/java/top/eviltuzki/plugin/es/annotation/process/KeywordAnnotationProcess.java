package top.eviltuzki.plugin.es.annotation.process;

import top.eviltuzki.plugin.es.annotation.Keyword;

import java.lang.annotation.Annotation;

public class KeywordAnnotationProcess implements AnnotationProcess {
    @Keyword
    private String defaultValue;

    @Override
    public String process(Annotation annotation) {
        Keyword keyword = null;
        if (null == annotation){
            try {
                keyword = this.getClass().getDeclaredField("defaultValue").getDeclaredAnnotation(Keyword.class);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }else if (annotation instanceof Keyword){
            keyword = (Keyword) annotation;
        }
        if (keyword!=null) {
            return  "\"type\":\"keyword\""+
                    ",\"boost\":" + keyword.boost() +
                    ",\"doc_values\":" + keyword.docValues() +
                    ",\"eager_global_ordinals\":" + keyword.eagerGlobalOrdinals() +
                    ",\"ignore_above\":" + keyword.ignoreAbove() +
                    ",\"include_in_all\":" + keyword.includeInAll() +
                    ",\"index\":" + keyword.index() +
                    ",\"index_options\":\"" + keyword.indexOptions().toString().toLowerCase() +"\"" +
                    ",\"norms\":" + keyword.norms() +
                    processNull(keyword.nullValue()) +
                    ",\"store\":" + keyword.store()+
                    ProcessFieldsUtil.processFields(keyword.fields());
        }
        return "";
    }


    private String processNull(String nullValue) {
        if ("null".equals(nullValue)){
            return "";
        }else{
            return ",\"null_value\":" + "\"" + nullValue + "\"";
        }
    }
}
