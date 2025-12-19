package xtel.base21v2.module.account.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AccountLoginRequest {
    @NotBlank(message = "Tài khoản không được để trống.")
    private String userName;
    @NotBlank(message = "Mật khẩu không được để trống.")
    private String password;

}
