package ru.spiso4nik.accountservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.spiso4nik.accountservice.dto.request.GoodsDtoRequest;
import ru.spiso4nik.accountservice.models.orderList.GlobalSpisokModel;
import ru.spiso4nik.accountservice.models.orderList.GoodsModel;
import ru.spiso4nik.accountservice.services.GlobalSpisokService;
import ru.spiso4nik.accountservice.services.GoodsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/goods/")
public class GoodsController {
    private final GlobalSpisokService globalSpisokService;
    private final GoodsService goodsService;
    private final ModelMapper modelMapper;

    public GoodsController(GlobalSpisokService globalSpisokService,
                           GoodsService goodsService,
                           ModelMapper modelMapper) {
        this.globalSpisokService = globalSpisokService;
        this.goodsService = goodsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{idSpisok}")
    public List<GoodsModel> getGoodsFromCurrentGlobalSpisok(@PathVariable Long idSpisok) {
        var goodsList = goodsService.allGoodsFromCurrentShopList(idSpisok);
        var nameSpisok = globalSpisokService.getCurrentSpisok(idSpisok).getNameOfShopList();
        return goodsList;
    }

    @PostMapping("/{idSpisok}")
    public void addNewGood(@RequestBody GoodsDtoRequest goodsDtoRequest,
                           @PathVariable Long idSpisok){
        GlobalSpisokModel currentSpisok = globalSpisokService.getCurrentSpisok(idSpisok);
        GoodsModel goodsModel = modelMapper.map(goodsDtoRequest, GoodsModel.class);

        currentSpisok.getGoodsModels().add(goodsModel);
        goodsModel.setGlobalSpisokModel(currentSpisok);


    }
}
