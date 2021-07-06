
public abstract class Compiler {

    protected Code codefiles;
    protected String mainfile;
    protected String outfile;

    protected Compiler(Code codefiles, String outfile) {
        this.codefiles = codefiles;
        this.outfile = outfile;
        this.setMainFile(this.codefiles.getMainFile());
    }

    protected void writeScript() {
        this.writeScript(this.codefiles.getBasePath()+"vpl_compile.sh");
    }
    
    protected void writeScript(String to) {
        Util.writeToFile(to, this.scriptify());
    }    

    protected Code getCode() {
        return this.codefiles;
    }

    protected Language getLanguage() {
        return this.codefiles.getLanguage();
    }
    
    protected String getOutFile() {
        return this.outfile;
    }

    protected void setOutFile(String out) {
        this.outfile = out;
    }

    protected String getMainFile() {
        return this.mainfile;
    }

    protected void setMainFile(String mf) {
        this.mainfile = mf;
    }

    // implement for every compiler you create
    protected abstract String scriptify();

}
