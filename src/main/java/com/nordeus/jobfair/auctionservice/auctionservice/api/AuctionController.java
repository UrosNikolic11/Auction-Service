package com.nordeus.jobfair.auctionservice.auctionservice.api;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.AuctionFinishedMessage;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.BidPlacedMessage;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.NewAuctionsMessage;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/active")
    public ResponseEntity<?>  getAllActive(Pageable pageable) {
        return ResponseEntity.ok(auctionService.getAllActiveAuctions(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  getAuctionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(auctionService.getAuction(id));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<?> joinAuction(@PathVariable("id") Long id) {
        auctionService.join(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/bid")
    public ResponseEntity<?> bidOnAuction(@PathVariable("id") Long id) {
        auctionService.bid(id);
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/send-newAuctionsMessage")
    @SendTo("/topic/messages-newAuctionsMessage")
    public NewAuctionsMessage handleNewAuctionsMessage(@Payload NewAuctionsMessage newAuctionsMessage, StompHeaderAccessor stompHeaderAccessor) {
        return newAuctionsMessage;
    }

    @MessageMapping("/send-bidPlacedMessage")
    @SendTo("/topic/messages-bidPlacedMessage")
    public BidPlacedMessage handleBidPlacedMessage(@Payload BidPlacedMessage bidPlacedMessage, StompHeaderAccessor stompHeaderAccessor) {
        return bidPlacedMessage;
    }

    @MessageMapping("/send-auctionFinishedMessage")
    @SendTo("/topic/messages-auctionFinishedMessage")
    public AuctionFinishedMessage handleAuctionFinishedMessage(@Payload AuctionFinishedMessage auctionFinishedMessage, StompHeaderAccessor stompHeaderAccessor) {
        return auctionFinishedMessage;
    }
}
