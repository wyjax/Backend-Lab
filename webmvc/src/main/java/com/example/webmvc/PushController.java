package com.example.webmvc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
public class PushController {
    private final PushService pushService;

    @GetMapping(value = "/push/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter test(@PathVariable("id") String uid) throws InterruptedException {
        SseEmitter emitter = new SseEmitter();
        UserChannel channel = new UserChannel(uid, emitter);
        final String USER_ID = uid;

        try {
            pushService.addChannel(uid, channel);
        }
        catch (Exception e) {
            throw new InterruptedException(e.getMessage());
        }
        emitter.onCompletion(() -> {
            pushService.removeChannel(USER_ID);
        });
        emitter.onTimeout(() -> {
            emitter.complete();
            pushService.removeChannel(USER_ID);
        });

        System.out.println(uid + " 연결");
        return emitter;
    }

    @PostMapping("/push/{id}")
    public void good(@PathVariable("id") String uid,
                     @RequestBody String message) {
        String msg = "안녕하세요,";
        System.out.println("send : uid : " + uid + ", message : " + message);
        pushService.send(uid, msg);
    }

//    @Scheduled(fixedRate = 3000L)
//    public void scheduless() {
//    }
}
