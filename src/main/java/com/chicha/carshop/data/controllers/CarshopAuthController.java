package com.chicha.carshop.data.controllers;

import com.chicha.carshop.data.DTO.PasswordDTO;
import com.chicha.carshop.data.enities.Client;
import com.chicha.carshop.data.services.ClientService;
import com.chicha.carshop.security.AuthResponse;
import com.chicha.carshop.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class CarshopAuthController {
    @Autowired
    private final ClientService clientService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody Client client) {
        AuthResponse authResponse = new AuthResponse();
        try{
            Client isExist = clientService.findByEmail(client.getEmail());
            if (isExist != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Client newClient = new Client();

            newClient.setEmail(client.getEmail());
            newClient.setPassword(passwordEncoder.encode(client.getPassword()));
            newClient.setAdress(client.getAdress());
            newClient.setFullName(client.getFullName());
            newClient.setPhone(client.getPhone());
            newClient.setPassportNumber(client.getPassportNumber());
            newClient.setPassportSeries(client.getPassportSeries());
            Client saveClient = clientService.save(newClient);
            Authentication auth = new UsernamePasswordAuthenticationToken(client.getEmail(), client.getPassword());
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = JwtProvider.generateToken(auth);
            authResponse.setJwt(token);
            authResponse.setStatus(true);
            authResponse.setMessage("Success");
        } catch (Exception e){
            authResponse.setStatus(false);
            authResponse.setMessage(e.getMessage());
            authResponse.setJwt(null);
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody Client client) {
        AuthResponse authResponse = new AuthResponse();
        try {
            String username = client.getEmail();
            String password = client.getPassword();
            Client user = clientService.findByEmail(username);
            Authentication authentication;
            if (passwordEncoder.matches(password, user.getGeneratedPassword())) {
                authentication = authenticaticate(username, password);
            } else {
                authentication = authenticaticate(username, password);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = JwtProvider.generateToken(authentication);

            authResponse.setJwt(token);
            authResponse.setStatus(true);
            authResponse.setMessage("Success");
        } catch (Exception e){
            authResponse.setStatus(false);
            authResponse.setMessage(e.getMessage());
            authResponse.setJwt(null);
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/changepass")
    public ResponseEntity<AuthResponse> changePass(@RequestBody PasswordDTO passwordDTO) {
        try {
            clientService.changePass(passwordDTO);
        } catch (Exception e){
            AuthResponse authResponse = new AuthResponse();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private Authentication authenticaticate(String username, String password) {
        UserDetails userDetails = clientService.loadUserByUsername(username);
        Client client = clientService.findByEmail(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword()) && !passwordEncoder.matches(password, client.getGeneratedPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, client.getPassword());
    }


}
