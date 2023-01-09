package ru.spiso4nik.accountservice.controllers;

import com.example.shopinglist.auth_model.AuthUserModel;
import com.example.shopinglist.services.AuthUserService;
import com.example.shopinglist.services.GlobalSpisokService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spiso4nik.accountservice.services.AccountService;
import ru.spiso4nik.accountservice.services.GlobalSpisokService;

@Controller
@RequestMapping("startPageToDay")
@AllArgsConstructor
public class ToDaySpisokController {

    private final GlobalSpisokService globalSpisokService;
    private final AccountService accountService;

    @GetMapping
    public String getToDaySpisok(Model model){
        AuthUserModel userModel = accountService.getUserFromContext();

        var allShopSpisok = globalSpisokService.getAllToDaySpisok(userModel);

        model.addAttribute("spisokShop", allShopSpisok);
        model.addAttribute("nickName", userModel.getNickName());

        return "start_page_shopList_current";
    }
}
