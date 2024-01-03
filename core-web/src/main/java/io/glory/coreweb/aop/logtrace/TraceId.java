package io.glory.coreweb.aop.logtrace;

import java.util.UUID;

import io.glory.coreweb.WebAppConst;

public class TraceId {

    private static final int    FIRST_LEVEL = 1;
    private final        String id;
    private              int    level;


    public TraceId() {
        id = generateId();
        level = FIRST_LEVEL;
    }

    public TraceId setNextLevel() {
        level = level + 1;
        return this;
    }

    public TraceId setPrevLevel() {
        level = level - 1;
        return this;
    }

    public boolean isFirstLevel() {
        return level == FIRST_LEVEL;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    private String generateId() {
        return WebAppConst.getMdcTraceKey().orElseGet(() -> UUID.randomUUID().toString());
    }

}
