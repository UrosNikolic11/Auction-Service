package com.nordeus.jobfair.auctionservice.auctionservice.domain.mapper;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.CreateFootballManagerDto;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.FootballManagerDto;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.FootballManager;
import org.springframework.stereotype.Component;

@Component
public class FootballManagerMapper {

    public FootballManager mapFromDto(CreateFootballManagerDto createFootballManagerDto) {
        var footballManager = new FootballManager();
        footballManager.setUsername(createFootballManagerDto.username());
        footballManager.setEmail(createFootballManagerDto.email());
        footballManager.setPassword(createFootballManagerDto.password());
        return footballManager;
    }

    public FootballManagerDto mapToDto(FootballManager footballManager) {
        return new FootballManagerDto(footballManager.getUsername(), footballManager.getEmail(), footballManager.getTokens());
    }
}
