package ua.kiev.prog;

/**
 * Created by Валерий on 30.03.2016.
 */
public class Photo {
    public Photo(long id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    private  long id;
    private  byte [] data;
}
