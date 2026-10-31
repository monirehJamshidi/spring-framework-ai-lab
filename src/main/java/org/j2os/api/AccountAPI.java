package org.j2os.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.j2os.account.entity.Account;
import org.j2os.account.service.AccountClassicService;
import org.j2os.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Slf4j
public class AccountAPI {
    private final AccountClassicService accountClassicService;
    private final AccountService accountService;

//    @PostMapping("/saveAndFindAll.do")
//    public Object saveAndFindAll(@Valid Account account){
//        List<Account> accounts = new ArrayList<>();
//        try {
//            log.info("call saveAndFindAll");
//            accountClassicService.saveAccount(account);
//
//            accounts = accountClassicService.getAccounts();
//        } catch (Exception e){
//            log.error(e.getMessage());
//        }
//
//        return accounts;
//
//    }

    @PostMapping("/saveAndFindAll.do")
    public ResponseEntity<String> createAccount(@Valid @RequestBody Account account) {
        // اگر این متد به دلیل ستون اشتباه دیتابیس خطا بدهد،
        // اسپرینگ خودش خطا را به ErrorHandler می‌فرستد.
        log.info("call saveAndFindAll");
        accountClassicService.saveAccount(account);
        return ResponseEntity.ok("Account created successfully");
    }

    @PostMapping("/saveWithInheritanceMethod.do")
    public ResponseEntity<String> saveWithInheritanceMethod(@Valid Account account){
        log.info("saveWithInheritenceMethod");
        accountService.saveInheritanceMethod(account);
        return ResponseEntity.ok("saveWithInheritanceMethod done successfully");
    }

    @GetMapping("/fullFetch.do")
    public Object fullFetch(@Valid Account account) {
        log.info("API fullFetch started ...");
        return accountService.fullFetch(account);
    }

    @GetMapping("/getAccountsTestWithPragramatic.do")
    public Object getAccountsTestWithPragramatic(@Valid Account account){
        return accountService.getAccountsTestWithPragramatic(account);
    }
}
