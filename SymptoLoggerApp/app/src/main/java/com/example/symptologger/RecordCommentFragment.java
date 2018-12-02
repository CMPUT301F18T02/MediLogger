package com.example.symptologger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

/*
 *  Copyright 2018 Remi Arshad, Noni Hua, Jason Lee, Patrick Tamm, Kaiwen Zhang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * <p>
 *     Fragment to display list of Care Provider comments.
 * </p>
 */

public class RecordCommentFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;

    private ArrayList<CareProviderComment> careProviderCommentList = new ArrayList<>();
    private ArrayAdapter<CareProviderComment> adapter;

    int RECORD_POS;
    int CONCERN_POS;
    String USERNAME;

    Record record;

    private TextView textBox;
    private EditText messageBox;

    private String senderID;
    private String receiverID;
    private String message;
    private Date date;

    public static boolean updateLogsReady = false;
    public static List<List<ChatLogs>> chatLogs = new ArrayList<>();

    private static RecyclerView recyclerView;
    private ChatManager cm;
    private static ChatViewAdapter mAdapter;


    public RecordCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_comment, container, false);

        try {
            RECORD_POS = bundle.getInt("RECORD_POS");
            CONCERN_POS = bundle.getInt("CONCERN_POS");
            USERNAME = bundle.getString("USERNAME");
        } catch (Exception e) {
            // TODO: offline mode
        }

        Collection<Concern> concerns = ConcernListController.getConcernList(USERNAME).getConcernsList();
        ArrayList<Concern> concernList = new ArrayList<Concern>(concerns);
        Concern concernToView = concernList.get(CONCERN_POS);
        ArrayList<Record> recordList = new ArrayList<Record>(concernToView.getRecords());

        record = recordList.get(RECORD_POS);

        senderID = USERNAME;
        ElasticSearchClient.GetSinglePatient singlePatient = new ElasticSearchClient.GetSinglePatient();
        singlePatient.execute(USERNAME);
        Patient patient = null;
        try {
            patient = singlePatient.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messageBox = view.findViewById(R.id.addComment);
        textBox = view.findViewById(R.id.chatbox);

        if (patient != null) {
            // Set receiver ID if there is a care provider added
            receiverID = patient.getCpUserName();

            recyclerView = view.findViewById(R.id.chatlogs_holder);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new ChatViewAdapter(chatLogs);
            recyclerView.setAdapter(mAdapter);

            cm = new ChatManager(getActivity());
            cm.startFetchLogsTimer();
        }
        else {
            record.addCareProviderComment();
            careProviderCommentList = record.getCareProviderComment();

            adapter = new ArrayAdapter<CareProviderComment>(
                getContext(),
                R.layout.list_layout,
                careProviderCommentList);
            ListView careProviderCommentsListView = view.findViewById(R.id.chatlogs_holder);
            careProviderCommentsListView.setAdapter(adapter);
        }

        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
        cm.stopFetchLogsTimer();
    }

    public void send(View v) { //USE LISTENER METHOD INSTEAD ON FINAL PROJECT
        cm.stopFetchLogsTimer();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("MST"));
        date = new Date();

        message = messageBox.getText().toString();
        System.out.println(message);

        ChatManager.setSaveVars(senderID, receiverID, message, formatter.format(date));
        ChatManager.saveLogs();
        cm.restartTimer();
    }

    public static void updateView() {
        System.out.println("-----status-----");
        System.out.println(updateLogsReady);
        if (updateLogsReady == true) {
            updateLogsReady = false;
            mAdapter.notifyDataSetChanged();
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, mAdapter.getItemCount()-1);
        }
    }
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
