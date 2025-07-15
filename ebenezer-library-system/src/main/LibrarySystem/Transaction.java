public class Transaction {
    private String bookISBN;
    private String borrowerID;
    private String borrowDate;
    private String returnDate;
    private String status; // "Borrowed" or "Returned"

    public Transaction(String bookISBN, String borrowerID, String borrowDate) {
        this.bookISBN = bookISBN;
        this.borrowerID = borrowerID;
        this.borrowDate = borrowDate;
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

    public String toString() {
        return "ISBN: " + bookISBN +
               " | Borrower ID: " + borrowerID +
               " | Borrow Date: " + borrowDate +
               " | Return Date: " + returnDate +
               " | Status: " + status;
    }
}
