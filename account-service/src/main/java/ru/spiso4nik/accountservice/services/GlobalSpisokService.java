package ru.spiso4nik.accountservice.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.spiso4nik.accountservice.exceptions.ExceptionNotElements;
import ru.spiso4nik.accountservice.models.account.AccountModel;
import ru.spiso4nik.accountservice.models.orderList.GlobalSpisokModel;
import ru.spiso4nik.accountservice.repository.GlobalSpisokRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class GlobalSpisokService {
    final GlobalSpisokRepository globalSpisokRepository;

    private final String NOT_FIND_ELEMENT = "Элемента нет в базе данных";

    public GlobalSpisokService(GlobalSpisokRepository globalSpisokRepository) {
        this.globalSpisokRepository = globalSpisokRepository;
    }

    public List<GlobalSpisokModel> getAllShopSpisok() {
        return globalSpisokRepository.findAll();
    }

    public List<GlobalSpisokModel> getAllShopSpisokByCurrentAccount(AccountModel accountModel) {
        return globalSpisokRepository.findAllByAccountModel(accountModel);
    }

    public void addNewSpisok(String spisokName, String storeName, LocalDate localDate, AccountModel accountModel) {
        if (StringUtils.isEmpty(spisokName) || StringUtils.isEmpty(storeName) || localDate == null) {
            throw new ExceptionNotElements("");
        }
        GlobalSpisokModel globalSpisokModel = new GlobalSpisokModel();
        globalSpisokModel.setNameOfShopList(spisokName);
        globalSpisokModel.setStoreName(storeName);
        globalSpisokModel.setDateTo(localDate);
        globalSpisokModel.setAccountModel(accountModel);
        globalSpisokRepository.saveAndFlush(globalSpisokModel);
    }

    public GlobalSpisokModel getCurrentSpisok(Long id) {
        return globalSpisokRepository.findById(id)
                .orElseThrow(() -> new ExceptionNotElements("Не смогли найти список"));
    }

    public void deleteCurrentSpisok(Long id) {
        globalSpisokRepository.deleteById(id);
    }

    public List<GlobalSpisokModel> getAllToDaySpisok(AccountModel accountModel){
        LocalDate date = LocalDate.now();
        return globalSpisokRepository.findAllByDateToAndAccountModel(date, accountModel);
    }

}
