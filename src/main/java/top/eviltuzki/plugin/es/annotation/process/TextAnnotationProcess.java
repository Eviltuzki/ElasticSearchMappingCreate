package top.eviltuzki.plugin.es.annotation.process;

import top.eviltuzki.plugin.es.annotation.Text;

import java.lang.annotation.Annotation;

public class TextAnnotationProcess implements AnnotationProcess {
    @Override
    public String process(Annotation annotation) {
        Text text = null;
        if (annotation instanceof Text) {
            text = (Text) annotation;
        }
        if (text != null) {
            return  "\"type\":\"text\""+
                    ",\"boost\":" + text.boost() +
                    ",\"analyzer\":\"" + text.analyzer() + "\"" +
                    ",\"eager_global_ordinals\":" + text.eagerGlobalOrdinals() +
                    ",\"include_in_all\":" + text.includeInAll() +
                    ",\"index\":" + text.index() +
                    ",\"index_options\":\"" + text.indexOptions().toString().toLowerCase() + "\"" +
                    ",\"norms\":" + text.norms() +
                    ",\"store\":" + text.store()+
                    ProcessFieldsUtil.processFields(text.fields());
        }
        return "";

    }
}
