package OOP.Solution.Trait;

import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Provided.Trait.OOPTraitConflict;
import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Multiple.MethodWrapper;
import OOP.Solution.OOPDebuggingExceptionTraits;
import OOP.Solution.Multiple.OOPMultipleControl;
import javafx.util.Pair;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;

import java.util.ArrayList;
import java.util.HashSet;

public class OOPTraitControl
{

    //TODO: DO NOT CHANGE !!!!!!
    private Class<?> traitCollector;
    private File sourceFile;

    //TODO: DO NOT CHANGE !!!!!!
    public OOPTraitControl(Class<?> traitCollector, File sourceFile)
    {
        this.traitCollector = traitCollector;
        this.sourceFile = sourceFile;
    }


    public static MethodWrapper getMethodWrapperByClass (Class<?> myClass, MethodWrapper parMethodWrapper) throws OOPDebuggingExceptionTraits {
        for ( Method currentMethod : myClass.getMethods() ) {
            MethodWrapper currentMethodWrapper = new MethodWrapper(currentMethod);
            if ( currentMethodWrapper.equals(parMethodWrapper) )  {
                return currentMethodWrapper;
            }
        }
        throw new OOPDebuggingExceptionTraits(parMethodWrapper.toString() + " is not exist in : " + myClass );
    }

    public static void certainThatMethodSolveTheConflict ( MethodWrapper methodWrapper ) throws OOPTraitException {
        OOPTraitMethod traitMethodAnnotation = null;
        try
        {
           traitMethodAnnotation = methodWrapper.getMethod().getAnnotation(OOPTraitMethod.class);
        } catch (NullPointerException e)
        {
            throw new OOPTraitConflict(methodWrapper.getMethod());
        }

        if (traitMethodAnnotation.modifier() != OOPTraitMethodModifier.INTER_CONFLICT)
        {
            throw new OOPTraitConflict(methodWrapper.getMethod());
        }
    }

    // include child
    public static HashSet<MethodWrapper> getParentsMethodsWithoutConflicts(Class<?> child, Class<?> myTraitCollector) throws OOPTraitException
    {
        Class<?>[] supers = child.getInterfaces();
        HashSet<MethodWrapper> currMethods = new HashSet<>();

        // Halt Condition
        if (supers.length == 0)
        {
            for (Method method : child.getMethods())
            {
                currMethods.add(new MethodWrapper(method));
            }
            return currMethods;
        }

        for (Class<?> currentParent : supers)
        {
            HashSet<MethodWrapper> currentParentMethods = new HashSet<>();
            currentParentMethods = getParentsMethodsWithoutConflicts(currentParent, myTraitCollector);

            for (MethodWrapper ParentMethodWrapper : currentParentMethods)
            {
                for (MethodWrapper curMethodWrapper : currMethods)
                {
                    if (curMethodWrapper.equals(ParentMethodWrapper))
                    {
                        MethodWrapper traitMethod = getMethodWrapperByClass(myTraitCollector, ParentMethodWrapper);
                        certainThatMethodSolveTheConflict(traitMethod);
                    }
                }
            }
        }

            for (Method method : child.getMethods())
            {
                for (MethodWrapper methodWrapper : currMethods)
                {
                    if (methodWrapper.equals(new MethodWrapper(method)))
                        currMethods.remove(methodWrapper);
                }

                currMethods.add(new MethodWrapper(method));
            }
            return currMethods;
        }





    //TODO: fill in here :
    public void validateTraitLayout() throws OOPTraitException
    {

    }

    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args) throws OOPTraitException
    {
        return null;
    }

    //TODO: add more of your code :

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
                int curCastInt = OOPMultipleControl.GetArgsDiffirence(args, methodWrapper.getParameterTypes());
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
                    int curCastInt = OOPMultipleControl.GetArgsDiffirence(args, method.getParameterTypes());
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
            OOPTraitMethod annotation = minimumCastMethod.getAnnotation(OOPTraitMethod.class);
            if(annotation.modifier() == OOPTraitMethodModifier.INTER_CONFLICT)
            {
                OOPTraitConflictResolver conflectResolver = minimumCastMethod.getAnnotation(OOPTraitConflictResolver.class);
                Method[] methodClassmethods = conflectResolver.resolve().getMethods();
                for(Method method : methodClassmethods)
                {
                    if(method.getName() == minimumCastMethod.getName()
                            && method.getParameterCount() == minimumCastMethod.getParameterCount())
                    {
                        return method;
                    }
                }
            }
            throw new OOPCoincidentalAmbiguity(candidates);
        }

        return minimumCastMethod;
    }

    public static boolean HandleAbsMethods(Class<?> currClass,ArrayList<Method> subsMethods)
    {
        Method[] currMethods = currClass.getMethods();
        if(currMethods != null)
        {
            for(Method curMethod : currMethods)
            {
                OOPTraitMethod annotation = curMethod.getAnnotation(OOPTraitMethod.class);
                if(annotation.modifier() == OOPTraitMethodModifier.INTER_ABS)
                {
                    boolean hasImpl = false;
                    for(Method subMethod : subsMethods)
                    {
                        OOPTraitMethod subAnnotation = curMethod.getAnnotation(OOPTraitMethod.class);
                        if(subAnnotation.modifier() == OOPTraitMethodModifier.INTER_IMPL)
                        {
                            hasImpl = true;
                        }
                    }
                    if(!hasImpl)
                    {
                        return false;
                    }
                }
            }
        }

        ArrayList<Method> currAndSubsMethods = new ArrayList<>();
        currAndSubsMethods.addAll(subsMethods);
        for (Method method : currClass.getMethods())
        {
            currAndSubsMethods.add(method);
        }

        for(Class<?> superClass : currClass.getInterfaces())
        {
            if(!HandleAbsMethods(superClass,currAndSubsMethods))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean AreAnnotated(Class<?> currClass)
    {
        if(!currClass.isAnnotationPresent(OOPTraitBehaviour.class))
        {
            return false;
        }

        if(currClass.getMethods() != null)
        {
            for (Method method : currClass.getMethods())
            {
                if(!method.isAnnotationPresent(OOPTraitMethod.class))
                {
                    return false;
                }
            }
        }

        for(Class<?> superClass : currClass.getInterfaces())
        {
            if(!AreAnnotated(superClass))
            {
                return false;
            }
        }

        return true;
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
