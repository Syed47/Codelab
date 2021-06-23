
public abstract class Runner {
    protected String mainfile;
    protected Code codefiles;
    protected Language language; 

    protected Runner(Language language, Code codefiles, String mainfile) {
        this.mainfile = mainfile;
        this.language = language;
        this.codefiles = codefiles;
    }

    protected abstract String scriptify();
    protected abstract void writeScript();
    protected abstract void setMainFile(String file);
    protected abstract String getMainFile();
    protected abstract Language getLanguage();
}
