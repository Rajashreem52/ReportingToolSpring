package edu.baylor.ecs.rt.controller;

import edu.baylor.ecs.rt.Service.PurchaseService;

import edu.baylor.ecs.rt.model.License;
import edu.baylor.ecs.rt.model.Sale;
import edu.baylor.ecs.rt.repository.SaleRepository;
import edu.baylor.ecs.rt.security.payload.request.PurchaseRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;
    
    @Autowired
    SaleRepository saleRepository;

    @PostMapping("/purchase")
    @ResponseBody
    public void purchase( @RequestBody PurchaseRequest license) {
    	System.out.println(license.getUsername()+license.getTypel());
        purchaseService.purchase(license);
    }
    
    @PostMapping("/accept/{saleId}")
    @ResponseBody
    public void accept(@PathVariable(value = "saleId") String saleId) throws IOException, MessagingException{
        //set the sale being active
        long id = Long.parseLong(saleId);
        Sale sale = saleRepository.findById(id).get();
        sale.setActive(true);
        saleRepository.save(sale);
        //send a confirmation email to user
        purchaseService.sendMail(sale);
    }
}