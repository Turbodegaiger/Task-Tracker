/*      Получение списка всех задач.
        Удаление всех задач.
        Получение по идентификатору.
        Создание. Сам объект должен передаваться в качестве параметра.
        Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        Удаление по идентификатору.
        Дополнительные методы:
        Получение списка всех подзадач определённого эпика.*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        int userInput;
        String name;
        String description;
        int status;
        int epicID;
        int newID;

        while (true) {
            printMenu();
            userInput = scanner.nextInt();
            if (userInput == 1) {
                taskManager.getTaskList();
            } else if (userInput == 2) {
                taskManager.removeAllTasks();
            } else if (userInput == 3) {
                userInput = scanner.nextInt();
                taskManager.getTaskByID(userInput);
            } else if (userInput == 4) {
                System.out.println("0 - task, 1 - subTask, 2 - Epic");
                userInput = scanner.nextInt();
                        switch (userInput) {
                            case 0: scanner.nextLine();
                                System.out.println("Имя:");
                                name = scanner.nextLine();
                                System.out.println("Описание:");
                                description = scanner.nextLine();
                                System.out.println("Статус: 0-NEW 1- IN_PROGRESS 2-DONE");
                                status = scanner.nextInt();
                                taskManager.createTask(someNewTasks(name, description, status));
                                break;
                            case 1: scanner.nextLine();
                                System.out.println("Имя:");
                                name = scanner.nextLine();
                                System.out.println("Описание:");
                                description = scanner.nextLine();
                                System.out.println("Статус: 0-NEW 1- IN_PROGRESS 2-DONE");
                                status = scanner.nextInt();
                                System.out.println("ID нужного эпика");
                                epicID = scanner.nextInt();
                                taskManager.createTaskSub(someNewSubTasks(name, description, status, epicID));
                                break;
                            case 2: scanner.nextLine();
                                System.out.println("Имя:");
                                name = scanner.nextLine();
                                System.out.println("Описание:");
                                description = scanner.nextLine();
                                taskManager.createTaskEpic(someNewEpic(name, description));
                                break;
                        }
            } else if (userInput == 5) {
                System.out.println("Сначала введите ID. Затем выберите тип задачи 0 - task, 1 - subTask, 2 - Epic");
                newID = scanner.nextInt();
                userInput = scanner.nextInt();
                switch (userInput) {
                    case 0: scanner.nextLine();
                        System.out.println("Имя:");
                        name = scanner.nextLine();
                        System.out.println("Описание:");
                        description = scanner.nextLine();
                        System.out.println("Статус: 0-NEW 1- IN_PROGRESS 2-DONE");
                        status = scanner.nextInt();
                        taskManager.updateTask(newID, someNewTasks(name, description, status));
                        break;
                    case 1: scanner.nextLine();
                        System.out.println("Имя:");
                        name = scanner.nextLine();
                        System.out.println("Описание:");
                        description = scanner.nextLine();
                        System.out.println("Статус: 0-NEW 1- IN_PROGRESS 2-DONE");
                        status = scanner.nextInt();
                        epicID = 0;
                        taskManager.updateTask(newID, someNewSubTasks(name, description, status, epicID));
                        break;
                    case 2: scanner.nextLine();
                        System.out.println("Имя:");
                        name = scanner.nextLine();
                        System.out.println("Описание:");
                        description = scanner.nextLine();
                        taskManager.updateTask(newID, someNewEpic(name, description));
                        break;
                }
            } else if (userInput == 6) {
                newID = scanner.nextInt();
                taskManager.removeTask(newID);
            } else if (userInput == 7) {
                newID = scanner.nextInt();
                System.out.println(taskManager.getSubTasks(newID));
            } else if (userInput == 8) {
                break;
            }
        }
    }

    static private void printMenu() {
        String menu =
                "1 Получение списка всех задач.\n" +
                "2        Удаление всех задач.\n" +
                "3        Получение по идентификатору.\n" +
                "4        Создание. Сам объект должен передаваться в качестве параметра.\n" +
                "5        Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.\n" +
                "6        Удаление по идентификатору.\n" +
                "7        Дополнительные методы:\n" +
                "        Получение списка всех подзадач определённого эпика и на 8 - выыход";
        System.out.println(menu);
    }

    private static Object someNewTasks(String name, String description, int status) {
        return new Task(name, description, status);
    }

    private static Object someNewSubTasks(String name, String description, int status, int epicID) {
        return new SubTask(name, description, status, epicID);
    }

    private static Object someNewEpic(String name, String description) {
        return new Epic(name, description);
    }
}