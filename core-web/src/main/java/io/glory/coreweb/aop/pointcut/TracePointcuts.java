package io.glory.coreweb.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class TracePointcuts {

    @Pointcut("@annotation(io.glory.mcore.annotation.ExcludeLogTrace)")
    public void excludeLogTraceAnnotation() {
    }

    @Pointcut("@annotation(io.glory.mcore.annotation.LogTraceInfo)")
    public void logTraceAnnotation() {
    }

    @Pointcut("execution(* io.glory..*Controller.*(..))")
    public void allController() {
    }

    @Pointcut("execution(* io.glory..*Service.*(..))")
    public void allService() {
    }

    @Pointcut("execution(* io.glory..*Repository.*(..))")
    public void allRepository() {
    }

    @Pointcut("(allController() || allService() || allRepository() || logTraceAnnotation()) && !excludeLogTraceAnnotation()")
    public void logTracePointcut() {
    }

}
