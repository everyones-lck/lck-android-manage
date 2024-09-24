package umc.everyones.everyoneslckmanage.util.extension

import android.app.Activity
import android.content.Intent

inline fun <reified T : Activity> Activity.navigateToScreen(
    flags: List<Int>? = null,
    isFinish: Boolean = true,
) {
    Intent(this, T::class.java).apply {
        flags?.map {
            addFlags(it)
        }
        startActivity(this)
    }
    if (isFinish) {
        finish()
    }
}

inline fun <reified T : Activity> Activity.navigateToScreenClear() {
    Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(this)
    }
    finish()
}