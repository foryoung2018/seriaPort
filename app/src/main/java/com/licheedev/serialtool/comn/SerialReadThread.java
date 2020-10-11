package com.licheedev.serialtool.comn;

import android.content.Context;
import android.os.SystemClock;
import android.widget.Toast;

import com.licheedev.myutils.LogPlus;
import com.licheedev.serialtool.activity.MainActivity;
import com.licheedev.serialtool.comn.message.IMessage;
import com.licheedev.serialtool.comn.message.LogManager;
import com.licheedev.serialtool.comn.message.RecvMessage;
import com.licheedev.serialtool.util.ByteUtil;
import com.licheedev.serialtool.util.constant.Money;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 读串口线程
 */
public class SerialReadThread extends Thread {

    private static final String TAG = "SerialReadThread";
    private static final String sendok = "A1A2A3A4040044BBBB44";
    private static final String sendok1 = "A1A2A3A4040015BBBB15";
    private BufferedInputStream mInputStream;

    public SerialReadThread(InputStream is) {
        mInputStream = new BufferedInputStream(is);
    }

    @Override
    public void run() {
        byte[] received = new byte[1024];
        int size;

        LogPlus.e("开始读线程");

        while (true) {

            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {

                int available = mInputStream.available();

                if (available > 0) {
                    size = mInputStream.read(received);
                    String s = bytesToHexString(received, available);
                    LogPlus.e("收到byte数组", "received size = " + available + " received:" + s);

                    if (size > 0) {
                        onDataReceive(received, size);
                    }
                    received = new byte[1024];
                } else {
                    // 暂停一点时间，免得一直循环造成CPU占用率过高
                    SystemClock.sleep(50);
                }
            } catch (IOException e) {
                LogPlus.e("读取数据失败", e);
                close();
            }
            //Thread.yield();
        }

//        LogPlus.e("结束读进程");
    }

    public static String bytesToHexString(byte[] src, int length){

        StringBuilder stringBuilder = new StringBuilder("");

        if (src == null || length <= 0) {

            return null;

        }

        for (int i = 0; i < length; i++) {

            int v = src[i] & 0xFF;

            String hv = Integer.toHexString(v);

            if (hv.length() < 2) {

                stringBuilder.append(0);

            }

            stringBuilder.append(hv);

        }

        return stringBuilder.toString();

    }

