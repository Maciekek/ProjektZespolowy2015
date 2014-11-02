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
    private String url;
    private String login;
    private String password;

    public PostExecutor(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    private String makeJSON(String login, String password) {

        return "{\"userCredentials\": {\"login\": \"" +login +"\", \"password\":\"" + password +"\"}}";
    }

    public String execute() throws IOException {
//        String urlParameters = json.toString();

//        https://myconfluence.atlassian.net/wiki/rest/api/content/
//        String encoding = new String(Base64.encodeBase64(credentials.getBytes("UTF-8")),
//                "UTF-8");

        String json = makeJSON(login, password);
        URL object=new URL("http://178.62.111.179/userCredentials");
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
//        con.setRequestProperty("Authorization", "Basic " + encoding);
        OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
        wr.write(json);
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
         result = "Wysłano: "+makeJSON(login, password)+ "\n Odebrano: "+execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
