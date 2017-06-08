package OOP.Solution;

import OOP.Solution.Multiple.OOPMultipleInterface;

import java.lang.annotation.Annotation;

/**
 * Created by owais on 07-Jun-17.
 */
public class test1
{
    public static void main(String [] args) {
        @OOPMultipleInterface
         class test2 {

        }

        Annotation[] annotations = test2.class.getAnnotations();
        for ( Annotation currentAnnotaion : annotations  ) {
           System.out.println(currentAnnotaion instanceof OOPMultipleInterface);

        }

    }
}
