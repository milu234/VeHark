package com.example.vehark;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String nameOfPlace1 = bundle.getString("nameOfPlace");
        String vicinity1 = bundle.getString("vicinity");
        final Double plat = bundle.getDouble("plat",0);
        final Double plng = bundle.getDouble("plng",0);



        TextView nameOfPlace = (TextView)findViewById(R.id.nameOfPlace);
        TextView vicinity = (TextView)findViewById(R.id.vicinity);

        nameOfPlace.setText(nameOfPlace1);
        vicinity.setText(vicinity1);


        Button viewRoutes = findViewById(R.id.routes);
        viewRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(InfoPage.this,Map2Activity.class);
                intent1.putExtra("plat",plat);
                intent1.putExtra("plng",plng);
                startActivity(intent1);
            }
        });








    }
}
