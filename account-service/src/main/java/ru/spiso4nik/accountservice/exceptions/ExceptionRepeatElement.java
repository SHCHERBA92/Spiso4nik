package ru.spiso4nik.accountservice.exceptions;

public class ExceptionRepeatElement extends RuntimeException{
    public ExceptionRepeatElement() {
    }

    public ExceptionRepeatElement(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
