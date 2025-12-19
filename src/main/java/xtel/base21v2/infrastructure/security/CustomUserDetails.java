package xtel.base21v2.infrastructure.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xtel.base21v2.infrastructure.exception.CustomException;
import xtel.base21v2.infrastructure.shared.model.Status;
import xtel.base21v2.module.account.entity.UserAccount;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails implements UserDetails, Serializable {

    private Long id;
    private String password;
    private String userName;
    private Status status;
    private String phone;
    private boolean isDeleted;

    public CustomUserDetails(UserAccount account) {
        if (account == null) {
            throw new CustomException("Account cannot be null");
        }

        this.id = account.getId();
        this.password = account.getPassword();
        this.status = account.getStatus();
        this.isDeleted = account.getIsDeleted();
        this.userName = account.getUserName();
        this.phone = account.getPhone();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isEnabled() {
        return Status.ACTIVE.equals(this.status) && !isDeleted;
    }
}
