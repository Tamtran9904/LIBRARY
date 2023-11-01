package sparkminds.library.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import sparkminds.library.enums.Gender;

@Entity
@Table (name = "author")
@Data
public class Author {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name", columnDefinition = "varchar(50)")
    private String name;

    @Column (name = "gender", columnDefinition = "varchar(10)")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column (name = "phone", columnDefinition = "varchar(10)")
    private String phone;

    @Column (name = "email", columnDefinition = "varchar(50)")
    private String email;

    @Column (name = "address", columnDefinition = "varchar(100)")
    private String address;

    @Column (name = "deleted", columnDefinition = "Boolean default false")
    private Boolean deleted;
}
