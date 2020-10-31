package com.licheedev.serialtool.comn.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.greenrobot.eventbus.EventBus;

/**
 * log管理类
 */

public class LogManager {
    public static final int COUNT_COMMAND = 0x1;
    public static final int EXIT_WORK_COMMAND = 0x2;
    public static final int SAVE_SUCCESS_COMMAND = 0x3;
    public static final int FINISH_DEPOSIT = 0x4;

    /**
     * 查询退钞详情
     */
    public static final int SEARCH_LEAD = 0x5;

    public final List<IMessage> messages;
    private boolean mAutoEnd = true;

    public LogManager() {
        messages = new ArrayList<>();
    }

    public void post(ReceiveData map) {
        EventBus.getDefault().post(map);
    }

    public void postError(String error) {
        EventBus.getDefault().post(error);
    }

    private static class InstanceHolder {

        public static LogManager sManager = new LogManager();
    }

    public static LogManager instance() {
        return InstanceHolder.sManager;
    }

    public void add(IMessage message) {
        messages.add(message);
    }

    public void post(IMessage message) {
        EventBus.getDefault().post(message);
    }

    public void clear() {
        messages.clear();
    }

    public boolean isAutoEnd() {
        return mAutoEnd;
    }

    public void setAutoEnd(boolean autoEnd) {
        mAutoEnd = autoEnd;
    }

    public void changAutoEnd() {
        mAutoEnd = !mAutoEnd;
    }

    public static class ReceiveData {
        public byte[] data;
        public int what;

        public ReceiveData(byte[] data, int what) {
            this.data = data;
            this.what = what;
        }
    }
}

