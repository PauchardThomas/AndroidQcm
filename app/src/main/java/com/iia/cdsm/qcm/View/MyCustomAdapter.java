//package com.iia.cdsm.qcm.View;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Adapter;
//import android.widget.CheckBox;
//import android.widget.Toast;
//
//import com.iia.cdsm.qcm.Data.ProposalUserSqLiteAdapter;
//import com.iia.cdsm.qcm.Entity.Proposal;
//import com.iia.cdsm.qcm.Entity.ProposalUser;
//import com.iia.cdsm.qcm.R;
//
//import java.util.ArrayList;
//
///**
// * Created by Thom' on 20/04/2016.
// */
//abstract class MyCustomAdapter extends Activity {
//
//    private ArrayList<Proposal> proposalList;
//    private int id_question;
//    private long id_user;
//    private int id_qcm;
//    ViewHolder holder = null;
//
//    public MyCustomAdapter(Context context, int textViewResourceId,
//                           ArrayList<Proposal> proposalList, int id_question, long id_user, int id_qcm) {
//        super(context, textViewResourceId, proposalList);
//        this.proposalList = new ArrayList<>();
//        this.proposalList.addAll(proposalList);
//        this.id_user = id_user;
//        this.id_qcm = id_qcm;
//        this.id_question = id_question;
//
//    }
//
//    private class ViewHolder {
//        CheckBox mycheckbox;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//
//        Log.v("ConvertView", String.valueOf(position));
//
//        if (convertView == null) {
//            LayoutInflater vi = (LayoutInflater) getSystemService(
//                    Context.LAYOUT_INFLATER_SERVICE);
//            convertView = vi.inflate(R.layout.row_question, null);
//
//            holder = new ViewHolder();
//            holder.mycheckbox = (CheckBox) convertView.findViewById(R.id.cbQuestion);
//
//            convertView.setTag(holder);
//
//
//            ProposalUserSqLiteAdapter proposalUserSqLiteAdapter =
//                    new ProposalUserSqLiteAdapter(MyCustomAdapter.this);
//            proposalUserSqLiteAdapter.open();
//
//            ArrayList<ProposalUser> proposalUsers =
//                    proposalUserSqLiteAdapter.getProposalsUserQuestion(id_user, id_question);
//
//            Proposal proposal = proposalList.get(position);
//            holder.mycheckbox.setText(proposal.getLibelle());
//            holder.mycheckbox.setId(proposal.getId());
//
//            for (ProposalUser proposalUser : proposalUsers) {
//                if (holder.mycheckbox.getId() == proposalUser.getId_proposal()) {
//                    holder.mycheckbox.setChecked(true);
//                }
//            }
//
//            holder.mycheckbox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    CheckBox cb = (CheckBox) v;
//                    Toast.makeText(getApplicationContext(), "Clicked on proposal " +
//                            cb.getId() + " |" + cb.getText() + "is " + cb.isChecked()
//                            , Toast.LENGTH_SHORT).show();
//
//                    ProposalUserSqLiteAdapter proposalUserSqLiteAdapter =
//                            new ProposalUserSqLiteAdapter(MyCustomAdapter.this);
//                    proposalUserSqLiteAdapter.open();
//
//                    if (cb.isChecked()) {
//
//
//                        ProposalUser proposalUser = new ProposalUser();
//                        proposalUser.setId_user(id_user);
//                        proposalUser.setId_qcm(id_qcm);
//                        proposalUser.setId_question(id_question);
//                        proposalUser.setId_proposal(cb.getId());
//
//                        proposalUserSqLiteAdapter.insert(proposalUser);
//
//
//                    } else {
//                        proposalUserSqLiteAdapter.delete(id_user, cb.getId());
//
//                    }
//
//                    proposalUserSqLiteAdapter.close();
//                }
//            });
//
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        return convertView;
//    }
//
//}
