package com.example.trouvetout.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trouvetout.MainActivity;
import com.example.trouvetout.R;
import com.example.trouvetout.models.Conversation;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.Query;

public class ConversationsFragment extends Fragment {
    private FirebaseListAdapter<Conversation> adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView listOfConversations;
    private Fragment fragment_Message;

    public ConversationsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ConversationsFragment newInstance(String param1, String param2) {
        ConversationsFragment fragment = new ConversationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        fragment_Message = new MessageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);

        listOfConversations = view.findViewById(R.id.listofconversations);


        diplayConversation();

        return view;
    }

    private void diplayConversation(){
        Query query = MainActivity.MDATABASE.getDatabase().getReference("Conversations");

        FirebaseListOptions<Conversation> options = new FirebaseListOptions.Builder<Conversation>()
                .setQuery(query, Conversation.class)
                .setLayout(R.layout.conversation)
                .build();

        adapter = new FirebaseListAdapter<Conversation>(options) {
            @Override
            protected void populateView(View v, Conversation model, int position) {
                TextView convTitre          = (TextView)v.findViewById(R.id.conversation_titre);
                TextView convDescription    = (TextView)v.findViewById(R.id.conversation_description);

                convTitre.setText(model.getNomAnnonce());
                convDescription.setText(model.getId());

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment, fragment_Message);
                        ft.commit();
                    }
                });

            }
        };


        listOfConversations.setAdapter(adapter);

    }

    @Override public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}