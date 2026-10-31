package org.j2os.account.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.j2os.account.dto.AccountSummary;
import org.j2os.account.dto.AccountSummaryDto;
import org.j2os.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AccountRepository
        extends JpaRepository<Account, UUID>,
        AccountRepositoryCustom,
        JpaSpecificationExecutor<Account> {

    //----- Derived Query Method -----
    //یعنی Spring Data JPA از نام متد Query را می‌سازد و لازم نیست خودمان SQL یا JPQL بنویسیم.
    List<Account> findByAccountBalance(Long accountBalance);

    List<Account> findByAccountBalanceOrAccountOwnerName(
            Long accountBalance,
            @Size(min = 2, max = 25) String accountOwnerName
    );

    List<Account> findByAccountBalanceAndAccountOwnerName(
            Long accountBalance,
            @NotBlank @Size(min = 2, max = 25) String accountOwnerName
    );

    //Derived Query
    List<Account> findByAccountBalanceLessThan(Long accountBalanceIsLessThan);

    List<Account> findByAccountBalanceGreaterThan(Long accountBalanceIsGreaterThan);

    List<Account> findByAccountBalanceLessThanEqual(Long accountBalanceIsLessThan);

    List<Account> findByAccountBalanceGreaterThanEqual(Long accountBalanceIsGreaterThan);

    @Query("""
            select a 
            from Account a 
            where a.accountBalance > :n
            """)
    List<Account> findAccountsWithBalanceGreaterThan(
            @Param("n") Long balance
    );


    //در Queryهای پیچیده‌تر، این روش معمولاً خواناتر و قابل‌کنترل‌تر از یک method name بسیار طولانی است.
    @Query("""
            select a
            from Account a
            where a.accountBalance = :balance
            and (
            a.accountOwnerName = :ownerName1
            or a.accountOwnerName = :ownerName2
            )
            """)
    List<Account> findByBalanceAndOwnerNames(
            @Param("balance") Long balance,
            @Param("ownerName1") String ownerName1,
            @Param("ownerName2") String ownerName2
    );

    @Query(value = """
        SELECT COUNT(*)
        FROM account
        WHERE (:balance IS NULL OR account_balance = :balance)
        """,
            nativeQuery = true)
    long countAccounts(@Param("balance") Long balance);


    //test Interface Projection
    //اما نکته جالب اینجاست که چون return type، AccountSummary است، Spring Data می‌فهمد که باید Projection را بسازد.
    List<AccountSummary> findSummaryByAccountBalance(Long accountBalance);


//    DTO Projection
//    JPQL @Query + constructor expression
//    این قسمت:
//    select new org.j2os.account.dto.AccountSummaryDto(...)
//    عنی Hibernate برای هر رکورد واقعاً یک object از AccountSummaryDto بساز و این دو مقدار را به constructor آن بده:
//    new AccountSummaryDto(
//            accountOwnerName,
//            accountOwnerMail
//            )
//    پس برخلاف Interface Projection، دیگر TupleBackedMap و Proxy نداریم.
    @Query("""
       select new org.j2os.account.dto.AccountSummaryDto(
           a.accountOwnerName,
           a.accountOwnerMail
       )
       from Account a
       where a.accountBalance = :balance
       """)
    List<AccountSummaryDto> findSummaryDtoByAccountBalance(
            @Param("balance") Long balance);



    <T> List<T> findByAccountBalance(
            Long accountBalance,
            Class<T> type
    );
}
