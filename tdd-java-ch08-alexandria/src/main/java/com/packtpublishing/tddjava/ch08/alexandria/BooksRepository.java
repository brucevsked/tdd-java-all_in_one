package com.packtpublishing.tddjava.ch08.alexandria;

public class BooksRepository {

    private final Books books;

    public BooksRepository() {
        books = new Books();
    }

    public Books list() {
        return books;
    }

    public void add(Book book) {
        books.add(book);
    }
}
