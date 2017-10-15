package com.udacity.syed.newsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.syed.newsapplication.utils.JSONUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoiab on 2017-09-11.
 */

public class PageFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Article>> {
    public static final String ARG_PAGE = "ARG_PAGE";
    public ArticleResultReciever receiver;
    String category;
    ArticleRVAdapter rvAdapter;
    List<Article> articles;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    Boolean doNotReload = true;
    private int mPage;

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

        setupServiceReceiver();
        articles = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(null);

                Intent intent = new Intent(getContext(), NetworkIntentService.class);
                intent.putExtra("category", category);
                intent.putExtra("receiver", receiver);                                                              // We setup the reciever which extents  ResultReceiver instead of

                getContext().startService(intent);
            }
        });
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
        if (doNotReload) {

            rvAdapter = new ArticleRVAdapter(data, getActivity());

            recyclerView.setAdapter(rvAdapter);
            doNotReload = false;
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Article>> loader) {
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


    public void setupServiceReceiver() {
        receiver = new ArticleResultReciever(new Handler());
        // This is where we specify what happens when data is received from the service
        receiver.setReceiver(new ArticleResultReciever.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                byte[] resultValue = resultData.getByteArray("resultValue");
                recyclerView.setAdapter(null);

                String responseString = new String(resultValue);
                List<Article> resultArticles = new ArrayList<Article>();

                resultArticles = JSONUtils.parseArticleJSONString(responseString);
                articles.addAll(resultArticles);

                rvAdapter = new ArticleRVAdapter(articles, getActivity());
                recyclerView.setAdapter(rvAdapter);
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }
}
