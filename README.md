# NewsApp

App makes it easier to view news from different news sources provided by newsApi. The following is a summary of how the App was
Implemented. 
  1. The App uses RestAPI to request the latest news post, the result JSON data is parsed.
  2. I have used material design principles to design the frontend of the app, using elevations and transition between activities.
  3. The app uses the standard TabLayout and Navigation Layout.
  4. User has to sign into the App using Google sign in.
  5. I have used ContentResolver to store the settings , the category(Eg.Business,Politics,Tech)info and Different sources for each 
  of these categories. 
  6. User can also store the favorite articles to refer later.
  7. The app uses Loader Manager to make the network request 
  8. Information is displayed using RecyclerView.
  9. User can visit and share the link of the article using the Explicit intents.
  10. SwipeRefreshLayout is implemented to refresh the articles by creating the intent service that fetches the data and updates the recycleview
  11. Google ads are displayed in the detail article layout
  
