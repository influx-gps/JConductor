package gut.follower.org.ControllerTests;

import gut.follower.org.Controller.LoginController;
import gut.follower.org.Models.Account;
import gut.follower.org.Repositories.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class LoginControllerTest {

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_PASSWORD = "testPassword";
    private static final Account validAcc = new Account(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL);

    private static final String INVALID_TEST_EMAIL = "test@example.com";
    private static final String INVALID_TEST_USERNAME = "testUsername";
    private static final String INVALID_TEST_PASSWORD = "testPassword";
    private static final Account invalidAcc = new Account(INVALID_TEST_USERNAME,
                                                          INVALID_TEST_PASSWORD,
                                                          INVALID_TEST_EMAIL);

    @RunWith(MockitoJUnitRunner.class)
    public static class InvalidLoginTest {

        @Mock
        protected static AccountRepository accountRepository;

        @InjectMocks
        protected static LoginController sut;

        @Before
        public void setUp() {
            when(accountRepository.findByUsername(invalidAcc.getUsername())).thenReturn(null);
            when(accountRepository.save(invalidAcc)).thenReturn(null);
        }

        @Test(expected = IllegalStateException.class)
        public void testGetAccountForUsername_invalidUsernameGiven_throwIllegalState() {
            sut.getAccountForUsername(invalidAcc);
        }

        @Test(expected = IllegalStateException.class)
        public void testAuthenticateUser_invalidUsernameGiven_throwIllegalState() {
            sut.authenticateUser(invalidAcc);
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class ValidLoginTest {

        @Mock
        protected static AccountRepository accountRepository;

        @InjectMocks
        protected static LoginController sut;

        @Before
        public void setUp() {
            when(accountRepository.findByUsername(validAcc.getUsername())).thenReturn(validAcc);
            when(accountRepository.save(validAcc)).thenReturn(validAcc);
        }

        @Test
        public void testGetAccountForUsername_validUsernameGiven() {
            Account result = sut.getAccountForUsername(validAcc);
            Assert.assertEquals(validAcc, result);
        }

        @Test
        public void testAuthenticateUser_validUsernameGiven() {
            Account result = sut.authenticateUser(validAcc);
            Assert.assertEquals(validAcc, result);
        }
    }
}
