import java.time.LocalDate;

public class Transaction {
    private String bookISBN;
    private String borrowerID;
    private String borrowDate;
    private String returnDate;
    private LocalDate dueDate;
    private String status; // "Borrowed" or "Returned"

    public Transaction(String bookISBN, String borrowerID, String borrowDate, LocalDate dueDate) {
        this.bookISBN = bookISBN;
        this.borrowerID = borrowerID;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = "Borrowed";
        this.returnDate = "N/A";
    }

    public void markAsReturned(String returnDate) {
        this.returnDate = returnDate;
        this.status = "Returned";
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public String getBorrowerID() {
        return borrowerID;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String toString() {
        return "ISBN: " + bookISBN +
                " | Borrower ID: " + borrowerID +
                " | Borrow Date: " + borrowDate +
                " | Due Date: " + dueDate +
                " | Return Date: " + returnDate +
                " | Status: " + status;
    }

        public String getBorrowDate() {
    return borrowDate;
    }   

}
