package com.example.trouvetout.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.trouvetout.MainActivity;
import com.example.trouvetout.R;
import com.example.trouvetout.models.User;
import com.example.trouvetout.models.UserPro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);


        view.findViewById(R.id.checkBoxPro).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        CheckBox checkBox = view1.findViewById(R.id.checkBoxPro);
                        if (checkBox.isChecked()) {
                            TableLayout table = view.findViewById(R.id.proTable);

                            TableRow tableRow =  view.findViewById(R.id.topTableRowPro);
                            EditText editText = new EditText(view1.getContext());
                            editText.setBackgroundResource(R.drawable.custom_input);
                            editText.setPadding(dpToPixel(12), dpToPixel(5), 0, dpToPixel(5));
                            editText.setHint("Numéro carte");
                            editText.setWidth(dpToPixel(300));
                            editText.setTag(1);

                            tableRow.addView(editText);


                            TableRow tableRow2 =  view.findViewById(R.id.middleTableRowPro);
                            LinearLayout linearLayout = view.findViewById(R.id.midLinearLayoutPro);
                            linearLayout.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.weight = 1.0f;
                            params.gravity = Gravity.LEFT;
                            params.rightMargin = dpToPixel(20);


                            EditText editText1 = new EditText(view1.getContext());
                            editText1.setBackgroundResource(R.drawable.custom_input);
                            editText1.setPadding(dpToPixel(12), dpToPixel(5), 0, dpToPixel(5));
                            editText1.setHint("date expiration");
                            editText1.setLayoutParams(params);
                            editText1.setTag(2);

                            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params2.weight = 1.0f;
                            params2.gravity = Gravity.RIGHT;

                            EditText editText2 = new EditText(view1.getContext());
                            editText2.setBackgroundResource(R.drawable.custom_input);
                            editText2.setPadding(dpToPixel(12), dpToPixel(5), 0, dpToPixel(5));
                            editText2.setHint("num secret");
                            editText2.setLayoutParams(params2);
                            editText2.setTag(3);

                            linearLayout.addView(editText1);
                            linearLayout.addView(editText2);

                        }else{
                            TableRow tableRow =  view.findViewById(R.id.topTableRowPro);
                            tableRow.removeAllViews();
                            LinearLayout linearLayout = view.findViewById(R.id.midLinearLayoutPro);
                            linearLayout.removeAllViews();

                        }
                    }
                }
        );





        view.findViewById(R.id.confirmButtonSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                EditText textPseudo = view.findViewById(R.id.editTextPseudoSigin);
                EditText textMail = view.findViewById(R.id.editTextEmailSigin);
                EditText textPassword = view.findViewById(R.id.editTextPasswordSigin);
                EditText textPasswordConfirm = view.findViewById(R.id.editTextPasswordConfirmSigin);
                EditText textNum = view.findViewById(R.id.editTextNumSigin);
                CheckBox checkBox = view.findViewById(R.id.checkBoxPro);


                // Verification que tous les champs on été rentré
                if (textPseudo.getText().toString().equals("") ||
                        textMail.getText().toString().equals("") ||
                        textPassword.getText().toString().equals("") ||
                        textPasswordConfirm.getText().toString().equals("") ||
                        textNum.getText().toString().equals("")){
                    Toast.makeText(view.getContext(), "veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;

                } else if (checkBox.isChecked()){
                    if(((EditText)  ( view.findViewWithTag(1))).getText().toString().equals("") ||
                        ((EditText)  ( view.findViewWithTag(2))).getText().toString().equals("") ||
                        ((EditText)  ( view.findViewWithTag(3))).getText().toString().equals(""))
                    Toast.makeText(view.getContext(), "veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }




                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                // si le mot de passe et la confiramtion du mot de passe son identique
                if(textPassword.getText().toString().equals(textPasswordConfirm.getText().toString())){
                        // création d'un utilisateur firebase
                        mAuth.createUserWithEmailAndPassword(textMail.getText().toString().trim(), textPassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("TAF", "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            User profil;

                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(textPseudo.getText().toString())
                                                    .build();

                                            user.updateProfile(profileUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("update", "User profile updated.");
                                                            }
                                                        }
                                                    });




                                            // création dans la base d'un user pour y rentrer d'autre information
                                            if(checkBox.isChecked()){
                                                EditText textNumCard = view.findViewWithTag(1);
                                                EditText textDate = view.findViewWithTag(2);
                                                EditText textCode = view.findViewWithTag(3);



                                                profil = new UserPro(user.getUid(),
                                                        textPseudo.getText().toString(),
                                                        textNum.getText().toString(),
                                                        textNumCard.getText().toString(),
                                                        textDate.getText().toString(),
                                                        textCode.getText().toString());

                                            }else {
                                                profil = new User(user.getUid(),
                                                        textPseudo.getText().toString(),
                                                        textNum.getText().toString());
                                            }

                                            MainActivity.MDATABASE.child("users").child(profil.getUid()).setValue(profil);

                                            Fragment fragment = new MyProfileFragment();
                                            Context context = view.getContext();
                                            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();


                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("CREATION", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(view.getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                }else{
                    Toast.makeText(view.getContext(), "Mot de passe non identique", Toast.LENGTH_SHORT);
                }


            }
        });


        return view;
    }

    private int dpToPixel(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }
}