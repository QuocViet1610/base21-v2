package xtel.base21v2.module.account.domain.request;

import lombok.Data;

@Data
public class SendEmailEvent  {
    private final String email;

    public SendEmailEvent (String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
