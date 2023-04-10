package com.numble.webnovelservice.novel.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.novel.dto.request.NovelRegisterRequest;
import com.numble.webnovelservice.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/novels")
public class NovelController {

    private final NovelService novelService;

    @PostMapping
    public ResponseEntity<ResponseMessage<Void>> registerNovel(@RequestBody NovelRegisterRequest request){
        novelService.registerNovel(request);
        return new ResponseEntity<>(new ResponseMessage<>("소설 등록 성공",null), HttpStatus.CREATED);

    }
}
