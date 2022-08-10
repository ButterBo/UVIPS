package sg.edu.rp.c346.id21044912.uvips;
import java.io.Serializable;

public class UVLight implements Serializable {
    private int id;
    private int value;
    private String timestamp;

    public UVLight(int value, String timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UV\n" +"id: " + id+
                "\nvalue: " + value +
                "\ntimestamp: " + timestamp;
    }
}