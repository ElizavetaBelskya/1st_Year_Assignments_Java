package ru.kpfu.itis.belskaya.expandableArray;

public class CapacityOverflowException extends RuntimeException {
    public CapacityOverflowException() {
    }

    public CapacityOverflowException(String message) {
        super(message);
    }

    public CapacityOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public CapacityOverflowException(Throwable cause) {
        super(cause);
    }

    public CapacityOverflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
