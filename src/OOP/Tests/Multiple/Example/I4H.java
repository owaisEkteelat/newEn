package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.Multiple.OOPMultipleInterface;
import OOP.Solution.Multiple.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/6/2017.
 */
@OOPMultipleInterface
public interface I4H extends I4G{
    @OOPMultipleMethod
    default void g() throws OOPMultipleException{}
}
