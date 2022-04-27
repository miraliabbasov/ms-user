package com.app.msuser.model.map;

import com.app.msuser.model.entity.UserEntity;
import com.app.msuser.model.dto.UserDto;
import com.app.msuser.model.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class UserMap {

    public static final UserMap INSTANCE = Mappers.getMapper(UserMap.class);


    public  abstract UserEntity dtoToEntity (RegisterRequest dto);

    public abstract UserDto entityToDto(UserEntity entity);

}
