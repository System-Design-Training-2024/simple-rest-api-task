package com.example.task1.product;

public class UpdateParameter {
    private String nname;
    private String ndesc;
    private Double nprice;

    UpdateParameter(String nname, String ndesc, Double nprice) {
        this.nname = nname;
        this.ndesc = ndesc;
        this.nprice = nprice;
    }

    public String getNname() {
        return nname;
    }

    public String getNdesc() {
        return ndesc;
    }

    public Double getNprice() {
        return nprice;
    }
}
