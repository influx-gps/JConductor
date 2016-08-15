package gut.follower.org.ControllerTests;


import gut.follower.org.Controller.RegisterController;
import gut.follower.org.Models.Account;
import gut.follower.org.Repositories.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_PASSWORD = "testPassword";
    private static final Account acc = new Account(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL);

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private RegisterController sut;

    @Before
    public void setUp() {
        when(accountRepository.findByEmail(acc.getEmail())).thenReturn(null);
        when(accountRepository.findByUsername(acc.getUsername())).thenReturn(null);
        when(accountRepository.save(acc)).thenReturn(acc);
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckIfEmailNotTaken_nullGiven_throwIllegalState() {
        sut.checkIfEmailNotTaken(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testRegisterAccount_nullGiven_throwIllegalState() {
        sut.registerAccount(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testRegisterUser_nullGiven_throwIllegalState() {
        sut.registerUser(null);
    }

    @Test
    public void testCheckIfEmailNotTaken_allOk() {
        Account result = sut.checkIfEmailNotTaken(acc);
        Assert.assertEquals(acc, result);
    }

    @Test
    public void testRegisterAccount_allOk() {
        Account result = sut.registerAccount(acc);
        Assert.assertEquals(acc, result);
    }

    @Test
    public void testRegisterUser_allOk() {
        Account result = sut.registerUser(acc);
        Assert.assertEquals(acc, result);
    }
}
