package playwright_java_multithread;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target({ TYPE, METHOD, CONSTRUCTOR })
/**
 * 
 * Anything annotated with this annotation is intended only for <b>**internal use**</b> only.
 * Users are highly discouraged from directly using these internal components outside their packages.
 *
 */
@interface Internal {}
