package com.koreait.validators;

public interface Validator<T> extends RequiredValidator, ExistValidator {

    void check(T t);
}
