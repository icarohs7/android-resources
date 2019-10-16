package base.corextresources.presentation.main

import android.os.Bundle
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import base.coreresources.presentation.activities.BaseArchActivity
import base.coreresources.state.addOnLoadingListener
import base.corextresources.R

abstract class BaseFragholderMainActivity : BaseArchActivity() {
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }
    val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragholder_main)
        lifecycleScope.addOnLoadingListener { isLoading -> progressBar.isVisible = isLoading }
    }
}