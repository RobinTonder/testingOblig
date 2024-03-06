package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AdminKundeControllerTest {
    @InjectMocks
    private AdminKundeController adminKundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_loggetInn() {
        List<Kunde> expectedKunder = new ArrayList<>();
        Kunde kunde = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");
        expectedKunder.add(kunde);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentAlleKunder()).thenReturn(expectedKunder);


        List<Kunde> actualKunder = adminKundeController.hentAlle();

        assertEquals(expectedKunder, actualKunder);
    }

    @Test
    public void hentAlle_IkkeloggetInn() {

        List<Kunde> expectedKunder = new ArrayList<>();
        Kunde kunde = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");
        expectedKunder.add(kunde);

        when(sjekk.loggetInn()).thenReturn(null);


        List<Kunde> actualKunder = adminKundeController.hentAlle();


        assertNull(actualKunder);
    }

    @Test
    public void lagreKunde_LoggetInn() {
        String expectedPersonnummer = "12345678901";
        Kunde innKunde = new Kunde();
        String expectedResponse = "Success";

        when(sjekk.loggetInn()).thenReturn(expectedPersonnummer);
        when(repository.registrerKunde(innKunde)).thenReturn(expectedResponse);

        String actualResponse = adminKundeController.lagreKunde(innKunde);

        assertEquals(expectedResponse, actualResponse);
        Mockito.verify(repository).registrerKunde(innKunde);
    }


    @Test
    public void lagreKunde_IkkeLoggetInn() {
        Kunde innKunde = new Kunde();

        when(sjekk.loggetInn()).thenReturn(null);

        String actualResponse = adminKundeController.lagreKunde(innKunde);

        assertEquals("Ikke logget inn", actualResponse);
        Mockito.verify(repository, Mockito.never()).registrerKunde(innKunde);

    }

    @Test
    public void slett_LoggetInn() {
        String personnummer = "01010110523";
        String expectedResponse = "Kunde slettet";

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.slettKunde(personnummer)).thenReturn(expectedResponse);

        String actualResponse = adminKundeController.slett(personnummer);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void slett_IkkeLoggetInn() {
        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(null);

        String actualResponse = adminKundeController.slett(personnummer);

        assertEquals("Ikke logget inn", actualResponse);
    }




}