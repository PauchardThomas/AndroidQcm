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

    ArrayAdapter dataAdapter;
    ArrayList<Proposal> proposals;
    ListView lvReponse;
    TextView tvQuestion, tvQcm, tvCategory, tvUsername;
    Button btPrevious, btNext;
    int number_question;
    HashMap questionHashMap ;
    Qcm qcm;
    User user;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                     * Send answers user
                     */
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Toast.makeText(QuestionActivity.this, "YES", Toast.LENGTH_SHORT).show();


                                    ProposalUserSqLiteAdapter proposalUserSqLiteAdapter =
                                            new ProposalUserSqLiteAdapter(QuestionActivity.this);
                                    proposalUserSqLiteAdapter.open();
                                    ArrayList<ProposalUser> proposalUsers =
                                            proposalUserSqLiteAdapter.getProposalsUser(user.getId(), qcm.getId());
                                    proposalUserSqLiteAdapter.close();

                                    try {
                                        QuestionWSAdapter.post(QuestionActivity.this, proposalUsers, new AsyncHttpResponseHandler() {
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
                                    Intent i = new Intent(QuestionActivity.this, ListCategoriesActivity.class);
                                    i.putExtra(User.SERIAL, user);
                                    startActivity(i);
                                    finish();

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(QuestionActivity.this, "NO", Toast.LENGTH_SHORT).show();
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                    builder.setMessage("Envoyer les réponses ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    //
                } else {

                }
            }
        });
    }

    /**
     * Adapter
     */
    private class MyCustomAdapter extends ArrayAdapter<Proposal> {

        private ArrayList<Proposal> proposalList;
        private int id_question;
        private long id_user;
        private int id_qcm;
        ViewHolder holder = null;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Proposal> proposalList, int id_question, long id_user, int id_qcm) {
            super(context, textViewResourceId, proposalList);
            this.proposalList = new ArrayList<>();
            this.proposalList.addAll(proposalList);
            this.id_user = id_user;
            this.id_qcm = id_qcm;
            this.id_question = id_question;

        }

        private class ViewHolder {
            CheckBox mycheckbox;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.row_question, null);

                holder = new ViewHolder();
                holder.mycheckbox = (CheckBox) convertView.findViewById(R.id.cbQuestion);

                convertView.setTag(holder);


                ProposalUserSqLiteAdapter proposalUserSqLiteAdapter =
                        new ProposalUserSqLiteAdapter(QuestionActivity.this);
                proposalUserSqLiteAdapter.open();

                ArrayList<ProposalUser> proposalUsers =
                        proposalUserSqLiteAdapter.getProposalsUserQuestion(id_user, id_question);

                Proposal proposal = proposalList.get(position);
                holder.mycheckbox.setText(proposal.getLibelle());
                holder.mycheckbox.setId(proposal.getId());

                for (ProposalUser proposalUser : proposalUsers) {
                    if (holder.mycheckbox.getId() == proposalUser.getId_proposal()) {
                        holder.mycheckbox.setChecked(true);
                    }
                }

                holder.mycheckbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CheckBox cb = (CheckBox) v;
                        Toast.makeText(getApplicationContext(), "Clicked on proposal " +
                                cb.getId() + " |" + cb.getText() + "is " + cb.isChecked()
                                , Toast.LENGTH_SHORT).show();

                        ProposalUserSqLiteAdapter proposalUserSqLiteAdapter =
                                new ProposalUserSqLiteAdapter(QuestionActivity.this);
                        proposalUserSqLiteAdapter.open();

                        if (cb.isChecked()) {


                            ProposalUser proposalUser = new ProposalUser();
                            proposalUser.setId_user(id_user);
                            proposalUser.setId_qcm(id_qcm);
                            proposalUser.setId_question(id_question);
                            proposalUser.setId_proposal(cb.getId());

                            proposalUserSqLiteAdapter.insert(proposalUser);


                        } else {
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
