package uoc.ds.pr;

import edu.uoc.ds.adt.nonlinear.Dictionary;
import edu.uoc.ds.adt.nonlinear.DictionaryAVLImpl;
import edu.uoc.ds.adt.nonlinear.HashTable;
import edu.uoc.ds.adt.nonlinear.graphs.DirectedGraph;
import edu.uoc.ds.adt.nonlinear.graphs.DirectedGraphImpl;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.DSArray;
import uoc.ds.pr.util.OrderedVector;
import uoc.ds.pr.util.QueueLinkedList;

import java.time.LocalDate;
import java.util.Arrays;


public class CTTCompaniesJobsPR2Impl implements CTTCompaniesJobsPR2 {

    /////////
    // PR1 //
    /////////
    //private DSArray<Worker> workers;
    //private DSArray<Company> companies;

    private Queue<Request> requests;

   // private DSArray<JobOffer> jobOffers;
    private int numRejectedRequests;

    private OrderedVector<JobOffer> bestJobOffer;
    private Worker mostActiveWorker;

    /////////
    // PR2 //
    /////////
    private Role[] roles;
    private int numRole;
    private int numRooms;
    private int numEquipments;
    private int numEmployees;
    private int numRoomsByEmployee;
    private Dictionary<String, Employee> employee;
    private Dictionary<String, Room> rooms;
    private DirectedGraph<Equipment,String> graph;
    private Dictionary<String, Equipment> equipments; //AVL
    private Dictionary<String,Worker> workers;
    private Dictionary<String,JobOffer> jobOffers;
    private Dictionary<String, Company> companies;
    private OrderedVector<Room> best5EquippedRooms;

    public CTTCompaniesJobsPR2Impl(){
        /////////
        // PR1 //
        /////////

        //workers = new DSArray<>(MAX_NUM_WORKERS);
        //companies = new DSArray<>(MAX_NUM_COMPANIES);
        requests = new QueueLinkedList<>();
        //jobOffers = new DSArray<>(MAX_NUM_JOBOFFERS);
        bestJobOffer = new OrderedVector<>(MAX_NUM_JOBOFFERS, JobOffer.CMP_V);
        numRejectedRequests = 0;
        mostActiveWorker = null;
        /////////
        // PR2 //
        /////////
        workers = new DictionaryAVLImpl<>();
        roles = new Role[MAX_NUM_ROLES];
        employee = new HashTable<>();
        rooms = new HashTable<>();
        equipments = new DictionaryAVLImpl<>();
        graph = new DirectedGraphImpl<>();
        jobOffers = new DictionaryAVLImpl<>();
        companies = new HashTable<>();
        best5EquippedRooms = new OrderedVector<>(MAX_BEST5_EQUIPPEMENT, Room.CMP_V);
    }

    /////////
    // PR2 //
    /////////
    @Override
    public void addRole(String roleId, String description) {
        Role role = getRole(roleId);
        if (role != null){
            role.setDescription(description);
            role.setRoleId(roleId);
        }
        else{
            role = new Role(roleId, description);
            roles[numRole] = role;
            numRole++;
        }
    }

    @Override
    public void addEmployee(String employeeId, String name, String surname, LocalDate localDate, String role) {
        Employee employee = getEmployee(employeeId);
        Role roleob = getRole(role);
        if (employee != null){
            if (!employee.getRole().getRoleId().equals(role)) {
                employee.getRole().removeEmployee(employee);
                employee.addRole(roleob);
            }
            employee.setName(name);
            employee.setSurname(surname);
            employee.setBirthday(localDate);
            employee.setRole(roleob);
        }
        else{
            employee = new Employee(employeeId, name, surname, localDate, roleob);
            roleob.addEmployee(employee);
            this.employee.put(employeeId,employee);
            numEmployees++;
        }
    }

    @Override
    public void addRoom(String roomId, String name, String description, RoomType roomtype) {
        Room room = getRoom(roomId);
        if (room != null) {
            room.setName(name);
            room.setDescription(description);
        } else {
            room = new Room(roomId, name, description, roomtype);

            this.rooms.put(roomId, room);
            numRooms++;
        }
    }

    @Override
    public void assignEmployee(String employeeId, String roomId) throws EmployeeAlreadyAssignedException, EmployeeNotFoundException, RoomNotFoundException {
        Room room = getRoom(roomId);
        if (room == null) {
            throw new RoomNotFoundException();
        }

        Employee employee = getEmployee(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException();
        }

        if (room.existEmployee(employee)) {
            throw new EmployeeAlreadyAssignedException();
        }
        room.addEmployee(employee);
    }

