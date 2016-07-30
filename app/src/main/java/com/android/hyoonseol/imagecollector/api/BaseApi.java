package com.android.hyoonseol.imagecollector.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016-07-30.
 */

public class BaseApi {

    public static final String DEFAULT_SIZE = "100";

    public static final String SORT_ACCU = "accu";
    public static final String SORT_DATE = "date";

    private static final int CONN_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;

    private static final String METHOD_GET = "GET";

    private static final String OUTPUT_JSON = "json";

    private static final String API_IMG_SEARCH = "https://apis.daum.net/search/image";

    //TODO cache

    public JSONObject getSearchResult(String keyword, int page, String sortType) {
        JSONObject jsonObject = new JSONObject();

        Map<String, String> params = new HashMap<>();
        // TODO. api key
        params.put("apikey", "");
        params.put("q", keyword);
        params.put("result", DEFAULT_SIZE);
        params.put("pageno", page + "");
        params.put("sort", sortType);
        params.put("output", OUTPUT_JSON);

        try {
            jsonObject = getHttpResponse(new URL(getUrl(API_IMG_SEARCH, params)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    private JSONObject getHttpResponse(URL url) {
        JSONObject jsonObject = null;

        try {
            HttpURLConnection conn;

            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(CONN_TIMEOUT * 1000);
            conn.setReadTimeout(READ_TIMEOUT * 1000);
            conn.setRequestMethod(METHOD_GET);
            conn.setRequestProperty("Cache-Control", "no-cache");
//            conn.setRequestProperty("Content-Type", "application/json");  // request body type
//            conn.setRequestProperty("Accept", "application/json");    // response type
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

    private String getUrl(String api, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(api);

        if (params != null && params.size() > 0) {
            Iterator<String> keys = params.keySet().iterator();
            sb.append("?");

            while( keys.hasNext() ){
                sb.append(params.get(keys.next()));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
