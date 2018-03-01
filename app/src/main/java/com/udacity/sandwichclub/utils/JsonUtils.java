package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Log.d("PARSEJSON",json.toString());
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichObject =  new JSONObject(json);
            String name = sandwichObject.getJSONObject("name").getString("mainName");
            String image = sandwichObject.getString("image");
            String placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            String description = sandwichObject.getString("description");
            List<String> alsoKnownAs = toStringList(sandwichObject.getJSONObject("name").getJSONArray("alsoKnownAs"));
            List<String> ingredients = toStringList(sandwichObject.getJSONArray("ingredients"));
            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setIngredients(ingredients);
            sandwich.setDescription(description);
            sandwich.setMainName(name);
            sandwich.setImage(image);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    public static List<String> toStringList(JSONArray arr) throws JSONException {
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < arr.length(); i++){
            list.add(arr.getString(i));
        }
        return list;

    }
}
