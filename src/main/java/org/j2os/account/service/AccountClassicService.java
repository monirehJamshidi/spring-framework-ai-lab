package org.j2os.account.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.j2os.account.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AccountClassicService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveAccount(Account account){
        entityManager.persist(account);
    }

    public List<Account> getAccounts(){
        return entityManager.createQuery("select a from Account a").getResultList();
    }

    @Transactional
    public void testPersistenceContext(
            UUID accountId) {

        // 1. Load Account from the database
        Account account = entityManager.find(
                Account.class,
                accountId
        );

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        // 2. Change the managed Entity
        account.setAccountOwnerName("Managed Test");

        // 3. Flush changes to the database
        entityManager.flush();
    }

    @Transactional
    public void testDetached(java.util.UUID accountId) {

        Account account = entityManager.find(
                Account.class,
                accountId
        );

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        entityManager.detach(account);

        account.setAccountOwnerName("Detached Test");

        entityManager.flush();
    }

    @Transactional
    public void deleteAccount(UUID accountId) {
        Account account = entityManager.find(Account.class, accountId);

        if (account != null) {
            entityManager.remove(account);
        }
    }
}
