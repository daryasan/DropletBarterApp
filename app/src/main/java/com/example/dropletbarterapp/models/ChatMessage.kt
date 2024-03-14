package com.example.dropletbarterapp.models

import lombok.Data
import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.Setter
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

class ChatMessage(
    val message: String,
    val sender: User,
    val sendingTime: LocalDateTime,
    val read: Boolean = false
)