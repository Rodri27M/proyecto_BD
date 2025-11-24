
package modelo;

import java.util.Date;


public class Factura {
    private String numFac;
    private Date fecFac;
    private String codCli;
    private Date fecCan;
    private String estFac;
    private String codVen;
    private double porIgv;

    public Factura() {
    }

    public Factura(String numFac, Date fecFac, String codCli, Date fecCan, String estFac, String codVen, double porIgv) {
        this.numFac = numFac;
        this.fecFac = fecFac;
        this.codCli = codCli;
        this.fecCan = fecCan;
        this.estFac = estFac;
        this.codVen = codVen;
        this.porIgv = porIgv;
    }

    public String getNumFac() {
        return numFac;
    }

    public void setNumFac(String numFac) {
        this.numFac = numFac;
    }

    public Date getFecFac() {
        return fecFac;
    }

    public void setFecFac(Date fecFac) {
        this.fecFac = fecFac;
    }

    public String getCodCli() {
        return codCli;
    }

    public void setCodCli(String codCli) {
        this.codCli = codCli;
    }

    public Date getFecCan() {
        return fecCan;
    }

    public void setFecCan(Date fecCan) {
        this.fecCan = fecCan;
    }

    public String getEstFac() {
        return estFac;
    }

    public void setEstFac(String estFac) {
        this.estFac = estFac;
    }

    public String getCodVen() {
        return codVen;
    }

    public void setCodVen(String codVen) {
        this.codVen = codVen;
    }

    public double getPorIgv() {
        return porIgv;
    }

    public void setPorIgv(double porIgv) {
        this.porIgv = porIgv;
    }

    @Override
    public String toString() {
        return "Factura{" + "numFac=" + numFac + ", fecFac=" + fecFac + ", codCli=" + codCli + ", fecCan=" + fecCan + ", estFac=" + estFac + ", codVen=" + codVen + ", porIgv=" + porIgv + '}';
    }
    
    
}
