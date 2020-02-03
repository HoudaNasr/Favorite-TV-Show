package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper peopleDB;
    Button btnAddData , btnViewData ,btnUpdateData , btnDeleteData;
    EditText etName , edEmail , edTVShow , etUpdate ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        peopleDB = new DataBaseHelper(this);
        btnAddData = findViewById(R.id.AddDataButtonBTN);
        btnUpdateData = findViewById(R.id.UpdateDataButtonBTN);
        btnViewData = findViewById(R.id.GetDataButtonBTN);
        btnDeleteData = findViewById(R.id.DeleteDataBTN);
        etName = findViewById(R.id.NameETID);
        edEmail = findViewById(R.id.EmailETID);
        edTVShow = findViewById(R.id.FavoriteTvShowETID);
        etUpdate = findViewById(R.id.IDRequiredET);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = edEmail.getText().toString();
                String tvshow = edTVShow.getText().toString();
                boolean insertData = peopleDB.addData(name , email , tvshow);
                if(insertData == true){
                    Toast.makeText(MainActivity.this , "Data iserted Successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this , "Wrong",Toast.LENGTH_LONG).show();

                }
            }
        });
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = peopleDB.showData();
                if(data.getCount() == 0){
                    display("Error" , "No Data Found.");
                    return;
                }
                else {
                    StringBuffer buffer = new StringBuffer();
                    while (data.moveToNext()){
                        buffer.append("ID: "+data.getString(0)+ "\n");
                        buffer.append("NAME: "+data.getString(1)+ "\n");
                        buffer.append("EMAIL: "+data.getString(2)+ "\n");
                        buffer.append("Favorite TV Show: "+data.getString(3)+ "\n\n");

                        display("Data" , buffer.toString());
                    }
                }
            }
        });
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etUpdate.getText().toString();
                if(id.length() > 0){
                    String name = etName.getText().toString();
                    String email = edEmail.getText().toString();
                    String tvshow = edTVShow.getText().toString();
                    boolean update = peopleDB.update(id , name , email , tvshow);
                    if(update == true){
                        Toast.makeText(MainActivity.this , "UPDATED",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this , "Not UPDATED",Toast.LENGTH_LONG).show();

                    }
                }
                else {
                    Toast.makeText(MainActivity.this , "Must insert ID",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deleteId = etUpdate.getText().toString();
                if(deleteId.length() > 0 ){
                    boolean delete = peopleDB.delete(deleteId);
                    if (delete == true){
                        Toast.makeText(MainActivity.this , "Deleted",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this , "Not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this , "Insert ID Please",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
        public void display(String title , String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Yes to cancell the app", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
