package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.Multiple.OOPMultipleInterface;
import OOP.Solution.Multiple.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/6/2017.
 */
@OOPMultipleInterface
public interface I2C extends I2A,I2B {
    @OOPMultipleMethod
    Integer f() throws OOPMultipleException;
}
