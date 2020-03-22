package mscc.net.churchdirectory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import mscc.net.churchdirectory.ui.activity.RegisterActivity;
import mscc.net.churchdirectory.ui.activity.RegisterGuestActivity;
import mscc.net.churchdirectory.ui.activity.WebViewActivity;

import mscc.net.churchdirectory.connection.ConnectionManager;
import mscc.net.churchdirectory.model.Auth;
import mscc.net.churchdirectory.session.SessionManager;

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
    ToggleSwitch loginToggleSwitch;
    private int toggleIndex = 0;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

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
        loginToggleSwitch = (ToggleSwitch) findViewById(R.id.loginToggleSwitch);

        loginToggleSwitch.setCheckedPosition(0);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        if(getIntent().getExtras()!=null){
            if(getIntent().getBooleanExtra("SplashRedirect", false)){
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


        loginToggleSwitch.setOnChangeListener(new ToggleSwitch.OnChangeListener(){
            @Override
            public void onToggleSwitchChanged(int position) {
                toggleIndex = position;
            }
        });

        signIn.setOnClickListener(view -> {

           if(toggleIndex == 0){
               auth(email.getText().toString(), password.getText().toString())
                       .subscribe(this::setAuth, this::authError);
           }else{
               checkLogin();
           }

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleIndex == 0){
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(LoginActivity.this, RegisterGuestActivity.class);
                    startActivity(intent);
                }
            }
        });

        passwordReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String apiUrl;

                if(toggleIndex == 0){
                    apiUrl = "http://msccsharjah.com/passwordReset/User/passwordReset.php?";
                }else{
                    apiUrl = "http://msccsharjah.com/passwordReset/Guest/passwordReset.php?";
                }

                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                intent.putExtra("urlString", apiUrl);
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

    public void checkLogin() {

        final String emailText = email.getText().toString();
        final String passwordText = password.getText().toString();

        new AsyncLogin().execute(emailText, passwordText);

    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://msccsharjah.com/api/guestLogin/");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                Toast.makeText(LoginActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();

               // Intent intent = new Intent(LoginActivity.this, MainActivity.class);
               // startActivity(intent);
               // LoginActivity.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(LoginActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


}

