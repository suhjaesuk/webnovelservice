package com.numble.webnovelservice.episode.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.episode.dto.request.EpisodeRegisterRequest;
import com.numble.webnovelservice.episode.dto.request.EpisodeUpdateRequest;
import com.numble.webnovelservice.episode.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/novels/{novelId}/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    public ResponseEntity<ResponseMessage<Void>> registerEpisode(@PathVariable Long novelId, @RequestBody EpisodeRegisterRequest request){

        episodeService.registerEpisode(novelId, request);
        return new ResponseEntity<>(new ResponseMessage<>("에피소드 등록 성공",null), HttpStatus.CREATED);
    }

    @PutMapping("/{episodeId}")
    public ResponseEntity<ResponseMessage<Void>> updateEpisode(@PathVariable Long novelId, @PathVariable Long episodeId, @RequestBody EpisodeUpdateRequest request){

        episodeService.updateEpisode(novelId, episodeId, request);
        return new ResponseEntity<>(new ResponseMessage<>("에피소드 수정 성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/{episodeId}")
    public ResponseEntity<ResponseMessage<Void>> deleteEpisode(@PathVariable Long novelId, @PathVariable Long episodeId){

        episodeService.deleteEpisode(novelId, episodeId);
        return new ResponseEntity<>(new ResponseMessage<>("에피소드 삭제 성공",null), HttpStatus.OK);
    }
}
