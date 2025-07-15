import java.util.*;

public class TransactionManager {
    private Queue<Transaction> borrowQueue;
    private Stack<Transaction> returnStack;
    private List<Transaction> allTransactions;

    public TransactionManager() {
        borrowQueue = new LinkedList<>();
        returnStack = new Stack<>();
        allTransactions = new ArrayList<>();
    }

    public void borrowBook(String isbn, String borrowerId) {
        Transaction t = new Transaction(isbn, borrowerId);
        borrowQueue.add(t);
        allTransactions.add(t);
        System.out.println(" Book borrowed successfully.\n");
    }

    public void returnBook(String isbn, String borrowerId) {
        for (Transaction t : allTransactions) {
            if (t.getIsbn().equals(isbn) && t.getBorrowerId().equals(borrowerId) && t.getStatus().equals("Borrowed")) {
                t.markReturned();
                returnStack.push(t);
                System.out.println(" Book returned successfully.\n");
                return;
            }
        }
        System.out.println(" No matching borrow record found.\n");
    }

    public void viewAllTransactions() {
        if (allTransactions.isEmpty()) {
            System.out.println(" No transactions available.\n");
            return;
        }
        for (Transaction t : allTransactions) {
            System.out.println(t);
        }
        System.out.println();
    }
}
