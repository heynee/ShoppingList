# ShoppingList
This is a simple shopping list app to help out anyone who doesn't want to waste their time in a grocery store.

# How to compile and run the app
The app is intended to work with Firebase's Realtime Database. See https://firebase.google.com/products/realtime-database for more information how to set it up. It literally takes less than 5 minutes and if I could do it, you surely can as well. Once you have your database up and running, just copy its URL and replace ADD_YOUR_FIREBASE_URL_HERE in app's build.gradle file with it.

# How it works
The app has one list to which you can add new shopping list items. You can also check, un-check or delete (long click) existing items. The maximum length of one item is 50 characters. Keep in mind that downloading the shopping list takes place only when app is launched. So if two devices are using the same Firebase endpoint, and if one of them updates the list, the other device's list will not be synced until app is restarted.

# Test coverage
The project has unit tests that cover the basic features - getting an existing shopping list, adding new items, updating and deleting existing ones. The easiest way to run these unit tests is straight from Android Studio.

# Architecture
The project follows or at least tries to follow Android's recommended app architecture (see https://developer.android.com/jetpack/guide). The overall goal is separation of concerns, meaning that app's components are kept apart from one another as much as possible.
