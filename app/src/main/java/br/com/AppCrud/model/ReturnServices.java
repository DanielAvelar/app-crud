package br.com.AppCrud.model;

public class ReturnServices {
    private Boolean retorno;
    private String message;

    // Constructor that is used to create an instance of the Movie object
    public ReturnServices(Boolean retorno, String message) {
        this.retorno = retorno;
        this.message = message;
    }

    public Boolean getRetorno() { return retorno; }

    public void setRetorno(Boolean retorno) {
        this.retorno = retorno;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
