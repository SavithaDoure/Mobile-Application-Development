package com.example.demofragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends Activity implements AFragment.OnFragmentTextChanged{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().add(R.id.fragment_linearLayout,new AFragment(),"tag_fragment").commit();
        //getFragmentManager().beginTransaction().add(R.id.fragment_linearLayout,new AFragment(),"tag_fragment_2").commit();


        RadioGroup rg = (RadioGroup) findViewById(R.id.rgColors);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               // AFragment f =(AFragment) getFragmentManager().findFragmentById(R.id.fragment);
                AFragment f =(AFragment) getFragmentManager().findFragmentByTag("tag_fragment");
               // AFragment f2 =(AFragment) getFragmentManager().findFragmentByTag("tag_fragment_2");
                if(checkedId == R.id.rbred){
                    f.changeColor(Color.RED);
                 //   f2.changeColor(Color.RED);
                }
                else if(checkedId == R.id.rbGreen){
                    f.changeColor(Color.GREEN);
                 //   f2.changeColor(Color.GREEN);
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DemoFragment", "Main Activity: onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DemoFragment", "Main Activity: onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DemoFragment", "Main Activity: onResume");
    }

    @Override
    public void onTextChanged(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotoNextFragment() {
       // getFragmentManager().beginTransaction().replace(R.id.fragment_linearLayout,new SecondFragment(),"second").addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }
}
