package com.example.sergi.meusacadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.nbsp.materialfilepicker.MaterialFilePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Alumnos extends AppCompatActivity {
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);
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
        requestQueue = Volley.newRequestQueue(this);
        JsonParse2();
        spinner=(Spinner) findViewById(R.id.spinner2);
        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Alumnos.this,
                        AlumnoDatos.class);
                intent.putExtra("usuario",spinner.getSelectedItem().toString());
                startActivity(intent);

            }
        });
    }
    Bundle dato;
    static ArrayList<String> dataModels = new ArrayList<>();;
    ListView listView;
    private static CustomAdapter adapter;
    RequestQueue requestQueue;
    private void JsonParse2( ) {
        String URL = getIp.ip+"Alumnos";
        //dato = getIntent().getExtras();
        //String usuario = dato.getString("usuario");
        JsonArrayRequest arrayRequest2 = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Alumnos.dataModels = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.d("Aqui","*******************************Entro al onResponses");
                            try {
                                JSONObject activity = jsonArray.getJSONObject(i);
                                String archivo = activity.getString("carnet");
                                Log.d("Aqui",archivo);
                                dataModels.add(archivo);
                                Log.d("Aqui",Alumnos.dataModels.size()+"");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("Aqui",Alumnos.dataModels.size()+" segunda ves");
                        ArrayAdapter<String> comboAdapter = new ArrayAdapter<String>
                                (Alumnos.this, android.R.layout.simple_spinner_item, Alumnos.dataModels);
                        comboAdapter.setDropDownViewResource(android.R.layout
                                .simple_spinner_dropdown_item);
                        Spinner textbox=(Spinner) findViewById(R.id.spinner2);
                        textbox.setAdapter(comboAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                });
        requestQueue.add(arrayRequest2);
    }
}
