package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String NAME_KEY = "name";
    private static final String SANDWICH_NAME_KEY = "mainName";
    private static final String ALSO_KNOWN_AS_NAME_KEY = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "image";
    private static final String INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject namesObj = jsonObject.getJSONObject(NAME_KEY);

            String mainName = parseStringFromJson(namesObj, SANDWICH_NAME_KEY);
            sandwich.setMainName(mainName);

            List<String> alsoKnownAs = parseListFromJson(namesObj, ALSO_KNOWN_AS_NAME_KEY);
            sandwich.setAlsoKnownAs(alsoKnownAs);

            String placeOfOrigin = parseStringFromJson(jsonObject, PLACE_OF_ORIGIN_KEY);
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            String description = parseStringFromJson(jsonObject, DESCRIPTION_KEY);
            sandwich.setDescription(description);

            String image = parseStringFromJson(jsonObject, IMAGE_KEY);
            sandwich.setImage(image);

            List<String> ingredients = parseListFromJson(jsonObject, INGREDIENTS_KEY);
            sandwich.setIngredients(ingredients);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static String parseStringFromJson(JSONObject jsonObject, String keyName) {
        String keyValue = "";
        try {
            keyValue = jsonObject.getString(keyName);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        return keyValue;
    }

    private static List<String> parseListFromJson(JSONObject jsonObject, String keyName) {
        List<String> keyValues = new ArrayList<>();

        try{
            JSONArray jsonArray = jsonObject.getJSONArray(keyName);

            if(jsonArray != null) {

                for(int i = 0; i < jsonArray.length(); i++) {
                    keyValues.add(jsonArray.getString(i));
                }

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return keyValues;
    }

}
