package com.nordeus.jobfair.auctionservice.auctionservice.api;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.CreateFootballManagerDto;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.FootballManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/managers")
public class FootballManagerController {

    @Autowired
    private FootballManagerService footballManagerService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody CreateFootballManagerDto createFootballManagerDto) {
        return ResponseEntity.ok(footballManagerService.register(createFootballManagerDto));
    }
}
