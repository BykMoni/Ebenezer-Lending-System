import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class FileHandler {

    private static final String BORROWER_FILE = "Borrower.txt";

    // Save a list of borrowers to file
    public static void saveBorrowers(List<Borrower> borrowers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BORROWER_FILE))) {
            for (Borrower b : borrowers) {
                writer.println(serializeBorrower(b));
            }
        } catch (IOException e) {
            System.out.println("Error saving borrowers to file.");
            e.printStackTrace();
        }
    }

    // Load borrowers from file
    public static List<Borrower> loadBorrowers() {
        List<Borrower> borrowers = new ArrayList<>();
        File file = new File(BORROWER_FILE);
        if (!file.exists()) return borrowers;

        try (BufferedReader reader = new BufferedReader(new FileReader(BORROWER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                borrowers.add(deserializeBorrower(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading borrowers from file.");
            e.printStackTrace();
        }
        return borrowers;
    }

    // Convert borrower to string for file
    private static String serializeBorrower(Borrower b) {
        return b.getName() + " ; " + b.getId() + " ; " + b.getContactInfo() + " ; " +
                String.join(" , ", b.getBorrowedBooks()) + " ; " + b.getFinesOwed();
    }

    // Convert line from file to Borrower
    private static Borrower deserializeBorrower(String line) {
        String[] parts = line.split(";");
        if (parts.length < 5) return null;
        String name = parts[0];
        String id = parts[1];
        String contact = parts[2];
        List<String> books = parts[3].isEmpty() ? new ArrayList<>() : Arrays.asList(parts[3].split(","));
        double fines = Double.parseDouble(parts[4]);

        Borrower b = new Borrower(name, id, contact);
        for (String isbn : books) b.addBorrowedBook(isbn);
        b.addFine(fines);
        return b;
    }


// ========== BOOKS ==========
private static final String BOOK_FILE = "books.txt";

    // Save list of books to file
    public static void saveBooks(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOK_FILE))) {
            for (Book b : books) {
                writer.println(serializeBook(b));
            }
        } catch (IOException e) {
            System.out.println("Error saving books to file.");
            e.printStackTrace();
        }
    }

    // Load list of books from file
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOK_FILE);
        if (!file.exists()) return books;

        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book b = deserializeBook(line);
                if (b != null) books.add(b);
            }
        } catch (IOException e) {
            System.out.println("Error loading books from file.");
            e.printStackTrace();
        }
        return books;
    }

    // Convert Book to text
    private static String serializeBook(Book b) {
        return b.getTitle() + ";" + b.getAuthor() + ";" + b.getIsbn() + ";" +
                b.getCategory() + ";" + b.getYear() + ";" + b.getPublisher() + ";" + b.toString().split("Shelf: ")[1];
    }

    // Convert line to Book object
    private static Book deserializeBook(String line) {
        String[] parts = line.split(";");
        if (parts.length < 7) return null;
        String title = parts[0];
        String author = parts[1];
        String isbn = parts[2];
        String category = parts[3];
        int year = Integer.parseInt(parts[4]);
        String publisher = parts[5];
        String shelfLocation = parts[6];
        return new Book(title, author, isbn, category, year, publisher, shelfLocation);
    }


// ========== TRANSACTIONS ==========

    private static final String TRANSACTION_FILE = "transactions.txt";

    // Save all transactions to file
    public static void saveTransactions(List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTION_FILE))) {
            for (Transaction t : transactions) {
                writer.println(serializeTransaction(t));
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions.");
            e.printStackTrace();
        }
    }

    // Load all transactions from file
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTION_FILE);
        if (!file.exists()) return transactions;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction t = deserializeTransaction(line);
                if (t != null) transactions.add(t);
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions.");
            e.printStackTrace();
        }

        return transactions;
    }

    // Convert transaction to string
    private static String serializeTransaction(Transaction t) {
        return t.getBookISBN() + ";" + t.getBorrowerID() + ";" + t.getStatus() + ";" + t.getDueDate() + "," +
                t.toString().split("Borrow Date: ")[1].split(" \\|")[0] + ";" +
                t.toString().split("Return Date: ")[1].split(" \\|")[0];
    }

    // Convert string to transaction object
    private static Transaction deserializeTransaction(String line) {
        String[] parts = line.split(";");
        if (parts.length < 6) return null;

        String isbn = parts[0];
        String borrowerID = parts[1];
        String status = parts[2];
        String borrowDate = parts[3];
        String returnDate = parts[4].equals("null") ? null : parts[4];
        LocalDate dueDate = LocalDate.parse(parts[5]);

        Transaction t = new Transaction(isbn, borrowerID, borrowDate, dueDate);
        if (status.equals("Returned") && returnDate != null) {
            t.markAsReturned(returnDate);
        }

        return t;
    }

}


