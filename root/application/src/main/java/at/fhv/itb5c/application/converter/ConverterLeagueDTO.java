package at.fhv.itb5c.application.converter;

import java.util.Collection;
import java.util.stream.Collectors;

import at.fhv.itb5c.commons.dto.LeagueDTO;
import at.fhv.itb5c.model.entity.League;

public class ConverterLeagueDTO {
	public static LeagueDTO toDTO(League entity) {
		if (entity == null) {
			return null;
		}

		LeagueDTO dto = new LeagueDTO();

		dto.setId(entity.getId());
		dto.setVersion(entity.getVersion());
		dto.setName(entity.getName());
		dto.setTypeOfSport(entity.getTypeOfSport());

		return dto;
	}

	public static Collection<LeagueDTO> toDTO(Collection<League> entities) {
		if (entities == null) {
			return null;
		}

		return entities.stream().map(ConverterLeagueDTO::toDTO).collect(Collectors.toList());
	}

	public static League toEntity(LeagueDTO dto) {
		if (dto == null) {
			return null;
		}

		League entity = new League();

		entity.setId(dto.getId());
		entity.setVersion(dto.getVersion());
		entity.setName(dto.getName());
		entity.setTypeOfSport(dto.getTypeOfSport());

		return entity;
	}
}
