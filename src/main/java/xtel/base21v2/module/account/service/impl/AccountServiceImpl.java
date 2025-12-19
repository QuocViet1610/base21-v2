package xtel.base21v2.module.account.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xtel.base21v2.module.account.repository.UserAccountRepository;
import xtel.base21v2.module.account.service.UserAccountService;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;



}
