package com.example.a16022706.c302_photostoreclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvCategories;
    ArrayList<String> alCategories = new ArrayList<String>();
    ArrayAdapter<String> aaCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        lvCategories = (ListView) findViewById(R.id.listViewCategories);
        aaCategories = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alCategories);
        lvCategories.setAdapter(aaCategories);

        // Code for step 1 start
        HttpRequest request = new HttpRequest
                ("http://10.0.2.2/C302_P06_PhotoStoreWS/getCategories.php");
        request.setOnHttpResponseListener(mHttpResponseListener);
        request.setMethod("GET");
        request.execute();
        // Code for step 1 end
    }

    // Code for step 2 start
    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){

                    // process response here
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObj = jsonArray.getJSONObject(i);

                            int categoryId = jsonObj.getInt("category_id");
                            String categoryName = jsonObj.getString("name");
                            String description = jsonObj.getString("description");

                            String displayResults = "Category Id: " + categoryId + "\n\nCategory Name: "
                                    + categoryName + "\n\nDescription: " + description + "\n";
                            alCategories.add(displayResults);
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                    aaCategories.notifyDataSetChanged();
                }
            };
    // Code for step 2 end

}

