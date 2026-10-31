package org.j2os.account.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.j2os.account.entity.Account;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Account> findAccountsForPage(
            long startRow,
            long endRow,
            Long balance,
            String sortColumn,
            String sortDirection) {

        String whereClause = "";

        if (balance != null) {
            whereClause = "WHERE account_balance = :balance";
        }

        String sql = """
            SELECT *
            FROM (
                SELECT a.*, ROWNUM rn
                FROM (
                    SELECT *
                    FROM account
                    %s
                    ORDER BY %s %s, account_id ASC
                ) a
                WHERE ROWNUM <= :endRow
            )
            WHERE rn > :startRow
            """.formatted(
                whereClause,
                sortColumn,
                sortDirection
        );

        var query = entityManager
                .createNativeQuery(sql, Account.class)
                .setParameter("startRow", startRow)
                .setParameter("endRow", endRow);

        if (balance != null) {
            query.setParameter("balance", balance);
        }

        return query.getResultList();
    }

    @Override
    public List<Account> findAccountsForPage(
            long startRow,
            long endRow,
            Specification<Account> specification,
            String sortColumn,
            String sortDirection) {

        var criteriaBuilder =
                entityManager.getCriteriaBuilder();

        var criteriaQuery =
                criteriaBuilder.createQuery(Account.class);

        var root =
                criteriaQuery.from(Account.class);

        criteriaQuery.select(root);

        if (specification != null) {
            criteriaQuery.where(
                    specification.toPredicate(
                            root,
                            criteriaQuery,
                            criteriaBuilder
                    )
            );
        }

        criteriaQuery.orderBy(
                sortDirection.equalsIgnoreCase("DESC")
                        ? criteriaBuilder.desc(root.get(sortColumn))
                        : criteriaBuilder.asc(root.get(sortColumn))
        );

        return entityManager
                .createQuery(criteriaQuery)
                .setFirstResult((int) startRow)
                .setMaxResults((int) (endRow - startRow))
                .getResultList();
    }
}