package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		
		String sql = "insert into department (Name) values (?)";
		PreparedStatement stm = null;
		
		try {
			stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stm.setString(1, department.getName());
			
			int rowsAffected =stm.executeUpdate();
			
			if(rowsAffected!=0) {
				ResultSet rs = stm.getGeneratedKeys();

				if(rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}
				
				DB.closeResultSet(rs);
			}
			
			else {
				throw new DbException ("Unexpected error! No rows affected!");
			}
			
		} catch (SQLException e) {
			throw new DbException("Error to insert department: "+e.getMessage());
		}
		finally {
			DB.closeStatement(stm);
		}
		
	}

	@Override
	public void update(Department department) {
		String sql = "update department set Name = ? where id =?";
		PreparedStatement stm = null;
		
		try {
			stm = conn.prepareStatement(sql);
			
			stm.setString(1, department.getName());
			stm.setInt(2, department.getId());
			
			stm.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatement(stm);
		}
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "delete from department where id =?";
		PreparedStatement stm = null;
		
		try {
			stm = conn.prepareStatement(sql);
			
			stm.setInt(1, id);
			
			
			int rows = stm.executeUpdate();
			
			if (rows ==0) {
				System.out.println("id inexistente");
			}
			else {
				System.out.println("excluído com sucesso!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatement(stm);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		
		String sql = "select * from department where id=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sql);
			stm.setInt(1, id);
			rs = stm.executeQuery();
			
			while (rs.next()) {
				Department department = instantiateDepartment(rs);
				
							
				return department; 
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		return null;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		Department department = new Department();
		
		department.setId(rs.getInt("Id"));
		department.setName(rs.getString("Name"));
		return department;
	}

	@Override
	public List<Department> findAll() {

		String sql ="select * from department order by name";
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try {

			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			
			List<Department> lista = new ArrayList<>();
			
			while(rs.next()) {
				Department department = instantiateDepartment(rs);
				
				lista.add(department);
			}
			return lista;
			
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(stm);
			DB.closeResultSet(rs);
		}
		
	}

}
