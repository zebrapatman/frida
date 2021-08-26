package be.vdab.frida.services;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.dto.Dagverkoop;

import java.util.List;
import java.util.Optional;

public interface SnackService {
    Optional<Snack> read(long id);
    void update(Snack snack);
    List<Snack> findByBeginNaam(String beginNaam);
    List<Dagverkoop> findVerkoopPerDag();
}
