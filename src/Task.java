
public class Task {
    private String name;
    private String description;
    private int taskID;
    private int status; // 0-NEW 1- IN_PROGRESS 2-DONE

    public Task(String name, String description, int status) {
        this.name = name;
        this.description = description;
        this.setStatus(status);
    }

    public Task () {
    }



    public String getStatusText() {
        String statusName = "";
        switch (status) {
            case 0: statusName = "NEW";
                break;
            case 1: statusName = "IN_PROGRESS";
                break;
            case 2: statusName = "DONE";
                break;
        }
        return statusName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        if (taskID >= 0) {
            this.taskID = taskID;
        }
    }

    public int getStatusInt() {
        return status;
    }

    public void setStatus(int status) {
        if (status >= 0 && status < 3) {
            this.status = status;
        } else {
            System.out.println("Неверный ввод статуса задачи!");
        }
    }

    @Override
    public String toString() {
        return "ID - " + this.taskID + ". Задача - " + this.name + ". Статус - " + getStatusText();
    }
}
