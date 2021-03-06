package com.iia.cdsm.qcm.webservice;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.util.Log;

import com.iia.cdsm.qcm.Data.UserSqlLiteAdapter;
import com.iia.cdsm.qcm.Entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.ssl.SSLConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.SSLContextBuilder;
import cz.msebera.android.httpclient.conn.ssl.TrustSelfSignedStrategy;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.params.BasicHttpParams;

/**
 * Created by Thom' on 21/03/2016.
 */
public class UserWSAdapter {

    /**
     * Api Base Url
     */
    private static final String BASE_URL = "http://192.168.100.212/qcm2/web/app_dev.php/api";
    /**
     * User entity
     */
    private static final String ENTITY = "users";
    /**
     * Username
     */
    private static final String USERNAME ="username";
    /**
     * User password
     */
    private static final String PASSWORD ="password";
    /**
     * User id
     */
    private static final String ID ="id";

    /**
     * Login a User without Library
     * @param user User
     * @param context activity context
     * @return User
     * @throws JSONException
     */
    public static User Login(User user,Context context) throws JSONException {
        InputStream inputStream = null;
        String result = "";
        String validate ="";
        User myuser = null;
        try {


            // 1. create HttpClient
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            //1.1 set time out
            RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
            requestConfigBuilder.setConnectionRequestTimeout(3000).setMaxRedirects(1);
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(BASE_URL + "/" + ENTITY);
            httpPost.setConfig(requestConfigBuilder.build());
            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(USERNAME, user.getUsername());
            jsonObject.accumulate(PASSWORD, user.getPassword());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            System.out.println(e);
            Log.d("InputStream", e.getLocalizedMessage());
        }

        if (result.equals("[]")) {
            // Mauvais identifiants.
        } else {
            // Insert user.
            JSONArray jsonArray = new JSONArray(result);
            jsonToUser(jsonArray);
            UserSqlLiteAdapter userSqlLiteAdapter = new UserSqlLiteAdapter(context);
            userSqlLiteAdapter.open();

             myuser = jsonToUser(jsonArray);

           User isUserExist = userSqlLiteAdapter.getUser(myuser.getIdServer());
            if (isUserExist == null) {
               myuser.setId(userSqlLiteAdapter.insert(myuser));
            } else {
                myuser.setId(userSqlLiteAdapter.update(myuser));
            }

            userSqlLiteAdapter.close();
        }
        return myuser;
    }

    /**
     * Convert response to String
     * @param inputStream response
     * @return Response to String
     * @throws IOException
     */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    /**
     * Convert json to User
     * @param json json
     * @return User
     * @throws JSONException
     */
    public static User jsonToUser(JSONArray json) throws JSONException {
        User user = new User(0,null,null,0);

        for (int i = 0; i < json.length(); i++) {
            int test = json.length();
            JSONObject jsonobject = json.getJSONObject(i);

            int idServer = jsonobject.getInt(ID);
            String username = jsonobject.getString(USERNAME);
            String password = jsonobject.getString(PASSWORD);

            user.setUsername(username);
            user.setPassword(password);
            user.setIdServer(idServer);
        }
        return user;
    }

}

