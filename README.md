# PaymentGitHistory

> A local-first Android personal finance tracker that models your financial history like a Git repository.

PaymentGitHistory turns **transactions into nodes, balances into branches, loans into feature branches, and repayments into merges**.

## ✨ Features

* 🌳 Git-style visual transaction history
* 💰 Credit, debit & running balance tracking
* 🤝 Loan tracking with partial repayments
* 🗂️ Multiple workspaces for trips, events, etc.
* 📱 Bank SMS & notification parsing
* 🏷️ Transaction tagging and categorization
* 📊 Daily, weekly & monthly summaries
* 📄 PDF export/import with embedded backup data
* 🔒 Local-first — no backend required

## 🛠️ Tech Stack

| Component     | Technology                  |
| ------------- | --------------------------- |
| Language      | Kotlin                      |
| UI            | Jetpack Compose             |
| Database      | Room / SQLite               |
| Notifications | NotificationListenerService |
| PDF           | iText                       |          |
| Architecture  | MVVM                        |
| Pagination    | Paging 3                    |

## 🌳 Core Concept

```text
                Financial History
                       │
             ┌─────────┴─────────┐
             │                   │
        Main Balance          Spending
             │                   │
        Transactions        Transactions
             │
          ┌──┴──┐
          │     │
        Loans  Normal
          │
      Repayments
          │
        Merge
```

The app treats financial history similarly to version control:

* **Transaction** → Commit
* **Balance** → Main branch
* **Loan** → Feature branch
* **Repayment** → Merge
* **Workspace** → Repository

## 🔐 Data & Privacy

PaymentGitHistory is designed as a **local first application**.

Financial data is stored locally using Room.

## 🚧 Status

**Early development / architecture phase**

## 💡 Why?

Most finance apps show your transactions as a flat list.

**PaymentGitHistory experiments with a different idea:**

> Your financial history is a version-control system.

Instead of simply asking *"Where did my money go?"*, the app aims to let you **see how your financial state evolved over time**.
