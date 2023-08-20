package com.messenger.MessengerService.dao.impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.messenger.MessengerService.dao.MessengerDao;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Component
public class FirebaseMessengerDao implements MessengerDao {

    private List<Object> readDataList;
    private Map<String, Object> readDataMap;
    private Object readData;

    public void create(@NonNull String path, @NonNull String key, @NonNull Object value) {
        Map<String, Object> createData = new HashMap<>();
        createData.put(key, value);

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(path);
        ref.updateChildrenAsync(createData);
    }

    public String create(@NonNull String path, @NonNull Object value) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(path);

        DatabaseReference newRef = ref.push();
        newRef.setValueAsync(value);

        return newRef.getKey();
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

    public List<Object> readList(@NonNull String path) {
        CountDownLatch done = new CountDownLatch(1);
        readDataList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(path);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    GenericTypeIndicator<Map<String, Object>> UsernameMap = new GenericTypeIndicator<>() { };
                    readDataList.addAll(snapshot.getValue(UsernameMap).values());
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

        return readDataList;
    }

    public Map<String, Object> readMap(@NonNull String path) {
        CountDownLatch done = new CountDownLatch(1);
        readDataMap = new HashMap<>();

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(path);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    GenericTypeIndicator<Map<String, Object>> UsernameMap = new GenericTypeIndicator<>() { };
                    readDataMap = snapshot.getValue(UsernameMap);
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

        return readDataMap;
    }

    public void delete(@NonNull String path) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(path);
        ref.setValueAsync(null);
    }

}
