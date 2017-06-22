package design.shortnd.searchbooks;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    /**
     * Tag for the log messaages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Google Book API and return a list of {@link Book} objects
     */
    public static List<Book> fetchBookData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problems making HTTP request", e);
        }

        // Extracts relevant fields from the JSON response and create a list of {@link Books}s
        List<Book> books = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Book}s
        return books;
    }

    /**
     * @return New URL object from the @param stringUrl
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }

    /**
     * Make a HTTP request to the given URL and return a String as the response
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* Milliseconds */);
            urlConnection.setConnectTimeout(15000 /* Milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Google Books JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature than an IOException
                // could be thrown
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing the given JSON response
     */
    private static List<Book> extractFeatureFromJson(String bookJSON) {
        // If the JSON String is empty or null, then return early
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding books too
        List<Book> books = new ArrayList<>();

        // Try to parse the JSON response String. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs
        try {
            // Create a JSONObject from the JSON response String
            JSONObject baseJsonResponce = new JSONObject(bookJSON);

            // Extract the JSONArray associated with the key called "items"
            // which represents a list of items (or books)
            JSONArray bookArray = baseJsonResponce.getJSONArray("items");

            // For each book in the bookArray, create an {@link Book} object
            for (int i = 0; i < bookArray.length(); i++) {

                // Get a single book at position i within the list of books
                JSONObject currentBook = bookArray.getJSONObject(i);

                // For a given book, extract the JSONObject associated with the
                // key called "volumeInfo", which represents a list of all properties
                // for that book
                JSONObject properties = currentBook.getJSONObject("volumeInfo");

                // Extract the value for the key called "title"
                String title = properties.getString("titles");

                // Extract the value for the key called "authors"
                JSONArray authorList = properties.getJSONArray("authors");

                // Converts the JSON Array to a String Array and
                // then stores it in a new variable
                String[] resultingArray = authorList.join(",").split(",");

                // Saves the first Author name in the Array to the variable
                // author
                String author = resultingArray[0];

                // Extracts the value for the key called "description"
                String description = properties.getString("description");

                // Extract the value for the key called "infoLink"
                String url = properties.getString("infoLink");

                // Create a new {@link Book} object with the title, author, description,
                // and url from the JSON response.
                Book book = new Book(title, author, description, url);

                // Add the new {@link Book} to the list of books.
                books.add(book);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the Google Book API JSON results", e);
        }
        return books;
    }

}
