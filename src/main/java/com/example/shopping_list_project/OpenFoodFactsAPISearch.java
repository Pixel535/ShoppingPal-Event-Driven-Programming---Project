package com.example.shopping_list_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

public class OpenFoodFactsAPISearch {

    public static String APIURL;
    public static String APISearchFilter;
    public static String product_key;
    public static String productName_key;
    public static String price_key;

    public List<Product> SearchForProduct(String userGivenProductName) {
        try {
            ConfigPropertiesReader.ReadPropertiesFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Product> result = new ArrayList<>();
        userGivenProductName = StringUtils.replace(userGivenProductName, " ", "+");
        try{
            String url = APIURL + userGivenProductName + APISearchFilter;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject obj = new JSONObject(response.toString());
                JSONArray arr = obj.getJSONArray(product_key);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject product = arr.getJSONObject(i);
                    if (!product.has(productName_key)) {
                        continue;
                    }
                    String productName = product.getString(productName_key);
                    double productPrice = product.optDouble(price_key, 0.0);
                    Product newProduct = new Product(productName, 1, productPrice);
                    result.add(newProduct);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
