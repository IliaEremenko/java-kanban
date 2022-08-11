public class Managers {


    static InMemoryTaskManager getDefault(int type){
        switch (type) { //сделал switch заранее как написано в ТЗ
            case (1):
                return new InMemoryTaskManager();
        }
        return null;
    }
}