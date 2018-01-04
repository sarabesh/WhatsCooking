package com.wolf.silver.whatscooking;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InsertActivity extends AppCompatActivity {

EditText dish,recipe;
ImageView imageView;
Bundle bd=new Bundle();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_insert);
            bd=getIntent().getExtras();

            //initializing views
            imageView = (ImageView) findViewById(R.id.imageView);
            dish = (EditText) findViewById(R.id.editText7);
            recipe = (EditText) findViewById(R.id.editText8);
            //checking the permission
            //if the permission is not given we will open setting to add permission
            //else app will not open
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                finish();
                startActivity(intent);
                return;
            }

            findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent i= new Intent(InsertActivity.this,DisplayActivity.class);
                    Bundle b=new Bundle();
                    b.putString("id",bd.getString("id"));
                    i.putExtras(b);
                    startActivity(i);
                }
            });
            //adding click listener to button
            findViewById(R.id.buttonUploadImage).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //if the tags edittext is empty
                    //we will throw input error
                    if ((dish.getText().toString().trim().isEmpty())||(recipe.getText().toString().trim().isEmpty())) {
                        dish.setError("Enter dish and recipe first");
                        dish.requestFocus();
                        return;
                    }

                    //if everything is ok we will open image chooser
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 100);
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

                //getting the image Uri
                Uri imageUri = data.getData();
                try {
                    //getting bitmap object from uri
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    //displaying selected image to imageview
                    imageView.setImageBitmap(bitmap);

                    //calling the method uploadBitmap to upload image
                    uploadBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    /*
    * The method is taking Bitmap as an argument
    * then it will return the byte[] array for the given bitmap
    * and we will send this array to the server
    * here we are using PNG Compression with 80% quality
    * you can give quality between 0 to 100
    * 0 means worse quality
    * 100 means best quality
    * */
        public byte[] getFileDataFromDrawable(Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }

    private void uploadBitmap(final Bitmap bitmap) {
        Toast.makeText(getApplicationContext(),"uploaded", Toast.LENGTH_SHORT).show();
        //getting the tag from the edittext
        final String dtags = dish.getText().toString().trim();
        final String rtags = recipe.getText().toString().trim();
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, "http://192.168.43.207/wcapi/process_recipins.php",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(),"uploaded", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dish", dtags);
                params.put("recipe", rtags);
                params.put("uid",bd.getString("id"));

                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}
