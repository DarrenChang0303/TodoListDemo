package darren.com.example.todolist;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import darren.com.example.todolist.controller.TodoController;
import darren.com.example.todolist.entity.Todo;
import darren.com.example.todolist.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TesTodoController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TodoController todoController;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    TodoService todoService;

    /**
     * mockMvc.perform 執行一個請求，並對應到controller。
     * mockMvc.andExpect 期待並驗證回應是否正確。
     * mockMvc.andReturn 最後回應的值(body)，可以再利用這個值，做其他Assert驗證
     */

    @Test
    public void testGetTodos() throws Exception{
        //設定預期資料
        List<Todo> exceptedTodoList = new ArrayList<Todo>();
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("Test1");
        todo.setStatus(1);
        exceptedTodoList.add(todo);

        //模擬todoService.getTodo() 回傳 exceptedList
        Mockito.when(todoService.getTodos()).thenReturn(exceptedTodoList);

        //模擬呼叫[GET]/api/todos
        String returnString = mockMvc.perform(MockMvcRequestBuilders.get("/api/todos")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Iterable<Todo> actualList = objectMapper.readValue(returnString, new TypeReference<Iterable<Todo>>() {
        });

        // 判定回傳的body是否跟預期的一樣
        assertEquals(exceptedTodoList, actualList);
    }
    // TODO: 2022/3/1 update others test (POST,DELETE...)
}
