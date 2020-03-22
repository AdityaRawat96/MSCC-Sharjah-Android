package mscc.net.churchdirectory.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import mscc.net.churchdirectory.LoginActivity;
import mscc.net.churchdirectory.MainActivity;
import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.connection.ConnectionManager;
import mscc.net.churchdirectory.model.Register;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText nameEt;
    private EditText phoneEt;
    private EditText emailEt;
    private EditText residenceEt;
    private EditText homeParishEt;
    private EditText dioceseEt;
    private Button register;
    private Button cancel;

    private Boolean isNameValid = false;
    private Boolean isPhoneValid = false;
    private Boolean isEmailValid = false;
    private Boolean isResidenceValid = false;
    private Boolean isHomeParishValid = false;
    private Boolean isDioceseValid = false;

    private Observable<Response<Register>> registration(String name, String phone, String email, String residence, String homeParish, String diocese) {
        return ConnectionManager
                .getInstance()
                .getClient()
                .register(name, phone, email, residence, homeParish, diocese)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = findViewById(R.id.toolbar);
        nameEt = findViewById(R.id.register_name);
        phoneEt = findViewById(R.id.register_contact);
        emailEt = findViewById(R.id.register_email);
        residenceEt = findViewById(R.id.register_residence);
        homeParishEt = findViewById(R.id.register_home_parish);
        dioceseEt = findViewById(R.id.register_diocese);
        register = findViewById(R.id.register);
        cancel = findViewById(R.id.cancel);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration");

        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isNameValid = s.length() > 0;
                if (isNameValid) nameEt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPhoneValid = s.length() > 0;
                if (isPhoneValid) phoneEt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEmailValid = s.length() > 0;
                if (isEmailValid) emailEt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        residenceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isResidenceValid = s.length() > 0;
                if (isResidenceValid) residenceEt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        homeParishEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isHomeParishValid = s.length() > 0;
                if (isHomeParishValid) homeParishEt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dioceseEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isDioceseValid = s.length() > 0;
                if (isDioceseValid) dioceseEt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cancel.setOnClickListener(v -> finish());

        register.setOnClickListener(v -> {
            if (isNameValid && isPhoneValid && isEmailValid && isResidenceValid && isHomeParishValid && isDioceseValid) {
                registration(nameEt.getText().toString(),
                        phoneEt.getText().toString(),
                        emailEt.getText().toString(),
                        residenceEt.getText().toString(),
                        homeParishEt.getText().toString(),
                        dioceseEt.getText().toString())
                        .subscribe(this::onRegistered, this::onError);
                register.setEnabled(false);
            } else {
                Toast.makeText(this, "Please fill in all the data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onError(Throwable throwable) {
        Log.e("RX Network Error", throwable.getLocalizedMessage());
    }

    private void onRegistered(Response<Register> registerResponse) {
        register.setEnabled(true);
        if (registerResponse.isSuccessful()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(registerResponse.body().getMessage());
            builder.setPositiveButton("Ok", (dialog, id) -> {
                Intent i = new Intent(this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
