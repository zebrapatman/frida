package be.vdab.frida.services;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.repositories.SausRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSausServiceTest {
@Mock
    private SausRepository sausRepository;
    private DefaultSausService sausService;
@BeforeEach
    void beforeEach(){
    sausService = new DefaultSausService(sausRepository);
    sausService.findByNaamBegintMet(Character.valueOf('m'));

}
@Test
    void findAll(){
        when(sausRepository.findAll()).thenReturn(List.of(new Saus(1,"mosterd",new String[]{"mayonnaise","ei"}),new Saus(2,"ketchup",new String[]{"mayonnaise","mosterd"})));

        assertThat(sausService.findByNaamBegintMet(Character.valueOf('m'))).hasSize(1);
}

}