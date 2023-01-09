package ru.spiso4nik.accountservice.services;

import org.springframework.stereotype.Service;
import ru.spiso4nik.accountservice.exceptions.ExceptionNotElements;
import ru.spiso4nik.accountservice.exceptions.ExceptionRepeatElement;
import ru.spiso4nik.accountservice.models.account.AccountModel;
import ru.spiso4nik.accountservice.models.account.RoleOfUser;
import ru.spiso4nik.accountservice.repository.AccountRepo;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public void addNewUser(AccountModel accountModel){
        accountModel.setRoleOfUser(RoleOfUser.USER);
        accountModel.setPassword_(accountModel.getPassword_());
        accountRepo.save(accountModel);
    }

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
