package com.koreait.validators;

public interface RequiredValidator {

    default void requiredCk(String str, RuntimeException e) {
        if (str == null || str.isBlank()) {
            throw e;
        }
    }
}
