package com.example.model

data class DemoStage(
    val id: String,
    val timeRange: String,
    val title: String,
    val speakerRole: String,
    val keyPoints: List<String>,
    val actionLabel: String? = null,
    val actionType: DemoActionType? = null
)

enum class DemoActionType {
    TRIGGER_EMPTY_STATE,
    TRIGGER_REJECTED_FORM,
    TRIGGER_BUDGET_WARNING,
    RESTORE_SAMPLE_DATA,
    VIEW_GITHUB_INFO
}

object DemoScript {
    val stages = listOf(
        DemoStage(
            id = "intro",
            timeRange = "0:00 - 0:30",
            title = "Target Audience & Core Problem",
            speakerRole = "Speaker 1 (Product & Problem Statement)",
            keyPoints = listOf(
                "Target Audience: College students, gig freelancers, and young professionals managing dynamic monthly cash flows.",
                "The Problem: Traditional banking apps are slow and lack real-time budget guardrails. Users overspend without realizing until the end of the month.",
                "Our Solution: SpendWise — an instant-entry, local-first expense tracker with proactive threshold budget warnings and visual analytics."
            )
        ),
        DemoStage(
            id = "live_demo",
            timeRange = "0:30 - 3:30",
            title = "Primary Phone Flow & Intentional Edge Cases",
            speakerRole = "Speaker 2 & 3 (Live App Walkthrough)",
            keyPoints = listOf(
                "1. Empty State Showcase: Demonstrating user onboarding with guidance when no transactions exist.",
                "2. Rejected Form Entry: Attempting to submit invalid $0.00 or negative amounts and empty notes to demonstrate robust input validation and defensive state checks.",
                "3. Error Banner & Inline Feedback: Clear visual cues explaining why corrupt data was rejected.",
                "4. Successful Entry: Adding valid Expense ($24.50 Lunch) and Income ($200 Freelance) with immediate reactive dashboard balance updates.",
                "5. Budget Alert: Pacing bar changes color dynamically (Green -> Amber warning at >80% -> Coral alert at >100%)."
            ),
            actionLabel = "Interactive Demo Controls Available",
            actionType = null
        ),
        DemoStage(
            id = "tech_challenge",
            timeRange = "3:30 - 4:30",
            title = "Technical Challenge Faced & Engineering Solution",
            speakerRole = "Speaker 4 (Architecture & Tech Deep-Dive)",
            keyPoints = listOf(
                "Challenge: Preventing asynchronous race conditions and UI jank between dynamic multi-field form validation and immediate Room SQLite database queries.",
                "Solution: Implemented an immutable MVI-style TransactionFormState combined with Jetpack Compose StateFlow and Kotlin Coroutines.",
                "Key Benefit: The UI observes a Single Source of Truth; invalid submissions are rejected in-memory before reaching SQLite, keeping database integrity 100% clean."
            )
        ),
        DemoStage(
            id = "git_and_apk",
            timeRange = "4:30 - 5:00",
            title = "Git Commit History & APK Packaging",
            speakerRole = "Speaker 5 / All Team Members (Wrap-Up)",
            keyPoints = listOf(
                "Git Repository: Structured feature branch commits ('feat: room database', 'feat: validation engine', 'feat: m3 UI & charts').",
                "Packaging: Android Gradle Plugin 8.9, Kotlin 2.0 Compose, compileSdk 36, minSdk 24 (compatible with 95%+ of Android devices).",
                "Release Artifact: Self-contained APK compiled and verified without external crash dependencies."
            ),
            actionLabel = "Inspect Build & Repo Details",
            actionType = DemoActionType.VIEW_GITHUB_INFO
        )
    )
}
