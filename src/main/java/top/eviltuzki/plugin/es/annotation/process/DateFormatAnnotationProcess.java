package top.eviltuzki.plugin.es.annotation.process;

import top.eviltuzki.plugin.es.annotation.DateFormat;

import java.lang.annotation.Annotation;
import java.util.Date;

public class DateFormatAnnotationProcess implements AnnotationProcess {
    @DateFormat
    private Date defaultValue;
    @Override
    public String process(Annotation annotation) {
        DateFormat text = null;
        if (null == annotation){
            try {
                text = this.getClass().getDeclaredField("defaultValue").getDeclaredAnnotation(DateFormat.class);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }else  if (annotation instanceof DateFormat) {
            text = (DateFormat) annotation;
        }
        if (text != null) {
            return  "\"type\":\"date\",\"format\":\"" + text.format()+"\"";
        }
        return "";
    }
}
