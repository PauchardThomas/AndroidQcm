package com.iia.cdsm.qcm.webservice;

import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.Entity.Proposal;
import com.iia.cdsm.qcm.Entity.Qcm;
import com.iia.cdsm.qcm.Entity.Question;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thom' on 24/02/2016.
 */
public class QcmWSAdapter {

    /**
     * Api Base Url
     */
    private static final String BASE_URL = "http://192.168.1.39/app_dev.php/api";
    /**
     * Api qcm URL
     */
    private static final String ENTITY = "qcm";
    /**
     * Api qcms URL
     */
    private static final String ENTITY2 = "qcms";
    /**
     * api list qcm
     */
    private static final String ENTITY_LIST = "lists";
    /**
     * Library AsyncHttpClient
     */
    private static AsyncHttpClient client = new AsyncHttpClient();
    /**
     * Qcm id
     */
    private static final String ID = "id";
    /**
     * Qcm name
     */
    private static final String LIBELLE = "libelle";
    /**
     * Qcm duration
     */
    private static final String DURATION = "duration";
    /**
     * Qcm points
     */
    private static final String NB_POINTS = "nbPoints";
    /**
     * Question points
     */
    private static final String POINTS = "points";
    /**
     * Question's proposal
     */
    private static final String QUESTION_PROP = "question_prop";
    /**
     * Qcm category
     */
    private static final String CATEGORY = "category";
    /**
     * Qcm questions
     */
    private static final String QUESTION_ID = "question_id";
    /**
     * Date format
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss+SSSS";

    /**
     * Get all Qcm for one Category
     *
     * @param id_category Category id
     * @param resp        response
     */
    public static void getAll(int id_category, AsyncHttpResponseHandler resp) {
        String url = String.format("%s/%s/%s/%s", BASE_URL, ENTITY_LIST, id_category, ENTITY);
        client.get(url, resp);
        client.setTimeout(3000);
    }

    /**
     * Get Qcm data for one Qcm
     *
     * @param id_qcm qcm id
     * @param resp   response
     */
    public static void get(int id_qcm, AsyncHttpResponseHandler resp) {
        String url = String.format("%s/%s/%s", BASE_URL, ENTITY2, id_qcm);
        client.get(url, resp);
        client.setTimeout(3000);
    }

    /**
     * Convert jsonArray to list of Qcm
     *
     * @param json jsonArray
     * @return list of Qcm
     * @throws JSONException
     */
    public static ArrayList<Qcm> jsonArrayToItem(JSONArray json) throws JSONException {

        ArrayList<Qcm> item = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {

            Qcm qcm = new Qcm();
            Category category = new Category();
            Date duration = null;

            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            JSONObject jsonobject = json.getJSONObject(i);
            int idServer = jsonobject.getInt(ID);
            String libelle = jsonobject.getString(LIBELLE);
            int id_category = jsonobject.getInt(CATEGORY);

            category.setIdServer(id_category);
            try {
                duration = formatter.parse(jsonobject.getString(DURATION));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int nbPoints = jsonobject.getInt(NB_POINTS);

            qcm.setIdServer(idServer);
            qcm.setLibelle(libelle);
            qcm.setDuration(duration);
            qcm.getDuration();
            qcm.setNbPoints(nbPoints);
            qcm.setCategory(category);
            item.add(qcm);
        }

        return item;
    }

    /**
     * Convert json Object to Qcm
     *
     * @param json json Object
     * @return Qcm
     * @throws JSONException
     */
    public static Qcm jsonObjectToItem(JSONObject json) throws JSONException {


        Qcm qcm = new Qcm();
        int idQcm = json.getInt(ID);
        ArrayList<Question> questions = new ArrayList<>();

        JSONArray questionsArray = json.getJSONArray(QUESTION_ID);

        for (int i = 0; i < questionsArray.length(); i++) {

            Question question = new Question();

            int questionIdServer = questionsArray.getJSONObject(i).getInt(ID);
            String questionLibelle = questionsArray.getJSONObject(i).getString(LIBELLE);
            int questionPoints = questionsArray.getJSONObject(i).getInt(POINTS);

            ArrayList<Proposal> proposals = new ArrayList<>();
            JSONArray proposalsArray = new JSONArray(questionsArray.getJSONObject(i).getString(QUESTION_PROP));

            for (int j = 0; j < proposalsArray.length(); j++) {

                Proposal proposal = new Proposal();
                int proposalIdServer = proposalsArray.getJSONObject(j).getInt(ID);
                String proposalLibelle = proposalsArray.getJSONObject(j).getString(LIBELLE);

                proposal.setIdServer(proposalIdServer);
                proposal.setLibelle(proposalLibelle);

                proposals.add(proposal);

            }
            question.setIdServer(questionIdServer);
            question.setPoints(questionPoints);
            question.setProposals(proposals);
            question.setLibelle(questionLibelle);
            questions.add(question);

        }


        qcm.setIdServer(idQcm);
        qcm.setQuestions(questions);
        return qcm;
    }


}
