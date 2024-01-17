package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.Employee;
import uoc.ds.pr.util.DateUtils;

import java.time.LocalDate;

public class CTTCCompaniesJobsPR2PlusTest extends CTTCCompaniesJobsPR2Test {


    @Before
    public void setUp() throws Exception {

        cttCompaniesJobs = FactoryCTTCCompaniesJobs.getCTTCompaniesJobs();

    }

    @After
    public void tearDown() {
        cttCompaniesJobs=null;
    }

    @Test
    public void addFollowerTest() throws DSException {
        addEmployeeTest();

        cttCompaniesJobs.addFollower("334545445A","3811123A");
        cttCompaniesJobs.addFollower("12122145A","3811123A");
        cttCompaniesJobs.addFollower("439452145T", "3811123A");
        cttCompaniesJobs.addFollower("12365232F","3811123A");

        cttCompaniesJobs.addFollower("12122145A", "334545445A");
        cttCompaniesJobs.addFollower("439452145T","334545445A");

        cttCompaniesJobs.addFollower("334545445A","12122145A");
        cttCompaniesJobs.addFollower("439452145T", "12122145A");
        cttCompaniesJobs.addFollower("12365232F", "12122145A");


        Assert.assertEquals(4, cttCompaniesJobs.numFollowers("3811123A"));
        Assert.assertEquals(2, cttCompaniesJobs.numFollowers("334545445A"));
        Assert.assertEquals(3, cttCompaniesJobs.numFollowers("12122145A"));

        Assert.assertEquals(2, cttCompaniesJobs.numFollowings("334545445A"));
        Assert.assertEquals(3, cttCompaniesJobs.numFollowings("439452145T"));
        Assert.assertEquals(2, cttCompaniesJobs.numFollowings("12365232F"));


        Assert.assertThrows(FollowerNotFound.class, () ->
                cttCompaniesJobs.addFollower("XXXXXXXXXX", "334545445A"));

        Assert.assertThrows(FollowedException.class, () ->
                cttCompaniesJobs.addFollower("334545445A", "XXXXXXX"));

    }


    @Test
    public void getFollowersTest() throws DSException {

        addFollowerTest();

        Assert.assertThrows(EmployeeNotFoundException.class, () ->
                cttCompaniesJobs.getFollowers("XXXXXXXXXX"));

        Iterator<Employee> it1 = cttCompaniesJobs.getFollowers("3811123A");

        Assert.assertEquals("334545445A", it1.next().getEmployeeId());
        Assert.assertEquals("12122145A", it1.next().getEmployeeId());
        Assert.assertEquals("439452145T", it1.next().getEmployeeId());
        Assert.assertEquals("12365232F", it1.next().getEmployeeId());
        Assert.assertFalse(it1.hasNext());

        Iterator<Employee> it2 = cttCompaniesJobs.getFollowers("334545445A");
        Assert.assertEquals("12122145A", it2.next().getEmployeeId());
        Assert.assertEquals("439452145T", it2.next().getEmployeeId());
        Assert.assertFalse(it1.hasNext());

        Assert.assertThrows(NoFollowersException.class, () ->
                cttCompaniesJobs.getFollowers("439452145T"));


    }


    @Test
    public void getFollowingsTest() throws DSException {
        addFollowerTest();

        Assert.assertThrows(EmployeeNotFoundException.class, () ->
                cttCompaniesJobs.getFollowings("XXXXXXXXXX"));

        Iterator<Employee> it1 = cttCompaniesJobs.getFollowings("439452145T");

        Assert.assertEquals("3811123A", it1.next().getEmployeeId());
        Assert.assertEquals("334545445A", it1.next().getEmployeeId());
        Assert.assertEquals("12122145A", it1.next().getEmployeeId());
        Assert.assertFalse(it1.hasNext());

        Assert.assertThrows(NoFollowedException.class, () ->
                cttCompaniesJobs.getFollowings("93534145Z"));


    }

