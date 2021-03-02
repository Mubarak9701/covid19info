package com.mubarak.covid19apiinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
TextView date,confirmed,active,death,country,recovered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date=findViewById(R.id.tv_date);
        confirmed=findViewById(R.id.tv_confirmed);
        death=findViewById(R.id.tv_deaths);
        active=findViewById(R.id.tv_active);
        recovered=findViewById(R.id.tv_recovered);
        country=findViewById(R.id.tv_country);
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("DATA FETCHING FROM INTERNET");
        progressDialog.setMessage("PLEASE WAIL LOADING");
        progressDialog.show();
        EndPoint endPoint=Covid19Instance.getInsatnce().create(EndPoint.class);
        Call<String> c=endPoint.getData();
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                try {
                    JSONArray array=new JSONArray(response.body());
                    for (int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        String cnf=object.getString("Confirmed");
                        String deaths=object.getString("Deaths");
                        String rec=object.getString("Recovered");
                        String act=object.getString("Active");
                        String dt=object.getString("Date");
                        String con=object.getString("Country");
                        date.setText("Date :"+forDateFormat(dt));
                        active.setText("Activie :"+act);
                        confirmed.setText("Conformed :"+cnf);
                        recovered.setText("Recovered :"+rec);
                        death.setText("Deaths :"+deaths);
                        country.setText("Country:"+con);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wong", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private String forDateFormat(String dt) {
        String inputPattern="yy-mm-dd";
        String outputPattern="dd-mm-yy";

        SimpleDateFormat inputFormat=new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat=new SimpleDateFormat(outputPattern);
        Date d=null;
        String str=null;
        try {
            d=inputFormat.parse(dt);
            str=outputFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }
}