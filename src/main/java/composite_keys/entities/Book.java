package composite_keys.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="books")
public class Book {
    @EmbeddedId
    private BookId bookId;
    private String description;
    private int pageCount;

    public Book() {
    }

    public Book(BookId bookId, String description, int pageCount) {
        this.bookId = bookId;
        this.description = description;
        this.pageCount = pageCount;
    }

    public BookId getBookId() {
        return bookId;
    }

    public void setBookId(BookId bookId) {
        this.bookId = bookId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                '}';
    }

    public boolean update(Book b){
        if(this.equals(b)) {
            this.description = b.description;
            this.pageCount = b.pageCount;
            return true;
        }
        return false;
    }
}
