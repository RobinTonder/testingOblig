package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        Kunde enKunde = new Kunde
                ("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        Kunde resultat = bankController.hentKundeInfo();

        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        Kunde resultat = bankController.hentKundeInfo();

        assertNull(resultat);
    }




    @Test
    public void hentKonti_LoggetInn()  {
        // lager en konto å teste på
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", null);
        konti.add(konto1);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        List<Konto> resultat = bankController.hentKonti();

        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {


        when(sjekk.loggetInn()).thenReturn(null);

        List<Konto> resultat = bankController.hentKonti();

        assertNull(resultat);
    }

    @Test
    public void hentSaldi_loggetInn() {
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", null);
        konti.add(konto1);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        List<Konto> resultat = bankController.hentSaldi();

        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn()  {
        when(sjekk.loggetInn()).thenReturn(null);

        List<Konto> resultat = bankController.hentSaldi();

        assertNull(resultat);
    }

    @Test
    public void endreKundeInfo_loggetInn() {

        Kunde innKunde = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");
        String respons = "riktig";

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn(respons);

        String faktiseRespons = bankController.endre(innKunde);

        assertEquals(respons, faktiseRespons);

    }

    @Test
    public void endreKundeInfo_IkkeLoggetInn()  {

        Kunde innKunde = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = bankController.endre(innKunde);

        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_loggetInn() {

        Konto expectedKonto = new Konto("105010123456", "01010110523", 720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(expectedKonto);

        Konto faktsikeKonto = bankController.hentTransaksjoner("105010123456", "01.01.2022", "31.01.2022");

        assertEquals(expectedKonto, faktsikeKonto);
    }


    @Test
    public void hentTransaksjoner_IkkeLoggetInn()  {

        when(sjekk.loggetInn()).thenReturn(null);

        Konto faktsikeKonto = bankController.hentTransaksjoner("105010123456", "01.01.2022", "31.01.2022");

        assertNull(faktsikeKonto);
    }


    @Test
    public void registrerBetaling_loggetInn() {

        Transaksjon betaling = new Transaksjon(1, "105010123456", 500.0, "01.01.2022", "Betaling", "NOK", "105010987654");
        String expectedKonto = "Succes";
        // på dette stegge sjekk.loggetinnt. gir bare personNr.
        when(sjekk.loggetInn()).thenReturn("01010110523");
        //
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn(expectedKonto);


        String faktsikeKonto = bankController.registrerBetaling(betaling);


        assertEquals(expectedKonto, faktsikeKonto);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn()  {
        Transaksjon betaling = new Transaksjon(1, "105010123456", 500.0, "01.01.2022", "Betaling", "NOK", "105010987654");
        String expectedKonto = "Success";

        when(sjekk.loggetInn()).thenReturn(null);
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn(expectedKonto);

        String faktsikeKonto = bankController.registrerBetaling(betaling);

        assertNull(faktsikeKonto);
    }


    @Test
    public void hentBetalinger_loggetInn() {
        List<Transaksjon> expectedTransaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "105010123456", 500.0, "01.01.2022", "Betaling", "NOK", "105010987654");
        expectedTransaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentBetalinger(anyString())).thenReturn(expectedTransaksjoner);


        List<Transaksjon> actualTransaksjoner = bankController.hentBetalinger();


        assertEquals(expectedTransaksjoner, actualTransaksjoner);

    }

    @Test
    public void hentBetalinger_IkkeLoggetInn()  {
        List<Transaksjon> expectedTransaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "105010123456", 500.0, "01.01.2022", "Betaling", "NOK", "105010987654");
        expectedTransaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn(null);
        when(repository.hentBetalinger(anyString())).thenReturn(expectedTransaksjoner);


        List<Transaksjon> actualTransaksjoner = bankController.hentBetalinger();


        assertNull(actualTransaksjoner);

    }


    @Test
    public void utforBetaling_loggetInn() {

        int txID = 1;
        List<Transaksjon> expectedTransaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "105010123456", 500.0, "01.01.2022", "Betaling", "NOK", "105010987654");
        expectedTransaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.utforBetaling(txID)).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(expectedTransaksjoner);


        List<Transaksjon> actualTransaksjoner = bankController.utforBetaling(txID);


        assertEquals(expectedTransaksjoner, actualTransaksjoner);

    }

    @Test
    public void utforBetaling_IkkeLoggetInn()  {

        int txID = 1;

        when(sjekk.loggetInn()).thenReturn(null);


        List<Transaksjon> actualTransaksjoner = bankController.utforBetaling(txID);


        assertNull(actualTransaksjoner);
    }



}