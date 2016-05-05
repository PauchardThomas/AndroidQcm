package com.iia.cdsm.qcm.webservices;

import com.loopj.android.http.HttpGet;

import org.junit.Test;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thom' on 26/04/2016.
 */
public class UserWSTest {

    private static final String BASE_URL = "http://192.168.1.39/app_dev.php/api";
    private static final String ENTITY = "users";
    private static final int ID_USER = 1;
    private static final String RESPONSE = "HTTP/1.1 200 OK";

    @Test
    public void UserWSTest() throws IOException {


        HttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost(BASE_URL + "/" + ENTITY );

        HttpResponse httpResponse = httpclient.execute(httpPost);

        assertEquals(RESPONSE,httpResponse.getStatusLine().toString());

    }
}
