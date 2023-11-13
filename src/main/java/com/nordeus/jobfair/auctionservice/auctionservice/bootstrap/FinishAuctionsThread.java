package com.nordeus.jobfair.auctionservice.auctionservice.bootstrap;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.NotificationService;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.FootballManagerRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class FinishAuctionsThread implements Runnable{

    private final AuctionRepository auctionRepository;

    private final FootballManagerRepository footballManagerRepository;

    private final NotificationService notificationService;

    @Override
    public void run() {
        while (true) {
            disableAuctions();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void disableAuctions() {
        var auctions = auctionRepository.getActive();
        auctions.forEach(auction -> {
            if (auction.getExpirationDate().isBefore(LocalDateTime.now())) {
                var footballManager = footballManagerRepository.findByUsername(auction.getHighestBidder());
                footballManager.ifPresent(fm -> {
                    footballManager.get().setTokens(footballManager.get().getTokens() - auction.getBid());
                    footballManagerRepository.save(footballManager.get());
                });
                auction.setActive(false);
                auctionRepository.save(auction);
                notificationService.notifyEndOfAuction(auction);
            }
        });
    }
}
