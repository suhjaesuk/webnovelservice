package com.numble.webnovelservice.episode.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.episode.dto.response.OwnedEpisodeInfoResponseList;
import com.numble.webnovelservice.episode.dto.response.OwnedEpisodeReadResponse;
import com.numble.webnovelservice.episode.service.OwnedEpisodeService;
import com.numble.webnovelservice.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/episodes")
public class OwnedEpisodeController {

    private final OwnedEpisodeService ownedEpisodeService;

    @PostMapping("/{episodeId}")
    public ResponseEntity<ResponseMessage<Void>> purchaseEpisode(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable Long episodeId){

        ownedEpisodeService.purchaseEpisode(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new ResponseMessage<>("에피소드 구매 성공",null), HttpStatus.CREATED);
    }

    @PutMapping("/{episodeId}")
    public ResponseEntity<ResponseMessage<OwnedEpisodeReadResponse>> readOwnedEpisode(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                      @PathVariable Long episodeId){

        OwnedEpisodeReadResponse response = ownedEpisodeService.readOwnedEpisode(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new ResponseMessage<>("에피소드 열람 성공",response), HttpStatus.OK);
    }

    @PutMapping("/{episodeId}")
    public ResponseEntity<ResponseMessage<Void>> readOwnedEpisodeNextPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                            @PathVariable Long episodeId){

        ownedEpisodeService.readOwnedEpisodeNextPage(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new ResponseMessage<>("에피소드 다음 페이지 읽기 성공", null), HttpStatus.OK);
    }

    @PutMapping("/{episodeId}")
    public ResponseEntity<ResponseMessage<Void>> readOwnedEpisodePreviousPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                          @PathVariable Long episodeId){

        ownedEpisodeService.readOwnedEpisodePreviousPage(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new ResponseMessage<>("에피소드 이전 페이지 읽기 성공", null), HttpStatus.OK);
    }

    @GetMapping("/owned-episode")
    public ResponseEntity<ResponseMessage<OwnedEpisodeInfoResponseList>> retrieveOwnedEpisodesByMember(@AuthenticationPrincipal UserDetailsImpl userDetails){

        OwnedEpisodeInfoResponseList response = ownedEpisodeService.retrieveOwnedEpisodesByMember(userDetails.getMember());
        return new ResponseEntity<>(new ResponseMessage<>("소장 에피소드 조회 성공", response), HttpStatus.OK);
    }
}
