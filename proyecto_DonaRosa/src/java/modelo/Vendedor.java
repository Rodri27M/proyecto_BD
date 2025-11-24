
package modelo;

import java.util.Date;


public class Vendedor {
     private String codVen;
    private String nomVen;
    private String apeVen;
    private double sueVen;
    private Date finVen;
    private String tipVen;
    private String codDis;

    public Vendedor() {
    }

    public Vendedor(String codVen, String nomVen, String apeVen, double sueVen, Date finVen, String tipVen, String codDis) {
        this.codVen = codVen;
        this.nomVen = nomVen;
        this.apeVen = apeVen;
        this.sueVen = sueVen;
        this.finVen = finVen;
        this.tipVen = tipVen;
        this.codDis = codDis;
    }

    public String getCodVen() {
        return codVen;
    }

    public void setCodVen(String codVen) {
        this.codVen = codVen;
    }

    public String getNomVen() {
        return nomVen;
    }

    public void setNomVen(String nomVen) {
        this.nomVen = nomVen;
    }

    public String getApeVen() {
        return apeVen;
    }

    public void setApeVen(String apeVen) {
        this.apeVen = apeVen;
    }

    public double getSueVen() {
        return sueVen;
    }

    public void setSueVen(double sueVen) {
        this.sueVen = sueVen;
    }

    public Date getFinVen() {
        return finVen;
    }

    public void setFinVen(Date finVen) {
        this.finVen = finVen;
    }

    public String getTipVen() {
        return tipVen;
    }

    public void setTipVen(String tipVen) {
        this.tipVen = tipVen;
    }

    public String getCodDis() {
        return codDis;
    }

    public void setCodDis(String codDis) {
        this.codDis = codDis;
    }

    @Override
    public String toString() {
        return "Vendedor{" + "codVen=" + codVen + ", nomVen=" + nomVen + ", apeVen=" + apeVen + ", sueVen=" + sueVen + ", finVen=" + finVen + ", tipVen=" + tipVen + ", codDis=" + codDis + '}';
    }
    
    
}
