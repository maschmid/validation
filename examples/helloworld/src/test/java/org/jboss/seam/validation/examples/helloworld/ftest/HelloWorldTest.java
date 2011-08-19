package org.jboss.seam.validation.examples.helloworld.ftest;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.test.selenium.AbstractTestCase;
import org.jboss.test.selenium.locator.XpathLocator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.*;

/**
 * A functional test for the HelloWorld example
 * 
 * @author <a href="http://community.jboss.org/people/maschmid">Marek Schmidt</a>
 *
 */
public class HelloWorldTest extends AbstractTestCase {
    
    protected XpathLocator OK_BUTTON = xp("//input[contains(@value,'OK')]");
    protected XpathLocator FIRST_NAME_INPUT = xp("//input[contains(@name,'firstName')]");
    protected XpathLocator MIDDLE_NAME_INPUT = xp("//input[contains(@name,'middleName')]");
    protected XpathLocator LAST_NAME_INPUT = xp("//input[contains(@name,'lastName')]");
    
    private static final String[] bannedNames = {"root", "admin", "foo bar"};
    
    private static final Name validName = new Name("John", "William", "Doe");
    private static final Name noFirstName = new Name("", "William", "Doe");
    private static final Name tooLongName = new Name("Jonathan", "Williamson", "Doe");
    private static final Name tooShortName = new Name("A", "", "");
    private static final Name bannedName = new Name("John", "Root", "Doe");
    
    private static final String NO_FIRST_NAME_ERROR = "may not be empty"; 
    private static final String TOO_LONG_NAME_ERROR = "size must be between 0 and 16"; 
    private static final String TOO_SHORT_NAME_ERROR = "size must be between 3 and";
    private static final String BANNED_NAME_ERROR = "Fails the banned filter";
    
    @BeforeMethod
    public void openStartUrl() throws MalformedURLException {
        selenium.open(new URL(contextPath.toString()));
    }

    @Test
    public void testFrontPage() {
        assertTrue(selenium.isTextPresent("Hi, what's your name?"), "Question not found on the front page");   
        
        assertTrue(selenium.isElementPresent(FIRST_NAME_INPUT), "First Name field not found on the front page");
        assertTrue(selenium.isElementPresent(MIDDLE_NAME_INPUT), "Middle Name field not found on the front page");
        assertTrue(selenium.isElementPresent(LAST_NAME_INPUT), "Laste Name field not found on the front page");
        
        assertTrue(selenium.isElementPresent(OK_BUTTON), "OK button not found on the front page");
        
        for (String bannedName : bannedNames) {
            assertTrue(selenium.isTextPresent(bannedName), "banned name \"" + bannedName + "\" not found on the front page");
        }
    }   
    
    @Test
    public void testValidName() {
        enterName(validName);        
        assertTrue(selenium.isTextPresent("Hello, " + validName.getName() + "!"), "Greeting expected");
    }
    
    @Test
    public void testNoFirstName() {
        testInvalidName(noFirstName, NO_FIRST_NAME_ERROR);
    }
    
    @Test
    public void testTooLongName() {
        testInvalidName(tooLongName, TOO_LONG_NAME_ERROR);
    }
    
    @Test
    public void testTooShortName() {
        testInvalidName(tooShortName, TOO_SHORT_NAME_ERROR);
    }
    
    @Test
    public void testBannedName() {
        testInvalidName(bannedName, BANNED_NAME_ERROR);
    }
    
    private void enterName(Name name) {
        selenium.type(FIRST_NAME_INPUT, name.getFirstName());
        selenium.type(MIDDLE_NAME_INPUT, name.getMiddleName());
        selenium.type(LAST_NAME_INPUT, name.getLastName());
        
        waitHttp(selenium).click(OK_BUTTON);
    }
    
    private void testInvalidName(Name name, String expectedMessage) {
        enterName(name);
        assertTrue(selenium.isTextPresent(expectedMessage), "Expecting validation error message \"" + expectedMessage +  "\"");
        assertFalse(selenium.isTextPresent("Hello, "), "No greeting should appear for an invalid name");
    }
}

