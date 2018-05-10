package com.framgia.tuannmb.omusic.screen.flash;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.screen.BaseActivity;
import com.framgia.tuannmb.omusic.screen.main.MainActivity;

public class FlashActivity extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION = 111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void registerListeners() {

    }

    @Override
    protected void initializeComponents() {
        requestPermission();
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_flash;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            boolean isGrantedAll = true;
            for (int i = 0; i < permissions.length; i++) {
                if (!isGranted(permissions[i])) {
                    isGrantedAll = false;
                    break;
                }
            }
            if (isGrantedAll) {
                startMainActivity();
                return;
            }
            //request permissions runtime
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION);
            return;
        }
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isGranted(String permission) {
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != REQUEST_CODE_PERMISSION) {
            return;
        }
        if (permissions.length != grantResults.length) {
            return;
        }
        boolean isGrantedAll = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isGrantedAll = false;
                break;
            }
        }
        if (isGrantedAll) {
            startMainActivity();
            return;
        }
        //TOAS
    }
}
