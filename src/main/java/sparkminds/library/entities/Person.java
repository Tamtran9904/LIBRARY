package sparkminds.library.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sparkminds.library.enums.AccountStatus;
import sparkminds.library.enums.Gender;

@Entity
@Table (name = "person")
@Data
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "email", columnDefinition = "varchar(50)")
    private String email;

    @Column (name = "password", columnDefinition = "varchar(255)")
    private String password;

    @Column (name = "name", columnDefinition = "varchar(50)")
    private String name;

    @Column (name = "gender", columnDefinition = "varchar(10)")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column (name = "phone", columnDefinition = "varchar(10)")
    private String phone;

    @Column (name = "address", columnDefinition = "varchar(100)")
    private String address;

    @Column (name = "deleted", columnDefinition = "Boolean default false")
    private Boolean deleted = false;

    @Column (name = "account_status", columnDefinition = "varchar(10)")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @OneToMany (mappedBy = "personId", fetch = FetchType.LAZY)
    private List<VerificationToken> verificationTokenList;

    @OneToMany (mappedBy = "personId", fetch = FetchType.LAZY)
    private List<OneTimePassword> oneTimePasswordList;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "role_user_id")
    private RoleUser roleUserId;
}
