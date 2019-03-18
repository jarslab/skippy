package org.skipta.skipta.core;

import java.lang.reflect.Method;
import java.util.function.Function;

public interface MethodExceptionMapperProvider extends Function<Method, ExceptionMapper>
{
}
