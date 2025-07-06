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
    
    /**
     * Serves the joke page.
     * @return The name of the view template to render.
     */
    @GetMapping("/joke")
    public String joke() {
        return "joke";
    }
    
    /**
     * Serves the email signature page.
     * @return The name of the view template to render.
     */
    @GetMapping("/signature")
    public String signature() {
        return "signature";
    }
    
    /**
     * Serves the restaurant finder page.
     * @return The name of the view template to render.
     */
    @GetMapping("/restaurant")
    public String restaurant() {
        return "restaurant";
    }
    
    /**
     * Serves the performance test page.
     * @return The name of the view template to render.
     */
    @GetMapping("/performance")
    public String performance() {
        return "performance";
    }
    
    /**
     * Serves the JavaScript test page.
     * @return The name of the view template to render.
     */
    @GetMapping("/javascript")
    public String javascript() {
        return "javascript";
    }
    
    /**
     * Serves the XML test page.
     * @return The name of the view template to render.
     */
    @GetMapping("/xml")
    public String xml() {
        return "xml";
    }
}