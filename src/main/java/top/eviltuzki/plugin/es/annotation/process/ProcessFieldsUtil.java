package top.eviltuzki.plugin.es.annotation.process;

import top.eviltuzki.plugin.es.annotation.Field;

class ProcessFieldsUtil {
    static String processFields(Field[] fields) {
        StringBuilder stringBuilder = new StringBuilder(",\"fields\":{");
        for (int i = 0; i < fields.length; i++) {
            stringBuilder.append(geneSubType(fields[i]));
            if (fields.length-1 != i){
                stringBuilder.append(",");
            }

        }
        return stringBuilder.append("}").toString();
    }
    private static String geneSubType(Field field) {
        if (field.isKeyword()){
            return "\"" + field.name() +"\":{\"type\": \"keyword\"}";
        }else {
            return "\"" + field.name() +"\":{\"type\": \"text\",\"analyzer\":\""+field.analyzer()+"\"}";
        }
    }
}
