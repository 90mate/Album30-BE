package com.mate.album30.domain.alarm.controller;

import com.mate.album30.domain.alarm.entity.Notification;
import com.mate.album30.domain.alarm.service.SseEmitters;
import com.mate.album30.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class SseController {

    private final SseEmitters emitters;
    /*
    *
    * 역할: 클라이언트와 SSE 연결을 설정.

클라이언트가 이 엔드포인트에 요청하면 서버는 SseEmitter를 생성하고 연결 상태를 유지합니다.
연결을 유지하는 동안 서버는 클라이언트로 데이터를 푸시할 수 있습니다.
    *
    * */



//    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public ResponseEntity<SseEmitter> connect(@RequestParam  Long memberId) {
//        SseEmitter emitter = sseEmitters.connect(memberId);
//        try {
//            emitter.send(SseEmitter.event()
//                    .name("connect : notification")
//                    .data("connected! : notification"));
//        } catch (IOException e) {
//            log.error("Error while sending connection message in notification", e);
//            emitter.completeWithError(e);
//        }
//        return ResponseEntity.ok(emitter);
//    }
//
//    @GetMapping("/sendNotification")
//    public ResponseEntity<Void> sendNotification(@RequestParam Long userId, @RequestParam String eventName) {
//        Notification notification = Notification.builder()
//                .title("notification").build();
//
//        sseEmitters.sendToClient(userId, "eventType", notification);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@RequestParam Long memberId) {
        return ResponseEntity.ok(emitters.connect(memberId));
    }

    @GetMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestParam Long memberId, @RequestParam String eventName) {
        Notification notification = Notification.builder()
                .title("New Notification")
                .build();
        emitters.sendNotification(memberId, eventName, notification);
        return ResponseEntity.ok().build();
    }
}
