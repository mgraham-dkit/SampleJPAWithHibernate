package composite_keys.daos;

import composite_keys.entities.Book;
import composite_keys.entities.BookId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public class BookDao implements BookDaoInterface {
    private final EntityManager entityManager;

    public BookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> getAllBooks() {
        Query query = entityManager.createQuery("SELECT b FROM Book b");
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        Query query = entityManager.createQuery("SELECT b FROM Book b WHERE b.bookId.author = ?1");
        query.setParameter(1, author);
        return query.getResultList();
    }

    @Override
    public Optional<Book> getBookByPrimary(BookId id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public boolean save(Book b) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(b);
            transaction.commit();
            return true;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while persisting\n\t" + b);
            transaction.rollback();
            return false;
        }
    }

    @Override
    public Book update(Book b) {
        Optional<Book> result = getBookByPrimary(b.getBookId());
        if (result.isPresent()) {
            Book book = result.get();
            book.update(b);
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Book updated = entityManager.merge(book);
                transaction.commit();
                return updated;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while merging\n\t" + book);
                transaction.rollback();
                return null;
            }
        } else {
            System.err.println("An error occurred while trying to update\n\t" + b);
            System.err.println("No such entity exists in the database");
            return null;
        }
    }

    @Override
    public boolean remove(Book b) {
        Optional<Book> result = getBookByPrimary(b.getBookId());
        if (result.isPresent()) {
            Book loadedBook = result.get();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.remove(loadedBook);
                transaction.commit();
                return true;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while deleting\n\t" + b);
                transaction.rollback();
                return false;
            }
        } else {
            System.err.println("An error occurred while trying to remove\n\t" + b);
            System.err.println("No such entity exists in the database");
            return false;
        }
    }

    public boolean removeFailIfDetached(Book b) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(b);
            transaction.commit();
            return true;
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Entity\n\t" + b + "\n is currently detached or does not exist");
            transaction.rollback();
            return false;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while deleting\n\t" + b);
            transaction.rollback();
            return false;
        }
    }
}
