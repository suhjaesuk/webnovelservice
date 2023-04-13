package com.numble.webnovelservice.favoritenovel.dto.response;

import com.numble.webnovelservice.favoritenovel.entity.FavoriteNovel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FavoriteNovelInfoResponseList {

    private List<FavoriteNovelInfoResponse> favoriteNovels;

    public FavoriteNovelInfoResponseList(List<FavoriteNovelInfoResponse> favoriteNovels) {

        this.favoriteNovels = favoriteNovels;
    }

    public static FavoriteNovelInfoResponseList toResponse(List<FavoriteNovel> favoriteNovels) {

        List<FavoriteNovelInfoResponse> responseList = favoriteNovels.stream()
                .map(FavoriteNovelInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new FavoriteNovelInfoResponseList(responseList);
    }
}