    @Test
    public void recommendationTest() throws DSException {
        addRoleTest();

        cttCompaniesJobs.addEmployee("JUAN", "juan", "perez", DateUtils.createLocalDate("03-01-1978"), "R1");
        cttCompaniesJobs.addEmployee("ANA", "Anna", "Garcia", DateUtils.createLocalDate("03-03-1976"), "R1");
        cttCompaniesJobs.addEmployee("ALBERTO", "Alberto", "Garcia", DateUtils.createLocalDate("07-02-1971"), "R1");
        cttCompaniesJobs.addEmployee("PATRICIA", "Patricia", "Fernandez", DateUtils.createLocalDate("13-11-1976"), "R1");
        cttCompaniesJobs.addEmployee("MARTA", "Marta", "perez", DateUtils.createLocalDate("30-01-1963"), "R1");
        cttCompaniesJobs.addEmployee("PEDRO", "Pedro", "Suert", DateUtils.createLocalDate("30-01-1953"), "R1");
        cttCompaniesJobs.addEmployee("PABLO", "Pablo", "Marmol", DateUtils.createLocalDate("30-10-1951"), "R1");


        cttCompaniesJobs.addFollower("JUAN", "ANA");
        cttCompaniesJobs.addFollower("JUAN", "ALBERTO");
        cttCompaniesJobs.addFollower("ANA", "PATRICIA");
        cttCompaniesJobs.addFollower("ALBERTO", "MARTA");
        cttCompaniesJobs.addFollower("ALBERTO", "PEDRO");
        cttCompaniesJobs.addFollower("PATRICIA", "PABLO");

        Assert.assertThrows(EmployeeNotFoundException.class, () ->
                cttCompaniesJobs.recommendations("XXXXXXXX"));


        Iterator<Employee> it =  cttCompaniesJobs.recommendations("JUAN");
        Assert.assertEquals("PATRICIA", it.next().getEmployeeId());
        Assert.assertEquals("MARTA", it.next().getEmployeeId());
        Assert.assertEquals("PEDRO", it.next().getEmployeeId());
        Assert.assertFalse(it.hasNext());


        Assert.assertThrows(NoFollowedException.class, () ->
                cttCompaniesJobs.recommendations("MARTA"));

    }

    @Test
    public void getUnfollowedColleaguesTest() throws DSException {
        addAssignEmployeeTest();
        cttCompaniesJobs.addFollower("334545445A","3811123A");
        cttCompaniesJobs.addFollower("12122145A","3811123A");
        cttCompaniesJobs.addFollower("439452145T", "3811123A");
        cttCompaniesJobs.addFollower("12365232F","3811123A");

        cttCompaniesJobs.addFollower("12122145A", "334545445A");
        cttCompaniesJobs.addFollower("439452145T","334545445A");

        cttCompaniesJobs.addFollower("334545445A","12122145A");
        cttCompaniesJobs.addFollower("439452145T", "12122145A");
        cttCompaniesJobs.addFollower("12365232F", "12122145A");

        cttCompaniesJobs.addFollower("3811123A","439452145T");


        Assert.assertThrows(EmployeeNotFoundException.class, () ->
                cttCompaniesJobs.getUnfollowedColleagues("Pepito"));

        Iterator<Employee> it1 = cttCompaniesJobs.getFollowings("3811123A");
        Assert.assertEquals("439452145T", it1.next().getEmployeeId());
        Assert.assertFalse(it1.hasNext());


        Iterator<Employee> it = cttCompaniesJobs.getUnfollowedColleagues("3811123A");
        Employee employee1 = it.next();
        Assert.assertEquals("12122145A", employee1.getEmployeeId());

        Employee employee2 = it.next();
        Assert.assertEquals("67122145A", employee2.getEmployeeId());


        Employee employee3 = it.next();
        Assert.assertEquals("89333214B", employee3.getEmployeeId());

        Employee employee4= it.next();
        Assert.assertEquals("76239045G", employee4.getEmployeeId());

        Employee employee5= it.next();
        Assert.assertEquals("67322245Z", employee5.getEmployeeId());

        Employee employee6= it.next();
        Assert.assertEquals("12365232F", employee6.getEmployeeId());
        Assert.assertFalse(it.hasNext());

    }
}