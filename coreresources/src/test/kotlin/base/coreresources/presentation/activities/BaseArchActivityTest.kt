package base.coreresources.presentation.activities

import android.os.Build
import base.coreresources.testutils.TestActivity
import base.coreresources.testutils.TestApplication
import base.coreresources.testutils.mockActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.O])
class BaseArchActivityTest {
    @Test
    fun `should start activity`() {
        val (controller, _) = mockActivity<TestActivity>()
        controller.start()
        controller.resume()
        controller.pause()
        controller.stop()
        controller.destroy()
    }
}