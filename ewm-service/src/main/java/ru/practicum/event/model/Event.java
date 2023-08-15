package ru.practicum.event.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.category.model.Category;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @JoinColumn(name = "annotation")
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JoinColumn(name = "description")
    private String description;

    @JoinColumn(name = "created_on")
    private LocalDateTime createdOn;

    @JoinColumn(name = "event_date")
    private LocalDateTime eventDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id")
    private Location location;

    @JoinColumn(name = "paid")
    private Boolean paid;

    @JoinColumn(name = "participant_limit")
    private Long participantLimit;

    @JoinColumn(name = "request_moderation")
    private Boolean requestModeration;

    @JoinColumn(name = "title")
    private String title;

    @JoinColumn(name = "state")
    @Enumerated(value = EnumType.STRING)
    private State state;

    @JoinColumn(name = "published_on")
    private LocalDateTime publishedOn;
}
