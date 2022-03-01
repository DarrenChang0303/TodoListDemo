package darren.com.example.todolist.dao;

import darren.com.example.todolist.entity.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoDao extends CrudRepository<Todo,Integer> {
}
