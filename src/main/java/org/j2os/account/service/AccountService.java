package org.j2os.account.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.j2os.account.dto.*;
import org.j2os.account.entity.Account;
import org.j2os.account.entity.Branch;
import org.j2os.account.exception.AccountNotFoundException;
import org.j2os.account.repository.AccountSpecifications;
import org.j2os.account.repository.BranchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.j2os.account.repository.AccountRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    @PersistenceContext
    private EntityManager entityManager;

    private final AccountRepository accountRepository;

    private final BranchRepository branchRepository;

    public AccountResponse create(AccountCreateRequest request) {

        Account account = Account.builder()
                .accountBalance(request.getAccountBalance())
                .accountOwnerName(request.getAccountOwnerName())
                .accountOwnerMail(request.getAccountOwnerMail())
                .accountOwnerAddress(request.getAccountOwnerAddress())
                .build();

        Account savedAccount = accountRepository.save(account);

        log.info(
                "Account created successfully: {}",
                savedAccount.getAccountId()
        );

        return toResponse(savedAccount);

    }

    private AccountResponse toResponse(Account account){
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .accountBalance(account.getAccountBalance())
                .accountOwnerName(account.getAccountOwnerName())
                .accountOwnerMail(account.getAccountOwnerMail())
                .accountOwnerAddress(account.getAccountOwnerAddress())
                .build();
    }

    @Transactional(readOnly = true)
    public AccountResponse getById(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        return toResponse(account);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAll() {

        return accountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getByBalance(Long balance) {

        //Derived Query Method
        return accountRepository.findByAccountBalance(balance)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getByBalanceLessThan(Long balance){

        //Derived Query
        //Spring Data از اسم متد متوجه می‌شود که منظور ما:
        //WHERE accountBalance < ?
        return accountRepository.findByAccountBalanceLessThan(balance)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getByBalanceGreaterThan(Long balance) {

        return accountRepository.findByAccountBalanceGreaterThan(balance)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getByBalanceLessThanEqual(Long balance) {

        return accountRepository.findByAccountBalanceLessThanEqual(balance)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getByBalanceGreaterThanEqual(Long balance) {

        return accountRepository.findByAccountBalanceGreaterThanEqual(balance)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getByBalanceOrOwnerName(
            Long balance, String ownerNmae) {

        return accountRepository.findByAccountBalanceOrAccountOwnerName(balance, ownerNmae)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getByBalanceAndOwnerName(
            Long balance, String ownerName) {

        return accountRepository.findByAccountBalanceAndAccountOwnerName(balance,ownerName)
                .stream()
                .map(this::toResponse)
                .toList();

    }


    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsWithBalanceGreaterThan(Long balance) {

        return accountRepository.findAccountsWithBalanceGreaterThan(balance)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getByBalanceAndOwnerNames(
            Long balance,
            String ownerName1,
            String ownerName2) {

        return accountRepository.
                findByBalanceAndOwnerNames(
                        balance,
                        ownerName1,
                        ownerName2
                ).stream()
                .map(this::toResponse)
                .toList();
    }

    //this methode did not work in oracle with 10g
//    @Transactional(readOnly = true)
//    public Page<AccountResponse> getAll(Pageable pageable) {
//
//        return accountRepository.findAll(pageable)
//                .map(this::toResponse);
//    }

    @Transactional(readOnly = true)
    public Page<AccountResponse> getAll(
            Pageable pageable,
            Long balance) {

        long startRow = pageable.getOffset();
        long endRow = startRow + pageable.getPageSize();

        String sortColumn = "account_id";
        String sortDirection = "ASC";

        if (pageable.getSort().isSorted()) {

            var order = pageable.getSort().iterator().next();

            sortColumn = switch (order.getProperty()) {
                case "accountId" -> "account_id";
                case "accountBalance" -> "account_balance";
                case "accountOwnerName" -> "account_owner_name";
                case "accountOwnerMail" -> "account_owner_mail";
                case "accountOwnerAddress" -> "account_owner_address";
                default -> "account_id";
            };

            sortDirection = order.getDirection().name();
        }

        List<AccountResponse> content =
                accountRepository.findAccountsForPage(
                                startRow,
                                endRow,
                                balance,
                                sortColumn,
                                sortDirection
                        )
                        .stream()
                        .map(this::toResponse)
                        .toList();

        long total = accountRepository.countAccounts(balance);

        return new PageImpl<>(
                content,
                pageable,
                total
        );
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getByBalanceAndOwnerNameUsingSpecification(
            Long balance,
            String ownerName) {

        Specification<Account> specification =
                Specification
                        .where(AccountSpecifications.hasBalance(balance))
                        .and(AccountSpecifications.hasOwnerName(ownerName));

        return accountRepository
                .findAll(specification)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<AccountResponse> search(
            Long balance,
            String ownerName) {

        Specification<Account> specification =
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.conjunction();

        if (balance != null) {
            specification = specification
                    .and(AccountSpecifications.hasBalance(balance));
        }

        if (ownerName != null && !ownerName.isBlank()) {
            specification = specification
                    .and(AccountSpecifications.hasOwnerName(ownerName));
        }

        return accountRepository
                .findAll(specification)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AccountSummary> getSummaryByBalance(Long balance) {

        return accountRepository
                .findSummaryByAccountBalance(balance);
    }

    @Transactional(readOnly = true)
    public List<AccountSummaryDto> getSummaryDtoByBalance(Long balance) {

        return accountRepository
                .findSummaryDtoByAccountBalance(balance);
    }

    @Transactional(readOnly = true)
    public List<AccountSummaryDto> getDynamicSummary(Long balance) {

        return accountRepository
                .findByAccountBalance(
                        balance,
                        AccountSummaryDto.class
                );
    }

    @Transactional
    public void assignBranch(
            UUID accountId,
            String branchName,
            String branchAddress) {

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(accountId));

        Branch branch = Branch.builder()
                .branchName(branchName)
                .branchAddress(branchAddress)
                .build();

        branchRepository.save(branch);

        account.setBranch(branch);

        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public LazyTestResponse testLazy(UUID accountId) {

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(accountId));

        String branchName = account.getBranch().getBranchName();

        return new LazyTestResponse(
                account.getAccountOwnerName(),
                branchName
        );
    }

    @Transactional(readOnly = true)
    public LazyBranchTestResponse testBranchAccounts(UUID branchId) {

        Branch branch = branchRepository
                .findById(branchId)
                .orElseThrow(() ->
                        new RuntimeException("Branch not found"));

        List<String> accountOwnerNames = branch.getAccounts()
                .stream()
                .map(Account::getAccountOwnerName)
                .toList();

        return new LazyBranchTestResponse(
                branch.getBranchName(),
                accountOwnerNames
        );
    }

    @Transactional(readOnly = true)
    public void testNPlusOne() {

        List<Branch> branches = branchRepository.findAll();

        for (Branch branch : branches) {
            System.out.println(
                    branch.getBranchName()
                            + " -> "
                            + branch.getAccounts().size()
                            + " accounts"
            );
        }
    }

    @Transactional(readOnly = true)
    public void testJoinFetch() {

        List<Branch> branches =
                branchRepository.findAllWithAccounts();

        for (Branch branch : branches) {
            System.out.println(
                    branch.getBranchName()
                            + " -> "
                            + branch.getAccounts().size()
                            + " accounts"
            );
        }
    }


    @Transactional(readOnly = true)
    public void testEntityGraph() {

        List<Branch> branches =
                branchRepository.findAll();

        for (Branch branch : branches) {
            System.out.println(
                    branch.getBranchName()
                            + " -> "
                            + branch.getAccounts().size()
                            + " accounts"
            );
        }
    }

    @Transactional
    public void testCascade() {

        Branch branch = Branch.builder()
                .branchName("Cascade Branch")
                .branchAddress("Vienna")
                .build();

        Account account = Account.builder()
                .accountBalance(1000L)
                .accountOwnerName("Cascade Test")
                .accountOwnerMail("cascade@test.com")
                .accountOwnerAddress("Vienna")
                .branch(branch)
                .build();

        branch.getAccounts().add(account);

        branchRepository.save(branch);
    }

    @Transactional
    public void testRemoveBranch(UUID branchId){

        Branch branch = branchRepository
                .findById(branchId)
                .orElseThrow(() ->
                        new RuntimeException("Branch not found"));

        branchRepository.delete(branch);
    }


    @Transactional
    public void testOrphanRemoval(UUID branchId) {
        Branch branch = branchRepository
                .findById(branchId)
                .orElseThrow(() ->
                        new RuntimeException("Branch not found"));

        Account account = branch.getAccounts()
                .get(0);

        branch.getAccounts().remove(account);
    }

    @Transactional
    public UUID createOrphanTest(){

        Branch branch = Branch.builder()
                .branchName("Orphan Branch222")
                .branchAddress("Vienna222")
                .build();

        Account account = Account.builder()
                .accountBalance(2000L)
                .accountOwnerName("Orphan Test222")
                .accountOwnerMail("orphan222@test.com")
                .accountOwnerAddress("Vienna222")
                .branch(branch)
                .build();

        branch.getAccounts().add(account);

        branchRepository.save(branch);

        return branch.getBranchId();
    }


    @Transactional
    public void testMerge(UUID branchId) {

        Branch branch = branchRepository
                .findById(branchId)
                .orElseThrow(() ->
                        new RuntimeException("Branch not found"));

        Account account = branch.getAccounts()
                .get(0);

        entityManager.detach(account);
        entityManager.detach(branch);

        branch.setBranchName("Merged Branch Updated1");

        account.setAccountOwnerName("Merged Account Updated1");

        entityManager.merge(branch);
    }





    //for Inheritance methode
    public void saveInheritanceMethod(Account account){
        accountRepository.save(account);
    }

    @Transactional
    public void saveInheritanceMethodTwoAccounts(Account account1, Account account2){
        accountRepository.save(account1);
        accountRepository.save(account2);
    }

    //for Grammar methode
    public Map<String, Object> fullFetch(Account account) {
        var genericDTO = new HashMap<String, Object>();
        genericDTO.put("findByAccountBalance", accountRepository.findByAccountBalance(account.getAccountBalance()));
        genericDTO.put("findByAccountBalanceLessThan", accountRepository.findByAccountBalanceLessThan(account.getAccountBalance()));
        genericDTO.put("findByAccountBalanceOrAccountOwnerName", accountRepository.findByAccountBalanceOrAccountOwnerName(account.getAccountBalance(), account.getAccountOwnerName()));
        genericDTO.put("customField",2+2);

        log.info("Service fullFetch done successfully");
        return genericDTO;
    }

    //for Pragramatic methode
    public List<Account> getAccountsTestWithPragramatic(Account account){
        return accountRepository.findAccountsWithBalanceGreaterThan(account.getAccountBalance());
    }




}
