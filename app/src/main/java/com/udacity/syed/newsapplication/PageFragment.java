package com.udacity.syed.newsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * Created by shoiab on 2017-09-11.
 */

public class PageFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Article>>,
                                                                                                                            ArticleRVAdapter.ArticleClickListener
{
    public static final String ARG_PAGE = "ARG_PAGE";
    String category;
    RecyclerView recyclerView;
    private int mPage;
    Boolean doNotReload= true;
    public PageFragment() {
    }

    public static PageFragment newInstance(int page, String cat) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setCategory(cat);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_article);
        LoaderManager loaderManager = getLoaderManager();
        if (loaderManager.hasRunningLoaders()) {
            loaderManager.restartLoader(0, null, this);
        } else {
            loaderManager.initLoader(0, null, this);
        }

        return view;
    }


    @Override
    public android.support.v4.content.Loader<List<Article>> onCreateLoader(int id, Bundle args) {


        return new ArticleLoader(getContext(), category);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Article>> loader, List<Article> data) {
        if(doNotReload) {
            ArticleRVAdapter rvAdapter = new ArticleRVAdapter(data, this);
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(rvAdapter);
            doNotReload=false;
            }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Article>> loader) {
        getLoaderManager().restartLoader(0, null, this);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void articleClickListener(int position, Article article) {
        Intent intent = new Intent(getContext(),DetailArticleActivity.class);
        intent.putExtra("title",article.getTitle());
        intent.putExtra("detail",article.getDescription());
        intent.putExtra("pic",article.getImgURL());
        intent.putExtra("url",article.getArticleURL());
        intent.putExtra("date",article.getPublishedTime());
        intent.putExtra("author",article.getAuthor());
        startActivity(intent);
    }
}
