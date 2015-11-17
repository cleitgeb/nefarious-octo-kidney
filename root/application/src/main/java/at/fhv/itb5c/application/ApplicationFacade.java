package at.fhv.itb5c.application;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import at.fhv.itb5c.application.converter.ConverterDepartmentDTO;
import at.fhv.itb5c.application.converter.ConverterLeagueDTO;
import at.fhv.itb5c.application.converter.ConverterMatchDTO;
import at.fhv.itb5c.application.converter.ConverterTeamDTO;
import at.fhv.itb5c.application.converter.ConverterTournamentDTO;
import at.fhv.itb5c.application.converter.ConverterUserDTO;
import at.fhv.itb5c.application.dto.DepartmentDTO;
import at.fhv.itb5c.application.dto.LeagueDTO;
import at.fhv.itb5c.application.dto.MatchDTO;
import at.fhv.itb5c.application.dto.TeamDTO;
import at.fhv.itb5c.application.dto.TournamentDTO;
import at.fhv.itb5c.application.dto.UserDTO;
import at.fhv.itb5c.commons.enums.TypeOfSport;
import at.fhv.itb5c.commons.enums.UserRole;
import at.fhv.itb5c.commons.util.auth.LDAPAuth;
import at.fhv.itb5c.commons.util.auth.SessionManager;
import at.fhv.itb5c.logging.ILogger;
import at.fhv.itb5c.model.PersistenceFacade;
import at.fhv.itb5c.model.entity.Department;
import at.fhv.itb5c.model.entity.League;
import at.fhv.itb5c.model.entity.Match;
import at.fhv.itb5c.model.entity.Team;
import at.fhv.itb5c.model.entity.Tournament;
import at.fhv.itb5c.model.entity.User;

public class ApplicationFacade implements ILogger {
	SessionManager _sessionManager;

	public ApplicationFacade() {
		_sessionManager = SessionManager.getInstance();
	}

	public UserDTO createUser(String sessionId) {
		if (hasRole(sessionId, UserRole.Admin)) {
			return ConverterUserDTO.toDTO(new User());
		}
		return null;
	}

	public UserDTO getUserById(String sessionId, Long id) {
		if (hasRole(sessionId, UserRole.StandardUser, UserRole.Admin)) {
			User user = PersistenceFacade.getInstance().getById(User.class, id);
			return ConverterUserDTO.toDTO(user);
		}
		return null;
	}

	/**
	 * If a parameter is null, it is ignored.
	 */
	public Collection<UserDTO> findUsers(String sessionId, String firstName, String lastName, Long departmentId,
			Boolean membershipFeePaid) {
		if (hasRole(sessionId, UserRole.Admin)) {
			return ConverterUserDTO.toDTO(
					PersistenceFacade.getInstance().findUsers(firstName, lastName, departmentId, membershipFeePaid));
		}
		return null;
	}

	/**
	 * If a parameter is null, it is ignored.
	 */
	public Collection<UserDTO> findUsersSimple(String sessionId, String name) {
		if (hasRole(sessionId, UserRole.Admin)) {
			return ConverterUserDTO.toDTO(PersistenceFacade.getInstance().findUsersSimple(name));
		}
		return null;
	}

