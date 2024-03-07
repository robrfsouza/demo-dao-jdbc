package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC (Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {

		String sql = "Select seller.*, department.Name as DepName "
				+ "from seller inner join department "
				+ "on seller.DepartmentId = department.Id "
				+ "where seller.Id = ?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try {
			stm = conn.prepareStatement(sql);
			stm.setInt(1, id);
			rs = stm.executeQuery();
			
			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				
				Seller seller = instantiateSeller (rs, department);
				
				return seller;
			}
			else
				return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(stm);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		
		Seller seller = new Seller();
		
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartment(department);
		
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
	
		Department department = new Department();
		
		department.setId(rs.getInt("DepartmentId"));
		department.setName(rs.getString("DepName"));
		
		return department;
	}

	@Override
	public List<Seller> findAll() {

		String sql = "Select seller.*, department.Name as DepName "
				+ "from seller inner join department "
				+ "on seller.DepartmentId = department.Id "
				+ "order by Name";
		PreparedStatement stm = null;
		ResultSet rs = null;
	
		try {
			stm = conn.prepareStatement(sql);
			rs=stm.executeQuery();
			
			List<Seller> lista = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep= instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(rs, dep);
				
				lista.add(seller);
			}
			return lista;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			// TODO: handle exception
		}
		finally {
			DB.closeStatement(stm);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		String sql = "Select seller.*, department.Name as DepName "
				+ "from seller inner join department "
				+ "on seller.DepartmentId = department.Id "
				+ "where  DepartmentId = ? "
				+ "order by Name";
		PreparedStatement stm = null;
		ResultSet rs = null;
	
		try {
			stm = conn.prepareStatement(sql);
			stm.setInt(1, department.getId());
			rs=stm.executeQuery();
			
			List<Seller> lista = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep= instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(rs, dep);
				
				lista.add(seller);
			}
			return lista;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			// TODO: handle exception
		}
		finally {
			DB.closeStatement(stm);
			DB.closeResultSet(rs);
		}
	}

}
