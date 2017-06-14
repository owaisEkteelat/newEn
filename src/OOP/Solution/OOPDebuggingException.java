package OOP.Solution;


import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by owais on 14-Jun-17.
 */
public class OOPDebuggingException extends OOPMultipleException
{
    private String message;

    public OOPDebuggingException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return  "Debugging Exception : " + message;
    }
}