package duma.asu.models.serializableModels;


import duma.asu.models.interfaces.SendDataParameter;

import java.io.Serializable;
import java.sql.Date;

public class Parameter implements Serializable, SendDataParameter {

    private static final long serialVersionUID = 1L;


    private int id;
    private Date datetime;
    private String name;
    private String codParameter;
    private String lastUpdate;
    private int meaning;
    private int dumaId;// внешний ключь
    //public Duma duma  = new Duma();//навигационное свойство


    public Parameter(String name, String extension) {
        this.name = name;
    }

    public String getName(){ return name; }


    public int getMeaning(){ return meaning; }


    public int setMeaning(int meaning){ return this.meaning = meaning; }


    @Override
    public String toString() {
        return "Parameter{" +
                "id=" + id +
                ", datetime=" + datetime +
                ", name='" + name + '\'' +
                ", codParameter='" + codParameter + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", meaning=" + meaning +
                ", dumaId=" + dumaId +
                '}';
    }
}
