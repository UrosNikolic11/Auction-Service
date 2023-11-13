package com.nordeus.jobfair.auctionservice.auctionservice.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateFootballManagerDto(@NotBlank String username, @Email String email, @NotBlank String password) {
}
