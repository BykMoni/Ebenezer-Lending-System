import java.time.LocalDate;
import java.util.*;

public class ReportManager {
    private LendingTracker lendingTracker;
    private BorrowerManager borrowerManager;
    private BookManager bookManager;

    public ReportManager(LendingTracker lendingTracker, BorrowerManager borrowerManager, BookManager bookManager) {
        this.lendingTracker = lendingTracker;
        this.borrowerManager = borrowerManager;
        this.bookManager = bookManager;
    }

    //  1. Most Borrowed Books (Last 30 Days)
    public void mostBorrowedBooksLast30Days() {
        Map<String, Integer> borrowCount = new HashMap<>();
        LocalDate cutoff = LocalDate.now().minusDays(30);

        for (Transaction t : lendingTracker.getAllTransactions()) {
            if (t.getStatus().equals("Borrowed")) {
                LocalDate borrowed = LocalDate.parse(t.getBorrowDate());
                if (!borrowed.isBefore(cutoff)) {
                    borrowCount.put(t.getBookISBN(), borrowCount.getOrDefault(t.getBookISBN(), 0) + 1);
                }
            }
        }

        if (borrowCount.isEmpty()) {
            System.out.println(" No books borrowed in the last 30 days.");
            return;
        }

        System.out.println(" Most Borrowed Books (Last 30 Days):");
        borrowCount.entrySet().stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .forEach(e -> System.out.println("ISBN: " + e.getKey() + " | Times Borrowed: " + e.getValue()));
    }

    //  2. Borrowers with Highest Outstanding Fines
    public void topBorrowersByFines() {
        Collection<Borrower> all = borrowerManager.getAllBorrowers();
        if (all.isEmpty()) {
            System.out.println(" No borrowers found.");
            return;
        }

        System.out.println(" Top Borrowers by Fines:");
        all.stream()
            .filter(b -> b.getFinesOwed() > 0)
            .sorted((a, b) -> Double.compare(b.getFinesOwed(), a.getFinesOwed()))
            .forEach(b -> System.out.println(b.getName() + " (ID: " + b.getId() + ") - Fine: $" + b.getFinesOwed()));
    }

    //  3. Inventory Distribution by Category
    public void inventoryByCategory() {
        Map<String, List<Book>> map = bookManager.getBooksByCategory();
        if (map.isEmpty()) {
            System.out.println(" No books in inventory.");
            return;
        }

        System.out.println(" Inventory by Category:");
        for (Map.Entry<String, List<Book>> entry : map.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue().size() + " book(s)");
        }
    }

    //  4. Performance Summary (Big O and Ω)
    public void showPerformanceAnalysis() {
        System.out.println("\n Performance Analysis:");
        System.out.println("-------------------------------------------------");
        System.out.println(" Linear Search:");
        System.out.println("  - Best Case (Ω): Ω(1) — match found early");
        System.out.println("  - Worst Case (O): O(n) — full scan");

        System.out.println("\n Sorting (Java Built-in Sort - TimSort):");
        System.out.println("  - Best Case (Ω): Ω(n)");
        System.out.println("  - Worst Case (O): O(n log n)");

        System.out.println("\n HashMap Access:");
        System.out.println("  - Best/Worst Case: O(1) average");

        System.out.println("\n Priority Queue (Overdue Tracker):");
        System.out.println("  - Add/Remove: O(log n)");
        System.out.println("-------------------------------------------------\n");
    }
}
