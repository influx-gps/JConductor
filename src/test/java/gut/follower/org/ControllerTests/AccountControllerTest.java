package gut.follower.org.ControllerTests;

import gut.follower.org.Controller.AccountController;
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
import org.springframework.http.HttpStatus;

import java.security.Principal;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_PASSWORD = "testPassword";
    private static final String TEST_PASSWORD_MOD = "testPasswordMod";
    private static final Account acc = new Account(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL);
    private static final Account acc_mod = new Account(TEST_USERNAME, TEST_PASSWORD_MOD, TEST_EMAIL);

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountController sut;

    @Mock
    private Principal principal;

    @Before
    public void setUp() {
        when(accountRepository.findByEmail(acc.getEmail())).thenReturn(null);
        when(accountRepository.findByUsername(acc.getUsername())).thenReturn(acc);
        when(accountRepository.save(acc)).thenReturn(acc_mod);
        when(principal.getName()).thenReturn(TEST_USERNAME);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetAccount_accDoesNotExist(){
        when(accountRepository.findByUsername(TEST_USERNAME)).thenReturn(null);
        sut.getAccount(principal);
    }

    @Test
    public void testUpdateAccount_allOk() {
        Account result = sut.updateAccount(principal, acc);
        Assert.assertEquals(acc_mod, result);
    }

    @Test(expected = IllegalStateException.class)
    public void testUpdateAccount_emailTaken() {
        when(accountRepository.findByEmail(acc.getEmail())).thenReturn(acc);
        sut.updateAccount(principal, acc);
    }

    @Test
    public void testDeleteAccount(){
        HttpStatus httpStatus = sut.deleteAccount(principal);
        Assert.assertThat(httpStatus, is(HttpStatus.OK));
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteAccount_accDoesNotExist(){
        when(accountRepository.findByUsername(TEST_USERNAME)).thenReturn(null);
        sut.deleteAccount(principal);
    }

}
