package com.nordeus.jobfair.auctionservice.auctionservice.bootstrap;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.NotificationService;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.FootballManagerRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class BootstrapData implements CommandLineRunner {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private FootballManagerRepository footballManagerRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private NotificationService notificationService;

    @Override
    public void run(String... args) throws Exception {
        var newAuctionsThread = new CreateNewAuctionsThread(playerRepository, auctionRepository, notificationService);
        var finishAuctionsThread = new FinishAuctionsThread(auctionRepository, footballManagerRepository, notificationService);

        taskScheduler.schedule(newAuctionsThread, Instant.now());
        taskScheduler.schedule(finishAuctionsThread, Instant.now().plus(Duration.ofMinutes(1)));
    }
}
