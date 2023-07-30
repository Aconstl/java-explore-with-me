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
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime eventDate;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id")
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private State state;
    private String title;

}
