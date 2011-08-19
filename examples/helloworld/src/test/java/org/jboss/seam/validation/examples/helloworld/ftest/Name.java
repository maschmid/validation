package org.jboss.seam.validation.examples.helloworld.ftest;

/**
 * A model for the name form.
 * 
 * @author <a href="http://community.jboss.org/people/maschmid">Marek Schmidt</a>
 *
 */
public class Name {
    private String firstName;
    private String middleName;
    private String lastName;
    
    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }
    
    public String getName() {
        return this.firstName + " " + this.middleName + " " + this.lastName;
    }
}
