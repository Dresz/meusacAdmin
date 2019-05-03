package com.example.sergi.meusacadmin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AlumnoDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_datos);
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
        dato = getIntent().getExtras();
        usuario = dato.getString("usuario");
        new SendHttpRequestTask().execute();
        TextView textView = (TextView) findViewById(R.id.textView13);
        textView.setText(usuario);
        requestQueue = Volley.newRequestQueue(this);
        JsonParse2();
    }
    Bundle dato;
    String usuario;
    RequestQueue requestQueue;
    private void JsonParse2( ) {
        String URL = getIp.ip+"Alumnos/"+usuario;
        //dato = getIntent().getExtras();
        //String usuario = dato.getString("usuario");
        JsonArrayRequest arrayRequest2 = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                            Log.d("Aqui","*******************************Entro al onResponses");
                            try {
                                JSONObject activity = jsonArray.getJSONObject(0);
                                String archivo = activity.getString("nombre");
                                TextView textView = (TextView) findViewById(R.id.textView12);
                                textView.setText(archivo);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {

                URL url = new URL(getIp.ip+"download/"+usuario+".jpg");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                Log.d("Aqui",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            try
            {
                ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                imageView.setImageBitmap(result);
            }
            catch (Exception e){
                Log.d("Aqui",e.getMessage());
            }

        }
    }

}
