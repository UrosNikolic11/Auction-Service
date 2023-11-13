package com.nordeus.jobfair.auctionservice.auctionservice.repository;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.AuctionDto;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query(value = "select new com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.AuctionDto(a.id, a.highestBidder, " +
            "a.bid, a.expirationDate, a.player.firstName, a.player.lastName, a.player.position, a.player.attackRating, a.player.defenseRating, " +
            "a.player.physicalAndMentalRating) " +
            "from Auction a where a.active = true")
    Page<AuctionDto> getAllActiveAuctions(Pageable pageable);

    @Query(value = "select new com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.AuctionDto(a.id, a.highestBidder, " +
            "a.bid, a.expirationDate, a.player.firstName, a.player.lastName, a.player.position, a.player.attackRating, a.player.defenseRating, " +
            "a.player.physicalAndMentalRating) " +
            "from Auction a where a.id = :auctionId")
    AuctionDto getAuctionByAuctionId(@Param("auctionId") Long auctionId);

    @Query(value = "select a from Auction a where a.highestBidder = :bidder and a.active = true and a.id != :biddingAuctionId")
    List<Auction> getActiveAuctionsForHighestBidder(@Param("bidder") String bidder, @Param("biddingAuctionId") Long biddingAuctionId);

    @Query(value = "select a from Auction a where a.active = true")
    List<Auction> getActive();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select a from Auction a where a.id = :id")
    Optional<Auction> findByIdForBid(@Param("id") Long id);
}
