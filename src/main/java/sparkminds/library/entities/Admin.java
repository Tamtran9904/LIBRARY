package sparkminds.library.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sparkminds.library.enums.AccountStatus;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin")
@Data
@PrimaryKeyJoinColumn(name = "admin_id")
public class Admin extends Person {

}
