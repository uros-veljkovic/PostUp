package com.urkeev14.myapplication.feature.post

import com.urkeev14.myapplication.data.source.local.entity.TypicodeUserEntity
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import com.urkeev14.myapplication.utils.DataMapper
import javax.inject.Inject

class TypicodeUserDataMapper @Inject constructor() : DataMapper<TypicodeUserDto, TypicodeUserEntity> {
    override fun map(input: TypicodeUserDto): TypicodeUserEntity {
        return TypicodeUserEntity(
            id = input.id,
            fullName = input.name,
            email = input.email
        )
    }
}