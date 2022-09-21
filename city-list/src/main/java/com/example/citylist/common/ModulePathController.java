package com.example.citylist.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/" )
public class ModulePathController {

    @GetMapping( "/" )
    public String pathRedirect()
    {
        return "redirect:/city_list_module";
    }
}
