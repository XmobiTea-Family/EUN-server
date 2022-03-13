package org.youngmonkeys.eun.common.entity;

import lombok.var;

public enum EzyTargets {
    All(0),
    Others(1),
    LeaderClient(2),
    OnlyMe(3);

    private final int value;

    EzyTargets(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public final static EzyTargets valueOf(int value) {
        for (var e : values()) {
            if (e.getValue() == value) return e;
        }

        return null;
    }
}