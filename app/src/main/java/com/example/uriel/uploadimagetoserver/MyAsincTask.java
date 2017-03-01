package com.example.uriel.uploadimagetoserver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by uriel on 3/1/2017.
 */

public class MyAsincTask extends AsyncTask<String,Void,Boolean> {
    private ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    private Context context;

    public MyAsincTask(Context context){
        this.context=context;
        builder = new AlertDialog.Builder(context);
    }

    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog=ProgressDialog.show(context, "por favor espere", "subiendo");
    }
    @Override
    protected Boolean doInBackground(String... params) {
        Boolean r = false;
        ApiRest apiRest=new ApiRest();
        try{
            r=apiRest.uploadPhoto(params[0]);
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return r;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progressDialog.dismiss();
        if (aBoolean){
            builder.setMessage("imagen subida al servidor").setTitle("JC le informa").setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            }).create().show();
        }
        else {

            builder.setMessage("no se pudo abrir la imagen").setTitle("JC le informa").setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            }).create().show();
        }

    }
}
