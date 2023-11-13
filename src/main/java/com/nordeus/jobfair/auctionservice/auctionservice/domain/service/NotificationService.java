package com.nordeus.jobfair.auctionservice.auctionservice.domain.service;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;

import java.util.List;

public interface NotificationService {

    void notifyNewAuctions(List<Auction> auctions);

    void notifyBidPlaced(Auction auction);

    void notifyEndOfAuction(Auction finishedAuction);
}
