package com.iia.cdsm.qcm.webservices;

import com.loopj.android.http.HttpGet;

import org.junit.Test;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thom' on 26/04/2016.
 */
public class QcmWSTest {
    private static final String BASE_URL = "http://192.168.1.39/app_dev.php/api";
    private static final String ENTITY = "qcm";
    private static final String ENTITY_LIST = "lists";
    private static final int ID = 1;
    private static final String RESPONSE = "HTTP/1.1 200 OK";

    @Test
    public void QcmWSTest() throws IOException {


        HttpClient httpclient = HttpClientBuilder.create().build();

        HttpGet httpget = new HttpGet(BASE_URL + "/" + ENTITY_LIST + "/" + ID + "/" + ENTITY);

        HttpResponse httpResponse = httpclient.execute(httpget);

        assertEquals(RESPONSE,httpResponse.getStatusLine().toString());

    }
}
