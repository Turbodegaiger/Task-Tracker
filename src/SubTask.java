public class SubTask extends Task {
    private int epicID;

    SubTask (String name, String description, int status, int epicID) {
        super(name, description, status);
        this.setEpicID(epicID);
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }
}
