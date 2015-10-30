package at.fhv.itb5c.commons.dto.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Set;

import at.fhv.itb5c.commons.dto.IUser;
import at.fhv.itb5c.commons.enums.Gender;
import at.fhv.itb5c.commons.enums.TypeOfSport;
import at.fhv.itb5c.commons.enums.UserRole;

public interface IUserRMI extends Remote, IUser {
	public String getFirstName() throws RemoteException;

	public void setFirstName(String firstName) throws RemoteException;

	public String getLastName() throws RemoteException;

	public void setLastName(String lastName) throws RemoteException;

	public String getEmail() throws RemoteException;

	public void setEmail(String email) throws RemoteException;

	public String getTelephoneNumber() throws RemoteException;

	public void setTelephoneNumber(String telephoneNumber) throws RemoteException;

	public Gender getGender() throws RemoteException;

	public void setGender(Gender gender) throws RemoteException;

	public String getAddress() throws RemoteException;

	public void setAddress(String address) throws RemoteException;

	public LocalDate getDateOfBirth() throws RemoteException;

	public void setDateOfBirth(LocalDate dateOfBirth) throws RemoteException;

	public double getMembershipFee() throws RemoteException;

	public void setMembershipFee(double membershipFee) throws RemoteException;

	public Set<UserRole> getRoles() throws RemoteException;

	public void setRoles(Set<UserRole> roles) throws RemoteException;

	public Set<TypeOfSport> getTypeOfSports() throws RemoteException;

	public void setTypeOfSports(Set<TypeOfSport> typeOfSports) throws RemoteException;

	public long getId() throws RemoteException;

	public long getVersion() throws RemoteException;

	public void setId(long id);

	public void setVersion(long version);
}
