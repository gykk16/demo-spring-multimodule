package io.glory.coreweb.filter.trace;

import lombok.RequiredArgsConstructor;

import io.glory.mcore.util.idgenerator.TraceIdGenerator;

@RequiredArgsConstructor
public class TraceIdGeneratorTraceFilter extends TraceKeyFilter {

    private final TraceIdGenerator traceIdGenerator;

    @Override
    protected String generateTraceKey() {
        return String.valueOf(traceIdGenerator.generate());
    }

}
