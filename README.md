# ДИПЛОМНЫЙ ПРОЕКТ
# java-explore-with-me
## Этап 3. Дополнительная функциональность - Комментарии
## https://github.com/Aconstl/java-explore-with-me/pull/5
#### Данная функциональность позволяет оставлять всем пользователям комментарии под опубликованными событиями. 
#### Комментарии можно редактировать. Размер комментария должен быть не более 7000 символов

### ЭНДПОИНТЫ
#### Private - пользовательские
    1. создание комментария:    POST "/users/{userId}/comments?eventId"
    2. изменение комментария:   PATCH "/users/{userId}/comments/{commentId}"
    3. удаление комментария:    DELETE "/users/{userId}/comments/{commentId}"
    4. получение конкретного комментария пользователя: 
                                GET "/users/{userId}/comments/{commentId}" 
    5. получение комментариев пользователя (от самого последнего к самому старому): 
                                GET "/users/{userId}/comments?from&size" 
#### Public - публичные
    6. получение коментариев события (от самого последнего к самому старому): 
                                GET "/events/{eventId}/comments?from&size"

#### Admin - административные
    7. удаления комментария:    GET "/admin/comments/{commentId}"