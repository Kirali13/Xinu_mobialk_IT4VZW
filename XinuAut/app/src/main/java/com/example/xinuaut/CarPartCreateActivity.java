package com.example.xinuaut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


public class CarPartCreateActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    private Button updatebutton;

    EditText updateName;
    EditText updateCodes;
    EditText updatePrice;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_car_part);

        db = FirebaseFirestore.getInstance();

        updateName = findViewById(R.id.updateName);
        updateCodes = findViewById(R.id.updateCodes);
        updatePrice = findViewById(R.id.updatePrice);
        updatebutton = findViewById(R.id.upload);

        String itemId = getIntent().getStringExtra("itemId");
        String name = getIntent().getStringExtra("name");
        String code = getIntent().getStringExtra("codes");
        int price = getIntent().getIntExtra("price", 0);

        updateName.setText(name);
        updateCodes.setText(code);
        updatePrice.setText(String.valueOf(price));

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new value for itemField1
                String newNameValue = updateName.getText().toString();
                String newCodesValue = updateCodes.getText().toString();
                String newPriceValue = updatePrice.getText().toString();
                int newPrice = Integer.parseInt(newPriceValue);

                // Update the document in Firebase Firestore
                db.collection("Carparts").document(itemId).update(
                                "name", newNameValue,
                                "codes", newCodesValue,
                                "price", newPrice,
                                "updatedAt", FieldValue.serverTimestamp()
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(LOG_TAG, "DocumentSnapshot successfully updated!");
                                // Close the current activity and return to the previous activity
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(LOG_TAG, "Error updating document", e);
                            }
                        });
            }
        });

    }





    public void cancel() {
        Intent intent = new Intent(this, CarPartsActivityAdmin.class);

        startActivity(intent);
    }
}



