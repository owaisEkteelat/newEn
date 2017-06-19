package OOP.Solution;

import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Provided.Trait.OOPTraitException;

/**
 * Created by owais on 19-Jun-17.
 */
public class OOPDebuggingExceptionTraits extends OOPTraitException
{
    private String message;

    public OOPDebuggingExceptionTraits(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return  "Debugging Exception Traits : " + message;
    }
}
