package springboot.practise.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.practise.banking_app.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
