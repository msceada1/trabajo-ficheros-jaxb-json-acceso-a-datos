package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Equipamiento {

    @XmlElement(name = "extra")
    private List<String> equipamiento;

    public Equipamiento() {
    }

    public List<String> getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(List<String> equipamiento) {
        this.equipamiento = equipamiento;
    }

    @Override
    public String toString() {
        return "Equipamiento{" +
                "equipamiento=" + equipamiento +
                '}';
    }
}
