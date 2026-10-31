package org.j2os.account.repository;

import org.j2os.account.entity.Account;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AccountRepositoryCustom {

    List<Account> findAccountsForPage(
            long startRow,
            long endRow,
            Long balance,
            String sortColumn,
            String sortDirection
    );

    List<Account> findAccountsForPage(
            long startRow,
            long endRow,
            Specification<Account> specification,
            String sortColumn,
            String sortDirection
    );
}
