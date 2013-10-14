package demo;

public class Cliente {

    protected String creditCardDetails;
    protected String firstName;
    protected String lastName;

    public String getDettagliCartaCredito() {
        return creditCardDetails;
    }

    public void setDettagliCartaCredito(String value) {
        this.creditCardDetails = value;
    }

    public String getNome() {
        return firstName;
    }

    public void setNome(String value) {
        this.firstName = value;
    }

    public String geCognome() {
        return lastName;
    }

    public void setCognome(String value) {
        this.lastName = value;
    }

}
