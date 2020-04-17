package com.example.webmvc;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@EnableScheduling
@Service
public class PushService {
    private final ConcurrentHashMap<String, UserChannel> map = new ConcurrentHashMap<>();

    public void addChannel(String uid, UserChannel channel) {
        map.put(uid, channel);
    }

    public void removeChannel(String uid) {
        map.remove(uid);
    }

    public void send(String uid, String msg) {
        UserChannel channel = map.get(uid);

        if (channel != null) {
            try {
                channel.emitter.send(msg, MediaType.APPLICATION_JSON_UTF8);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
