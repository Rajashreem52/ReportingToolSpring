package edu.baylor.ecs.rt.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class PaypalConfig {

    //@Value("${paypal.client.app}")
    private String clientId = "AXGSku5orDq91CH4xrva1DlEmpWxUhY-z9BVtxhmIyQ0UdiTB4KHa8b89azkniuPIPP2hY6nuSGTaEx2";
    //@Value("${paypal.client.secret}")
    private String clientSecret = "EDuA1RYdZKqUIppFoNVsb3XXSq6LeXpARrApbVqqlD6GKt9FU0R5O4UBuz4AUuTRWwVr-2dqtLUOh9dT";
   // @Value("${paypal.mode}")
    private String mode="sandbox";

    @Bean
    public Map<String, String> paypalSdkConfig(){
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mode);
        return sdkConfig;
    }

    @Bean
    public OAuthTokenCredential authTokenCredential(){
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException{
        APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig());
        return apiContext;
    }
}