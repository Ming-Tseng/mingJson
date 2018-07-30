package tw.org.cmtseng.mingjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TransActivity extends AppCompatActivity {

    private static final String TAG  = "vanessa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        Log.d(TAG, "onCreate: " + "into  LoginActivity");
        new TransTask().execute("http://atm201605.appspot.com/h");
    }

    private class TransTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuffer sb = new StringBuffer();
            try {
                URL url = new URL(strings[0]);
                InputStream is = url.openStream();
                InputStreamReader sr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(sr);
                //上3行可以合併成下1行
//                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = in.readLine();
                while(line!=null){
//                    Log.d(TAG, line);
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG, "onPostExecute: "+s);
//            parseJSON(s);
            parseGSON(s);
        }
    }

    private void parseGSON(String s) {
        Gson gson = new Gson();
        ArrayList<TransTask> list = gson.fromJson(s, new TypeToken<ArrayList<Transaction>>(){}.getType());
        Log.d(TAG,list.size()+"/"+list.get(0));
    }

    private void parseJSON(String s) {
        ArrayList<Transaction> trans = new ArrayList<>(); //先準備ArrayList集合物件 trans 裏面只能存放  Transaction物件。
        try {
            JSONArray array = new JSONArray(s);    //將傳入的字串s  交給JSONArray的建構子,產生array物件。
            for (int i = 0; i <array.length() ; i++) {   //以迴圈依取出交易記錄
                JSONObject obj = array.getJSONObject(i);  //  以索引值取得JSONObject物件 obj

                String account =obj.getString("account");  //呼叫JSONObject的  getxx方法取得各個屬性值。
                String date = obj.getString("type");
                int amount = obj.getInt("amount");
                int type = obj.getInt("type");

                Log.d(TAG,account+"/"+date+"/"+amount+"/"+type);
                Transaction t = new Transaction(account, date, amount, type); ////產生Transaction物件,代表一筆交易記錄
                trans.add(t);  // 將物件加入集合中.
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
