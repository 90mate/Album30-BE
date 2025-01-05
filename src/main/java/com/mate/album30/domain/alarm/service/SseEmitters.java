package com.mate.album30.domain.alarm.service;

import com.mate.album30.domain.alarm.entity.Notification;
import com.mate.album30.domain.alarm.repository.EmitterRepositoryImpl;
import com.mate.album30.domain.alarm.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class SseEmitters {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final EmitterRepositoryImpl emitterRepository;
    private final NotificationRepository notificationRepository;
    private static final long DEFAULT_TIMEOUT = 60 * 1000L;


    /**
     * 클라이언트와 SSE 연결 설정
     *
     * @param memberId 클라이언트의 회원 ID
     * @return 생성된 SseEmitter
     */


    public SseEmitter connect(Long memberId) {
        String emitterId = createEmitterId(memberId);
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // Emitter 저장 및 관리
        emitterRepository.save(emitterId, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        emitter.onError((e) -> {
            log.error("Error for memberId: {}", memberId, e);
            emitterRepository.deleteById(emitterId);
        });

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("Connected to notification service!"));
        } catch (IOException e) {
            log.error("Failed to send initial connection message", e);
            emitterRepository.deleteById(emitterId);
        }

        return emitter;
    }

    /**
     * 특정 사용자에게 알림 전송
     *
     * @param memberId   사용자 ID
     * @param eventName  이벤트 이름
     * @param notification 알림 데이터
     */
    public void sendNotification(Long memberId, String eventName, Notification notification) {
        String memberIdStr = String.valueOf(memberId);
        ConcurrentHashMap<String, SseEmitter> emitters = emitterRepository.findAllEmittersByMemberId(memberIdStr);

        emitters.forEach((emitterId, emitter) -> {
            try {
                emitterRepository.saveEventCache(emitterId, notification);
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(notification));
            } catch (IOException e) {
                log.error("Failed to send notification to memberId: {}", memberId, e);
                emitterRepository.deleteById(emitterId);
            }
        });
    }

    /**
     * Emitter ID 생성
     *
     * @param memberId 사용자 ID
     * @return Emitter ID
     */
    private String createEmitterId(Long memberId) {
        return memberId + "_" + System.currentTimeMillis();
    }
}