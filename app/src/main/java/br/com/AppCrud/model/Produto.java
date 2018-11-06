package br.com.AppCrud.model;

public class Produto {
    private String _id;
    private String name;
    private String description;
    private String amount;
    private String idProduct;
    private String category;
    private String price;
    private String urlImage;

    // Constructor that is used to create an instance of the Movie object
    public Produto(String _id, String name, String description, String amount, String idProduct, String category, String price, String urlImage) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.idProduct = idProduct;
        this.category = category;
        this.price = price;
        this.urlImage = urlImage;
    }

    public String geId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrlImage() {
        return urlImage;
    }
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
