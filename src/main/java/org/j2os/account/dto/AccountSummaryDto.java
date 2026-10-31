package org.j2os.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountSummaryDto {

    private String accountOwnerName;
    private String accountOwnerMail;
}
