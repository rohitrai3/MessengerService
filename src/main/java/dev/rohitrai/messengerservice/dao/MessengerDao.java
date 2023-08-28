package dev.rohitrai.messengerservice.dao;

import lombok.NonNull;

public interface MessengerDao {

    void create(@NonNull String path, @NonNull String key, @NonNull Object value);
}
