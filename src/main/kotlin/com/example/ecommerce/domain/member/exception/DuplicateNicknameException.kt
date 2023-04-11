package com.example.ecommerce.domain.member.exception

import com.example.ecommerce.global.exception.AbstractConflictException

class DuplicateNicknameException(nickname : String) : AbstractConflictException("Nickname 이 기존 회원의 닉네임과 중복됩니다. [$nickname]")
