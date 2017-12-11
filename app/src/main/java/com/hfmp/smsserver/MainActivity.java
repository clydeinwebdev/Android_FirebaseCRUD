package com.hfmp.smsserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnSave;
    EditText edtName;
    DatabaseReference databaseReference;
    ListView listViewUsers;
    List<User> users;

    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new ArrayList<User>();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        btnSave = (Button) findViewById(R.id.btnSave);
        edtName = (EditText) findViewById(R.id.edtName);
        listViewUsers = (ListView) findViewById(R.id.listViewUsers);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();

                if(TextUtils.isEmpty(userId)){
                    //save
                    String id = databaseReference.push().getKey();
                    User user = new User(id, name);
                    databaseReference.child(id).setValue(user);
                    Toast.makeText(MainActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    //update
                    databaseReference.child(userId).child("name").setValue(name);
                    Toast.makeText(MainActivity.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                edtName.setText(null);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }

                UserList userAdapter = new UserList(MainActivity.this, users, databaseReference, edtName);
                listViewUsers.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*    private Button btnFirebase;
    private EditText txtName;
    private EditText txtEmail;
    private ListView listNames;

    ArrayList<String> listData;
    ArrayAdapter<String> adapter = null;
    List<SMS> sms;

    private DatabaseReference dbReference;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFirebase = (Button) findViewById(R.id.btnFirebase);
        txtName = (EditText) findViewById(R.id.txtName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        listNames = (ListView) findViewById(R.id.listNames);

        sms = new ArrayList<SMS>();
        listData = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        listNames.setAdapter(adapter);


        dbReference = FirebaseDatabase.getInstance().getReference(); //Root
        //dbReference = FirebaseDatabase.getInstance().getReference().child("User_01");

        dbReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                SMS sm = new SMS(key, value);
                listData.add(sm.getName());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                SMS sm = new SMS(key, value);
                sms[count++] = sm;
                listData.add(sm.getName());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dbReference.child("Name").setValue(txtName.getText().toString());
                String name = txtName.getText().toString().trim();

                if(TextUtils.isEmpty(userId)){
                    //save
                    String id = dbReference.push().getKey();
                    SMS sm = new SMS(id, name);
                    dbReference.child(id).setValue(sm);

                    Toast.makeText(MainActivity.this,"Name created succssfully!",Toast.LENGTH_SHORT).show();
                }else{
                    //update
                    dbReference.child(userId).child("name").setValue(name);
                    Toast.makeText(MainActivity.this,"Name updated succssfully!",Toast.LENGTH_SHORT).show();
                }

                txtName.setText(null);
                *//*String email = txtEmail.getText().toString().trim();
                HashMap<String,String> dataMap = new HashMap<String, String>();
                dataMap.put("Name",name);
                dataMap.put("Email",email);*//*

                *//*dbReference.push().setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Stored..",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Error..",Toast.LENGTH_LONG).show();
                        }
                    }
                });*//*

                *//*dbReference.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Stored..",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Error..",Toast.LENGTH_LONG).show();
                        }
                    }
                });*//*
            }
        });

        *//*listNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, i + " " + sms.get(i).getKey() + " " + sms.get(i).getName(), Toast.LENGTH_SHORT).show();
                dbReference.child(sms.get(i).getKey()).removeValue();
                Toast.makeText(MainActivity.this, "Removed" , Toast.LENGTH_SHORT).show();
            }
        });*//*
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sms.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    SMS sm = postSnapshot.getValue(SMS.class);
                    sms.add(sm);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
}
