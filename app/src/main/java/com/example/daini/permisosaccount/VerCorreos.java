package com.example.daini.permisosaccount;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class VerCorreos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_correos);

        Account[] lista_cuentas = null;

        AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        lista_cuentas = accountManager.getAccounts();
        for (Account cuenta : lista_cuentas) {
            if (cuenta.type.equals("com.google")) {
                SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
                String cuentas = sharedPreferences.getString("pref", String.valueOf(cuenta.name));
                String[] array = new String[]{cuentas};


                ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.pintar, array);
                ListView listView = (ListView) findViewById(R.id.miLV);
                listView.setAdapter(adapter);
            }


        }
    }
}
