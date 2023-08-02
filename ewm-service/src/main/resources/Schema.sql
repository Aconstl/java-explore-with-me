CREATE TABLE IF NOT EXISTS public.users
(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(63) NOT NULL,
    email VARCHAR(63) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS public.locations
(
    location_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (location_id)
);

CREATE TABLE IF NOT EXISTS public.categories
(
    category_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (category_id),
    CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS public.events
(
    event_id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    initiator_id       BIGINT NOT NULL,
    annotation         VARCHAR(2000),
    category_id        BIGINT NOT NULL,
    description        VARCHAR(7000),
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    event_date         TIMESTAMP WITHOUT TIME ZONE,
    location_id        BIGINT NOT NULL,
    paid               BOOLEAN,
    participant_limit  BIGINT                                 NOT NULL,
    request_moderation BOOLEAN,
    title              VARCHAR(255),
    state              VARCHAR,
    views  			   BIGINT,
    published_on       TIMESTAMP WITHOUT TIME ZONE
    CONSTRAINT pk_events PRIMARY KEY (event_id),
    CONSTRAINT events_initiator_FK FOREIGN KEY (initiator_id) REFERENCES Public.users(user_id),
    CONSTRAINT events_category_FK FOREIGN KEY (category_id) REFERENCES Public.categories(category_id),
    CONSTRAINT events_location_FK FOREIGN KEY (location_id) REFERENCES Public.locations(location_id)
);

CREATE TABLE IF NOT EXISTS public.EventRequests
(
    request_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    requester_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT pk_eventRequests PRIMARY KEY (request_id),
    CONSTRAINT request_requester_FK FOREIGN KEY (requester_id) REFERENCES Public.users(user_id),
    CONSTRAINT request_event_FK FOREIGN KEY (event_id) REFERENCES Public.events(event_id)
);