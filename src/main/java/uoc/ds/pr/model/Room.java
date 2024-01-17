package uoc.ds.pr.model;

import edu.uoc.ds.adt.helpers.Position;
import edu.uoc.ds.adt.nonlinear.Dictionary;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.Traversal;
import uoc.ds.pr.CTTCompaniesJobsPR2;

import java.util.Comparator;

public class Room implements Comparable<Room>{
    public static final Comparator<Room> CMP_V = (ro1, ro2)->Double.compare(ro1.numEquipments(), ro2.numEquipments());
    private String roomId;
    private String name;
    private String description;
    private CTTCompaniesJobsPR2.RoomType roomType;
    private List<Employee> employees;
    private List<Equipment> equipments;

    public Room(String roomId, String name, String description, CTTCompaniesJobsPR2.RoomType roomType) {
        setRoomId(roomId);
        setName(name);
        setDescription(description);
        setRoomType(roomType);
        this.employees = new LinkedList<>();
        this.equipments = new LinkedList<>();
    }
    @Override
    public int compareTo(Room o) {
        return this.roomId.compareTo(o.roomId);
    }
    public List<Equipment> getAllEquipments(){
        return this.equipments;
    }

    public List<Employee> getAllEmployees(){
        return this.employees;
    }
    public int numEquipments(){
        return equipments.size();
    }
    public boolean existEmployee(Employee employee) {
        boolean found = false;
        Iterator<Employee> it = employees.values();

        while (it.hasNext() && !found) {
            found = it.next().is(employee);
        }
       return found;
    }

    public boolean existEquipment(Equipment equipment) {
        boolean found = false;
        Iterator<Equipment> it = equipments.values();

        while (it.hasNext() && !found) {
            found = it.next().is(equipment);
        }
        return found;
    }
    public void addEmployee(Employee employee) {
        employees.insertEnd(employee);
    }

    public void addEquipment(Equipment equipment){
        equipments.insertEnd(equipment);
    }

    public void removeEquipment(List<Equipment> equipments, Equipment equipment){
        Traversal<Equipment> r = equipments.positions();
        Position<Equipment> pos = null;
        while(r.hasNext()){
            pos = r.next();
            if (pos.getElem().is(equipment)){
                this.equipments.delete(pos);
            }
        }

    }

    public String getName(){
        return  this.name;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getDescription() {
        return description;
    }

    public CTTCompaniesJobsPR2.RoomType getRoomType() {
        return roomType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomType(CTTCompaniesJobsPR2.RoomType roomType) {
        this.roomType = roomType;
    }
}
