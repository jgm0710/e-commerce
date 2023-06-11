package com.example.ecommerce.user.adepter.out.persistence.account

import com.example.ecommerce.global.persistence.BaseEntity
import com.example.ecommerce.user.domain.account.Account
import com.example.ecommerce.user.domain.account.AccountId
import com.example.ecommerce.user.domain.account.AccountType
import com.example.ecommerce.user.domain.member.MemberId
import java.time.Instant
import javax.persistence.*


@Entity
@Table(name = "account")
class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private val accountType: AccountType,

    @Column(name = "member_id")
    private val memberId: Long,

    @Column(name = "login_id", unique = true)
    private val loginId: String,

    private val password: String?,

    createdAt: Instant,

    lastModifiedAt: Instant,
) : BaseEntity(createdAt, lastModifiedAt) {

    companion object {
        fun Account.toEntity(): AccountEntity {
            return AccountEntity(
                id = savedId,
                accountType = accountType,
                memberId = memberId.value,
                loginId = loginId,
                password = password,
                createdAt = createdAt,
                lastModifiedAt = lastModifiedAt,
            )
        }
    }

    fun toDomain(): Account {
        return Account(
            accountType = accountType,
            memberId = MemberId(memberId),
            loginId = loginId,
            password = password
        ).also {
            it.id = AccountId(checkNotNull(id) { "[id] 는 null 일 수 없습니다." })
            it.createdAt = createdAt
            it.lastModifiedAt = lastModifiedAt
        }
    }
}