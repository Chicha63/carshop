package com.chicha.carshop.data.services;

import com.chicha.carshop.data.DTO.PasswordDTO;
import com.chicha.carshop.data.enities.Client;
import com.chicha.carshop.data.enities.Deal;
import com.chicha.carshop.data.enities.Employee;
import com.chicha.carshop.data.repos.ClientRepository;
import com.chicha.carshop.data.repos.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClientService implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DealRepository dealRepository;

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client findById(Integer id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client update(Client client) {
        Client authed = findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        if (!Objects.equals(authed.getId(), client.getId())) {
            throw new BadCredentialsException("User mismatch");
        }
        if (client.getPassword() != null) {
            authed.setPassword(passwordEncoder.encode(client.getPassword()));
        }
        if (client.getEmail() != null) {
            authed.setEmail(client.getEmail());
        }
        if (client.getPhone() != null) {
            authed.setPhone(client.getPhone());
        }
        if (client.getAdress() != null) {
            authed.setAdress(client.getAdress());
        }
        if (client.getFullName() != null) {
            authed.setFullName(client.getFullName());
        }
        if (client.getPassportNumber() != null) {
            authed.setPassportNumber(client.getPassportNumber());
        }
        if (client.getPassportSeries() != null) {
            authed.setPassportSeries(client.getPassportSeries());
        }
        save(authed);
        return authed;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username);
        if (client == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(client.getEmail(),client.getPassword(), authorities);
    }

    public void changePass(PasswordDTO passwordDTO) {
        Client client = clientRepository.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (passwordEncoder.matches(passwordDTO.getOldPassword(), client.getPassword())) {
            client.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        } else {
            throw new BadCredentialsException("Wrong old password");
        }
    }

    public Client getMe() {
        return clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public List<Deal> getMyDeals() {
        return dealRepository.findByClient(clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    public void updatePass(String oldPassword, String newPassword) {
        Client authed = findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!passwordEncoder.matches(oldPassword, authed.getPassword()) && !passwordEncoder.matches(oldPassword, authed.getGeneratedPassword())) {
            throw new BadCredentialsException("Wrong old password");
        }
        authed.setPassword(passwordEncoder.encode(newPassword));
    }
}
