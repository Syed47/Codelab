
public abstract class Runner {
    protected String runfile;
    protected String mainfile;
    protected Code codefiles;
    protected Language language; 

    protected Runner(Language language, Code codefiles, String mainfile, String runfile) {
        this.language = language;
        this.codefiles = codefiles;
        this.mainfile = mainfile;
        this.runfile = runfile;
    }

    protected abstract String scriptify();
    protected abstract void writeScript();
    protected abstract void setMainFile(String file);
    protected abstract void setRunFile(String file);
    protected abstract String getMainFile();
    protected abstract String getRunFile();
    protected abstract Language getLanguage();
}
