package be.vdab.frida.controllers;

import be.vdab.frida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("snacks")
class SnackController {
    private final char [] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SnackService snackService;

    SnackController(SnackService snackService) {
        this.snackService = snackService;
    }
    @GetMapping("alfabet")
    public ModelAndView toonAlfabet(){
        var modelAndView = new ModelAndView("snacks");
        return modelAndView.addObject("letters",alfabet);
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView toonSnackPerLetter(@PathVariable char letter){
        var modelAndView = new ModelAndView("snacks");
        modelAndView.addObject("letters",alfabet);
        modelAndView.addObject("snacks",snackService.findByBeginNaam(String.valueOf(letter)));
        return modelAndView;
    }
    @GetMapping("dagverkoop")
    public ModelAndView toonVerkoopPerDag(){
        var modelAndView = new ModelAndView("dagverkoop");
        modelAndView.addObject("dagverkopen",snackService.findVerkoopPerDag());
        return modelAndView;
    }
}
