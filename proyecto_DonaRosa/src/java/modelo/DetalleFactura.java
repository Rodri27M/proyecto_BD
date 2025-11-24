/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class DetalleFactura {
     private String numFac;
    private String codPro;
    private int canVen;
    private double preVen;

    
     private String desPro; 
    private double subtotal; 

    public DetalleFactura() {
    }

    public DetalleFactura(String numFac, String codPro, int canVen, double preVen) {
        this.numFac = numFac;
        this.codPro = codPro;
        this.canVen = canVen;
        this.preVen = preVen;
    }

   
    
     
    public DetalleFactura(String numFac, String codPro, int canVen, double preVen, String desPro, double subtotal) {
        this.numFac = numFac;
        this.codPro = codPro;
        this.canVen = canVen;
        this.preVen = preVen;
        this.desPro = desPro;
        this.subtotal = subtotal;
    }
      
    
  
    public String getNumFac() {
        return numFac;
    }

    public void setNumFac(String numFac) {
        this.numFac = numFac;
    }

    public String getCodPro() {
        return codPro;
    }

    public void setCodPro(String codPro) {
        this.codPro = codPro;
    }

    public int getCanVen() {
        return canVen;
    }

    public void setCanVen(int canVen) {
        this.canVen = canVen;
    }

    public double getPreVen() {
        return preVen;
    }

    public void setPreVen(double preVen) {
        this.preVen = preVen;
    }

    public String getDesPro() {
        return desPro;
    }

    public void setDesPro(String desPro) {
        this.desPro = desPro;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double seVenubtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" + "numFac=" + numFac + ", codPro=" + codPro + ", canVen=" + canVen + ", preVen=" + preVen + ", desPro=" + desPro + ", seVenubtotal=" + subtotal + '}';
    }
    
    
    
}