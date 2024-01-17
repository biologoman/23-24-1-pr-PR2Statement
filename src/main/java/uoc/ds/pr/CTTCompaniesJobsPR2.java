package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;

import java.time.LocalDate;

public interface CTTCompaniesJobsPR2 extends CTTCompaniesJobs {
    public static final int MAX_NUM_ROLES= 24;
    public static final int HOURS_PER_DAY = 8;

    public static final int MAX_BEST5_EQUIPPEMENT = 5000;

    enum RoomType {
        LABORATORY,
        OFFICE,
        COWORKING
    }

    enum AssignEquipmentResponse {
        ASSIGNED,
        REASSIGNED
    }

    enum Level {
        EXPERT (0),
        SENIOR (1),
        JUNIOR (2),
        INTERN (3),
        BEFINNER (4);
        private final int value;

        Level(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    public void addRole(String roleId, String description);

    public void addEmployee(String employeeId, String name, String surname, LocalDate localDate, String role);

    public void addRoom(String roomId, String name, String description, RoomType roomtype);

    public void assignEmployee(String employeeId, String roomId) throws EmployeeAlreadyAssignedException, EmployeeNotFoundException, RoomNotFoundException;

    public Iterator<Employee> getEmployeesByRoom(String roomId) throws RoomNotFoundException, NOEmployeeException;

    public Iterator<Employee> getEmployeesByRole(String roleId) throws NOEmployeeException;

    public void addEquipment(String equipmentId, String name, String description);

    public AssignEquipmentResponse assignEquipment(String equipmentId, String roomId) throws EquipmentNotFoundException, RoomNotFoundException, EquipmentAlreadyAssignedException;

    public Level getLevel(String workerId) throws WorkerNotFoundException;

    public Iterator<Enrollment> getWorkersByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NoWorkerException;

    public Iterator<Enrollment> getSubstitutesByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NoWorkerException;

    public Iterator<Room> getRoomsWithoutEmployees() throws NoRoomsException;

    public Iterator<Room> best5EquippedRooms() throws NoRoomsException;

    public void addFollower(String followerId, String followedId) throws FollowerNotFound, FollowedException;
    public Iterator<Employee> getFollowers(String followedId) throws EmployeeNotFoundException, NoFollowersException, FollowerNotFound;
    public Iterator<Employee> getFollowings(String followerId) throws EmployeeNotFoundException, NoFollowedException;

    public Iterator<Employee> recommendations(String followerId) throws EmployeeNotFoundException, NoFollowedException;

    public Iterator<Employee> getUnfollowedColleagues(String employeeId) throws EmployeeNotFoundException, NOEmployeeException;


    /***********************************************************************************/
    /******************** AUX OPERATIONS  **********************************************/
    /***********************************************************************************/
    public int numRoles();


    public int numEmployees();
    public int numEmployeesByRole(String roleId);
    public int numRooms();
    public int numEquipments();
    public int numEquipmentsByRoom(String roomId);

    public int numFollowers(String employeeId);

    public int numFollowings(String employeeId);

    public int numRoomsByEmployee(String employee);

    public Room whereIs(String equipmentId);

    public Role getRole(String role);
    public Employee getEmployee(String employeeId);
    public Room getRoom(String roomId);
    public Equipment getEquipment(String equipmentId);


}
