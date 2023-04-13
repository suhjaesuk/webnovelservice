package com.numble.webnovelservice.homeexposure.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.homeexposure.dto.response.HomeExposureInfoResponseList;
import com.numble.webnovelservice.homeexposure.service.HomeExposureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeExposureController {

    private final HomeExposureService homeExposureService;

    @PostMapping("/novels/{novelId}/home-exposures")
    public ResponseEntity<ResponseMessage<Void>> registerHomeExposure(@PathVariable Long novelId){

        homeExposureService.registerHomeExposure(novelId);
        return new ResponseEntity<>(new ResponseMessage<>("홈 노출 등록 성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/novels/{novelId}/home-exposures")
    public ResponseEntity<ResponseMessage<Void>> removeHomeExposure(@PathVariable Long novelId){

        homeExposureService.removeHomeExposure(novelId);
        return new ResponseEntity<>(new ResponseMessage<>("홈 노출 삭제 성공",null), HttpStatus.OK);
    }

    @GetMapping("/home-exposures")
    public ResponseEntity<ResponseMessage<HomeExposureInfoResponseList>> retrieveAllHomeExposures(){

        HomeExposureInfoResponseList response = homeExposureService.retrieveAllHomeExposures();
        return new ResponseEntity<>(new ResponseMessage<>("홈 노출 조회 성공", response), HttpStatus.OK);
    }
}
