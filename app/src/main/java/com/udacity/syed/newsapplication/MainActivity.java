package com.udacity.syed.newsapplication;

import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentSender;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.udacity.syed.newsapplication.data.NewsContract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
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
    DrawerLayout drawer;
    NavigationView navigationView;
    TabLayout tabLayout;
    GridLayout gridLayout;
    RecyclerView recyclerView;
    ArrayList<String> category;
    List<Source> selectedSources;
    List<Source> storedSources;
    Button articleButton;
    ScrollView categorySelectorView;
    FrameLayout sourceFragmentLayout;
    TextView name;
    TextView email;
    String emailId;
    String nameNav;
    boolean notOnResume;
    private Button mSignInButton;
    private Button mSignOutButton;
    private Button mRevokeButton;
    private TextView mStatus;
    private GoogleApiClient mGoogleApiClient;
    private int mSignInProgress;
    private PendingIntent mSignInIntent;
    private int mSignInError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notOnResume = true;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignOutButton = (Button) findViewById(R.id.sign_out_button);
        mRevokeButton = (Button) findViewById(R.id.revoke_access_button);
        mStatus = (TextView) findViewById(R.id.sign_in_status);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        signInLayout = (LinearLayout) findViewById(R.id.sign_layout);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        invalidateOptionsMenu();
        //  toolbar.setVisibility(View.GONE);
        mSignInButton.setVisibility(View.VISIBLE);

        //drawer.setVisibility(View.GONE);
        navigationView.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        mSignInButton.setOnClickListener(this);
    }



