package org.skipta.skipta.http;

import org.skipta.skipta.api.ErrorDetails;

public interface HttpErrorDetails extends ErrorDetails
{
    int getStatusCode();

    String getMessage();

    default String getDetails()
    {
        return null;
    }
}
