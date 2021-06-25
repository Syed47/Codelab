import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class JavaCode extends Code {
    public JavaCode(String dirpath) {
        super(dirpath, new Java());
    }
    public JavaCode(String dirpath, String mainfile) {
        super(new Java(), dirpath, mainfile);
    }
}

class CCode extends Code {
    public CCode(String dirpath) {
        super(dirpath, new C());
    }
    public CCode(String dirpath, String mainfile) {
        super(new C(), dirpath, mainfile);
    }
}

class PythonCode extends Code {
    public PythonCode(String dirpath) {
        super(dirpath, new Python3());
    }
    public PythonCode(String dirpath, String mainfile) {
        super(new Python3(), dirpath, mainfile);
    }
}


public abstract class Code {
    
    protected final int count;
    protected final String basePath;
    protected final Language language;
    protected final HashMap<String, String> files;
    protected String mainfile;

    protected Code(String path, Language language) {
        this(language, path, null);
        this.setMainFile(this.locateMainFile());
    }

    protected Code(Language language, String path, String mainfile) {
        this.basePath = path.charAt(path.length()-1) == '/' ? path : path.concat("/");
        this.language = language;
        this.mainfile = mainfile;
        this.files = new HashMap<>();
        String[] fileNames = Util.getFileNames(this.basePath, this.language.getExtension());
        for (String file : fileNames) {
            String code = Util.readlines(this.basePath+file);
            this.files.put(file, code);
        }
        this.count = this.files.keySet().size();
    }

    protected String locateMainFile() {
        String regex = this.getLanguage().getMainRegex();
        for (Map.Entry<String, String> pair : this.files.entrySet()) {
            if (Util.checkRegex(regex, pair.getValue())) {
                String name = pair.getKey();
                Util.DEBUG("MAIN FILE = " + name);
                return name.substring(0, name.indexOf("."));
            }
        }
        Util.ERROR("NO file with main method found");
        return null;
    }

    protected void setMainFile(String mainfile) {
        this.mainfile = mainfile;
    }

    protected String getMainFile() {
        return this.mainfile;
    }

    protected Language getLanguage() {
        return this.language;
    }

    protected String getBasePath() {
        return this.basePath;
    }
    
    protected int getCount() {
        return this.count;
    }

    protected Map<String, String> getFileTree() {
        return this.files;
    }

    protected String[] getFileNames() {
        Set<String> keys = files.keySet();
        String[] names = new String[keys.size()];
        System.arraycopy(keys.toArray(), 0, names, 0, keys.size());
        return names;
    }

    protected String[] getFilePaths() {
        String[] names = getFileNames();
        String[] filePaths = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            filePaths[i] = String.format("%s%s", basePath, names[i]);
        }
        return filePaths;
    }

    protected String[] getFileTitles() {
        String[] names = getFileNames();
        String[] titles = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            titles[i] = names[i].substring(0, names[i].indexOf("."));
        }
        return titles;
    }

    protected void print() {
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
