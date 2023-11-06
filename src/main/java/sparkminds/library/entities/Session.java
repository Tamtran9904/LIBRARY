package sparkminds.library.entities;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "session")
@Data
public class Session {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "jti", columnDefinition = "varchar(255)")
    private String jti;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id")
    private Person personId;
}
