package uoc.ds.pr.model;

public class Equipment {
    private String equipmentId;
    private String name;
    private String description;
    private Room roomAssigned;

    public Equipment(String equipmentId, String name, String description) {
        setEquipmentId(equipmentId);
        setDescription(description);
        setName(name);
        roomAssigned = null;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void updateRoomAssigned(Room room) {
        this.roomAssigned = room;
    }

    public Room getRoomAssigned() {
             return this.roomAssigned;
    }

    public boolean is(Equipment equipment) {
        return this.equipmentId.equals(equipment.getEquipmentId());
    }
}
