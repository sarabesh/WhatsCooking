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

public class RegisterActivity extends AppCompatActivity {
    Button b1,b2;
    EditText name,email,pass;
    String reg_url="http://192.168.43.207/wcapi/process_register.php";
    String n,e,p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        b1=findViewById(R.id.button3);
        b2=findViewById(R.id.button4);
        name=findViewById(R.id.editText4);
        email=findViewById(R.id.editText5);
        pass=findViewById(R.id.editText6);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = name.getText().toString();
                e = email.getText().toString();
                p=pass.getText().toString();
                if (n.equals("")||e.equals("")||p.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter details",Toast.LENGTH_SHORT).show();
                } else{



                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                reg_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");
                                    String message = jsonObject.getString("message");
                                    Intent intent = new Intent(RegisterActivity.this,
                                            DisplayActivity.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("id",jsonObject.getString("id"));


                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                //The keys must match the keys on $_POST on SSS.
                                params.put("name",n);
                                params.put("pass",e);
                                params.put("email",p);


                                return params; //Return the MAP.
                            }
                        };
                        MySingleton.getInstance(RegisterActivity.this).addToRequestque(stringRequest);
                    }

            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }
}
