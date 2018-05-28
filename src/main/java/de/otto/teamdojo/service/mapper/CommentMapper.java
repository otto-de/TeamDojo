package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.Comment;
import de.otto.teamdojo.service.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {TeamMapper.class, SkillMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.shortName", target = "teamShortName")
    @Mapping(source = "skill.id", target = "skillId")
    @Mapping(source = "skill.title", target = "skillTitle")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "teamId", target = "team")
    @Mapping(source = "skillId", target = "skill")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
