package operation;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import data.Employee;

public class manageEmployee {
	
	public static SessionFactory factory;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Configuration configuration = new Configuration(); 
			factory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();
			
			//factory = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed to create sessionfactory object" + e);
			
			throw new ExceptionInInitializerError(e);
			
		}
		
		manageEmployee me = new manageEmployee();
		Integer empID1 = me.addEmployee("ak", "ak", 1);
		Integer empID2 = me.addEmployee("tt", "tt", 1);
		Integer empID3 = me.addEmployee("rr", "rr", 1);
		Integer empID4 = me.addEmployee("jj", "jj", 1);
		
		me.listEmployee();
		
		//me.updateEmployee(empID4, 2);
		
		//me.deleteEmployee(empID4);
		
		//me.listEmployee();
	}
	
	public Integer addEmployee(String fname,String lname,int salary) {
		
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeId = null;
		
		try {
			
			tx = session.beginTransaction();
			Employee e = new Employee(fname, lname, salary);
			employeeId = (Integer) session.save(e);
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		
		return employeeId;
	}
	
	public void listEmployee() {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			List<Employee> employees = session.createQuery("From Employee").list();
			for(Iterator itr = employees.iterator(); itr.hasNext();) {
				Employee emp = (Employee) itr.next();
				System.out.println("First Name: " + emp.getFirstName());
				System.out.println("Last Name:" + emp.getLastName());
				System.out.println("Salary: " + emp.getSalary());
			}
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
	}
	
	public void updateEmployee(Integer employeeID,int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			Employee e = session.get(Employee.class, employeeID);
			e.setSalary(salary);
			session.update(e);
		} catch (Exception e) {
			// TODO: handle exception
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
	}
	
	public void deleteEmployee(Integer employeeID) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			Employee e = session.get(Employee.class, employeeID);
			session.delete(e);
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
	}
}
