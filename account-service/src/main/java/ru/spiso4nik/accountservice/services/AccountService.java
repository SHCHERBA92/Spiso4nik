package ru.spiso4nik.accountservice.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spiso4nik.accountservice.dto.request.AccountDtoRequest;
import ru.spiso4nik.accountservice.exceptions.ExceptionNotElements;
import ru.spiso4nik.accountservice.exceptions.ExceptionRepeatElement;
import ru.spiso4nik.accountservice.models.account.AccountModel;
import ru.spiso4nik.accountservice.models.account.RoleOfUser;
import ru.spiso4nik.accountservice.repository.AccountRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Transactional
    public void addNewAccount(AccountDtoRequest accountDto){
        AccountModel accountModel = new AccountModel();
        accountModel.setActive(accountDto.getActive());
        accountModel.setEmail(accountDto.getEmail());
        accountModel.setFirstName(accountDto.getFirstName());
        accountModel.setSurname(accountDto.getSurname());
        accountModel.setPassword_(accountDto.getPassword_());
        accountModel.setCodeActivation(accountDto.getCodeActivation());

        accountModel.setRoleOfUser(RoleOfUser.USER);
        accountModel.setLocalDateCreated(LocalDate.now());
        accountModel.setGlobalSpisokModels(new ArrayList<>());
        accountRepo.save(accountModel);
    }

    @Transactional(readOnly = true)
    public AccountModel getAccountByEmail(String email){
        return accountRepo.findByEmail(email)
                .orElseThrow(() -> new ExceptionNotElements("Пользователь не найден"));
    }

    public List<AccountModel> getAccountsByNickName(String name, String surname){
        return accountRepo.findByFirstNameAndSurname(name, surname);
    }

    public List<AccountModel> getActiveAccounts(){
        return accountRepo.findAllByActive(true);
    }

    public List<AccountModel> getNotActiveAccounts(){
        return accountRepo.findAllByActive(false);
    }

    public void checkEmail(String email) {
        if (accountRepo.findByEmail(email).isPresent()){
            throw new ExceptionRepeatElement("Такой email уже существует");
        }
    }
}
