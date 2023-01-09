package ru.spiso4nik.accountservice.models.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spiso4nik.accountservice.models.orderList.GlobalSpisokModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "account")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {

    private final String _NAME = "auth_user";
    private final String SEQUENCE_NAME = "sequence_auth_user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = _NAME, sequenceName = SEQUENCE_NAME, initialValue = 50)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_name")
    private String firstName;

    @Column(name = "account_surname")
    private String surname;

    @Column(name = "account_email")
    private String email;

    @Column(name = "account_password")
    private String password_;

    @Column(name = "code_activation")
    private String codeActivation;

    @Column(name = "account_role")
    @Enumerated(EnumType.STRING)
    private RoleOfUser roleOfUser;

//    @Column(name = "user_active")
//    private Boolean active;

    @Column(name = "account_localCreated")
    private LocalDate localDateCreated;

    @OneToMany(mappedBy = "accountModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GlobalSpisokModel> globalSpisokModels;
}
