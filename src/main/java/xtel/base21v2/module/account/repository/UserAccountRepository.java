package xtel.base21v2.module.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import xtel.base21v2.module.account.entity.UserAccount;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long>,
  JpaSpecificationExecutor<UserAccount> {


  Optional<UserAccount> findByUserName(String username);
}
