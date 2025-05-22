package com.example.todosummary.service;

import com.example.todosummary.model.Todo;
import com.example.todosummary.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> getPendingTodos1() {
        return todoRepository.findAll()
                .stream()
                .filter(todo -> !todo.isCompleted())
                .toList();
    }

	public Collection<Todo> getPendingTodos() {
	
		return null;
	}

	public void deleteTodo1(Long id) {
	
		
	}

	public void deleteById(Long id) {
	
		
	}
}
