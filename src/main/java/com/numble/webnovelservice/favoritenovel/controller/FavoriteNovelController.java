package com.numble.webnovelservice.favoritenovel.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.favoritenovel.dto.response.FavoriteNovelInfoResponseList;
import com.numble.webnovelservice.favoritenovel.service.FavoriteNovelService;
import com.numble.webnovelservice.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class FavoriteNovelController {

    private final FavoriteNovelService favoriteNovelService;

    @PostMapping("/novels/{novelId}/favorite-novels")
    public ResponseEntity<ResponseMessage<Void>> registerFavoriteNovel(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                       @PathVariable Long novelId){

        favoriteNovelService.registerFavoriteNovel(userDetails.getMember(), novelId);
        return new ResponseEntity<>(new ResponseMessage<>("선호 작품 등록 성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/novels/{novelId}/favorite-novels")
    public ResponseEntity<ResponseMessage<Void>> removeFavoriteNovel(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                       @PathVariable Long novelId){

        favoriteNovelService.removeFavoriteNovel(userDetails.getMember(), novelId);
        return new ResponseEntity<>(new ResponseMessage<>("선호 작품 삭제 성공",null), HttpStatus.OK);
    }

    @GetMapping("/favorite-novels")
    public ResponseEntity<ResponseMessage<FavoriteNovelInfoResponseList>> retrieveFavoriteNovelsByMember(@AuthenticationPrincipal UserDetailsImpl userDetails){

        FavoriteNovelInfoResponseList response = favoriteNovelService.retrieveFavoriteNovelsByMember(userDetails.getMember());
        return new ResponseEntity<>(new ResponseMessage<>("선호 작품 조회 성공",response), HttpStatus.OK);
    }
}
