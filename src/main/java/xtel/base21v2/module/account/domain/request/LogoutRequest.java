package xtel.base21v2.module.account.domain.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LogoutRequest {

  @NotEmpty(message = "Token không được để trống")
  private String token;

}
