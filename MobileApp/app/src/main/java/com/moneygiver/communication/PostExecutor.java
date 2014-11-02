package com.moneygiver.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;

import org.apache.commons.codec.binary.Base64;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by Szymon on 2014-10-29.
 */
public class  PostExecutor implements Runnable {
    public String result = "dupa";

    public String execute(String passedUrl, String message) throws IOException {
//        String urlParameters = json.toString();

//        https://myconfluence.atlassian.net/wiki/rest/api/content/
//        String encoding = new String(Base64.encodeBase64(credentials.getBytes("UTF-8")),
//                "UTF-8");
        String url = passedUrl;
        URL object=new URL(url);
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
//        con.setRequestProperty("Authorization", "Basic " + encoding);
        OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
        wr.write(message);
        wr.flush();
        //display what returns the POST request
        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        if(HttpResult ==HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println(""+sb.toString());
        }else{
            System.out.println(con.getResponseMessage());
        }

        return sb.toString();
    }

    @Override
    public void run() {
        try {
         result = execute("http://178.62.111.179/userCredentials", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
