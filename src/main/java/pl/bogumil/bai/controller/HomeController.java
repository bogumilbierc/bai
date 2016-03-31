package pl.bogumil.bai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by bbierc on 2016-03-31.
 */
@Controller
public class HomeController {
    private static final String INDEX_FTL = "index";

    @RequestMapping("/")
    public String indexPage() {
        return INDEX_FTL;
    }

}
