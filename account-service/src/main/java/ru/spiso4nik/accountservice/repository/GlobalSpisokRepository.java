package ru.spiso4nik.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spiso4nik.accountservice.models.account.AccountModel;
import ru.spiso4nik.accountservice.models.orderList.GlobalSpisokModel;

import java.time.LocalDate;
import java.util.List;

public interface GlobalSpisokRepository extends JpaRepository<GlobalSpisokModel, Long> {
    List<GlobalSpisokModel> findAllByDateToAndAccountModel(LocalDate date, AccountModel userModel);
    List<GlobalSpisokModel> findAllByAccountModel(AccountModel userModel);
}
