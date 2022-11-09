package com.example.schoolmealserver.domain.auth.repository

import com.example.schoolmealserver.domain.auth.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SignUpRepository: CrudRepository<UserEntity, Int> {
    fun findById(id: String): UserEntity?
}