import java.time.LocalDate;
import java.util.*;

public class LendingTracker {
    private Queue<Transaction> transactions;
    private PriorityQueue<Transaction> dueQueue;
    private BorrowerManager borrowerManager;

    public LendingTracker(BorrowerManager manager) {
        this.transactions = new LinkedList<>();
        this.borrowerManager = manager;
        this.dueQueue = new PriorityQueue<>(Comparator.comparing(Transaction::getDueDate));
    }

    // Borrow Book
    public void borrowBook(String bookISBN, String borrowerID, String borrowDateStr) {
        Borrower borrower = borrowerManager.getBorrower(borrowerID);
        if (borrower == null) {
            System.out.println("Borrower not found.");
            return;
        }

        LocalDate borrowDate = LocalDate.parse(borrowDateStr);
        LocalDate dueDate = borrowDate.plusDays(14);

        borrower.addBorrowedBook(bookISBN);
        Transaction transaction = new Transaction(bookISBN, borrowerID, borrowDateStr, dueDate);
        transactions.offer(transaction);
        dueQueue.offer(transaction);

        saveToFile(); // 🔹 Save
        System.out.println("Book borrowed successfully. Due Date: " + dueDate);
    }

    // Return Book
    public void returnBook(String bookISBN, String borrowerID, String returnDate) {
        Borrower borrower = borrowerManager.getBorrower(borrowerID);
        if (borrower == null) {
            System.out.println("Borrower not found.");
            return;
        }

        borrower.returnBorrowedBook(bookISBN);

        for (Transaction t : transactions) {
            if (t.getBookISBN().equals(bookISBN) &&
                    t.getBorrowerID().equals(borrowerID) &&
                    t.getStatus().equals("Borrowed")) {

                t.markAsReturned(returnDate);

                // Check for overdue
                LocalDate returnDateParsed = LocalDate.parse(returnDate);
                long daysLate = t.getDueDate().until(returnDateParsed).getDays();
                if (daysLate > 0) {
                    double fine = daysLate * 5.0;
                    borrower.addFine(fine);
                }
                break;
            }
        }

        saveToFile(); // 🔹 Save
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

    // Overdue Check
    public void listOverdueBooks() {
        LocalDate today = LocalDate.now();
        List<Transaction> tempList = new ArrayList<>();
        boolean found = false;

        System.out.println("Overdue Books as of " + today + ":");

        while (!dueQueue.isEmpty()) {
            Transaction t = dueQueue.peek();
            if ((t.getDueDate().isBefore(today) || t.getDueDate().isEqual(today)) && t.getStatus().equals("Borrowed")) {
                long daysLate = t.getDueDate().until(today).getDays();
                if (daysLate > 0) {
                    t.setStatus("Overdue");
                    found = true;
                    Borrower b = borrowerManager.getBorrower(t.getBorrowerID());
                    double fine = daysLate * 5.0;
                    b.addFine(fine);
                    System.out.println("Overdue: " + t + " | Days Late: " + daysLate + " | Fine: $" + fine);
                }
                tempList.add(dueQueue.poll());
            } else {
                break;
            }
        }

        dueQueue.addAll(tempList);

        if (!found) {
            System.out.println("No overdue books.");
        }
    }

    // File Operations
    public void loadFromFile() {
        List<Transaction> list = FileHandler.loadTransactions();
        transactions.addAll(list);
        dueQueue.addAll(list);
    }

    public void saveToFile() {
        FileHandler.saveTransactions(new ArrayList<>(transactions));
    }

    public List<Transaction> getAllTransactions() {
    return new ArrayList<>(transactions);
}

}
