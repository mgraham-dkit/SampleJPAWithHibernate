package composite_keys.daos;

import composite_keys.entities.Book;
import composite_keys.entities.BookId;

import java.util.List;
import java.util.Optional;

public interface BookDaoInterface {
    public List<Book> getAllBooks();
    public List<Book> getBooksByAuthor(String author);
    public Optional<Book> getBookByPrimary(BookId id);
    public boolean save(Book b);
    public Book update(Book b);
    public boolean remove(Book b);
}
