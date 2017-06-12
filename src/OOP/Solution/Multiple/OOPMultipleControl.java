package OOP.Solution.Multiple;

import OOP.Provided.Multiple.OOPBadClass;
import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Multiple.OOPInherentAmbiguity;
import OOP.Provided.Multiple.OOPMultipleException;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandlerImpl;
import javafx.util.Pair;
import sun.reflect.annotation.AnnotationType;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.annotation.Annotation;
import java.util.*;


public class OOPMultipleControl
{

    //TODO: DO NOT CHANGE !!!!!!
    private Class<?> interfaceClass;
    private File sourceFile;

    //TODO: DO NOT CHANGE !!!!!!
    public OOPMultipleControl(Class<?> interfaceClass, File sourceFile)
    {
        this.interfaceClass = interfaceClass;
        this.sourceFile = sourceFile;
    }

    //TODO: fill in here :
    public void validateInheritanceGraph() throws OOPMultipleException
    {
        HasInherentAmbiguity(interfaceClass);

    }

    //TODO: fill in here :
    public static HashSet<MethodWrapper> func(Class<?> cl) throws OOPMultipleException
    {
        Class<?>[] supers = cl.getInterfaces();
        HashSet<MethodWrapper> currMethods = new HashSet<>();
        if (supers.length == 0)
        {
            for (Method method : cl.getMethods())
            {
                currMethods.add(new MethodWrapper(method));
            }
            return currMethods;
        }

        for (Class<?> parent : supers)
        {
            HashSet<MethodWrapper> parentMethods = new HashSet<>();
            parentMethods = func(parent);

            for (MethodWrapper methodWrapper : parentMethods)
            {
                for (MethodWrapper curMethodWrapper : currMethods)
                {
                    if (curMethodWrapper.equals(methodWrapper))
                    {
                        HashSet<Pair<Class<?>, Method>> candidates = new HashSet<Pair<Class<?>, Method>>();
                        candidates.add(new Pair<>(methodWrapper.getMethod().getClass(), methodWrapper.getMethod()));
                        candidates.add(new Pair<>(curMethodWrapper.getMethod().getClass(), curMethodWrapper.getMethod()));
                        throw new OOPCoincidentalAmbiguity(candidates);
                    }
                }
            }
            currMethods.addAll(parentMethods);
        }

        for (Method method : cl.getMethods())
        {
            currMethods.add(new MethodWrapper(method));
        }
        return currMethods;
    }

    public Object invoke(String methodName, Object[] args) throws OOPMultipleException
    {

        return null;
    }

    public static void HasCoincidentalAmbiguity(Class<?> cl) throws OOPMultipleException
    {


    }


    //TODO: add more of your code :

    //****************************************************************
    //TODO: 1) check code duplicate in HasInherentAmbiguity functions.
    //TODO: 2) check which inteface should pass to the exception.
    //TODO: remember that you get a BFS code from internet.
    public static void HasInherentAmbiguity(Class<?> cl) throws OOPMultipleException
    {
        Queue<Class<?>> queue = new LinkedList<Class<?>>();
        Set<Class<?>> types = new HashSet<>();
        queue.add(cl);
        types.add(cl);

        if (!cl.isAnnotationPresent(OOPMultipleInterface.class))
        {
            throw new OOPBadClass(cl); // change c1 to interfaceClass
        }

        Method[] clMethods = cl.getDeclaredMethods();
        for (Method method : clMethods)
        {
            if (!method.isAnnotationPresent(OOPMultipleMethod.class))
            {
                throw new OOPBadClass(cl); // change c1 to interfaceClass
            }
        }

        //BFS:
        while (!queue.isEmpty())
        {
            Class<?> curr = queue.poll();

            Class<?>[] supers = curr.getInterfaces();

            for (Class<?> next : supers)
            {
                if (next == null)
                {
                    break;
                }

                if (!next.isAnnotationPresent(OOPMultipleInterface.class))
                {
                    throw new OOPBadClass(cl); // change c1 to interfaceClass
                }

                Method[] nextMethods = next.getDeclaredMethods();
                for (Method method : nextMethods)
                {
                    if (!method.isAnnotationPresent(OOPMultipleMethod.class))
                    {
                        throw new OOPBadClass(cl); // change c1 to interfaceClass
                    }
                }

                if (types.contains(next))
                {
                    for (Method method : nextMethods)
                    {
                        if (!Modifier.isPrivate(method.getModifiers()))
                        {
                            throw new OOPInherentAmbiguity(cl, next, method); // change c1 to interfaceClass
                        }
                    }
                }

                if (!types.contains(next))
                {
                    types.add(next);
                    queue.add(next);
                }
            }
        }

    }

    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile()
    {
        if (sourceFile.exists())
        {
            sourceFile.delete();
        }
    }


    @OOPMultipleInterface
    interface C
    {
        @OOPMultipleMethod
        public void getfive();
    }


    @OOPMultipleInterface
    interface B1 extends C
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface B2 extends C
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface A extends B1, B2
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface B extends B1
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface H1
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface H2
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface I extends H1,H2
    {

    }



    public static void main(String[] args)
    {
        System.out.println(B.class.getMethods()[0].getDeclaringClass());

        try
        {
            func(I.class);
        } catch (OOPCoincidentalAmbiguity e)
        {
            System.out.println("Failed OOPCoincidentalAmbiguity: " + e.getMessage());
        } catch (Exception e)
        {
            System.out.println("Failed Built In exception");
        }

//        try
//        {
//            OOPMultipleControl.HasInherentAmbiguity(A.class);
//        } catch (OOPInherentAmbiguity e)
//        {
//            System.out.println("Failed OOPInherentAmbiguity");
////            System.out.println(e);
//        } catch (OOPBadClass e)
//        {
//            System.out.println("Failed OOPBadClass");
////            System.out.println(e);
//        } catch (Exception e)
//        {
//
//            System.out.println("Failed Built In exception");
//        }


    }
}
