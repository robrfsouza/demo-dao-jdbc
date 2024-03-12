package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao dao = DaoFactory.createSellerDao();

		System.out.println("=== Test 1: seller findById ===");
		Seller seller = dao.findById(3);
		System.out.println(seller);

		System.out.println("\n=== Test 2: seller findByDepartment ===");
		Department department = new Department(2, null);
		List<Seller> lista = dao.findByDepartment(department);

		for (Seller sel : lista) {
			System.out.println(sel);
		}
		
		System.out.println("\n=== Test 3: seller findAll ===");
		lista = dao.findAll();

		for (Seller sel : lista) {
			System.out.println(sel);
		}

		System.out.println("\n=== Test 4: seller insert ===");
		Seller insertSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		dao.insert(insertSeller);
		System.out.println("Inserted new id: " + insertSeller.getId());

		System.out.println("\n=== Test 5: seller update ===");
		seller = dao.findById(1);
		seller.setName("Martha Wayne");
		seller.setEmail("marthawayne@gmail.com");
		dao.update(seller);
		System.out.println("update completed");
	}
}
