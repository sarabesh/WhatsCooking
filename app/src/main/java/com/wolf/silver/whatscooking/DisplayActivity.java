package com.wolf.silver.whatscooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
Button b1;
ListView lv;
    List<Recipe> Rlist;
String show_url="http://192.168.43.207/wcapi/process_recipshow.php";
    RecipeAdapter ra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
    b1=findViewById(R.id.newti);
    lv=findViewById(R.id.lv);
    final Bundle b=getIntent().getExtras();
        final String id=b.getString("id");

        // Toast.makeText(getApplicationContext(),b.getString("id"),Toast.LENGTH_LONG).show();
    b1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //String id=b.getString("id");
            Intent intent = new Intent(DisplayActivity.this,
                    InsertActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("id",id);


            intent.putExtras(bundle);
            startActivity(intent);
        }
    });



        Rlist=new ArrayList<>();
        ra=new RecipeAdapter(this,Rlist);



        StringRequest stringRequest = new StringRequest(Request.Method.GET, show_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj=array.getJSONObject(i);

                                    Recipe r1 = new Recipe(
                                            obj.getString("dish"),
                                            obj.getString("recipe"),
                                            obj.getString("image")
                                    );
                                    Rlist.add(r1);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }ra.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        MySingleton.getInstance(DisplayActivity.this).addToRequestque(stringRequest);
        lv.setAdapter(ra);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent inti=new Intent(view.getContext(),ViewActivity.class);
            Recipe NowR= (Recipe) adapterView.getItemAtPosition(i);
            Bundle b=new Bundle();
            b.putString("dish", NowR.getDish());
            b.putString("recipe", NowR.getRecipe());
            b.putString("image", NowR.getImage());
            b.putString("id",id);
            inti.putExtras(b);
            startActivity(inti);

        }
    });

    }
}
