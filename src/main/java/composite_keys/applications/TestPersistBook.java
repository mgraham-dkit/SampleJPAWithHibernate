package composite_keys.applications;

import composite_keys.daos.BookDao;
import composite_keys.daos.BookDaoInterface;
import composite_keys.entities.Book;
import composite_keys.entities.BookId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class TestPersistBook {
    public static void main(String[] args) {
        // Hide info messages about hibernate activities
        // Can also hide warnings by setting level to Level.SEVERE
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        BookDaoInterface dao = new BookDao(entityManager);
        BookId bookId = new BookId("The Colour Purple", "Alice Walker", "English");
        Book newBook = new Book(bookId, "Sad but hopeful tale", 350);
        dao.save(newBook);

        BookId bookId1 = new BookId("The Colour Purple", "Alice Walker", "French");
        Book newBook1 = new Book(bookId1, "Sad but hopeful tale", 350);
        dao.save(newBook1);

        BookId bookId2 = new BookId("Green Eggs and Ham", "Dr. Seuss", "English");
        Book newBook2 = new Book(bookId2, "I do not like them, Sam-I-Am", 20);
        dao.save(newBook2);

        BookId bookId3 = new BookId("The Cat in the Hat", "Dr. Seuss", "English");
        Book newBook3 = new Book(bookId3, "I can do some good tricks, I will show them to you.", 15);
        dao.save(newBook3);

        printList(dao.getAllBooks());
        System.out.println("-----------------");

        Book toBeUpdated = new Book(new BookId("The Colour Purple", "Alice Walker", "English"), "Maybe not so sad now" +
                "...", 350);
        Book updated = dao.update(toBeUpdated);
        System.out.println(updated);
        
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void printList(List list){
        System.out.println("*****************");
        for (Object o : list) {
            System.out.println(o);
            System.out.println("-----------------");
        }
        System.out.println("*****************");
    }
}