	public UserDTO saveUser(String sessionId, UserDTO user) {
		if (hasRole(sessionId, UserRole.Admin)) {
			try {
				return ConverterUserDTO
						.toDTO(PersistenceFacade.getInstance().saveOrUpdate(ConverterUserDTO.toEntity(user)));
			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
		}
		return null;
	}

	public DepartmentDTO getDepartmentById(String sessionId, Long id) {
		if (hasRole(sessionId, UserRole.StandardUser, UserRole.Admin)) {
			Department entity = PersistenceFacade.getInstance().getById(Department.class, id);
			return ConverterDepartmentDTO.toDTO(entity);
		}
		return null;
	}

	public Collection<DepartmentDTO> getAllDepartments(String sessionId) {
		if (hasRole(sessionId, UserRole.Admin, UserRole.StandardUser)) {
			List<Department> departments = PersistenceFacade.getInstance().getAll(Department.class);
			return ConverterDepartmentDTO.toDTO(departments);
		}
		return null;
	}

	public DepartmentDTO saveDepartment(String sessionId, DepartmentDTO department) {
		if (hasRole(sessionId, UserRole.Admin)) {
			Department entity = ConverterDepartmentDTO.toEntity(department);
			try {
				entity = PersistenceFacade.getInstance().saveOrUpdate(entity);
			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
			return ConverterDepartmentDTO.toDTO(entity);
		}
		return null;
	}

	public TeamDTO createTeam(String sessionId) {
		if (hasRole(sessionId, UserRole.Admin)) {
			return ConverterTeamDTO.toDTO(new Team());
		}
		return null;
	}

	public TeamDTO getTeamById(String sessionId, Long id) {
		if (hasRole(sessionId, UserRole.StandardUser, UserRole.Admin)) {
			Team entity = PersistenceFacade.getInstance().getById(Team.class, id);
			return ConverterTeamDTO.toDTO(entity);
		}
		return null;
	}

	/**
	 * If a parameter is null, it is ignored.
	 */
	public Collection<TeamDTO> findTeams(String sessionId, String name, TypeOfSport typeOfSport, Long departmentId,
			Long leagueId) {
		if (hasRole(sessionId, UserRole.Admin)) {
			List<Team> entities = PersistenceFacade.getInstance().findTeams(name, typeOfSport, departmentId, leagueId);
			return ConverterTeamDTO.toDTO(entities);
		}
		return null;
	}

	public TeamDTO saveTeam(String sessionId, TeamDTO team) {
		if (hasRole(sessionId, UserRole.Admin)) {
			Team entity = ConverterTeamDTO.toEntity(team);
			try {
				entity = PersistenceFacade.getInstance().saveOrUpdate(entity);
			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
			return ConverterTeamDTO.toDTO(entity);
		}
		return null;
	}

	/**
	 * Adds the specified player to the specified team.
	 *
	 * This method is currently untested!
	 *
	 * @param sessionId
	 *            a session id
	 * @param team
	 *            the team the player should be added to
	 * @param player
	 *            the user that should be added to the team
	 * @return the updated team if the session can add players; null, otherwise.
	 */
	public TeamDTO addPlayerToTeam(String sessionId, TeamDTO team, UserDTO player) {
		if (team != null && player != null) {
			UserDTO currentUser = getCurrentUser(sessionId);
			if (currentUser != null
					&& (hasRole(sessionId, UserRole.Admin) || team.getCoachId().equals(currentUser.getId()))) {
				Team teamEntity = ConverterTeamDTO.toEntity(team);
				if (teamEntity != null) {
					Set<Long> memberIds = teamEntity.getMemberIds();
					memberIds.add(player.getId());
					teamEntity.setMemberIds(memberIds);
					try {
						teamEntity = PersistenceFacade.getInstance().saveOrUpdate(teamEntity);
						return ConverterTeamDTO.toDTO(teamEntity);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
		}
		return null;
	}

	public LeagueDTO getLeagueById(String sessionId, Long id) {
		if (hasRole(sessionId, UserRole.StandardUser, UserRole.Admin)) {
			return ConverterLeagueDTO.toDTO(PersistenceFacade.getInstance().getById(League.class, id));
		}
		return null;
	}

	public Collection<LeagueDTO> getAllLeagues(String sessionId) {
		if (hasRole(sessionId, UserRole.Admin)) {
			List<League> entities = PersistenceFacade.getInstance().getAll(League.class);
			return ConverterLeagueDTO.toDTO(entities);
		}
		return null;
	}

	public Collection<TournamentDTO> findTournaments(String sessionId, String name, Long departmentId) {
		if (hasRole(sessionId, UserRole.Admin)) {
			List<Tournament> entities = PersistenceFacade.getInstance().findTournaments(name, departmentId);
			return ConverterTournamentDTO.toDTO(entities);
		}
		return null;
	}
	
	public MatchDTO getMatchById(String sessionId, Long matchId){
		if (hasRole(sessionId, UserRole.Admin, UserRole.StandardUser)) {
			Match match = PersistenceFacade.getInstance().getById(Match.class, matchId);
			return ConverterMatchDTO.toDTO(match);
		}
		return null;
	}

	public MatchDTO createMatch(String sessionId) {
		return ConverterMatchDTO.toDTO(new Match());
	}

	/**
	 * Adds a Match to a Tournament. Only administrators and the head of the
	 * hosting department have permissions to do this.
	 *
	 * @param sessionId
	 *            the session id of the currently logged in user
	 * @param tournament
	 *            the tournament, the match should be added to
	 * @param match
	 *            the match that should be added to the tournament
	 * @return the updated tournament; or null, if the match could not be added
	 */
	public TournamentDTO addMatchToTournament(String sessionId, TournamentDTO tournament, MatchDTO match) {
		if (tournament != null && match != null) {
			UserDTO currentUser = getCurrentUser(sessionId);
			DepartmentDTO currentDepartment = this.getDepartmentById(sessionId, tournament.getDepartmentId());
			if (currentUser != null) {
				if (hasRole(sessionId, UserRole.Admin) || currentUser.getId().equals(currentDepartment.getHeadId())) {
					Tournament tournamentEntity = ConverterTournamentDTO.toEntity(tournament);
					if (tournamentEntity != null) {
						try {
							// first: save match (this is a new entity)
							Match matchEntity = ConverterMatchDTO.toEntity(match);
							matchEntity = PersistenceFacade.getInstance().saveOrUpdate(matchEntity);
							// add match id to tournament
							Set<Long> matchIds = tournamentEntity.getMatchesIds();
							matchIds.add(matchEntity.getId());
							tournamentEntity.setMatchesIds(matchIds);
							tournamentEntity = PersistenceFacade.getInstance().saveOrUpdate(tournamentEntity);
							return ConverterTournamentDTO.toDTO(tournamentEntity);
						} catch (Exception e) {
							log.error(e.getMessage());
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * checks the login credentials on the ldap server and returns the new
	 * created session id
	 *
	 * @param username
	 *            ldap username
	 * @param password
	 *            ldap password
	 * @return session id
	 */
	public String loginLDAP(String username, String password) {
		if (username != null && password != null) {
			if (LDAPAuth.ldapLogin(username, password) != null) {
				User user = PersistenceFacade.getInstance().findUserByLDAP(username);
				if (user != null) {
					return SessionManager.getInstance().createNewSession(user.getId(), user.getRoles());
				}
			}
		}

		return null;
	}

	public UserDTO getCurrentUser(String sessionId) {
		return ConverterUserDTO.toDTO(
				PersistenceFacade.getInstance().getById(User.class, SessionManager.getInstance().getUserId(sessionId)));
	}

	private boolean hasRole(String sessionId, UserRole... roles) {
		for (UserRole role : roles) {
			if (_sessionManager.hasRole(sessionId, role)) {
				return true;
			}
		}
		return false;
	}
	
	public TournamentDTO createTournament(String sessionId, DepartmentDTO dept) {
		if (hasRole(sessionId, UserRole.Admin) || isDepartmentHead(_sessionManager.getUserId(sessionId), dept)) {
			return ConverterTournamentDTO.toDTO(new Tournament());
		}
		return null;
	}
	
	public TournamentDTO saveTournament(String sessionId, TournamentDTO tournament, DepartmentDTO dept) {
		if (hasRole(sessionId, UserRole.Admin) || isDepartmentHead(_sessionManager.getUserId(sessionId), dept)) {
			Tournament entity = ConverterTournamentDTO.toEntity(tournament);
			try {
				entity = PersistenceFacade.getInstance().saveOrUpdate(entity);
			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
			return ConverterTournamentDTO.toDTO(entity);
		}
		return null;
	}
	
	public TournamentDTO getTournamentById(String sessionId, Long id) {
		if (hasRole(sessionId, UserRole.StandardUser, UserRole.Admin)) {
			return ConverterTournamentDTO.toDTO(PersistenceFacade.getInstance().getById(Tournament.class, id));
		}
		return null;
	}
	
	public boolean isDepartmentHead(Long userId, DepartmentDTO dept){
		return dept.getHeadId().equals(userId);
	}
	
	public boolean isCoach(Long userId, TeamDTO team){
		return team.getCoachId().equals(userId);
	}

	public MatchDTO saveMatch(String sessionId, MatchDTO matchDTO, DepartmentDTO dept) {
		if (hasRole(sessionId, UserRole.Admin) || isDepartmentHead(_sessionManager.getUserId(sessionId), dept)) {
			Match entity = ConverterMatchDTO.toEntity(matchDTO);
			try {
				entity = PersistenceFacade.getInstance().saveOrUpdate(entity);
			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
			return ConverterMatchDTO.toDTO(entity);
		}
		return null;
	}
}