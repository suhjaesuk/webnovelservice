package com.numble.webnovelservice.dailybest.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForFreeResponseList;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForPaidResponseList;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelResponseList;
import com.numble.webnovelservice.dailybest.service.DailyBestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily-bests")
public class DailyBestController {

    private final DailyBestService dailyBestService;

    @GetMapping
    public ResponseEntity<ResponseMessage<DailyBestNovelResponseList>> retrieveDailyBestNovelForAllPaymentType(){

        DailyBestNovelResponseList response = dailyBestService.retrieveDailyBestNovelForAllPaymentType();
        return new ResponseEntity<>(new ResponseMessage<>("일일 베스트 소설 조회 성공",response), HttpStatus.OK);
    }

    @GetMapping("/paid")
    public ResponseEntity<ResponseMessage<DailyBestNovelForPaidResponseList>> retrieveDailyBestNovelForPaid(){

        DailyBestNovelForPaidResponseList response = dailyBestService.retrieveDailyBestNovelForPaid();
        return new ResponseEntity<>(new ResponseMessage<>("유료 일일 베스트 소설 조회 성공",response), HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<ResponseMessage<DailyBestNovelForFreeResponseList>> retrieveDailyBestNovelForFree(){

        DailyBestNovelForFreeResponseList response = dailyBestService.retrieveDailyBestNovelForFree();
        return new ResponseEntity<>(new ResponseMessage<>("무료 일일 베스트 소설 조회 성공",response), HttpStatus.OK);
    }
}
