package ru.spiso4nik.accountservice.services;

import org.springframework.stereotype.Service;
import ru.spiso4nik.accountservice.exceptions.ExceptionNotElements;
import ru.spiso4nik.accountservice.models.orderList.GlobalSpisokModel;
import ru.spiso4nik.accountservice.models.orderList.GoodsModel;
import ru.spiso4nik.accountservice.models.orderList.RoleOfStatus;
import ru.spiso4nik.accountservice.repository.GlobalSpisokRepository;
import ru.spiso4nik.accountservice.repository.GoodsRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServiceGoods {
    private final GoodsRepository goodsRepository;
    private final GlobalSpisokRepository globalSpisokRepository;

    private final String NOT_FIND_ELEMENT = "Элемента нет в базе данных";

    public ServiceGoods(GoodsRepository goodsRepository, GlobalSpisokRepository globalSpisokRepository) {
        this.goodsRepository = goodsRepository;
        this.globalSpisokRepository = globalSpisokRepository;
    }

    public void createNewGood(GoodsModel model) {
        goodsRepository.saveAndFlush(model);
    }

    public void createNewGood(String name, GlobalSpisokModel globalSpisokModel) {
        GoodsModel model = new GoodsModel();
        model.setName(name);
        model.setRoleOfStatus(RoleOfStatus.READY_BUY);
        model.setGlobalSpisokModel(globalSpisokModel);
        goodsRepository.saveAndFlush(model);
    }

    public void createNewGood(String name, Long id) {
        GoodsModel model = new GoodsModel();
        var currentGlobalSpisok = globalSpisokRepository.findById(id).orElseThrow(() -> new ExceptionNotElements(NOT_FIND_ELEMENT));
        model.setName(name);
        model.setRoleOfStatus(RoleOfStatus.READY_BUY);
        model.setGlobalSpisokModel(currentGlobalSpisok);
        goodsRepository.saveAndFlush(model);
    }

    public void createNewGood(String name, String img, BigDecimal price, Integer count, Long idSpisok) {
        GoodsModel model = new GoodsModel();
        var currentGlobalSpisok = globalSpisokRepository.findById(idSpisok).orElseThrow(() -> new ExceptionNotElements(NOT_FIND_ELEMENT));
        model.setName(name);
        model.setRoleOfStatus(RoleOfStatus.READY_BUY);
        model.setImg(img);
        model.setPrice(price.multiply(new BigDecimal(count)));
        model.setCount(count);
        model.setGlobalSpisokModel(currentGlobalSpisok);
        goodsRepository.saveAndFlush(model);
    }

    /**
     * Получаю товар по наименованию
     *
     * @param name - наименование товара
     */
    public GoodsModel getGoodsByName(String name) {
        return goodsRepository.findByName(name).orElseThrow(() -> new ExceptionNotElements(NOT_FIND_ELEMENT));
    }

    /**
     * Получаю все возможные товары
     */
    public List<GoodsModel> allGoods() {
        return goodsRepository.findAll();
    }

    /**
     * Получаю товары согласно их статусу
     *
     * @param role - статус товара
     */
    public List<GoodsModel> allGoodsFromRole(RoleOfStatus role) {
        return goodsRepository.findByRoleOfStatus(role);
    }


    public List<GoodsModel> allGoodsFromCurrentShopList(Long id) {
        return goodsRepository.findAllByGlobalSpisokModel_Id(id);
    }

    public List<GoodsModel> allGoodsFromCurrentShopList(Long id, RoleOfStatus role) {
        return goodsRepository.findAllByGlobalSpisokModel_IdAndRoleOfStatus(id, role);
    }

    public Long deleteGood(Long id) {
        //TODO: сделать проверки на то есть ли такой элемент с таким id в БД
//        Long idCurrentSpisok = goodsRepository.findByGlobalSpisokModelId(id).get().getId();
        Long idCurrentSpisok = goodsRepository.findById(id).get().getGlobalSpisokModel().getId();
        goodsRepository.deleteById(id);
        return idCurrentSpisok;
    }

    public GoodsModel getGoodById(Long id) {
        return goodsRepository.findById(id).get();
    }
}
