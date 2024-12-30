package com.chicha.carshop.data.controllers;

import com.chicha.carshop.data.DTO.DealDTO;
import com.chicha.carshop.data.enities.Client;
import com.chicha.carshop.data.enities.Deal;
import com.chicha.carshop.data.enities.Good;
import com.chicha.carshop.data.enities.Payment;
import com.chicha.carshop.data.services.ClientService;
import com.chicha.carshop.data.services.DealService;
import com.chicha.carshop.data.services.GoodService;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class CarshopController {
    @Autowired
    GoodService goodService;
    @Autowired
    private DealService dealService;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;


    @GetMapping("/goods")
    public ResponseEntity<List<Good>> getFilteredGoods(
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) Integer colorId,
            @RequestParam(required = false) Integer countryId,
            @RequestParam(required = false) Integer availabilityId,
            @RequestParam(required = false) Integer manufacturerId,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) Integer modelYear,
            @RequestParam(required = false) Float engineVolumeMin,
            @RequestParam(required = false) Float engineVolumeMax,
            @RequestParam(required = false) String engineName,
            @RequestParam(required = false) Integer bodyTypeId,
            @RequestParam(required = false) Integer enginePlacementId,
            @RequestParam(required = false) Integer doorsCount,
            @RequestParam(required = false) Integer placesCount
    ) {
        List<Good> goods = goodService.getFilteredGoods(
                priceMin, priceMax, colorId, countryId, availabilityId, manufacturerId,
                modelName, modelYear, engineVolumeMin, engineVolumeMax, engineName,
                bodyTypeId, enginePlacementId, doorsCount, placesCount
        );
        return ResponseEntity.ok(goods);
    }


    @PostMapping("/authed/buy:{id}")
    public ResponseEntity<DealDTO> buyGood(@PathVariable Integer id, @RequestBody DealDTO dealDTO) {
        Deal deal = goodService.buy(id, dealDTO.isDelivery(), dealDTO.getPayment());
        dealDTO.setGood(deal.getGood());
        dealDTO.setDate(deal.getDate());
        dealDTO.setStatus(deal.getStatus());
        return new ResponseEntity<>(dealDTO, HttpStatus.OK);
    }

    @PostMapping("/authed/cancel:{id}")
    public ResponseEntity<Deal> cancelGood(@PathVariable Integer id) {
        dealService.cancelDeal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getgeneratedpass:{email}")
    public ResponseEntity<String> getGeneratedPass(@PathVariable String email) {
        String pass = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gupal2004@gmail.com");
        message.setTo(email);
        message.setSubject("Generated Pass");
        message.setText("Your generated password: " + pass);
        mailSender.send(message);
        Client client = clientService.findByEmail(email);
        client.setGeneratedPassword(passwordEncoder.encode(pass));
        clientService.save(client);
        return new ResponseEntity<>("email sent to " + email, HttpStatus.OK);
    }

    @GetMapping("/paymentmethods")
    public ResponseEntity<List<Payment>> getPayment(){
        return new ResponseEntity<>(dealService.getPayments(), HttpStatus.OK);
    }

    @GetMapping("/authed/me")
    public ResponseEntity<Client> getMe(){
        return new ResponseEntity<>(clientService.getMe(), HttpStatus.OK);
    }

    @GetMapping("/authed/deals")
    public ResponseEntity<List<Deal>> getDeals(){
        return new ResponseEntity<>(clientService.getMyDeals(), HttpStatus.OK);
    }

    @PutMapping("/authed/client")
    public ResponseEntity<Client> updateClient(@RequestBody Client client){
        return new ResponseEntity<>(clientService.update(client), HttpStatus.OK);
    }

    @PutMapping("/authed/client/password")
    public ResponseEntity<String> updatePassword(@RequestBody Password password){
        clientService.updatePass(password.oldPassword, password.newPassword);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public static class Password{
        String oldPassword;
        String newPassword;
        public Password(String oldPassword, String newPassword) {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }
    }
}

