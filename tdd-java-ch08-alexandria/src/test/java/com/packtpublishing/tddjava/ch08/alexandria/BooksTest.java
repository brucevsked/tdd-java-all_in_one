package com.packtpublishing.tddjava.ch08.alexandria;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by lj1218.
 * Date: 2019/11/22
 */
public class BooksTest {

    @Test
    public void should_search_for_any_past_state() {
        Book book = new Book("title", "author", States.AVAILABLE);
        book.censor();

        Books books = new Books();
        books.add(book);

        String available = String.valueOf(States.AVAILABLE.getValue());
        assertThat(books.filterByState(available).isEmpty(), is(false));
    }
}
