package com.example.schoolmealserver.domain.auth.service

import com.example.schoolmealserver.domain.auth.UserEntity
import com.example.schoolmealserver.domain.auth.payload.request.PhoneRequest
import com.example.schoolmealserver.domain.auth.payload.request.SignUpRequest
import com.example.schoolmealserver.domain.auth.repository.SignUpRepository
import net.nurigo.java_sdk.api.Message
import org.springframework.stereotype.Service


@Service
class AuthService(
        private val signUpRepository: SignUpRepository
) {
    private val apiKey = "NCSZ0AWENYOWEMAV"
    private val apiSecret = "XNUHSSWCI0JS2D6RZAVFZ3NVXHJBB5GW"

    fun signUp(signUpRequest: SignUpRequest) {
        signUpRepository.save(
                UserEntity(
                        signUpRequest.id,
                        signUpRequest.password,
                        signUpRequest.phone,
                        signUpRequest.schoolName,
                        signUpRequest.`class`,
                        signUpRequest.grade,
                        signUpRequest.name
                )
        )
    }

    fun certificatePhone(phone: String, number: String) {
        val coolsms = Message(apiKey, apiSecret)
        val params = HashMap<String, String>()
        params["to"] = phone
        params["from"] = "01040426797"
        params["type"] = "SMS"
        params["text"] = "본인확인 인증번호는 ${number}입니다."
        params["app_version"] = "test app 1.2"
        coolsms.send(params)
    }
}