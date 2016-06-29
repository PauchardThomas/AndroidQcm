package com.iia.cdsm.qcm.webservices;

import android.os.Looper;
import android.os.Message;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Handler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thom' on 23/04/2016.
 */
public class CategoryWSTest {
    /**
     * Base URL
     */
    private static final String BASE_URL = "http://192.168.100.152/app_dev.php/api";
    /**
     * Entity URL
     */
    private static final String ENTITY = "categories";
    /**
     * ID User URL
     */
    private static final int ID_USER = 1;
    /**
     * Response expected
     */
    private static final String RESPONSE = "HTTP/1.1 200 OK";

    /**
     * Test if webservice return a response 200 (OK)
     *
     * @throws IOException
     */
    @Test
    public void CategoryWSTest() throws IOException {


        HttpClient httpclient = HttpClientBuilder.create().build();

        HttpGet httpget = new HttpGet(BASE_URL + "/" + ENTITY + "/" + ID_USER);

        HttpResponse httpResponse = httpclient.execute(httpget);

        assertEquals(RESPONSE, httpResponse.getStatusLine().toString());

    }
}

