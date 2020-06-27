package com.bp.roomtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

    Activity activity;
    List<Person> people;
    PersonRepository repository;

    public PersonAdapter(Activity activity, List<Person> people) {
        this.activity = activity;
        this.people = people;
        repository = new PersonRepository(activity);
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.person_raw, null, false);
        return new PersonHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {

        final Person person = people.get(position);
        holder.txt_name.setText(person.getName());
        holder.txt_family.setText(person.getFamily());
        holder.txt_age.setText(person.getAge() + "");


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(activity, "delete", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(activity);
                builder.setTitle("Delete Person");
                builder.setMessage("your person data will be deleted");
                builder.setIcon(R.drawable.ic_launcher_foreground);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.getPersonDatabase().personDAO().deletePerson(person)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<Integer>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(Integer integer) {

                                        if (integer >= 1) {
                                            people.remove(person);
                                            notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();


            }


        });


        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(activity, "edit", Toast.LENGTH_SHORT).show();


                AlertDialog.Builder builder= new AlertDialog.Builder(activity);
                final LayoutInflater inflater= LayoutInflater.from(activity);
                View view= inflater.inflate(R.layout.dialog_edit,null);
                builder.setView(view);
                final AlertDialog dialog=builder.create();
                dialog.show();

                final EditText edt_Name,edt_family,edt_age;
                Button btn_edit,btn_cancel;

                edt_Name = dialog.findViewById(R.id.edt_name_dialog);
                edt_family = dialog.findViewById(R.id.edt_family_dialog);
                edt_age = dialog.findViewById(R.id.edt_age_dialog);
                btn_edit = dialog.findViewById(R.id.btn_edit_dialog);
                btn_cancel = dialog.findViewById(R.id.btn_cancel_dialog);

                edt_Name.setText(person.getName());
                edt_family.setText(person.getFamily());
                edt_age.setText(String.valueOf(person.getAge()));

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edt_Name.getText().toString();
                        String family = edt_family.getText().toString();
                        String age = edt_age.getText().toString();

                        if (name.isEmpty()) {
                            edt_Name.setError("Enter a name please!");
                            edt_Name.requestFocus();
                        }else if (family.isEmpty()) {
                            edt_family.setError("Enter a family please!!");
                            edt_family.requestFocus();
                        }else if (age.isEmpty()) {
                            edt_age.setError("please enter a right value of age !!!");
                            edt_age.requestFocus();
                        }else {
                            person.setName(name);
                            person.setFamily(family);
                            person.setAge(Integer.parseInt(age));
                            repository.getPersonDatabase().personDAO().updatePerson(person)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<Integer>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onSuccess(Integer integer) {
                                            if (integer==1){
                                                Toast.makeText(activity, "Person Information updated", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }
                                    });
                        }
                    }
                });




            }
        });

    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public static class PersonHolder extends RecyclerView.ViewHolder {

        TextView txt_name, txt_family, txt_age;
        Button btn_edit, btn_delete;

        public PersonHolder(@NonNull View itemView) {
            super(itemView);

            txt_age = itemView.findViewById(R.id.txt_Age);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_family = itemView.findViewById(R.id.txt_family);

            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }

}