/*    private GoogleApiClient buildApiClient() {
        return new GoogleApiClient.Builder(this)
                .enableAutoManage(this *//* FragmentActivity *//*, this *//* OnConnectionFailedListener *//*)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }*/


    @Override
    protected void onStop() {
        super.onStop();


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
    protected void onResume() {
        super.onResume();

    }


    public void updateUI() {

        menuStatus = true;
        invalidateOptionsMenu();
        signInLayout.setVisibility(View.GONE);
        mStatus.setVisibility(View.GONE);
        mSignInButton.setVisibility(View.GONE);
        mSignOutButton.setVisibility(View.GONE);
        mRevokeButton.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        drawer.setVisibility(View.VISIBLE);
        navigationView.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);

        setSupportActionBar(toolbar);


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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.signOut) {
            notOnResume = true;
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            updateSignoutUI();

                            // [END_EXCLUDE]
                        }
                    });
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
        viewPager.setVisibility(View.GONE);

        navigationView.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        mSignInButton.setEnabled(true);
        mSignOutButton.setEnabled(false);
        mRevokeButton.setEnabled(false);

        mStatus.setText(getString(R.string.sign_out));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        name.setText(nameNav);
        email.setText(emailId);

        if (id == R.id.profile) {

        } else if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.fav_articles) {
            Intent intent = new Intent(this, SavedArticlesActivity.class);
            startActivity(intent);
        } else if (id == R.id.signout_now) {

            notOnResume = true;
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            updateSignoutUI();

                            // [END_EXCLUDE]
                        }
                    });


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
                    mStatus.setText(getString(R.string.singin));

                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                    break;
                case R.id.sign_out_button:
                    // We clear the default account on sign out so that Google Play
                    // services will not return an onConnected callback without user
                    // interaction.
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    // [START_EXCLUDE]

                                    // [END_EXCLUDE]
                                }
                            });

                    break;
                case R.id.revoke_access_button:
                    // After we revoke permissions for the user with a GoogleApiClient
                    // instance, we must discard it and create a new one.
                    // Our sample has caches no user data from Google+, however we
                    // would normally register a callback on revokeAccessAndDisconnect
                    // to delete user data so that we comply with Google developer
                    // policies.

                    break;
            }
        }
    }


    public void handleSignInResult(GoogleSignInResult result) {
        Log.i(TAG, "onConnected");
        signInLayout.setVisibility(View.GONE);
        if (notOnResume) {
            // Update the user interface to reflect that the user is signed in.
            mSignInButton.setEnabled(false);
            mSignOutButton.setEnabled(false);
            mRevokeButton.setEnabled(false);
            notOnResume = false;
            // Indicate that the sign in process is complete.
            mSignInProgress = SIGNED_IN;

            // We are signed in!
            // Retrieve some profile information to personalize our app for the user.
            try {
                ////Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                ////mStatus.setText(String.format("Signed In to G+ as %s", currentUser.getDisplayName()));
                GoogleSignInAccount acct = result.getSignInAccount();
                if (acct != null) {
                    emailId = acct.getEmail();
                    nameNav = acct.getDisplayName();
                }
                String selection = NewsContract.CategoryColumns.COLUMN_STATUS + "=" + NewsContract.CategoryColumns.CATEGORY_SELECTED;
                Uri uri = NewsContract.CategoryColumns.CONTENT_URI;
                Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
                int index = cursor.getColumnIndex(NewsContract.CategoryColumns.COLUMN_NAME);
                category = new ArrayList<>();
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        category.add(cursor.getString(index));
                        cursor.moveToNext();
                    }
                    cursor.close();
                    menuStatus = true;
                    invalidateOptionsMenu();
                    signInLayout.setVisibility(View.GONE);
                    mStatus.setVisibility(View.GONE);
                    mSignInButton.setVisibility(View.GONE);
                    mSignOutButton.setVisibility(View.GONE);
                    mRevokeButton.setVisibility(View.GONE);
                    toolbar.setVisibility(View.VISIBLE);
                    drawer.setVisibility(View.VISIBLE);
                    navigationView.setVisibility(View.VISIBLE);
                    setSupportActionBar(toolbar);
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


    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
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
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

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

        mStatus.setText(getString(R.string.sign_out));

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
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleSignInResult(result);

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


        Cursor cursor = getContentResolver().query(NewsContract.CategoryColumns.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) {
            List<String> allCategories = new ArrayList<>();
            allCategories.add("business");
            allCategories.add("sport");
            allCategories.add("science-and-nature");
            allCategories.add("politics");
            allCategories.add("technology");
            allCategories.add("music");
            allCategories.add("general");
            allCategories.add("gaming");
            allCategories.add("entertainment");
            for (int i = 0; i < allCategories.size(); i++) {
                ContentValues values = new ContentValues();
                String str = allCategories.get(i);
                values.put(NewsContract.CategoryColumns.COLUMN_NAME, str);
                values.put(NewsContract.CategoryColumns.COLUMN_STATUS, NewsContract.CategoryColumns.CATEGORY_NOT_SELECTED);
                getContentResolver().insert(NewsContract.CategoryColumns.CONTENT_URI, values);
            }
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);

        }
    }


    @Override
    public Loader<List<Source>> onCreateLoader(int i, Bundle bundle) {
        return new SourcesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Source>> loader, List<Source> sources) {
        for (int i = 0; i < sources.size(); i++) {
            ContentValues contentValues = new ContentValues();

            Source source = sources.get(i);
            contentValues.put(NewsContract.SourceColumns.COLUMN_SOURCE_NAME, source.getName());
            contentValues.put(NewsContract.SourceColumns.COLUMN_SOURCE_ID, source.getId());
            contentValues.put(NewsContract.SourceColumns.COLUMN_CATEGORY_NAME, source.getCategory());
            contentValues.put(NewsContract.SourceColumns.COLUMN_COLUMN_STATUS, NewsContract.SourceColumns.SOURCE_NOT_SELECTED);
            getContentResolver().insert(NewsContract.SourceColumns.CONTENT_URI, contentValues);
        }
        startActivity(new Intent(this, CategoryActivity.class));
     /*   selectedSources = new ArrayList<>();
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
*/
    }

    @Override
    public void onLoaderReset(Loader<List<Source>> loader) {
    }


}