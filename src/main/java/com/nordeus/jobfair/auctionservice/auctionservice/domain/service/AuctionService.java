package com.nordeus.jobfair.auctionservice.auctionservice.domain.service;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.AuctionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionService {

    Page<AuctionDto> getAllActiveAuctions(Pageable pageable);

    AuctionDto getAuction(Long id);

    void join(Long id);

    void bid(Long id);
}
