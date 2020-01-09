package com.flysands.binderexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.os.Process.myPid;

public class RemoteService extends Service {

    private EncryptManagerNative mEncryptManagerNative = new EncryptManagerNative();

    class EncryptManagerNative extends EncryptManager.Stub {

        @Override
        public String revert(MyMessage source) throws RemoteException {
            Log.d(Const.TAG, "pid " + myPid() + " tid " + Thread.currentThread().getName()
                             + " receive revert call");
            return new StringBuffer(source.text).reverse().toString();
        }

        @Override
        public String append(MyMessage source) throws RemoteException {
            Log.d(Const.TAG, "pid " + myPid() + " tid " + Thread.currentThread().getName()
                             + " receive append call");
            return source.text + " add append string";
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Const.TAG, "remote service bind");
        return mEncryptManagerNative;
    }
}
