package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> notes = new ArrayList<>();
    static  ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_item);
         sharedPreferences = getApplicationContext().getSharedPreferences("com.example.mynotes",MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null) ;

        if(set == null) {
            notes.add("Example Note");
        }else {
            notes = new ArrayList(set);
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),noteActivity.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                final int itemToDelete = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do You Want To Delete This Note?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();


                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("NO",null)
                         .show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.notes,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.item1){
            Intent intent = new Intent(getApplicationContext(),noteActivity.class);
            startActivity(intent);
            return  true;
        }
        return  false;
    }
}