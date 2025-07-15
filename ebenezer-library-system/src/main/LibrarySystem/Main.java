import java.util.*;

public class Main {
    static BookManager bookManager = new BookManager();
    static BorrowerManager borrowerManager = new BorrowerManager();
    static TransactionManager transactionManager = new TransactionManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showIntro();
        while (true) {
            showMainMenu();
            int choice = getIntInRange("Enter choice: ", 1, 6);
            switch (choice) {
                case 1 -> showBookManagementMenu();
                case 2 -> showBorrowerManagementMenu();
                case 3 -> showLendingMenu();
                case 4 -> showBookToolsMenu();
                case 5 -> showTransactionMenu();
                case 6 -> {
                    System.out.println("\n👋 Exiting... Goodbye!");
                    return;
                }
            }
        }
    }

    static void showIntro() {
        System.out.println("=====================================");
        System.out.println(" Welcome to the Library System ");
        System.out.println("Manage books, borrowers, and lending");
        System.out.println("=====================================");
    }

    static void showMainMenu() {
        System.out.println("\n=========== MAIN MENU ===========");
        System.out.println("1. Book Management");
        System.out.println("2. Borrower Management");
        System.out.println("3. Book Lending & Returns");
        System.out.println("4. Book Search & Sorting");
        System.out.println("5. Transaction Records");
        System.out.println("6. Exit");
        System.out.println("=================================");
    }

    static void showBookManagementMenu() {
        while (true) {
            System.out.println("\n------ Book Management ------");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List All Books");
            System.out.println("0. Return to Main Menu");
            System.out.println("-----------------------------");

            int choice = getIntInRange("Choose an option: ", 0, 3);
            switch (choice) {
                case 1 -> handleAddBook();
                case 2 -> handleRemoveBook();
                case 3 -> bookManager.listAllBooks();
                case 0 -> {
                    System.out.println(" Returning to Main Menu...");
                    return;
                }
            }
        }
    }

    static void showBorrowerManagementMenu() {
        while (true) {
            System.out.println("\n------ Borrower Management ------");
            System.out.println("1. Add Borrower");
            System.out.println("2. List All Borrowers");
            System.out.println("3. Search Borrower by ID");
            System.out.println("0. Return to Main Menu");
            System.out.println("-------------------------------");

            int choice = getIntInRange("Choose an option: ", 0, 3);
            switch (choice) {
                case 1 -> handleAddBorrower();
                case 2 -> borrowerManager.listAllBorrowers();
                case 3 -> handleSearchBorrower();
                case 0 -> {
                    System.out.println(" Returning to Main Menu...");
                    return;
                }
            }
        }
    }

    static void showLendingMenu() {
        while (true) {
            System.out.println("\n------ Lending & Returns ------");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("0. Return to Main Menu");
            System.out.println("------------------------------");

            int choice = getIntInRange("Choose an option: ", 0, 2);
            switch (choice) {
                case 1 -> handleBorrowBook();
                case 2 -> handleReturnBook();
                case 0 -> {
                    System.out.println(" Returning to Main Menu...");
                    return;
                }
            }
        }
    }

    static void showBookToolsMenu() {
        while (true) {
            System.out.println("\n------ Book Search & Sorting ------");
            System.out.println("1. Search by Title");
            System.out.println("2. Search by Author");
            System.out.println("3. Search by ISBN");
            System.out.println("4. Sort by Title");
            System.out.println("5. Sort by Year");
            System.out.println("0. Return to Main Menu");
            System.out.println("----------------------------------");

            int choice = getIntInRange("Choose an option: ", 0, 5);
            switch (choice) {
                case 1 -> handleSearchBook("title");
                case 2 -> handleSearchBook("author");
                case 3 -> handleSearchBook("isbn");
                case 4 -> handleSortBooks("title");
                case 5 -> handleSortBooks("year");
                case 0 -> {
                    System.out.println(" Returning to Main Menu...");
                    return;
                }
            }
        }
    }

    static void showTransactionMenu() {
        while (true) {
            System.out.println("\n------ Transactions ------");
            System.out.println("1. View All Transactions");
            System.out.println("0. Return to Main Menu");
            System.out.println("------------------------");

            int choice = getIntInRange("Choose an option: ", 0, 1);
            switch (choice) {
                case 1 -> transactionManager.viewAllTransactions();
                case 0 -> {
                    System.out.println(" Returning to Main Menu...");
                    return;
                }
            }
        }
    }

    // HANDLERS BELOW

    static void handleAddBook() {
        String title = getAlphabeticInput("Enter title");
        if (title == null) return;
        String author = getAlphabeticInput("Enter author");
        if (author == null) return;
        String isbn = getNumericInput("Enter ISBN");
        if (isbn == null) return;
        String category = getAlphabeticInput("Enter category");
        if (category == null) return;
        int year = getIntInRange("Enter year: ", 1800, 2100);
        String publisher = getAlphabeticInput("Enter publisher");
        if (publisher == null) return;
        String shelf = getAlphabeticInput("Enter shelf location");
        if (shelf == null) return;

        Book book = new Book(title, author, isbn, category, year, publisher, shelf);

        System.out.println("\n Preview of Book:");
        System.out.println(book);

        if (!confirmAction("Are you sure you want to add this book?")) {
            System.out.println(" Action cancelled.");
            return;
        }

        bookManager.addBook(book);
    }

    static void handleRemoveBook() {
        String isbn = getNumericInput("Enter ISBN of book to remove");
        if (isbn == null) return;

        System.out.println(" You entered ISBN: " + isbn);
        if (!confirmAction("Are you sure you want to remove this book?")) {
            System.out.println(" Action cancelled.");
            return;
        }

        bookManager.removeBookByISBN(isbn);
    }

    static void handleAddBorrower() {
        String name = getAlphabeticInput("Enter borrower's name");
        if (name == null) return;
        String id = getNumericInput("Enter borrower's ID");
        if (id == null) return;
        System.out.print("Enter contact info (or type 0 to cancel): ");
        String contact = scanner.nextLine();
        if (contact.equals("0")) return;

        Borrower b = new Borrower(name, id, contact);

        System.out.println("\n Preview of Borrower:");
        System.out.println(b);

        if (!confirmAction("Are you sure you want to add this borrower?")) {
            System.out.println(" Action cancelled.");
            return;
        }

        borrowerManager.addBorrower(b);
    }

    static void handleSearchBorrower() {
        String id = getNumericInput("Enter borrower ID to search");
        if (id == null) return;
        Borrower found = borrowerManager.searchBorrowerByID(id);
        if (found != null) {
            System.out.println(" Found: " + found + "\n");
        } else {
            System.out.println(" Borrower not found.\n");
        }
    }

    static void handleBorrowBook() {
        String isbn = getNumericInput("Enter ISBN to borrow");
        if (isbn == null) return;
        String borrowerId = getNumericInput("Enter Borrower ID");
        if (borrowerId == null) return;

        System.out.println("\n Borrowing Preview:");
        System.out.println("ISBN: " + isbn + " | Borrower ID: " + borrowerId);

        if (!confirmAction("Confirm borrow action?")) {
            System.out.println(" Borrow cancelled.");
            return;
        }

        transactionManager.borrowBook(isbn, borrowerId);
    }

    static void handleReturnBook() {
        String isbn = getNumericInput("Enter ISBN to return");
        if (isbn == null) return;
        String borrowerId = getNumericInput("Enter Borrower ID");
        if (borrowerId == null) return;

        System.out.println("\n Return Preview:");
        System.out.println("ISBN: " + isbn + " | Borrower ID: " + borrowerId);

        if (!confirmAction("Confirm return action?")) {
            System.out.println(" Return cancelled.");
            return;
        }

        transactionManager.returnBook(isbn, borrowerId);
    }

    static void handleSearchBook(String type) {
        System.out.print("Enter search keyword (or 0 to cancel): ");
        String term = scanner.nextLine();
        if (term.equals("0")) return;

        List<Book> results = switch (type) {
            case "title" -> bookManager.searchBooksByTitle(term);
            case "author" -> bookManager.searchBooksByAuthor(term);
            case "isbn" -> bookManager.searchBooksByISBN(term);
            default -> new ArrayList<>();
        };

        if (results.isEmpty()) {
            System.out.println(" No books found.");
        } else {
            System.out.println(" Search Results:");
            for (Book b : results) {
                System.out.println(" - " + b);
            }
        }
    }

    static void handleSortBooks(String type) {
        List<Book> books = bookManager.getAllBooksFlatList();
        if (books.isEmpty()) {
            System.out.println(" No books available to sort.");
            return;
        }

        if (type.equals("title")) {
            bookManager.sortBooksByTitle(books);
            System.out.println(" Books sorted by Title:");
        } else {
            bookManager.sortBooksByYear(books);
            System.out.println(" Books sorted by Year:");
        }

        for (Book b : books) {
            System.out.println(" - " + b);
        }
    }

    // VALIDATORS & CONFIRMATION
    static boolean confirmAction(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("y")) return true;
            if (input.equalsIgnoreCase("n")) return false;
            System.out.println(" Invalid input. Please type Y or N only.");
        }
    }

    static int getIntInRange(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int val = Integer.parseInt(scanner.nextLine());
                if (val >= min && val <= max) return val;
                System.out.println(" Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println(" Invalid number. Try again.");
            }
        }
    }

    static String getAlphabeticInput(String message) {
        while (true) {
            System.out.print(message + " (or type 0 to cancel): ");
            String input = scanner.nextLine();
            if (input.equals("0")) return null;
            if (input.matches("[a-zA-Z ]+")) return input;
            System.out.println(" Invalid input. Only letters and spaces allowed.");
        }
    }

    static String getNumericInput(String message) {
        while (true) {
            System.out.print(message + " (or type 0 to cancel): ");
            String input = scanner.nextLine();
            if (input.equals("0")) return null;
            if (input.matches("\\d+")) return input;
            System.out.println(" Invalid input. Only digits allowed.");
        }
    }
}
// Note: The Book, Borrower, BookManager, BorrowerManager, and TransactionManager classes