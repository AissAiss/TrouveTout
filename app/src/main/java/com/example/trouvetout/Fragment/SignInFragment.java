package com.example.trouvetout.Fragment;

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

import androidx.fragment.app.Fragment;

import com.example.trouvetout.R;

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

                            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params2.weight = 1.0f;
                            params2.gravity = Gravity.RIGHT;

                            EditText editText2 = new EditText(view1.getContext());
                            editText2.setBackgroundResource(R.drawable.custom_input);
                            editText2.setPadding(dpToPixel(12), dpToPixel(5), 0, dpToPixel(5));
                            editText2.setHint("num secret");
                            editText2.setLayoutParams(params2);

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
        return view;
    }

    private int dpToPixel(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }
}