    @Override
    public Iterator<Employee> getEmployeesByRoom(String roomId) throws RoomNotFoundException, NOEmployeeException {
        Room room = getRoom(roomId);
        if (room==null){
            throw new RoomNotFoundException();
        }
        List<Employee> list = room.getAllEmployees();
        if (list.isEmpty()){
            throw new NOEmployeeException();
        }
        Iterator <Employee> it = list.values();
        return it;
    }

    @Override
    public Iterator<Employee> getEmployeesByRole(String roleId) throws NOEmployeeException {
        Role role = getRole(roleId);

        Iterator <Employee> it = role.employees();
        if(!it.hasNext()){
            throw new NOEmployeeException();
        }
        return it;
    }

    @Override
    public void addEquipment(String equipmentId, String name, String description) {
        Equipment equipment = getEquipment(equipmentId);
        if (equipment != null) {
            equipment.setName(name);
            equipment.setDescription(description);
        } else {
            equipment = new Equipment(equipmentId, name, description);

            this.equipments.put(equipmentId, equipment);
            numEquipments++;
        }
    }

    @Override
    public AssignEquipmentResponse assignEquipment(String equipmentId, String roomId) throws EquipmentNotFoundException, RoomNotFoundException, EquipmentAlreadyAssignedException {
        Boolean newRoom;
        Equipment equipment = getEquipment(equipmentId);
        if (equipment == null){
            throw new EquipmentNotFoundException();
        }
        Room room = getRoom(roomId);
        if (room == null){
            throw new RoomNotFoundException();
        }
        if (room.existEquipment(equipment)){
            throw new EquipmentAlreadyAssignedException();
        }
        room.addEquipment(equipment);

        //room.removeEquipment( room.getAllEquipments(), equipment );
        if (equipment.getRoomAssigned()==room){
            newRoom = false;
        }
        else{//está asignado a otra sala
            newRoom = true;
            if( equipment.getRoomAssigned() != null) {
                equipment.getRoomAssigned().removeEquipment((equipment.getRoomAssigned().getAllEquipments()), equipment);
            }
            equipment.updateRoomAssigned(room);
        }

        if (newRoom) {
            return AssignEquipmentResponse.REASSIGNED;
        }
        else{
            return AssignEquipmentResponse.ASSIGNED;
        }
    }

    @Override
    public Level getLevel(String workerId) throws WorkerNotFoundException {
        Worker worker = getWorker(workerId);
        if (worker == null){
            throw new WorkerNotFoundException();
        }
        return getWorker(workerId).getLevel();
    }

    @Override
    public Iterator<Enrollment> getWorkersByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NoWorkerException {
        JobOffer jobOffer = getJobOffer(jobOfferId);
        if (jobOffer==null){
            throw new JobOfferNotFoundException();
        }
        Iterator<Enrollment> it = jobOffer.enrollments();
        if (!it.hasNext()){
            throw new NoWorkerException();
        }
        return it;
    }

    @Override
    public Iterator<Enrollment> getSubstitutesByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NoWorkerException {
        return null;
    }

    @Override
    public Iterator<Room> getRoomsWithoutEmployees() throws NoRoomsException {
        Iterator<Room> it = this.rooms.values();
        if(!it.hasNext()){
            throw new NoRoomsException();
        }
        List<Room> list;
        list = new LinkedList<>();
        Iterator<Room> it2 = null;
        Room room;
        while(it.hasNext()){
            room = it.next();
            if(room.getAllEmployees().isEmpty()){
                list.insertEnd(room);
            }
        }
        it2 = list.values();
        return it2;
    }

    @Override
    public Iterator<Room> best5EquippedRooms() throws NoRoomsException {
        if(this.rooms.isEmpty()){
            throw new NoRoomsException();
        }

    }

    @Override
    public void addFollower(String followerId, String followedId) throws FollowerNotFound, FollowedException {

    }

    @Override
    public Iterator<Employee> getFollowers(String followedId) throws EmployeeNotFoundException, NoFollowersException, FollowerNotFound {
        return null;
    }

    @Override
    public Iterator<Employee> getFollowings(String followerId) throws EmployeeNotFoundException, NoFollowedException {
        return null;
    }

