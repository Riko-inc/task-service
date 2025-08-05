package com.example.taskManagementSystem.services;

import com.example.taskManagementSystem.domain.dto.requests.TaskCreateRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskGetAllRequest;
import com.example.taskManagementSystem.domain.dto.requests.TaskUpdateRequest;
import com.example.taskManagementSystem.domain.entities.TaskEntity;
import com.example.taskManagementSystem.domain.entities.UserEntity;
import com.example.taskManagementSystem.domain.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {


    /**
     * Создаёт новую задачу
     *
     * @param user        Автор задачи
     * @param taskRequest Поля, получаемые при запросе
     * @return {@link TaskEntity}, сохранённый в базе данных
     * @see TaskCreateRequest
     */
    TaskEntity createTask(UserEntity user, TaskCreateRequest taskRequest);


    /**
     * Обновляет существующую задачу. Операция доступна только для автора задачи
     *
     * @param taskUpdateRequest Поля, получаемые при запросе
     * @return Обновленный {@link TaskEntity}, сохранённый в базе данных
     * @see TaskUpdateRequest
     */
    TaskEntity updateTask(TaskUpdateRequest taskUpdateRequest);


    /**
     * Получает TaskEntity по id
     *
     * @param id id задачи
     * @return {@link TaskEntity}, сохранённый в базе данных
     */
    TaskEntity getTaskById(long id);

    /**
     * Получает список всех задач пользователя - созданных им и тех, которые ему назначены.
     *
     * @param userId            UserEntity, которому принадлежат задачи
     * @return список {@link TaskEntity}, сохранённый в базе данных
     */
    List<TaskEntity> getAllTasksByUserId(Long userId, TaskGetAllRequest request);

    /**
     * Удаляет задачу по её id. Может быть удалена только автором задачи
     *
     * @param id TaskEntity, для удаления
     */
    void deleteTaskById(long id);


    /**
     * Позволяет изменить статус задачи по её id. Изменять статус может как автор задачи, так и человек, которому
     * она назначена
     *
     * @param taskId     id задачи, статус которой нужно изменить
     * @param taskStatus Новый статус задачи
     * @return {@link TaskEntity} сохранённый в базе данных
     * @see TaskStatus
     */
    TaskEntity updateTaskStatus(long taskId, TaskStatus taskStatus);
}
