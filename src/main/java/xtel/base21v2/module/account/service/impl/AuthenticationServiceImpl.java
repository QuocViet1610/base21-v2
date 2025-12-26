package xtel.base21v2.module.account.service.impl;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xtel.base21v2.infrastructure.exception.CustomException;
import xtel.base21v2.infrastructure.exception.MessageCode;
import xtel.base21v2.infrastructure.security.JwtService;
import xtel.base21v2.infrastructure.shared.model.Status;
import xtel.base21v2.infrastructure.shared.model.TokenInfo;
import xtel.base21v2.module.account.domain.LoginDto;
import xtel.base21v2.module.account.domain.request.AccountLoginRequest;
import xtel.base21v2.module.account.domain.request.LogoutRequest;
import xtel.base21v2.module.account.domain.request.RegisterDto;
import xtel.base21v2.module.account.domain.request.SendEmailEvent;
import xtel.base21v2.module.account.entity.LogoutManage;
import xtel.base21v2.module.account.entity.UserAccount;
import xtel.base21v2.module.account.repository.LogoutManageRepo;
import xtel.base21v2.module.account.repository.UserAccountRepository;
import xtel.base21v2.module.account.service.AuthenticationService;
import org.apache.commons.codec.digest.DigestUtils;
import java.time.LocalDateTime;


@Service
@Transactional
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserAccountRepository accountRepository;
    private final JwtService jwtService;
    private final LogoutManageRepo logoutManageRepo;
    private final SendMailService service;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public LoginDto login(AccountLoginRequest accountLoginRequest) {
        UserAccount account = accountRepository.findByUserName(accountLoginRequest.getUserName()).orElseThrow(() -> new CustomException(MessageCode.ACCOUNT_NOT_FOUND));

        if (!new BCryptPasswordEncoder().matches(DigestUtils.md5Hex(accountLoginRequest.getPassword()), account.getPassword()))
        {
            throw new CustomException(MessageCode.INCORRECT_PASSWORD);
        }
        if (!Status.ACTIVE.equals(account.getStatus())) {
            throw new CustomException(MessageCode.ACCOUNT_INACTIVE);
        }

        String token = jwtService.generateToken(account.getId(), account.getUserName());

        return new LoginDto(token);
    }


    @Override
    public void logout(LogoutRequest logoutRequest) {
        TokenInfo tokenInfo = jwtService.getTokenInfo(logoutRequest.getToken());
        if (logoutManageRepo.existsByTokenUuid(tokenInfo.getJit())) {
            return;
        }
        LogoutManage logoutManage = LogoutManage.builder()
                .tokenUuid(tokenInfo.getJit())
                .issuedAt(LocalDateTime.now())
                .expiredAt(tokenInfo.getExpiration())
                .accountId(tokenInfo.getId())
                .build();
        logoutManageRepo.save(logoutManage);
    }


    @Override
    @Transactional
    public void register(RegisterDto register) {
        UserAccount account = accountRepository.findByUserName(register.getUserName()).orElse(null);
        if (account != null){
            throw new CustomException("Đã tồn tại");
        }
        UserAccount accountSave = new UserAccount();
        accountSave.setUserName(register.getUserName());
        if (register.getPassword() != null && !register.getPassword().isEmpty()) {
            accountSave.setPassword(new BCryptPasswordEncoder().encode(DigestUtils.md5Hex(register.getPassword())));
        }

//        accountRepository.save(accountSave);

        //send mail
//        service.sendEmail(register.getUserName()); // dùng Async
        eventPublisher.publishEvent(new SendEmailEvent(register.getUserName()));

        System.out.println("Thành công");
        throw new RuntimeException("SendEmailEvent sẽ bị chặn lại nếu có rollback");
    }

}























