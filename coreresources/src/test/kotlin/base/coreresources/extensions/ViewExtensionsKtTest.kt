package base.coreresources.extensions

import android.os.Build
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.contains
import androidx.core.view.plusAssign
import base.coreresources.testutils.TestActivity
import base.coreresources.testutils.TestApplication
import base.coreresources.testutils.mockActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import se.lovef.assert.v1.shouldBeFalse
import se.lovef.assert.v1.shouldBeTrue

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class ViewExtensionsKtTest {
    @Test
    fun should_remove_view_from_parent() {
        val (controller, act) = mockActivity<TestActivity>()
        val layout = LinearLayout(act)
        act.setContentView(layout)
        controller.resume()

        val view = View(act)
        layout += view
        layout.contains(view).shouldBeTrue()

        view.removeFromParent()
        layout.contains(view).shouldBeFalse()
    }
}