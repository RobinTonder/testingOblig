package oslomet.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SikkerhetTest {
    @InjectMocks
    private Sikkerhet sikkerhet;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private HttpSession httpSession;



    @Test
    public void sjekkLoggInn_FeilPersonnummer() {
        String personnummer = "123"; // This is an incorrect personnummer format
        String passord = "password123";

        String response = sikkerhet.sjekkLoggInn(personnummer, passord);

        assertEquals("Feil i personnummer", response);
    }

    @Test
    public void sjekkLoggInn_FeilPassord() {
        String personnummer = "12345678901";
        String passord = "pw"; // This is an incorrect password format

        String response = sikkerhet.sjekkLoggInn(personnummer, passord);

        assertEquals("Feil i passord", response);
    }

    @Test
    public void sjekkLoggInn_OK() {
        String personnummer = "12345678901";
        String passord = "password123";

        when(bankRepository.sjekkLoggInn(personnummer, passord)).thenReturn("OK");

        String response = sikkerhet.sjekkLoggInn(personnummer, passord);

        assertEquals("OK", response);
        verify(httpSession).setAttribute("Innlogget", personnummer);
    }

    @Test
    public void sjekkLoggInn_Feil() {
        String personnummer = "12345678901";
        String passord = "password123";

        when(bankRepository.sjekkLoggInn(personnummer, passord)).thenReturn("Feil");

        String response = sikkerhet.sjekkLoggInn(personnummer, passord);

        assertEquals("Feil i personnummer eller passord", response);
        verify(httpSession, never()).setAttribute("Innlogget", personnummer);
    }

    @Test
    public void loggUt_Test() {
        sikkerhet.loggUt();
        verify(httpSession).setAttribute("Innlogget", null);
    }

    @Test
    public void loggInnAdmin_OK() {
        String bruker = "Admin";
        String passord = "Admin";

        String response = sikkerhet.loggInnAdmin(bruker, passord);

        assertEquals("Logget inn", response);
        verify(httpSession).setAttribute("Innlogget", "Admin");
    }

    @Test
    public void loggInnAdmin_Feil() {
        String bruker = "NotAdmin";
        String passord = "NotAdmin";

        String response = sikkerhet.loggInnAdmin(bruker, passord);

        assertEquals("Ikke logget inn", response);
        verify(httpSession).setAttribute("Innlogget", null);
    }

    @Test
    public void loggetInn_OK() {
        String personnummer = "12345678901";

        when(httpSession.getAttribute("Innlogget")).thenReturn(personnummer);

        String response = sikkerhet.loggetInn();

        assertEquals(personnummer, response);
    }

    @Test
    public void loggetInn_Feil() {
        when(httpSession.getAttribute("Innlogget")).thenReturn(null);

        String response = sikkerhet.loggetInn();

        assertNull(response);
    }






}
