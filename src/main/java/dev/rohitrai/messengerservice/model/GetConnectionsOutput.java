package dev.rohitrai.messengerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class GetConnectionsOutput {

    @NonNull
    private List<UserData> userDataList;
}
