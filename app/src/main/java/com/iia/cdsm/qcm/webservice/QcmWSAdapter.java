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

    private static final String BASE_URL = "http://192.168.1.39/app_dev.php/api";
    private static final String ENTITY = "qcm";
    private static final String ENTITY2 = "qcms";
    private static final String ENTITY_LIST = "lists";
    private static final int VERSION = 1;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String ID = "id";
    private static final String LIBELLE = "libelle";
    private static final String DATE_PUBLI = "date_Publi";
    private static final String DATE_FIN = "date_Fin";
    private static final String NB_POINTS = "nb_points";
    private static final String CATEGORY_ID = "category_id";


    public static void getAll(int id_category, AsyncHttpResponseHandler resp) {
        String url = String.format("%s/%s/%s/%s", BASE_URL, ENTITY_LIST, id_category, ENTITY);
        client.get(url, resp);
        client.setTimeout(10000);
    }

    public static void get(int id_qcm, AsyncHttpResponseHandler resp) {
        String url = String.format("%s/%s/%s", BASE_URL, ENTITY2, id_qcm);
        client.get(url, resp);
        client.setTimeout(10000);
    }

    public static ArrayList<Qcm> jsonArrayToItem(JSONArray json) throws JSONException {

        ArrayList<Qcm> item = new ArrayList<Qcm>();

        for (int i = 0; i < json.length(); i++) {
            Qcm qcm = new Qcm();
            Category category = new Category();
            Date duration = null;
            //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSSS");
            JSONObject jsonobject = json.getJSONObject(i);
            int idServer = jsonobject.getInt("id");
            String libelle = jsonobject.getString("libelle");
            int id_category = jsonobject.getInt("category");

            category.setIdServer(id_category);
            try {
                duration = formatter.parse(jsonobject.getString("duration"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int nbPoints = jsonobject.getInt("nbPoints");

            qcm.setIdServer(idServer);
            qcm.setLibelle(libelle);
            qcm.setDuration(duration);
            Date mydate = qcm.getDuration();
            qcm.setNbPoints(nbPoints);
            qcm.setCategory(category);
            item.add(qcm);
        }

        return item;
    }

    public static Qcm jsonObjectToItem(JSONObject json) throws JSONException {


        Qcm qcm = new Qcm();
        int idQcm = json.getInt("id");
        ArrayList<Question> questions = new ArrayList<>();

        JSONArray questionsArray = json.getJSONArray("question_id");

        for (int i = 0; i < questionsArray.length(); i++) {
            Question question = new Question();
            int questionIdServer = questionsArray.getJSONObject(i).getInt("id");
            String questionLibelle = questionsArray.getJSONObject(i).getString("libelle");
            int questionPoints = questionsArray.getJSONObject(i).getInt("points");

            ArrayList<Proposal> proposals = new ArrayList<>();
            JSONArray proposalsArray = new JSONArray(questionsArray.getJSONObject(i).getString("question_prop"));

            for (int j = 0; j < proposalsArray.length(); j++) {

                Proposal proposal = new Proposal();
                int proposalIdServer = proposalsArray.getJSONObject(j).getInt("id");
                String proposalLibelle = proposalsArray.getJSONObject(j).getString("libelle");

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
