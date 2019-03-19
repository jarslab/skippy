package org.skipta.skipta.dropwizard;

import org.skipta.skipta.http.HttpErrorDetails;

import javax.ws.rs.core.Response;

public interface ResponseErrorDetails extends HttpErrorDetails
{
    Response getResponse();

    @Override
    default int getStatusCode()
    {
        return getResponse().getStatus();
    }

    @Override
    default String getMessage()
    {
        return getResponse().getEntity().toString();
    }
}
