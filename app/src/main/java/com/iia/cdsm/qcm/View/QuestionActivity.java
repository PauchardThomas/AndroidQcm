package com.iia.cdsm.qcm.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.cdsm.qcm.Data.ProposalUserSqLiteAdapter;
import com.iia.cdsm.qcm.Data.QuestionSqlliteAdapter;
import com.iia.cdsm.qcm.Entity.Category;
import com.iia.cdsm.qcm.Entity.Proposal;
import com.iia.cdsm.qcm.Entity.ProposalUser;
import com.iia.cdsm.qcm.Entity.Qcm;
import com.iia.cdsm.qcm.Entity.Question;
import com.iia.cdsm.qcm.Entity.User;
import com.iia.cdsm.qcm.R;
import com.iia.cdsm.qcm.webservice.QuestionWSAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Thom' on 07/04/2016.
 */
public class QuestionActivity extends Activity {

    /**
     * ArrayAdapter
     */
    ArrayAdapter dataAdapter;
    /**
     * List of proposals
     */
    ArrayList<Proposal> proposals;
    /**
     * Response ListView
     */
    ListView lvReponse;
    /**
     * TextViews question ; qcm , category , Username
     */
    TextView tvQuestion, tvQcm, tvCategory, tvUsername;
    /**
     * Buttons previous , next
     */
    Button btPrevious, btNext;
    /**
     * Question number
     */
    int number_question;
    /**
     * Question hashmap
     */
    HashMap questionHashMap;
    /**
     * Qcm
     */
    Qcm qcm;
    /**
     * User
     */
    User user;
    /**
     * Category
     */
    Category category;

    /**
     * Activity create
     *
     * @param savedInstanceState instanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Set View
         */
        setContentView(R.layout.activity_question);
        /**
         * Get View items
         */

        lvReponse = (ListView) findViewById(R.id.lvResponses);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvQcm = (TextView) findViewById(R.id.tvQcm);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        btPrevious = (Button) findViewById(R.id.btPrevious);
        btNext = (Button) findViewById(R.id.btNext);

        /**
         * Get intents
         *
         */
        qcm = (Qcm) getIntent().getSerializableExtra(Qcm.SERIAL);
        user = (User) getIntent().getSerializableExtra(User.SERIAL);
        category = (Category) getIntent().getSerializableExtra(Category.SERIAL);
        number_question = getIntent().getIntExtra("number", 1);
        questionHashMap = (HashMap) getIntent().getSerializableExtra(Question.SERIAL);


        /**
         * Set items to View
         */
        tvQcm.setText(qcm.getLibelle());
        tvUsername.setText(user.getUsername());
        tvUsername.setId((int) user.getId());
        tvCategory.setText(category.getLibelle());
        tvCategory.setId((int) category.getId());
        tvQuestion.setText(questionHashMap.get(number_question).toString());
        Question question = (Question) questionHashMap.get(number_question);
        tvQuestion.setId(question.getId());
        proposals = question.getProposals();


        /**
         * Call Adapter
         */
        dataAdapter = new MyCustomAdapter(this, R.layout.row_question, proposals, tvQuestion.getId(),
                user.getId(), qcm.getId());
        lvReponse.setAdapter(dataAdapter);


