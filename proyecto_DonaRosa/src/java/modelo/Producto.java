
package modelo;


public class Producto {
    private String codPro;
    private String desPro;
    private double prePro;
    private int sacPro;
    private int smiPro;
    private String uniPro;
    private String linPro;
    private String impPro;

    public Producto() {
    }

    public Producto(String codPro, String desPro, double prePro, int sacPro, int smiPro, String uniPro, String linPro, String impPro) {
        this.codPro = codPro;
        this.desPro = desPro;
        this.prePro = prePro;
        this.sacPro = sacPro;
        this.smiPro = smiPro;
        this.uniPro = uniPro;
        this.linPro = linPro;
        this.impPro = impPro;
    }

    public String getCodPro() {
        return codPro;
    }

    public void setCodPro(String codPro) {
        this.codPro = codPro;
    }

    public String getDesPro() {
        return desPro;
    }

    public void setDesPro(String desPro) {
        this.desPro = desPro;
    }

    public double getPrePro() {
        return prePro;
    }

    public void setPrePro(double prePro) {
        this.prePro = prePro;
    }

    public int getSacPro() {
        return sacPro;
    }

    public void setSacPro(int sacPro) {
        this.sacPro = sacPro;
    }

    public int getSmiPro() {
        return smiPro;
    }

    public void setSmiPro(int smiPro) {
        this.smiPro = smiPro;
    }

    public String getUniPro() {
        return uniPro;
    }

    public void setUniPro(String uniPro) {
        this.uniPro = uniPro;
    }

    public String getLinPro() {
        return linPro;
    }

    public void setLinPro(String linPro) {
        this.linPro = linPro;
    }

    public String getImpPro() {
        return impPro;
    }

    public void setImpPro(String impPro) {
        this.impPro = impPro;
    }

    @Override
    public String toString() {
        return "Producto{" + "codPro=" + codPro + ", desPro=" + desPro + ", prePro=" + prePro + ", sacPro=" + sacPro + ", smiPro=" + smiPro + ", uniPro=" + uniPro + ", linPro=" + linPro + ", impPro=" + impPro + '}';
    }
    
    
}
