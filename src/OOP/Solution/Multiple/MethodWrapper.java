package OOP.Solution.Multiple;

import java.lang.reflect.Method;

/**
 * Created by yasin on 6/12/2017.
 */
public class MethodWrapper
{

    private Method method;

    public MethodWrapper(Method method)
    {
        this.method = method;
    }

    public Method getMethod()
    {
        return method;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj instanceof MethodWrapper) {
            MethodWrapper other = (MethodWrapper)obj;
            if (method.getName() == other.getMethod().getName()) {
                if (!((method.getReturnType()).equals(other.getMethod().getReturnType())))
                    return false;
                return equalParamTypes(method.getParameterTypes(), other.getMethod().getParameterTypes());
            }
        }
        return false;
    }

    public boolean equalsWithDiffer(Object obj,int diff1,int diff2) {
        if (obj != null && obj instanceof MethodWrapper) {
            MethodWrapper other = (MethodWrapper)obj;
            if (method.getName() == other.getMethod().getName()) {
                if (!((method.getReturnType()).equals(other.getMethod().getReturnType())))
                    return false;
                return diff1!=-1 && diff2 != -1 && diff1 == diff2;
            }
        }
        return false;
    }

    boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        /* Avoid unnecessary cloning */
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i])
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
