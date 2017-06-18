package OOP.Solution.Trait;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static OOP.Solution.Trait.OOPMethodModifier.INTER_ABS;

/**
 * Created by owais on 18-Jun-17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OOPMethod
{
    public  OOPMethodModifier modifier() default INTER_ABS  ;
}
