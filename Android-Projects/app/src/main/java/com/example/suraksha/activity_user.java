package com.example.suraksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class activity_user extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView usern;
    private TextView resultTEXT;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        usern=findViewById(R.id.usern);
        Bundle bundle=getIntent().getExtras();
        String name=bundle.getString("name");
        System.out.println("Name : "+name);
        usern.setText("WELCOME");

        resultTEXT=findViewById(R.id.recordText);
        resultTEXT.setVisibility(View.INVISIBLE);


        Button navigate =findViewById(R.id.navigateButton);
        navigate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(activity_user.this,MapsDirection.class);
                startActivity(intent);
            }
        });


        //////////////////////////BUTTON FOR ANALYSE//////////////////////////

        Button analyse=findViewById(R.id.analyseButton);
        analyse.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                resultTEXT.setVisibility(View.VISIBLE);
                System.out.println(resultTEXT.getText().toString());
                if(resultTEXT.getText().toString()=="help")
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:100"));
                    startActivity(callIntent);
                    Toast.makeText(activity_user.this, "Emergency", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //////////////////////////BUTTON FOR ANALYSE//////////////////////////




        drawerLayout=findViewById(R.id.drawerLayout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view=findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                int id=menuItem.getItemId();

                if(id==R.id.trackDaughter)
                {
                    ///IMPLEMENT ON CLICK LISTENER
                    Toast.makeText(activity_user.this, "Track Daughter", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(activity_user.this,MapsActivity.class);
                    startActivity(intent);
                }

                else if(id==R.id.myProfile)
                {
                    ///IMPLEMENT ON CLICK LISTENER
                    Toast.makeText(activity_user.this, "My Profile", Toast.LENGTH_SHORT).show();
                }

                else if(id==R.id.emergency)
                {
                    ///IMPLEMENT ON CLICK LISTENER
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:100"));
                    startActivity(callIntent);
                    Toast.makeText(activity_user.this, "Emergency", Toast.LENGTH_SHORT).show();
                }

                else if(id==R.id.selfDefense)
                {
                    ///IMPLEMENT ON CLICK LISTENER
                    Intent intent=new Intent(activity_user.this,SelfDefense.class);
                    startActivity(intent);
                    Toast.makeText(activity_user.this, "Self Defence", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

    }

    public void onClickRecord(View view)
    {
        if(view.getId()==R.id.recordButton)
        {
            promptSpeechInput();
        }
    }

    public void promptSpeechInput()
    {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something!");
        try{
            startActivityForResult(intent,100);
        }
        catch (Exception e)
        {
            Toast.makeText(activity_user.this,"Sorry! Your device does not support speech language", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int request_code,int result_code,Intent intent)
    {
        super.onActivityResult(request_code,result_code,intent);

        switch (request_code)
        {
            case 100:
                if(result_code==RESULT_OK&& intent!=null)
                {
                    ArrayList<String> result=intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultTEXT.setText(result.get(0));

                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return actionBarDrawerToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }
}
