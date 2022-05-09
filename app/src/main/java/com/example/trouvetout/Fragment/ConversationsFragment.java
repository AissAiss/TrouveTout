package com.example.trouvetout.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trouvetout.MainActivity;
import com.example.trouvetout.R;
import com.example.trouvetout.models.Conversation;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class ConversationsFragment extends Fragment {
    private FirebaseListAdapter<Conversation> adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RadioButton radioButtonAchats;
    private RadioButton radioButtonVentes;
    private RadioGroup radioGroup;
    private ListView listOfConversations;
    private Fragment fragment_Message;

    public static String ID_CURRENT_CONVERSATION;

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
        radioButtonAchats = view.findViewById(R.id.radioButtonAchats);
        radioButtonVentes = view.findViewById(R.id.radioButtonVentes);
        radioGroup = view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.radioButtonAchats:
                        displayConversation("idClient");

                        break;
                    case R.id.radioButtonVentes:
                        displayConversation("idOwner");

                        break;
                }
                //displayConversation();
            }
        });

        displayConversation("idClient");
        return view;
    }

    private void displayConversation(String child){
        //listOfConversations.setAdapter(null);

        //Toast.makeText(getContext(), "Display " + child, Toast.LENGTH_SHORT).show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query query = MainActivity.MDATABASE.getDatabase().getReference("Conversations")
                .orderByChild(child)
                .equalTo(user.getUid());


        FirebaseListOptions<Conversation> options = new FirebaseListOptions.Builder<Conversation>()
                .setQuery(query, Conversation.class)
                .setLayout(R.layout.conversation)
                .build();

        adapter = new FirebaseListAdapter<Conversation>(options) {
            @Override
            protected void populateView(View v, Conversation model, int position) {
                TextView convTitre          = (TextView)v.findViewById(R.id.conversation_titre);
                TextView convDescription    = (TextView)v.findViewById(R.id.conversation_description);
                ImageView miniature         = (ImageView)v.findViewById(R.id.miniature);

                StorageReference imageRef = MainActivity.STORAGE.getReference().child(model.getMiniature());
                final Bitmap[] img = new Bitmap[1];
                final long ONE_MEGABYTE = 1024 * 1024;
                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        img[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        miniature.setImageBitmap(img[0]);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("error", "error dl Image");
                    }
                });


                convTitre.setText(model.getNomAnnonce());
                convDescription.setText(model.getId());

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ID_CURRENT_CONVERSATION = model.getId();
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment, fragment_Message);
                        ft.commit();
                    }
                });

            }
        };

        listOfConversations.setAdapter(adapter);
        adapter.startListening();
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