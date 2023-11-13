package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "football_manager")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FootballManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "tokens", columnDefinition = "int4 default 100")
    private Integer tokens = 100;

    @OneToMany(mappedBy = "footballManager")
    private List<FootballManagerAuction> footballManagerAuctions;
}
