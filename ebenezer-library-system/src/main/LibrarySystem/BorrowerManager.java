import java.util.*;

public class BorrowerManager {
    private Map<String, Borrower> borrowerMap;

    public BorrowerManager() {
        borrowerMap = new HashMap<>();
    }

    //  Add Borrower
    public void addBorrower(Borrower borrower) {
        if (borrowerMap.containsKey(borrower.getId())) {
            System.out.println(" Borrower with ID " + borrower.getId() + " already exists.");
            return;
        }
        borrowerMap.put(borrower.getId(), borrower);
        saveToFile(); // Save after adding
        System.out.println(" Borrower added successfully.\n");
    }

    // List All Borrowers
    public void listAllBorrowers() {
        if (borrowerMap.isEmpty()) {
            System.out.println(" No borrowers found.\n");
            return;
        }
        for (Borrower b : borrowerMap.values()) {
            System.out.println(b);
        }
        System.out.println();
    }

    //  Public Recursive Search Caller
    public Borrower searchBorrowerByID(String id) {
        List<String> keys = new ArrayList<>(borrowerMap.keySet());
        return recursiveSearch(keys, id, 0);
    }

    //  Recursive Search Implementation
    private Borrower recursiveSearch(List<String> keys, String id, int index) {
        if (index >= keys.size()) return null;
        if (keys.get(index).equals(id)) return borrowerMap.get(id);
        return recursiveSearch(keys, id, index + 1);
    }

    //  Extra: Get borrower (direct access if needed)
    public Borrower getBorrower(String id) {
        return borrowerMap.get(id);
    }

    // Load from file
    public void loadFromFile() {
        List<Borrower> list = FileHandler.loadBorrowers();
        for (Borrower b : list) {
            borrowerMap.put(b.getId(), b);
        }
    }

    // Save to file
    public void saveToFile() {
        FileHandler.saveBorrowers(new ArrayList<>(borrowerMap.values()));
    }

    public Collection<Borrower> getAllBorrowers() {
    return borrowerMap.values();
    }


}
