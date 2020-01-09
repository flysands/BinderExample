package com.flysands.binderexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import static android.os.Process.myPid;

public class MainActivity extends AppCompatActivity {

    Button append;
    Button revert;
    EditText input;

    EncryptManager encryptManager;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            encryptManager = EncryptManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            encryptManager = null;
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (encryptManager != null) {
                encryptManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
                encryptManager = null;
                bindService();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        append = findViewById(R.id.append);
        input = findViewById(R.id.input);
        revert = findViewById(R.id.revert);
        bindService();
        revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Const.TAG, "pid " + myPid() + " tid " + Thread.currentThread().getName()
                                 + " call revert");
                MyMessage message = new MyMessage();
                message.text = input.getText().toString();
                try {
                    input.setText(encryptManager.revert(message));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        append.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Const.TAG, "pid " + myPid() + " tid " + Thread.currentThread().getName()
                                 + " call append");
                MyMessage message = new MyMessage();
                message.text = input.getText().toString();
                try {
                    input.setText(encryptManager.append(message));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.flysands.binder");
        intent.setPackage("com.flysands.binderexample");
        Log.d(Const.TAG, "client request bind");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
}
