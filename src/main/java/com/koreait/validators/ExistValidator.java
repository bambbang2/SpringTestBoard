package com.koreait.validators;

public interface ExistValidator {
    default void existCk(boolean id, RuntimeException e) {
        if (!id) {
            throw e;
        }
    }
}
