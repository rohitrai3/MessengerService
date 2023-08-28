package dev.rohitrai.messengerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class AcceptConnectionRequestInput {

    @NonNull
    private String user;
    @NonNull
    private String connection;
    @NonNull
    private String connectionRequestKey;

}
