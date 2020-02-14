package com.darkshadows.shady.studentsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewUploadsActivity extends AppCompatActivity {

    private Toolbar viewUploadsToolbar;
    ListView listView;
    DatabaseReference mDatabaseReference;
    List<UploadFiles> uploadFilesList;
    public FirebaseUser mAuth;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploads);

        uploadFilesList = new ArrayList<>();
        listView = findViewById(R.id.listView);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();

        viewUploadsToolbar = (Toolbar) findViewById(R.id.view_uploads_toolbar);
        setSupportActionBar(viewUploadsToolbar);
        getSupportActionBar().setTitle("View Uploads");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UploadFiles upload = uploadFilesList.get(i);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(upload.getUrl()));
                startActivity(intent);
            }
        });


        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadFiles upload = postSnapshot.getValue(UploadFiles.class);
                    uploadFilesList.add(upload);
                }

                String[] uploads = new String[uploadFilesList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadFilesList.get(i).getName();
                }

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                listView.setAdapter(adapter);
                Collections.reverse(uploadFilesList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
