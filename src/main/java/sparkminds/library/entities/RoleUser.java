package sparkminds.library.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import lombok.Data;
import sparkminds.library.enums.Role;

@Entity
@Table (name = "role_user")
@Data
public class RoleUser {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "role", columnDefinition = "varchar(10)")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column (name = "description", columnDefinition = "varchar(255)")
    private String description;

    @Column (name = "deleted", columnDefinition = "Boolean default false")
    private boolean deleted;

    @OneToMany (mappedBy = "roleUserId")
    private List<Person> personList;

}
