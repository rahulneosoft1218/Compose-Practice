package rahul.co.neosoft.compose.other

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log


class PowerConnectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val level = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        Log.d("jgfhjgfbdf",intent!!.action+" battery - $level %")
    }
}