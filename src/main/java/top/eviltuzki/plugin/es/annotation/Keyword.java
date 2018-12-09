package top.eviltuzki.plugin.es.annotation;

import top.eviltuzki.plugin.es.consts.Const;
import top.eviltuzki.plugin.es.enums.IndexOptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Keyword {
    float boost() default 1.0f;
    boolean docValues() default true;
    boolean eagerGlobalOrdinals() default false;
    int ignoreAbove() default 2147483647;
    boolean includeInAll() default false;
    boolean index() default true;
    IndexOptions indexOptions() default IndexOptions.DOCS;
    boolean norms() default false;
    boolean store() default false;
    String nullValue() default Const.NULL_VALUE;
    Field[] fields() default {};
}
