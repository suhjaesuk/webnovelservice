package com.numble.webnovelservice.util.redis.service;

import com.numble.webnovelservice.episode.entity.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     *
     * @param title(Key) 소설의 제목
     * @param payment(Value) 결제방식(무료, 유료)
     *
     * Key (제목) 가 같아도 Value (결제 방식) 값이 다를 경우 다른 값입니다.
     *
     * 현재 시간을 가져와서 자정까지 남은 시간을 계산합니다.
     * 데이터를 Redis에 저장하고 만료 시간을 설정합니다.
     */
    public void increaseDailyView(String title, PaymentType payment) {
        redisTemplate.opsForHash().increment(title, payment.getName(), 1);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();
        Duration duration = Duration.between(now, midnight);

        redisTemplate.expire(title, duration);
    }

    /**
     * 일일 조회 베스트는 최대 10개 저장된다.
     * @return List<String> titles = ["제일 많이 조회된 소설제목", "두번째로 많이 조회된 소설제목", ...., "열 번째로 많이 조회된 소설제목"]
     */
    public List<String> getDailyBestsNovelTitleForAllPaymentType() {

        Set<String> titles = Optional.ofNullable(redisTemplate.keys("*")).orElse(Collections.emptySet());
        Map<String, Double> scores = new HashMap<>();

        for (String title : titles) {

            if (redisTemplate.type(title) != DataType.HASH) {
                continue;
            }

            Map<Object, Object> views = redisTemplate.opsForHash().entries(title);
            double totalViews = views.values().stream()
                    .mapToDouble(value -> Double.parseDouble(value.toString()))
                    .sum();
            scores.put(title, totalViews);
        }

        List<String> dailyBestsForAllPaymentType = scores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return dailyBestsForAllPaymentType;
    }



    public List<String> getDailyBestsNovelTitleForFree() {

        Set<ZSetOperations.TypedTuple<String>> dailyBestsForPaid = redisTemplate.opsForZSet().reverseRangeWithScores("views:" + PaymentType.PAID.getName(), 0, 9);

        if (dailyBestsForPaid == null) return Collections.emptyList();

        return dailyBestsForPaid.stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());
    }

    public List<String> getDailyBestsNovelTitleForPaid() {

        Set<ZSetOperations.TypedTuple<String>> dailyBestsForFree = redisTemplate.opsForZSet().reverseRangeWithScores("views:" + PaymentType.FREE.getName(), 0, 9);

        if (dailyBestsForFree == null) return Collections.emptyList();

        return dailyBestsForFree.stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());
    }
}

