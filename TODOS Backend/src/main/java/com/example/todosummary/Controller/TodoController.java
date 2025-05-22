package com.example.todosummary.Controller;

import com.example.todosummary.model.Todo;
import com.example.todosummary.service.OpenAIService;
import com.example.todosummary.service.SlackService;
import com.example.todosummary.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;
    private final OpenAIService openAIService;
    private final SlackService slackService;

    public TodoController(TodoService todoService, OpenAIService openAIService, SlackService slackService) {
        this.todoService = todoService;
        this.openAIService = openAIService;
        this.slackService = slackService;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }
    @GetMapping("/test/slack")
    public ResponseEntity<String> testSlack() {
        try {
            boolean sent = slackService.sendToSlack(" message from  app.");
            return sent ? ResponseEntity.ok("Slack message sent successful.") :
                          ResponseEntity.status(500).body("Slack  failed.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Slack  error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        Todo savedTodo = todoService.addTodo(todo);
        return ResponseEntity.ok(savedTodo);
    }
    @GetMapping("/test-openai")
    public ResponseEntity<String> testOpenAIApi() {
        List<String> sampleTodos = List.of(
            "Write a blog post about AI",
            "Clean the garage",
            "Finish the Spring Boot assignment"
        );

        try {
            String summary = openAIService.generateSummary(sampleTodos);
            return ResponseEntity.ok("OpenAI Summary:\n" + summary);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error calling OpenAI API: " + e.getMessage());
        }
    }

    @GetMapping("/test-slack")
    public ResponseEntity<String> testSlackApi() {
        String testMessage = "🧪 Test message from Todo app Slack integration!";
        boolean success = slackService.sendToSlack(testMessage);

        if (success) {
            return ResponseEntity.ok("Slack message sent successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to send message to Slack.");
        }
    }
    @PostMapping("/summarize")
    public ResponseEntity<String> summarizeAndSend() {
        System.out.println("Summarize endpoint called.");

        List<String> pendingTodos = todoService.getPendingTodos()
                .stream()
                .map(Todo::getTitle)
                .collect(Collectors.toList());

        System.out.println("Pending todos: " + pendingTodos);

        if (pendingTodos.isEmpty()) {
            return ResponseEntity.ok("No pending todos to summarize.");
        }

        String summary;
        try {
            summary = openAIService.generateSummary(pendingTodos);
            System.out.println("Generated summary: " + summary);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to generate summary: " + e.getMessage());
        }

        try {
            boolean sent = slackService.sendToSlack(summary);
            System.out.println("Slack response: " + sent);
            if (sent) {
                return ResponseEntity.ok("Summary sent to Slack successfully.");
            } else {
                return ResponseEntity.status(500).body("Failed to send summary to Slack.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sending to Slack: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
