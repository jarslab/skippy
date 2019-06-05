package com.jarslab.skippy.dropwizard;


import com.jarslab.skippy.http.HttpErrorDetails;
import javax.ws.rs.core.Response;

public interface ResponseErrorDetails extends HttpErrorDetails
{
    Response getResponse();

    @Override
    default int getCode()
    {
        return getResponse().getStatus();
    }

    @Override
    default String getMessage()
    {
        return getResponse().getEntity().toString();
    }
}
