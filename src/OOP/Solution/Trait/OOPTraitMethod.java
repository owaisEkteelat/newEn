package OOP.Solution.Trait;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_ABS;

/**
 * Created by yasin on 6/19/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OOPTraitMethod
{
    public  OOPTraitMethodModifier modifier() default INTER_ABS  ;
}
