package ru.spiso4nik.accountservice.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccountDtoResponse {
    private String firstName;

    private String surname;

    private String email;

    private String password_;

    @JsonProperty(value = "code")
    private String codeActivation;

    private Boolean active;
}
