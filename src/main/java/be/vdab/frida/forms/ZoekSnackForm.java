package be.vdab.frida.forms;

import javax.validation.constraints.NotBlank;

public class ZoekSnackForm {
    @NotBlank
    private final String beginLetters;

    public ZoekSnackForm(String beginLetters) {
        this.beginLetters = beginLetters;
    }

    public String getBeginLetters() {
        return beginLetters;
    }
}
