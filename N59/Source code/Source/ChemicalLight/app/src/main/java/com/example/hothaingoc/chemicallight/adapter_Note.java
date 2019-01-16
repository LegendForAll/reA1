package com.example.hothaingoc.chemicallight;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter_Note extends RecyclerView.Adapter<adapter_Note.ViewHolder>{

    ArrayList<data_Note> dataRec;
    Activity context;

    public adapter_Note() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_note,parent,false);

        return new adapter_Note.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView_id.setText(String.valueOf(dataRec.get(position).getNoteid()));
        holder.textView_time.setText(dataRec.get(position).getNoteTime());
        holder.textView_topic.setText(dataRec.get(position).getNoteTopic());
        holder.textView_main.setText(".....");
    }

    @Override
    public int getItemCount() {
        return dataRec.size();
    }

    public adapter_Note(ArrayList<data_Note> dataRec, Activity context) {
        this.dataRec = dataRec;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_id;
        TextView textView_topic;
        TextView textView_time;
        TextView textView_main;

        public ViewHolder(View itemView) {
            super(itemView);

            textView_id = (TextView) itemView.findViewById(R.id.id_note);
            textView_topic = (TextView) itemView.findViewById(R.id.txt_topicNote);
            textView_time = (TextView) itemView.findViewById(R.id.txt_timeNote);
            textView_main = (TextView) itemView.findViewById(R.id.txt_mainNote);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context,ChemicalEditNote.class);
                    intent.putExtra("ID",textView_id.getText());
                    context.startActivity(intent);

                }
            });
        }
    }
}
