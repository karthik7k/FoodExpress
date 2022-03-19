package entities;

public abstract class Data{

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Data(int id) {
        this.id = id;
    }

    public Data() {
    }
}
