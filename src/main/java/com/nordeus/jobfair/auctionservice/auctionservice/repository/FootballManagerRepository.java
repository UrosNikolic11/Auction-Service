package com.nordeus.jobfair.auctionservice.auctionservice.repository;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.FootballManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FootballManagerRepository extends JpaRepository<FootballManager, Long> {
    Optional<FootballManager> findByUsername(String username);
}
