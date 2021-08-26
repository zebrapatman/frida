package be.vdab.frida.repositories;

import be.vdab.frida.domain.Saus;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SausRepository {
    List<Saus> findAll();
}
