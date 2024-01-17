package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.DateUtils;

import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class CTTCCompaniesJobsPR2Test extends CTTCompaniesJobsPR1Test {


    @Before
    public void setUp() throws Exception {
        super.setUp();
        super.cttCompaniesJobs = FactoryCTTCCompaniesJobs.getCTTCompaniesJobs();
    }

    @Test
    public void addRoleTest() {
        cttCompaniesJobs.addRole("R1", "the Role1");
        cttCompaniesJobs.addRole("R2", "the Role2");
        cttCompaniesJobs.addRole("R3", "the Role3");
        cttCompaniesJobs.addRole("R4", "the Role4");
        Assert.assertEquals(4, cttCompaniesJobs.numRoles());

        cttCompaniesJobs.addRole("R5", "XXXXXXX");
        Assert.assertEquals(5, cttCompaniesJobs.numRoles());
        Role role = cttCompaniesJobs.getRole("R5");
        Assert.assertEquals("XXXXXXX", role.getDescription());

        cttCompaniesJobs.addRole("R5", "the Role5");
        Assert.assertEquals(5, cttCompaniesJobs.numRoles());

        Assert.assertEquals("the Role5", role.getDescription());

    }

    @Test
    public void addEmployeeTest() {
        addRoleTest();
        cttCompaniesJobs.addEmployee("3811123A", "Pepe", "Gotera", DateUtils.createLocalDate("07-02-1992"), "R1");
        Assert.assertEquals(1, cttCompaniesJobs.numEmployees());

        cttCompaniesJobs.addEmployee("334545445A", "Joan", "XXXX", DateUtils.createLocalDate("03-01-2001"), "R2");
        Assert.assertEquals(2, cttCompaniesJobs.numEmployees());
        Employee employee = cttCompaniesJobs.getEmployee("334545445A");
        Assert.assertEquals("XXXX", employee.getSurname());

        Assert.assertEquals(1, cttCompaniesJobs.numEmployeesByRole("R1"));
        Assert.assertEquals(1, cttCompaniesJobs.numEmployeesByRole("R2"));

        cttCompaniesJobs.addEmployee("334545445A", "Joan", "Salvat", DateUtils.createLocalDate("03-01-2001"), "R2");
        Assert.assertEquals(2, cttCompaniesJobs.numEmployees());
        Assert.assertEquals("Salvat", employee.getSurname());

        Assert.assertEquals(1, cttCompaniesJobs.numEmployeesByRole("R1"));
        Assert.assertEquals(1, cttCompaniesJobs.numEmployeesByRole("R2"));

        cttCompaniesJobs.addEmployee("334545445A", "Joan", "Salvat", DateUtils.createLocalDate("03-01-2001"), "R1");
        Assert.assertEquals(2, cttCompaniesJobs.numEmployees());
        Assert.assertEquals(2, cttCompaniesJobs.numEmployeesByRole("R1"));
        Assert.assertEquals(0, cttCompaniesJobs.numEmployeesByRole("R2"));
        Assert.assertEquals("Salvat", employee.getSurname());

        cttCompaniesJobs.addEmployee("12122145A", "Silvia", "Pérez", DateUtils.createLocalDate("13-07-2003"), "R2");
        Assert.assertEquals(3, cttCompaniesJobs.numEmployees());

        Assert.assertEquals(2, cttCompaniesJobs.numEmployeesByRole("R1"));
        Assert.assertEquals(1, cttCompaniesJobs.numEmployeesByRole("R2"));

        cttCompaniesJobs.addEmployee("67122145A", "Pere", "Sánchez", DateUtils.createLocalDate("13-10-1999"), "R1");
        cttCompaniesJobs.addEmployee("89333214B", "Josep", "Martí", DateUtils.createLocalDate("13-10-1999"), "R1");
        cttCompaniesJobs.addEmployee("67322245Z", "Borja", "Campos", DateUtils.createLocalDate("03-01-1992"), "R1");
        cttCompaniesJobs.addEmployee("93534145Z", "Salvador", "Cardús", DateUtils.createLocalDate("23-12-1992"), "R1");
        cttCompaniesJobs.addEmployee("12365232F", "Josep", "Ibarra", DateUtils.createLocalDate("24-11-2000"), "R1");
        cttCompaniesJobs.addEmployee("76239045G", "Linda", "Lzópe", DateUtils.createLocalDate("14-08-1072"), "R1");
        cttCompaniesJobs.addEmployee("439452145T", "Mario", "Fernández", DateUtils.createLocalDate("11-11-1969"), "R1");
        Assert.assertEquals(10, cttCompaniesJobs.numEmployees());
        Assert.assertEquals(9, cttCompaniesJobs.numEmployeesByRole("R1"));
        Assert.assertEquals(1, cttCompaniesJobs.numEmployeesByRole("R2"));
    }


    @Test
    public void addRoomTest() {
        cttCompaniesJobs.addRoom("room1", "The ROOM1 ...","Description/Room1", CTTCompaniesJobsPR2.RoomType.OFFICE );
        cttCompaniesJobs.addRoom("room2", "The ROOM2 ...","Description/Room2", CTTCompaniesJobsPR2.RoomType.OFFICE );
        cttCompaniesJobs.addRoom("room3", "The ROOM3 ...","Description/Room3", CTTCompaniesJobsPR2.RoomType.LABORATORY );
        cttCompaniesJobs.addRoom("room4", "The XXXXXX ...","Description/XXXXX", CTTCompaniesJobsPR2.RoomType.COWORKING );
        Room room4 = cttCompaniesJobs.getRoom("room4");
        Assert.assertEquals("The XXXXXX ...", room4.getName());
        Assert.assertEquals("Description/XXXXX", room4.getDescription());

        Assert.assertEquals(4, cttCompaniesJobs.numRooms());

        cttCompaniesJobs.addRoom("room4", "The ROOM4 ...","Description/Room4", CTTCompaniesJobsPR2.RoomType.COWORKING );

        Assert.assertEquals("The ROOM4 ...", room4.getName());
        Assert.assertEquals("Description/Room4", room4.getDescription());
        Assert.assertEquals(4, cttCompaniesJobs.numRooms());

    }


    @Test
    public void addAssignEmployeeTest() throws DSException {
        this.addEmployeeTest();
        this.addRoomTest();

        Assert.assertEquals(10, cttCompaniesJobs.numEmployees());
        Assert.assertEquals(4, cttCompaniesJobs.numRooms());


        Assert.assertThrows(EmployeeNotFoundException.class, () ->
                cttCompaniesJobs.assignEmployee("XXXXXXX", "room1"));

        Assert.assertThrows(RoomNotFoundException.class, () ->
                cttCompaniesJobs.assignEmployee("3811123A", "XXXXXX"));

        cttCompaniesJobs.assignEmployee("3811123A", "room1");
        cttCompaniesJobs.assignEmployee("3811123A", "room2");
        cttCompaniesJobs.assignEmployee("3811123A", "room4");

        Assert.assertEquals(3, cttCompaniesJobs.numRoomsByEmployee("3811123A"));

        cttCompaniesJobs.assignEmployee("12122145A", "room1");
        cttCompaniesJobs.assignEmployee("12122145A", "room4");

        Assert.assertEquals(2, cttCompaniesJobs.numRoomsByEmployee("12122145A"));

        Assert.assertThrows(EmployeeAlreadyAssignedException.class, () ->
                cttCompaniesJobs.assignEmployee("3811123A", "room1"));

        cttCompaniesJobs.assignEmployee("67122145A", "room1");
        cttCompaniesJobs.assignEmployee("89333214B", "room1");
        cttCompaniesJobs.assignEmployee("67322245Z", "room4");
        cttCompaniesJobs.assignEmployee("12365232F", "room4");
        cttCompaniesJobs.assignEmployee("76239045G", "room1");
        cttCompaniesJobs.assignEmployee("439452145T", "room4");
    }


    @Test
    public void getEmployeesByRoomTest() throws DSException {
        Assert.assertThrows(RoomNotFoundException.class, () ->
                cttCompaniesJobs.getEmployeesByRoom("room1"));

        this.addAssignEmployeeTest();

        Assert.assertThrows(NOEmployeeException.class, () ->
                cttCompaniesJobs.getEmployeesByRoom("room3"));

        Iterator<Employee> it = cttCompaniesJobs.getEmployeesByRoom("room1");

        it.hasNext();
        Employee e1 = it.next();
        Assert.assertEquals("3811123A", e1.getEmployeeId());
        Assert.assertEquals("Pepe", e1.getName());

        it.hasNext();
        Employee e2 = it.next();
        Assert.assertEquals("12122145A", e2.getEmployeeId());
        Assert.assertEquals("Silvia", e2.getName());

        it.hasNext();
        Employee e3 = it.next();
        Assert.assertEquals("67122145A", e3.getEmployeeId());
        Assert.assertEquals("Pere", e3.getName());

        it.hasNext();
        Employee e4 = it.next();
        Assert.assertEquals("89333214B", e4.getEmployeeId());
        Assert.assertEquals("Josep", e4.getName());

        it.hasNext();
        Employee e5 = it.next();
        Assert.assertEquals("76239045G", e5.getEmployeeId());
        Assert.assertEquals("Linda", e5.getName());

        Assert.assertFalse(it.hasNext());

    }

    @Test
    public void getEmployeesByRoleTest() throws DSException {
        addEmployeeTest();

        Assert.assertThrows(NOEmployeeException.class, () ->
                cttCompaniesJobs.getEmployeesByRole("R5"));

        Iterator<Employee> it = cttCompaniesJobs.getEmployeesByRole("R2");
        it.hasNext();
        Employee e1 = it.next();
        Assert.assertEquals("12122145A", e1.getEmployeeId());
        Assert.assertEquals("Silvia", e1.getName());

        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void addEquipmentTest() {
        cttCompaniesJobs.addEquipment("equipment1", "Projector1", "Description/Projector1");
        cttCompaniesJobs.addEquipment("equipment2", "Projector2", "Description/Projector2");
        cttCompaniesJobs.addEquipment("equipment3", "Projector3", "Description/Projector3");
        cttCompaniesJobs.addEquipment("equipment4", "Projector4", "Description/Projector4");
        cttCompaniesJobs.addEquipment("equipment5", "Videoconference1", "Description/Videoconference1");
        cttCompaniesJobs.addEquipment("equipment6", "Videoconference1", "Description/Videoconference1");
        cttCompaniesJobs.addEquipment("equipment7", "Projector5", "Description/Projector5");
        cttCompaniesJobs.addEquipment("equipment8", "Projector6", "Description/Projector6");
        cttCompaniesJobs.addEquipment("equipment9", "Projector7", "Description/Projector7");

        Assert.assertEquals(9, cttCompaniesJobs.numEquipments());

        cttCompaniesJobs.addEquipment("equipment6", "VideoconferenceXXX", "Description/VideoconferenceXXX");
        Equipment equipment = cttCompaniesJobs.getEquipment("equipment6");
        Assert.assertEquals("VideoconferenceXXX", equipment.getName());
        Assert.assertEquals("Description/VideoconferenceXXX", equipment.getDescription());

        cttCompaniesJobs.addEquipment("equipment6", "Videoconference2", "Description/Videoconference2");
        Assert.assertEquals("Videoconference2", equipment.getName());
        Assert.assertEquals("Description/Videoconference2", equipment.getDescription());

    }

    @Test
    public void assignEquipmentTest() throws DSException {
        addEquipmentTest();
        addRoomTest();

        Assert.assertThrows(EquipmentNotFoundException.class, () ->
                cttCompaniesJobs.assignEquipment("equipmentXXXX","room1"));

        Assert.assertThrows(RoomNotFoundException.class, () ->
                cttCompaniesJobs.assignEquipment("equipment6","roomXXXXX"));

        cttCompaniesJobs.assignEquipment("equipment1", "room1");
        cttCompaniesJobs.assignEquipment("equipment2", "room1");
        cttCompaniesJobs.assignEquipment("equipment3", "room1");
        cttCompaniesJobs.assignEquipment("equipment4", "room1");
        Assert.assertEquals(4, cttCompaniesJobs.numEquipmentsByRoom("room1"));
        Assert.assertEquals("room1", cttCompaniesJobs.whereIs("equipment1").getRoomId());
        Assert.assertEquals("room1", cttCompaniesJobs.whereIs("equipment2").getRoomId());
        Assert.assertEquals("room1", cttCompaniesJobs.whereIs("equipment3").getRoomId());
        Assert.assertEquals("room1", cttCompaniesJobs.whereIs("equipment4").getRoomId());


        cttCompaniesJobs.assignEquipment("equipment5", "room2");
        cttCompaniesJobs.assignEquipment("equipment6", "room2");
        cttCompaniesJobs.assignEquipment("equipment7", "room2");
        Assert.assertEquals(3, cttCompaniesJobs.numEquipmentsByRoom("room2"));
        Assert.assertEquals("room2", cttCompaniesJobs.whereIs("equipment5").getRoomId());
        Assert.assertEquals("room2", cttCompaniesJobs.whereIs("equipment6").getRoomId());
        Assert.assertEquals("room2", cttCompaniesJobs.whereIs("equipment7").getRoomId());

        Assert.assertThrows(EquipmentAlreadyAssignedException.class, () ->
                cttCompaniesJobs.assignEquipment("equipment7", "room2"));

        Assert.assertEquals(4, cttCompaniesJobs.numEquipmentsByRoom("room1"));
        Assert.assertEquals(3, cttCompaniesJobs.numEquipmentsByRoom("room2"));

        CTTCompaniesJobsPR2.AssignEquipmentResponse response = cttCompaniesJobs.assignEquipment("equipment7", "room1");

        Assert.assertEquals(CTTCompaniesJobsPR2.AssignEquipmentResponse.REASSIGNED, response);
        Assert.assertEquals("room1", cttCompaniesJobs.whereIs("equipment7").getRoomId());

        Assert.assertEquals(5, cttCompaniesJobs.numEquipmentsByRoom("room1"));
        Assert.assertEquals(2, cttCompaniesJobs.numEquipmentsByRoom("room2"));

        Assert.assertEquals("room1", cttCompaniesJobs.whereIs("equipment7").getRoomId());

    }

    @Test
    public void getLevelTest() throws DSException {
        Assert.assertThrows(WorkerNotFoundException.class, () ->
                cttCompaniesJobs.getLevel("workerIdXXXXX"));

        super.signUpJobOfferTest();

        Worker worker1 = cttCompaniesJobs.getWorker("workerId1");
        Assert.assertEquals(403, worker1.getWorkingDays());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.EXPERT, cttCompaniesJobs.getLevel("workerId1"));

        Worker worker2 = cttCompaniesJobs.getWorker("workerId2");
        Assert.assertEquals(26, worker2.getWorkingDays());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.JUNIOR, cttCompaniesJobs.getLevel("workerId2"));

        Worker worker3 = cttCompaniesJobs.getWorker("workerId3");
        Assert.assertEquals(0, worker3.getWorkingDays());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.BEFINNER, cttCompaniesJobs.getLevel("workerId3"));

        Worker worker5 = cttCompaniesJobs.getWorker("workerId5");
        Assert.assertEquals(403, worker5.getWorkingDays());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.EXPERT, cttCompaniesJobs.getLevel("workerId5"));

    }


    @Test
    public void getWorkersByJobOfferTest() throws DSException {

        signUpJobOfferTest();

        Request request34= cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.ENABLED,
                createLocalDate("12-12-2023"), "OK (id4)");
        Assert.assertEquals("jobOfferIdB1", request34.getJobOffer().getJobOfferId());


        Assert.assertThrows(NoWorkerException.class, () ->
                cttCompaniesJobs.getWorkersByJobOffer("jobOfferIdB1"));

        Assert.assertThrows(JobOfferNotFoundException.class, () ->
                cttCompaniesJobs.getWorkersByJobOffer("jobOfferIdXXXXX"));


        JobOffer jobOfferIdA1 = cttCompaniesJobs.getJobOffer("jobOfferIdA1");
        Assert.assertEquals(5, jobOfferIdA1.getNumWorkers());
        Assert.assertEquals(2, jobOfferIdA1.getNumSubstitutes());

        Iterator<Enrollment> it = cttCompaniesJobs.getWorkersByJobOffer("jobOfferIdA1");

        it.hasNext();
        Enrollment enrollment1 = it.next();
        Assert.assertEquals("workerId1",enrollment1.getWorker().getId());

        it.hasNext();
        Enrollment enrollment2 = it.next();
        Assert.assertEquals("workerId5",enrollment2.getWorker().getId());

        it.hasNext();
        Enrollment enrollment3 = it.next();
        Assert.assertEquals("workerId6",enrollment3.getWorker().getId());

        it.hasNext();
        Enrollment enrollment4 = it.next();
        Assert.assertEquals("workerId7",enrollment4.getWorker().getId());

        it.hasNext();
        Enrollment enrollment5 = it.next();
        Assert.assertEquals("workerId8",enrollment5.getWorker().getId());

        Assert.assertFalse(it.hasNext());

    }

    @Test
    public void getSubstitutesByJobOfferTest() throws DSException {
        signUpJobOfferTest();
        cttCompaniesJobs.addWorker("workerId11", "Joanot", "Martoller", createLocalDate("08-01-1979"), CTTCompaniesJobs.Qualification.DOCTORATE);
        cttCompaniesJobs.addWorker("workerId12", "Narcis", "Monturiol", createLocalDate("08-01-1979"), CTTCompaniesJobs.Qualification.DOCTORATE);
        cttCompaniesJobs.addWorker("workerId13", "Emma", "Watson", createLocalDate("08-01-2001"), CTTCompaniesJobs.Qualification.MASTER);
        cttCompaniesJobs.addWorker("workerId14", "Laurin", "Prats", createLocalDate("08-01-1974"), CTTCompaniesJobs.Qualification.MASTER);
        cttCompaniesJobs.addWorker("workerId14", "Laurin", "Prats", createLocalDate("08-01-1974"), CTTCompaniesJobs.Qualification.DOCTORATE);

        Request request3= cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.ENABLED,
                createLocalDate("12-12-2023"), "OK (id4)");
        Assert.assertEquals("jobOfferIdB1", request3.getJobOffer().getJobOfferId());

        Request request4= cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.ENABLED,
                createLocalDate("12-12-2023"), "OK (id5)");
        Assert.assertEquals("jobOfferIdB2", request4.getJobOffer().getJobOfferId());

        JobOffer jobOfferIdB2 = cttCompaniesJobs.getJobOffer("jobOfferIdB2");
        Assert.assertEquals("jobOfferIdB2", jobOfferIdB2.getJobOfferId());

        CTTCompaniesJobs.Response responseB2 = cttCompaniesJobs.signUpJobOffer("workerId12", "jobOfferIdB2");

        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, responseB2);
        Assert.assertEquals(1, jobOfferIdB2.getNumWorkers());
        Assert.assertEquals(0, jobOfferIdB2.getNumSubstitutes());

        JobOffer jobOfferIdB1 = cttCompaniesJobs.getJobOffer("jobOfferIdB1");

        Assert.assertThrows(NoWorkerException.class, () ->
                cttCompaniesJobs.getSubstitutesByJobOffer("jobOfferIdB1"));

        CTTCompaniesJobs.Response response1 = cttCompaniesJobs.signUpJobOffer("workerId11", "jobOfferIdB1");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response1);
        Assert.assertEquals(1, jobOfferIdB1.getNumWorkers());
        Assert.assertEquals(0, jobOfferIdB1.getNumSubstitutes());

        CTTCompaniesJobs.Response response2 = cttCompaniesJobs.signUpJobOffer("workerId12", "jobOfferIdB1");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response2);
        Assert.assertEquals(2, jobOfferIdB1.getNumWorkers());
        Assert.assertEquals(0, jobOfferIdB1.getNumSubstitutes());

        CTTCompaniesJobs.Response response3 = cttCompaniesJobs.signUpJobOffer("workerId13", "jobOfferIdB1");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response2);
        Assert.assertEquals(3, jobOfferIdB1.getNumWorkers());
        Assert.assertEquals(0, jobOfferIdB1.getNumSubstitutes());

        CTTCompaniesJobs.Response response4 = cttCompaniesJobs.signUpJobOffer("workerId14", "jobOfferIdB2");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response4);

        Assert.assertThrows(JobOfferNotFoundException.class, () ->
                cttCompaniesJobs.getSubstitutesByJobOffer("jobOfferIdXXXXX"));

        JobOffer jobOfferIdA1 = cttCompaniesJobs.getJobOffer("jobOfferIdA1");
        Assert.assertEquals(5, jobOfferIdA1.getNumWorkers());
        Assert.assertEquals(2, jobOfferIdA1.getNumSubstitutes());

        ////
        //// jobOfferIdB1
        ///
        Iterator<Enrollment> it1 = cttCompaniesJobs.getSubstitutesByJobOffer("jobOfferIdA1");

        it1.hasNext();
        Enrollment enrollment1 = it1.next();
        Assert.assertEquals("workerId9",enrollment1.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.BEFINNER, enrollment1.getWorker().getLevel());

        it1.hasNext();
        Enrollment enrollment2 = it1.next();
        Assert.assertEquals("workerId10",enrollment2.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.BEFINNER, enrollment2.getWorker().getLevel());


        ////
        //// jobOfferIdB1
        ///
        CTTCompaniesJobs.Response response5 = cttCompaniesJobs.signUpJobOffer("workerId11", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.SUBSTITUTE, response5);
        Assert.assertEquals(5, jobOfferIdA1.getNumWorkers());
        Assert.assertEquals(3, jobOfferIdA1.getNumSubstitutes());

        it1 = cttCompaniesJobs.getSubstitutesByJobOffer("jobOfferIdA1");

        it1.hasNext();
        Enrollment enrollment = it1.next();
        Assert.assertEquals("workerId9",enrollment.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.BEFINNER, enrollment.getWorker().getLevel());

        it1.hasNext();
        enrollment = it1.next();
        Assert.assertEquals("workerId10",enrollment.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.BEFINNER, enrollment.getWorker().getLevel());

        it1.hasNext();
        enrollment= it1.next();
        Assert.assertEquals("workerId11",enrollment.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.EXPERT, enrollment.getWorker().getLevel());

        ////
        //// jobOfferIdB1
        ///

        response5 = cttCompaniesJobs.signUpJobOffer("workerId12", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.SUBSTITUTE, response5);
        Assert.assertEquals(5, jobOfferIdA1.getNumWorkers());
        Assert.assertEquals(4, jobOfferIdA1.getNumSubstitutes());

        response5 = cttCompaniesJobs.signUpJobOffer("workerId14", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.SUBSTITUTE, response5);
        Assert.assertEquals(5, jobOfferIdA1.getNumWorkers());
        Assert.assertEquals(5, jobOfferIdA1.getNumSubstitutes());

        it1 = cttCompaniesJobs.getSubstitutesByJobOffer("jobOfferIdA1");

        it1.hasNext();
        enrollment = it1.next();
        Assert.assertEquals("workerId9",enrollment.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.BEFINNER, enrollment.getWorker().getLevel());

        it1.hasNext();
        enrollment = it1.next();
        Assert.assertEquals("workerId10",enrollment.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.BEFINNER, enrollment.getWorker().getLevel());

        it1.hasNext();
        enrollment= it1.next();
        Assert.assertEquals("workerId14",enrollment.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.INTERN, enrollment.getWorker().getLevel());

        it1.hasNext();
        enrollment= it1.next();
        Assert.assertEquals("workerId11",enrollment.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.EXPERT, enrollment.getWorker().getLevel());

        it1.hasNext();
        enrollment= it1.next();
        Assert.assertEquals("workerId12",enrollment.getWorker().getId());
        Assert.assertEquals(CTTCompaniesJobsPR2.Level.EXPERT, enrollment.getWorker().getLevel());

    }

    @Test
    public void getRoomsWithoutEmployeesTest() throws DSException {
        Assert.assertThrows(NoRoomsException.class, () ->
                cttCompaniesJobs.getRoomsWithoutEmployees());

        addAssignEmployeeTest();

        Assert.assertEquals(4, cttCompaniesJobs.numRooms());
        Iterator<Room> it = cttCompaniesJobs.getRoomsWithoutEmployees();
        it.hasNext();
        Room room = it.next();
        Assert.assertEquals("room3", room.getRoomId());

        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void best5EquippedRoomsTest() throws DSException {
        Assert.assertThrows(NoRoomsException.class, () ->
                cttCompaniesJobs.best5EquippedRooms());

        assignEquipmentTest();

        Iterator<Room> it = cttCompaniesJobs.best5EquippedRooms();

        Room room = it.next();
        Assert.assertEquals("room1", room.getRoomId());
        Assert.assertEquals(5, room.numEquipments());

        room = it.next();
        Assert.assertEquals("room2", room.getRoomId());
        Assert.assertEquals(2, room.numEquipments());

    }

}
