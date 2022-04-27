package com.app.msuser.service;

import com.app.msuser.client.PaymentClient;
import com.app.msuser.exception.NotFoundException;
import com.app.msuser.model.dto.UserDto;
import com.app.msuser.model.request.RegisterRequest;
import com.app.msuser.model.map.UserMap;
import com.app.msuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PaymentClient client;
    private final UserMap mapper = UserMap.INSTANCE;

    @Transactional
    public UserDto userRegister(RegisterRequest dto) {
        if (doesExistBy(dto.getEmail()))
            throw new NotFoundException("USER_ALREADY_EXIST", String.format("Email %s already exist", dto.getEmail()));

        var userEntity = mapper.dtoToEntity(dto);

        String key = generateApiKey();
        userEntity.setBalance(BigDecimal.valueOf(0.00));
        userEntity.setApiKey(key);

        return mapper.entityToDto(repository.save(userEntity));
    }

    @Transactional
    public UserDto getUserAndBalanceByApiKey(String apiKey, BigDecimal amount) {
        var entity = repository.findByApiKey(apiKey)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", String.format("User not found with %s", apiKey)));
        entity.setBalance(entity.getBalance().subtract(amount));
        return mapper.entityToDto(repository.save(entity));
    }

    public UserDto getUserByApiKey(String apiKey) {
        var entity = repository.findByApiKey(apiKey)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND.getReasonPhrase(), String.format("User not found with apikey: %s", apiKey)));
        return mapper.entityToDto(entity);

    }

    @Transactional
    public void addBalance(String cardNumber , BigDecimal amount, String apiKey){
       var amountBalance= client.increaseBalanceOfUser(cardNumber,amount,apiKey).getBalance();
       var entity = repository.findByApiKey(apiKey)
               .orElseThrow(() -> new NotFoundException(NOT_FOUND.getReasonPhrase(), String.format("User not found with apikey: %s", apiKey)));
       entity.setBalance(amountBalance);
       repository.save(entity);
    }

    private Boolean doesExistBy(String email){
        return repository.existsByEmail(email);
    }

    private static String generateApiKey() {
        return RandomString.make(6);
    }

}
