package com.paulazaidel.keywallet.Extensions

import android.os.Build
import java.util.*

fun String.encrypt(): String {
    val encrypt = this.toByteArray()

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getEncoder().encodeToString(encrypt)
    } else {
        android.util.Base64.encodeToString(encrypt, android.util.Base64.DEFAULT)
    }
}

fun String.decrypt(): String {
    val base = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getDecoder().decode(this)
    } else {
        android.util.Base64.decode(this, android.util.Base64.DEFAULT)
    }
    return String(base, Charsets.UTF_8)
}