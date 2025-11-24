
package modelo;

import java.util.Date;

public class Cliente {
    private String codCli;
    private String rsoCli;
    private String dirCli;
    private String tlfCli;
    private String rucCli;
    private String codDis;
    private Date fecReg;
    private String tipCli;
    private String conCli;

    public Cliente() {
    }

    public Cliente(String codCli, String rsoCli, String dirCli, String tlfCli, String rucCli, String codDis, Date fecReg, String tipCli, String conCli) {
        this.codCli = codCli;
        this.rsoCli = rsoCli;
        this.dirCli = dirCli;
        this.tlfCli = tlfCli;
        this.rucCli = rucCli;
        this.codDis = codDis;
        this.fecReg = fecReg;
        this.tipCli = tipCli;
        this.conCli = conCli;
    }

    public String getCodCli() {
        return codCli;
    }

    public void setCodCli(String codCli) {
        this.codCli = codCli;
    }

    public String getRsoCli() {
        return rsoCli;
    }

    public void setRsoCli(String rsoCli) {
        this.rsoCli = rsoCli;
    }

    public String getDirCli() {
        return dirCli;
    }

    public void setDirCli(String dirCli) {
        this.dirCli = dirCli;
    }

    public String getTlfCli() {
        return tlfCli;
    }

    public void setTlfCli(String tlfCli) {
        this.tlfCli = tlfCli;
    }

    public String getRucCli() {
        return rucCli;
    }

    public void setRucCli(String rucCli) {
        this.rucCli = rucCli;
    }

    public String getCodDis() {
        return codDis;
    }

    public void setCodDis(String codDis) {
        this.codDis = codDis;
    }

    public Date getFecReg() {
        return fecReg;
    }

    public void setFecReg(Date fecReg) {
        this.fecReg = fecReg;
    }

    public String getTipCli() {
        return tipCli;
    }

    public void setTipCli(String tipCli) {
        this.tipCli = tipCli;
    }

    public String getConCli() {
        return conCli;
    }

    public void setConCli(String conCli) {
        this.conCli = conCli;
    }

    @Override
    public String toString() {
        return "Cliente{" + "codCli=" + codCli + ", rsoCli=" + rsoCli + ", dirCli=" + dirCli + ", tlfCli=" + tlfCli + ", rucCli=" + rucCli + ", codDis=" + codDis + ", fecReg=" + fecReg + ", tipCli=" + tipCli + ", conCli=" + conCli + '}';
    }
    
    
}
