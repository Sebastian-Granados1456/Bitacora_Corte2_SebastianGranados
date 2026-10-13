package com.dosw.bluevelvet.mapper.usuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dosw.bluevelvet.dto.auth.RegistroRequestDTO;
import com.dosw.bluevelvet.dto.auth.UsuarioResponseDTO;
import com.dosw.bluevelvet.model.domain.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // El registro publico siempre crea clientes
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", constant = "CLIENTE")
    Usuario toDomain(RegistroRequestDTO dto);

    // El ResponseDTO no tiene password, asi que nunca se expone
    UsuarioResponseDTO toResponse(Usuario usuario);
}
