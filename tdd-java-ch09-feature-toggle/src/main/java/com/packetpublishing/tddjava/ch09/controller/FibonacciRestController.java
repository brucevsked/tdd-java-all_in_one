package com.packetpublishing.tddjava.ch09.controller;

import com.packetpublishing.tddjava.ch09.config.FibonacciFeatureConfig;
import com.packetpublishing.tddjava.ch09.service.FibonacciNumber;
import com.packetpublishing.tddjava.ch09.service.FibonacciService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lj1218.
 * Date: 2019/11/25
 */
@RestController
public class FibonacciRestController {
    private FibonacciFeatureConfig fibonacciFeatureConfig;

    @Qualifier("fibonacci")
    private FibonacciService fibonacciProvider;

    public FibonacciRestController(FibonacciFeatureConfig fibonacciFeatureConfig, FibonacciService fibonacciProvider) {
        this.fibonacciFeatureConfig = fibonacciFeatureConfig;
        this.fibonacciProvider = fibonacciProvider;
    }

    @RequestMapping(value = "/fibonacci", method = RequestMethod.GET)
    public FibonacciNumber fibonacci(@RequestParam(value = "number", defaultValue = "0") int number) {
        if (fibonacciFeatureConfig.isRestEnabled()) {
            int fibonacciValue = fibonacciProvider.getNthNumber(number);
            return new FibonacciNumber(number, fibonacciValue);
        }
        throw new UnsupportedOperationException();
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public void unsupportedException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(),
                "This feature is currently unavailable");
    }

    @ExceptionHandler(Exception.class)
    public void handleGenericException(HttpServletResponse response, Exception e) throws IOException {
        String msg = "There was an error processing your request: " + e.getMessage();
        response.sendError(HttpStatus.BAD_REQUEST.value(), msg);
    }
}
