package br.com.joaovq.article_data.local

import androidx.room.TypeConverter
import br.com.joaovq.article_data.network.dto.AuthorDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AuthorTypeConverter {
    @TypeConverter
    fun toString(authors: List<AuthorDto>): String {
        val gson = Gson()
        return gson.toJson(authors)
    }

    @TypeConverter
    fun toAuthors(authors: String): List<AuthorDto> {
        val gson = Gson()
        return gson.fromJson(authors, object : TypeToken<List<AuthorDto>>() {})
    }
}