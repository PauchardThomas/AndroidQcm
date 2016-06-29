package com.iia.cdsm.qcm.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.cdsm.qcm.Data.ProposalSqlLiteAdapter;
import com.iia.cdsm.qcm.Data.QcmSqlLiteAdapter;
import com.iia.cdsm.qcm.Data.QuestionSqlliteAdapter;
import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.Entity.Proposal;
import com.iia.cdsm.qcm.Entity.Qcm;
import com.iia.cdsm.qcm.Entity.Question;
import com.iia.cdsm.qcm.Entity.User;
import com.iia.cdsm.qcm.R;
import com.iia.cdsm.qcm.webservice.QcmWSAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Thom' on 30/03/2016.
 */
public class QcmActivity extends Activity {

    /**
     * Question number
     */
    private static final String NUMBER = "number";
    /**
     * Duree default value
     */
    private static String duree = "Durée non définie";

    /**
     * TextViews Qcm namme , dureeValue
     */
    TextView tvQcmLibelle, tvDureeValue;
    /**
     * Qcm
     */
    Qcm qcm;
    /**
     * Category
     */
    Category category;
    /**
     * User
     */
    User user;
    /**
     * Button begin Qcm
     */
    Button btBegin;

    /**
     * Create activity
     *
     * @param savedInstanceState instanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Set View
         */
        setContentView(R.layout.activity_qcm);

        /**
         * Get items from View
         */
        qcm = (Qcm) getIntent().getSerializableExtra(Qcm.SERIAL);
        category = (Category) getIntent().getSerializableExtra(Category.SERIAL);
        user = (User) getIntent().getSerializableExtra(User.SERIAL);
        tvQcmLibelle = (TextView) findViewById(R.id.tvQcmLibelle);
        tvDureeValue = (TextView) findViewById(R.id.tvDureeValue);
        btBegin = (Button) findViewById(R.id.btCommencer);


        /**
         * Set items to view
         */
        tvQcmLibelle.setText(qcm.getLibelle());
        if (qcm.getDuration() != null) {
            duree = new SimpleDateFormat("HH:mm:ss").format(qcm.getDuration());
        }
        tvDureeValue.setText(duree);

        /**
         * Click to start the Qcm
         */
        btBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * Get all informations about the qcm clicked
                 */
                QcmWSAdapter.get(qcm.getIdServer(), new JsonHttpResponseHandler() {

                    /**
                     * If server response
                     * @param statusCode response StatusCode
                     * @param headers response headers
                     * @param response response
                     */
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            //**************************//
                            //** Convert json to Qcm **//
                            //************************//
                            Qcm qcm = QcmWSAdapter.jsonObjectToItem(response);

                            //************************//
                            //** Set id to the Qcm **//
                            //**********************//
                            QcmSqlLiteAdapter qcmSqlLiteAdapter = new QcmSqlLiteAdapter(QcmActivity.this);
                            qcmSqlLiteAdapter.open();
                            qcm.setId(qcmSqlLiteAdapter.getQcm(qcm.getIdServer()).getId());
                            qcmSqlLiteAdapter.close();


                            //**************************//
                            //** Get Qcm's proposals **//
                            //************************//
                            ProposalSqlLiteAdapter proposalSqlLiteAdapter = new ProposalSqlLiteAdapter(QcmActivity.this);
                            proposalSqlLiteAdapter.open();
                            ArrayList<Question> questions = qcm.getQuestions();

                            //*********************************************//
                            //** Insert / Update Question and Proposals **//
                            //*******************************************//
                            QuestionSqlliteAdapter questionSqlliteAdapter = new QuestionSqlliteAdapter(QcmActivity.this);
                            questionSqlliteAdapter.open();

                            for (Question question : questions) {

                                if (questionSqlliteAdapter.getQuestion(question.getIdServer()) == null) {

                                    question.setQcm(qcm);
                                    question.setId(questionSqlliteAdapter.insert(question));
                                    ArrayList<Proposal> proposals = question.getProposals();

                                    for (Proposal proposal : proposals) {
                                        proposal.setQuestion(question);
                                        proposalSqlLiteAdapter.insert(proposal);
                                    }
                                }
                            }
                            proposalSqlLiteAdapter.close();
                            questionSqlliteAdapter.close();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //*********************************************//
                        //** Create list of questions and proposals **//
                        //*******************************************//
                        QuestionSqlliteAdapter questionSqlliteAdapter = new QuestionSqlliteAdapter(QcmActivity.this);
                        questionSqlliteAdapter.open();

                        ArrayList<Question> questions = questionSqlliteAdapter.getQuestions(qcm.getId(), QcmActivity.this);
                        HashMap hashmapQuestions = new HashMap();
                        int counter = 1;
                        for (Question question : questions) {

                            hashmapQuestions.put(counter, question);
                            counter++;

                        }
                        questionSqlliteAdapter.close();


                        //******************************************//
                        //** Create intent , start next activity **//
                        //****************************************//
                        Intent i = new Intent(QcmActivity.this, QuestionActivity.class);
                        i.putExtra(Question.SERIAL, hashmapQuestions);
                        i.putExtra(NUMBER, 1);
                        i.putExtra(Qcm.SERIAL, qcm);
                        i.putExtra(Category.SERIAL, category);
                        i.putExtra(User.SERIAL, user);

                        startActivity(i);
                    }

                    /**
                     * If server doesn't response
                     * @param statusCode statusCode
                     * @param headers headers
                     * @param throwable error
                     * @param errorResponse errorResponse
                     */
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
        });
    }
}
