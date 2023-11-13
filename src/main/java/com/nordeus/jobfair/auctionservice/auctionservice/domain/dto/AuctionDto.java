package com.nordeus.jobfair.auctionservice.auctionservice.domain.dto;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.enums.Positions;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AuctionDto(Long id, String highestBidder, Integer bid, LocalDateTime expirationDate,
                         String playerFirstName, String playerLastName, Positions position,
                         Integer attackRating, Integer defenceRating,
                         Integer physicalAndMentalRating) {
}
