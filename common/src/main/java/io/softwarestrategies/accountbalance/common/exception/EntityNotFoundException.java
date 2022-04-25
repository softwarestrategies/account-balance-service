package io.softwarestrategies.accountbalance.common.exception;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
