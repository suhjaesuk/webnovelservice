package com.numble.webnovelservice.episode.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.episode.dto.response.OwnedEpisodeInfoResponseList;
import com.numble.webnovelservice.episode.dto.response.OwnedEpisodeReadResponse;
import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.episode.entity.OwnedEpisode;
import com.numble.webnovelservice.episode.entity.PaymentType;
import com.numble.webnovelservice.episode.repository.EpisodeRepository;
import com.numble.webnovelservice.episode.repository.OwnedEpisodeRepository;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.member.repository.MemberRepository;
import com.numble.webnovelservice.novel.entity.Novel;
import com.numble.webnovelservice.transaction.entity.TicketTransaction;
import com.numble.webnovelservice.transaction.repository.TicketTransactionRepository;
import com.numble.webnovelservice.util.redis.repository.DailyBestRedisRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.numble.webnovelservice.common.exception.ErrorCode.DUPLICATE_OWNED_EPISODE;
import static com.numble.webnovelservice.common.exception.ErrorCode.INSUFFICIENT_TICKET;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_AVAILABLE_LOCK;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_EPISODE;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_OWNED_EPISODE;
import static com.numble.webnovelservice.common.exception.ErrorCode.PAGE_OUT_OF_BOUNDS;
import static com.numble.webnovelservice.episode.entity.PaymentType.FREE;
import static com.numble.webnovelservice.episode.entity.PaymentType.PAID;


@Service
@RequiredArgsConstructor
public class OwnedEpisodeService {

    private final OwnedEpisodeRepository ownedEpisodeRepository;
    private final EpisodeRepository episodeRepository;
    private final MemberRepository memberRepository;
    private final TicketTransactionRepository ticketTransactionRepository;
    private final RedissonClient redissonClient;
    private final DailyBestRedisRepository dailyBestRedisRepository;

    @Transactional
    public void purchaseEpisode(Member currentMember, Long episodeId) {

        String lockName = "purchase-episode" + " / " + "username: " + currentMember.getUsername() + " / " + "episodeId: " + episodeId;
        RLock lock = redissonClient.getLock(lockName);

        try {
            boolean isLocked = lock.tryLock(2, 5, TimeUnit.SECONDS);
            if (!isLocked) throw new WebNovelServiceException(NOT_AVAILABLE_LOCK);

            throwIfDuplicateOwnedEpisode(currentMember.getId(), episodeId);

            Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                    () -> new WebNovelServiceException(NOT_FOUND_MEMBER));
            Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                    () -> new WebNovelServiceException(NOT_FOUND_EPISODE));

            int availableTickets = member.getTicketCount();
            int requiredTickets = episode.getNeededTicketCount();

            throwIfInsufficientTicket(availableTickets, requiredTickets);
            member.consumeTicket(requiredTickets);

            OwnedEpisode ownedEpisode = OwnedEpisode.createOwnedEpisode(member, episode);
            TicketTransaction ticketTransaction = TicketTransaction.createConsumeTicketTransaction(member, requiredTickets);

            episode.addOwnedEpisode(ownedEpisode);

            ownedEpisodeRepository.save(ownedEpisode);
            ticketTransactionRepository.save(ticketTransaction);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } finally {
            lock.unlock();
        }
    }

    private void throwIfInsufficientTicket(int availableTickets, int requiredTickets) {

        if(availableTickets < requiredTickets){
             throw new WebNovelServiceException(INSUFFICIENT_TICKET);
        }
    }

    private void throwIfDuplicateOwnedEpisode(Long memberId, Long episodeId) {

        if(ownedEpisodeRepository.existsByMemberIdAndEpisodeId(memberId, episodeId)){
            throw new WebNovelServiceException(DUPLICATE_OWNED_EPISODE);
        }
    }

    @Transactional
    public OwnedEpisodeReadResponse readOwnedEpisode(Member currentMember, Long episodeId) {

        OwnedEpisode ownedEpisode = ownedEpisodeRepository.findByMemberIdAndEpisodeId(currentMember.getId(), episodeId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_OWNED_EPISODE));

        Episode episode = ownedEpisode.getEpisode();
        Novel novel = episode.getNovel();

        ownedEpisode.markAsRead();
        episode.increaseViewCount();
        novel.increaseTotalViewCount();

        PaymentType payment = getEpisodePaymentType(episode.getIsFree());
        dailyBestRedisRepository.increaseDailyView(novel.getTitle(), payment);

        return OwnedEpisodeReadResponse.toResponse(ownedEpisode);
    }

    private PaymentType getEpisodePaymentType(Boolean isFree) {

        PaymentType payment = PAID;
        if(isFree)  payment = FREE;

        return payment;
    }

    @Transactional
    public void readOwnedEpisodeNextPage(Member currentMember, Long episodeId) {

        OwnedEpisode ownedEpisode = ownedEpisodeRepository.findByMemberIdAndEpisodeId(currentMember.getId(), episodeId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_OWNED_EPISODE));

        Episode episode = ownedEpisode.getEpisode();

        int totalPage = episode.getTotalPageCount();
        int currentReadingPage = ownedEpisode.getCurrentReadingPage();

        throwIfInvalidPageNumber(currentReadingPage, totalPage);

        ownedEpisode.readNextPage();
    }

    private void throwIfInvalidPageNumber(int currentReadingPage, int totalPage) {
        
        if(currentReadingPage >= totalPage){
            throw new WebNovelServiceException(PAGE_OUT_OF_BOUNDS);
        }
    }

    @Transactional
    public void readOwnedEpisodePreviousPage(Member currentMember, Long episodeId) {

        OwnedEpisode ownedEpisode = ownedEpisodeRepository.findByMemberIdAndEpisodeId(currentMember.getId(), episodeId).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_OWNED_EPISODE));

        int currentReadingPage = ownedEpisode.getCurrentReadingPage();

        throwIfInvalidPageNumber(currentReadingPage);

        ownedEpisode.readPreviousPage();
    }

    private void throwIfInvalidPageNumber(int currentReadingPage) {

        if(currentReadingPage <= 1){
            throw new WebNovelServiceException(PAGE_OUT_OF_BOUNDS);
        }
    }

    @Transactional(readOnly = true)
    public OwnedEpisodeInfoResponseList retrieveOwnedEpisodesByMember(Member currentMember) {

        List<OwnedEpisode> ownedEpisodes =  ownedEpisodeRepository.findByMemberId(currentMember.getId());

        return OwnedEpisodeInfoResponseList.toResponse(ownedEpisodes);
    }
}
