import java.util.Map;

public abstract class Compiler {

    protected Code codefiles;
    protected String mainfile;
    protected String outfile;

    protected Compiler(Code codefiles, String outfile) {
        this.codefiles = codefiles;
        this.setOutFile(outfile);
        this.setMainFile(this.locateMainFile());
    }
    protected Compiler(Code codefiles, String mainfile, String outfile) {
        this.codefiles = codefiles;
        this.setMainFile(mainfile);
        this.setOutFile(outfile);
    }

    protected String locateMainFile() {
        String regex = this.getLanguage().getMainRegex();
        for (Map.Entry<String, String> pair : this.codefiles.getFileTree().entrySet()) {
            if (Util.checkRegex(regex, pair.getValue())) {
                String name = pair.getKey();
                Util.DEBUG("MAIN FILE = " + name);
                return name.substring(0, name.indexOf("."));
            }
        }
        Util.ERROR("NO file with main method found");
        return null;
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
