package sparkminds.library.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table (name = "customer")
@Data
@PrimaryKeyJoinColumn(name = "customerId")
public class Customer extends Person {

    @OneToMany (mappedBy = "customerId", fetch = FetchType.LAZY)
    private List<Record> recordList;
}
