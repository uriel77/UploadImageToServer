package com.example.uriel.uploadimagetoserver;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by uriel on 3/1/2017.
 */

public class ApiRest {
    private final String HTTP_EVENT="https://otroejmplo.000webhostapp.com/API/apirest.php";



    public Boolean uploadPhoto(String encodedImege)throws ClientProtocolException, IOException, JSONException{
        HttpClient htttpclient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(HTTP_EVENT);
        httpPost.addHeader("Content-Type", "application/json");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("encodeImage", encodedImege);
        StringEntity stringEntity=new StringEntity(jsonObject.toString());
        stringEntity.setContentType((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);

        HttpResponse response = htttpclient.execute(httpPost);
        String jsonResult= inputStreamToString(response.getEntity().getContent()).toString();
        JSONObject object = new JSONObject(jsonResult);
        if (object.getString("Result").equals("200")){
            return true;
        }
        return false;
    }
    private StringBuilder inputStreamToString(InputStream is){
        String line="";
        StringBuilder stringBuilder=new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line=rd.readLine())!=null){
                stringBuilder.append(line);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return stringBuilder;
    }
}
