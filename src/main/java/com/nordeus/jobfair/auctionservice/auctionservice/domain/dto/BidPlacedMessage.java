package com.nordeus.jobfair.auctionservice.auctionservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BidPlacedMessage {

    private AuctionDto auction;

    private String message;
}
