package familytree.model.storage;

import familytree.model.Person;
import familytree.model.familyTree.FamilyTree;

public class FileStorage implements FileManager {
    private final Writable serializer;

    public FileStorage(Writable serializer) {
        this.serializer = serializer;
    }

    public boolean saveFamilyTree(FamilyTree<Person> familyTree, String filePath) {
        if (familyTree == null || filePath == null) {
            return false;
        }
        return serializer.save(familyTree, filePath);
    }

    public FamilyTree<Person> loadFamilyTree(String filePath) {
        Object obj = serializer.read(filePath);
        if (obj instanceof FamilyTree) {
            return (FamilyTree<Person>) obj;
        }
        return null;
    }
}


