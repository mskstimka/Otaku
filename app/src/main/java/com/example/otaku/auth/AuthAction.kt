package com.example.otaku.auth

import com.example.otaku_domain.models.Token

sealed class AuthAction {
    object IS_UNAUTHORIZED : AuthAction()
    object ERROR : AuthAction()
    class IS_AUTHORIZED(val token: Token) : AuthAction()
    class ACTIVITY_ON_BACK_PRESSED(val token: Token) : AuthAction()
}