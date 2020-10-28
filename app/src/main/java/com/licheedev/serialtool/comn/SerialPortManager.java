package com.licheedev.serialtool.comn;

import android.app.Application;
import android.os.HandlerThread;
import android.serialport.SerialPort;
import android.serialport.SerialPortFinder;

import com.licheedev.serialtool.App;
import com.licheedev.serialtool.R;
import com.licheedev.serialtool.comn.message.LogManager;
import com.licheedev.serialtool.comn.message.SendMessage;
import com.licheedev.serialtool.util.ByteUtil;
import com.licheedev.serialtool.util.LogPlus;
import com.licheedev.serialtool.util.PrefHelper;
import com.licheedev.serialtool.util.ToastUtil;
import com.licheedev.serialtool.util.constant.PreferenceKeys;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static com.licheedev.serialtool.R.array.baudrates;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class SerialPortManager {

    private static final String TAG = "SerialPortManager";

    private SerialReadThread mReadThread;
    private OutputStream mOutputStream;
    private HandlerThread mWriteThread;
    private Scheduler mSendScheduler;
    final String commandCount = "A1A2A3A40F00220150C300003F0096003200BBBB20";
    //    final String commandSave = "A1A2A3A40F00220150C300003F0096003200BBBB20";
    int[] commandSave = new int[]{0xA1, 0xA2, 0xA3, 0xA4,/*STX 4byte*/
            0x04, 0x00,/*size 2byte*/
            0x24,/*CMD 1byte*/
            0xBB, 0xBB, 0x24};
    int[] commandExit = new int[]{0xA1, 0xA2, 0xA3, 0xA4,/*STX 4byte*/
            0x04, 0x00,/*size 2byte*/
            0x26,/*CMD 1byte*/
            0xBB, 0xBB, 0x38};
    int[] commandSaveAck = new int[]{0xA1, 0xA2, 0xA3, 0xA4,/*STX 4byte*/
            0x04, 0x00,/*size 2byte*/
            0x25,/*CMD 1byte*/
            0xBB, 0xBB, 0x25};

    /**
     * 查看退钞详情
     */
    int[] mCommandLead = new int[]{0xA1, 0xA2, 0xA3, 0xA4,/*STX 4byte*/
            0x00, 0x00,/*size 2byte*/
            0x33,/*CMD 1byte*/
            0xBB, 0xBB, 0x25};

    int[] commandMaskOpen = new int[]{0xA1, 0xA2, 0xA3, 0xA4, 0x05, 0x00, 0x31, 0x5A, 0xBB, 0xBB, 0x6A}; //开防罩门6A
    int[] commandMaskClose = new int[]{0xA1, 0xA2, 0xA3, 0xA4, 0x05, 0x00, 0x32, 0x5A, 0xBB, 0xBB, 0x69}; //关防罩门69

    final String commandStatus = "A1 A2 A3 A4 04 00 11 BB BB 11";


    //退出工作模式再进入，可以二次存钱 CMD25
    final String exitCommand0 = "A1 A2 A3 A4 2F 00 25 06 01 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 02 00 01 00 03 00 00 00 01 00 00 00 00 00 00 00 00 00 00 00 00 BB BB 09";

    final String exitCommand1 = "A1 A2 A3 A4 05 00 4F 01 BB BB 4F ";
    final String exitCommand2 = "A1 A2 A3 A4 13 00 11 01 00 00 04 3D 00 05 40 00 00 00 00 00 00 01 BB BB 7A";
    final String exitCommand = "A1 A2 A3 A4 05 00 20 06 BB BB 27 ";
    final String exitCommand4 = "A1 A2 A3 A4 04 00 16 BB BB 16 ";
    final String exitCommand5 = "A1 A2 A3 A4 05 00 4F 01 BB BB 4F ";
    final String exitCommand6 = "A1 A2 A3 A4 05 00 4F 00 BB BB 4E ";
    final String exitCommand7 = "A1 A2 A3 A4 2C 00 90 13 79 00 54 14 47 0A 54 BD 6F 70 1F 04 00 00 02 14 53 5F 00 14 52 01 02 00 00 00 00 44 45 33 30 30 2D 30 30 31 20 20 20 BB BB 63 ";
    final String exitCommand8 = "A1 A2 A3 A4 05 00 4F 01 BB BB 4F ";
    final String exitCommand9 = "A1 A2 A3 A4 44 00 4D B2 0C B2 0C B2 0C B5 0C 3B 0C 3D 0C 3C 0C 3A 0C 3D 0C 3C 0C 3A 0C 91 00 3C 0C 3A 0C 00 00 00 00 00 00 3A 0C 00 00 00 00 19 00 03 00 38 0C 3B 0C 00 00 3B 0C 3C 0C 39 0C 00 00 00 00 00 00 00 00 BB BB B7 ";
    final String exitCommand10 = "A1 A2 A3 A4 48 00 50 DC 05 DC 05 DC 05 DC 05 58 02 CF 07 CF 07 CF 07 CF 07 C3 09 C3 09 C3 09 C3 09 8B 0A DC 05 DC 05 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 BB BB C7 ";
    final String exitCommand11 = "A1 A2 A3 A4 05 00 4F 00 BB BB 4E ";

    public void sendCountCommand() {
        sendCommand(commandCount);
    }

    public void sendSaveCommand() {
        SerialPortManager.instance().sendCommand(byteArrayToHexString(commandSave));
    }

    public void sendSaveAck() {
        SerialPortManager.instance().sendCommand(byteArrayToHexString(commandSaveAck));

    }

    /**
     * 查询退钞详情
     */
    public void sendLeadCommand() {
        SerialPortManager.instance().close();
        mDevice = new Device("/dev/ttyS4", "115200");
        SerialPortManager.instance().open(mDevice);
        SerialPortManager.instance().sendCommand("A1A2A3A4040033BBBB33");
    }


    public void sendExitWorkModeCommand() {
        String replace = exitCommand.replace(" ", "");
        sendCommand(replace);
//        SerialPortManager.instance().sendCommand(byteArrayToHexString(commandExit));
    }


    public void sendStatusCommand() {
        String replace = commandStatus.replace(" ", "");
        sendCommand(replace);
    }


    public void closeMaskDoor() {
        SerialPortManager.instance().sendCommand(byteArrayToHexString(commandMaskClose));
    }


    public void openMaskDoor() {
        SerialPortManager.instance().sendCommand(byteArrayToHexString(commandMaskOpen));
    }

    private Device mDevice;

    private int mDeviceIndex;
    private int mBaudrateIndex;

    private String[] mDevices;
    private String[] mBaudrates;


    public void initDevice() {

        SerialPortFinder serialPortFinder = new SerialPortFinder();

        // 设备
        mDevices = serialPortFinder.getAllDevicesPath();
        if (mDevices.length == 0) {
            mDevices = new String[]{
                    App.instance().getString(R.string.no_serial_device)
            };
        }
        // 波特率
        mBaudrates = App.instance().getResources().getStringArray(baudrates);

        mDeviceIndex = PrefHelper.getDefault().getInt(PreferenceKeys.SERIAL_PORT_DEVICES, 0);
        mDeviceIndex = mDeviceIndex >= mDevices.length ? mDevices.length - 1 : mDeviceIndex;
        mBaudrateIndex = PrefHelper.getDefault().getInt(PreferenceKeys.BAUD_RATE, 0);

        mDevice = new Device(mDevices[mDeviceIndex], mBaudrates[mBaudrateIndex]);

        SerialPortManager.instance().close();
        mDevice = new Device("/dev/ttyS4", "115200");
        SerialPortManager.instance().open(mDevice);
    }


    private static class InstanceHolder {
        public static SerialPortManager sManager = new SerialPortManager();
    }

    public static SerialPortManager instance() {
        return InstanceHolder.sManager;
    }

    private SerialPort mSerialPort;

    private SerialPortManager() {
    }

    /**
     * 打开串口
     *
     * @param device
     * @return
     */
    public SerialPort open(Device device) {
        return open(device.getPath(), device.getBaudrate());
    }

    /**
     * 打开串口
     *
     * @param devicePath
     * @param baudrateString
     * @return
     */
    public SerialPort open(String devicePath, String baudrateString) {
        if (mSerialPort != null) {
            close();
        }

        try {
            File device = new File(devicePath);
            int baurate = Integer.parseInt(baudrateString);
            mSerialPort = new SerialPort(device, baurate, 0);

            mReadThread = new SerialReadThread(mSerialPort.getInputStream());
            mReadThread.start();

            mOutputStream = mSerialPort.getOutputStream();

            mWriteThread = new HandlerThread("write-thread");
            mWriteThread.start();
            mSendScheduler = AndroidSchedulers.from(mWriteThread.getLooper());

            return mSerialPort;
        } catch (Throwable tr) {
            LogPlus.e(TAG, "打开串口失败", tr);
            close();
            return null;
        }
    }

    /**
     * 关闭串口
     */
    public void close() {
        if (mReadThread != null) {
            mReadThread.close();
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mWriteThread != null) {
            mWriteThread.quit();
        }

        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    /**
     * 发送数据
     *
     * @param datas
     * @return
     */
    private void sendData(byte[] datas) throws Exception {
        mOutputStream.write(datas);
    }

    /**
     * (rx包裹)发送数据
     *
     * @param datas
     * @return
     */
    private Observable<Object> rxSendData(final byte[] datas) {

        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    sendData(datas);
                    emitter.onNext(new Object());
                } catch (Exception e) {

                    LogPlus.e("发送：" + ByteUtil.bytes2HexStr(datas) + " 失败", e);

                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                        return;
                    }
                }
                emitter.onComplete();
            }
        });
    }

    public static String byteArrayToHexString(int[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if (b[i] < 10) {
                resultSb.append(Integer.toHexString(0x00));
            }
            resultSb.append(Integer.toHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 发送命令包
     */
    public void sendCommand(final String command) {

        // TODO: 2018/3/22  
        LogPlus.i("发送命令：" + command);
//        ToastUtil.show(App.instance(),"发送命令command = " + command);

        byte[] bytes = ByteUtil.hexStr2bytes(command);
        rxSendData(bytes).subscribeOn(mSendScheduler).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Object o) {
                LogManager.instance().post(new SendMessage(command));
            }

            @Override
            public void onError(Throwable e) {
                LogPlus.e("发送失败", e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
