package xtel.base21v2.infrastructure.shared.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenInfo {
    private Long id;
    private String username;
    private String jit;
    private LocalDateTime issuedAt;
    private LocalDateTime expiration;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiration);
    }
}