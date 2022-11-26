import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Object> tasks = new HashMap<>(); // Хранятся обычные задачи и эпики
    private ArrayList <Integer> epicsID = new ArrayList<>(); // Хранятся номера эпиков
    private int taskIDCounter = 0;
    
    public void taskIDIncrement() {
        taskIDCounter++;
    }

    public void createTask(Object obj) {
        if (obj != null) {
            Task task = (Task) obj;     // Приведение
            task.setTaskID(taskIDCounter);  // Забиваем в объект его ID
            tasks.put(taskIDCounter, task); // Кладём в хэшмапу
            System.out.println("Создана задача с номером " + taskIDCounter);
            taskIDIncrement();  // Инкремент, чтобы не повторялось
        }
    }

    public void createTaskSub(Object obj) {
        if (obj != null) {
            SubTask subTask = (SubTask) obj;
            int epicID = subTask.getEpicID();
            if (epicsID.contains(epicID)) {   // Проверка на существование эпика
                Epic epic = (Epic) tasks.get(subTask.getEpicID());
                subTask.setTaskID(taskIDCounter);
                epic.getSubTasks().put(taskIDCounter, subTask);
                System.out.println("Создана задача с номером " + taskIDCounter);
                updateEpicStatus(epic.getTaskID()); // Обновление статуса эпика на основании подзадач
                taskIDIncrement();
            } else {
                System.out.println("Не найдена эпик задача с ID номером " + subTask.getEpicID());
            }
        }
    }

    public void createTaskEpic(Object obj) {
        if (obj != null) {
            Epic epic = (Epic) obj;
            epic.setTaskID(taskIDCounter);
            tasks.put(taskIDCounter, epic);
            epicsID.add(taskIDCounter);   // Добавление в массив с номерами эпиков, по которому потом удобно искать их
            System.out.println("Создана задача с номером " + taskIDCounter);    // и не тратить время на сравнение с
            taskIDIncrement();                                                  // обычными задачами
        }
    }

    public Object getTaskByID(int key) {
        if (tasks.containsKey(key)) {
            System.out.println("Найдено! Задача: " + tasks.get(key));
            return tasks.get(key);
        } else {
            for (Integer epicID : epicsID) {
                Epic epic = (Epic) tasks.get(epicID);
                if (epic.getSubTasks().containsKey(key)) {
                    System.out.println("Найдено! Подзадача: " + epic.getSubTasks().get(key));
                    return epic.getSubTasks().get(key);
                }
            }
        }
        System.out.println("Задачи с указанным ключом нет");
        return null;
    }

    public void updateTask(int key, Object obj) {
        if (obj != null) {
            if (tasks.containsKey(key)) {
                if (epicsID.contains(key)) {
                    Epic epic = (Epic) obj;
                    epic.setTaskID(key);
                    tasks.replace(key, epic);
                    updateEpicStatus(epic.getTaskID());
                    System.out.println("Найдено! Задача эпик: " + tasks.get(key) + " заменена.");
                    return;
                }
                Task task = (Task) obj;
                task.setTaskID(key);
                tasks.replace(key, task);
                System.out.println("Найдено! Задача: " + tasks.get(key) + " заменена.");
                return;
            } else {
                for (Integer epicID : epicsID) {
                    Epic epic = (Epic) tasks.get(epicID);
                    if (epic.getSubTasks().containsKey(key)) {
                        SubTask subTask = (SubTask) obj;
                        subTask.setTaskID(key);
                        subTask.setEpicID(epicID);
                        epic.getSubTasks().replace(key, subTask);
                        updateEpicStatus(epicID);
                        System.out.println("Найдено! Подзадача: " + epic.getSubTasks().get(key) + " заменена.");
                        return;
                    }
                }
            }
            System.out.println("Задачи с указанным ключом нет");
        }
    }

    public void removeTask(int key) {
        if (tasks.containsKey(key)) {
            System.out.println("Найдено! Задача: " + tasks.get(key) + " удалена.");
            tasks.remove(key);
            if (epicsID.contains(key)) {
                epicsID.remove((Integer) key);    // Если эпик - убираем из списка
            }
            return;
        } else {
            for (Integer epicID : epicsID) {
                if (tasks.get(epicID) != null) {
                    Epic epic1 = (Epic) tasks.get(epicID);
                    if (epic1.getSubTasks().containsKey(key)) {
                        System.out.println("Найдено! Подзадача: " + epic1.getSubTasks().get(key) + " удалена.");
                        tasks.remove(key);
                        updateEpicStatus(epicID);
                        return;
                    }
                }
            }
        }
        System.out.println("Задачи с указанным ключом нет");
    }

    private void updateEpicStatus(int epicID) {
        int doneStatusCount = 0;
        int newStatusCount = 0;
        Epic epic = (Epic) tasks.get(epicID);

        if (epic.getSubTasks().size() != 0) {
            for (SubTask subTask : epic.getSubTasks().values()) {
                if (subTask.getStatusInt() == 0) {
                    newStatusCount++;
                } else if (subTask.getStatusInt() == 2) {
                    doneStatusCount++;
                }
            }
            if (doneStatusCount == epic.getSubTasks().size()) {
                epic.setStatus(2);
            } else if (newStatusCount == epic.getSubTasks().size()) {
                epic.setStatus(0);
            } else {
                epic.setStatus(1);
            }
        } else {
            System.out.println("В эпике не осталось подзадач.");
        }
    }

    public void getTaskList() {
        for (Object task : tasks.values()) {
            System.out.println(task.toString());
        }
    }

    public void removeAllTasks() {
        tasks.clear();
        taskIDCounter = 0;
    }

    public String getSubTasks(int epicID) {
        String subTaskList = "";

        Epic epic = (Epic) tasks.get(epicID);
        if (epic.getSubTasks().size() != 0) {
            for (Integer taskID : epic.getSubTasks().keySet()) {
                subTaskList += "\n" + epic.getSubTasks().get(taskID);
            }
            return subTaskList;
        } else {
            return "Эпик пуст";
        }
    }
}
