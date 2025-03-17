package duma.asu.views;

import duma.asu.models.interfaces.SendDataParameter;

public class ViewReadStreamReturnGenericObject {



    public void viewSendModelToClient(String name){
        System.out.print("Объект отправлен клиенту " + name);
    }

    public void viewsNameAndClass(String nameObjectAnClass, String nameModel){
        System.out.println("Десерилизация объекта " + nameObjectAnClass + ": " + nameModel);
    }
}
