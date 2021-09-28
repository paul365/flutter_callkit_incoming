package com.hiennv.flutter_callkit_incoming

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class CallkitIncomingActivity : Activity() {


    companion object {

        private const val ACTION_CALLKIT_INCOMING =
            "com.hiennv.flutter_callkit_incoming.ACTION_CALLKIT_INCOMING"
        private const val ACTION_CALL_ACCEPT =
            "com.hiennv.flutter_callkit_incoming.ACTION_CALL_ACCEPT"
        private const val ACTION_CALL_DECLINE =
            "com.hiennv.flutter_callkit_incoming.ACTION_CALL_DECLINE"
        private const val ACTION_CALL_ENDED =
            "com.hiennv.flutter_callkit_incoming.ACTION_CALL_ENDED"


        const val EXTRA_CALLKIT_INCOMING_DATA = "EXTRA_CALLKIT_INCOMING_DATA"
        const val EXTRA_CALLKIT_ID = "EXTRA_CALLKIT_ID"
        const val EXTRA_CALLKIT_NAME_CALLER = "EXTRA_CALLKIT_NAME_CALLER"
        const val EXTRA_CALLKIT_NUMBER = "EXTRA_CALLKIT_NUMBER"
        const val EXTRA_CALLKIT_TYPE = "EXTRA_CALLKIT_TYPE"
        const val EXTRA_CALLKIT_AVATAR = "EXTRA_CALLKIT_AVATAR"



        fun start(data: Bundle) = Intent(ACTION_CALLKIT_INCOMING).apply {
            putExtra(EXTRA_CALLKIT_INCOMING_DATA, data)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        }
    }

    private lateinit var tvNameCaller: TextView
    private lateinit var tvNumber: TextView
    private lateinit var ivAvatar: CircleImageView

    private lateinit var ivAcceptCall: ImageView
    private lateinit var ivDeclineCall: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
            setTurnScreenOn(true)
            setShowWhenLocked(true)

            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            )
        }
        initView()
        incomingData(intent)
    }

    private fun incomingData(intent: Intent) {
        val data = intent.extras?.getBundle(EXTRA_CALLKIT_INCOMING_DATA)
        if (data == null) finish()

        tvNameCaller.text = data?.getString(EXTRA_CALLKIT_NAME_CALLER, "")
        tvNumber.text = data?.getString(EXTRA_CALLKIT_NUMBER, "")

        val avatarUrl = data?.getString(EXTRA_CALLKIT_AVATAR, "")
        if (avatarUrl != null && avatarUrl.isNotEmpty()) {
            ivAvatar.visibility = View.VISIBLE
            Picasso.get().load(avatarUrl).into(ivAvatar)
        } else {
            ivAvatar.visibility = View.GONE
        }
    }

    private fun initView() {
        tvNameCaller = findViewById(R.id.tvNameCaller)
        tvNumber = findViewById(R.id.tvNumber)
        ivAvatar = findViewById(R.id.ivAvatar)

        ivAcceptCall = findViewById(R.id.ivAcceptCall)
        ivDeclineCall = findViewById(R.id.ivDeclineCall)

        ivAcceptCall.setOnClickListener {
            onAcceptClick()
        }
        ivDeclineCall.setOnClickListener {
            onDeclineClick()
        }
    }


    private fun onAcceptClick() {

    }

    private fun onDeclineClick() {

    }


}