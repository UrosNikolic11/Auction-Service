package com.nordeus.jobfair.auctionservice.auctionservice.domain.service.impl;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.AuctionDto;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.FootballManagerAuction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionService;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.NotificationService;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.BadRequestException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.NotFoundException;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.ServerException;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.FootballManagerAuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.FootballManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private FootballManagerRepository footballManagerRepository;

    @Autowired
    private FootballManagerAuctionRepository footballManagerAuctionRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Page<AuctionDto> getAllActiveAuctions(Pageable pageable) {
        return auctionRepository.getAllActiveAuctions(pageable);
    }

    @Override
    public AuctionDto getAuction(Long id) {
        return auctionRepository.getAuctionByAuctionId(id);
    }

    @Override
    @Transactional
    public void join(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var footballManager = footballManagerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Manager not found"));
        var auction = auctionRepository.findById(id).orElseThrow(() -> new NotFoundException("Auction not found"));
        var join = new FootballManagerAuction(auction, footballManager);
        footballManagerAuctionRepository.save(join);
    }

    @Override
    @Transactional
    public void bid(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var footballManager = footballManagerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Manager not found"));
        try {
            var auction = auctionRepository.findByIdForBid(id).orElseThrow(() -> new NotFoundException("Auction not found"));

            if (!auction.getActive()) {
                throw new BadRequestException("This Auction is over");
            }

            var joined = footballManager.getFootballManagerAuctions();
            var check = joined.stream()
                    .filter(footballManagerAuction -> Objects.equals(footballManagerAuction.getAuction().getId(), auction.getId())).toList();
            if (check.isEmpty()) {
                throw new BadRequestException("You need to join the Auction first");
            }

            var otherActiveAuctions = auctionRepository.getActiveAuctionsForHighestBidder(username, auction.getId());
            var sum = otherActiveAuctions.stream().mapToInt(Auction::getBid).sum();

            if (footballManager.getTokens() - sum - auction.getBid() < 0) {
                throw new BadRequestException("Not enough tokens to bid on this auction");
            }

            var duration = Duration.between(LocalDateTime.now(), auction.getExpirationDate()).getSeconds();
            if (duration <= 5) {
                auction.setExpirationDate(LocalDateTime.now().plusSeconds(5));
            }
            auction.setBid(auction.getBid() + 1);
            auction.setHighestBidder(username);
            auctionRepository.save(auction);
            notificationService.notifyBidPlaced(auction);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException("Something went wrong....");
        }
    }
}
