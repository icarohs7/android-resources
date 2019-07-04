package base.authresources.presentation.authentication

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import base.authresources.R
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch

fun AuthenticationActivity.loadFragment(frag: Fragment) {
    supportFragmentManager.commit {
        val animation = UnoxAndroidArch.AnimationType.INOUT
        setCustomAnimations(
                animation.enterRes,
                animation.exitRes,
                animation.enterRes,
                animation.exitRes
        )
        replace(R.id.layout_main_content, frag)
    }
}