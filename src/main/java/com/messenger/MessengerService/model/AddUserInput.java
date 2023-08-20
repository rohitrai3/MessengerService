package com.messenger.MessengerService.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class AddUserInput {

    @NonNull
    private String uid;
    @NonNull
    private UserData userData;

}
