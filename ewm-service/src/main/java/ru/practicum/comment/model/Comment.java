package ru.practicum.comment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "commentator_id")
    private User commentator;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String text;

    @Column(name = "created_on")
    private LocalDateTime created;


}
