package schoolSystem.annotations;

import schoolSystem.entity.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface PreAuthenticate {

    boolean loggedIn() default false;

    String inRole() default Constants.USER;
}
