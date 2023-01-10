package ru.spiso4nik.accountservice.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccountDtoRequest {
    private String firstName;

    private String surname;

    private String email;

    private String password_;

    @JsonAlias(value = "code")
    private String codeActivation;

    private Boolean active;
}
