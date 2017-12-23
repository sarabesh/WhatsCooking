package com.wolf.silver.whatscooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
TextView d,r;
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    d=findViewById(R.id.di);
    r=findViewById(R.id.re);
      b1=findViewById(R.id.button10);
        final Bundle b=getIntent().getExtras();
        d.setText(b.getString("dish"));
        r.setText(b.getString("recipe"));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ViewActivity.this,DisplayActivity.class);
                Bundle bu=getIntent().getExtras();
                bu.putString("id",b.getString("id"));
                i.putExtras(bu);
                startActivity(i);
            }
        });
    }
}
