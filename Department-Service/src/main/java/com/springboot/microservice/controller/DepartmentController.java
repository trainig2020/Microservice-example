package com.springboot.microservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.microservice.model.Department;
import com.springboot.microservice.model.Employee;
import com.springboot.microservice.model.ListOfDepartments;
import com.springboot.microservice.model.ListOfEmployees;
import com.springboot.microservice.service.DepartmentService;
import com.springboot.microservice.service.RemoteEmployeeService;

@RestController
@RequestMapping("/Department")
public class DepartmentController {
    
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private RemoteEmployeeService remoteEmployeeService;
	
	@Value("${message}")
	private String message;
	
	@RequestMapping("/message")
	public String getMess() {
		return message;
	}
	
	@RequestMapping("/GetAll")
	public ListOfDepartments getAllDepartments(){
		List<Department> lst = departmentService.getAllDepartments();
		ListOfDepartments listOfDepartments = new ListOfDepartments();
		listOfDepartments.setDeptList(lst);
	  return listOfDepartments;	
	}
	
	@RequestMapping("/{deptid}")
	public Optional<Department> getDepartment(@PathVariable("deptid") int deptid) {
		return departmentService.getDepartment(deptid);
	}
	
	@PostMapping("/AddDepartment")
	public void addDepartment(@RequestBody Department department) {
		departmentService.addDepartment(department);
	}
	
	@PutMapping("/updateDepartment/{deptid}")
	public void updateDepartment(@RequestBody Department department, @PathVariable int deptid) {
		department.setDeptid(deptid);
		departmentService.updateDepartment(department);
	}
	
	@DeleteMapping("/DeleteDepartment/{deptid}")
	public void deleteDepartment(@PathVariable int deptid) {
		departmentService.deleteDepartment(deptid);
		remoteEmployeeService.deleteEmployee(deptid);
	}
	
	@GetMapping("{edid}/employees")
	public ListOfEmployees getAllEmployees(@PathVariable int edid){
	   ListOfEmployees lst =remoteEmployeeService.getAllEmployees(edid);
	   System.out.println(lst.getListOfEmployee().size());
	   return lst;
	}
	
	@GetMapping("/employess/{empid}")
	public Employee getEmployee(@PathVariable int empid) {
		return remoteEmployeeService.getEmployee(empid);
	}
	
	@PostMapping("/employees/{edid}/addEmployee")
	public void addEmployee(@RequestBody Employee employee , @PathVariable int edid) {
		employee.setEdid(edid);
		remoteEmployeeService.addEmployee(employee,edid);
	}
	
	@PutMapping("/employees/{edid}/updateEmployee/{empid}")
	public void updateEmployee(@RequestBody Employee employee,@PathVariable int edid, @PathVariable int empid) {
		employee.setEdid(edid);
		employee.setEmpid(empid);
		remoteEmployeeService.updateEmployee(employee,edid,empid);
	}
	@DeleteMapping("/employees/deleteEmployee/{edid}")
	public void deleteEmployee(@PathVariable int edid) {
		remoteEmployeeService.deleteEmployee(edid);
	}
	@DeleteMapping("/employees/{edid}/deleteEmployee/{empid}")
	public void deleteSingleEmployee(@PathVariable int edid,@PathVariable int empid) {
		remoteEmployeeService.deleteSingleEmployee(edid, empid);
	}
	
}
