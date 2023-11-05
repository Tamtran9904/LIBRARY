package sparkminds.library.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import lombok.Data;
import sparkminds.library.enums.BookType;

@Entity
@Table (name = "category")
@Data
public class Category {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name", columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private BookType name;

    @Column (name = "note", columnDefinition = "varchar(255)")
    private String note;

    @OneToMany (mappedBy = "categoryId", fetch = FetchType.LAZY)
    private List<Book> bookList;

    @Column (name = "deleted", columnDefinition = "Boolean default false")
    private boolean deleted;

}
