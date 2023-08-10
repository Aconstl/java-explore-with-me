package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "statistic")
@Builder
@NoArgsConstructor
public class Stat {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    private Long id;

    private String app;

    private String uri;

    private String ip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
