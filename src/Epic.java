import java.util.HashMap;

class Epic extends Task {
    private HashMap<Integer, SubTask> subTasks = new HashMap<>(); // Хранятся подзадачи
    Epic (String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }
}
