package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.CSVexception;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Qualifier("Properties")
@Component
class PropertiesSausRepository implements SausRepository{
    private final Path pad;

    PropertiesSausRepository(@Value("${propertiesSausenPad}")Path pad) {
        this.pad = pad;
    }

    @Override
    public List<Saus> findAll() {
        try {
            return Files.lines(pad).map(this::maakSaus).collect(Collectors.toList());
        } catch (IOException ex) {
            throw new CSVexception("Fout bij lezen " + pad);
        }
    }

    private Saus maakSaus(String regel) {
        var deelID = regel.split(":");
        if (deelID.length < 2) {
            throw new CSVexception(pad + ":" + regel + ": minder dan 2 onderdelen");
        }
        var id = deelID[0];
        var onderdelen = deelID[1].split(",",2);
        try {
            var ingredienten = Arrays.copyOfRange(onderdelen, 1, onderdelen.length);
            return new Saus(Long.parseLong(id), onderdelen[0], ingredienten);
        } catch (NumberFormatException ex) {
            throw new CSVexception(pad + ":" + regel + ": verkeerde id");
        }
    }
}
