package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "auction")
@Getter
@Setter
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active", columnDefinition = "bool default true")
    private Boolean active = true;

    @Column(name = "highest_bidder")
    private String highestBidder;

    @Column(name = "bid", columnDefinition = "int4 default 1")
    private Integer bid = 1;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @OneToMany(mappedBy = "auction")
    private List<FootballManagerAuction> footballManagerAuctions;

    public Auction(LocalDateTime expirationDate, Player player) {
        this.expirationDate = expirationDate;
        this.player = player;
    }
}
