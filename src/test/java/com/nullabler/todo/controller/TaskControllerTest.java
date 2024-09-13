package com.nullabler.todo.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nullabler.todo.dto.request.CreateTaskRequest;
import com.nullabler.todo.dto.request.ToggleActiveTaskRequest;
import com.nullabler.todo.dto.response.MessageResponse;
import com.nullabler.todo.entity.TaskEntity;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

	@Autowired
	private MockMvc mvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testTask() throws Exception {
        // Empty list
		mvc.perform(MockMvcRequestBuilders.get("/api/task").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("[]")));

        CreateTaskRequest req = new CreateTaskRequest("root_1", null);
        TaskEntity task = new TaskEntity();
        task.setTitle("root_1");
        task.setId(1);
        task.setActive(true);

        // Create
        mvc.perform(MockMvcRequestBuilders.put("/api/task")
            .content(objectMapper.writeValueAsString(req))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().string(equalTo(objectMapper.writeValueAsString(task))));

        // View
        mvc.perform(MockMvcRequestBuilders.get("/api/task/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("\"title\":\"" + task.getTitle() + "\"")))
        .andExpect(content().string(containsString("\"active\":true")));


        // Toggle active
        ToggleActiveTaskRequest reqToggle = new ToggleActiveTaskRequest(1, false);
        MessageResponse resp = new MessageResponse("Changed");
        mvc.perform(MockMvcRequestBuilders.post("/api/task/toggle")
            .content(objectMapper.writeValueAsString(reqToggle))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo(objectMapper.writeValueAsString(resp))));

        // View
        mvc.perform(MockMvcRequestBuilders.get("/api/task/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("\"title\":\"" + task.getTitle() + "\"")))
        .andExpect(content().string(containsString("\"active\":false")));

        // List
		mvc.perform(MockMvcRequestBuilders.get("/api/task").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
        .andExpect(content().string(containsString("\"title\":\"" + task.getTitle() + "\"")))
        .andExpect(content().string(containsString("\"active\":false")));

        // Delete
        resp = new MessageResponse("Deleted");
        mvc.perform(MockMvcRequestBuilders.delete("/api/task/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo(objectMapper.writeValueAsString(resp))));

    }
}
