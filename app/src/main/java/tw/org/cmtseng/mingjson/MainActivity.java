package tw.org.cmtseng.mingjson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int FUNC_LOGIN = 1;
    private static final String TAG ="vanessa" ;
    boolean logon = false;
    String[] func = {"Balance", "History", "news", "finance", "exit"};
    int[] icons = {R.drawable.func_balance,
            R.drawable.func_history,
            R.drawable.func_news,
            R.drawable.func_finance,
            R.drawable.func_exit};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildview();
        if (!logon) {
            Intent intnet = new Intent(this, LoginActivity.class);
//            startActivity(intnet);
            startActivityForResult(intnet,FUNC_LOGIN);
        }
    }

    private void buildview() {
        Log.d(TAG, "onCreate: " + "on MainActivity  buildview()  ");
        GridView grid = (GridView) findViewById(R.id.grid);
        iconAdapter gAdapter = new iconAdapter();
//        ArrayAdapter gAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, func);
        grid.setAdapter(gAdapter);
        grid.setOnItemClickListener(this);
        //listView
//        ListView list = (ListView) findViewById(R.id.list);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, func);
//        list.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FUNC_LOGIN) {
            if (resultCode == RESULT_OK) {
                String uid = data.getStringExtra("LOGIN_USERID");
                String pw = data.getStringExtra("LOGIN_PASSWD");
//                Log.d(TAG, "onActivityResult: "+ uid+"/" +pw);
            }else{
                finish();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch ((int) id) {
            case R.drawable.func_balance :
                Log.d(TAG, "onItemClick: 0 func_balance");
                break;
            case R.drawable.func_history:
                Log.d(TAG, "onItemClick: 1 func_history");
                startActivity(new Intent(this, TransActivity.class));
                break;
            case R.drawable.func_news:
                Log.d(TAG, "onItemClick: 2 func_news");
                break;
            case R.drawable.func_finance:
                Log.d(TAG, "onItemClick: 3 func_finance");
                break;
            case R.drawable.func_exit:
                Log.d(TAG, "onItemClick: func_exit");
                finish();
                break;

        }

        }


    private class iconAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int i) {
            return func[i];
        }

        @Override
        public long getItemId(int i) {
            return icons[i];
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
//                row = getLayoutInflater().inflate(R.layout.item_row, null);
                row = getLayoutInflater().inflate(R.layout.item_row, null);  //使用呼叫Activity的getLayoutInflater方法 取得 Layoutlnflater物件,再亻吏用它的 inflate方法 ,參考 版面檔R.layout.itemrow  建立一實際的View 物件。
                ImageView image = (ImageView) row.findViewById(R.id.item_image);
                TextView text  = (TextView) row.findViewById(R.id.item_text);
                image.setImageResource(icons[i]);
                text.setText(func[i]);
            }
            return row;
        }
    }
}
