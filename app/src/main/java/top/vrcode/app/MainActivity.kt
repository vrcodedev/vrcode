package top.vrcode.app

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.termux.shared.termux.TermuxUtils
import top.vrcode.app.components.TerminalDialog
import top.vrcode.app.errView.AddGraphicalSupportActivity
import top.vrcode.app.errView.TermuxNotEnableActivity
import top.vrcode.app.utils.Utils

class MainActivity : AppCompatActivity() {

    private var frm: FrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LorieService.setMainActivity(this)
        LorieService.start(LorieService.ACTION_START_FROM_ACTIVITY)
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.main_activity)

//            kbd = findViewById(R.id.additionalKbd)
        frm = findViewById(R.id.frame)

        window.decorView.pointerIcon =
            PointerIcon.getSystemIcon(this, PointerIcon.TYPE_NULL)

//            Handler(Looper.getMainLooper()).postDelayed({
//                val scriptString =
//                    Utils.getAssetScript(Constant.LINUX_ENV_STARTUP_SCRIPT, application)
//                val bashScript = Utils.BashScript(scriptString, true)
//
//                TerminalDialog(this)
//                    .execute(
//                        bashScript.get()
//                    ).setPositiveButtonCallback { terminalDialog, terminalSession ->
//                        run {
//                            Log.d("TerminalCheck", terminalSession?.isRunning.toString())
//                            if ((terminalSession?.isRunning != true)) {
//                                terminalDialog.dismiss()
//                            }
//                        }
//                    }.show("Test Service")
//            }, 1000)

    }


    var orientation = 0
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation != orientation /*&& kbd != null && kbd!!.visibility == View.VISIBLE*/) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            var view = currentFocus
            if (view == null) {
                view = View(this)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        orientation = newConfig.orientation
    }

    fun onLorieServiceStart(instance: LorieService) {
        val lorieView = findViewById<SurfaceView>(R.id.lorieView)
        instance.setListeners(lorieView)
//        kbd?.reload(keys, lorieView, LorieService.onKeyListener)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val window = window
        val decorView = window.decorView
        if (preferences.getBoolean("Reseed", true)) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        } else {
            window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
            )
        }
    }

    override fun onBackPressed() {}

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onDestroy() {
        super.onDestroy()
        val exitIntent = Intent(applicationContext, LorieService::class.java)
        exitIntent.action = LorieService.ACTION_STOP_SERVICE
        startService(exitIntent)
    }
//    public override fun onUserLeaveHint() {
//        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
//        if (preferences.getBoolean("PIP", true)) {
//            enterPictureInPictureMode()
//        }
//    }

//    override fun onPictureInPictureModeChanged(
//        isInPictureInPictureMode: Boolean,
//        newConfig: Configuration
//    ) {
//        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
//        if (kbd!!.visibility != View.VISIBLE) if (preferences.getBoolean(
//                "showAdditionalKbd",
//                true
//            )
//        ) {
//            kbd!!.visibility = View.VISIBLE
//            val paddingDp = 35
//            val density = this.resources.displayMetrics.density
//            val paddingPixel = (paddingDp * density).toInt()
//            frm!!.setPadding(0, 0, 0, paddingPixel)
//        }
//        return
//
//    }

//    companion object {
//        private val keys = intArrayOf(
//            KeyEvent.KEYCODE_ESCAPE,
//            KeyEvent.KEYCODE_TAB,
//            KeyEvent.KEYCODE_CTRL_LEFT,
//            KeyEvent.KEYCODE_ALT_LEFT,
//            KeyEvent.KEYCODE_DPAD_UP,
//            KeyEvent.KEYCODE_DPAD_DOWN,
//            KeyEvent.KEYCODE_DPAD_LEFT,
//            KeyEvent.KEYCODE_DPAD_RIGHT
//        )
//    }
}