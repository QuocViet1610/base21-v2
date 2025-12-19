package xtel.base21v2.module.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xtel.base21v2.module.account.domain.LoginDto;
import xtel.base21v2.module.account.domain.request.AccountLoginRequest;
import xtel.base21v2.module.account.domain.request.LogoutRequest;
import xtel.base21v2.module.account.service.AuthenticationService;

@Slf4j
@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public LoginDto login(@Valid @RequestBody AccountLoginRequest accountLoginRequest) {
        return authenticationService.login(accountLoginRequest);
    }

    @PostMapping("logout")
    @Operation(summary = "Đăng xuất")
    public void logout(@RequestBody LogoutRequest logoutRequest) {
        authenticationService.logout(logoutRequest);
    }

}
