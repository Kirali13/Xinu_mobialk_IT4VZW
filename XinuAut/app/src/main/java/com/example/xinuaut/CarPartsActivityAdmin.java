package com.example.xinuaut;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CarPartsActivityAdmin extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int REQUEST_CODE_UPDATE_ITEM = 1;
    private FirebaseUser user;
    private int gridNumber = 1;
    private RecyclerView recyclerView;
    private ArrayList<CarPartItem> items;
    private CarPartItemAdapterAdmin Adapter;
    private FrameLayout circle;
    private TextView number;
    private int cartItems = 0;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private NotiHandler noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_parts_admin);

        user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        items = new ArrayList<>();
        Adapter = new CarPartItemAdapterAdmin(this, items);
        recyclerView.setAdapter(Adapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Carparts");
        queryData();

        noti = new NotiHandler(this);
    }

    private void initializeData1() {
        String[] itemsList = getResources()
                .getStringArray(R.array.carpartnames);
        String[] itemsCodes = getResources()
                .getStringArray(R.array.carpartcodes);
        String[] itemsPrice = getResources()
                .getStringArray(R.array.carpartprices);
        TypedArray itemsImageResources =
                getResources().obtainTypedArray(R.array.carpartimgs);

        for (int i = 0; i < itemsList.length; i++) {
            int price = Integer.parseInt(itemsPrice[i]);
            mItems.add(new CarPartItem(itemsList[i], itemsCodes[i], price,
                    itemsImageResources.getResourceId(i, 0)));
        }
        itemsImageResources.recycle();
    }

    private void queryData() {
        items.clear();

        mItems.orderBy("name").limit(5).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                CarPartItem item = document.toObject(CarPartItem.class);
                item.setID(document.getId());
                items.add(item);
            }
            if (items.size() == 0) {
                initializeData1();
                queryData();
            }
            Adapter.notifyDataSetChanged();
        });
    }

    private void queryDataPrice() {
        items.clear();

        mItems.orderBy("price").limit(5).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                CarPartItem item = document.toObject(CarPartItem.class);
                item.setID(document.getId());
                items.add(item);
            }
            if (items.size() == 0) {
                initializeData1();
                queryDataPrice();
            }
            Adapter.notifyDataSetChanged();
        });
    }

    public void updatePart(CarPartItem current) {
        Intent intent = new Intent(this, CarPartCreateActivity.class);

        intent.putExtra("itemId", current._getID());
        intent.putExtra("name", current.getName());
        intent.putExtra("codes", current.getCodes());
        intent.putExtra("price", current.getPrice());

        startActivityForResult(intent, REQUEST_CODE_UPDATE_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_ITEM && resultCode == Activity.RESULT_OK) {
            queryData();
        }
    }

    public void deleteCarPart(CarPartItem item){
        DocumentReference ref = mItems.document(item._getID());

        ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(LOG_TAG, "Deleted" + item._getID());
            }
        });

        ref.delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(LOG_TAG, "Cannot be Deleted" + item._getID());
            }
        });

        noti.send("A(z) " + item.getName() + " törölve lett az elérhető alkatrészekből!");

        queryData();
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if (item.getItemId() == R.id.app_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        } else if (item.getItemId() == R.id.cart) {
            return true;
        } else if (item.getItemId() == R.id.order_name) {
            queryData();
            return true;
        } else if (item.getItemId() == R.id.order_price) {
            queryDataPrice();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        final MenuItem alertMenuItem = menu.findItem(R.id.app_cart);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();
        circle = (FrameLayout) rootView.findViewById(R.id.view_alert_red_circle);
        number = (TextView) rootView.findViewById(R.id.view_alert_count_textview);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(alertMenuItem);
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    public void updateAlertIcon (CarPartItem item) {
        cartItems = (cartItems + 1);
        if (0 < cartItems) {
            number.setText(String.valueOf(cartItems));
        } else {
            number.setText("");
        }
        circle.setVisibility((cartItems > 0) ? VISIBLE : GONE);

        queryData();
    }
}
