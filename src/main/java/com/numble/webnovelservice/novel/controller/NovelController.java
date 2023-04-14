package com.numble.webnovelservice.novel.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.novel.dto.request.NovelRegisterRequest;
import com.numble.webnovelservice.novel.dto.request.NovelUpdateInfoRequest;
import com.numble.webnovelservice.novel.dto.response.NovelDetailsResponse;
import com.numble.webnovelservice.novel.dto.response.NovelInfoResponseList;
import com.numble.webnovelservice.novel.service.NovelService;
import com.numble.webnovelservice.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{novelId}")
    public ResponseEntity<ResponseMessage<Void>> updateNovelInfo(@PathVariable Long novelId, @RequestBody NovelUpdateInfoRequest request){

        novelService.updateNovelInfo(novelId, request);
        return new ResponseEntity<>(new ResponseMessage<>("소설 정보 수정 성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/{novelId}")
    public ResponseEntity<ResponseMessage<Void>> deleteNovel(@PathVariable Long novelId){

        novelService.deleteNovel(novelId);
        return new ResponseEntity<>(new ResponseMessage<>("소설 삭제 성공",null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseMessage<NovelInfoResponseList>> retrieveAllNovels(){

        NovelInfoResponseList response = novelService.retrieveAllNovels();
        return new ResponseEntity<>(new ResponseMessage<>("소설 전체 조회 성공", response), HttpStatus.OK);
    }

    @GetMapping("/{genre}")
    public ResponseEntity<ResponseMessage<NovelInfoResponseList>> retrieveNovelsByGenre(@PathVariable String genre){

        NovelInfoResponseList response = novelService.retrieveNovelsByGenre(genre);
        return new ResponseEntity<>(new ResponseMessage<>("장르별 소설 조회 성공", response), HttpStatus.OK);
    }

    @GetMapping("/search/{titleOrAuthor}")
    public ResponseEntity<ResponseMessage<NovelInfoResponseList>> retrieveNovelsBySearch(@PathVariable String titleOrAuthor){

        NovelInfoResponseList response = novelService.retrieveNovelsBySearch(titleOrAuthor);
        return new ResponseEntity<>(new ResponseMessage<>("소설 검색 성공", response), HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<ResponseMessage<NovelInfoResponseList>> retrieveLatestUpdateNovels(){

        NovelInfoResponseList response = novelService.retrieveLatestUpdateNovels();
        return new ResponseEntity<>(new ResponseMessage<>("최신 업데이트 소설 조회 성공", response), HttpStatus.OK);
    }

    @GetMapping("/{novelId}/details")
    public ResponseEntity<ResponseMessage<NovelDetailsResponse>> retrieveNovelDetails(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                      @PathVariable Long novelId){

        NovelDetailsResponse response = novelService.retrieveNovelDetails(userDetails.getMember(), novelId);
        return new ResponseEntity<>(new ResponseMessage<>("소설 상세 조회 성공", response), HttpStatus.OK);
    }
}
