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

public class LoginActivity extends AppCompatActivity {
Button b1,b2;
    EditText UserName, Password,docname,docpswd;
    String username, password,dname,dpass;
    String login_url = "http://192.168.43.207/wcapi/process_login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);

        UserName=findViewById(R.id.editText2);
        Password=findViewById(R.id.editText3);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = UserName.getText().toString();
                password = Password.getText().toString();
                if (username.equals("")||password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Username and password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            login_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");

                                if (code.equals("login_failed")){
                                   Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
                                } else{

                                    Intent intent = new Intent(LoginActivity.this,
                                            DisplayActivity.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("id",jsonObject.getString("id"));

//Toast.makeText(getApplicationContext(),bundle.getString("id"),Toast.LENGTH_LONG).show();
                                    intent.putExtras(bundle);
                                   startActivity(intent);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }){


                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("un", username);
                            params.put("pwd", password);
                            return params;
                        }
                    };

                    MySingleton.getInstance(LoginActivity.this).addToRequestque(stringRequest);


                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

}
