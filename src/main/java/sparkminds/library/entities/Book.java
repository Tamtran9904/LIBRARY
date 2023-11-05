package sparkminds.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import sparkminds.library.enums.StatusBook;


@Entity
@Table (name = "book")
@Data
public class Book {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "title", columnDefinition = "varchar(50)")
    private String title;

    @Column (name = "price", columnDefinition = "Decimal(6,3)")
    private BigDecimal price;

    @Column (name = "publish_year", columnDefinition = "Integer")
    private Integer publishYear;

    @Column (name = "publish_name", columnDefinition = "varchar(100)")
    private String publishName;

    @Column (name = "status_book", columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private StatusBook statusBook;


    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "author_id")
    private Author authorId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "category_id")
    private Category categoryId;

    @JsonIgnore
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "record_id")
    private Record recordId;

    @JsonIgnore
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "admin_id")
    private Admin adminId;

    @Column (name = "deleted", columnDefinition = "Boolean default false")
    private Boolean deleted;
}
