package com.udacity.syed.newsapplication.utils;

import com.udacity.syed.newsapplication.Article;
import com.udacity.syed.newsapplication.Source;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shoiab on 2017-09-12.
 */

public class JSONUtils {

    public static ArrayList<Source> parseSourceJSONString(String JSONString) {

        ArrayList<Source> sources = new ArrayList<>();
        JSONObject baseJSON = null;
        try {
            baseJSON = new JSONObject(JSONString);
            JSONArray resultArray = baseJSON.getJSONArray("sources");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject sourceObject = resultArray.getJSONObject(i);
                String id = sourceObject.getString("id");
                String name = sourceObject.getString("name");
                String desc = sourceObject.getString("description");
                String url = sourceObject.getString("url");
                String category = sourceObject.getString("category");

                sources.add(new Source(name, id, desc, url, category));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sources;
    }

    public static ArrayList<Article> parseArticleJSONString(String JSONString) {

        ArrayList<Article> articles = new ArrayList<>();
        JSONObject baseJSON = null;
        try {
            baseJSON = new JSONObject(JSONString);
            JSONArray resultArray = baseJSON.getJSONArray("articles");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject sourceObject = resultArray.getJSONObject(i);
                String author = sourceObject.getString("author");
                String title = sourceObject.getString("title");
                String desc = sourceObject.getString("description");
                String url = sourceObject.getString("url");
                String imgURL = sourceObject.getString("urlToImage");
                String date = sourceObject.getString("publishedAt");

                articles.add(new Article(author, title, desc, url, imgURL, date));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return articles;
    }

}
