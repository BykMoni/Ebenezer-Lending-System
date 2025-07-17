import java.util.*;

public class Borrower {
    private String name;
    private String id;
    private String contactInfo;
    private List<String> borrowedBooks;
    private double finesOwed;



    public Borrower(String name, String id, String contactInfo) {
        this.name = name;
        this.id = id;
        this.contactInfo = contactInfo;
        this.borrowedBooks = new ArrayList<>();
        this.finesOwed = 0.0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public double getFinesOwed() {
        return finesOwed;
    }

    public void addBorrowedBook(String isbn) {
        borrowedBooks.add(isbn);
    }

    public void returnBorrowedBook(String isbn) {
        borrowedBooks.remove(isbn);
    }

    public void addFine(double amount) {
        finesOwed += amount;
    }

    public void payFine(double amount) {
        finesOwed -= amount;
        if (finesOwed < 0) finesOwed = 0;
    }

    public String toString() {
        return "Name: " + name + " | ID: " + id +
               " | Contact: " + contactInfo +
               " | Borrowed Books: " + borrowedBooks +
               " | Fines: $" + String.format("%.2f", finesOwed);
    }


}
