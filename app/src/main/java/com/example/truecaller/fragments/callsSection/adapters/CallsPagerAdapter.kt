import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.truecaller.fragments.callsSection.AllFragment
import com.example.truecaller.fragments.callsSection.PrimaryFragment
import com.example.truecaller.fragments.callsSection.PromoFragment
import com.example.truecaller.fragments.callsSection.SpamFragment

class CallsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PrimaryFragment()
            1 -> AllFragment()
            2 -> PromoFragment()
            3 -> SpamFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}