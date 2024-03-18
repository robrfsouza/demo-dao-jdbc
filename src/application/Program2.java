package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class Program2 {
	
	public static void main(String[] args) {
		
		DepartmentDao dao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== Test 1: department findById ===");
		Department department = dao.findById(1);
		System.out.println(department);
		
		System.out.println("\n=== Test 2: seller findAll ===");
		List<Department> lista = dao.findAll();
		for (Department dep : lista) {
			System.out.println(dep);
		}
		
		System.out.println("\n=== Test 3: seller insert ===");
		Department insertDepartment = new Department(null, "Music");
		dao.insert(insertDepartment);
		System.out.println("Inserted new id: " + insertDepartment.getId());

		System.out.println("\n=== Test 4: seller update ===");
		department = dao.findById(1);
		department.setName("Food");
		dao.update(department);
		System.out.println("updated complete");
		
		System.out.println("\n=== Test 5: seller delete ===");
		System.out.println("Enter id for delete test: ");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		dao.deleteById(id);
		
		sc.close();



	}

}
