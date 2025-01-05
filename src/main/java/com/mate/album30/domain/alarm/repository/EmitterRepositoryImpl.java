package com.mate.album30.domain.alarm.repository;



import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//package com.mate.album30.domain.alarm.repository;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
//@Slf4j
//@Repository
//@RequiredArgsConstructor
//public class EmitterRepositoryImpl {
//
//    // 유저ID를 키로 SseEmitter를 안전하게 저장할 수 있도록 ConcurrentHashMap 사용
//    private ConcurrentMap<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();
//
//    // SseEmitter 저장
//    public SseEmitter save(Long userId, SseEmitter sseEmitter) {
//        emitterMap.put(getKey(userId), sseEmitter);
//        log.info("Saved SseEmitter for userId: {}", userId);
//        return sseEmitter;
//    }
//
//    // SseEmitter 조회
//    public SseEmitter get(Long userId) {
//        log.info("Got SseEmitter for userId: {}", userId);
//        return emitterMap.get(getKey(userId));
//    }
//
//    // SseEmitter 삭제
//    public void delete(Long userId) {
//        emitterMap.remove(getKey(userId));
//        log.info("Deleted SseEmitter for userId: {}", userId);
//    }
//
//    // userId를 이용해 key 생성
//    private String getKey(Long userId) {
//        return "Emitter:UID:" + userId;
//    }
//}
@Repository
public class EmitterRepositoryImpl {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();


    public void save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId,sseEmitter);
    }


    public void saveEventCache(String emitterId, Object event) {
        eventCache.put(emitterId,event);
    }
    public ConcurrentHashMap<String, SseEmitter> findAllEmittersByMemberId(String memberId) {
        ConcurrentHashMap<String, SseEmitter> result = new ConcurrentHashMap<>();
        emitters.forEach((key, emitter) -> {
            if (key.startsWith(memberId)) {
                result.put(key, emitter);
            }
        });
        return result;
    }

    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }


}