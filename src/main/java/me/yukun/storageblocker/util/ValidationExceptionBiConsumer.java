package me.yukun.storageblocker.util;

import me.yukun.storageblocker.config.validator.ValidationException;

@FunctionalInterface
public interface ValidationExceptionBiConsumer<T, U> {

  void apply(T t, U u) throws ValidationException;
}
