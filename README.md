# DSA Novahive Attendance & Project Tracking

## 🧑‍🤝‍🧑 Team Attendance

| Names     | Tasks (Requirements) | 1st Meeting (5th July, 2025) | 2nd Meeting (7th July, 2025) | 3rd Meeting (9th July, 2025) | 4th Meeting (11th July, 2025) | 5th Meeting (13th July, 2025) |
|-----------|----------------------|------------------------------|------------------------------|------------------------------|-------------------------------|-------------------------------|
| **Daniel** (Leader) | 5 | ✅ | ✅ | ✅ |  |  |
| Elsie     | 7                    | ✅ | ✅ | ✅ |  |  |
| Anas      | 3                    | ✅ | ✅ | ✅ |  |  |
| Marie     | 4                    | ✅ | ✅ | ✅ |  |  |
| Joel      | 6                    | ✅ | ✅ | ✅ |  |  |
| Sefakor   | 1                    | ✅ | ✅ | ✅ |  |  |
| Selasi    | 2                    | ✅ | ✅ | ✅ |  |  |

---

## 📘 Project Requirements Breakdown

### 1. 📚 Book Inventory
- Add, remove, and list books. Each book should store:
  - **Title**, **Author**, **ISBN**, **Category**, **Year**, **Publisher**, **Shelf Location**
- Use a **hash map** or **tree** to group books by category and allow filtering.

---

### 2. 🔍 Book Search & Sorting
- Implement:
  - **Binary** or **linear search** to locate a book by title, author, or ISBN.
  - **Custom sorting** (e.g., selection sort, merge sort) for alphabetizing book titles or sorting by publication date.

---

### 3. 🧑‍💼 Borrower Registry
- Create a **nested dictionary** or **tree** to manage borrowers:
  - Name, ID number, borrowed books, fines owed, contact info.
- Implement **recursive search** by borrower ID.

---

### 4. 🔄 Lending Tracker
- Use **queues** or **stacks** to manage borrowing and returning of books.
- Each transaction should record:
  - Book ISBN, borrower’s ID, borrow date, return date, and status.

---

### 5. ⏰ Overdue Monitoring
- Use a **priority queue** or **min-heap** to track overdue books based on return deadlines.
- Automatically flag books overdue by more than **14 days** and update borrower fines.

---

### 6. 🗃️ File Logging
- Store all data changes and transaction logs in files:
  - `books.txt`, `borrowers.txt`, `transactions.txt`, etc.
- Enable **reloading from files** after shutdown.

---

### 7. 📈 Reports & Data Analysis
- Generate reports such as:
  - Most borrowed books in the past month.
  - Borrowers with highest outstanding fines.
  - Inventory distribution across categories.
- Provide a brief **performance analysis** using **Big O** and **Omega Notation**.
