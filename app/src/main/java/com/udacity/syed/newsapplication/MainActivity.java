package com.udacity.syed.newsapplication;

import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentSender;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.udacity.syed.newsapplication.data.NewsContract;
import com.udacity.syed.newsapplication.data.NewsProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener,
        LoaderManager.LoaderCallbacks<List<Source>> {

    private static final String TAG = "signin1";
    private static final int SIGNED_IN = 0;
    private static final int STATE_SIGNING_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;
    private static final int RC_SIGN_IN = 0;
    private static final int DIALOG_PLAY_SERVICES_ERROR = 0;
    Boolean menuStatus = false;
    Toolbar toolbar;
    LinearLayout signInLayout;
    ViewPager viewPager;
    FloatingActionButton fab;
    DrawerLayout drawer;
    NavigationView navigationView;
    TabLayout tabLayout;
    GridLayout gridLayout;
    RecyclerView recyclerView;
    ArrayList<String> category;
    List<Source> selectedSources;
    List<Source> storedSources;
    Button articleButton;
    private Button mSignInButton;
    private Button mSignOutButton;
    private Button mRevokeButton;
    private TextView mStatus;
    private GoogleApiClient mGoogleApiClient;
    private int mSignInProgress;
    private PendingIntent mSignInIntent;
    private int mSignInError;
    boolean notOnResume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notOnResume=true;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignOutButton = (Button) findViewById(R.id.sign_out_button);
        mRevokeButton = (Button) findViewById(R.id.revoke_access_button);
        mStatus = (TextView) findViewById(R.id.sign_in_status);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        signInLayout = (LinearLayout) findViewById(R.id.sign_layout);
        gridLayout = (GridLayout) findViewById(R.id.grid_category);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_source);
        recyclerView.setVisibility(View.GONE);
        articleButton = (Button) findViewById(R.id.findarticles_button);
        articleButton.setVisibility(View.GONE);

        mSignOutButton.setVisibility(View.GONE);
        mRevokeButton.setVisibility(View.GONE);

        gridLayout.setVisibility(View.GONE);
        mGoogleApiClient = buildApiClient();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
             updateUI();
        } else {
            invalidateOptionsMenu();
        //  toolbar.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
            mSignInButton.setVisibility(View.VISIBLE);

            //drawer.setVisibility(View.GONE);
        gridLayout.setVisibility(View.GONE);
        navigationView.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        mSignInButton.setOnClickListener(this);
        }
        }
        public  void setupPreferences(){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        }

    private GoogleApiClient buildApiClient() {
        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(new Scope("email"))
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void onResume(){
         super.onResume();
    }


   public void updateUI() {

        menuStatus = true;
        invalidateOptionsMenu();
        gridLayout.setVisibility(View.GONE);
        signInLayout.setVisibility(View.GONE);
        mStatus.setVisibility(View.GONE);
        mSignInButton.setVisibility(View.GONE);
        mSignOutButton.setVisibility(View.GONE);
        mRevokeButton.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);
        drawer.setVisibility(View.VISIBLE);
        navigationView.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        SampleFragmentPagerAdapter pageAdapter = new SampleFragmentPagerAdapter(fm, MainActivity.this);

        pageAdapter.setTabs(category.size(), category);
        viewPager.setAdapter(pageAdapter);

        // Give the TabLayout the ViewPager

        tabLayout.setupWithViewPager(viewPager);
        if (category.size() <= 3) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (menuStatus) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        } else return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        } else if (id == R.id.signOut) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();

            mGoogleApiClient.connect();
            notOnResume=true;
            updateSignoutUI();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void updateSignoutUI() {
        menuStatus = false;
        invalidateOptionsMenu();
        signInLayout.setVisibility(View.VISIBLE);
        mStatus.setVisibility(View.VISIBLE);
        mSignInButton.setVisibility(View.VISIBLE);
        mSignOutButton.setVisibility(View.VISIBLE);
        mRevokeButton.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);

        fab.setVisibility(View.GONE);
        navigationView.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        mSignInButton.setEnabled(true);
        mSignOutButton.setEnabled(false);
        mRevokeButton.setEnabled(false);

        mStatus.setText("Signed out");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(this,SavedArticlesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (!mGoogleApiClient.isConnecting()) {
            // We only process button clicks when GoogleApiClient is not transitioning
            // between connected and not connected.
            switch (v.getId()) {
                case R.id.sign_in_button:
                    mStatus.setText("Signing In");
                    resolveSignInError();
                    break;
                case R.id.sign_out_button:
                    // We clear the default account on sign out so that Google Play
                    // services will not return an onConnected callback without user
                    // interaction.
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                    break;
                case R.id.revoke_access_button:
                    // After we revoke permissions for the user with a GoogleApiClient
                    // instance, we must discard it and create a new one.
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    // Our sample has caches no user data from Google+, however we
                    // would normally register a callback on revokeAccessAndDisconnect
                    // to delete user data so that we comply with Google developer
                    // policies.
                    Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
                    mGoogleApiClient = buildApiClient();
                    mGoogleApiClient.connect();
                    break;
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected");

        if(notOnResume) {
            // Update the user interface to reflect that the user is signed in.
            mSignInButton.setEnabled(false);
            mSignOutButton.setEnabled(false);
            mRevokeButton.setEnabled(false);
            notOnResume=false   ;
            // Indicate that the sign in process is complete.
            mSignInProgress = SIGNED_IN;

            // We are signed in!
            // Retrieve some profile information to personalize our app for the user.
            try {
                ////Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                ////mStatus.setText(String.format("Signed In to G+ as %s", currentUser.getDisplayName()));
                String emailAddress = Plus.AccountApi.getAccountName(mGoogleApiClient);
                String selection = NewsContract.CategoryColumns.COLUMN_STATUS + "='" + NewsContract.CategoryColumns.CATEGORY_SELECTED + "'";
                Cursor cursor = getContentResolver().query(NewsProvider.Categories.CONTENT_URI, null, selection, null, null);
                int index = cursor.getColumnIndex(NewsContract.CategoryColumns.COLUMN_NAME);
                category = new ArrayList<>();
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        category.add(cursor.getString(index));
                        cursor.moveToNext();
                    }
                    menuStatus = true;
                    invalidateOptionsMenu();
                    gridLayout.setVisibility(View.GONE);
                    signInLayout.setVisibility(View.GONE);
                    mStatus.setVisibility(View.GONE);
                    mSignInButton.setVisibility(View.GONE);
                    mSignOutButton.setVisibility(View.GONE);
                    mRevokeButton.setVisibility(View.GONE);
                    toolbar.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    drawer.setVisibility(View.VISIBLE);
                    navigationView.setVisibility(View.VISIBLE);
                    setSupportActionBar(toolbar);
                    recyclerView.setVisibility(View.GONE);
                    updateUI();
                    articleButton.setVisibility(View.GONE);
                } else {
                    getFav();
                }
            } catch (Exception ex) {
                String exception = ex.getLocalizedMessage();
                String exceptionString = ex.toString();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        if (mSignInProgress != STATE_IN_PROGRESS) {
            // We do not have an intent in progress so we should store the latest
            // error resolution intent for use when the sign in button is clicked.
            mSignInIntent = connectionResult.getResolution();
            mSignInError = connectionResult.getErrorCode();

            if (mSignInProgress == STATE_SIGNING_IN) {
                // STATE_SIGNING_IN indicates the user already clicked the sign in button
                // so we should continue processing errors until the user is signed in
                // or they click cancel.
                resolveSignInError();
            }
        }

        // In this sample we consider the user signed out whenever they do not have
        // a connection to Google Play services.
        onSignedOut();
    }


    private void resolveSignInError() {
        if (mSignInIntent != null) {
            // We have an intent which will allow our user to sign in or
            // resolve an error.  For example if the user needs to
            // select an account to sign in with, or if they need to consent
            // to the permissions your app is requesting.

            try {
                // Send the pending intent that we stored on the most recent
                // OnConnectionFailed callback.  This will allow the user to
                // resolve the error currently preventing our connection to
                // Google Play services.
                mSignInProgress = STATE_IN_PROGRESS;
                startIntentSenderForResult(mSignInIntent.getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.i(TAG, "Sign in intent could not be sent: "
                        + e.getLocalizedMessage());
                // The intent was canceled before it was sent.  Attempt to connect to
                // get an updated ConnectionResult.
                mSignInProgress = STATE_SIGNING_IN;
                mGoogleApiClient.connect();
            }
        } else {
            // Google Play services wasn't able to provide an intent for some
            // error types, so we show the default Google Play services error
            // dialog which may still start an intent on our behalf if the
            // user can resolve the issue.
            showDialog(DIALOG_PLAY_SERVICES_ERROR);
        }
    }

    private void onSignedOut() {
        // Update the UI to reflect that the user is signed out.
        mSignInButton.setEnabled(true);
        mSignOutButton.setEnabled(false);
        mRevokeButton.setEnabled(false);

        mStatus.setText("Signed out");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    // If the error resolution was successful we should continue
                    // processing errors.
                    mSignInProgress = STATE_SIGNING_IN;

                } else {
                    // If the error resolution was not successful or the user canceled,
                    // we should stop processing errors.
                    mSignInProgress = SIGNED_IN;
                }

                if (!mGoogleApiClient.isConnecting()) {
                    // If Google Play services resolved the issue with a dialog then
                    // onStart is not called so we need to re-attempt connection here.
                    mGoogleApiClient.connect();

                }
                break;
        }
    }

    public void getFav() {

        menuStatus = true;
        invalidateOptionsMenu();
        gridLayout.setVisibility(View.VISIBLE);
        signInLayout.setVisibility(View.GONE);
        mStatus.setVisibility(View.GONE);
        mSignInButton.setVisibility(View.GONE);
        mSignOutButton.setVisibility(View.GONE);
        mRevokeButton.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        drawer.setVisibility(View.VISIBLE);
        navigationView.setVisibility(View.VISIBLE);


        setSupportActionBar(toolbar);

    }

    public void findSources(View v) {
        CheckBox entertainment = (CheckBox) findViewById(R.id.enterTextView);
        CheckBox politic = (CheckBox) findViewById(R.id.politicsTextView);
        CheckBox business = (CheckBox) findViewById(R.id.businessTextView);
        CheckBox sports = (CheckBox) findViewById(R.id.sportsTextView);
        CheckBox science = (CheckBox) findViewById(R.id.scienceTextView);
        CheckBox tech = (CheckBox) findViewById(R.id.techTextView);
        CheckBox music = (CheckBox) findViewById(R.id.musicTextView);
        CheckBox general = (CheckBox) findViewById(R.id.generalTextView);
        CheckBox gaming = (CheckBox) findViewById(R.id.gameTextView);

        category = new ArrayList<>();
        gridLayout.setVisibility(View.GONE);

        if (entertainment.isChecked()) {
            category.add("entertainment");
        }
        if (politic.isChecked()) {
            category.add("politics");
        }
        if (business.isChecked()) {
            category.add("business");
        }
        if (sports.isChecked()) {
            category.add("sport");
        }
        if (science.isChecked()) {
            category.add("science-and-nature");
        }
        if (tech.isChecked()) {
            category.add("technology");
        }
        if (music.isChecked()) {
            category.add("music");
        }
        if (general.isChecked()) {
            category.add("general");
        }
        if (gaming.isChecked()) {
            category.add("gaming");
        }

        articleButton.setVisibility(View.VISIBLE);
        List<String> allCategories = new ArrayList<>();
        allCategories.add("business");
        allCategories.add("sports");
        allCategories.add("science-and-nature");
        allCategories.add("politics");
        allCategories.add("technology");
        allCategories.add("music");
        allCategories.add("general");
        allCategories.add("gaming");
        for (int i = 0; i < allCategories.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(NewsContract.CategoryColumns.COLUMN_NAME, allCategories.get(i));
            if (category.contains(allCategories.get(i))) {
                values.put(NewsContract.CategoryColumns.COLUMN_STATUS, NewsContract.CategoryColumns.CATEGORY_SELECTED);
            } else {
                values.put(NewsContract.CategoryColumns.COLUMN_STATUS, NewsContract.CategoryColumns.CATEGORY_NOT_SELECTED);
            }
            getContentResolver().insert(NewsProvider.Categories.CONTENT_URI, values);
        }
        selectSources();
    }
    public void  selectSources() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);
    }


    @Override
    public Loader<List<Source>> onCreateLoader(int i, Bundle bundle) {
        return new SourcesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Source>> loader, List<Source> sources) {
        selectedSources = new ArrayList<>();
        GridLayoutManager gl = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gl);
        recyclerView.setVisibility(View.VISIBLE);
        for (int i = 0; i < sources.size(); i++) {
            Source source = sources.get(i);
            if (category.contains(source.getCategory())) {
                selectedSources.add(source);
            }
        }
        storedSources = new ArrayList<>();
        MySourceItemRecyclerViewAdapter rv = new MySourceItemRecyclerViewAdapter(selectedSources, new MySourceItemRecyclerViewAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Source item) {
                storedSources.add(item);
            }

            @Override
            public void onItemUncheck(Source item) {
                storedSources.remove(item);
            }
        });
        recyclerView.setAdapter(rv);
        gridLayout.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<List<Source>> loader) {
    }

    public void findArticles(View v) {

        for (int i = 0; i < storedSources.size(); i++) {
            ContentValues contentValues = new ContentValues();

            Source source = storedSources.get(i);
            contentValues.put(NewsContract.SourceColumns.COLUMN_SOURCE_NAME, source.getName());
            contentValues.put(NewsContract.SourceColumns.COLUMN_SOURCE_ID, source.getId());
            contentValues.put(NewsContract.SourceColumns.COLUMN_CATEGORY_NAME, source.getCategory());
            getContentResolver().insert(NewsProvider.Sources.CONTENT_URI, contentValues);
        }

        recyclerView.setVisibility(View.GONE);
        updateUI();
        articleButton.setVisibility(View.GONE);
    }




}