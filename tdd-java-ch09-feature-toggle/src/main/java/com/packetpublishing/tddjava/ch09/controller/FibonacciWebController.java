package com.packetpublishing.tddjava.ch09.controller;

import com.packetpublishing.tddjava.ch09.config.FibonacciFeatureConfig;
import com.packetpublishing.tddjava.ch09.service.FibonacciService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by lj1218.
 * Date: 2019/11/25
 */
@Controller
public class FibonacciWebController {
    private FibonacciFeatureConfig fibonacciFeatureConfig;

    @Qualifier("fibonacci")
    private FibonacciService fibonacciService;

    public FibonacciWebController(FibonacciFeatureConfig fibonacciFeatureConfig,
                                  FibonacciService fibonacciService) {
        this.fibonacciFeatureConfig = fibonacciFeatureConfig;
        this.fibonacciService = fibonacciService;
    }

    @RequestMapping(value = "/", method = GET)
    public String home(Model model) {
        model.addAttribute("isWebEnabled",
                fibonacciFeatureConfig.isWebEnabled());
        if (fibonacciFeatureConfig.isRestEnabled()) {
            model.addAttribute("arrayOfInts",
                    Arrays.asList(5, 7, 8, 16));
        }
        return "home";
    }

    @RequestMapping(value = "/web/fibonacci", method = GET)
    public String fibonacci(
            @RequestParam(value = "number") Integer number,
            Model model) {
        if (number != null) {
            model.addAttribute("number", number);
            model.addAttribute("value", fibonacciService.getNthNumber(number));
        }
        return "fibonacci";
    }
}
