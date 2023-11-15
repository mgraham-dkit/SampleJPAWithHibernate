package composite_keys.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookId implements Serializable {
    private String title;
    private String author;
    private String language;

    public BookId() {
    }

    public BookId(String title, String author, String language) {
        this.title = title;
        this.author = author;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return Objects.equals(title, bookId.title) && Objects.equals(author, bookId.author) && Objects.equals(language, bookId.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, language);
    }

    @Override
    public String toString() {
        return "BookId{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
