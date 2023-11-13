package com.nordeus.jobfair.auctionservice.auctionservice.domain.service.impl;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.CreateFootballManagerDto;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.dto.FootballManagerDto;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.mapper.FootballManagerMapper;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.FootballManager;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.FootballManagerService;
import com.nordeus.jobfair.auctionservice.auctionservice.exceptions.BadRequestException;
import com.nordeus.jobfair.auctionservice.auctionservice.repository.FootballManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FootballManagerServiceImpl implements FootballManagerService {

    @Autowired
    private FootballManagerMapper footballManagerMapper;

    @Autowired
    private FootballManagerRepository footballManagerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FootballManager footballManager = footballManagerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(footballManager.getUsername(), footballManager.getPassword(), authorities);
    }

    @Override
    public FootballManagerDto register(CreateFootballManagerDto createFootballManagerDto) {
        var check = footballManagerRepository.findByUsername(createFootballManagerDto.username());
        if (check.isPresent()) {
            throw new BadRequestException("Username in use");
        }

        var footballManager = footballManagerMapper.mapFromDto(createFootballManagerDto);
        try {
            String hashPwd = passwordEncoder.encode(footballManager.getPassword());
            footballManager.setPassword(hashPwd);
        } catch (Exception ex) {
            throw new BadRequestException("Error with password hashing!");
        }

        footballManagerRepository.save(footballManager);
        return footballManagerMapper.mapToDto(footballManager);
    }
}
