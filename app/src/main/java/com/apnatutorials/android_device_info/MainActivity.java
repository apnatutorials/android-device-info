package com.apnatutorials.android_device_info;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PHONE_STATE_READ = 1;
    private int checkedPermission = PackageManager.PERMISSION_DENIED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkedPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (Build.VERSION.SDK_INT >= 23 && checkedPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else
            checkedPermission = PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        this.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                REQUEST_CODE_PHONE_STATE_READ);
    }

    /**
     * Method will be use to show device info
     *
     * @param v
     */
    public void showDeviceInfo(View v) {
        TelephonyManager manager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        AlertDialog.Builder dBuilder = new AlertDialog.Builder(this);
        StringBuilder stringBuilder = new StringBuilder();

        if (checkedPermission != PackageManager.PERMISSION_DENIED) {
            dBuilder.setTitle("Device Info");
            // Name of underlying board like "GoldFish"
            stringBuilder.append("Board : " + Build.BOARD + "\n");
            // The consumer-visible brand with which the product/hardware will be associated, if any.
            stringBuilder.append("Brand : " + Build.BRAND + "\n");
            // The name of the industrial design.
            stringBuilder.append("DEVICE : " + Build.DEVICE + "\n");
            // A build ID string meant for displaying to the user
            stringBuilder.append("Display : " + Build.DISPLAY + "\n");
            // A string that uniquely identifies this build.
            stringBuilder.append("FINGERPRINT : " + Build.FINGERPRINT + "\n");
            // The name of the hardware
            stringBuilder.append("HARDWARE : " + Build.HARDWARE + "\n");
            // either a changelist number, or a label like "M4-rc20".
            stringBuilder.append("ID : " + Build.ID + "\n");
            // The manufacturer of the product/hardware.
            stringBuilder.append("Manufacturer : " + Build.MANUFACTURER + "\n");
            // The end-user-visible name for the end product.
            stringBuilder.append("MODEL : " + Build.MODEL + "\n");
            // A hardware serial number, if available.
            stringBuilder.append("SERIAL : " + Build.SERIAL + "\n");
            // The user-visible SDK version of the framework; its possible values are defined in Build.VERSION_CODES.
            stringBuilder.append("VERSION : " + Build.VERSION.SDK_INT + "\n");

            // Returns the phone number string for line 1, for example, the MSISDN for a GSM phone.
            // Return null if it is unavailable
            stringBuilder.append("Line 1 : " + manager.getLine1Number() + "\n");
            // Returns the unique device ID, for example, the IMEI for GSM and the MEID or ESN for CDMA phones.
            // Return null if device ID is not available.
            stringBuilder.append("Device ID/IMEI : " + manager.getDeviceId() + "\n");
            // Returns the unique subscriber ID, for example,
            // the IMSI for a GSM phone. Return null if it is unavailable.
            stringBuilder.append("IMSI : " + manager.getSubscriberId());
        } else {
            dBuilder.setTitle("Permission denied");
            stringBuilder.append("Can't access device info !");
        }
        dBuilder.setMessage(stringBuilder);
        dBuilder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PHONE_STATE_READ:
                if (grantResults.length > 0 && permissions[0] == Manifest.permission.READ_PHONE_STATE) {
                    checkedPermission = PackageManager.PERMISSION_GRANTED;
                }
                break;

        }
    }
}