    @Override
    public Iterator<Employee> recommendations(String followerId) throws EmployeeNotFoundException, NoFollowedException {
        return null;
    }

    @Override
    public Iterator<Employee> getUnfollowedColleagues(String employeeId) throws EmployeeNotFoundException, NOEmployeeException {
        return null;
    }

    @Override
    public int numRoles() {
        return this.numRole;
    }

    @Override
    public int numEmployees() {

        return this.numEmployees;
    }

    @Override
    public int numEmployeesByRole(String roleId) {
        Role role = getRole(roleId);
        if (role != null){
            return role.numEmployees();
        }
        return 0;
    }

    @Override
    public int numRooms() {
        return this.numRooms;
    }

    @Override
    public int numEquipments() {
        return this.numEquipments;
    }

    @Override
    public int numEquipmentsByRoom(String roomId) {
        int num = 0;
        Room room = getRoom(roomId);
        return room.numEquipments();
    }

    @Override
    public int numFollowers(String employeeId) {
        return 0;
    }

    @Override
    public int numFollowings(String employeeId) {
        return 0;
    }

    @Override
    public int numRoomsByEmployee(String employee) {
        int num = 0;
        Employee employee1 = getEmployee(employee);
        Iterator<Room> it = rooms.values();
        while(it.hasNext()){
            if ( it.next().existEmployee(employee1) ){
                num++;
            };
        }
        return num;
    }

    @Override
    public Room whereIs(String equipmentId) {
        Equipment equipment = getEquipment(equipmentId);
        return equipment.getRoomAssigned();
    }

    @Override
    public Role getRole(String role) {
        Role role2 = Arrays.stream(roles)
                .filter(r -> r!=null && r.getRoleId().equals(role))
                .findFirst()
                .orElse(null);

        return role2;
    }

    @Override
    public Employee getEmployee(String employeeId) {

        return employee.get(employeeId);
    }

    @Override
    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    @Override
    public Equipment getEquipment(String equipmentId) {
        Equipment equipment;
        Iterator<Equipment> it = equipments.values();

        while (it.hasNext()) {
            equipment = it.next();
            if  ( (equipment.getEquipmentId()==equipmentId) ){
                return it.next();
            }
        }
        return null;
    }

    ////////
    //PR1//
    ///////
    @Override
    public void addWorker(String id, String name, String surname, LocalDate dateOfBirth, Qualification qualification) {
        Worker worker = getWorker(id);
        if (worker == null) {
            worker = new Worker(id, name, surname, dateOfBirth, qualification);
            this.workers.put(id, worker);
        }
        else {
            worker.update(name, surname, dateOfBirth, qualification);
        }
    }



    @Override
    public void addCompany(String id, String name, String description) {
        Company company = getCompany(id);
        if (company == null) {
            company = new Company(id, name, description);
            this.companies.put(id, company);
        }
        else {
            company.update(name, description);
        }

    }

    public void addRequest(String id, String jobOfferId, String companyId, String description,
                           Qualification minQualification, int maxWorkers, LocalDate startDate,
                           LocalDate endDate) throws CompanyNotFoundException {

        Company company = getCompany(companyId);
        if (company == null) {
            throw new CompanyNotFoundException();
        }

        Request request = new Request(jobOfferId, company, description, minQualification, maxWorkers, startDate, endDate);
        this.requests.add(request);

    }

    public Request updateRequest(Status status, LocalDate date, String description) throws NoRequestException {
        if (requests.isEmpty()) throw new NoRequestException();
        Request request = requests.poll();

        if (request  == null) {
            throw new NoRequestException();
        }

        request.update(status, date, description);
        if (request.isEnabled()) {
            JobOffer jobOffer = request.getJobOffer();

            jobOffers.put(jobOffer.getJobOfferId(), jobOffer);
            Company company = jobOffer.getCompany();
            company.addJobOffer(jobOffer);
        }
        else {
            numRejectedRequests++;
        }
        return request;
    }


