package dev.sorokin.hacksovcom.api.excpetion

import io.swagger.v3.oas.annotations.media.Schema
import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
@Schema
class ErrorDto(
    val message: String
)