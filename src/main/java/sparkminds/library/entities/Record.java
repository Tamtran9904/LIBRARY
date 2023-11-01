package sparkminds.library.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Entity
@Table (name = "record")
@Data
public class Record {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany (mappedBy = "recordId")
    private List<Book> bookList;

    @Column (name = "date_of_expiry", columnDefinition = "Datetime")
    private Instant dateOfExpiry;

    @Column (name = "date_of_issue", columnDefinition = "Datetime")
    private Instant dateOfIssue;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "customer_id")
    private Customer customerId;

    @Column (name = "deleted", columnDefinition = "Boolean default false")
    private Boolean deleted;
}
