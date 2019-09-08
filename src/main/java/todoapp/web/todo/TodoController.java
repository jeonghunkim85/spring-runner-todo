package todoapp.web.todo;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import todoapp.core.todos.application.TodoFinder;
import todoapp.web.convert.TodoToSpreadsheetConverter;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {
	
	private SiteProperties site;
	
	private TodoFinder todoFinder;
	
	
	public TodoController(SiteProperties site, TodoFinder todoFinder) {
		this.site = site;
		this.todoFinder = todoFinder;
	}

	@RequestMapping("/todos")
	public ModelAndView todos() {
		ModelAndView mav = new ModelAndView("todos");
		
//		SiteProperties site = new SiteProperties();
//		site.setAuthor(env.getProperty("site.author"));
		
		mav.addObject("site", site);
		mav.addObject(new TodoToSpreadsheetConverter().convert(todoFinder.getAll()));
		
		return mav;
	}
	
	
}
