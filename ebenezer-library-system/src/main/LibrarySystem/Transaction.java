import java.time.LocalDate;

public class Transaction {
    private String isbn;
    private String borrowerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status;

    public Transaction(String isbn, String borrowerId) {
        this.isbn = isbn;
        this.borrowerId = borrowerId;
        this.borrowDate = LocalDate.now();
        this.status = "Borrowed";
    }

    public void markReturned() {
        this.returnDate = LocalDate.now();
        this.status = "Returned";
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public String getStatus() {
        return status;
    }

    public String toString() {
        return "ISBN: " + isbn + " | Borrower ID: " + borrowerId +
               " | Borrowed: " + borrowDate +
               (status.equals("Returned") ? " | Returned: " + returnDate : "") +
               " | Status: " + status;
    }
}
