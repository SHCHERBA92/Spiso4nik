package ru.spiso4nik.accountservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spiso4nik.accountservice.dto.request.GlobalSpisokDtoRequest;
import ru.spiso4nik.accountservice.models.account.AccountModel;
import ru.spiso4nik.accountservice.models.orderList.GlobalSpisokModel;
import ru.spiso4nik.accountservice.models.orderList.GoodsModel;
import ru.spiso4nik.accountservice.services.AccountService;
import ru.spiso4nik.accountservice.services.GlobalSpisokService;
import ru.spiso4nik.accountservice.services.GoodsService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/list/")
public class GlobalSpisokController {

    private final GlobalSpisokService globalSpisokService;
    private final AccountService accountService;
    private final GoodsService goodsService;

    public GlobalSpisokController(GlobalSpisokService globalSpisokService,
                                  AccountService accountService,
                                  GoodsService goodsService) {
        this.globalSpisokService = globalSpisokService;
        this.accountService = accountService;
        this.goodsService = goodsService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GlobalSpisokModel> getAllListCurrentAccount(@RequestBody String email) {
        AccountModel accountModel = accountService.getAccountByEmail(email);
        var allShopSpisok = globalSpisokService.getAllShopSpisokByCurrentAccount(accountModel);
        return allShopSpisok;
    }

    @PostMapping("create-new-list")
    public ResponseEntity createNewList(@RequestBody GlobalSpisokDtoRequest globalSpisokDto) {
        AccountModel accountModel = accountService.getAccountByEmail(globalSpisokDto.getEmailCurrentAccount());
        globalSpisokService.addNewSpisok(globalSpisokDto.getNameOfShopList(),
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
