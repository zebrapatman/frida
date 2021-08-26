package be.vdab.frida.controllers;

import be.vdab.frida.forms.ZoekSnackForm;
import be.vdab.frida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
    @GetMapping("zoeksnacks/form")
    public ModelAndView zoekSnacksForm(){
        var modelAndView = new ModelAndView("zoeksnacks");
        return modelAndView.addObject(new ZoekSnackForm(""));
    }
    @GetMapping("zoeksnacks")
    public ModelAndView zoekSnacks(@Valid ZoekSnackForm form, Errors errors){
        var modelAndView = new ModelAndView("zoeksnacks");
        if(errors.hasErrors()){
            return modelAndView;
        }
        return modelAndView.addObject("snacks",snackService.findByBeginNaam(form.getBeginLetters()));
    }
}
