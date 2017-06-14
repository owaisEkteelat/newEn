package OOP.Tests.Multiple.Example;
import  OOP.Provided.Multiple.OOPMultipleClassGenerator;

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
        OOPMultipleClassGenerator myGenerator = new OOPMultipleClassGenerator();
        I6 I6Obj = (I6) myGenerator.generateMultipleClass(I6.class);
        I6Obj.I6Func(5);
    }

    public void testGetClassName() throws Exception
    {
        String className = OOPMultipleControl.getClassName("OOP.Tests.Multiple.Example.I5");
        Assert.assertEquals("OOP.Tests.Multiple.Example.C5",className);
    }

    public void testGetClassMethod() throws Exception
    {
        /*
        Method methodInI5 = I5.class.getMethods()[0];
        Method methodInC5 =null;
        methodInC5 = OOPMultipleControl.getClassMethod(I5.class.getMethods()[0]);
        String excpectedMethodDeclaration = "public void OOP.Tests.Multiple.Example.C5.myFunc() throws OOP.Provided.Multiple.OOPMultipleException";
        Assert.assertEquals(excpectedMethodDeclaration,methodInC5.toString());

        Method methodInI4 = I4.class.getMethods()[0];
        Method methodInC4 =null;
        methodInC4 = OOPMultipleControl.getClassMethod(I4.class.getMethods()[0]);
        excpectedMethodDeclaration = "public void OOP.Tests.Multiple.Example.C4.myFunc() throws OOP.Provided.Multiple.OOPMultipleException";
        Assert.assertEquals(excpectedMethodDeclaration,methodInC4.toString());

        Method methodInI6 = I6.class.getMethods()[0];
        Method methodInC6 =null;
        methodInC6 = OOPMultipleControl.getClassMethod(I6.class.getMethods()[0]);
        excpectedMethodDeclaration = "public void OOP.Tests.Multiple.Example.C6.I6Func(int) throws OOP.Provided.Multiple.OOPMultipleException";
        Assert.assertEquals(excpectedMethodDeclaration,methodInC6.toString());
        */
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