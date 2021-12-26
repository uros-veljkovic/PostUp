package com.urkeev14.myapplication.feature.posts

import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.utils.DataMapper
import com.urkeev14.myapplication.utils.extensions.capitalized
import javax.inject.Inject

class TypicodePostDataMapper @Inject constructor() : DataMapper<TypicodePostDto, TypicodePostEntity> {
    override fun map(input: TypicodePostDto): TypicodePostEntity {
        return TypicodePostEntity(
            id = input.id,
            userId = input.userId,
            title = input.title.capitalized(),
            content = input.body.capitalized()
        )
    }
}