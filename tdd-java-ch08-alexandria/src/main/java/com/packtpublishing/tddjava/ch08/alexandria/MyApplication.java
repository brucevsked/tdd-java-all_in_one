package com.packtpublishing.tddjava.ch08.alexandria;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class MyApplication extends ResourceConfig {

    public MyApplication() {
        this(new BooksEndpoint(new BooksRepository()));
    }

    public MyApplication(BooksEndpoint booksEndpoint) {
        register(RequestContextFilter.class);
        register(booksEndpoint);
        register(JacksonJaxbJsonProvider.class);
        register(CustomExceptionMapper.class);
    }
}
