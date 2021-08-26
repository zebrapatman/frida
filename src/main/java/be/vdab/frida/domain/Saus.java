package be.vdab.frida.domain;

import java.util.Arrays;

public class Saus {
    private final long nummer;
    private final String naam;
    private final String [] ingredienten;

    public Saus(long nummer, String naam, String[] ingredienten) {
        this.nummer = nummer;
        this.naam = naam;
        this.ingredienten = ingredienten;
    }

    public long getNummer() {
        return nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String[] getIngredienten() {
        return ingredienten;
    }


    public String getNaamIngredient() {
        var strings = new StringBuilder();
        for (var ingredient :ingredienten){
            strings.append(ingredient.toString());
        }
        return strings.toString();
    }
}
