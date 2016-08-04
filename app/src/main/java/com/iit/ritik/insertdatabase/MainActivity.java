package com.iit.ritik.insertdatabase;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText adm;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        name = (EditText) findViewById(R.id.edit_name);
        adm = (EditText) findViewById(R.id.edit_adm);
        email = (EditText) findViewById(R.id.edit_email);
    }

    public void insert(View view)
    {
        String Name = name.getText().toString();
        String Adm = adm.getText().toString();
        String Email = email.getText().toString();
        if(Name!="" && Adm!="" && Email!="") {
            insertToDataBase(Name, Adm, Email);
            name.setText("");
            adm.setText("");
            email.setText("");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Enter All Fields...",Toast.LENGTH_SHORT).show();
        }
    }

    public void insertToDataBase(String Name,String Adm,String Email)
    {
        class SendPostReqAsynkTask extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... strings) {
                String pName = strings[0];
                String pAdm = strings[1];
                String pEmail = strings[2];

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name",pName));
                nameValuePairs.add(new BasicNameValuePair("id",pAdm));
                nameValuePairs.add(new BasicNameValuePair("emailId",pEmail));

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://androiddev.16mb.com/insert_db.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity entity = httpResponse.getEntity();

                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "Successfully Added to DataBase....";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        }
        SendPostReqAsynkTask sendPostReqAsynkTask = new SendPostReqAsynkTask();
        sendPostReqAsynkTask.execute(Name,Adm,Email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
