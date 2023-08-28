package dev.rohitrai.messengerservice.dao.impl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import dev.rohitrai.messengerservice.dao.MessengerDao;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FirebaseMessengerDao implements MessengerDao {

    public void create(@NonNull String path, @NonNull String key, @NonNull Object value) {
        Map<String, Object> createData = new HashMap<>();
        createData.put(key, value);

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(path);
        ref.updateChildrenAsync(createData);
    }

}
