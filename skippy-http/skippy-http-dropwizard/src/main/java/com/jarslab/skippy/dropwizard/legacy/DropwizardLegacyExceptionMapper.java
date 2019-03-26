package com.jarslab.skippy.dropwizard.legacy;

import com.google.common.collect.ImmutableList;
import com.jarslab.skippy.spi.CombinedExceptionMapper;
import com.jarslab.skippy.spi.ExceptionMapper;

import java.util.List;

public class DropwizardLegacyExceptionMapper implements CombinedExceptionMapper
{
    @Override
    public List<ExceptionMapper> getMappers()
    {
        return ImmutableList.of(new EarlyEofExceptionMapper(),
                new EmptyOptionalExceptionMapper(),
                new IllegalFormParamContentTypeExceptionMapper(),
                new JerseyViolationExceptionMapper(),
                new JsonProcessingExceptionMapper(true), // TODO
                new WebApplicationExceptionMapper());
    }
}
