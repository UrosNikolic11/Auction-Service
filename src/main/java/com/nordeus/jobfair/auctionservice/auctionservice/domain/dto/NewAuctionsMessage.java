package com.nordeus.jobfair.auctionservice.auctionservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewAuctionsMessage {

    private List<AuctionDto> auctions;

    private String message;
}
