package at.fhv.itb5c.application.dto;

import at.fhv.itb5c.commons.dto.IDepartment;
import at.fhv.itb5c.logging.ILogger;
import at.fhv.itb5c.model.entity.Department;

public class DepartmentConverter implements ILogger {
	public static IDepartment toDTO(Department department){
		if(department == null){
			return null;
		}
		
		IDepartment departmentdto = new DepartmentDTO();
		
		try {
			departmentdto.setId(department.getId());
			departmentdto.setVersion(department.getVersion());
			departmentdto.setName(department.getName());
			departmentdto.setTypeOfSport(department.getTypeOfSport());
			departmentdto.setHead(UserConverter.toDTO(department.getHead()));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return departmentdto;
	}
	
	public static Department toEntity(IDepartment departmentdto){
		if(departmentdto==null){
			return null;
		}
		
		Department department = new Department();
		
		try {
			department.setId(departmentdto.getId());
			department.setVersion(departmentdto.getVersion());
			department.setName(departmentdto.getName());
			department.setTypeOfSport(departmentdto.getTypeOfSport());
			department.setHead(UserConverter.toEntity(departmentdto.getHead()));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return department;
	}
}