package com.emr.javacalculator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.emr.javacalculator.Adapters.SimInfoAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> simInfoList = new ArrayList<String>();

        SimInfoAdapter adapter = new SimInfoAdapter(simInfoList);
        recyclerView.setAdapter(adapter);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Hata");

        if (ActivityCompat.checkSelfPermission(MainActivity.this , Manifest.permission.READ_PHONE_STATE) == PermissionChecker.PERMISSION_GRANTED)
        {
            TelephonyManager telephonyManager = (TelephonyManager) MainActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
            {
                SubscriptionManager subscriptionManager = (SubscriptionManager) MainActivity.this.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

                if (subscriptionInfoList != null)
                {
                    for (int i = 0; i < subscriptionInfoList.size(); i++) {
                        SubscriptionInfo info = subscriptionInfoList.get(i);
                        String simInfo = "Operatör: " + info.getCarrierName() + "\n" +
                                "MCC-MNC: " + info.getMcc() + "-" + info.getMnc() + "\n" +
                                "Country: " + info.getCountryIso().toUpperCase() + "\n" +
                                "Slot: " + info.getSimSlotIndex();
                        simInfoList.add(simInfo);
                    }

                    adapter.notifyDataSetChanged();
                }

                else
                {
                    alertBuilder.setMessage("SIM Kartınızdan bilgiler alınırken bir sorun oluştu.");
                    alertBuilder.show();
                }
            }
            else {
                alertBuilder.setMessage("Telefonunuz SIM kartı bilglerini paylaşmayı desteklemiyor.");
                alertBuilder.show();
            }
        }
        else
        {
            alertBuilder.setMessage("Gerekli izinler alınırken bir sorun oluştu. Bu uyarı kapandıktan sonra ilgili pencere açılacaktır.");
            alertBuilder.show();
            alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ActivityCompat.requestPermissions(MainActivity.this , new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                }
            });
        }
    }
}