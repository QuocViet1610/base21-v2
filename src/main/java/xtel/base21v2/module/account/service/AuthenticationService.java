package xtel.base21v2.module.account.service;


import org.springframework.stereotype.Service;
import xtel.base21v2.module.account.domain.LoginDto;
import xtel.base21v2.module.account.domain.request.AccountLoginRequest;
import xtel.base21v2.module.account.domain.request.LogoutRequest;
import xtel.base21v2.module.account.domain.request.RegisterDto;

import java.util.List;

@Service
public interface AuthenticationService {

  LoginDto login(AccountLoginRequest accountLoginRequest);

  void logout(LogoutRequest logoutRequest);

  void register(RegisterDto register);

}