package com.example.saud.vcarddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.saud.vcarddemo.bean.UserData;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ContactManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View button1 = findViewById(R.id.button);
        View buttion2 = findViewById(R.id.button2);
        button1.setOnClickListener(this);
        buttion2.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                backup_contact();
                break;
            case R.id.button2:
                recover_contact();
                break;

            default:
                break;
        }

    }

    public void backup_contact() {
        mManager = ContactManager.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<UserData> contactInfo = mManager.getContactInfo(MainActivity.this);
                    System.out.println("可用联系人大小====》" + contactInfo.size());
                    mManager.backupContacts(contactInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }).start();
    }

    public void recover_contact() {
        mManager = ContactManager.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<UserData> infoList = mManager.restoreContacts();
                    for (UserData userInfo : infoList) {
                        mManager.addContacts(MainActivity.this, userInfo);
                    }
                    Toast.makeText(MainActivity.this,"还原完成",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }).start();
    }
}
