package com.example.recyclerviewcars;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private final List<Car> carList;

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup recycleView, int viewType) {
        Log.d("CarAdapter", "onCreateViewHolder called");
        LayoutInflater inflater = LayoutInflater.from(recycleView.getContext());
        View view = inflater.inflate(R.layout.item_car, recycleView, false);
        CarViewHolder carView = new CarViewHolder(view);
        return carView;
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("CarAdapter", "onBindViewHolder called for position: " + position);
        Car car = carList.get(position);
        holder.imageViewCar.setImageResource(car.getImageResource());
        holder.textViewName.setText(car.getName());
        holder.textViewType.setText(car.getType());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), carList.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCar;
        TextView textViewName;
        TextView textViewType;
        LinearLayout linearLayout;
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCar = itemView.findViewById(R.id.imageViewCar);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewType = itemView.findViewById(R.id.textViewType);
            linearLayout = itemView.findViewById(R.id.LinearLayout);

        }
    }

}