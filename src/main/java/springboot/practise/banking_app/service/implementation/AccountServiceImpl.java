package springboot.practise.banking_app.service.implementation;

import org.springframework.stereotype.Service;
import springboot.practise.banking_app.dto.AccountDto;
import springboot.practise.banking_app.entity.Account;
import springboot.practise.banking_app.mapper.AccountMapper;
import springboot.practise.banking_app.repository.AccountRepository;
import springboot.practise.banking_app.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create Bank Account
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    // Get Account Details
    @Override
    public AccountDto getAccountById(Long id) {

        Account account = accountRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Account does not exist!!"));
        return AccountMapper.mapToAccountDto(account);
    }

    // Deposit
    @Override
    public AccountDto deposit(Long id, double amount) {

        Account account = accountRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Account does not exist!!"));

        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    // Withdraw
    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account = accountRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Account does not exist!!"));

        if(account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    // Get All Accounts
    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    // Delete Account
    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Account does not exist!!"));

        accountRepository.deleteById(id);
    }


}
