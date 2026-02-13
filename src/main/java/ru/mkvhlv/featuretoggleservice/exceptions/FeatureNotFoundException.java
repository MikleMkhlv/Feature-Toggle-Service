package ru.mkvhlv.featuretoggleservice.exceptions;

public class FeatureNotFoundException extends RuntimeException{
    public FeatureNotFoundException(String message) {
        super(message);
    }
}
