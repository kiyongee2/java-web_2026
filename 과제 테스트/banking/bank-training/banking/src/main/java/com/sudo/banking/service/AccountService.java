package com.sudo.banking.service;

import com.sudo.banking.entity.Account;
import com.sudo.banking.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository repository;

    // 생성자 주입(DI)
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Account createAccount(String owner, long initialBalance){
        if(owner == null || owner.isBlank()){
            throw new IllegalArgumentException("예금주 이름을 입력해야 합니다.");
        }
        if(initialBalance < 0){
            throw  new IllegalArgumentException("초기 잔액은 0원 이상이어야 합니다.");
        }
        String accountNumber = generateAccountNumber();
        Account newAccount = new Account(accountNumber, owner, initialBalance);
        return repository.save(newAccount);
    }

    // 110-0001 형태의 계좌번호 생성(중복시 다음 번호)
    public String generateAccountNumber(){
        System.out.println(repository.count());
        long seq = repository.count() + 1;
        String no = String.format("110-%04d", seq);
        while(repository.existsByAccountNumber(no)){
            seq++;
            no = String.format("110-%04d", seq);
        }
        return no;
    }

    @Transactional
    public List<Account> getAllAccounts(){
        return repository.findAll();
    }

    @Transactional
    public Account getAccount(String accountNumber){
        return repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 계좌입니다: " + accountNumber));
    }

    // 입금
    @Transactional
    public Account deposit(String accountNumber, long amount){
        Account account = getAccount(accountNumber);
        account.deposit(amount);
        return account;
    }
}
