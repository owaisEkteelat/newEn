package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

import static OOP.Tests.Trait.Example.TestTrait.obj2R;

/**
 * Created by danie_000 on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T2Q {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    default String startTalking() throws OOPTraitException{
        return obj2R.hello();
    }
    @OOPTraitMethod // Abstract :)
    String finishTalking() throws OOPTraitException;
}
