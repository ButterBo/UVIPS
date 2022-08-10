package sg.edu.rp.c346.id21044912.uvips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView lvUVLight;
    ArrayList<String> alString;
    ArrayAdapter<String> aaString;
    Spinner spinFilter;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvUVLight = findViewById(R.id.listViewUV);
        spinFilter = findViewById(R.id.spinner);

        alString = new ArrayList<String>();
        aaString = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, alString);
        spinFilter.setAdapter(aaString);

        client = new AsyncHttpClient();

    }//end of onCreate()

    @Override
    protected void onResume() {
        DBHelper dbh = new DBHelper(MainActivity.this);
        super.onResume();

        ArrayList<UVLight> alUV = new ArrayList<UVLight>();
//        ArrayAdapter<UVLight> aaUV = new ArrayAdapter<UVLight>(this, android.R.layout.simple_list_item_1, alUV);
//        lvUVLight.setAdapter(aaUV);
        CustomAdaptor caUV = new CustomAdaptor(this, R.layout.row, alUV);
        lvUVLight.setAdapter(caUV);


        client.get("https://api.data.gov.sg/v1/environment/uv-index", new JsonHttpResponseHandler(){
            int val;
            int same = -1;
            String time;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    //retrieve the JsonArray that is called "items" which is contained into jsonArrItems
                    JSONArray jsonArrItems = response.getJSONArray("items");
                    //retrieve and contain the first object of the JSON array "item"
                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
                    //Used to reference "forecasts" in the JSON array "item"
                    JSONArray jsonArrIndexes = firstObj.getJSONArray("index");

                    for (int x=0;x<jsonArrIndexes.length();x++){
                        JSONObject jsonObjUV = jsonArrIndexes.getJSONObject(x);
                        val = jsonObjUV.getInt("value");

                        if(val != same){
                            alString.add(val+"");
                            same = val;
                        }

                        time = jsonObjUV.getString("timestamp");
                        UVLight uvindex = new UVLight(val, time);
                        dbh.insertUV(val,time);

                    }

                }
                catch (JSONException e){
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }

//               aaUV.notifyDataSetChanged();
                alUV.addAll(dbh.getAllUVI());
                aaString.notifyDataSetChanged();
                caUV.notifyDataSetChanged();

            }
        });
    }//end of onResume()
}