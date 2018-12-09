package top.eviltuzki.plugin.es;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class Java2EsTypeMapping {
    private static Map<String,String> mapping = new HashMap<>();
    static {
        //Byte -> short
        mapping.put(Byte.class.getName(),"short");
        mapping.put("byte","short");
        mapping.put("[B","short");

        //Short -> short
        mapping.put(Short.class.getName(),"short");
        mapping.put("short","short");
        mapping.put("[S","short");

        //Integer -> integer
        mapping.put(Integer.class.getName(),"integer");
        mapping.put("int","integer");
        mapping.put("[I","integer");

        //Long -> long
        mapping.put(Long.class.getName(),"long");
        mapping.put("long","long");
        mapping.put("[J","long");

        //Float -> float
        mapping.put(Float.class.getName(),"float");
        mapping.put("float","float");
        mapping.put("[F","float");

        //Double -> double
        mapping.put(Double.class.getName(),"double");
        mapping.put("double","double");
        mapping.put("[D","double");

        //Boolean -> boolean
        mapping.put(Boolean.class.getName(),"boolean");
        mapping.put("boolean","boolean");
        mapping.put("[Z","boolean");
        //BigDecimal -> double
        mapping.put(BigDecimal.class.getName(),"double");

        //String
        mapping.put(String.class.getName(),"string");

        //Date
        mapping.put(Date.class.getName(),"date");

    }


    static String getName(String typeName) {
        return mapping.getOrDefault(typeName,"object");
    }


}