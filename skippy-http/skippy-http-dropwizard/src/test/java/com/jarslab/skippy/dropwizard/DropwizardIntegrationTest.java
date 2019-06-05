package com.jarslab.skippy.dropwizard;

import static com.jarslab.skippy.dropwizard.ResponseAssert.assertThat;
import static com.jarslab.skippy.dropwizard.ResponseAssert.readErrorDetails;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;
import io.dropwizard.jersey.optional.EmptyOptionalException;
import io.dropwizard.testing.junit.ResourceTestRule;
import java.util.concurrent.atomic.AtomicReference;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

public class DropwizardIntegrationTest
{
    private static final AtomicReference<Exception> EXCEPTION = new AtomicReference<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @ClassRule
    public static final ResourceTestRule LEGACY = ResourceTestRule.builder()
        .setMapper(OBJECT_MAPPER)
        .setRegisterDefaultExceptionMappers(true)
        .addResource(new SkippyTestResource(EXCEPTION))
        .build();
    @ClassRule
    public static final ResourceTestRule SKIPPY = ResourceTestRule.builder()
        .setMapper(OBJECT_MAPPER)
        .setRegisterDefaultExceptionMappers(false)
        .addProvider(new DropwizardExceptionMapper(SkippyTestResource.class))
        .addResource(new SkippyTestResource(EXCEPTION))
        .build();

    @After
    public void tearDown()
    {
        EXCEPTION.set(null);
    }

    @Test
    public void shouldReturnInternalServerErrorOnNullPointerException()
    {
        //given
        EXCEPTION.set(new NullPointerException());
        //when
        final Response skippyResponse = runFailingRequest(SKIPPY);
        final Response legacyResponse = runFailingRequest(LEGACY);
        //then
        assertThat(skippyResponse).isStatusCodeEqualTo(HttpCodes.INTERNAL_SERVER_ERROR_500);
        assertThat(skippyResponse).isStatusCodeSame(legacyResponse);
        final HttpErrorDetails skippyDetails = readErrorDetails(skippyResponse);
        final HttpErrorDetails legacyDetails = readErrorDetails(legacyResponse);
        assertThat(skippyDetails.getCode()).isEqualTo(legacyDetails.getCode());
        assertThat(skippyDetails.getDetails()).isEqualTo(legacyDetails.getDetails());
        assertThat(skippyDetails.getMessage())
            .matches(
                "There was an error processing your request. It has been logged \\(ID [0-9a-f]{16}\\).");
    }

    @Test
    public void shouldReturnNotFoundForNonExistingResource()
    {
        //given
        //when
        final Response skippyResponse = SKIPPY.target("/not-found").request().get();
        final Response legacyResponse = LEGACY.target("/not-found").request().get();
        //then
        assertThat(skippyResponse).isStatusCodeEqualTo(HttpCodes.NOT_FOUND_404);
        assertThat(skippyResponse).isStatusCodeSame(legacyResponse);
        assertThat(skippyResponse).isDetailsSame(legacyResponse);
    }

    @Test
    public void shouldReturnNotFoundForEmptyOptionalException()
    {
        //given
        EXCEPTION.set(EmptyOptionalException.INSTANCE);
        //when
        final Response skippyResponse = runFailingRequest(SKIPPY);
        final Response legacyResponse = runFailingRequest(LEGACY);
        //then
        assertThat(skippyResponse).isStatusCodeEqualTo(HttpCodes.NOT_FOUND_404);
        assertThat(skippyResponse).isStatusCodeSame(legacyResponse);
    }

    @Test
    public void shouldReturnBadRequestForJsonProcessing()
    {
        //given
        EXCEPTION.set(new JsonMappingException(null, ""));
        //when
        final Response skippyResponse = runFailingRequest(SKIPPY);
        final Response legacyResponse = runFailingRequest(LEGACY);
        //then
        assertThat(skippyResponse).isStatusCodeEqualTo(HttpCodes.BAD_REQUEST_400);
        assertThat(skippyResponse).isStatusCodeSame(legacyResponse);
        assertThat(skippyResponse).isDetailsSame(legacyResponse);
    }

    @Test
    public void shouldProperlyMapWebApplicationException()
    {
        //given
        EXCEPTION.set(new WebApplicationException("This Means War", HttpCodes.CONFLICT_409));
        //when
        final Response skippyResponse = runFailingRequest(SKIPPY);
        final Response legacyResponse = runFailingRequest(LEGACY);
        //then
        assertThat(skippyResponse).isStatusCodeEqualTo(HttpCodes.CONFLICT_409);
        assertThat(skippyResponse).isStatusCodeSame(legacyResponse);
        assertThat(skippyResponse).isDetailsSame(legacyResponse);
    }

    @Test
    public void shouldMapIllegalStateToBadRequest()
    {
        //given
        //when
        final Response response = SKIPPY.target("/skippy/illegal-state").request().get();
        //then
        assertThat(response).isStatusCodeEqualTo(HttpCodes.BAD_REQUEST_400);
    }

    @Test
    public void shouldMapIllegalArgumentToBadRequest()
    {
        //given
        //when
        final Response response = SKIPPY.target("/skippy/illegal-argument").request().get();
        //then
        assertThat(response).isStatusCodeEqualTo(HttpCodes.BAD_REQUEST_400);
    }


    @Test
    public void shouldMapCustomSkippyTestException()
    {
        //given
        //when
        final Response response = SKIPPY.target("/skippy/custom").request().get();
        //then
        assertThat(response).isStatusCodeEqualTo(HttpCodes.INTERNAL_SERVER_ERROR_500);
        assertThat(response).isDetailsEqualTo(
            BaseHttpErrorDetails.of(HttpCodes.INTERNAL_SERVER_ERROR_500, "skippy"));
    }

    private Response runFailingRequest(final ResourceTestRule resourceTestRule)
    {
        return resourceTestRule.target("/skippy/fail").request().get();
    }
}
