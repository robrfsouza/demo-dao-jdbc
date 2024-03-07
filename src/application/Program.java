package application;

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
	}
}
