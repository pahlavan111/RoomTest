package com.bp.roomtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Button btnSave, btnShow;
    EditText edtName, edtFamily, edtAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSave = findViewById(R.id.btn_save);
        btnShow = findViewById(R.id.btn_show);
        edtAge = findViewById(R.id.edt_age);
        edtFamily = findViewById(R.id.edt_family);
        edtName = findViewById(R.id.edt_name);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edtName.getText().toString();
                String family = edtFamily.getText().toString();
                String age = edtAge.getText().toString();

                if (inputAreCorrect(name, family, age)) {
                    hideKeyboard(MainActivity.this);

                    PersonRepository repository = new PersonRepository(getApplicationContext());
                    repository.getPersonDatabase().personDAO().insertPerson(new Person(name, family, Integer.parseInt(age)))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<Long>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(Long aLong) {

                                    Toast.makeText(MainActivity.this, aLong + "", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });

                    clearInputs();
                }
            }
        });


    }

    Boolean inputAreCorrect(String name, String family, String age) {


        if (name.isEmpty()) {
            edtName.setError("Enter a name please!");
            edtName.requestFocus();
            return false;

        }
        if (family.isEmpty()) {
            edtFamily.setError("Enter a family please!!");
            edtFamily.requestFocus();
            return false;
        }
        if (age.isEmpty()) {
            edtAge.setError("please enter a right value of age !!!");
            edtAge.requestFocus();
            return false;
        }


        return true;
    }

    void clearInputs() {
        edtAge.setText("");
        edtFamily.setText("");
        edtName.setText("");

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
    
    

