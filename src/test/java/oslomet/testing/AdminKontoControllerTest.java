package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AdminKontoControllerTest {

    @InjectMocks
    private AdminKontoController adminKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_LoggetInn() {
        List<Konto> expectedKonti = Arrays.asList(new Konto(), new Konto());
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentAlleKonti()).thenReturn(expectedKonti);

        List<Konto> actualKonti = adminKontoController.hentAlleKonti();

        assertEquals(expectedKonti, actualKonti);
    }
    @Test
    public void hentAlleKonti_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        List<Konto> actualKonti = adminKontoController.hentAlleKonti();

        assertNull(actualKonti);
    }
    @Test
    public void registrerKonto_LoggetInn() {
        Konto konto = new Konto();
        String expectedResponse = "Konto registrert";

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerKonto(any(Konto.class))).thenReturn(expectedResponse);

        String actualResponse = adminKontoController.registrerKonto(konto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void registrerKonto_IkkeLoggetInn() {
        Konto konto = new Konto();
        String expectedResponse = "Ikke innlogget";
        when(sjekk.loggetInn()).thenReturn(null);

        String actualResponse = adminKontoController.registrerKonto(konto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void endre_LoggetInn() {
        Konto konto = new Konto();
        String expectedResponse = "Endring var suksessfull";

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKonto(any(Konto.class))).thenReturn(expectedResponse);

        String actualResponse = adminKontoController.endreKonto(konto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void endre_IkkeLoggetInn() {
        Konto konto = new Konto();

        when(sjekk.loggetInn()).thenReturn(null);

        String actualResponse = adminKontoController.endreKonto(konto);

        assertEquals("Ikke innlogget", actualResponse);
    }

    @Test
    public void slett_LoggetInn() {
        String kontonummer = "12345678901";
        String expectedResponse = "Konto slettet";

        when(sjekk.loggetInn()).thenReturn(kontonummer);
        when(repository.slettKonto(anyString())).thenReturn(expectedResponse);

        String actualResponse = adminKontoController.slettKonto(kontonummer);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void slett_IkkeLoggetInn() {
        String kontonummer = "12345678901";
        String expectedResponse = "Ikke innlogget";
        when(sjekk.loggetInn()).thenReturn(null);

        String actualResponse = adminKontoController.slettKonto(kontonummer);

        assertEquals(expectedResponse, actualResponse);
    }

}
