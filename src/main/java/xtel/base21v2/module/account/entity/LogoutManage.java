package xtel.base21v2.module.account.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(
        name = "logout_manage",
        indexes = {
                @Index(name = "idx_token_uuid", columnList = "token_uuid")
        }
)
public class LogoutManage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "token_uuid")
    private String tokenUuid;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "account_id")
    private Long accountId;

}
