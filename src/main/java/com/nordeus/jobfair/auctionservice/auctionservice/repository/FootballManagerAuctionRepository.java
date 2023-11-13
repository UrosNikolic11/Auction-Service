package com.nordeus.jobfair.auctionservice.auctionservice.repository;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.FootballManagerAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballManagerAuctionRepository extends JpaRepository<FootballManagerAuction, Long> {
}
