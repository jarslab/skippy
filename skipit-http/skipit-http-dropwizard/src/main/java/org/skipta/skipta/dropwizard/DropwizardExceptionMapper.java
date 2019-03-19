package org.skipta.skipta.dropwizard;

import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.errors.LoggingExceptionMapper;
import org.eclipse.jetty.http.HttpStatus;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.core.ClassExceptionMapperProvider;
import org.skipta.skipta.core.DefaultExceptionMapper;
import org.skipta.skipta.core.ExceptionMapper;
import org.skipta.skipta.dropwizard.legacy.DropwizardLegacyExceptionMapper;
import org.skipta.skipta.http.HttpErrorDetails;

import javax.annotation.Nullable;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;

public class DropwizardExceptionMapper extends LoggingExceptionMapper<Throwable>
{
    @Context
    private ResourceInfo resourceInfo;

    private final ExceptionMapper defaultExceptionMapper;
    private final ClassExceptionMapperProvider classExceptionMapperProvider;

    public DropwizardExceptionMapper(final boolean registerLegacyMappers,
                                     final ClassExceptionMapperProvider classExceptionMapperProvider)
    {
        this.defaultExceptionMapper = registerLegacyMappers ?
                new DropwizardLegacyExceptionMapper() :
                DefaultExceptionMapper.EMPTY_MAPPER;
        this.classExceptionMapperProvider = checkNotNull(classExceptionMapperProvider);
    }

    @Override
    public Response toResponse(final Throwable exception)
    {
        final Method resourceMethod = resourceInfo.getResourceMethod();
        final ExceptionMapper exceptionMapper = classExceptionMapperProvider.getMapper(resourceMethod)
                .orElse(defaultExceptionMapper);

        final ErrorDetails errorDetails = exceptionMapper.test(exception) ? exceptionMapper.apply(exception) : null;
        return getResponse(exception, errorDetails);
    }

    private Response getResponse(final Throwable exception, @Nullable final ErrorDetails errorDetails)
    {
        if (errorDetails instanceof ResponseErrorDetails) {
            return ((ResponseErrorDetails) errorDetails).getResponse();
        } else if (errorDetails instanceof HttpErrorDetails) {
            final HttpErrorDetails httpErrorDetails = (HttpErrorDetails) errorDetails;
            return Response.status(httpErrorDetails.getStatusCode())
                    .entity(httpErrorDetails)
                    .build();
        } else {
            final long id = logException(exception);
            final String message = formatErrorMessage(id, exception);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500)
                    .entity(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR_500, message, exception.getMessage()))
                    .build();
        }
    }
}
