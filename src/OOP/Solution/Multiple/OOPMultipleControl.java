package OOP.Solution.Multiple;

import OOP.Provided.Multiple.OOPBadClass;
import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Multiple.OOPInherentAmbiguity;
import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.OOPDebuggingException;
import com.sun.beans.finder.ClassFinder;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandlerImpl;
import javafx.util.Pair;
import sun.reflect.Reflection;
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

        HashSet<Pair<Class<?>, Method>> candidates = new HashSet<Pair<Class<?>, Method>>();

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
                        candidates.add(new Pair<>(methodWrapper.getMethod().getDeclaringClass(), methodWrapper.getMethod()));
                        candidates.add(new Pair<>(curMethodWrapper.getMethod().getDeclaringClass(), curMethodWrapper.getMethod()));
//                        System.out.println(methodWrapper.getMethod().getDeclaringClass().toString() + methodWrapper.getMethod().toString());
//                        System.out.println(curMethodWrapper.getMethod().getDeclaringClass().toString() + curMethodWrapper.getMethod().toString());
                    }
                }
            }
            currMethods.addAll(parentMethods);
        }

        if (candidates.size() != 0)
        {
            throw new OOPCoincidentalAmbiguity(candidates);
        }

        for (Method method : cl.getMethods())
        {
            for (MethodWrapper methodWrapper : currMethods)
            {
                if (methodWrapper.equals(new MethodWrapper(method))) currMethods.remove(methodWrapper);
            }
            currMethods.add(new MethodWrapper(method));
        }
        return currMethods;
    }

    public Object invoke(String methodName, Object[] args) throws OOPMultipleException
    {

        HashSet<MethodWrapper> funcsSet = func(interfaceClass);

        for (MethodWrapper methodWrapper : funcsSet)
        {
            if (methodWrapper.getMethod().getName() == methodName)
            {
                try
                {
                    Object toReturn = methodWrapper.getMethod().invoke(this, args);
                    return toReturn;
                } catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        return null;
    }

    public static String getClassName(String interfaceName)
    {
        int indexOfLastPoint = interfaceName.lastIndexOf(".");
        String className = interfaceName.substring(0, indexOfLastPoint + 1) + 'C' + interfaceName.substring(indexOfLastPoint + 2);
        return className;
    }

    public static Method getClassMethod(Method InterfaceMethod) throws OOPMultipleException
    {
        String interfaceName = InterfaceMethod.getDeclaringClass().getName();
        String className = OOPMultipleControl.getClassName(interfaceName);
        MethodWrapper paramMethodWrapper = new MethodWrapper(InterfaceMethod);

        Class<?> matchesClass = null;
        try
        {
            matchesClass = ClassFinder.findClass(className);
        } catch (Exception e)
        {
            throw new OOPDebuggingException("Problem in finding matches class to " + interfaceName);
        }

        for (Method currentMethod : matchesClass.getMethods())
        {
            MethodWrapper currentMethodWrapper = new MethodWrapper(currentMethod);
            if (currentMethodWrapper.equals(paramMethodWrapper))
            {
               // System.out.println(currentMethod);
                return currentMethod;
            }
        }
        throw new OOPDebuggingException(" Method " + InterfaceMethod.getName() + " cannot found in " + className + " which(the class) founded match " + interfaceName);
    }

    private static int GetArgsDiffirence(Object[] args1, Object[] args2)
    {
        int counter = 0;

        if (args1.length != args2.length)
        {
            return -1;
        }

        for (int i = 0; i < args1.length; i++)
        {
            Class<?> type1 = args1[i].getClass();
            Class<?> type2 = args2[i].getClass();
            if (!(type1.equals(type2)))
            {

            }
        }

        return counter;
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
    interface F1
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface G1 extends F1
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface H1 extends G1
    {
//        @OOPMultipleMethod
//        public void getfive();
    }

    @OOPMultipleInterface
    interface F2
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface G2 extends F2
    {
//        @OOPMultipleMethod
//        public void getfive();
    }

    @OOPMultipleInterface
    interface H2 extends G2
    {
//        @OOPMultipleMethod
//        public void getfive();
    }

    @OOPMultipleInterface
    interface H3
    {
        @OOPMultipleMethod
        public void getfive();
    }

    @OOPMultipleInterface
    interface I extends H1, H2, H3
    {

    }

    @OOPMultipleInterface
    interface I2 extends H1
    {

    }


    public static void main(String[] args)
    {
        System.out.println(B.class.getMethods()[0].getDeclaringClass());

        System.out.println((new MethodWrapper(F1.class.getMethods()[0])).equals((new MethodWrapper(G1.class.getMethods()[0]))));


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
