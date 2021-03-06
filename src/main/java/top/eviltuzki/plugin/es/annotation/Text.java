package top.eviltuzki.plugin.es.annotation;

import top.eviltuzki.plugin.es.enums.IndexOptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Text {
    String analyzer() default "standard analyzer";
    float boost() default 1.0f;
    boolean eagerGlobalOrdinals() default false;
    boolean includeInAll() default false;
    boolean index() default true;
    IndexOptions indexOptions() default IndexOptions.POSITIONS;
    boolean norms() default true;
    boolean store() default false;
    Field[] fields() default {};
}
