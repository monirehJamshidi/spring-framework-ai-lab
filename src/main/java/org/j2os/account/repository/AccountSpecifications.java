package org.j2os.account.repository;

import org.j2os.account.entity.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecifications {

    public static Specification<Account> hasBalance(Long balance) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("accountBalance"),
                        balance
                );
    }

    public static Specification<Account> hasOwnerName(String ownerName) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("accountOwnerName"),
                        ownerName
                );
    }
}
