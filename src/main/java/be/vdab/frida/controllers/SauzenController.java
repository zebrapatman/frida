package be.vdab.frida.controllers;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("sauzen")
class SauzenController {
    private final SausService sausService;
    private final char [] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final Saus[] sauzen = {
            new Saus(1,"cocktail", new String[]{"mayonaise", "ketchup","cognac"}),
            new Saus(2,"mayonaise", new String[]{"ei", "mosterd"}),
            new Saus(3,"mosterd", new String[]{"mayonaise","azijn","witte wijn"}),
            new Saus(4,"tartare", new String[]{"mayonaise","augurk"}),
            new Saus(5,"vinaigrette", new String[]{"olijfolie","mosterd","azijn"})
    };

    SauzenController(SausService sausService) {
        this.sausService = sausService;
    }

    @GetMapping
    public ModelAndView toonSauzen(){
        var modelAndView = new ModelAndView("sauzen");
        modelAndView.addObject("sauzen",sausService.findAll());
        return modelAndView;
    }
    @GetMapping("{id}")
    public ModelAndView toonSaus(@PathVariable long id){
        var modelAndView = new ModelAndView("saus");
        sausService.findById(id).ifPresent(saus-> modelAndView.addObject(saus));
        return modelAndView;
    }
    @GetMapping("alfabet")
    public ModelAndView toonAlfabet(){
        return new ModelAndView("alfabet","letters",alfabet);
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView toonSaus (@PathVariable char letter){
        var modelAndView = new ModelAndView("alfabet","letters",alfabet).addObject("letterSauzen",sausService.findByNaamBegintMet(letter));
        return modelAndView;
    }
}
