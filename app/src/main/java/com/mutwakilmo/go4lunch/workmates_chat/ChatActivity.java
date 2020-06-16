package com.mutwakilmo.go4lunch.workmates_chat;

import android.os.Bundle;

import com.mutwakilmo.go4lunch.R;
import com.mutwakilmo.go4lunch.base.BaseActivity;

public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_chat;
    }

}