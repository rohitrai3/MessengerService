package dev.rohitrai.messengerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class AddConnectionRequestInput {

    @NonNull
    private @Getter String sender;
    @NonNull
    private @Getter String receiver;

}
