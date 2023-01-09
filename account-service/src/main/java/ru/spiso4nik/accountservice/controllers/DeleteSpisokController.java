package ru.spiso4nik.accountservice.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spiso4nik.accountservice.services.GlobalSpisokService;
import ru.spiso4nik.accountservice.services.ServiceGoods;

@RestController
@RequestMapping("delete")
public class DeleteSpisokController {

    private final GlobalSpisokService spisokService;
    private final ServiceGoods serviceGoods;

    public DeleteSpisokController(GlobalSpisokService spisokService, ServiceGoods serviceGoods) {
        this.spisokService = spisokService;
        this.serviceGoods = serviceGoods;
    }


    @GetMapping("/{id}/check")
    public String checkDeleteSpisok(@PathVariable Long id, Model model) {
        var allGoodsCurrentSpisok = serviceGoods.allGoodsFromCurrentShopList(id);
        model.addAttribute("allGoods", allGoodsCurrentSpisok);
        model.addAttribute("currentSpisok", spisokService.getCurrentSpisok(id));
        return "check_delete_spisok";
    }

    @PostMapping("/{id}/check")
    public String deleteSpisok(@PathVariable Long id, Model model) {
        spisokService.deleteCurrentSpisok(id);
        return "redirect:/startPage";
    }

}
