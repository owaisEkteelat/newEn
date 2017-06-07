package OOP.Solution.Multiple;

import OOP.Provided.Multiple.OOPMultipleException;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


public class OOPMultipleControl {

    //TODO: DO NOT CHANGE !!!!!!
    private Class<?> interfaceClass;
    private File sourceFile;

    //TODO: DO NOT CHANGE !!!!!!
    public OOPMultipleControl(Class<?> interfaceClass, File sourceFile) {
        this.interfaceClass = interfaceClass;
        this.sourceFile = sourceFile;
    }

    //TODO: fill in here :
    public void validateInheritanceGraph() throws OOPMultipleException {

    }

    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPMultipleException {
        return null;
    }

    //TODO: add more of your code :


    public boolean HasInherentAmbiguity(Class<?> cl){
        Queue<Class<?>> queue = new LinkedList<Class<?>>();
        LinkedList<Class<?>> types = new LinkedList<>();
        queue.add(cl);
        types.add(cl);
        //BFS:
        while (queue.isEmpty() == false) {
            Class<?> curr = queue.poll();
            Class<?>[] supers = curr.getInterfaces();
            for (Class<?> next : supers) {
                if (next != null && types.contains(next) == false) {
                    types.add(next);
                    queue.add(next);
                }
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
