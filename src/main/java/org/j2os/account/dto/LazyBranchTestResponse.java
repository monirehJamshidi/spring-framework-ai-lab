package org.j2os.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LazyBranchTestResponse {

    private String branchName;
    private List<String> accountOwnerNames;
}
