package com.numble.webnovelservice.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class SliceInfoResponse {

    private int size;
    private boolean isFirst;
    private boolean isLast;
    private boolean isEmpty;
    private boolean hasNext;
    private boolean hasPrevious;
    private int numberOfElements;

    @Builder
    public SliceInfoResponse(int size, boolean isFirst, boolean isLast, boolean isEmpty, boolean hasNext, boolean hasPrevious, int numberOfElements) {
        this.size = size;
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.isEmpty = isEmpty;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.numberOfElements = numberOfElements;
    }


    public static SliceInfoResponse toResponse(Slice<?> slice){

        return SliceInfoResponse.builder()
                .size(slice.getSize())
                .isFirst(slice.isFirst())
                .isLast(slice.isLast())
                .isEmpty(slice.isEmpty())
                .hasNext(slice.hasNext())
                .hasPrevious(slice.hasPrevious())
                .numberOfElements(slice.getNumberOfElements())
                .build();
    }
}
