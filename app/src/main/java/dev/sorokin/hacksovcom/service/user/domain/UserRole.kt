package dev.sorokin.hacksovcom.service.user.domain

enum class UserRole(
    public val id: Long
) {
    ADMIN(0L),
    USER(1L);

    companion object {
        fun findById(id: Long): UserRole {
           return UserRole.values().first { it.id == id }
        }
    }
}