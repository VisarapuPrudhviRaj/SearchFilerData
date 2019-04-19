package com.lokeshlabs.searchfilerdata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchableFilterActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ArrayList<ContactModel> contactModelArrayList = new ArrayList<>();

    @BindView(R.id.filter_rv)
    RecyclerView filterRv;

    public static final String BASE_URL = "https://api.androidhive.info/json/contacts.json";
    @BindView(R.id.serachView)
    SearchView serachView;

    FilterAdapter filterAdapter;

    FilterAdapter.ContactListener listener;
    List<ContactModel> dataFilteredList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable_filter);
        ButterKnife.bind(this);

        filterRv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        filterRv.setLayoutManager(layoutManager);

        serachView.setQueryHint("Search");
        serachView.onActionViewExpanded();
        serachView.setIconifiedByDefault(false);

        serachView.setOnQueryTextListener(this);


        if (Helper.isNetworkAvailable(this)){

            loadData();
        }else {
            Toast.makeText(this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadData() {

        JsonArrayRequest postRequest = new JsonArrayRequest(BASE_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Success", "onResponse: " + response.toString());
                parseResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "onResponse: " + error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);
    }

    private void parseResponse(JSONArray response) {

        try {
            JSONArray resultArray = response;
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                ContactModel model = new ContactModel();
                model.setName(resultObject.getString("name"));
                model.setPhone(resultObject.getString("phone"));
                model.setImage(resultObject.getString("image"));
                contactModelArrayList.add(model);

                 filterAdapter = new FilterAdapter(contactModelArrayList, SearchableFilterActivity.this,dataFilteredList);
                filterRv.setAdapter(filterAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterAdapter.getFilter().filter(newText);
        return false;
    }
}
