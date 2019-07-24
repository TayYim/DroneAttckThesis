package com.example.testdrone;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IPAddressView extends LinearLayout implements EditText.OnFocusChangeListener {
    private EditText ip_one,
            ip_two,
            ip_three,
            ip_four;

    private LinearLayout layout;

    public IPAddressView(@NonNull Context context) {
        super(context);
    }

    public IPAddressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IPAddressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IPAddressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * @param res 設置佈局
     */
    public void setLayout(int res) {
        layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(res, this, true);
        ip_one = findViewById(R.id.ip_one);
        ip_two = findViewById(R.id.ip_two);
        ip_three = findViewById(R.id.ip_three);
        ip_four = findViewById(R.id.ip_four);
        ip_one.setOnFocusChangeListener(this);
        ip_two.setOnFocusChangeListener(this);
        ip_three.setOnFocusChangeListener(this);
        ip_four.setOnFocusChangeListener(this);
    }

    public String getIp() {
        String ip = ip_one.getText().toString() + "."
                + ip_two.getText().toString() + "."
                + ip_three.getText().toString() + "."
                + ip_four.getText().toString();
        return ip;
    }

    /**
     * @param res 設置背景
     */
    public void setBackground(int res) {
        if (layout != null) {
            layout.setBackground(getResources().getDrawable(res, null));
        }
    }

    /**
     * @param color 顔色id
     *              設置字體顔色
     */
    public void setTextColor(int color) {
        if (ip_one != null && ip_two != null && ip_three != null && ip_four != null) {
            ip_one.setTextColor(getResources().getColor(color));
            ip_two.setTextColor(getResources().getColor(color));
            ip_three.setTextColor(getResources().getColor(color));
            ip_four.setTextColor(getResources().getColor(color));
        }
    }
    /**
     * @param size
     * 設置字體大小
     */
    public void setTextSize(float size) {
        if (ip_one != null && ip_two != null && ip_three != null && ip_four != null) {
            ip_one.setTextSize(size);
            ip_two.setTextSize(size);
            ip_three.setTextSize(size);
            ip_four.setTextSize(size);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (v instanceof EditText) {
                ((EditText) v).setSelection(((EditText) v).getText().toString().length());
            }
        }
    }

    /**
     * @param str
     * 设置数据
     */
    public void setTextIP(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] ipStr = str.split("\\.");
            if (ipStr.length == 4) {
                ip_one.setText(ipStr[0]);
                ip_two.setText(ipStr[1]);
                ip_three.setText(ipStr[2]);
                ip_four.setText(ipStr[3]);
            }
        }
    }
}
