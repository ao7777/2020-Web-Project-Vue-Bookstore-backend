package org.reins.orm.entity;

import javax.persistence.*;

@Entity
@Table(name = "bookdescription")
public class BookDescEntity {
    private String isbn;
    private String description;
    @Id
    @Column(name = "isbn")
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
