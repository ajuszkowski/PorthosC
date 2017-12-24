package mousquetaires.app.options;

import mousquetaires.starters.AppModule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;


/**
 * Annotation used together with com.beust.jcommander.Parameter
 * for non-required fields (where `@Parameter(required = false)`)
 * in order to specify which application module require that paramter.
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD })
public @interface RequiredBy {
    Class<? extends AppModule>[] modules();
}
