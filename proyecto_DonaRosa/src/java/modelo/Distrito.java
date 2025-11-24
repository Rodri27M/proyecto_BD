/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author rodri
 */
public class Distrito {
    private String codDis;
    private  String nomDis;

    public Distrito() {
    }

    public Distrito(String codDis, String nomDis) {
        this.codDis = codDis;
        this.nomDis = nomDis;
    }

    public String getCodDis() {
        return codDis;
    }

    public void setCodDis(String codDis) {
        this.codDis = codDis;
    }

    public String getNomDis() {
        return nomDis;
    }

    public void setNomDis(String nomDis) {
        this.nomDis = nomDis;
    }

    @Override
    public String toString() {
        return "Distrito{" + "codDis=" + codDis + ", nomDis=" + nomDis + '}';
    }
    
    
    
    }
