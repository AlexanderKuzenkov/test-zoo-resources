package com.kuzenkov.zooresources.exception;

import java.util.function.Supplier;

public class AppNotFoundException extends AppException {

    public AppNotFoundException(String msg) {
        super(msg);
    }

    public AppNotFoundException() {}

    public static Supplier<AppNotFoundException> noEntity() {
        return () -> new AppNotFoundException("Запрашиваемый объект не найден в базе данных.");
    }

    public static Supplier<AppNotFoundException> error(String msg) {
        return () -> new AppNotFoundException(msg);
    }
}
