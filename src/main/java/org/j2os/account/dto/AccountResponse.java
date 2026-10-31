package org.j2os.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AccountResponse {
    private UUID accountId;
    private Long accountBalance;
    private String accountOwnerName;
    private String accountOwnerMail;
    private String accountOwnerAddress;
}
