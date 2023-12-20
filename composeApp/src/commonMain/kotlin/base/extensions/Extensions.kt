@file:OptIn(ExperimentalResourceApi::class)

package base.extensions

import dev.icerock.moko.resources.AssetResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

fun String.isNameOrUserNameValid(
    onError : ()->Unit
): Boolean {
    if (this == "") {
        onError()
        return false
    }
    return true
}




fun String.isEmailValid(onError: () -> Unit): Boolean {
    if (this.isEmpty()) {
        onError()
        return false
    }

    val parts = this.split('@')

    if (parts.size != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
        onError()
        return false
    }

    val domainParts = parts[1].split('.')

    if (domainParts.size < 2 || domainParts.any { it.isEmpty() }) {
        onError()
        return false
    }

    return true
}

fun String.isPasswordKonfirmValid(
    confirm : String,onError : ()->Unit
): Boolean {
    if (this != confirm || this.isEmpty()) {
        onError()
        return false
    }
    return true
}

fun String.isOtpValid(
    onError: () -> Unit
): Boolean {
    if(this.length != 6 && this.toIntOrNull() != null){
        onError()
        return false
    }
    return true
}

fun String.isPasswordValid(
    onError : ()->Unit
): Boolean {
    if (this.length < 6) {
        onError()
        return false
    }
    return true
}

fun String.isNomorTeleponValid(
    onError: () -> Unit
): Boolean {
    if (this.length < 7 && this.toDoubleOrNull() != null) {
        onError()
        return false
    }
    return true
}





