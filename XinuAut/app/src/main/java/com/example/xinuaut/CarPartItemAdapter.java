package com.example.xinuaut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CarPartItemAdapter extends RecyclerView.Adapter<CarPartItemAdapter.ViewHolder> implements Filterable {

    private ArrayList carpartItemData;
    private ArrayList<CarPartItem> carpartItemDataAll;
    private Context context;

    public CarPartItemAdapter(Context context, ArrayList<CarPartItem> itemsData) {
        this.carpartItemData = itemsData;
        this.carpartItemDataAll = itemsData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_customer, parent, false));
    }

    @Override
    public void onBindViewHolder(CarPartItemAdapter.ViewHolder holder, int position) {
        CarPartItem current = (CarPartItem) carpartItemData.get(position);

        holder.bindTo(current);
    }

    @Override
    public int getItemCount() {
        return carpartItemData.size();
    }

    @Override
    public Filter getFilter() {
        return carpartFilter;
    }

    public static String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    private final Filter carpartFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<CarPartItem> filterList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.count = carpartItemDataAll.size();
                results.values = carpartItemDataAll;
            } else {
                String filterChar = constraint.toString().toLowerCase().trim();
                filterChar = removeAccents(filterChar);

                for (CarPartItem item : carpartItemDataAll) {
                    String noaccents = removeAccents(item.getName().toLowerCase());
                    if (noaccents.contains(filterChar)) {
                        filterList.add(item);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            }

            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            carpartItemData = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView code;
        private TextView priceText;
        private ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.itemTitle);
            code = itemView.findViewById(R.id.itemCode);
            priceText = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.itemImage);
        }

        public void bindTo(CarPartItem current) {
            titleText.setText(current.getName());
            code.setText(current.getCodes());
            int price = current.getPrice();
            String priceString = String.valueOf(price);
            priceText.setText(priceString + " FT");

            Glide.with(context).load(current.getImgResource()).into(image);

            itemView.findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CarPartsActivity) context).updateAlertIcon(current);

                }
            });
        }
    }
}


