package sparkminds.library.entities;

import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import sparkminds.library.enums.TargetToken;
import sparkminds.library.enums.VerificationTokenStatus;

@Entity
@Table(name = "one_time_password")
@Data
public class OneTimePassword {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "otp", columnDefinition = "varchar(255)")
    private String otp;

    @Column (name = "date_of_expiry", columnDefinition = "Datetime")
    private Instant dateOfExpiry;

    @Column (name = "target", columnDefinition = "varchar(15)")
    @Enumerated(EnumType.STRING)
    private TargetToken targetToken;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person personId;

}
