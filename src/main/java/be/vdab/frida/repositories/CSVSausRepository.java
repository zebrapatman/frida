package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.exceptions.CSVexception;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Qualifier("CSV")
@Component
class CSVSausRepository implements SausRepository {
    private final Path pad;


    public CSVSausRepository(@Value("${CSVSausenPad}")Path pad) {
        this.pad=pad;
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
        var onderdelen = regel.split(",");
        if (onderdelen.length < 2) {
            throw new CSVexception(pad + ":" + regel + ": minder dan 2 onderdelen");
        }
        try {
            var ingredienten = Arrays.copyOfRange(onderdelen, 2, onderdelen.length);
            return new Saus(Long.parseLong(onderdelen[0]), onderdelen[1], ingredienten);
        } catch (NumberFormatException ex) {
            throw new CSVexception(pad + ":" + regel + ": verkeerde id");
        }
    }
}