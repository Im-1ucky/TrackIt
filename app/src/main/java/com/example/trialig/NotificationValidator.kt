package com.example.trialig

object NotificationValidator {

    private val trustedPackages = listOf(

        "com.phonepe.app",

        "com.google.android.apps.messaging",

        "com.google.android.apps.nbu.paisa.user",

        "net.one97.paytm",

        "com.axis.mobile",

        "com.csam.icici.bank.imobile",

        "com.sbi.lotusintouch",

        "com.snapwork.hdfc"
    )

    private val suspiciousPackages = listOf(

        "com.whatsapp",

        "com.instagram.android",

        "com.facebook.katana"
    )

    private val bankIndicators = listOf(

        "hdfc",

        "sbi",

        "state bank",

        "icici",

        "axis",

        "kotak",

        "unionb",

        "union bank",

        "union bank of india",

        "phonepe",

        "google pay",

        "gpay",

        "paytm"
    )

    private val transactionWords = listOf(

        "debited",

        "credited",

        "received",

        "spent",

        "paid",

        "sent",

        "withdrawn"
    )

    private val suspiciousWords = listOf(

        "cashback",

        "offer",

        "reward",

        "win",

        "voucher"
    )

    fun isValid(
        packageName: String,
        title: String,
        message: String
    ): Boolean {

        val lowerTitle = title.lowercase()

        val lowerMessage = message.lowercase()

        var score = 0

        if (
            trustedPackages.any {
                it == packageName
            }
        ) {
            score += 5
        }

        if (
            suspiciousPackages.any {
                it == packageName
            }
        ) {
            score -= 5
        }

        val hasBankIndicator =
            bankIndicators.any {
                it in lowerTitle
            }

        if (hasBankIndicator) {
            score += 3
        }

        val hasTransactionWord =
            transactionWords.any {
                it in lowerMessage
            }

        if (hasTransactionWord) {
            score += 5
        }

        val hasAmount =
            Regex("""\d+""")
                .containsMatchIn(lowerMessage)

        if (hasAmount) {
            score += 3
        }

        val hasSuspiciousWord =
            suspiciousWords.any {
                it in lowerMessage
            }

        if (hasSuspiciousWord) {
            score -= 4
        }

        return score >= 5
    }
}