    public static String byteTo16(byte bt){
        String[] strHex={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        String resStr="";
        int low =(bt & 15);
        int high = bt>>4 & 15;
        resStr = strHex[high]+strHex[low];
        return resStr;
    }

    /**
     * 处理获取到的数据
     *
     * @param received
     * @param size
     */
    private void onDataReceive(byte[] received, int size) {
        // TODO: 2018/3/22 解决粘包、分包等
        String hexstr1 = null;
        byte[] version=new byte[160];
        String hexStr = ByteUtil.bytes2HexStr(received, 0, size);

        if((char)(received[6]&0xff)==0x15)
        {
            if(((char)(received[7]&0xff)&0x01)==0x01)
            {hexstr1=hexStr+"   PS01左错误"; }
            else if(((char)(received[7]&0xff)&0x02)==0x02)
            {hexstr1=hexStr+"   PS01右错误"; }
            else if(((char)(received[7]&0xff)&0x04)==0x04)
            {hexstr1=hexStr+"   PS02左错误"; }
            else if(((char)(received[7]&0xff)&0x08)==0x08)
            {hexstr1=hexStr+"   PS02右错误"; }
            else if(((char)(received[7]&0xff)&0x10)==0x10)
            {hexstr1=hexStr+"   PS03左1错误"; }
            else if(((char)(received[7]&0xff)&0x20)==0x20)
            {hexstr1=hexStr+"   PS03左2错误"; }
            else if(((char)(received[7]&0xff)&0x40)==0x40)
            {hexstr1=hexStr+"   PS03右1错误"; }
            else if(((char)(received[7]&0xff)&0x80)==0x80)
            {hexstr1=hexStr+"   PS03右2错误"; }
            if(((char)(received[8]&0xff)&0x01)==0x01)
            {hexstr1=hexStr+"   PS04错误"; }
            else if(((char)(received[8]&0xff)&0x02)==0x02)
            {hexstr1=hexStr+"   PS06错误"; }
            else if(((char)(received[8]&0xff)&0x04)==0x04)
            {hexstr1=hexStr+"   收钞电机错误"; }
            else if(((char)(received[8]&0xff)&0x08)==0x08)
            {hexstr1=hexStr+"   存款口打开错误"; }
            else if(((char)(received[8]&0xff)&0x10)==0x10)
            {hexstr1=hexStr+"   PS08左错误"; }
            else if(((char)(received[8]&0xff)&0x20)==0x20)
            {hexstr1=hexStr+"   PS08右错误"; }
            if(((char)(received[9]&0xff)&0x01)==0x01)
            {hexstr1=hexStr+"   "; }
            else if(((char)(received[9]&0xff)&0x02)==0x02)
            {hexstr1=hexStr+"   罩门打开出错"; }
            else if(((char)(received[9]&0xff)&0x04)==0x04)
            {hexstr1=hexStr+"   罩门关闭出错"; }
            else if(((char)(received[9]&0xff)&0x08)==0x08)
            {hexstr1=hexStr+"   落钞门关闭出错"; }
            else if(((char)(received[9]&0xff)&0x10)==0x10)
            {hexstr1=hexStr+"   "; }
            else if(((char)(received[9]&0xff)&0x20)==0x20)
            {hexstr1=hexStr+"   "; }
            else if(((char)(received[9]&0xff)&0x40)==0x40)
            {hexstr1=hexStr+"   PS09左错误"; }
            else if(((char)(received[9]&0xff)&0x80)==0x80)
            {hexstr1=hexStr+"   PS09右错误"; }
            if(((char)(received[10]&0xff)&0x01)==0x01)
            {hexstr1=hexStr+"   置钞口有纸币"; }
            else if(((char)(received[10]&0xff)&0x02)==0x02)
            {hexstr1=hexStr+"   PS05中错误"; }
            else if(((char)(received[10]&0xff)&0x04)==0x04)
            {hexstr1=hexStr+"   PS05左错误"; }
            else if(((char)(received[10]&0xff)&0x08)==0x08)
            {hexstr1=hexStr+"   PS05右错误"; }

            else
            {hexstr1=hexStr;}

//           SerialPortManager.instance().close();
//           SerialPortManager.instance().open("/dev/ttyS4","115200");
            SerialPortManager.instance().sendCommand(sendok1);

        }
        else if((char)(received[6]&0xff)==0x44)
        {
            if(((char)(received[7]&0xff)&0x01)==0x00)
            {hexstr1=hexStr+"   钞箱未到位"; }
            else if(((char)(received[7]&0xff)&0x02)==0x00)
            {hexstr1=hexStr+"   保险柜门未关"; }
            else if(((char)(received[7]&0xff)&0x04)==0x00)
            {hexstr1=hexStr+"   报警器报警"; }
            else if(((char)(received[8]&0xff)&0x01)==0x01)
            {hexstr1=hexStr+"   罩门传感器错误"; }
            else if(((char)(received[8]&0xff)&0x02)==0x02)
            {hexstr1=hexStr+"   落钞门传感器错误"; }
            else if(((char)(received[7]&0x08))==0x08)
            {hexstr1=hexStr+"   传感器检测到钞票";
                SerialPortManager.instance().sendCountCommand();
            }
            else
            {hexstr1=hexStr;}

            LogPlus.e("read_thread","传感器 " + hexstr1);
//            SerialPortManager.instance().sendCommand(sendok);
        }
        else if((char)(received[6]&0xff)==0x90)
        {   version[0]='B';
            version[1]='o';
            version[2]='o';
            version[3]='t';
            version[4]=':';
            version[5]= (byte)((char)(received[7]&0xff)/100+0x30) ;
            version[6]= (byte)(((char)(received[7]&0xff)/10)%10+0x30) ;
            version[7]= (byte)((char)(received[7]&0xff)%10+0x30) ;
            version[8]='-';
            version[9]= (byte)((char)(received[8]&0xff)/100+0x30) ;
            version[10]= (byte)(((char)(received[8]&0xff)/10)%10+0x30) ;
            version[11]= (byte)((char)(received[8]&0xff)%10+0x30) ;
            version[12]='-';
            version[13]= (byte)((char)(received[9]&0xff)/100+0x30) ;
            version[14]= (byte)(((char)(received[9]&0xff)/10)%10+0x30) ;
            version[15]= (byte)((char)(received[9]&0xff)%10+0x30) ;
            version[16]='-';
            version[17]= (byte)((char)(received[10]&0xff)/100+0x30) ;
            version[18]= (byte)(((char)(received[10]&0xff)/10)%10+0x30) ;
            version[19]= (byte)((char)(received[10]&0xff)%10+0x30) ;
            version[20]=' ';
            version[21]=' ';
            version[22]=' ';

            version[23]='H';
            version[24]='O';
            version[25]='S';
            version[26]='T';
            version[27]=':';
            version[28]= (byte)((char)(received[11]&0xff)/100+0x30) ;
            version[29]= (byte)(((char)(received[11]&0xff)/10)%10+0x30) ;
            version[30]= (byte)((char)(received[11]&0xff)%10+0x30) ;
            version[31]='-';
            version[32]= (byte)((char)(received[12]&0xff)/100+0x30) ;
            version[33]= (byte)(((char)(received[12]&0xff)/10)%10+0x30) ;
            version[34]= (byte)((char)(received[12]&0xff)%10+0x30) ;
            version[35]='-';
            version[36]= (byte)((char)(received[13]&0xff)/100+0x30) ;
            version[37]= (byte)(((char)(received[13]&0xff)/10)%10+0x30) ;
            version[38]= (byte)((char)(received[13]&0xff)%10+0x30) ;
            version[39]='-';
            version[40]= (byte)((char)(received[14]&0xff)/100+0x30) ;
            version[41]= (byte)(((char)(received[14]&0xff)/10)%10+0x30) ;
            version[42]= (byte)((char)(received[14]&0xff)%10+0x30) ;
            version[43]=' ';
            version[44]=' ';
            version[45]=' ';

            version[46]='M';
            version[47]='G';
            version[48]=':';
            version[49]= (byte)((char)(received[15]&0xff)/100+0x30) ;
            version[50]= (byte)(((char)(received[15]&0xff)/10)%10+0x30) ;
            version[51]= (byte)((char)(received[15]&0xff)%10+0x30) ;
            version[52]='-';
            version[53]= (byte)((char)(received[16]&0xff)/100+0x30) ;
            version[54]= (byte)(((char)(received[16]&0xff)/10)%10+0x30) ;
            version[55]= (byte)((char)(received[16]&0xff)%10+0x30) ;
            version[56]='-';
            version[57]= (byte)((char)(received[17]&0xff)/100+0x30) ;
            version[58]= (byte)(((char)(received[17]&0xff)/10)%10+0x30) ;
            version[59]= (byte)((char)(received[17]&0xff)%10+0x30) ;
            version[60]='-';
            version[61]= (byte)((char)(received[18]&0xff)/100+0x30) ;
            version[62]= (byte)(((char)(received[18]&0xff)/10)%10+0x30) ;
            version[63]= (byte)((char)(received[18]&0xff)%10+0x30) ;
            version[64]=' ';
            version[65]=' ';
            version[66]=' ';

            version[67]='F';
            version[68]='P';
            version[69]='G';
            version[70]='A';
            version[71]=':';
            version[72]= (byte)((char)(received[19]&0xff)/100+0x30) ;
            version[73]= (byte)(((char)(received[19]&0xff)/10)%10+0x30) ;
            version[74]= (byte)((char)(received[19]&0xff)%10+0x30) ;
            version[75]='-';
            version[76]= (byte)((char)(received[20]&0xff)/100+0x30) ;
            version[77]= (byte)(((char)(received[20]&0xff)/10)%10+0x30) ;
            version[78]= (byte)((char)(received[20]&0xff)%10+0x30) ;
            version[79]='-';
            version[80]= (byte)((char)(received[21]&0xff)/100+0x30) ;
            version[81]= (byte)(((char)(received[21]&0xff)/10)%10+0x30) ;
            version[82]= (byte)((char)(received[21]&0xff)%10+0x30) ;
            version[83]='-';
            version[84]= (byte)((char)(received[22]&0xff)/100+0x30) ;
            version[85]= (byte)(((char)(received[22]&0xff)/10)%10+0x30) ;
            version[86]= (byte)((char)(received[22]&0xff)%10+0x30) ;
            version[87]=' ';
            version[88]=' ';
            version[89]=' ';

            version[90]=' ';
            version[91]='A';
            version[92]='P';
            version[93]='P';
            version[94]=':';
            version[95]= (byte)((char)(received[23]&0xff)/100+0x30) ;
            version[96]= (byte)(((char)(received[23]&0xff)/10)%10+0x30) ;
            version[97]= (byte)((char)(received[23]&0xff)%10+0x30) ;
            version[98]='-';
            version[99]= (byte)((char)(received[24]&0xff)/100+0x30) ;
            version[100]= (byte)(((char)(received[24]&0xff)/10)%10+0x30) ;
            version[101]= (byte)((char)(received[24]&0xff)%10+0x30) ;
            version[102]='-';
            version[103]= (byte)((char)(received[25]&0xff)/100+0x30) ;
            version[104]= (byte)(((char)(received[25]&0xff)/10)%10+0x30) ;
            version[105]= (byte)((char)(received[25]&0xff)%10+0x30) ;
            version[106]='-';
            version[107]= (byte)((char)(received[26]&0xff)/100+0x30) ;
            version[108]= (byte)(((char)(received[26]&0xff)/10)%10+0x30) ;
            version[109]= (byte)((char)(received[26]&0xff)%10+0x30) ;
            version[110]=' ';
            version[111]=' ';
            version[112]=' ';

            version[113]=' ';
            version[114]='Z';
            version[115]='P';
            version[116]='K';
            version[117]=':';
            version[118]= (byte)((char)(received[27]&0xff)/100+0x30) ;
            version[119]= (byte)(((char)(received[27]&0xff)/10)%10+0x30) ;
            version[120]= (byte)((char)(received[27]&0xff)%10+0x30) ;
            version[121]='-';
            version[122]= (byte)((char)(received[28]&0xff)/100+0x30) ;
            version[123]= (byte)(((char)(received[28]&0xff)/10)%10+0x30) ;
            version[124]= (byte)((char)(received[28]&0xff)%10+0x30) ;
            version[125]='-';
            version[126]= (byte)((char)(received[29]&0xff)/100+0x30) ;
            version[127]= (byte)(((char)(received[29]&0xff)/10)%10+0x30) ;
            version[128]= (byte)((char)(received[29]&0xff)%10+0x30) ;
            version[129]='-';
            version[130]= (byte)((char)(received[30]&0xff)/100+0x30) ;
            version[131]= (byte)(((char)(received[30]&0xff)/10)%10+0x30) ;
            version[132]= (byte)((char)(received[30]&0xff)%10+0x30) ;
            version[133]=' ';
            version[134]=' ';
            version[135]=' ';

            version[136]='S';
            version[137]='N';
            version[138]=':';
            version[139]=(byte)(received[35]);
            version[140]=(byte)(received[36]);
            version[141]=(byte)(received[37]);
            version[142]=(byte)(received[38]);
            version[143]=(byte)(received[39]);
            version[144]=(byte)(received[40]);
            version[145]=(byte)(received[41]);
            version[146]=(byte)(received[42]);
            version[147]=(byte)(received[43]);
            version[148]=(byte)(received[44]);
            version[149]=(byte)(received[45]);
            version[150]=(byte)(received[46]);


            String b=new String(version);
            hexstr1=b;

        }
        else if((char)(received[6]&0xff)== 0x12)
        {
            LogPlus.e("read_thread","点钞信息 " + hexStr);
            byte status = received[7];
            switch (status){
                case 0:
                    LogPlus.e("read_thread","点钞中 ");
                    break;
                case 3:
                    LogPlus.e("read_thread","点钞完成 ");
//                    amountReceiveMoney(received);
                    LogManager.instance().post(received);
                    break;
                case 4:
                    LogPlus.e("read_thread","点钞暂停 ");
                    break;
            }
            return;
        }
        else if((char)(received[6]&0xff)== 0x21)
        {
            LogPlus.e("read_thread","进入工作模式");
        }
        else if((char)(received[6]&0xff)== 0x22)
        {
            LogPlus.e("read_thread","开始点算指令 " + hexStr);
            int result = received[8];
            switch (result){
                case 6:
                    LogPlus.e("read_thread","开始点算 ");
                    break;
                case 0x15:
                    LogPlus.e("read_thread","开始点算 未处在工作模式，不能开始");
                    break;
                case 0x16:
                    LogPlus.e("read_thread","开始点算 置钞口无纸币，不能开始");
                    break;
                case 0x18:
                    LogPlus.e("read_thread","开始点算 收钞口满（张数上限），不能开始");
                    break;
                case 0x19:
                    LogPlus.e("read_thread","收钞口满（面值上限），不能开始");
                    break;
                case 0x1A:
                    LogPlus.e("read_thread","开始点算 其他原因，不能开始，请重置置钞口");
                    break;
            }
        }
        else if((char)(received[6]&0xff)== 0x25)
        {
            LogPlus.e("read_thread","存储收钞口纸币 " + hexStr);
            byte status = received[7];
            switch (status){
                case 6:
                    LogPlus.e("read_thread","纸币存储成功 ");
                    amountReceiveMoney(received);
                    amountSaveMoney(received);
                    break;
                case 16:
                    LogPlus.e("read_thread","纸币存储失败 ");
                    break;
                case 17:
                    LogPlus.e("read_thread","纸币存储失败，位置8有吊币 ");
                    break;
                case 18:
                    LogPlus.e("read_thread","纸币存储失败，存款操作超时 ");
                    break;
            }
            return;
        }
        else if((char)(received[6]&0xff)== 0x33)
        {
            LogPlus.e("read_thread","查询退钞原因 " + hexStr);
        }
        else
        {hexstr1=hexStr;}
//        LogManager.instance().post(new RecvMessage(hexstr1));
    }

    private void amountSaveMoney(byte[] received) {
        int CNY_100 = received[29] + (received[30]<<8);
        int CNY_50 = received[31] + (received[32]<<8);
        int CNY_20 = received[33] + (received[34]<<8);
        int CNY_10 = received[35] + (received[36]<<8);
        int CNY_5 = received[37] + (received[38]<<8);
        int CNY_1 = received[39] + (received[40]<<8);
        final String amount = " 共存  100x" + CNY_100 + " 50x" +
                CNY_50 + " 20x" + CNY_20 + " 10x" + CNY_10 + " 5x" + CNY_5 + " 1x" + CNY_1;
        LogPlus.e("read_thread",amount);
    }

    private void amountReceiveMoney(byte[] received) {

        int CNY_100 = received[9] + (received[10]<<8);
        int CNY_50 = received[11] + (received[12]<<8);
        int CNY_20 = received[13] + (received[14]<<8);
        int CNY_10 = received[15] + (received[16]<<8);
        int CNY_5 = received[17] + (received[18]<<8);
        int CNY_1 = received[19] + (received[20]<<8);
        final String amount = " 收到  100x" + CNY_100 + " 50x" +
                CNY_50 + " 20x" + CNY_20 + " 10x" + CNY_10 + " 5x" + CNY_5 + " 1x" + CNY_1;
        LogPlus.e("read_thread",amount);

    }

    /**
     * 停止读线程
     */
    public void close() {

        try {
            mInputStream.close();
        } catch (IOException e) {
            LogPlus.e("异常", e);
        } finally {
            super.interrupt();
        }
    }
}
