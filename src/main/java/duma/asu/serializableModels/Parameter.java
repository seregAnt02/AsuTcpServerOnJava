package duma.asu.serializableModels;

import java.sql.Date;

public class Parameter {
    public int id;
    public Date datetime;
    public String parameter;
    public String codParameter;
    public String lastUpdate;
    public int meaning;
    public int dumaId;// внешний ключь
    public Duma duma  = new Duma();//навигационное свойство
}
