package com.example.webmvc;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class UserChannel {
    String uid;
    SseEmitter emitter;

    public UserChannel(String uid, SseEmitter emitter) {
        this.uid = uid;
        this.emitter = emitter;
    }
}
