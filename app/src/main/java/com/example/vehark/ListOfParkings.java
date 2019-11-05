package com.example.vehark;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListOfParkings extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private List<PayAndPark> payAndParks;
    private ListView listView;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_parkings);
        listView = findViewById(R.id.listView);

        getAllPayAndPark();

listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent = new Intent(ListOfParkings.this, PayAndParkInfo.class);
            intent.putExtra("Name", payAndParks.get(i).getName());
            intent.putExtra("GST",payAndParks.get(i).getGst());
            intent.putExtra("Address",payAndParks.get(i).getAddress());
            intent.putExtra("OpeningHours",payAndParks.get(i).getOh());
            startActivity(intent);


    }
});

        }

    private void getAllPayAndPark() {
        payAndParks = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("PayAndPark");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                payAndParks.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    PayAndPark payAndPark = dataSnapshot1.getValue(PayAndPark.class);
                    payAndParks.add(payAndPark);
                }


                PayAndParkAdapter adapter;
                adapter = new PayAndParkAdapter(ListOfParkings.this, payAndParks);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public class PayAndParkAdapter extends  BaseAdapter {

        private List<PayAndPark> payAndParks;
        Context context;

        public PayAndParkAdapter(Context context ,List<PayAndPark> payAndParks) {
            this.payAndParks = payAndParks;
            this.context = context;
        }

        @Override
        public int getCount() {
            return payAndParks.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_of_parkings, viewGroup, false);
            final TextView txtName, txtAddress,txtoh;
            txtName = view.findViewById(R.id.name);
            txtAddress = view.findViewById(R.id.address);
            txtoh = view.findViewById(R.id.opens);

            txtName.setText(payAndParks.get(i).getName());
            txtAddress.setText(payAndParks.get(i).getAddress());
            txtoh.setText(payAndParks.get(i).getOh());

            txtoh.append(" .00 AM - " + payAndParks.get(i).getOh() + " .00 AM");



            return view;
        }
    }

}

