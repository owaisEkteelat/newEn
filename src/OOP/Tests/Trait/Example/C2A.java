package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;

public class C2A implements T2A {

    @Override
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)    //only resolves missing implementation
    public void add(Integer v) throws OOPTraitException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
