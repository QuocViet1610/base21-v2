package xtel.base21v2.module.account.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xtel.base21v2.infrastructure.shared.entity.BaseEntity;
import xtel.base21v2.infrastructure.shared.model.Status;

@Getter
@Setter
@Entity
@Table(name = "user_account")
@NoArgsConstructor
public class UserAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avatar")
    private String avatar;

    @Size(max = 100)
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Size(max = 20)
    @Column(name = "phone", nullable = false)
    private String phone;

    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(columnDefinition = "SMALLINT")
    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.ACTIVE;

    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;

//    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
//    private List<AccountGroup> accountGroups;

}