    public Response signUpJobOffer(String workerId, String jobOfferId) throws JobOfferNotFoundException, WorkerNotFoundException, WorkerAlreadyEnrolledException {
        Response response = Response.REJECTED;
        Worker worker = getWorker(workerId);
        if (worker == null) {
            throw new WorkerNotFoundException();
        }

        JobOffer jobOffer = getJobOffer(jobOfferId);
        if (jobOffer == null) {
            throw new JobOfferNotFoundException();
        }

        if (worker.isInJobOffer(jobOffer)) {
            throw new WorkerAlreadyEnrolledException();
        }

        if (worker.isInJobOfferAsSubstitute(jobOffer)) {
            throw new WorkerAlreadyEnrolledException();
        }

        if (jobOffer.workerHasMinimumQualification(worker)){
            if (!jobOffer.isfull()) {
                response = Response.ACCEPTED;
                jobOffer.addRegistration(worker, response);
                worker.addJobOffer(jobOffer);
                updateMostActiveWorker(worker);
                response = Response.ACCEPTED;
            }
            else {
                response = Response.SUBSTITUTE;
                jobOffer.addRegistration(worker, response);
            }
        }
        else {
            // Rejected
            response = Response.REJECTED;
        }
        return response;
    }

    private void updateMostActiveWorker(Worker worker) {
        if ((this.mostActiveWorker==null) ||
                (this.mostActiveWorker.getWorkingDays() < worker.getWorkingDays())) {
            this.mostActiveWorker = worker;
        }
    }

    public double getPercentageRejectedRequests() {
        int total = jobOffers.size()+requests.size() + numRejectedRequests;
        return (total!=0?(double) numRejectedRequests / total:0);
    }

    public Iterator<JobOffer> getJobOffersByCompany(String companyId) throws NOJobOffersException {
        Company company = getCompany(companyId);
        Iterator<JobOffer> it = company.getJobOffers();
        if (!it.hasNext()) {
            throw new NOJobOffersException();
        }
        return it;
    }

    public Iterator<JobOffer> getAllJobOffers() throws NOJobOffersException {
        if (jobOffers.size()==0) {
            throw new NOJobOffersException();
        }
        return jobOffers.values();
    }

    public Iterator<JobOffer> getJobOffersByWorker(String workerId) throws NOJobOffersException {
        Worker worker = getWorker(workerId);
        Iterator<JobOffer> it = worker.getJobOffers();

        if (!it.hasNext()) {
            throw new NOJobOffersException();
        }
        return it;
    }

    public void addRating(String workerId, String jobOfferId, int value, String message) throws WorkerNotFoundException, JobOfferNotFoundException, WorkerNOEnrolledException {
        Worker worker = getWorker(workerId);
        if (worker == null) {
            throw new WorkerNotFoundException();
        }

        JobOffer jobOffer = getJobOffer(jobOfferId);
        if (jobOffer == null) {
            throw new JobOfferNotFoundException();
        }

        if (!worker.isInJobOffer(jobOffer)) {
            throw new WorkerNOEnrolledException();
        }

        jobOffer.addRating(value, message, worker);

        updateBestJobOffer(jobOffer);

    }


    public Iterator<Rating> getRatingsByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NORatingsException {
        JobOffer jobOffer = getJobOffer(jobOfferId);

        if (jobOffer == null) {
            throw new JobOfferNotFoundException();
        }

        Iterator<Rating> it = jobOffer.getRatings();

        if (!it.hasNext()) {
            throw new NORatingsException();
        }

        return it;
    }

    public Worker getMostActiveWorker() throws NoWorkerException {
        if (this.mostActiveWorker == null) {
            throw new NoWorkerException();
        }

        return this.mostActiveWorker;
    }

    public JobOffer getBestJobOffer() throws NOJobOffersException {
        if (bestJobOffer.isEmpty()) {
            throw new NOJobOffersException();
        }

        return bestJobOffer.elementAt(0);
    }



    private void updateBestJobOffer(JobOffer jobOffer) {
        bestJobOffer.delete(jobOffer);
        bestJobOffer.update(jobOffer);

    }


    /***********************************************************************************/
    /******************** AUX OPERATIONS  **********************************************/
    /***********************************************************************************/


    public Worker getWorker(String id) {
        return workers.get(id);
    }

    public Company getCompany(String id) {
        return companies.get(id);
    }

    public JobOffer getJobOffer(String jobOfferId) {
        return jobOffers.get(jobOfferId);
    }

    @Override
    public int numWorkers() {
        return this.workers.size();
    }

    @Override
    public int numCompanies() {
        return this.companies.size();
    }

    @Override
    public int numJobOffers() {
        return this.jobOffers.size();
    }

    @Override
    public int numPendingRequests() {
        return this.requests.size();
    }

    @Override
    public int numTotalRequests() {
        return jobOffers.size() + numPendingRequests() + numRejectedRequests;
    }

    public int numRejectedRequests() {
        return numRejectedRequests;
    }
}
