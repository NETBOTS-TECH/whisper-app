import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.truecaller.fragments.callsSection.subFragments.AllFragment
import com.example.truecaller.fragments.callsSection.subFragments.PrimaryFragment
import com.example.truecaller.fragments.callsSection.subFragments.PromoFragment
import com.example.truecaller.fragments.callsSection.subFragments.SpamFragment

class CallsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

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