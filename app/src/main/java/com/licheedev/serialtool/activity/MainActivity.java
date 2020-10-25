package com.licheedev.serialtool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.serialport.SerialPortFinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.comn.Device;
import com.licheedev.serialtool.comn.SerialPortManager;
import com.licheedev.serialtool.util.AllCapTransformationMethod;
import com.licheedev.serialtool.util.PrefHelper;
import com.licheedev.serialtool.util.ToastUtil;
import com.licheedev.serialtool.util.constant.PreferenceKeys;

import static com.licheedev.serialtool.R.array.baudrates;

public class MainActivity extends BaseActivity  {


    Button mBtnOpenDevice;
    @BindView(R.id.btn_send_data)
    Button mBtnSendData;

    @BindView(R.id.btn_send_data2)
    Button mBtnSendData1;

    @BindView(R.id.btn_send_data3)
    Button mBtnSendData3;

    @BindView(R.id.btn_send_data4)
    Button mBtnSendData4;

    @BindView(R.id.btn_send_data5)
    Button mBtnSendData5;

    @BindView(R.id.btn_send_data6)
    Button mBtnSendData6;

    @BindView(R.id.btn_send_data7)
    Button mBtnSendData7;

    @BindView(R.id.btn_send_data8)
    Button mBtnSendData8;

    @BindView(R.id.btn_send_data9)
    Button mBtnSendData9;

    @BindView(R.id.btn_close)
    Button mBtnclose;

    @BindView(R.id.et_data)
    EditText mEtData;

    private Device mDevice;

    private int mDeviceIndex;
    private int mBaudrateIndex;

    private String[] mDevices;
    private String[] mBaudrates;

    private boolean mOpened = false;

    int[] command1=new  int[]{'1','2','3','4','5','6','7','8','9','0','a','b','c','d','e','f','g','h','i','j','k','l'
            ,'m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O'
            ,'P','Q','R','S','T','U','V','W','X','Y','Z'};
    //int[] command2=new  int[]{'1','2','3','c',0xaa,0xaa,0x12,0x10,0x22,0xbb,0xbb};
    int[] command3=new  int[]{0xA1,0xA2,0xA3,0xA4,0x05,0x00,0x31,0x5A,0xBB,0xBB,0x6A}; //开防罩门6A
    int[] command4=new  int[]{0xA1,0xA2,0xA3,0xA4,0x05,0x00,0x32,0x5A,0xBB,0xBB,0x69}; //关防罩门69
    int[] command5=new  int[]{0xA1,0xA2,0xA3,0xA4,0x05,0x00,0x8E,0x5A,0xBB,0xBB,0xD5}; //开落钞门D5
    int[] command6=new  int[]{0xA1,0xA2,0xA3,0xA4,0x05,0x00,0x8F,0x5A,0xBB,0xBB,0xD4}; //关落钞门D4
    int[] command7=new  int[]{0xA1,0xA2,0xA3,0xA4,0x06,0x00,0x43,0xFF,0x3,0xBB,0xBB,0xBD}; //警 报器
    int[] command8=new  int[]{0xA1,0xA2,0xA3,0xA4,0x04,0x00,0x90,0xBB,0xBB,0x90}; //查机器编号
    int[] command9=new  int[]{0xA1,0xA2,0xA3,0xA4,/*STX 4byte*/
            0x12,0x00,/*size 2byte*/
            0x21,/*CMD 1byte*/
            0x02,0x00,0x01,0x08,0x00,0x04,0x02,0x06,0x01,0x00,0x01,0x01,0x01,0x05,/*DATA1 14byte*/
            0xBB,0xBB,0x39}; //进入工作模式
    int[] command10=new  int[]{0xA1,0xA2,0xA3,0xA4,/*STX 4byte*/
            0x0B,0x00,/*size 2byte*/
            0x22,/*CMD 1byte*/
            0x01,0x11,0x11,0x11,0x11,0x11,0x11,/*DATA1 7byte*/
            0xBB,0xBB,0x2C};; //开始点算指令
    int[] command11=new  int[]{0xA1,0xA2,0xA3,0xA4,/*STX 4byte*/
            0x04,0x00,/*size 2byte*/
            0x24,/*CMD 1byte*/
            0xBB,0xBB,0x36}; //存储收钞口纸币指令
    // int[] commandWorkMode=new  int[]{0xA1,0xA2,0xA3,0xA4,0x04,0x00,0x11,0xBB,0xBB,0x11}; //机器状态
    //int[] commandWorkMode=new  int[]{0xA1,0xA2,0xA3,0xA4,0x00,0x05,0x31,0x5a,0xBB,0xBB,0x6A}; //电机

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEtData.setTransformationMethod(new AllCapTransformationMethod(true));

//        initDevice();

    }

    @Override
    protected void onDestroy() {
        SerialPortManager.instance().close();
        super.onDestroy();
    }

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化设备列表
     */
    private void initDevice() {

        SerialPortFinder serialPortFinder = new SerialPortFinder();

        // 设备
        mDevices = serialPortFinder.getAllDevicesPath();
        if (mDevices.length == 0) {
            mDevices = new String[] {
                    getString(R.string.no_serial_device)
            };
        }
        // 波特率
        mBaudrates = getResources().getStringArray(baudrates);

        mDeviceIndex = PrefHelper.getDefault().getInt(PreferenceKeys.SERIAL_PORT_DEVICES, 0);
        mDeviceIndex = mDeviceIndex >= mDevices.length ? mDevices.length - 1 : mDeviceIndex;
        mBaudrateIndex = PrefHelper.getDefault().getInt(PreferenceKeys.BAUD_RATE, 0);

        mDevice = new Device(mDevices[mDeviceIndex], mBaudrates[mBaudrateIndex]);

        SerialPortManager.instance().close();
        mDevice = new Device("/dev/ttyS4", "115200");
        SerialPortManager.instance().open(mDevice);

    }


    final String comandRecjectReason =  "A1A2A3A4040033BBBB33";
    final String comandReset =          "A1A2A3A4040047BBBB47";
    //开始收钱点算

    @OnClick({R.id.send_exit, R.id.send_save, R.id.send_reset, R.id.send_custom, R.id.send, R.id.send_receive,R.id.send_count,R.id.send_work, R.id.btn_send_data ,R.id.btn_send_data2 ,R.id.btn_send_data3 ,R.id.btn_send_data4 ,R.id.btn_send_data5 ,R.id.btn_send_data6 ,R.id.btn_send_data7 ,R.id.btn_send_data8 ,R.id.btn_send_data9 , R.id.btn_close })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_exit:
                SerialPortManager.instance().sendExitWorkModeCommand();
                break;
            case R.id.send_save:
                SerialPortManager.instance().sendSaveCommand();
                break;
            case R.id.send:
                SerialPortManager.instance().sendCountCommand();
                break;
            case R.id.send_custom:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS4", "115200");
                SerialPortManager.instance().open(mDevice);
                SerialPortManager.instance().sendCommand(comandRecjectReason);
                break;

            case R.id.send_reset:
