package org.skipta.skipta.dropwizard.legacy;

import io.dropwizard.jersey.validation.ConstraintMessage;
import io.dropwizard.jersey.validation.JerseyViolationException;
import org.glassfish.jersey.server.model.Invocable;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.core.ExceptionMapper;
import org.skipta.skipta.http.BaseHttpErrorDetails;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public class JerseyViolationExceptionMapper implements ExceptionMapper
{
    private static final String MESSAGE = "Object validation failure";

    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        final JerseyViolationException exception = (JerseyViolationException) throwable;
        final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        final Invocable invocable = exception.getInvocable();
        final String details = exception.getConstraintViolations().stream()
                .map(violation -> ConstraintMessage.getMessage(violation, invocable))
                .collect(Collectors.joining(","));

        final int status = ConstraintMessage.determineStatus(violations, invocable);
        return BaseHttpErrorDetails.of(status, MESSAGE, details);
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return throwable.getClass().isAssignableFrom(JerseyViolationException.class);
    }
}
