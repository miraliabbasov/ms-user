package com.app.msuser.client;

import com.app.msuser.model.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "payment-client" , url = "${client.payment.url}" )
public interface PaymentClient {

    @PostMapping("/increase-balance")
    UserDto increaseBalanceOfUser(@RequestParam String cardNumber,@RequestParam BigDecimal amount,@RequestParam String apiKey);

}
