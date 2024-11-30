package com.doemmakara.base;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record BaseError<T>(Boolean status, Integer code, String message, LocalDateTime timestamp, T errors) {
}
