package org.j2os.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateRequest {

    private Long accountBalance;

    @NotBlank
    @Size(min = 2, max = 25)
    private String accountOwnerName;

    @NotBlank
    @Email
    private String accountOwnerMail;

    @NotBlank
    private String accountOwnerAddress;
}