//                SerialPortManager.instance().close();
//                mDevice = new Device("/dev/ttyS4", "115200");
//                SerialPortManager.instance().open(mDevice);
                SerialPortManager.instance().sendCommand(comandReset);
                break;

            case R.id.send_work:
                sendData(command9);
                break;
            case R.id.send_count:
//                SerialPortManager.instance().close();
//                mDevice = new Device("/dev/ttyS4", "115200");
//                SerialPortManager.instance().open(mDevice);
                sendData(command10);
//                SerialPortManager.instance().sendCommand("a1a2a3a40b002201101010101111bbbb28");
                break;
            case R.id.btn_send_data:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS3", "9600");
                SerialPortManager.instance().open(mDevice);
                sendData(command1);
                break;
            case R.id.send_receive:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS4", "115200");
                SerialPortManager.instance().open(mDevice);
                sendData(command11);
                break;

            case R.id.btn_send_data2:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS1", "9600");
                SerialPortManager.instance().open(mDevice);
                sendData(command1);
                break;

            case R.id.btn_send_data3:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS4", "115200");
                SerialPortManager.instance().open(mDevice);
                sendData(command3);
                //Toast.makeText(getApplicationContext(),"PS08 错误",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_send_data4:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS4", "115200");
                SerialPortManager.instance().open(mDevice);
                sendData(command4);
                break;

            case R.id.btn_send_data5:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS4", "115200");
                SerialPortManager.instance().open(mDevice);
                sendData(command5);
                break;

            case R.id.btn_send_data6:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS4", "115200");
                SerialPortManager.instance().open(mDevice);
                sendData(command6);
                break;

            case R.id.btn_send_data7:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS4", "115200");
                SerialPortManager.instance().open(mDevice);
                sendData(command7);
                break;

            case R.id.btn_send_data8:
                SerialPortManager.instance().close();
                mDevice = new Device("/dev/ttyS4", "115200");
                SerialPortManager.instance().open(mDevice);
                sendData(command8);
                break;

            case R.id.btn_send_data9:

                break;

            case R.id.btn_close:
                SerialPortManager.instance().close();
                this.finish();
                break;
        }
    }


    private void sendData(int[] b) {

        SerialPortManager.instance().sendCommand(SerialPortManager.byteArrayToHexString(b));
        // SerialPortManager.instance().sendCommand(senddat);
    }



    private static void xor() {
        int[] command3=new  int[]{0xA1,0xA2,0xA3,0xA4,/*STX 4byte*/
                0x04,0x00,/*size 2byte*/
                0x25,/*CMD 1byte*/
                0xBB,0xBB,0x38};
        int a  = 0xA1;
        for(int i = 1; i< command3.length-1; i++){
            a = a ^ command3[i];
        }
        System.out.println(a);
    }


}
