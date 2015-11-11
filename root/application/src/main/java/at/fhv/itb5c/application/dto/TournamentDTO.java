package at.fhv.itb5c.application.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class TournamentDTO extends BaseDTO {
	private String _name;
	private Date _persistDate;
	private Double _fee;
	private Set<Long> _homeTeams;
	private Set<String> _guestTeams;
	private Set<Long> _matches;
	private LocalDate _date;
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		_name = name;
	}
	public Date getPersistDate() {
		return _persistDate;
	}
	public void setPersistDate(Date persistDate) {
		_persistDate = persistDate;
	}
	public Double getFee() {
		return _fee;
	}
	public void setFee(Double fee) {
		_fee = fee;
	}
	public Set<Long> getHomeTeamsIds() {
		return _homeTeams;
	}
	public void setHomeTeamsIds(Set<Long> homeTeams) {
		_homeTeams = homeTeams;
	}
	public Set<String> getGuestTeams() {
		return _guestTeams;
	}
	public void setGuestTeams(Set<String> guestTeams) {
		_guestTeams = guestTeams;
	}
	public Set<Long> getMatchesIds() {
		return _matches;
	}
	public void setMatchesIds(Set<Long> matches) {
		_matches = matches;
	}
	public LocalDate getDate() {
		return _date;
	}
	public void setDate(LocalDate date) {
		_date = date;
	}
}
