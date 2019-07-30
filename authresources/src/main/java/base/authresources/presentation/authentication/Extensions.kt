package base.authresources.presentation.authentication

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import base.authresources.R

fun AuthenticationActivity.loadFragment(frag: Fragment) {
    supportFragmentManager.commit {
        setCustomAnimations(
                R.anim.activity_transition_enter,
                R.anim.activity_transition_exit,
                R.anim.activity_transition_enter,
                R.anim.activity_transition_exit
        )
        replace(R.id.layout_main_content, frag)
    }
}