package com.jarslab.skippy.dropwizard;

import static com.google.common.base.Preconditions.checkNotNull;

import com.jarslab.skippy.ExceptionMapping;
import com.jarslab.skippy.ExceptionMappings;
import com.jarslab.skippy.http.details.BadRequestErrorDetails;
import java.util.concurrent.atomic.AtomicReference;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/skippy")
@Produces(MediaType.APPLICATION_JSON)
@ExceptionMappings({
    @ExceptionMapping(exceptions = IllegalStateException.class, errorDetails = BadRequestErrorDetails.class),
    @ExceptionMapping(exceptions = IllegalArgumentException.class, errorDetails = BadRequestErrorDetails.class),
    @ExceptionMapping(exceptions = SkippyTestException.class, mapper = SkippyTestExceptionMapper.class)
})
public class SkippyTestResource
{
    private final AtomicReference<Exception> exception;

    SkippyTestResource(final AtomicReference<Exception> exception)
    {
        this.exception = checkNotNull(exception);
    }

    @GET
    @Path("/fail")
    public void fail() throws Exception
    {
        final Exception exception = this.exception.get();
        if (exception != null) {
            throw exception;
        }
    }

    @GET
    @Path("/illegal-state")
    public void illegalState()
    {
        throw new IllegalStateException();
    }

    @GET
    @Path("/illegal-argument")
    public void illegalArgument()
    {
        throw new IllegalArgumentException();
    }

    @GET
    @Path("/custom")
    public void customException()
    {
        throw new CustomSkippyTestException("skippy");
    }
}
