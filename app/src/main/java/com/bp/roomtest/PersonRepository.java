package com.bp.roomtest;

import android.content.Context;

import androidx.room.Room;

public class PersonRepository {

    private String DB_NAME = "person_db";
    private PersonDatabase personDatabase;
    private Context context;

    public PersonRepository(Context context) {
        this.context = context;

        personDatabase= Room.databaseBuilder(context,PersonDatabase.class,DB_NAME)
             //   .allowMainThreadQueries()
                .build();
    }


    public PersonDatabase getPersonDatabase() {
        return personDatabase;
    }
}
