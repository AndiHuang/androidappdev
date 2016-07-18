package com.example.eandihu.androidcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Helper class to detect incoming and outgoing calls.
 * @author Moskvichev Andrey V.
 *
 */
public class CallHelper {

    /**
     * Listener to detect incoming calls.
     */
    private class CallStateListener extends PhoneStateListener {
        private String phonenum;
        public CallStateListener(String num)
        {

            phonenum=num;
            //System.out.println(phonenum);
        }
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // called when someone is ringing to this phone
                    System.out.println(phonenum);
                    if(incomingNumber.equals(phonenum)) {
                        Toast.makeText(ctx,
                                "Incoming: " + incomingNumber+"Check: "+phonenum,
                                Toast.LENGTH_LONG).show();

                    }
                    break;
            }
        }
    }

    /**
     * Broadcast receiver to detect the outgoing calls.
     */
    public class OutgoingReceiver extends BroadcastReceiver {
        public OutgoingReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

            Toast.makeText(ctx,
                    "Outgoing: "+number,
                    Toast.LENGTH_LONG).show();
        }

    }

    private Context ctx;
    private TelephonyManager tm;
    private CallStateListener callStateListener;

    private OutgoingReceiver outgoingReceiver;

    public CallHelper(Context ctx, String num) {
        this.ctx = ctx;
        //System.out.println(num);
        callStateListener = new CallStateListener(num);
        outgoingReceiver = new OutgoingReceiver();
    }

    /**
     * Start calls detection.
     */
    public void start() {
        tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        ctx.registerReceiver(outgoingReceiver, intentFilter);
    }

    /**
     * Stop calls detection.
     */
    public void stop() {
        tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
        ctx.unregisterReceiver(outgoingReceiver);
    }

}
