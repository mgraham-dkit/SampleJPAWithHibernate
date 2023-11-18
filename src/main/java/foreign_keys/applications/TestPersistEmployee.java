package foreign_keys.applications;

import foreign_keys.daos.DepartmentDao;
import foreign_keys.daos.DepartmentDaoInterface;
import foreign_keys.daos.EmployeeDao;
import foreign_keys.daos.EmployeeDaoInterface;
import foreign_keys.entities.Address;
import foreign_keys.entities.Department;
import foreign_keys.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class TestPersistEmployee {
    public static List<Department> bootstrapDepartments(DepartmentDaoInterface deptDao) {
        String[] departmentNames = {"Human Resources", "Finance", "Sales", "Security", "IT"};
        List<Department> departments = deptDao.getAllDepartments();
        if (departments.size() < 5) {
            for (String name : departmentNames) {
                Department department = new Department(name);
                if (!departments.contains(department)) {
                    if (deptDao.save(department)) {
                        departments.add(department);
                    }
                }
            }
        }

        return departments;
    }

    public static List<Address> bootstrapAddresses(int count) {
        List<Address> addresses = new ArrayList<>();
        if (count > 0) {
            String [] streetNames = {"Elmview", "Outlook", "Mill View", "Sea Point"};
            String [] streetTypes = {"Street", "Way", "Lane", "Cove", "Road"};
            String [] counties = {"Dublin", "Louth", "Mayo", "Meath", "Wicklow", "Donegal"};
            String [] postcodeStems = {"K11", "A38", "M56", "Z22"};
            String [] postcodeEnds = {"BN22", "VU29", "MB29", "RX88"};
            int maxNum = 100;
            Random rand = new Random();

            for (int i = 0; i < count; i++) {
                int houseNum = rand.nextInt(maxNum);
                int nameIndex = rand.nextInt(streetNames.length);
                int typeIndex = rand.nextInt(streetTypes.length);
                String line1 = houseNum + " " + streetNames[nameIndex] + " " + streetTypes[typeIndex];

                String line2 = null;
                if(rand.nextBoolean()){
                    line2 = "Fingal";
                }

                String line3 = null;
                if(rand.nextBoolean()){
                    int countyIndex = rand.nextInt(counties.length);
                    line3 = counties[countyIndex];
                }

                int stemIndex = rand.nextInt(postcodeStems.length);
                int endIndex = rand.nextInt(postcodeEnds.length);
                String postcode = postcodeStems[stemIndex] + " " + postcodeEnds[endIndex];

                Address a = new Address(line1, line2, line3, postcode, "Ireland", null);
                addresses.add(a);
            }
        }
        return addresses;
    }

    public static List<Employee> bootstrapEmployees(EmployeeDaoInterface employeeDao, List<Department> departments,List<Address> addresses) {
        List<Employee> employees = employeeDao.getAllEmployees();
        if(employees.size() < 5){
            String [] firstNames = {"Henry", "Helga", "Helen", "Horace", "Honour", "Hephzibah"};
            String [] lastNames = {"Andrews", "Bacon", "Chillaw", "Dunderson", "Eliege", "Flounderson", "Gartner"};

            Random rand = new Random();
            while(employees.size() < 5) {
                int fNameIndex = rand.nextInt(firstNames.length);
                int lNameIndex = rand.nextInt(lastNames.length);
                String first = firstNames[fNameIndex];
                String last = lastNames[lNameIndex];
                String email = first + "." + last + "@workdomain.com";

                int deptIndex = rand.nextInt(departments.size());
                Department dept = departments.get(deptIndex);

                int addressIndex = rand.nextInt(addresses.size());
                Address address = addresses.get(addressIndex);

                Employee employee = new Employee(email, first, last, LocalDate.now(), dept, address);
                address.setResident(employee);
                if(dept.addStaff(employee)) {
                    if (employeeDao.save(employee)) {
                        employees.add(employee);
                    }
                }
            }
        }

        return employees;
    }

    public static void main(String[] args) {
        // Hide info messages about hibernate activities
        // Can also hide warnings by setting level to Level.SEVERE
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager empEntityManager = entityManagerFactory.createEntityManager();
        EntityManager deptEntityManager = entityManagerFactory.createEntityManager();
        DepartmentDaoInterface deptDao = new DepartmentDao(deptEntityManager);
        EmployeeDaoInterface dao = new EmployeeDao(empEntityManager);

        // Create addresses to use with bootstrapped Employees (if we need them)
        List<Address> sampleAddresses = bootstrapAddresses(5);
        // Get all departments, creating more and persisting them to the database if there is an insufficient number
        List<Department> departments = bootstrapDepartments(deptDao);
        // Get all employees, creating more and persisting them to the database if there is an insufficient number
        List<Employee> employees = bootstrapEmployees(dao, departments, sampleAddresses);

        printList(sampleAddresses);
        printList(departments);
        printList(employees);

        // Shutdown process - close all entity managers and the entity manager factory
        empEntityManager.close();
        deptEntityManager.close();
        entityManagerFactory.close();
    }

    public static void printList(List list){
        System.out.println("*****************");
        for (Object o : list) {
            System.out.println(o);
            System.out.println("-----------------");
        }
        System.out.println("*****************");
    }
}
