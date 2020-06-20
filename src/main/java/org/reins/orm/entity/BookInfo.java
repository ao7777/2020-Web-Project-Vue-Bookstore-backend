package org.reins.orm.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "bookinfo")
public class BookInfo {
    @Id
    private String _id;
    private Long isbn;
    private String description;
    private Binary pic;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public void setPic(Binary pic) {
        this.pic = pic;
    }

    public Binary getPic() {
        return pic;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
