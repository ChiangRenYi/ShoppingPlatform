package com.example.wmnl_yo.shoppingplatform.database;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.example.wmnl_yo.shoppingplatform.Constants;
import com.example.wmnl_yo.shoppingplatform.R;
import com.example.wmnl_yo.shoppingplatform.fragment.HealthHeightFragment;
import com.example.wmnl_yo.shoppingplatform.fragment.HealthManageFragment;
import com.example.wmnl_yo.shoppingplatform.object.HealthHeightObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sandy on 2017/8/30.
 */

public class GetHealthHeightFragment extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        Log.d("55125", "getHealthHeightFragment...");
        String url = Constants.SERVER_URL + "getHealthHeightFragment.php";
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;
        try {
            URL tkuUrl = new URL(url);
            connection = (HttpURLConnection) tkuUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(10000);
            //傳值
            Uri.Builder builder = new Uri.Builder()
                    //傳值到php
                    .appendQueryParameter("account",Constants.ACCOUNT.trim())
                    .appendQueryParameter("SD_Id", HealthManageFragment.SDId.trim());

            String query = builder.build().getEncodedQuery();

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {/*success*/
                InputStream inputStream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.d("55125", "get inputStream error");
                } else {
                    reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String inputLine;
                    while ((inputLine = reader.readLine()) != null)
                        buffer.append(inputLine + "\n");
                    if (buffer.length() == 0) {
                        // Stream was empty. No point in parsing.
                        Log.d("55125", "nothing");
                        HealthHeightObject.ITEMS.clear();
                    } else {
                        result = buffer.toString();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("55125", e.toString());
            return null;
        } finally {
                /*close urlConnection*/
            if (connection != null) {
                connection.disconnect();
            }
                /*close inputStreamReader*/
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("55125", "Error", e);
                }
            }
            Log.d("55125",result);
            int j = 0;

            String[] healthHeight = result.split("<QQ>");
            String[] healthHeight_value = new String[healthHeight.length - 1];
            String[] healthHeight_date = new String[healthHeight.length - 1];
            String[] healthHeight_time = new String[healthHeight.length - 1];

            HealthHeightObject.ITEMS.clear();
            HealthHeightObject dim = new HealthHeightObject();

            for (int i = 0; i < healthHeight.length - 1; i++) {
                String[] healthHeightInf = new String[5];
                healthHeightInf = healthHeight[i].split("@#");
                healthHeight_value[j] = healthHeightInf[1];
                healthHeight_date[j] = healthHeightInf[2];
                healthHeight_time[j] = healthHeightInf[3];

                dim.addItem(new HealthHeightObject.HealthHeightObjectItem(R.drawable.ic_height,
                        healthHeightInf[1],"Cm",healthHeightInf[2]+"("+healthHeightInf[3]+")"));

                j++;
                Log.d("55125", healthHeightInf[1]+","+"Cm"+","+healthHeightInf[2]+"("+healthHeightInf[3]+")");
            }
            return result;

        }
    }

    protected void onPostExecute(String s) {

        Log.d("55125","notifyDataSetChanged");
        HealthHeightFragment.hAdapter.notifyDataSetChanged();
    }
}
