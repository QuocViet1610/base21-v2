package xtel.base21v2.module.account.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xtel.base21v2.module.account.entity.LogoutManage;

@Repository
public interface LogoutManageRepo extends JpaRepository<LogoutManage, Long> {
    boolean existsByTokenUuid(String tokenUuid);
}
