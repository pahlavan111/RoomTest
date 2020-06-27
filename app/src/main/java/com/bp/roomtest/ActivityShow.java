package com.bp.roomtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActivityShow extends AppCompatActivity {

    RecyclerView rv_person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        rv_person = findViewById(R.id.rv_person);


        PersonRepository repository = new PersonRepository(getApplicationContext());
        repository.getPersonDatabase().personDAO().getPersonList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Person>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Person> people) {

                        Toast.makeText(getApplicationContext(), people.size() + "", Toast.LENGTH_SHORT).show();
                        showDataList(people,getApplicationContext());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }

    void showDataList(List<Person> personList, Context context){

        rv_person.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        rv_person.setAdapter(new PersonAdapter(context,personList));

    }



}
