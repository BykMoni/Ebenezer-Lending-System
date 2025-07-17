import java.util.*;

public class BookManager {
    private HashMap<String, List<Book>> booksByCategory;

    public BookManager() {
        booksByCategory = new HashMap<>();
    }

    public void addBook(Book book) {
        booksByCategory.putIfAbsent(book.getCategory(), new ArrayList<>());
        booksByCategory.get(book.getCategory()).add(book);
        saveToFile(); // Auto-save
        System.out.println(" Book added successfully.\n");
    }

    public void listAllBooks() {
        if (booksByCategory.isEmpty()) {
            System.out.println(" No books in inventory.\n");
            return;
        }
        for (String category : booksByCategory.keySet()) {
            System.out.println("Category: " + category);
            for (Book book : booksByCategory.get(category)) {
                System.out.println(" - " + book);
            }
        }
        System.out.println();
    }

    public void removeBookByISBN(String isbn) {
        for (String category : booksByCategory.keySet()) {
            Iterator<Book> iterator = booksByCategory.get(category).iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (book.getIsbn().equals(isbn)) {
                    iterator.remove();
                    saveToFile(); // Save after removal
                    System.out.println(" Book removed successfully.\n");
                    return;
                }
            }
        }
        System.out.println(" Book with ISBN " + isbn + " not found.\n");
    }

    //  Return a flat list of all books
    public List<Book> getAllBooksFlatList() {
        List<Book> allBooks = new ArrayList<>();
        for (List<Book> books : booksByCategory.values()) {
            allBooks.addAll(books);
        }
        return allBooks;
    }

    //  Search by Title
    public List<Book> searchBooksByTitle(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : getAllBooksFlatList()) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    //  Search by Author
    public List<Book> searchBooksByAuthor(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : getAllBooksFlatList()) {
            if (book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    //  Search by ISBN
    public List<Book> searchBooksByISBN(String isbn) {
        List<Book> result = new ArrayList<>();
        for (Book book : getAllBooksFlatList()) {
            if (book.getIsbn().equals(isbn)) {
                result.add(book);
            }
        }
        return result;
    }

    //  Sort by Title
    public void sortBooksByTitle(List<Book> books) {
        books.sort(Comparator.comparing(Book::getTitle));
    }

    // Sort by Year
    public void sortBooksByYear(List<Book> books) {
        books.sort(Comparator.comparingInt(Book::getYear));
    }

    // Load books from file into the map
    public void loadFromFile() {
        List<Book> list = FileHandler.loadBooks();
        for (Book b : list) {
            addBookWithoutPrint(b); // Internal add to avoid duplicate messages
        }
    }

    // Save books to file
    public void saveToFile() {
        List<Book> all = getAllBooksFlatList();
        FileHandler.saveBooks(all);
    }

    // Internal helper to add without printing success
    private void addBookWithoutPrint(Book book) {
        booksByCategory.putIfAbsent(book.getCategory(), new ArrayList<>());
        booksByCategory.get(book.getCategory()).add(book);
    }

}
