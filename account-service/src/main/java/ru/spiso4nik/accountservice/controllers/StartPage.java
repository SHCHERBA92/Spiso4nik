package ru.spiso4nik.accountservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spiso4nik.accountservice.dto.GlobalSpisokDto;
import ru.spiso4nik.accountservice.models.account.AccountModel;
import ru.spiso4nik.accountservice.models.orderList.GlobalSpisokModel;
import ru.spiso4nik.accountservice.services.AccountService;
import ru.spiso4nik.accountservice.services.GlobalSpisokService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/start-page/")
public class StartPage {

    private final GlobalSpisokService globalSpisokService;
    private final AccountService accountService;

    public StartPage(GlobalSpisokService globalSpisokService,
                     AccountService accountService) {
        this.globalSpisokService = globalSpisokService;
        this.accountService = accountService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GlobalSpisokModel> getAllListCurrentAccount(@RequestBody String email) {
        AccountModel accountModel = accountService.getAccountByEmail(email);
        var allShopSpisok = globalSpisokService.getAllShopSpisokByCurrentAccount(accountModel);
        return allShopSpisok;
    }

    @PostMapping("create-new-list")
    public ResponseEntity createNewList(@RequestBody GlobalSpisokDto globalSpisokDto) {
        AccountModel accountModel = accountService.getAccountByEmail(globalSpisokDto.getEmailCurrentAccount());
        globalSpisokService.addNewSpisok(globalSpisokDto.getNameOfShopList(),
                globalSpisokDto.getStoreName(),
                globalSpisokDto.getDateTo(),
                accountModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("created list " + globalSpisokDto.getNameOfShopList());
    }

    @DeleteMapping("delete-current-list/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteCurrentList(@PathVariable("id") Long id) {
        globalSpisokService.deleteCurrentSpisok(id);
    }
}
