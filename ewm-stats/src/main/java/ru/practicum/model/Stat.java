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
    @GeneratedValue
    @Column
    private Long id;

    private String app;

    private String uri;

    private String ip;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime timestamp;
}
