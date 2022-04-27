package com.app.msuser.controller;

import com.app.msuser.model.dto.UserDto;
import com.app.msuser.model.request.RegisterRequest;
import com.app.msuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public UserDto userRegister(@RequestBody /*@Valid*/ RegisterRequest dto) {
        return userService.userRegister(dto);
    }

    @GetMapping
    public UserDto getUserAndBalanceByApiKey(@RequestParam String apiKey,@RequestParam BigDecimal amount) {
        return userService.getUserAndBalanceByApiKey(apiKey,amount);
    }

    @GetMapping("/api-key")
    public UserDto getUserByApiKey(@RequestParam String apiKey){
        return userService.getUserByApiKey(apiKey);
    }

    @PostMapping("/add-balance")
    public void addBalance(@RequestParam String cardNumber , @RequestParam BigDecimal amount,@RequestParam String apiKey){
        userService.addBalance(cardNumber, amount, apiKey);
    }

}
