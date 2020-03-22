package mscc.net.churchdirectory.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import mscc.net.churchdirectory.LoginActivity;
import mscc.net.churchdirectory.R;

public class RegisterGuestActivity extends AppCompatActivity {

    EditText ed_fname;
    EditText ed_lname;
    EditText ed_email;
    EditText ed_phone;
    Toolbar toolbar;
    Button cancelButton;

    public static final String URL_REGISTER = "http://msccsharjah.com/api/guestRegistration/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_register);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration");

        ed_fname = findViewById(R.id.ed_fname);
        ed_lname = findViewById(R.id.ed_lname);
        ed_email = findViewById(R.id.ed_email);
        ed_phone = findViewById(R.id.ed_phone);

        cancelButton = findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(RegisterGuestActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void register(View view){
        final String fname = ed_fname.getText().toString();
        final String lname = ed_lname.getText().toString();
        final String email = ed_email.getText().toString();
        final String phone = ed_phone.getText().toString();

        if(fname.isEmpty() || lname.isEmpty() || email.isEmpty() || phone.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }

        else {
            class Login extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(RegisterGuestActivity.this);

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    //this method will be running on UI thread
                    pdLoading.setMessage("\tLoading...");
                    pdLoading.setCancelable(false);
                    pdLoading.show();
                }

                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();

                    //creating request parameters
                    HashMap<String, String> params = new HashMap<>();

                    params.put("email", email);
                    params.put("fname", fname);
                    params.put("lname", lname);
                    params.put("phone", phone);

                    //returing the response
                    return requestHandler.sendPostRequest(URL_REGISTER, params);
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    pdLoading.dismiss();

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(result);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(RegisterGuestActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterGuestActivity.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            }

            Login login = new Login();
            login.execute();
        }
    }

    public void login(View view){
        finish();
        Intent intent = new Intent(RegisterGuestActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}