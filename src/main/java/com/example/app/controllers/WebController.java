package com.example.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling web page requests.
 */
@Controller
public class WebController {

    /**
     * Serves the landing page.
     * @return The name of the view template to render.
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}