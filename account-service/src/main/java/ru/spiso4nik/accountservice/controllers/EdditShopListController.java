package ru.spiso4nik.accountservice.controllers;

import com.example.shopinglist.parserApi.ParsingFromSearch;
import com.example.shopinglist.parserApi.ProductPars;
import com.example.shopinglist.services.GlobalSpisokService;
import com.example.shopinglist.services.ServiceGoods;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("edditSpisok")
public class EdditShopListController {

    private final ServiceGoods serviceGoods;
    private final ParsingFromSearch parsingFromSearch;
    private final GlobalSpisokService globalSpisokService;



    @GetMapping("/{idSpisok}")
    public String edditSpisok(@PathVariable Long idSpisok, Model model) {
        var goodsList = serviceGoods.allGoodsFromCurrentShopList(idSpisok);
        var nameSpisok = globalSpisokService.getCurrentSpisok(idSpisok).getNameOfShopList();
        model.addAttribute("idSpisok", idSpisok);
        model.addAttribute("nameSpisok", nameSpisok);
        model.addAttribute("allGoods", goodsList);
        model.addAttribute("products", Collections.EMPTY_LIST);
        return "eddit_shopList";
    }

    @PostMapping("/{idSpisok}")
    public String edditSpisokQuary(@PathVariable Long idSpisok,
                                   @RequestParam String productName,
                                   Model model) {
        var goodsList = serviceGoods.allGoodsFromCurrentShopList(idSpisok);
        var nameSpisok = globalSpisokService.getCurrentSpisok(idSpisok).getNameOfShopList();
        model.addAttribute("idSpisok", idSpisok);
        model.addAttribute("nameSpisok", nameSpisok);
        model.addAttribute("allGoods", goodsList);
        var products = (List<ProductPars>)parsingFromSearch.getListProducts(productName);
        model.addAttribute("products", products);

        return "eddit_shopList";
    }

    @PostMapping("/{idSpisok}/newGood")
    public String addNewGood(@PathVariable Long idSpisok, Model model,
                             @RequestParam String nameProduct,
                             @RequestParam String imgProduct,
                             @RequestParam String priceProduct,
                             @RequestParam String countProduct) {
        serviceGoods.createNewGood(nameProduct, imgProduct, BigDecimal.valueOf(Double.valueOf(priceProduct)),Integer.valueOf(countProduct) , idSpisok);
        return "redirect:/edditSpisok/{idSpisok}";
    }

    @PostMapping("/{idSpisok}/deleteGood/{idGoods}")
    public String deleteGood(@PathVariable Long idGoods,
                             @PathVariable Long idSpisok,
                             Model model) {
        var f = serviceGoods.deleteGood(idGoods);
        return "redirect:/edditSpisok/{idSpisok}";
    }
}
