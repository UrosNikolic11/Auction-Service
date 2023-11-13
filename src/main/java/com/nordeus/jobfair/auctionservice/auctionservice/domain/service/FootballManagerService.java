package com.nordeus.jobfair.auctionservice.auctionservice.domain.service;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.CreateFootballManagerDto;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.FootballManagerDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface FootballManagerService extends UserDetailsService {

    FootballManagerDto register(CreateFootballManagerDto createFootballManagerDto);
}
