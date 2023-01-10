package ru.spiso4nik.accountservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spiso4nik.accountservice.dto.request.AccountDtoRequest;
import ru.spiso4nik.accountservice.dto.response.AccountDtoResponse;
import ru.spiso4nik.accountservice.services.AccountService;

@RestController
@RequestMapping("/api/v1/account/")
public class AccountController {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    public AccountController(AccountService accountService,
                             ModelMapper modelMapper) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createNewAccount(@RequestBody AccountDtoRequest accountDto){
        accountService.addNewAccount(accountDto);
    }

    @GetMapping("{email}")
    public AccountDtoResponse getAccount(@PathVariable String email){
        return modelMapper.map(accountService.getAccountByEmail(email), AccountDtoResponse.class);
    }
}
