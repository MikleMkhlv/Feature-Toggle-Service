package ru.mkvhlv.featuretoggleservice.rule;

import lombok.Getter;

@Getter
public enum RoleType {
    ROLE(1),
    PERCENTAGE(2);

    private final int priority;

    RoleType(int priority) {
        this.priority = priority;
    }



}
