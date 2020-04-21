package com.packtpublishing.tddjava.ch08.alexandria;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;


/**
 * Created by lj1218.
 * Date: 2019/11/22
 */
public class BooksEndpointTest {

    public static final URI FULL_PATH = URI.create("http://localhost:8080/alexandria");
    public static final String AUTHOR_BOOK_1 = "Viktor Farcic and Alex Garcia";
    public static final String TITLE_BOOK_1 = "TDD in Java";
    private final Map<String, String> TDD_IN_JAVA;
    private HttpServer server;

    public BooksEndpointTest() {
        TDD_IN_JAVA = getBookProperties(TITLE_BOOK_1, AUTHOR_BOOK_1);
        RestAssured.baseURI = FULL_PATH.toString();
    }

    private Map<String, String> getBookProperties(String title, String author) {
        Map<String, String> bookProperties = new HashMap<>();
        bookProperties.put("title", title);
        bookProperties.put("author", author);
        return bookProperties;
    }

    @Before
    public void setUp() throws IOException {
        ResourceConfig resourceConfig = new MyApplication();
        server = GrizzlyHttpServerFactory.createHttpServer(FULL_PATH, resourceConfig);
        server.start();
    }

    @After
    public void tearDown() {
        server.shutdown();
    }

    private void pauseTheServer() throws IOException {
        System.in.read();
    }

    @Test
    public void add_one_book() {
        final Response books1 = addBook(TDD_IN_JAVA);
        assertBooksSize(books1, is("1"));
    }

    @Test
    public void add_a_second_book() {
        addBook(TDD_IN_JAVA);

        final Response books2 = addBook(TDD_IN_JAVA);
        assertBooksSize(books2, is("2"));
    }

    @Test
    public void get_book_details_by_id() {
        addBook(TDD_IN_JAVA);

        final Response book2 = getBook(withId(0));
        checkBookSetDetails(book2, 0, hasStatus(States.BOUGHT));
    }

    @Test
    public void get_several_books_in_a_row() {
        addBook(TDD_IN_JAVA);
        addBook(TDD_IN_JAVA);
        addBook(TDD_IN_JAVA);
        addBook(TDD_IN_JAVA);

        checkThatBook(withId(0), hasStatus(States.BOUGHT));
        checkThatBook(withId(1), hasStatus(States.BOUGHT));
        checkThatBook(withId(2), hasStatus(States.BOUGHT));
        checkThatBook(withId(3), hasStatus(States.BOUGHT));
    }

    @Test
    public void censor_a_book() {
        addBook(TDD_IN_JAVA);

        final Response response = censorBook(withId(0));
        checkBookDetails(response, 0, hasStatus(States.CENSORED));
    }

    private Map<String, Object> withId(int id) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", id);
        return properties;
    }

    private void checkBookDetails(Response response, int id, Matcher<Integer> statusMatcher) {
        response.prettyPrint();
        response.then()
                .body("author", is(AUTHOR_BOOK_1))
                .body("title", is(TITLE_BOOK_1))
                .body("id", is(id))
                .body("status", statusMatcher);
    }

    private Response censorBook(Map<String, Object> properties) {
        return RestAssured.given().log().path().contentType(ContentType.URLENC)
                .post("books/{id}/censor", properties);
    }

    private Matcher<Integer> hasStatus(States status) {
        return is(status.getValue());
    }

    private void checkThatBook(Map<String, Object> properties, Matcher<Integer> status) {
        final Response book = getBook(properties);
        checkBookSetDetails(book, (Integer) properties.get("id"), status);
    }

    private void checkBookSetDetails(Response book, int id, Matcher<Integer> status) {
        book.prettyPrint();
        book.then()
                .body("empty", is(false))
                .body("result[0].author", is(AUTHOR_BOOK_1))
                .body("result[0].title", is(TITLE_BOOK_1))
                .body("result[0].id", is(id))
                .body("result[0].status", is(status));
    }

    private Response getBook(Map<String, Object> properties) {
        return RestAssured.given().log().path()
                .contentType(ContentType.URLENC)
                .accept(ContentType.JSON)
                .parameters(properties)
                .get("books");
    }

    private void assertBooksSize(Response response, Matcher<String> matcher) {
        response.then().body(matcher);
    }

    private Response addBook(Map<String, ?> bookProperties) {
        return RestAssured.given().log().path()
                .contentType(ContentType.URLENC)
                .parameters(bookProperties)
                .post("books");
    }

    //    @Test
    public void test() throws IOException {
        pauseTheServer();
    }
}
