package design.shortnd.searchbooks;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link BookAdapter}
     */
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books
     */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false
            );
        }

        // Find the Book at the given position in the list of Books
        Book currentBook = getItem(position);

        // Gets the information about the current book
        String bookTitle = currentBook.getBookTitle();
        String bookAuthor = currentBook.getBookAuthor();
        String bookDescription = currentBook.getBookDescription();

        // Find the TextView with the ID book_title
        TextView titleView = listItemView.findViewById(R.id.book_title);
        titleView.setText(bookTitle);

        // Find the TExtView with the ID book_author
        TextView authorView = listItemView.findViewById(R.id.book_author);
        authorView.setText(bookAuthor);

        // Find the TextView with the ID book_description
        TextView descriptionView = listItemView.findViewById(R.id.book_description);
        descriptionView.setText(bookDescription);

        return listItemView;

    }
}
