package com.nordeus.jobfair.auctionservice.auctionservice.bootstrap;

import com.github.javafaker.Faker;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.enums.Positions;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Player;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.NotificationService;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.AuctionRepository;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

@RequiredArgsConstructor
public class CreateNewAuctionsThread implements Runnable{

    private final PlayerRepository playerRepository;

    private final AuctionRepository auctionRepository;

    private final NotificationService notificationService;

    private static final int NUM_PLAYERS = 10;
    private final Random random = new Random();
    private final Faker faker = new Faker();


    @Override
    public void run() {
        while (true) {
            generateNewAuctions();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ArrayList<Player> generatePlayers() {
        var players = new ArrayList<Player>();

        for (int i = 0; i < NUM_PLAYERS; i++) {
            var atk = random.nextInt(100);
            var def = random.nextInt(100);
            var pms = random.nextInt(100);
            var firstName = faker.name().firstName();
            var lastName = faker.name().lastName();
            var positions = Positions.values();
            var index = random.nextInt(10);
            var player = new Player(firstName, lastName, positions[index], def, atk, pms);
            players.add(player);
        }

        playerRepository.saveAll(players);
        return players;
    }

    private void generateNewAuctions() {
        var players = generatePlayers();
        var newAuctions = new ArrayList<Auction>();

        players.forEach(player -> {
            var date = LocalDateTime.now();
            var auction = new Auction(date.plusMinutes(1), player);
            auctionRepository.save(auction);
            newAuctions.add(auction);
        });

        notificationService.notifyNewAuctions(newAuctions);
    }
}
