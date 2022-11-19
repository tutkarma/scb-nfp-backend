package dev.sorokin.hacksovcom.service.user.domain

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.sql.Time
import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Table

@Entity
@Table(name = "user_registration_request")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class UserRegistrationRequest(
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var name: String,
    var phone: String,
    var email: String,
    var passport: String,
    var password: String,
    var birthDate: Timestamp,
    var registrationDate: Timestamp
)