package com.kozikov.crs.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ArithmeticOperations implements EnumClass<String> {

    SUM("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private String id;

    ArithmeticOperations(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ArithmeticOperations fromId(String id) {
        for (ArithmeticOperations at : ArithmeticOperations.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}