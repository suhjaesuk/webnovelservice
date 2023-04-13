package com.numble.webnovelservice.episode.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.episode.dto.response.OwnedEpisodeInfoResponseList;
import com.numble.webnovelservice.episode.dto.response.OwnedEpisodeReadResponse;
import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.episode.entity.OwnedEpisode;
import com.numble.webnovelservice.episode.repository.EpisodeRepository;
import com.numble.webnovelservice.episode.repository.OwnedEpisodeRepository;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.member.repository.MemberRepository;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.transaction.entity.TicketTransaction;
import com.numble.webnovelservice.transaction.repository.TicketTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.numble.webnovelservice.common.exception.ErrorCode.INSUFFICIENT_TICKET;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_EPISODE;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_OWNED_EPISODE;
import static com.numble.webnovelservice.common.exception.ErrorCode.PAGE_OUT_OF_BOUND;

@Service
@RequiredArgsConstructor
public class OwnedEpisodeService {

    private final OwnedEpisodeRepository ownedEpisodeRepository;
    private final EpisodeRepository episodeRepository;
    private final MemberRepository memberRepository;
    private final TicketTransactionRepository ticketTransactionRepository;

    @Transactional
    public void purchaseEpisode(Member currentMember, Long episodeId) {

        Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_MEMBER)
        );

        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_EPISODE)
        );

        int availableTickets = member.getTicketCount();
        int requiredTickets = episode.getNeededTicketCount();

        throwIfInsufficientTicket(availableTickets, requiredTickets);

        member.consumeTicket(requiredTickets);

        OwnedEpisode ownedEpisode = OwnedEpisode.createOwnedEpisode(member, episode);

        ownedEpisodeRepository.save(ownedEpisode);

        TicketTransaction ticketTransaction = TicketTransaction.createConsumeTicketTransaction(member, requiredTickets);

        ticketTransactionRepository.save(ticketTransaction);
    }

    private static void throwIfInsufficientTicket(int availableTickets, int requiredTickets) {

        if(availableTickets < requiredTickets){
             throw new WebNovelServiceException(INSUFFICIENT_TICKET);
        }
    }

    @Transactional
    public OwnedEpisodeReadResponse readOwnedEpisode(Member currentMember, Long episodeId) {

        OwnedEpisode ownedEpisode = ownedEpisodeRepository.findByMemberIdAndEpisodeId(currentMember.getId(), episodeId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_OWNED_EPISODE)
        );

        Episode episode = ownedEpisode.getEpisode();
        Novel novel = episode.getNovel();

        ownedEpisode.markAsRead();

        episode.incrementViewCount();

        novel.incrementTotalViewCount();

        return OwnedEpisodeReadResponse.toResponse(ownedEpisode);
    }

    @Transactional
    public void readOwnedEpisodeNextPage(Member currentMember, Long episodeId) {

        OwnedEpisode ownedEpisode = ownedEpisodeRepository.findByMemberIdAndEpisodeId(currentMember.getId(), episodeId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_OWNED_EPISODE)
        );

        Episode episode = ownedEpisode.getEpisode();

        int totalPage = episode.getTotalPageCount();
        int currentReadingPage = ownedEpisode.getCurrentReadingPage();

        throwIfInvalidPageNumber(currentReadingPage, totalPage);

        ownedEpisode.readNextPage();
    }

    private void throwIfInvalidPageNumber(int currentReadingPage, int totalPage) {
        
        if(currentReadingPage >= totalPage){
            throw new WebNovelServiceException(PAGE_OUT_OF_BOUND);
        }
    }

    @Transactional
    public void readOwnedEpisodePreviousPage(Member currentMember, Long episodeId) {

        OwnedEpisode ownedEpisode = ownedEpisodeRepository.findByMemberIdAndEpisodeId(currentMember.getId(), episodeId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_OWNED_EPISODE)
        );

        int currentReadingPage = ownedEpisode.getCurrentReadingPage();

        throwIfInvalidPageNumber(currentReadingPage);

        ownedEpisode.readPreviousPage();
    }

    private void throwIfInvalidPageNumber(int currentReadingPage) {

        if(currentReadingPage <= 1){
            throw new WebNovelServiceException(PAGE_OUT_OF_BOUND);
        }
    }

    @Transactional(readOnly = true)
    public OwnedEpisodeInfoResponseList retrieveOwnedEpisodesByMember(Member currentMember) {

        List<OwnedEpisode> ownedEpisodes =  ownedEpisodeRepository.findByMemberId(currentMember.getId());

        return OwnedEpisodeInfoResponseList.toResponse(ownedEpisodes);
    }
}
