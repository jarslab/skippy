package com.jarslab.skippy.dropwizard;

import com.google.common.collect.ImmutableList;
import com.jarslab.skippy.dropwizard.legacy.EarlyEofExceptionMapper;
import com.jarslab.skippy.dropwizard.legacy.EmptyOptionalExceptionMapper;
import com.jarslab.skippy.dropwizard.legacy.IllegalFormParamContentTypeExceptionMapper;
import com.jarslab.skippy.dropwizard.legacy.JerseyViolationExceptionMapper;
import com.jarslab.skippy.dropwizard.legacy.JsonProcessingExceptionMapper;
import com.jarslab.skippy.dropwizard.legacy.WebApplicationExceptionMapper;
import com.jarslab.skippy.spi.CombinedExceptionMapper;
import com.jarslab.skippy.spi.ExceptionMapper;
import java.util.List;

/**
 * {@link io.dropwizard.setup.ExceptionMapperBinder}
 */
class DropwizardLegacyExceptionMapper implements CombinedExceptionMapper
{
    private final boolean showDetails;

    DropwizardLegacyExceptionMapper()
    {
        showDetails = false;
    }

    DropwizardLegacyExceptionMapper(final boolean showDetails)
    {
        this.showDetails = showDetails;
    }

    @Override
    public List<ExceptionMapper> getMappers()
    {
        return ImmutableList.of(new EarlyEofExceptionMapper(),
            new EmptyOptionalExceptionMapper(),
            new IllegalFormParamContentTypeExceptionMapper(),
            new JerseyViolationExceptionMapper(),
            new JsonProcessingExceptionMapper(showDetails),
            new WebApplicationExceptionMapper());
    }
}
