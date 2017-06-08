package OOP.Solution.Multiple;

import OOP.Provided.Multiple.OOPBadClass;
import OOP.Provided.Multiple.OOPInherentAmbiguity;
import OOP.Provided.Multiple.OOPMultipleException;
import sun.reflect.annotation.AnnotationType;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;


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

    }

    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args) throws OOPMultipleException
    {
        return null;
    }

    //TODO: add more of your code :


    public boolean HasInherentAmbiguity(Class<?> cl) throws OOPMultipleException
    {
        Queue<Class<?>> queue = new LinkedList<Class<?>>();
        Set<Class<?>> types = new HashSet<>();
        queue.add(cl);
        types.add(cl);
        //BFS:
        while (queue.isEmpty() == false)
        {
            Class<?> curr = queue.poll();
            Class<?>[] supers = curr.getInterfaces();
            for (Class<?> next : supers)
            {
                if(next == null ){
                    break;
                }

                Annotation[] annotations = next.getClass().getDeclaredAnnotations();
                if(!next.getClass().isAnnotationPresent(OOPMultipleInterface.class))
                {
                    throw new OOPBadClass(interfaceClass);
                }

                if (types.contains(next))
                {
                    Method[] nextMethods = next.getDeclaredMethods();
                    for (Method method : nextMethods)
                    {
                        if(!method.isAnnotationPresent(OOPMultipleMethod.class))
                        {
                            throw new OOPBadClass(interfaceClass);
                        }

                        if (!Modifier.isPrivate(method.getModifiers()))
                        {
                            throw new OOPInherentAmbiguity(interfaceClass, next, method);
                        }
                    }
                }

                if (types.contains(next) == false)
                {
                    types.add(next);
                    queue.add(next);
                }
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
