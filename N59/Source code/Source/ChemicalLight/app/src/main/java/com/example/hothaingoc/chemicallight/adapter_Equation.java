package com.example.hothaingoc.chemicallight;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter_Equation extends RecyclerView.Adapter<adapter_Equation.ViewHolder>{

    ArrayList<data_Equation> dataEqua;
    Activity context;

    public adapter_Equation(ArrayList<data_Equation> dataEqua, Activity context) {
        this.dataEqua = dataEqua;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_equation,parent,false);
        return new adapter_Equation.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView_TG.setText(dataEqua.get(position).getTG());
        holder.textView_SP.setText(dataEqua.get(position).getSP());

    }

    @Override
    public int getItemCount() {
        return dataEqua.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_TG;
        TextView textView_SP;

        public ViewHolder(View itemView) {
            super(itemView);

            textView_TG = (TextView) itemView.findViewById(R.id.txt_TG);
            textView_SP = (TextView) itemView.findViewById(R.id.txt_SP);

        }
    }
}
