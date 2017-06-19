package OOP.Solution.Trait;

import OOP.Provided.Trait.OOPTraitException;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class OOPTraitControl {

    //TODO: DO NOT CHANGE !!!!!!
    private Class<?> traitCollector;
    private File sourceFile;

    //TODO: DO NOT CHANGE !!!!!!
    public OOPTraitControl(Class<?> traitCollector, File sourceFile) {
        this.traitCollector = traitCollector;
        this.sourceFile = sourceFile;
    }

    //TODO: fill in here :
    public void validateTraitLayout() throws OOPTraitException {

    }

    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPTraitException {
        return null;
    }

    //TODO: add more of your code :

    public static boolean HandleAbsMethods(Class<?> currClass,ArrayList<Method> subsMethods)
    {
        Method[] currMethods = currClass.getMethods();
        if(currMethods != null)
        {
            for(Method curMethod : currMethods)
            {
                OOPMethod annotation = curMethod.getAnnotation(OOPMethod.class);
                if(annotation.modifier() == OOPMethodModifier.INTER_ABS)
                {
                    boolean hasImpl = false;
                    for(Method subMethod : subsMethods)
                    {
                        OOPMethod subAnnotation = curMethod.getAnnotation(OOPMethod.class);
                        if(subAnnotation.modifier() == OOPMethodModifier.INTER_IMPL)
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

    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
