package org.skipta.skipta.dropwizard.legacy;

import com.google.common.collect.ImmutableList;
import org.skipta.skipta.core.CombinedExceptionMapper;
import org.skipta.skipta.core.ExceptionMapper;

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
                new JsonProcessingExceptionMapper(true),
                new WebApplicationExceptionMapper()); // TODO
    }
}
