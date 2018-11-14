package br.com.AppCrud.model;

public class Autenticacao {
    private Boolean authentication;
    private String session;
    private String message;

    // Constructor that is used to create an instance of the Movie object
    public Autenticacao(Boolean authentication, String session, String message) {
        this.authentication = authentication;
        this.session = session;
        this.message = message;
    }

    public Boolean getAutenticacao() {
        return authentication;
    }

    public void setAutenticacao(Boolean authentication) {
        this.authentication = authentication;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
