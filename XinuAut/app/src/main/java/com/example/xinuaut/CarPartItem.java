package com.example.xinuaut;

public class CarPartItem {
    private String ID;
    private String Name;
    private String Codes;
    private int Price;
    private int imgResource;

    public String getName() {
        return Name;
    }

    public String getCodes() {
        return Codes;
    }

    public int getPrice() {
        return Price;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCodes(String codes) {
        Codes = codes;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public CarPartItem() {
    }

    public CarPartItem(String name, String codes, int price, int imgResource) {
        this.Name = name;
        this.Codes = codes;
        this.Price = price;
        this.imgResource = imgResource;
    }

    public String _getID(){
        return ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

}
