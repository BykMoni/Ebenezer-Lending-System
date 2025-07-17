public class Book {
    private String title;
    private String author;
    private String isbn;
    private String category;
    private int year;
    private String publisher;
    private String shelfLocation;

    public Book(String title, String author, String isbn, String category, int year, String publisher, String shelfLocation) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.year = year;
        this.publisher = publisher;
        this.shelfLocation = shelfLocation;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCategory() {
        return category;
    }
    public String getTitle() {
    return title;
}

public String getAuthor() {
    return author;
}

public int getYear() {
    return year;
}


    public String toString() {
        return "Title: " + title + " | Author: " + author + " | ISBN: " + isbn +
               " | Category: " + category + " | Year: " + year + " | Publisher: " + publisher +
               " | Shelf: " + shelfLocation;
    }

    public String getPublisher() { return publisher;
    }
}
