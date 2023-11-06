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
import javax.persistence.Table;
import lombok.Data;
import sparkminds.library.enums.TargetToken;
import sparkminds.library.enums.VerificationTokenStatus;

@Entity
@Table (name = "verification_token")
@Data
public class VerificationToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "verification_token", columnDefinition = "varchar(255)")
    private String token;

    @Column (name = "date_of_expiry", columnDefinition = "timestamp")
    private Instant dateOfExpiry;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id")
    private Person personId;

    @Column (name = "verification_token_status", columnDefinition = "varchar(15)")
    @Enumerated(EnumType.STRING)
    private VerificationTokenStatus verificationTokenStatus;

    @Column (name = "target", columnDefinition = "varchar(25)")
    @Enumerated(EnumType.STRING)
    private TargetToken targetToken;
}
