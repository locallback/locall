package org.locallback.common.exception;

public class LocallException extends RuntimeException {

    public LocallException() {
        super("Locall framework RuntimeException.");
    }

    public LocallException(String message) {
        super(message);
    }

    public LocallException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocallException(Throwable cause) {
        super(cause);
    }
}