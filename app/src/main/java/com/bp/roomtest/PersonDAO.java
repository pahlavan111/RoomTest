package com.bp.roomtest;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PersonDAO {

    @Insert
    Single<Long> insertPerson(Person person);

    @Update
    Single<Integer> updatePerson(Person person);

    @Delete
    Single<Integer> deletePerson(Person person);

    @Query(" SELECT * FROM person ORDER BY id DESC")
    Single<List<Person>> getPersonList();

}
