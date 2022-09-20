package CustomExceptions;

import java.io.IOException;

public class ManagerSaveException extends Exception{
    String message = "Ошибка при сохранении";
    public ManagerSaveException(IOException message){
        super(message);
    }
}
