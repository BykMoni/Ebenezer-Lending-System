import java.util.*;

public class LendingTracker {
    private Queue<Transaction> transactions;
    private BorrowerManager borrowerManager;

    public LendingTracker(BorrowerManager manager) {
        this.transactions = new LinkedList<>();
        this.borrowerManager = manager;
    }

    // Borrow Book
    public void borrowBook(String bookISBN, String borrowerID, String borrowDate) {
        Borrower borrower = borrowerManager.getBorrower(borrowerID);
        if (borrower == null) {
            System.out.println("Borrower not found.");
            return;
        }

        borrower.addBorrowedBook(bookISBN);
        Transaction transaction = new Transaction(bookISBN, borrowerID, borrowDate);
        transactions.offer(transaction);
        System.out.println("Book borrowed successfully.");
    }

    // Return Book
    public void returnBook(String bookISBN, String borrowerID, String returnDate) {
        Borrower borrower = borrowerManager.getBorrower(borrowerID);
        if (borrower == null) {
            System.out.println("Borrower not found.");
            return;
        }

        borrower.returnBorrowedBook(bookISBN);

        // Mark the earliest matching unreturned transaction
        for (Transaction t : transactions) {
            if (t.getBookISBN().equals(bookISBN) &&
                t.getBorrowerID().equals(borrowerID) &&
                t.getStatus().equals("Borrowed")) {
                t.markAsReturned(returnDate);
                break;
            }
        }

        System.out.println("Book returned successfully.");
    }

    // View All Transactions
    public void listAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}
