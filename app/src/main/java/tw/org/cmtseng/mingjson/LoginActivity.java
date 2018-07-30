package tw.org.cmtseng.mingjson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "vanessa";
    private String uid, pw;
    private EditText edtuserid;
    private EditText edPasswd;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate:  34 " + "into  LoginActivity");
        buildview();

    }


    private void buildview() {
        edtuserid = (EditText) findViewById(R.id.userid);
        edPasswd = (EditText) findViewById(R.id.passwd);
        Button btnlogin = (Button) findViewById(R.id.login);
        btnlogin.setOnClickListener(this);
        Button btncancel = (Button) findViewById(R.id.cancel);
        pref = getSharedPreferences("atm", MODE_PRIVATE);
        edtuserid.setText(pref.getString("PREF_USERID", ""));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d(TAG, "buildview: "+ database.toString());
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: " + " in");
        view.getId();
        switch (view.getId()) {
            case R.id.login:
//                Log.d(TAG, "onClick: " + " view33" );
                uid = edtuserid.getText().toString();
                pw = edPasswd.getText().toString();
//                String s = "http://j.snpy.org/atm/login?userid=" + uid + "&pw=" + pw;
                String s = "https://udn.com";//   Log.d(TAG, "onCreate:  " + s);
                //需在此進行非同步下載 . 此例是為  asyncTask  ,使用內部類 (inner class)
                new LoginTask().execute(s);
                break;
            case R.id.cancel:
                Log.d(TAG, "onClick: " + " view33");

        }
    }

    class LoginTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            int data = 0;
            try {
                URL url = new URL(params[0]);
                InputStream is = url.openStream();
                data=is.read();//                Log.d(TAG, "data : "+data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == 60) {
                Log.d(TAG, "onPostExecute:  Data = 60 " +integer);
                pref.edit().putString("PREF_USERID", uid).apply();
                Log.d(TAG, "onPostExecute:  pref.getString = "   + pref.getString("PREF_USERID","mingdefault")  );
//                Log.d(TAG, "onPostExecute: 登入成功");
                getIntent().putExtra("LOGIN_USERID",uid);
                getIntent().putExtra("LOGIN_PASSWD", pw);
                setResult(RESULT_OK,getIntent());
                finish();
            }
        }
    }
}
