package com.unpas.chatak.model;

public class CartItemModel  {

    public static final int CART_ITEM=0;
    public static final int TOTAL_AMOUNT_=1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    ////cart item
    private int productImage;
    private  String productTitle;
    private String productPrice;
    private  String cuttedPrice;

    public CartItemModel(int type, int productImage, String productTitle, String productPrice, String cuttedPrice) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    ///cart total

    private String totalHargaItem;
    private String diskon;
    private String ongkosKirim;
    private String totalPajak;
    private String jumlahTotal;


    public CartItemModel(int type ,String totalHargaItem, String diskon, String ongkosKirim, String totalPajak, String jumlahTotal) {
        this.type=type;
        this.totalHargaItem = totalHargaItem;
        this.diskon = diskon;
        this.ongkosKirim = ongkosKirim;
        this.totalPajak = totalPajak;
        this.jumlahTotal = jumlahTotal;

    }

    public String getTotalHargaItem() {
        return totalHargaItem;
    }

    public void setTotalHargaItem(String totalHargaItem) {
        this.totalHargaItem = totalHargaItem;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getOngkosKirim() {
        return ongkosKirim;
    }

    public void setOngkosKirim(String ongkosKirim) {
        this.ongkosKirim = ongkosKirim;
    }

    public String getTotalPajak() {
        return totalPajak;
    }

    public void setTotalPajak(String totalPajak) {
        this.totalPajak = totalPajak;
    }

    public String getJumlahTotal() {
        return jumlahTotal;
    }

    public void setJumlahTotal(String jumlahTotal) {
        this.jumlahTotal = jumlahTotal;
    }


}
