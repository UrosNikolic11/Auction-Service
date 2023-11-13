package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.enums.Positions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "player")
@Getter
@Setter
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
    private Positions position;

    @Column(name = "defense_rating", nullable = false)
    private Integer defenseRating;

    @Column(name = "attack_rating", nullable = false)
    private Integer attackRating;

    @Column(name = "physical_and_mental_rating", nullable = false)
    private Integer physicalAndMentalRating;

    @OneToMany(mappedBy = "player")
    private List<Auction> auctions = new ArrayList<>(0);

    public Player(String firstName, String lastName, Positions position, Integer defenseRating, Integer attackRating, Integer physicalAndMentalRating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.defenseRating = defenseRating;
        this.attackRating = attackRating;
        this.physicalAndMentalRating = physicalAndMentalRating;
    }
}
