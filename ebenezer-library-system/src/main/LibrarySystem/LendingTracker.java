import java.time.LocalDate;
import java.util.*;

public class LendingTracker {
    private Queue<Transaction> transactions;
    private PriorityQueue<Transaction> dueQueue;
    private BorrowerManager borrowerManager;

    public LendingTracker(BorrowerManager manager) {
        this.transactions = new LinkedList<>();
        this.borrowerManager = manager;

        // PriorityQueue sorted by due date (earliest due date has highest priority)
        this.dueQueue = new PriorityQueue<>(Comparator.comparing(Transaction::getDueDate));
    }

    public void borrowBook(String bookISBN, String borrowerID, String borrowDateStr) {
        Borrower borrower = borrowerManager.getBorrower(borrowerID);
        if (borrower == null) {
            System.out.println("Borrower not found.");
            return;
        }

        LocalDate borrowDate = LocalDate.parse(borrowDateStr);
        LocalDate dueDate = borrowDate.plusDays(14); // Standard 14-day return policy

        Transaction transaction = new Transaction(bookISBN, borrowerID, borrowDateStr, dueDate);
        borrower.addBorrowedBook(bookISBN);
        transactions.offer(transaction);
        dueQueue.offer(transaction);

        System.out.println("Book borrowed successfully. Due Date: " + dueDate);
    }

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

                // Calculate overdue days and apply fine if applicable
                LocalDate returnDateParsed = LocalDate.parse(returnDate);
                long daysLate = t.getDueDate().until(returnDateParsed).getDays();
                if (daysLate > 0) {
                    double fine = daysLate * 5.0; // $5 per day overdue
                    borrower.addFine(fine);
                }
                break;
            }
        }

        System.out.println("Book returned successfully.");
    }

    // List and flag overdue books and update borrower fines if over 14 days overdue
    public void listOverdueBooks() {
        LocalDate today = LocalDate.now();
        List<Transaction> tempList = new ArrayList<>();
        boolean found = false;

        System.out.println("Overdue Books as of " + today + ":");

        while (!dueQueue.isEmpty()) {
            Transaction t = dueQueue.peek();
            if (t.getDueDate().isBefore(today) || t.getStatus().equals("Borrowed")) {
                long daysLate = t.getDueDate().until(today).getDays();
                if (daysLate > 0) {
                    t.setStatus("Overdue");
                    found = true;
                    Borrower b = borrowerManager.getBorrower(t.getBorrowerID());
                    double fine = daysLate * 5.0; // $5 fine per overdue day
                    b.addFine(fine);
                    System.out.println("Overdue: " + t + " | Days Late: " + daysLate + " | Fine: $" + fine);
                }
                tempList.add(dueQueue.poll());
            } else {
                break; // stop at first non-overdue item
            }
        }

        dueQueue.addAll(tempList);

        if (!found) {
            System.out.println("No overdue books.");
        }
    }

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