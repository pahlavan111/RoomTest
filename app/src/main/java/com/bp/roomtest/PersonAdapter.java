package com.bp.roomtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

    Context context;
    List<Person> people;

    public PersonAdapter(Context context, List<Person> people) {
        this.context = context;
        this.people = people;
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.person_raw,null,false);
        return new PersonHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {

        Person person= people.get(position);
        holder.txt_name.setText(person.getName());
        holder.txt_family.setText(person.getFamily());
        holder.txt_age.setText(person.getAge()+"");


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public static class PersonHolder extends RecyclerView.ViewHolder {

        TextView txt_name,txt_family,txt_age;
        Button btn_edit,btn_delete;

        public PersonHolder(@NonNull View itemView) {
            super(itemView);

            txt_age = itemView.findViewById(R.id.txt_Age);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_family = itemView.findViewById(R.id.txt_family);

            btn_edit =itemView.findViewById(R.id.btn_edit);
            btn_delete =itemView.findViewById(R.id.btn_delete);
        }
    }
}
