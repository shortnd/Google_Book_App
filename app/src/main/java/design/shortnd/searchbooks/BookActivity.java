package design.shortnd.searchbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    // String that will be put in to Log if error occurs
    private static final String LOG_TAG = BookActivity.class.getName();

    /**
     * URL for book data from the Google Book API
     */
    private static final String BOOK_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?";

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're
     */
    private static final int BOOK_LOADER_ID = 1;

    /**
     * Adapter for list of books
     */
    private BookAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Find the empty state and sets the empty state if list is empty
        mEmptyStateView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateView);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView)
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Find the current book that was clicked on
                Book currentBook = mAdapter.getItem(position);
            }
        });


    }
}
