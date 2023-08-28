package dev.rohitrai.messengerservice.dao.impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import dev.rohitrai.messengerservice.dao.MessengerDao;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Component
public class FirebaseMessengerDao implements MessengerDao {

    private Object readData;

    public void create(@NonNull String path, @NonNull String key, @NonNull Object value) {
        Map<String, Object> createData = new HashMap<>();
        createData.put(key, value);

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(path);
        ref.updateChildrenAsync(createData);
    }

    public Object read(@NonNull String path) {
        CountDownLatch done = new CountDownLatch(1);
        readData = null;

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(path);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    readData = snapshot.getValue(Object.class);
                }
                done.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
                done.countDown();
            }
        });

        try {
            done.await();
        } catch (InterruptedException e) {
            System.out.println("error: " + e);
        }

        return readData;
    }

}
