package dev.rohitrai.messengerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class GetConnectionRequestsOutput {

    @NonNull
    private Map<String, UserData> requestIdToUserData;

}
