package danysam.net.churchdirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import danysam.net.churchdirectory.connection.ConnectionManager;
import danysam.net.churchdirectory.model.Auth;
import danysam.net.churchdirectory.session.SessionManager;
import danysam.net.churchdirectory.ui.activity.ContactsActivity;
import danysam.net.churchdirectory.ui.activity.RegisterActivity;
import danysam.net.churchdirectory.ui.activity.WebViewActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


@SuppressWarnings("FieldCanBeLocal")
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView email;
    private EditText password;
    private Button signIn;
    private Button register;
    private Button guestLoginButton;
    private Button passwordReset;
    private Toolbar toolbar;

    private Observable<Response<Auth>> auth(String userName, String password) {
        return ConnectionManager.getInstance().getClient().auth(userName, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.email_sign_in_button);
        register = findViewById(R.id.email_register_button);
        guestLoginButton = findViewById(R.id.guestLoginButton);
        passwordReset = findViewById(R.id.passwordResetButton);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        if(getIntent().getExtras()!=null){
            if(getIntent().getBooleanExtra("SplashRedirect", false) == true){
                getSupportActionBar().setHomeButtonEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }else{
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }else{
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }




        signIn.setOnClickListener(view -> {
            auth(email.getText().toString(), password.getText().toString())
                    .subscribe(this::setAuth, this::authError);
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        register.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));

        guestLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        passwordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/passwordReset/User/passwordReset.php?");
                startActivity(intent);
            }
        });

    }

    private void authError(Throwable throwable) {
        Toast.makeText(this, "Network Error\n" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("ConstantConditions")
    private void setAuth(Response<Auth> authResponse) {

        if (authResponse.isSuccessful()) {
            if (authResponse.body().getSuccess()) {
                SessionManager.getInstance().setSession(this,
                        authResponse.body().getResponse().getUid(),
                        email.getText().toString(),
                        authResponse.body().getResponse().getToken(),
                        authResponse.body().getResponse().getIsMember());

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("hasLoggedIn", "true");
                editor.apply();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();

            } else {
                password.setError("");
                Toast.makeText(this, authResponse.body().getError(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

