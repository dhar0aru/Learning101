/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.services;

import com.exavalu.models.Employee;
import com.exavalu.utils.JDBCConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Avijit Chattopadhyay
 */
public class EmployeeService {

    public static EmployeeService employeeService = null;

    public static EmployeeService getInstance() {
        if (employeeService == null) {
            return new EmployeeService();
        } else {
            return employeeService;
        }
    }

    public ArrayList getAllEmployees() {
        ArrayList empList = new ArrayList();
        String sql = "SELECT  employeeId,firstname,lastname,gender,age,address,phone,departmentName,roleName,basicSalary,carAllowance FROM employees LEFT JOIN roles On employees.roleId= roles.roleId LEFT JOIN departments On employees.deptId= departments.departmentId";
        try {
            Connection con = JDBCConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);

            while (rs.next()) {
                Employee emp = new Employee();

                emp.setEmployeeId(rs.getString("employeeId"));
                emp.setFirstName(rs.getString("firstname"));
                emp.setLastName(rs.getString("lastname"));
                emp.setGender(rs.getString("gender"));
                emp.setAge(rs.getString("age"));
                emp.setPhone(rs.getString("address"));
                emp.setPhone(rs.getString("phone"));
                emp.setDepName(rs.getString("departmentName"));
                emp.setRoleName(rs.getString("roleName"));
                emp.setSalary(rs.getString("basicSalary"));
                emp.setAllowance(rs.getString("carAllowance"));

                empList.add(emp);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.err.println("Total rows:" + empList.size());
        return empList;
    }

    public boolean editEmployee(Employee emp) {
        boolean result = false;
        try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "UPDATE employeedb.employees\n"
                    + "SET firstname = ? , lastname = ? , phone = ? ,\n"
                    + "gender = ? , age = ? , basicSalary = ?,carAllowance = ?,deptId=?,roleId=?\n"
                    + "WHERE employeeId = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, emp.getFirstName());
            preparedStatement.setString(2, emp.getLastName());
            preparedStatement.setString(3, emp.getPhone());

            preparedStatement.setString(4, emp.getGender());
            preparedStatement.setString(5, emp.getAge());
            preparedStatement.setDouble(6, Double.parseDouble(emp.getSalary()));
            preparedStatement.setDouble(7, Double.parseDouble(emp.getAllowance()));
            preparedStatement.setString(8, emp.getDepId());
            preparedStatement.setString(9, emp.getRoleId());

            preparedStatement.setString(10, emp.getEmployeeId());
            System.out.println(preparedStatement);
            int row = preparedStatement.executeUpdate();

            if (row == 1) {
                result = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public Employee getEmployee(String Id) {
        Employee emp = new Employee();
        try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "select * from employees e, departments d, roles r "
                    + "where e.deptId=d.departmentId and e.roleId=r.roleId and e.employeeId =?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, Id);

            ResultSet rs = preparedStatement.executeQuery();
            System.out.print(preparedStatement);
            while (rs.next()) {
                emp.setEmployeeId(rs.getString("employeeId"));
                emp.setFirstName(rs.getString("firstname"));
                emp.setLastName(rs.getString("lastname"));
                emp.setPhone(rs.getString("phone"));
                emp.setGender(rs.getString("gender"));
                emp.setAge(rs.getString("age"));
                emp.setDepName(rs.getString("departmentName"));
                emp.setRoleName(rs.getString("roleName"));
                emp.setSalary(rs.getString("basicSalary"));
                emp.setAllowance(rs.getString("carAllowance"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return emp;
    }

    public ArrayList searchEmployee(Employee emp) {
        ArrayList empList = new ArrayList<>();
        try {
            Connection con = JDBCConnectionManager.getConnection();
            String sql = "select * from employees e, departments d, roles r where e.deptId=d.departmentId and e.roleId=r.roleId "
                    + "having firstname like ? and lastname like ? and gender like ? and departmentId like ? and e.roleId like ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            System.out.println(emp.getFirstName());
            preparedStatement.setString(1, emp.getFirstName() + "%");
            preparedStatement.setString(2, emp.getLastName() + "%");
            preparedStatement.setString(3, emp.getGender() + "%");
            preparedStatement.setString(4, emp.getDepId() + "%");
            preparedStatement.setString(5, emp.getRoleId() + "%");
            System.out.println("Prepared statement = " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Employee emp1 = new Employee();
                emp1.setEmployeeId(rs.getString("employeeId"));
                emp1.setFirstName(rs.getString("firstname"));
                emp1.setLastName(rs.getString("lastname"));
                emp1.setPhone(rs.getString("phone"));
                emp1.setGender(rs.getString("gender"));
                emp1.setAge(rs.getString("age"));
                emp1.setDepId(rs.getString("departmentId"));
                emp1.setDepName(rs.getString("departmentName"));

                emp1.setRoleId(rs.getString("roleId"));
                emp1.setRoleName(rs.getString("roleName"));

                emp1.setSalary(rs.getString("basicSalary"));
                emp1.setAllowance(rs.getString("carAllowance"));

                empList.add(emp1);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return empList;

    }

    public boolean addnewEmployee(Employee emp) {
        boolean result = false;

        try {
            Connection con = JDBCConnectionManager.getConnection();

            String sql = "INSERT INTO employees (firstname,lastname,gender,age,address,phone,deptId,roleId,basicSalary,carAllowance)"
                    + "VALUES(?,?, ? ,? ,? ,?,? ,? ,? )";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, emp.getFirstName());
            preparedStatement.setString(2, emp.getLastName());         

            preparedStatement.setString(4, emp.getGender());
            preparedStatement.setString(5, emp.getAge());
            preparedStatement.setString(3, emp.getAddress());
            preparedStatement.setString(3, emp.getPhone());
            preparedStatement.setDouble(8, Double.parseDouble(emp.getSalary()));
            preparedStatement.setDouble(9, Double.parseDouble(emp.getAllowance()));
            preparedStatement.setString(6, emp.getDepId());
            preparedStatement.setString(7, emp.getRoleId());
            System.out.println(preparedStatement);
            int row = preparedStatement.executeUpdate();

            if (row == 1) {
                result = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean deleteEmployee(String id) {
        boolean result = false;
        try {
            Connection con = JDBCConnectionManager.getConnection();

            String sql = "Delete From employees WHERE employeeId = ?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, id);
            System.out.println(preparedStatement);
            int row = preparedStatement.executeUpdate();
            if (row != 0) {
                result = true;
            }

        } catch (SQLException ex) {

        }
        return result;

    }

}
