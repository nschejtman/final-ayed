import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class DataBase {
    private final File file;
    private final RandomAccessFile raf;
    private final int regSize = 56;


    public DataBase(String name) throws FileNotFoundException {
        file = new File(name);
        raf = new RandomAccessFile(file, "rw");
    }

    /**
     * Adds a car to the car database.
     *
     * @param car
     * @throws IOException
     */
    public void write(Car car) throws IOException {
        raf.writeUTF(car.getPlate());
        raf.writeUTF(car.getModel());
        raf.writeUTF(car.getBrand());
        raf.writeInt(car.getYear());
        raf.writeBoolean(car.isActive());
    }

    /**
     * Closes the random access file.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        raf.close();
    }

    /**
     * Returns the length of the random access file.
     *
     * @return
     * @throws IOException
     */
    public long size() throws IOException {
        return raf.length();
    }

    /**
     * Returns the amount of registries in the random access file.
     *
     * @return
     * @throws IOException
     */
    public long regQty() throws IOException {
        return size() / regSize;
    }

    /**
     * Returns the next car to the right of the pointer.
     *
     * @return
     * @throws IOException
     */
    public Car read() throws IOException {
        return new Car(raf.readUTF(), raf.readUTF(), raf.readUTF(), raf.readInt(), raf.readBoolean());
    }

    /**
     * Sets the random access file's pointer to the first position
     *
     * @throws IOException
     */
    public void start() throws IOException {
        raf.seek(0);
    }

    /**
     * Goes to a specific position in the database
     *
     * @param pos
     * @throws IOException
     */
    public void seek(long pos) throws IOException {
        raf.seek(pos);
    }

    /**
     * Moves a certain amount of positions
     *
     * @param amount
     * @throws IOException
     */
    public void move(long amount) throws IOException {
        raf.seek(raf.getFilePointer() + amount);
    }

    /**
     * Sets the random access file's pointer to the last position.
     *
     * @throws IOException
     */
    public void end() throws IOException {
        raf.seek(raf.length());
    }

    /**
     * Finds a car in the database given its plate. If its not contained in the database returns an empty car.
     *
     * @param plate
     * @return
     * @throws IOException
     */
    public Car find(String plate) throws IOException {
        plate = Car.validate(plate);
        long qty = regQty();
        Car car;
        start();
        for (int i = 0; i < qty; i++) {
            car = read();
            if (car.isActive() && car.getPlate().equals(plate)) return car;
        }
        return new Car();
    }

    /**
     * Removes a car from the database by setting it's active attribute to false. If such removal is successful returns
     * true, false otherwise.
     *
     * @param plate
     * @return
     * @throws IOException
     */
    public boolean remove(String plate) throws IOException {
        plate = Car.validate(plate);
        Car car = find(plate);
        if (car.getPlate() != null && car.isActive()) {
            raf.seek(raf.getFilePointer() - regSize);
            car.setActive(false);
            write(car);
            return true;
        } else
            return false;
    }

    /**
     * Deletes the file
     *
     * @throws IOException
     */
    public void delete() throws IOException {
        raf.close();
        file.delete();
    }

    /**
     * Returns registry size for the database
     *
     * @return
     */
    public int getRegSize() {
        return regSize;
    }

    /**
     * Renames the file to a given name
     *
     * @param name
     * @throws IOException
     */
    public void renameTo(String name) throws IOException {
        raf.close();
        File auxFile = new File(name);
        file.renameTo(auxFile);
    }


}
