import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Code {
    
    private final int count;
    private final String basePath;
    private final HashMap<String, String> files;

    public Code(String path, String filter) {
        this.basePath = path.charAt(path.length()-1) == '/' ? path : (path+"/");
        this.files = new HashMap<>();
        String[] fileNames = Util.getFileNames(this.basePath, filter);
        for (String file : fileNames) {
            String code = Util.readlines(this.basePath+file);
            this.files.put(file, code);
        }
        this.count = this.files.keySet().size();
    }

    public String getBasePath() {
        return this.basePath;
    }
    
    public int getCount() {
        return this.count;
    }

    public HashMap<String, String> getFileTree() {
        return this.files;
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
        }
        return filePaths;
    }

    public String[] getFileTitles() {
        String[] names = getFileNames();
        String[] titles = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            titles[i] = names[i].substring(0, names[i].indexOf("."));
        }
        return titles;
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
