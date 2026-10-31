package org.j2os.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.j2os.account.dto.*;
import org.j2os.account.service.AccountClassicService;
import org.j2os.account.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final AccountClassicService classicService;

    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @Valid @RequestBody AccountCreateRequest request
            ) {

        log.info("Create account request received");

        AccountResponse response =
                accountService.create(request);

        URI location = URI.create(
                "/api/accounts" + response.getAccountId()
        );

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getById(
            @PathVariable UUID accountId) {

        AccountResponse response = accountService.getById(accountId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAll(){

        List<AccountResponse> responses = accountService.getAll();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountResponse>> getByBalance(@RequestParam Long balance) {

        List<AccountResponse> responses =
                accountService.getByBalance(balance);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search/balance-less-than")
    public ResponseEntity<List<AccountResponse>> getByBalanceLessThan(
            @RequestParam Long balance) {
//        GET http://localhost:8081/api/accounts/search/balance-less-than?balance=1000
        List<AccountResponse> responses =
                accountService.getByBalanceLessThan(balance);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search/balance-greater-than")
    public ResponseEntity<List<AccountResponse>> getByBalanceGreaterThan(
            @RequestParam Long balance) {

        List<AccountResponse> responses =
                accountService.getByBalanceGreaterThan(balance);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search/balance-less-than-equal")
    public ResponseEntity<List<AccountResponse>> getByBalanceLessThanEqual(
            @RequestParam Long balance) {

        List<AccountResponse> responses =
                accountService.getByBalanceLessThanEqual(balance);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search/balance-greater-than-equal")
    public ResponseEntity<List<AccountResponse>> getByBalanceGreaterThanEqual(
            @RequestParam Long balance) {

        List<AccountResponse> responses =
                accountService.getByBalanceGreaterThanEqual(balance);

        return ResponseEntity.ok(responses);

    }

    @GetMapping("search/balance-greater-than-with-query")
    public ResponseEntity<List<AccountResponse>> getAccountsWithBalanceGreaterThan(
            @RequestParam Long balance) {

        List<AccountResponse> responses =
                accountService.getAccountsWithBalanceGreaterThan(balance);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("search/balance-or-owner")
    public ResponseEntity<List<AccountResponse>> getByBalanceOrOwnerName(
            @RequestParam Long balance,
            @RequestParam String ownerName) {

//        GET http://localhost:8081/api/accounts/search/balance-or-owner?balance=2200&ownerName=Monireh5
        List<AccountResponse> responses =
                accountService.getByBalanceOrOwnerName(balance, ownerName);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("search/balance-and-owner")
    public ResponseEntity<List<AccountResponse>> getByBalanceAndOwnerName(
            @RequestParam Long balance,
            @RequestParam String ownerName) {

        List<AccountResponse> responses =
                accountService.getByBalanceAndOwnerName(balance,ownerName);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search/complex")
    public ResponseEntity<List<AccountResponse>> getByBalanceAndOwnerNames(
            @RequestParam Long balance,
            @RequestParam String ownerName1,
            @RequestParam String ownerName2) {

//        GET http://localhost:8081/api/accounts/search/complex?balance=2200&ownerName1=monirehInherit&ownerName2=Monireh5
        List<AccountResponse> responses =
                accountService.getByBalanceAndOwnerNames(
                        balance,
                        ownerName1,
                        ownerName2
                );

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<AccountResponse>> getAll(
            Pageable pageable,
            @RequestParam(required = false) Long balance
    ) {

//        http://localhost:8081/api/accounts/paged?page=0&size=3
//        http://localhost:8081/api/accounts/paged?page=1&size=3
//        http://localhost:8081/api/accounts/paged?page=0&size=3&sort=accountBalance,desc
//        http://localhost:8081/api/accounts/paged?page=0&size=3&sort=accountBalance,asc
//        http://localhost:8081/api/accounts/paged?page=0&size=5&sort=accountOwnerName,asc
//        http://localhost:8081/api/accounts/paged?page=0&size=5&sort=accountOwnerName,desc
//        | Page | رکوردها |
//        | ---: | ------: |
//        |    0 |     1–3 |
//        |    1 |     4–6 |
//        |    2 |     7–9 |
//        |    3 |      10 |

        return ResponseEntity.ok(
                accountService.getAll(pageable, balance));
    }

    @GetMapping("/search/specification/complex")
    public ResponseEntity<List<AccountResponse>> getByBalanceAndOwnerNameUsingSpecification(
            @RequestParam Long balance,
            @RequestParam String ownerName) {

        //http://localhost:8081/api/accounts/search/specification/complex?balance=2200&ownerName=monirehInherit
        return ResponseEntity.ok(
                accountService.getByBalanceAndOwnerNameUsingSpecification(
                        balance,
                        ownerName
                )
        );
    }

    @GetMapping("/search/specification")
    public ResponseEntity<List<AccountResponse>> search(
            @RequestParam(required = false) Long balance,
            @RequestParam(required = false) String ownerName) {

        return ResponseEntity.ok(
                accountService.search(balance, ownerName)
        );
    }

    @GetMapping("/projection")
    public ResponseEntity<List<AccountSummary>> getSummaryByBalance(
            @RequestParam Long balance) {

//        http://localhost:8081/api/accounts/projection?balance=2200
//        فرض کن فقط این اطلاعات Account را می‌خواهیم:
//        خروجی علاوه بر دو فیلد اصلی، اطلاعات داخلی Proxy مربوط به Spring Data را هم نشان داده
//        accountOwnerName
//        accountOwnerMail
//        ولی الان با:
//        List<Account>
//        کل Entity را از Database می‌گیریم.
//        این به خاطر این است که AccountSummary یک Interface Projection است و Spring Data پشت صحنه یک Proxy می‌سازد.
//        Projection کمک می‌کند فقط اطلاعات موردنیاز را بگیریم.

        return ResponseEntity.ok(
                accountService.getSummaryByBalance(balance)
        );
    }

    @GetMapping("/projection/dto")
    public ResponseEntity<List<AccountSummaryDto>> getSummaryDtoByBalance(
            @RequestParam Long balance) {

        //http://localhost:8081/api/accounts/projection/dto?balance=2200
        return ResponseEntity.ok(
                accountService.getSummaryDtoByBalance(balance)
        );
    }

    @GetMapping("/projection/dynamic")
    public ResponseEntity<List<AccountSummaryDto>> getDynamicSummary(
            @RequestParam Long balance) {

        //Dynamic Projection
        return ResponseEntity.ok(
                accountService.getDynamicSummary(balance)
        );
    }

    @PostMapping("/{accountId}/branch")
    public ResponseEntity<Void> assignBranch(
            @PathVariable UUID accountId,
            @RequestParam String branchName,
            @RequestParam String branchAddress) {

        accountService.assignBranch(
                accountId,
                branchName,
                branchAddress
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/test-lazy/{accountId}")
    public ResponseEntity<LazyTestResponse> testLazy(
            @PathVariable UUID accountId) {

        return ResponseEntity.ok(
                accountService.testLazy(accountId)
        );
    }

    @GetMapping("/test-branch/{branchId}")
    public ResponseEntity<LazyBranchTestResponse> testBranchAccounts(
            @PathVariable UUID branchId) {

        //http://localhost:8081/api/accounts/test-branch/{branchId}
        return ResponseEntity.ok(
                accountService.testBranchAccounts(branchId)
        );
    }


    @GetMapping("/test-n-plus-one")
    public ResponseEntity<Void> testNPlusOne(){

        accountService.testNPlusOne();

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/test-join-fetch")
    public ResponseEntity<Void> testJoinFetch() {

        accountService.testJoinFetch();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/test-entity-graph")
    public ResponseEntity<Void> testEntityGraph() {

        accountService.testEntityGraph();

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/test-cascade")
    public ResponseEntity<Void> testCascade() {

        accountService.testCascade();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/test-remove-branch/{branchId}")
    public ResponseEntity<Void> testRemoveBranch(
            @PathVariable UUID branchId
    ) {

        accountService.testRemoveBranch(branchId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/test-orphan-removal/{branchId}")
    public ResponseEntity<Void> testOrphanRemoval(
            @PathVariable UUID branchId
    ) {
        accountService.testOrphanRemoval(branchId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/test-orphan-create")
    public ResponseEntity<UUID> createOrphanTest() {

        return ResponseEntity.ok(
                accountService.createOrphanTest()
        );
    }

    @PutMapping("/test-merge/{branchId}")
    public ResponseEntity<Void> testMerge(
            @PathVariable UUID branchId) {

        accountService.testMerge(branchId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/testPersistenceContext/{accountId}")
    public ResponseEntity<Void> testPersistenceContext(
            @PathVariable UUID accountId) {

        classicService.testPersistenceContext(accountId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/test-detached/{accountId}")
    public ResponseEntity<Void> testDetached(
            @PathVariable("accountId") UUID accountId) {

        classicService.testDetached(accountId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAccount/{accountId}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable UUID accountId
    ) {

        //DELETE http://localhost:8081/accounts/deleteAccount/{accountId}
        classicService.deleteAccount(accountId);

        return ResponseEntity.noContent().build();
    }


}
