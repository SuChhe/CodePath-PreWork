package com.suchhe.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    ArrayAdapter<String> editItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String item = getIntent().getStringExtra("item");
        EditText etEditItem = (EditText) findViewById(R.id.editText);
        etEditItem.setText(item);
    }

    public void onCancel (View v){
        setResult(RESULT_CANCELED);
        this.finish();
    }

    public void onSave (View v) {
        EditText etEditItem = (EditText) findViewById(R.id.editText);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("editedItem", etEditItem.getText().toString());
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
