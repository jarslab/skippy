package com.jarslab.skippy.dropwizard;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jarslab.skippy.http.HttpErrorDetails;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseHttpErrorDetails implements HttpErrorDetails
{
    private final int code;
    private final String message;
    private final String details;

    public static HttpErrorDetails of(final int statusCode, final String message)
    {
        return new BaseHttpErrorDetails(statusCode, message, null);
    }

    public static HttpErrorDetails of(final int statusCode,
                                      final String message,
                                      final String details)
    {
        return new BaseHttpErrorDetails(statusCode, message, details);
    }

    @JsonCreator
    private BaseHttpErrorDetails(@JsonProperty("code") final int code,
                                 @JsonProperty("message") final String message,
                                 @JsonProperty("details") final String details)
    {
        this.code = code;
        this.message = requireNonNull(message);
        this.details = details;
    }

    @Override
    public int getCode()
    {
        return code;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    @Override
    public String getDetails()
    {
        return details;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        final BaseHttpErrorDetails that = (BaseHttpErrorDetails) o;
        return code == that.code &&
            Objects.equals(message, that.message) &&
            Objects.equals(details, that.details);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(code, message, details);
    }

    @Override
    public String toString()
    {
        return "BaseHttpErrorDetails{" +
            "code=" + code +
            ", message='" + message + '\'' +
            ", details='" + details + '\'' +
            '}';
    }
}
