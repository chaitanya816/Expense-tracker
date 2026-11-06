# SpendWise — Personal Expense & Budget Tracker

An Android application built with Jetpack Compose, Material 3, and Room SQLite database, tailored for real-time personal finance management and designed according to the **5.1 Demonstration format**.

---

## 📱 Features

- **Real-Time Financial Dashboard**: Instant Net Balance, Total Income, and Total Expense calculations with zero latency.
- **Dynamic Monthly Budget Pacing**:
  - 🟢 **On Track** (< 80% used)
  - 🟡 **Warning Alert** (80% – 100% used)
  - 🔴 **Over Budget Alert** (> 100% used)
  - Dynamic budget editing dialog to adjust monthly spending limits.
- **Defensive Form Validation & Rejected Entry Feedback**:
  - Supports both **Expense** and **Income** entries.
  - Multi-tier validation: Rejects zero ($0.00) or negative amounts, non-numeric values, and blank descriptions.
  - Animated `RejectedEntryBanner` providing clear visual reasons why corrupt records are blocked before reaching SQLite.
- **Interactive 5.1 Demonstration Companion**:
  - Live 5-minute stopwatch with pause and reset.
  - 1-tap live triggers: **Show Empty State**, **Show Rejected Form**, **Simulate Budget Alert**, and **Reload Sample Data**.
  - Speaker transition cues for every team member.
- **Search & Filtering**:
  - Search transactions by merchant, note, or payment method.
  - Filter by All, Expenses Only, or Income Only.
  - Filter by individual spending categories.
- **Analytics & Financial Health**:
  - Financial Health Score (0–100) based on savings rate and budget compliance.
  - Cash flow ratio comparison bar.
  - Category breakdown with visual percentages.
  - Payment channel distribution (Cash, Credit Card, Debit Card, UPI/Bank Transfer, Wallet).

---

## ⏱️ 5.1 Demonstration Format Breakdown

| Time Window | Demonstration Segment | Key Presentation Points |
| :--- | :--- | :--- |
| **0:00 – 0:30** | **Audience & Core Problem** | Target audience: College students & young professionals. Solves end-of-month financial deficit and tracking friction. |
| **0:30 – 3:30** | **Primary Flow Live on Phone** | 1. Empty state showcase.<br>2. Intentional rejected form entry ($0.00 / blank title).<br>3. Successful expense ($24.50) & income ($200) entries.<br>4. Dynamic budget pacing color shift.<br>5. Search & filter. |
| **3:30 – 4:30** | **Technical Challenge & Solution** | **Challenge**: Multi-field form validation vs. asynchronous Room SQLite queries without race conditions.<br>**Solution**: Immutable MVI `TransactionFormState` with Jetpack Compose `StateFlow` and Kotlin Coroutines. |
| **4:30 – 5:00** | **Git History & APK Specs** | Review of commit history, architecture, and production APK packaging. |

---

## 🛠️ Tech Stack & Architecture

- **Language**: Kotlin 2.0
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Local Persistence**: Room SQLite 2.7 with Kotlin Symbol Processing (KSP)
- **Architecture**: MVVM / Single Source of Truth with Repository pattern
- **Asynchronous Flow**: Kotlin Coroutines & `StateFlow`
- **Minimum SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 16 (API 36)

---

## 🚀 Building & Running Locally

1. Clone this repository:
   ```bash
   git clone https://github.com/ck607639-ai/Expense-tracker.git
   cd Expense-tracker
   ```
2. Open the project in **Android Studio**.
3. Build and run on an Android device or emulator (API 24+):
   ```bash
   ./gradlew installDebug
   ```
4. Run automated tests:
   ```bash
   ./gradlew testDebugUnitTest
   ```
