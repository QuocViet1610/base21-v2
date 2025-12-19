package xtel.base21v2.infrastructure.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xtel.base21v2.module.account.entity.UserAccount;
import xtel.base21v2.module.account.repository.UserAccountRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository accountRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount account = accountRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(account);
    }

}
