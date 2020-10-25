package com.licheedev.serialtool.activity.dapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.licheedev.serialtool.R;

import java.util.List;


/**
 * 刊物列表的 Adatper
 * Created by xiangfenr on 2017/6/21 0021.
 */

public class CurrenySelectAdapter extends RecyclerView.Adapter<CurrenySelectAdapter.ViewHolder> {

    private Context context;
    private List<String> datas;

    public CurrenySelectAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_currency, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.btnCurrency.setText(datas.get(position));

        if (myViewHolerClicks != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myViewHolerClicks.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnCurrency;

        public ViewHolder(View itemView) {
            super(itemView);
            btnCurrency = itemView.findViewById(R.id.btnCurrency);
        }
    }

    public MyViewHolerClicks myViewHolerClicks;

    /**
     * 供外部Activity调用的点击事件
     *
     * @param myViewHolerClicks
     */
    public void setMyViewHolerClicks(MyViewHolerClicks myViewHolerClicks) {
        this.myViewHolerClicks = myViewHolerClicks;
    }

    /**
     * 定义按钮的点击事件的方法
     */
    public interface MyViewHolerClicks {
        //item的点击事件
        void onItemClick(int position);
    }

}
