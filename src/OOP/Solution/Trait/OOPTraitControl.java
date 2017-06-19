package OOP.Solution.Trait;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Multiple.MethodWrapper;
import OOP.Solution.OOPDebuggingExceptionTraits;
import javafx.util.Pair;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;


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

    static List<Method> allMethodsInParents(Class<?> base)
    {

        return null;
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

        // the
    //    HashSet<Method> candidatesMethods = new HashSet<Method>();

        for (Class<?> currentParent : supers)
        {
            HashSet<MethodWrapper> currentParentMethods = new HashSet<>();
            currentParentMethods = getParentsMethodsWithoutConflicts(currentParent,myTraitCollector);

            for (MethodWrapper methodWrapper : currentParentMethods)
            {
                for (MethodWrapper curMethodWrapper : currMethods)
                {
                    if (curMethodWrapper.equals(methodWrapper))
                    {
                            MethodWrapper traitMethod =  getMethodWrapperByClass(myTraitCollector,methodWrapper);
                            Annotation  traitMethodAnootation = traitMethod.getMethod().getAnnotation(OOPTra)



                    }
                }
            }
            currMethods.addAll(parentMethods);
        }


        return null;
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


    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile()
    {
        if (sourceFile.exists())
        {
            sourceFile.delete();
        }
    }
}
