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
        GetAndCheckDiamond(interfaceClass,interfaceClass);
    }

    public static int CheckCastDepth(Class<?> a, Class<?> b)
    {
        try
        {
            if (!(b.isInstance(a.newInstance())))
            {
                return -1;
            }
        } catch (Exception e)
        {

        }
        if (a == b)
        {
            return 0;
        }

        Class<?> cls = a.getSuperclass();

        int res = CheckCastDepth(cls, b);
        if (res >= 0)
        {
            return 1 + res;
        }


        return -1;
    }

    //TODO: fill in here :
    public static Method GetBestFitMethod(Class<?> forClass, Object[] args, String methodName) throws OOPMultipleException
    {
        Method[] funcsSet = forClass.getMethods();
        Method minimumCastMethod = null;
        int minCast = Integer.MAX_VALUE;

        HashSet<Method> currMethods = new HashSet<>();
        Class<?>[] supers = forClass.getInterfaces();
        if (supers.length != 0)
        {
            for (Class<?> currClass : supers)
            {
                Method method = GetBestFitMethod(currClass, args, methodName);
                if (method != null)
                {
                    currMethods.add(method);
                }
            }
        }

        HashSet<Pair<Class<?>, Method>> candidates = new HashSet<Pair<Class<?>, Method>>();
        for (Method methodWrapper : currMethods)
        {
            if (methodWrapper.getName() == methodName)
            {
                int curCastInt = GetArgsDiffirence(args, methodWrapper.getParameterTypes());
                if (curCastInt != -1 && curCastInt <= minCast)
                {
                    if (curCastInt == minCast)
                    {
                        candidates.add(new Pair<>(methodWrapper.getDeclaringClass(), methodWrapper));
                        candidates.add(new Pair<>(minimumCastMethod.getDeclaringClass(), minimumCastMethod));
                    }
                    else
                    {
                        minimumCastMethod = methodWrapper;
                        minCast = curCastInt;
                        candidates.clear();
                    }
                }
            }
        }

        boolean inCur = false;
        if (funcsSet != null)
        {
            for (Method method : funcsSet)
            {
                if (method.getDeclaringClass() != forClass)
                {
                    continue;
                }
                if (method.getName() == methodName)
                {
                    int curCastInt = GetArgsDiffirence(args, method.getParameterTypes());
                    if (curCastInt != -1 && curCastInt <= minCast)
                    {
                        if (curCastInt == minCast)
                        {
                            candidates.add(new Pair<>(method.getDeclaringClass(), method));
                            candidates.add(new Pair<>(minimumCastMethod.getDeclaringClass(), minimumCastMethod));
                        }
                        else
                        {
                            minimumCastMethod = method;
                            minCast = curCastInt;
                            candidates.clear();
                        }
                    }
                }
            }
        }

        if (candidates.size() != 0)
        {
            throw new OOPCoincidentalAmbiguity(candidates);
        }

        return minimumCastMethod;
    }

    public Object invoke(String methodName, Object[] args) throws OOPMultipleException
    {
        Method minimumCastMethod = GetBestFitMethod(interfaceClass, args, methodName);
        if (minimumCastMethod != null)
        {
            try
            {
                Method classMethod = getClassMethod(minimumCastMethod);
                Class<?> methodClass = classMethod.getDeclaringClass();
                Object toReturn = classMethod.invoke(methodClass.newInstance(), args);
                return toReturn;
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

        return null;
    }

    private static int GetArgsDiffirence(Object[] args1, Class<?>[] args2)
    {
        if (args1 == null || args2.length == 0)
        {
            if (args1 == null && args2.length == 0)
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }
        HashMap<Class<?>, Class<?>> PrimMap = new HashMap<Class<?>, Class<?>>();
        PrimMap.put(boolean.class, Boolean.class);
        PrimMap.put(byte.class, Byte.class);
        PrimMap.put(char.class, Character.class);
        PrimMap.put(double.class, Double.class);
        PrimMap.put(float.class, Float.class);
        PrimMap.put(int.class, Integer.class);
        PrimMap.put(long.class, Long.class);
        PrimMap.put(short.class, Short.class);
        PrimMap.put(void.class, Void.class);

        int counter = 0;

        if (args1.length != args2.length)
        {
            return -1;
        }

        for (int i = 0; i < args1.length; i++)
        {
            if (args2[i].isPrimitive())
            {
                args2[i] = PrimMap.get(args2[i]);
            }

            Class<?> type1 = args1[i].getClass();

            if (args2[i].isInstance(args1[i]))
            {
                if (type1 != args2[i])
                {
                    counter += CheckCastDepth(type1, args2[i]);
                }
            }
            else
            {
                return -1;
            }

        }

        return counter;
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
                return currentMethod;
            }
        }
        throw new OOPDebuggingException(" Method " + InterfaceMethod.getName() + " cannot found in " + className + " which(the class) founded match " + interfaceName);
    }

    //TODO: add more of your code :

    public static ArrayList<Class<?>> GetAndCheckDiamond(Class<?> cl, Class<?> initClass) throws OOPMultipleException
    {
        ArrayList<Class<?>> allSupers = new ArrayList<>();
        Class<?>[] supers = cl.getInterfaces();
        if (supers == null)
        {
            return null;
        }

        for (Class<?> next : supers)
        {
            allSupers.add(next);
        }

        for (Class<?> next : supers)
        {
            ArrayList<Class<?>> curSupers = GetAndCheckDiamond(next,initClass);
            if (curSupers != null)
            {
                for (Class<?> nextdou : curSupers)
                {
                    if (allSupers.contains(nextdou))
                    {
                        Method[] nextMethods = nextdou.getDeclaredMethods();
                        for (Method method : nextMethods)
                        {
                            if (!Modifier.isPrivate(method.getModifiers()))
                            {
                                throw new OOPInherentAmbiguity(initClass, method.getDeclaringClass(), method); // change c1 to interfaceClass
                            }
                        }
                    }
                    else
                    {
                        allSupers.add(nextdou);
                    }
                }
            }
        }

        return allSupers;
    }

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
                    throw new OOPBadClass(next); // change c1 to interfaceClass
                }

                Method[] nextMethods = next.getDeclaredMethods();
                for (Method method : nextMethods)
                {
                    if (!method.isAnnotationPresent(OOPMultipleMethod.class))
                    {
                        throw new OOPBadClass(method); // change c1 to interfaceClass
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
}