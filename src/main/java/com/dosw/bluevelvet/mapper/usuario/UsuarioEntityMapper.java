package com.dosw.bluevelvet.mapper.usuario;

import org.mapstruct.Mapper;

import com.dosw.bluevelvet.entity.UsuarioEntity;
import com.dosw.bluevelvet.model.domain.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioEntityMapper {

    Usuario toDomain(UsuarioEntity entity);

    UsuarioEntity toEntity(Usuario usuario);
}
