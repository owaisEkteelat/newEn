package OOP.Tests.Multiple.Example;

import OOP.Solution.Multiple.OOPMultipleControl;
import com.sun.beans.finder.ClassFinder;
import junit.framework.TestCase;
import org.junit.Assert;

import java.lang.reflect.Method;

/**
 * Created by owais on 14-Jun-17.
 */

public class OOPMultipleControlTest extends TestCase
{
    public void setUp() throws Exception
    {
        super.setUp();
    }

    public void tearDown() throws Exception
    {
    }

    public void testValidateInheritanceGraph() throws Exception
    {
    }

    public void testFunc() throws Exception
    {
    }

    public void testInvoke() throws Exception
    {
    }

    public void testGetClassName() throws Exception
    {
        String className = OOPMultipleControl.getClassName("OOP.Tests.Multiple.Example.I5");
        Assert.assertEquals("OOP.Tests.Multiple.Example.C5",className);
    }

    public void testGetClassMethod() throws Exception
    {
        /*
        System.out.println(ClassFinder.findClass("OOP.Tests.Multiple.Example.C5").);
        System.out.println(I5.class.getMethods()[0].getName());
        System.out.println(I4.class.getMethods()[0].getName());
*/
        OOPMultipleControl.getClassMethod(I5.class.getMethods()[0]);

    }

    public void testHasInherentAmbiguity() throws Exception
    {
    }

    public void testRemoveSourceFile() throws Exception
    {
    }

    public void testMain() throws Exception
    {
    }

}