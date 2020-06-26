package ru.papont.sarafan.controller;

import org.springframework.web.bind.annotation.*;
import ru.papont.sarafan.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {

    private int count = 4;

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First Message"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second Message"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third Message"); }});
    }};

    //получение списка сообщений
    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    //получение сообщения по id
    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    //создание сообщения
    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(count++));
        messages.add(message);
        return message;
    }

    //обновление сообшения по id
    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id", id);
        return messageFromDb;
    }

    //удаление сообщения
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }
}
