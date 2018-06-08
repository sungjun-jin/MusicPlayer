package com.example.jinsungjun.musicplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/*
추상 클래스로 권한 요청하기
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final int REQ_PERM = 111;

    public String[] permissions;

    public BaseActivity(String[] permissions) {

        this.permissions = permissions;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //버전 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            init();
        }
    }

    //1. 권한체크
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {

        boolean check = true;

        //0. 사용할 권한을 설정
        for (String permission : permissions) {

            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {

                check = false;
                requestPermissions(permissions, REQ_PERM);
                break;

            }
        }
        //반복문을 통과했다면 모든 권한이 승인된 상태이다.
        if (check)
            init();
    }

    //2. RequestPermisson 이후 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQ_PERM:
                for (int grant : grantResults) {

                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "해당 어플을 사용하려면 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }
                }
                init();
        }
    }

    public abstract void init();

}
