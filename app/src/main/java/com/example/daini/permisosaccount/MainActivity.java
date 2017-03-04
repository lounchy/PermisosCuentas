package com.example.daini.permisosaccount;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public String [] obtenerCorreosUsuario(){
        String [] lista_correos = null;
        Account []lista_cuentas = null;
        SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String cuentasGoogle = sharedPreferences.getString("cuentas", "");

        //Compruebo si el permiso el get account ha sido concedido en ejecucion
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS)==PackageManager.PERMISSION_GRANTED)
        {
            AccountManager accountManager = (AccountManager)getSystemService(ACCOUNT_SERVICE);
            lista_cuentas= accountManager.getAccounts();
            Log.e(getClass().getCanonicalName(), "TIENE PERMISOS DE GET ACCOUNTS");

            for (Account cuenta : lista_cuentas){
               if(cuenta.type.equals("com.google"))
               {
                   editor.putString("cuentas", String.valueOf(cuenta));
                   editor.commit();
                   Log.e(getClass().getCanonicalName(), " Cuente de correo . " +cuenta.name);
                   Log.e(getClass().getCanonicalName(), " GUARDO Cuente de correo EN Shared pref. " + cuentasGoogle);
               }else{
                   Log.e(getClass().getCanonicalName(), " Cuenta Tipo(NO GOOGLE) . " +cuenta.type);
               }
            }
        }else       //PERMISSION_DENIED
        {
            //TODO PEDIR PERMISOS
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS))
            {
                //Mostrar Mensaje explicativo AlertDialog
            }
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.GET_ACCOUNTS}, 100);
        }


        return lista_correos;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if((grantResults.length > 0) && (grantResults[0]==PackageManager.PERMISSION_GRANTED))
        {
            obtenerCorreosUsuario();
            Log.e(getClass().getCanonicalName(), "PERIMSO CONSEDIDO");
          obtenerCorreosUsuario();
        }else{
            Log.e(getClass().getCanonicalName(), "PERMISO DENEGADO");
            finish();

        }
    }
    private void pedirPermiso(){
        //TODO PEDIR PERMISOS
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS))
        {
            //Mostrar Mensaje explicativo AlertDialog para pedir permisos
        }
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.GET_ACCOUNTS}, 100);

    }
    boolean tienesPermisos(){
        boolean tienePermiso = false;
        tienePermiso = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS)==PackageManager.PERMISSION_GRANTED);
       return tienePermiso;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (tienesPermisos()){
            obtenerCorreosUsuario();
        }else {
            pedirPermiso();
        }

        Button button =(Button)findViewById(R.id.miButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VerCorreos.class));
            }
        });


    }
}
