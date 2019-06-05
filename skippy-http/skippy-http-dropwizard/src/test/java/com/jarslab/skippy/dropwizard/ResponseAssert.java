package com.jarslab.skippy.dropwizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarslab.skippy.http.HttpErrorDetails;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import javax.ws.rs.core.Response;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

class ResponseAssert extends AbstractAssert<ResponseAssert, Response>
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ResponseAssert(final Response response)
    {
        super(response, ResponseAssert.class);
    }

    static ResponseAssert assertThat(final Response response)
    {
        return new ResponseAssert(response);
    }

    static HttpErrorDetails readErrorDetails(final Response response)
    {
        Assertions.assertThat(response.hasEntity()).isTrue();
        final ByteArrayInputStream entity = (ByteArrayInputStream) response.getEntity();
        try {
            return OBJECT_MAPPER.readValue(entity, BaseHttpErrorDetails.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    ResponseAssert isStatusCodeEqualTo(final int statusCode)
    {
        Assertions.assertThat(actual.getStatus()).isEqualTo(statusCode);
        return this;
    }

    ResponseAssert isDetailsEqualTo(final HttpErrorDetails httpErrorDetails)
    {
        final HttpErrorDetails actualHttpErrorDetails = readErrorDetails(actual);
        Assertions.assertThat(actualHttpErrorDetails.getCode()).isEqualTo(httpErrorDetails.getCode());
        Assertions.assertThat(actualHttpErrorDetails.getMessage()).isEqualTo(httpErrorDetails.getMessage());
        Assertions.assertThat(actualHttpErrorDetails.getDetails()).isEqualTo(httpErrorDetails.getDetails());
        return this;
    }


    ResponseAssert isStatusCodeSame(final Response response)
    {
        isStatusCodeEqualTo(response.getStatus());
        return this;
    }

    ResponseAssert isDetailsSame(final Response response)
    {
        isDetailsEqualTo(readErrorDetails(response));
        return this;
    }
}
