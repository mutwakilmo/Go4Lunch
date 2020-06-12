package com.mutwakilmo.go4lunch;

import android.os.Bundle;

import com.mutwakilmo.go4lunch.base.BaseActivity;

public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    public int getFragmentLayout() {
        return 0;
    }
}