        /**
         * Click on Previous
         */
        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number_question > 1) {


                    number_question = number_question - 1;
                    btNext.setText("Suivant");
                    tvQuestion.setText(questionHashMap.get(number_question).toString());
                    Question question = (Question) questionHashMap.get(number_question);
                    tvQuestion.setId(question.getId());
                    proposals = question.getProposals();
                    dataAdapter = new MyCustomAdapter(QuestionActivity.this, R.layout.row_question,
                            proposals, tvQuestion.getId(), user.getId(), qcm.getId());
                    lvReponse.setAdapter(dataAdapter);
                } else {
                    Toast.makeText(QuestionActivity.this, "On est a la 1ere question", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Click on Next
         */
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * If next question exist
                 */
                if (number_question < questionHashMap.size()) {

                    number_question = number_question + 1;
                    tvQuestion.setText(questionHashMap.get(number_question).toString());
                    Question question = (Question) questionHashMap.get(number_question);
                    tvQuestion.setId(question.getId());
                    proposals = question.getProposals();
                    dataAdapter = new MyCustomAdapter(QuestionActivity.this, R.layout.row_question,
                            proposals, tvQuestion.getId(), user.getId(), qcm.getId());
                    lvReponse.setAdapter(dataAdapter);
                    /**
                     * Else this is the end of the Qcm
                     */
                } else if (number_question + 1 > questionHashMap.size()) {
                    Toast.makeText(QuestionActivity.this, "On est a la dernière question", Toast.LENGTH_SHORT).show();
                    btNext.setText("Terminé");
                    /**
                     * Send user answers
                     */
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        /**
                         * Click on YES : send reponse NO : not yet
                         * @param dialog dialog YES/NO
                         * @param which choose
                         */
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {

                                //*******************//
                                //** Click on YES **//
                                //*****************//
                                case DialogInterface.BUTTON_POSITIVE:
                                    Toast.makeText(QuestionActivity.this, "YES", Toast.LENGTH_SHORT).show();


                                    //***********************//
                                    //** Get User answers **//
                                    //*********************//
                                    ProposalUserSqLiteAdapter proposalUserSqLiteAdapter =
                                            new ProposalUserSqLiteAdapter(QuestionActivity.this);
                                    proposalUserSqLiteAdapter.open();
                                    ArrayList<ProposalUser> proposalUsers =
                                            proposalUserSqLiteAdapter.getProposalsUser(user.getId(), qcm.getId());
                                    proposalUserSqLiteAdapter.close();


                                    //**********************************//
                                    //** Send User answers to server **//
                                    //********************************//
                                    try {
                                        QuestionWSAdapter.post(QuestionActivity.this, proposalUsers,
                                                new AsyncHttpResponseHandler() {
                                                    @Override
                                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                                    }

                                                    @Override
                                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                                    }
                                                });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    //*********************************//
                                    //** Back to List of categories **//
                                    //*******************************//
                                    Intent i = new Intent(QuestionActivity.this, ListCategoriesActivity.class);
                                    i.putExtra(User.SERIAL, user);
                                    startActivity(i);
                                    finish();
                                    break;

                                //******************//
                                //** Click on NO **//
                                //*****************//
                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(QuestionActivity.this, "NO", Toast.LENGTH_SHORT).show();
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    //**************************//
                    //** Create Dialog chose **//
                    //************************//
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                    builder.setMessage("Envoyer les réponses ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        });
    }

    /**
     * Adapter
     */
    private class MyCustomAdapter extends ArrayAdapter<Proposal>  {

        /**
         * Proposal List
         */
        private ArrayList<Proposal> proposalList;
        /**
         * Quesrtion id
         */
        private int id_question;
        /**
         * User id
         */
        private long id_user;
        /**
         * Qcm id
         */
        private int id_qcm;
        /**
         * ViewHolder
         */
        ViewHolder holder = null;

        /**
         * MyCustomAdapter constructor
         *
         * @param context            activity context
         * @param textViewResourceId textViewRessourceid
         * @param proposalList       List of proposals
         * @param id_question        question id
         * @param id_user            user id
         * @param id_qcm             qcm id
         */
        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Proposal> proposalList, int id_question, long id_user, int id_qcm) {
            super(context, textViewResourceId, proposalList);
            this.proposalList = new ArrayList<>();
            this.proposalList.addAll(proposalList);
            this.id_user = id_user;
            this.id_qcm = id_qcm;
            this.id_question = id_question;

        }

        /**
         * Viex holder
         */
        private class ViewHolder {
            CheckBox mycheckbox;
        }

        /**
         * Get View
         *
         * @param position    position view
         * @param convertView convertView
         * @param parent      parentView
         * @return View
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {

                //************************//
                //** Initialize holder **//
                //***********************//
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.row_question, null);

                holder = new ViewHolder();
                holder.mycheckbox = (CheckBox) convertView.findViewById(R.id.cbQuestion);

                convertView.setTag(holder);


                //********************************//
                //** Get Proposals of question **//
                //******************************//
                ProposalUserSqLiteAdapter proposalUserSqLiteAdapter =
                        new ProposalUserSqLiteAdapter(QuestionActivity.this);
                proposalUserSqLiteAdapter.open();

                ArrayList<ProposalUser> proposalUsers =
                        proposalUserSqLiteAdapter.getProposalsUserQuestion(id_user, id_question);
                proposalUserSqLiteAdapter.close();

                //****************************//
                //** Set proposals to View **//
                //**************************//
                Proposal proposal = proposalList.get(position);
                holder.mycheckbox.setText(proposal.getLibelle());
                holder.mycheckbox.setId(proposal.getId());

                for (ProposalUser proposalUser : proposalUsers) {
                    if (holder.mycheckbox.getId() == proposalUser.getId_proposal()) {
                        holder.mycheckbox.setChecked(true);
                    }
                }

                //*********************//
                //** Check proposal **//
                //*******************//
                holder.mycheckbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CheckBox cb = (CheckBox) v;
                        /*Toast.makeText(getApplicationContext(), "Clicked on proposal " +
                                cb.getId() + " |" + cb.getText() + "is " + cb.isChecked()
                                , Toast.LENGTH_SHORT).show();*/

                        //********************//
                        //** Open database **//
                        //*******************//
                        ProposalUserSqLiteAdapter proposalUserSqLiteAdapter =
                                new ProposalUserSqLiteAdapter(QuestionActivity.this);
                        proposalUserSqLiteAdapter.open();

                        //**************//
                        //** CHECKED **//
                        //************//
                        if (cb.isChecked()) {

                            //*************************//
                            //** Insert user answer **//
                            //***********************//
                            ProposalUser proposalUser = new ProposalUser();
                            proposalUser.setId_user(id_user);
                            proposalUser.setId_qcm(id_qcm);
                            proposalUser.setId_question(id_question);
                            proposalUser.setId_proposal(cb.getId());

                            proposalUserSqLiteAdapter.insert(proposalUser);


                        //****************//
                        //** UNCHECKED **//
                        // **************//
                        } else {

                            //*************************//
                            //** Delete User answer **//
                            //***********************//
                            proposalUserSqLiteAdapter.delete(id_user, cb.getId());

                        }

                        proposalUserSqLiteAdapter.close();
                    }
                });

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }
    }
}
