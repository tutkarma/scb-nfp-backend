package dev.sorokin.hacksovcom.service.user.domain

import lombok.AllArgsConstructor
import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.springframework.data.annotation.Id
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Table

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class User (
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column(unique = true)
    var name: String,
    @Column(unique = true)
    var phone: String,
    @Column(unique = true)
    var email: String,
    @Column(unique = true)
    var passport: String,
    var password: String,
    var birthDate: Timestamp,
    var isBlocked: Boolean,
    var roleId: Long,
) {
}
