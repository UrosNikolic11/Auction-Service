package com.nordeus.jobfair.auctionservice.auctionservice.domain.service.impl;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.AuctionDto;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.AuctionFinishedMessage;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.BidPlacedMessage;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.NewAuctionsMessage;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private SimpMessageSendingOperations simpMessagingTemplate;

    @Override
    public void notifyNewAuctions(List<Auction> auctions) {
        var newAuctions = new ArrayList<AuctionDto>();
        auctions.forEach(auction -> {
            var newAuction = AuctionDto.builder()
                    .id(auction.getId())
                    .bid(auction.getBid())
                    .physicalAndMentalRating(auction.getPlayer().getPhysicalAndMentalRating())
                    .defenceRating(auction.getPlayer().getDefenseRating())
                    .attackRating(auction.getPlayer().getAttackRating())
                    .playerFirstName(auction.getPlayer().getFirstName())
                    .playerLastName(auction.getPlayer().getLastName())
                    .highestBidder(auction.getHighestBidder())
                    .position(auction.getPlayer().getPosition())
                    .build();
            newAuctions.add(newAuction);
        });

        var message = NewAuctionsMessage.builder()
                .auctions(newAuctions)
                .message("New auctions available")
                .build();

        simpMessagingTemplate.convertAndSend("/topic/messages-newAuctionsMessage", message);
    }

    @Override
    public void notifyBidPlaced(Auction auction) {
        var message = BidPlacedMessage.builder()
                .auction(AuctionDto.builder()
                        .id(auction.getId())
                        .bid(auction.getBid())
                        .physicalAndMentalRating(auction.getPlayer().getPhysicalAndMentalRating())
                        .defenceRating(auction.getPlayer().getDefenseRating())
                        .attackRating(auction.getPlayer().getAttackRating())
                        .playerFirstName(auction.getPlayer().getFirstName())
                        .playerLastName(auction.getPlayer().getLastName())
                        .highestBidder(auction.getHighestBidder())
                        .position(auction.getPlayer().getPosition())
                        .build())
                .message(auction.getHighestBidder() + " placed bid on auction with id: " + auction.getId())
                .build();

        simpMessagingTemplate.convertAndSend("/topic/messages-bidPlacedMessage", message);
    }

    @Override
    public void notifyEndOfAuction(Auction finishedAuction) {
        var message = AuctionFinishedMessage.builder()
                .auction(AuctionDto.builder()
                        .id(finishedAuction.getId())
                        .bid(finishedAuction.getBid())
                        .physicalAndMentalRating(finishedAuction.getPlayer().getPhysicalAndMentalRating())
                        .defenceRating(finishedAuction.getPlayer().getDefenseRating())
                        .attackRating(finishedAuction.getPlayer().getAttackRating())
                        .playerFirstName(finishedAuction.getPlayer().getFirstName())
                        .playerLastName(finishedAuction.getPlayer().getLastName())
                        .highestBidder(finishedAuction.getHighestBidder())
                        .position(finishedAuction.getPlayer().getPosition())
                        .build())
                .message("Auction with id: " + finishedAuction.getId() + "is finished, the winner is: " + finishedAuction.getHighestBidder())
                .build();

        simpMessagingTemplate.convertAndSend("/topic/messages-auctionFinishedMessage", message);
    }
}
