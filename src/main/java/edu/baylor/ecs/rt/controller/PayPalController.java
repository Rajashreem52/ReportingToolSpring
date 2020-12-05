package edu.baylor.ecs.rt.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import edu.baylor.ecs.rt.Service.PayPalService;
import edu.baylor.ecs.rt.Service.PurchaseService;
import edu.baylor.ecs.rt.component.PaypalPaymentIntent;
import edu.baylor.ecs.rt.component.PaypalPaymentMethod;
import edu.baylor.ecs.rt.security.payload.request.PurchaseRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/")
public class PayPalController {


    public static  String PAYPAL_SUCCESS_URL = "pay/success";
    public static final  String PAYPAL_CANCEL_URL = "pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PayPalService paypalService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @GetMapping("/pay/{price}")
    public String pay(HttpServletRequest request,@PathVariable BigInteger price){
        String cancelUrl = "http://localhost:2020/api/test/" + PAYPAL_CANCEL_URL;
        
       
        String successUrl = "http://localhost:3000/license";
        try {
            Payment payment = paypalService.createPayment(
                    price.doubleValue(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_CANCEL_URL)
    public String cancelPay(){
        return "cancel";
    }

    @GetMapping("/pay/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
            	
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }
}
