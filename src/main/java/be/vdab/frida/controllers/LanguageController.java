package be.vdab.frida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("taal")
class LanguageController {
    @GetMapping
    public ModelAndView toonTaal(@RequestHeader("Accept-Language") String language){
        var modelAndView = new ModelAndView("taal");
        var taal = language.substring(0,4).contains("nl") ? "U spreekt Nederlands" : "No hablo";
        return modelAndView.addObject("taal",taal);
    }
}
