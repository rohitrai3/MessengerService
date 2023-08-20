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
public class GetUserOutput {

    @NonNull
    private UserData userData;

}
