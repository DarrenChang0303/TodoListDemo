package darren.com.example.todolist;

import darren.com.example.todolist.dao.TodoDao;
import darren.com.example.todolist.entity.Todo;
import darren.com.example.todolist.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class TestTodoService {
    @Autowired
    TodoService todoService;
    @MockBean
    TodoDao todoDao;

    /**
     * 測試3A原則
     * Arrange:初始化目標物件，相依物件，方法參數，預期結果
     * Act:呼叫目標，物件方法
     * Assert:驗證是否符合預期
     */
    @Test
    public void testGetTodos(){
    //Arrange
    List<Todo> exceptedTodosList = new ArrayList();
    Todo todo = new Todo();
    todo.setId(1);
    todo.setTask("洗衣服");
    todo.setStatus(1);
    exceptedTodosList.add(todo);

        //定義模擬呼叫todoDao.findAll(),要回傳的預設結果
         Mockito.when(todoDao.findAll()).thenReturn(exceptedTodosList);

        // [Act]操作todoService.getTodos();
        Iterable<Todo> actualTodoList = todoService.getTodos();

        // [Assert] 預期與實際的資料
        assertEquals(exceptedTodosList, actualTodoList);
    }
    @Test
    public void testCreateTodo(){
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("eat");
        todo.setStatus(1);

        Mockito.when(todoDao.save(todo)).thenReturn(todo);
        Integer actualId = todoService.createTodo(todo);
        assertEquals(todo.getId(),actualId);
    }
    @Test
    public void testUpdateTodoSuccess(){
    Todo todo = new Todo();
    todo.setId(1);
    todo.setTask("Test");
    todo.setStatus(1);
    Optional<Todo> resTodo = Optional.of(todo);

    Mockito.when(todoDao.findById(1)).thenReturn(resTodo);
    todo.setId(2);

    // [Act] 實際呼叫操作todoService.createTodo
    Boolean actualUpdateRlt = todoService.updateTodo(1, todo);

    //  [Assert] 預期與實際的資料
    assertEquals(true, actualUpdateRlt);
    }

    @Test
    public void testUpdateTodoNotExistId () {
        // 準備更改的資料
        Todo todo = new Todo();
        todo.setStatus(2);
        Optional<Todo> resTodo = Optional.of(todo);

        // 模擬呼叫todoDao.findById(id)，資料庫沒有id=100的資料 回傳empty物件
        Mockito.when(todoDao.findById(100)).thenReturn(Optional.empty());

        // [Act] 實際呼叫操作todoService.updateTodo()
        Boolean actualUpdateRlt = todoService.updateTodo(100, todo);

        // [Assert] 預期與實際的資料
        assertEquals(false, actualUpdateRlt);
    }

    @Test
    public void testUpdateTodoOccurException () {
        // 準備更改的資料
        Todo todo = new Todo();
        todo.setId(1);
        todo.setStatus(1);
        Optional<Todo> resTodo = Optional.of(todo);

        // 模擬呼叫todoDao.findById(id)，資料庫有id=1的資料
        Mockito.when(todoDao.findById(1)).thenReturn(resTodo);
        todo.setStatus(2);

        // 模擬呼叫todoDao.save(todo)時發生NullPointerException例外
        doThrow(NullPointerException.class).when(todoDao).save(todo);

        // [Act] 實際呼叫操作todoService.updateTodo()
        Boolean actualUpdateRlt = todoService.updateTodo(100, todo);

        //  [Assert] 預期與實際的資料
        assertEquals(false, actualUpdateRlt);
    }


}
