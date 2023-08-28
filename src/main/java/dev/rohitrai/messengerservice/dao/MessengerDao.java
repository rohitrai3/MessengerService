package dev.rohitrai.messengerservice.dao;

import lombok.NonNull;

public interface MessengerDao {

    void create(@NonNull String path, @NonNull String key, @NonNull Object value);
    String create(@NonNull String path, @NonNull Object value);
    Object read(@NonNull String path);
    void delete(@NonNull String path);

}
