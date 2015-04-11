package me.jaxbot.pyconautowifi;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 4/11/15.
 */
public class LoginToWifi {
    static int giveUp = 0;
    public static void login() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                System.out.println("Goin' async");
                String str = getHTTPString("http://example.com/");
                if (str.contains("Example Domain")) {
                    System.out.println("Already signed in");
                    return null;
                }
                System.out.println(getHTTPString("http://webauth.congresmtl.com/fs/customwebauth/login.html?switch_url=http%3A%2F%2Fwebauth.congresmtl.com%2Flogin.html%2F&ap_mac=5c:50:15:e6:16:f0&client_mac=bc:f5:ac:e2:41:be&wlan=PyCon+2015&redirect=example.com/"));
                System.out.println(str);
                try {
                    String[] ap = str.split("ap_mac=");
                    ap = ap[1].split("&wlan");
                    String[] ap_mac = ap[0].split("&");
                    String mac_ap = ap_mac[0];
                    String[] client_mac = ap_mac[1].split("client_mac=");
                    String mac_client = client_mac[1];
                    System.out.println("AP: " + mac_ap);
                    System.out.println("Client: " + mac_client);

                    DefaultHttpClient httpclient = new DefaultHttpClient();

                    HttpPost httppost = new HttpPost("http://webauth.congresmtl.com/login.html?ap_mac=" + mac_ap + "&client_mac=" + mac_client + "&wlan=Pycon+2015&redirect=example.com/");
                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("redirect_url", "http://example.com/"));
                        nameValuePairs.add(new BasicNameValuePair("buttonClicked", "4"));
                        nameValuePairs.add(new BasicNameValuePair("err_flag", "0"));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                    } catch (ClientProtocolException e) {
                        System.out.println(e.toString());
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    }
                } catch (Exception e) {
                    System.out.println("not on network.");
                    try {
                        Thread.sleep(5000);
                        giveUp++;
                        if (giveUp < 5)
                            LoginToWifi.login();
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }


                return null;
            }
        }.execute(null, null, null);
    }

    private static String getHTTPString(String url) {
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent", "Mozilla/4.0 (compatible; Chrome)");

            HttpResponse response = httpclient.execute(httpget);

            InputStream inputStream = response.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String result = "";

            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();

            return result;
        } catch (Exception e) {
            System.out.println(e);
        }

        return "";
    }

}
