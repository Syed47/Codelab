import java.util.HashMap;
import java.util.Map;

class JavaCode extends Code {
    public JavaCode(String dirpath) {
        super(new Java(), dirpath);
    }
    public JavaCode(String dirpath, String mainfile) {
        super(new Java(), dirpath, mainfile);
    }
}

class CCode extends Code {
    public CCode(String dirpath) {
        super(new C(), dirpath);
    }
    public CCode(String dirpath, String mainfile) {
        super(new C(), dirpath, mainfile);
    }
}

class PythonCode extends Code {
    public PythonCode(String dirpath) {
        super(new Python3(), dirpath);
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

    protected Code( Language language, String path) {
        this(language, path, null);
        this.setMainFile(
            (this.count == 1) ? 
            this.files.keySet().iterator().next() : 
            (this.count > 1) ?
            this.locateMainFile() : 
            null
        );
    }

    protected Code(Language language, String path, String mainfile) {
        this.basePath = path.charAt(path.length()-1) == '/' ? path : path.concat("/");
        this.language = language;
        this.mainfile = mainfile;
        this.files = new HashMap<>();
        for (String file : this.getFileTitles()) {
            String code = Util.readlines(this.basePath+(file+this.language.getExtension()));
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
                return name;
            }
        }
        Util.ERROR("NO file with main method found");
        return null;
    }

    protected String[] getFileTitles() {
        String[] names = this.getFileNames();
        String[] titles = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            titles[i] = Util.fileTitle(names[i]);
        }
        return titles;
    }

    protected String[] getFilePaths() {
        String[] names = getFileNames();
        String[] filePaths = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            filePaths[i] = basePath.concat(names[i]);
        }
        return filePaths;
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
        return Util.getFileNames(this.basePath, this.language.getExtension());
    }

}
