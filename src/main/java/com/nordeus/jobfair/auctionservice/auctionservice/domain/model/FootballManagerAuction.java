package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "football_manager_auction")
@Getter
@Setter
@NoArgsConstructor
public class FootballManagerAuction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "football_manager_id", nullable = false)
    private FootballManager footballManager;

    public FootballManagerAuction(Auction auction, FootballManager footballManager) {
        this.auction = auction;
        this.footballManager = footballManager;
    }
}
