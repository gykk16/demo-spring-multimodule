package io.glory.coreweb.aop.logtrace;

public record TraceStatus(TraceId traceId, Long startTimeMs, String message) {

}
