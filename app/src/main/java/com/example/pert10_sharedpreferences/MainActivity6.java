package com.example.pert10_sharedpreferences;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pert10_sharedpreferences.databinding.ActivityMain6Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity6 extends AppCompatActivity implements
        View.OnClickListener{
    //declaration variable
    private ActivityMain6Binding binding;
    String index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setup view binding
        binding = ActivityMain6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fetchButton.setOnClickListener(this);
    }
    //onclik button fetch
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fetch_button){
            index = binding.inputId.getText().toString();
            try {
                getData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    //get data using api link
    public void getData() throws MalformedURLException {
        Uri uri = Uri.parse("https://gist.githubusercontent.com/lukebaker/1040981/raw/07fbd3e76d9a9f19c2cca143b892c6985c5632a4/playlist-sample.json").buildUpon().build();
        URL url = new URL(uri.toString());
        new DOTask().execute(url);
    }
    class DOTask extends AsyncTask<URL, Void, String>{
        //connection request
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls [0];
            String data = null;
            try {
                data = NetworkUtils.makeHTTPRequest(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String s){
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //get data json
        public void parseJson(String data) throws JSONException{
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data);
            }catch (JSONException e){
                e.printStackTrace();
            }
            JSONObject innerObj = jsonObject.getJSONObject("playlist");
            JSONArray cityArray = innerObj.getJSONArray("videos");
            for (int i =0; i <cityArray.length(); i++){
                JSONObject obj = cityArray.getJSONObject(i);
                String Sobj = obj.get("id").toString();
                if (Sobj.equals(index)){
//                    String name = obj.get("name").toString();
//                    binding.resultName.setText(name);
//                    break;
                    String id = obj.get("title").toString();
                    binding.resultId.setText(id);
                    String created_at = obj.get("thumbnail").toString();
                    binding.resultCreated.setText(created_at);
                    String updated_at = obj.get("poster").toString();
                    binding.resultUpdated.setText(updated_at);
                    String name = obj.get("hi").toString();
                    binding.resultName.setText(name);
                    String description = obj.get("med").toString();
                    binding.resultDescription.setText(description);
                    String qty = obj.get("low").toString();
                    binding.resultQty.setText(qty);
//                    String price = obj.get("price").toString();
//                    binding.resultPrice.setText(price);
//                    String rating = obj.get("rating").toString();
//                    binding.resultRating.setText(rating);
//                    String image = obj.get("image").toString();
//                    binding.resultImage.setText(image);
                    break;
                }
                else{
                    binding.resultName.setText("Not Found");
                }
            }
        }
    }
}