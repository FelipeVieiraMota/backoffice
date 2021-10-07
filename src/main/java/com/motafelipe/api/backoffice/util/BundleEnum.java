package com.motafelipe.api.backoffice.util;

public enum BundleEnum {

    STUDENT_NOT_FOUND("student.id.not.found");

    private final String message;

    BundleEnum(String message){
        this.message = message;
    }
}
