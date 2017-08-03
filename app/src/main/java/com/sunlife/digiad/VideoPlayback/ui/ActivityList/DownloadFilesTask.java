package com.sunlife.digiad.VideoPlayback.ui.ActivityList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;
import android.content.Context;

/**
 * Created by HKT7 on 7/13/2017.
 */

public class DownloadFilesTask extends AsyncTask<String, Integer, Integer> {

    private Context ctx;
    public DownloadFilesTask(Context _ctx)
    {
        ctx = _ctx;
    }

    protected Integer doInBackground(String... urls)  {
        int count = urls.length;;
        int totalSize = 0;
        for (int i = 0; i < count; i++) {
            try {
                totalSize += Download(urls[i]);
            }
            catch (Exception e)
            {
                return 0;
            }
            publishProgress((int) ((i / (float) count) * 100));
            // Escape early if cancel() is called
            if (isCancelled()) break;
        }
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
        // setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        // showDialog("Downloaded " + result + " bytes");
    }

    public int Download(String url) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(url);

        // Execute the request
        HttpResponse response;

        InputStream input = null;
        try {
            response = httpclient.execute(httpget);
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            if (entity != null) {

                // A Simple JSON Response Read
                input = entity.getContent();
            }


        } catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception();
        }


        String fileName = "";
        if(url.toString().contains(".xml"))
        {
            fileName = "SunlifeAR2.xml";//"enfield.xml";
        }
        if(url.toString().contains(".dat"))
        {
            fileName = "SunlifeAR2.dat";//"enfield.dat";
        }
        if(url.toString().contains("TargetVideoMapping.xml"))
        {
            fileName = "TargetVideoMapping.xml";//"enfield.dat";
        }
        if(input!=null) {
            File file = new File(ctx.getFilesDir().getAbsolutePath(), fileName); //
            OutputStream output = null;
            try {
                output = new FileOutputStream(file, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new Exception();
            }
            try {
                try {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;

                    while ((read = input.read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                    }
                    output.flush();
                } finally {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace(); // handle exception, define IOException and others
                throw new Exception();
            }
        }
        return 0;
    }
}


