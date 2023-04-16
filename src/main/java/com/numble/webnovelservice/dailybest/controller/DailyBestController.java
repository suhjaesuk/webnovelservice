package com.numble.webnovelservice.dailybest.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForFreeResponse;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForPaidResponse;
import com.numble.webnovelservice.dailybest.dto.response.DailyBestNovelForAllPaymentTypeResponse;
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

    @GetMapping("/all-types")
    public ResponseEntity<ResponseMessage<DailyBestNovelForAllPaymentTypeResponse>> retrieveDailyBestNovelForAllPaymentType(){

        DailyBestNovelForAllPaymentTypeResponse response = dailyBestService.retrieveDailyBestNovelForAllPaymentType();
        return new ResponseEntity<>(new ResponseMessage<>("전체 일일 베스트 소설 조회 성공",response), HttpStatus.OK);
    }

    @GetMapping("/paid")
    public ResponseEntity<ResponseMessage<DailyBestNovelForPaidResponse>> retrieveDailyBestNovelForPaid(){

        DailyBestNovelForPaidResponse response = dailyBestService.retrieveDailyBestNovelForPaid();
        return new ResponseEntity<>(new ResponseMessage<>("유료 일일 베스트 소설 조회 성공",response), HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<ResponseMessage<DailyBestNovelForFreeResponse>> retrieveDailyBestNovelForFree(){

        DailyBestNovelForFreeResponse response = dailyBestService.retrieveDailyBestNovelForFree();
        return new ResponseEntity<>(new ResponseMessage<>("무료 일일 베스트 소설 조회 성공",response), HttpStatus.OK);
    }
}
