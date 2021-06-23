
public abstract class Compiler {
    protected Code codefiles;
    protected Language language; 

    protected Compiler(Language language, Code codefiles) {
        this.language = language;
        this.codefiles = codefiles;
    }

    protected abstract String scriptify();
    protected abstract void writeScript();
    protected abstract Language getLanguage();
}
