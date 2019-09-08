package todoapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import todoapp.core.todos.domain.Todo;
import todoapp.core.todos.domain.TodoRepository;

@SpringBootApplication
public class TodosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodosApplication.class, args);
	}
	
	@Component
	static class TodoDataInit implements InitializingBean, CommandLineRunner, ApplicationRunner{
		
		private final Logger log = LoggerFactory.getLogger(TodoDataInit.class);
		private TodoRepository todoRepository;
		
		public TodoDataInit(todoapp.core.todos.domain.TodoRepository todoRepository) {
			this.todoRepository = todoRepository;
		}

		//InitializingBean
		@Override
		public void afterPropertiesSet() throws Exception {
			this.todoRepository.save(Todo.create("Task one"));
		}

		//CommandLineRunner
		@Override
		public void run(String... args) throws Exception {
			// TODO Auto-generated method stub
			this.todoRepository.save(Todo.create("Task two"));
		}

		//ApplicationRunner
		@Override
		public void run(ApplicationArguments args) throws Exception {
			// TODO Auto-generated method stub
			log.debug("applcation run.");
		}
		
		
	}

}
