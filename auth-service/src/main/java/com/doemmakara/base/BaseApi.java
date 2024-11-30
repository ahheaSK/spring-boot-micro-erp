package com.doemmakara.base;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record BaseApi<T>(Boolean status, Integer code, String message, LocalDateTime timestamp, T dataList) {
}