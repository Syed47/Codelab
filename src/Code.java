import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Code {
    
    private final String basePath;
    private final HashMap<String, String> files;

    public Code(String path, String extension) {
        this.basePath = path;
        this.files = new HashMap<>();
        String[] fileNames = Util.getFileNames(this.basePath, extension);
        for (String file : fileNames) {
            String code = Util.readlines(this.basePath+file);
            this.files.put(file, code);
        }
    }

    public String[] getFileNames() {
        Set<String> keys = files.keySet();
        String[] names = new String[keys.size()];
        System.arraycopy(keys.toArray(), 0, names, 0, keys.size());
        return names;
    }

    public String[] getFilePaths() {
        String[] names = getFileNames();
        String[] filePaths = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            filePaths[i] = String.format("%s%s", basePath, names[i]);
            System.out.println("Path: "+ filePaths[i]);
        }
        return filePaths;
    }

    public void print() {
        for (Map.Entry<String, String> pair : this.files.entrySet()) {
            Util.ECHO(
                String.format(
                    "[%s]\n+++++++++++\n\n%s\n+++++++++++\n",
                    pair.getKey(),
                    pair.getValue()    
                )
            );
        }
    }
}
