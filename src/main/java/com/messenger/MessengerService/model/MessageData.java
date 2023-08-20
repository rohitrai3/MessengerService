package com.messenger.MessengerService.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class MessageData {

    @NonNull
    private String sender;
    @NonNull
    private String receiver;
    @NonNull
    private String message;
    @NonNull
    private Long timestamp;
}
