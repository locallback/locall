package org.locallback.common.exception;

public class MethodNotFindException extends LocallException {

    public MethodNotFindException() {
        super("Method not found.");
    }

    public MethodNotFindException(String message) {
        super(message);
    }

    public MethodNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodNotFindException(Throwable cause) {
        super(cause);
    }
}