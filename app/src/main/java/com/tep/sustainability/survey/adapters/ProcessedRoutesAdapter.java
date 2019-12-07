package com.tep.sustainability.survey.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tep.sustainability.survey.R;
import com.tep.sustainability.survey.models.Route;

import java.util.ArrayList;

public class ProcessedRoutesAdapter extends RecyclerView.Adapter {
    private ArrayList<Route> processedRoutes;
    private int count;

    public ProcessedRoutesAdapter(ArrayList<Route> processedRoutes) {
        this.processedRoutes = processedRoutes;
        this.count = processedRoutes.size();
    }

    public static class RouteViewHolder extends RecyclerView.ViewHolder {
        // declare view variables
        CheckBox checkBox;
        TextView textView;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            // link view with variables
            checkBox = itemView.findViewById(R.id.check);
            textView = itemView.findViewById(R.id.text);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.processed_route_item, parent, false);
        RouteViewHolder v = new RouteViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Route item = this.processedRoutes.get(position);
        ((RouteViewHolder) holder).textView.setText(""+item.getPurpose()+"\n"+item.getStartLocation()+"\t"+item.getStartTimeLocal()+"\n"+item.getEndLocation()+"\t"+item.getEndTimeLocal()+"\n"+item.getDistance());
    }

    @Override
    public int getItemCount() {
        return this.count;
    }
}
