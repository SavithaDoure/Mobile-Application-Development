package com.example.demofragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class AFragment extends Fragment {

    public AFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_a, container, false);
        view.findViewById(R.id.bSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"onCreateView: Submit",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void changeColor(int color){
        //getView().setBackgroundColor(color);
        getActivity().findViewById(R.id.fragmentLinearLayout).setBackgroundColor(color);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("DemoFragment", "Fragment Activity: onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DemoFragment", "Fragment Activity: onDestroy");
    }

    OnFragmentTextChanged mList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("DemoFragment", "Fragment Activity: onAttach");
        try {
            mList = (OnFragmentTextChanged) activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException(activity.toString()+ " should implement OnFragmentTextChanged");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("DemoFragment", "Fragment Activity: onDetach");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("DemoFragment", "Fragment Activity: onActivityCreated");


        getActivity().findViewById(R.id.bSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mList.onTextChanged(((EditText)getActivity().findViewById(R.id.etFragText)).getText().toString());
            }
        });


        getActivity().findViewById(R.id.bNextFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.gotoNextFragment();
            }
        });
    }

    public interface OnFragmentTextChanged{
        void onTextChanged(String text);
        void gotoNextFragment();
    }


}
