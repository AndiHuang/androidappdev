package com.example.eandihu.androidcall;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Call detect service.
 * This service is needed, because MainActivity can lost it's focus,
 * and calls will not be detected.
 *
 * @author Moskvichev Andrey V.
 *
 */
public class CallDetectService extends Service {
    private CallHelper callHelper;

    public CallDetectService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String num = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //System.out.println(num);
        callHelper = new CallHelper(this, num);

        int res = super.onStartCommand(intent, flags, startId);
        callHelper.start();
        return res;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        callHelper.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // not supporting binding
        return null;
    }
}

