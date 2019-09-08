package todoapp.core.todos.domain;

import org.springframework.context.MessageSourceResolvable;

/**
 * 할 일 저장소에서 할 일 엔티티를 찾을 수 없을 때 발생 가능한 예외 클래스
 *
 * @author springrunner.kr@gmail.com
 */
public class TodoEntityNotFoundException extends TodoEntityException implements MessageSourceResolvable {

    private Long id;

    public TodoEntityNotFoundException(Long id) {
        super("Todo 엔티티를 찾을 수 없습니다. (id: %d)", id);
        this.id = id;
    }

    @Override
    public String[] getCodes() {
        return new String[]{"Exception.TodoEntityNotFoundException"};
    }

    @Override
    public Object[] getArguments() {
        return new Object[] {String.valueOf(id)};
    }

    @Override
    public String getDefaultMessage() {
        return getMessage();
    }

}
