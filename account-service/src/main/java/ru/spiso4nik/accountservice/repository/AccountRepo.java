package ru.spiso4nik.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spiso4nik.accountservice.models.account.AccountModel;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<AccountModel, Long> {

    Optional<AccountModel> findByEmail(String eMail);

    List<AccountModel> findByFirstNameAndSurname(String firstName, String surname);

    List<AccountModel> findAllByActive(boolean active);
}
