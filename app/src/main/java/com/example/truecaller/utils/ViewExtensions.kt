import android.view.View
import com.example.truecaller.utils.SafeClickListener

fun View.setSafeOnClickListener(interval: Int = 1000, onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener(interval) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}
