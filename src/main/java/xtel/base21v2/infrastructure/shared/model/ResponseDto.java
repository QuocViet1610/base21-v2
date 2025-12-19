package xtel.base21v2.infrastructure.shared.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ResponseDto<T> {
    private String requestId;
    private LocalDateTime timestamp;
    private String path;
    private Integer code;
    private String message;
    private T data;
}
