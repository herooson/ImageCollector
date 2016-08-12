package com.android.hyoonseol.imagecollector.api;

import android.content.Context;
import android.util.JsonReader;

import com.android.hyoonseol.imagecollector.R;
import com.android.hyoonseol.imagecollector.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 서버와의 api 통신을 담당
 * Created by Administrator on 2016-07-30.
 */

public class ICApi {

    private String TAG = "ICApi";

    public static String DEFAULT_SIZE = "18";

    public static final String SORT_ACCU = "accu";
    public static final String SORT_DATE = "date";
    public static final String SORT_NAME = "name";

    private static final int CONN_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;

    private static final String METHOD_GET = "GET";

    private static final String OUTPUT_JSON = "json";

    private static final String API = "https://apis.daum.net";
    private static final String SEARCH = "/search/image";

    private Context mContext;

    public ICApi(Context context) {
        mContext = context;
    }

    //TODO cache



    public JSONObject getSearchResult(String keyword, int page, String sortType) {
        JSONObject jsonObject = null;

        try {
            Map<String, String> params = new HashMap<>();
            params.put("apikey", mContext.getResources().getString(R.string.api_key));
            params.put("q", URLEncoder.encode(keyword, "utf-8"));
            params.put("result", DEFAULT_SIZE);
            params.put("pageno", page + "");
            params.put("sort", sortType);
            params.put("output", OUTPUT_JSON);

            String url = getUrl(API, SEARCH, params);
            File cacheFile = new File(mContext.getCacheDir(), url.replace("/", ""));
            if (cacheFile.exists()) {
                long cacheTime = cacheFile.lastModified();
                // 10분 단위 캐시
                cacheTime += 1000 * 60 * 10;
                if (cacheTime > System.currentTimeMillis()) {
                    jsonObject = loadCacheFile(cacheFile);
                    Log.d(TAG, "api cache valid time = " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(cacheTime)));
                }
            }

            if (jsonObject == null) {
                try {
                    jsonObject = getHttpResponse(new URL(url));
                    saveCacheFile(url.replace("/", ""), jsonObject);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
        }
        return  jsonObject;
    }

    private JSONObject loadCacheFile(File file) {
        JSONObject jsonObject = null;
        try {
            StringBuffer fileData = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            char[] buf = new char[1024];
            int numRead;
            while ((numRead=reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
            jsonObject = new JSONObject(fileData.toString());
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return jsonObject;
    }

    private void saveCacheFile(String fileName, JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        try {
            File file = new File(mContext.getCacheDir(), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsolutePath());
            fw.write(jsonObject.toString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getHttpResponse(URL url) {
        Log.d(TAG, "url = " + url);
        JSONObject jsonObject = null;

        try {
            HttpURLConnection conn;

            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(CONN_TIMEOUT * 1000);
            conn.setReadTimeout(READ_TIMEOUT * 1000);
            conn.setRequestMethod(METHOD_GET);
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setDoInput(true);

            InputStream is;
            ByteArrayOutputStream baos;

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData;
                int nLength;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                jsonObject = new JSONObject(new String(byteData));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return jsonObject;
    }

    private String getUrl(String api, String method, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(api);
        sb.append(method);

        if (params != null && params.size() > 0) {
            sb.append("?");

            for( String key : params.keySet() ){
                sb.append(key);
                sb.append("=");
                sb.append(params.get(key));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
