package controller;

import model.Pracownik;
import model.Stanowisko;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class OknoLogowaniaController {

    public Pracownik checkIfEmployeeExists(String name, String surname){
        name = name.toLowerCase();
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        surname = surname.toLowerCase();
        surname = surname.substring(0,1).toUpperCase() + surname.substring(1);
        DatabaseController db = new DatabaseController();
        Pracownik employee = db.selectPracownik(name, surname);
        if(employee != null)
            return employee;
        return null;
    }

    public boolean checkIfEmployeeIsManager(Pracownik employee){
        DatabaseController db = new DatabaseController();
        List<Stanowisko> posts = db.selectAllFromStanowisko();
        for (Stanowisko position : posts) {
            //TODO: simplified this function as it did not work due to 'ż' in 'Menadżer;
            //TODO::might be decessary to imrove
            if(employee.getPositionId() == 1){// && position.getName().equals(Stanowisko.positions.Menadżer.toString())){
                return true;
            }
        }
        return false;
    }
}
