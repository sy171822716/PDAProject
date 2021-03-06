package lz.lmf.pda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import lz.lmf.base.BaseActivity;
import lz.lmf.base.Tools;

public class MainActivity extends BaseActivity {
    ScanDevice sm;
    private final static String SCAN_ACTION = "scan.rcv.message";
    private String barcodeStr;
    private EditText showScanResult;


    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            android.util.Log.i("debug", "----codetype--" + temp);
            barcodeStr = new String(barocode, 0, barocodelen);
            showScanResult.append("广播输出：");
            showScanResult.append(barcodeStr);
            showScanResult.append("\n");
            showScanResult.setText(barcodeStr);
            sm.stopScan();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = new ScanDevice();
        CheckBox ch = (CheckBox) findViewById(R.id.checkBox1);
        if (sm.getOutScanMode() == 1) {
            ch.setChecked(true);
        } else {
            ch.setChecked(false);
        }
        ch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                if (arg1) {
                    sm.setOutScanMode(1);   //
                } else {
                    sm.setOutScanMode(0); //
                }
            }
        });

        showScanResult = (EditText) findViewById(R.id.editText1);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openScanner:
                System.out.println("openScanner = " + sm.getOutScanMode());
                sm.openScan();
                break;
            case R.id.closeScanner:
                sm.closeScan();
                break;
            case R.id.startDecode:
                sm.startScan();
                break;
            case R.id.stopDecode:
                sm.stopScan();
                break;
            case R.id.start_continue:
                sm.setScanLaserMode(4);
                break;
            case R.id.stop_continue:
                sm.setScanLaserMode(8);
                break;
            case R.id.close:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (sm != null) {
            sm.stopScan();
        }
        unregisterReceiver(mScanReceiver);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        registerReceiver(mScanReceiver, filter);
    }

    @Override
    public void onNetChange(int netModile) {
        super.onNetChange(netModile);
        //在此处判断网络状态
        if (netModile == -1) {
            Tools.showToast(this, "网络未连接");
        } else {
            Tools.showToast(this, "网络已连接");
        }

    }
}