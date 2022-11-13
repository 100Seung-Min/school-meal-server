package com.example.schoolmealserver.domain.auth

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
class UserEntity(
        @Id
        val id: String,
        val password: String,
        @Column(columnDefinition = "int")
        val phone: String,
        val cityCode: String,
        val schoolName: String,
        @Column(columnDefinition = "int")
        val schoolCode: String,
        @Column(columnDefinition = "int")
        val `class`: String,
        @Column(columnDefinition = "int")
        val grade: String,
        val name: String
) {

}