package be.vdab.frida.repositories;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.exceptions.SnackNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat; import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import(JdbcSnackRepository.class)
@Sql("/insertSnacks.sql")
class JdbcSnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String SNACKS = "snacks";
    private final JdbcSnackRepository repository;
    private final RowMapper<Snack> snackMapper = (result,rowNum)-> new Snack(result.getLong("id"), result.getString("naam"),result.getBigDecimal("prijs") );

    JdbcSnackRepositoryTest(JdbcSnackRepository repository) {
        this.repository = repository;
    }

    @Test
    void findByIDgeeftCorrecteWaarde(){
        var id = jdbcTemplate.queryForObject("select id from snacks where naam='test'",Long.class);
        var snack = jdbcTemplate.queryForObject("select id,naam,prijs from snacks where naam='test'",snackMapper);
        assertThat(repository.findById(id)).hasValueSatisfying(hapje-> assertThat(hapje.getNaam()).isEqualTo("test"));
    }
    @Test
    void findByID(){
        var id = jdbcTemplate.queryForObject("select id from snacks where naam='test'",Long.class);
        assertThat(repository.findById(id)).isNotEmpty();
    }
    @Test
    void findByIDgeeftFouteWaarde(){
        assertThat(repository.findById(100)).isEmpty();
    }
    @Test
    void findByIDgeeftNietsBijNegatieveWaarde(){
        assertThat(repository.findById(-1L)).isEmpty();
    }
    @Test
    void findByIDgeeft1WaardeTerug(){
        assertThat(repository.findById(1)).isPresent();
    }

    @Test
    void update(){
        var id = jdbcTemplate.queryForObject("select id from snacks where naam='test'",Long.class);
        var snack = new Snack(id,"example", BigDecimal.TEN);
        repository.update(snack);
        assertThat(countRowsInTableWhere(SNACKS,"naam='example'")).isOne();
    }
    @Test
    void updateOnbestaandePizzaGeeftEenFout(){
        assertThatExceptionOfType(SnackNietGevondenException.class).isThrownBy(()->repository.update(new Snack(-1, "test", BigDecimal.TEN)));
    }
    @Test
    void findByBeginNaam(){
        assertThat(repository.findByBeginNaam("tes")).hasSize(2);
    }
    @Test
    void naUpdateKomtSnackNietMeerInFindByNaam(){
        var id = jdbcTemplate.queryForObject("select id from snacks where naam='test2'",Long.class);
        var snack = new Snack(id,"example", BigDecimal.TEN);
        repository.update(snack);
        assertThat(repository.findByBeginNaam("tes")).hasSize(1);
    }
    @Test
    void findByBeginNaamGeeftEvenVeelResultatenAlsInTabel(){
        assertThat(repository.findByBeginNaam("")).hasSize(countRowsInTable(SNACKS));
    }
    @Test
    void findByBeginGeeftNietsTerug(){
        assertThat(repository.findByBeginNaam("123")).isEmpty();
    }
    @Test
    void findByBeginNaamGeeftGesorteerdTerug(){
        assertThat(repository.findByBeginNaam("t")).hasSize(countRowsInTableWhere(SNACKS,"naam like 't%'")).extracting(Snack::getNaam).allSatisfy(naam->assertThat(naam.toLowerCase()).startsWith("t")).isSortedAccordingTo(String::compareToIgnoreCase);
    }
    @Test
    void verkoopPerDag(){
        assertThat(repository.findVerkoopPerDag()).hasSize(countRowsInTable(SNACKS));
    }
}