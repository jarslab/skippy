package com.jarslab.skippy.dropwizard;

import static com.google.common.base.Preconditions.checkNotNull;

import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;
import com.jarslab.skippy.spi.ClassExceptionMapperProvider;
import com.jarslab.skippy.spi.ExceptionMapper;
import io.dropwizard.jersey.errors.LoggingExceptionMapper;
import java.lang.reflect.Method;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Dropwizard {@link LoggingExceptionMapper} that uses {@link com.jarslab.skippy.ExceptionMapping} annotations to match
 * and map exceptions into {@link Response} object.
 *
 * <h4>Usage</h4>
 * <pre>
 *     ClassExceptionMapperProvider mapperProvider =
 *         new ClassExceptionMapperProvider(listOfAnnotatedResourcesClasses);
 *     environment.jersey().register(new DropwizardExceptionMapper(true, false, mapperProvider));
 * </pre>
 */
public class DropwizardExceptionMapper extends LoggingExceptionMapper<Throwable>
{
    @Context
    private ResourceInfo resourceInfo;

    private final ExceptionMapper defaultExceptionMapper;
    private final ClassExceptionMapperProvider classExceptionMapperProvider;

    public DropwizardExceptionMapper(final Class<?> resourceClasses)
    {
        this(true, false, new ClassExceptionMapperProvider(resourceClasses));
    }

    public DropwizardExceptionMapper(final boolean registerLegacyMappers,
                                     final boolean showDetails,
                                     final Class<?> resourceClasses)
    {
        this(registerLegacyMappers, showDetails, new ClassExceptionMapperProvider(resourceClasses));
    }

    private DropwizardExceptionMapper(final boolean registerLegacyMappers,
                                      final boolean showDetails,
                                      final ClassExceptionMapperProvider classExceptionMapperProvider)
    {
        this.defaultExceptionMapper = registerLegacyMappers ?
            new DropwizardLegacyExceptionMapper(showDetails) :
            ClassExceptionMapperProvider.EMPTY_MAPPER;
        this.classExceptionMapperProvider = checkNotNull(classExceptionMapperProvider);
    }

    @Override
    public Response toResponse(final Throwable exception)
    {
        final Method resourceMethod = resourceInfo.getResourceMethod();
        final ExceptionMapper exceptionMapper = classExceptionMapperProvider.getMapper(resourceMethod);

        final ErrorDetails errorDetails = exceptionMapper.test(exception) ?
            exceptionMapper.apply(exception) :
            defaultExceptionMapper.apply(exception);
        return getResponse(exception, errorDetails);
    }

    private Response getResponse(final Throwable exception, final ErrorDetails errorDetails)
    {
        if (errorDetails instanceof ResponseErrorDetails) {
            return ((ResponseErrorDetails) errorDetails).getResponse();
        } else if (errorDetails instanceof HttpErrorDetails) {
            final HttpErrorDetails httpErrorDetails = (HttpErrorDetails) errorDetails;
            return Response.status(httpErrorDetails.getCode())
                .entity(httpErrorDetails)
                .build();
        } else {
            final long id = logException(exception);
            final String message = formatErrorMessage(id, exception);
            return Response.status(HttpCodes.INTERNAL_SERVER_ERROR_500)
                .entity(BaseHttpErrorDetails.of(HttpCodes.INTERNAL_SERVER_ERROR_500, message, exception.getMessage()))
                .build();
        }
    }
}
