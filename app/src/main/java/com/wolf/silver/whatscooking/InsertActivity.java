package com.wolf.silver.whatscooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InsertActivity extends AppCompatActivity {
Button b1,b2;
EditText e7,e8;
String dish,recipe;
    String ins_url="http://192.168.43.207/wcapi/process_recipins.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        b1=findViewById(R.id.button5);
        b2=findViewById(R.id.button6);
        e7=findViewById(R.id.editText7);
        e8=findViewById(R.id.editText8);
    final Bundle bd=getIntent().getExtras();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uid=bd.getString("id");
                dish=e7.getText().toString();
                recipe=e8.getText().toString();
                if (dish.equals("") || recipe.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter dish and recipe", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            ins_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");

                                if (code.equals("login_failed")) {
                                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                                } else {

                                    Intent intent = new Intent(InsertActivity.this,
                                            DisplayActivity.class);
                                    Bundle b=new Bundle();
                                    b.putString("id",uid);
                                    intent.putExtras(b);
                                    startActivity(intent);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }) {


                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("dish", dish);
                            params.put("recipe", recipe);
                            params.put("uid",uid);
                            return params;
                        }
                    };

                    MySingleton.getInstance(InsertActivity.this).addToRequestque(stringRequest);


                }


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertActivity.this,DisplayActivity.class));
            }
        });
    }
}
