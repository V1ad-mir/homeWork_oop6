package familytree.model.storage;

import familytree.model.exceptions.SerializationException;

import java.io.*;

public class SerializationStorage implements Writable {

    @Override
    public boolean save(Serializable serializable, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(serializable);
            return true;
        } catch (IOException e) {
            throw new SerializationException("Ошибка при сохранении данных", e);
        }
    }

    @Override
    public Object read(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        } catch (IOException e) {
            throw new SerializationException("Ошибка при чтении данных", e);
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Ошибка при десериализации объекта", e);
        }
    }
}
