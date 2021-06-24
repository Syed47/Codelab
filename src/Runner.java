
public abstract class Runner {

    protected Compiler compiler;
    protected String runfile;

    protected Runner(Compiler compiler) {
        this.compiler = compiler;
        this.runfile = this.compiler.getOutFile();
    }

    // protected Runner(Compiler compiler, String runfile) {
    //     this.compiler = compiler;
    //     this.runfile = runfile;
    // }

    protected void setRunFile(String runfile) {
        this.runfile = runfile;
    }

    protected String getRunFile() {
        return this.runfile;
    }

    protected Compiler getCompiler() {
        return this.compiler;
    }

    protected Language getLanguage() {
        return this.compiler.getLanguage();
    }

    protected void writeScript() {
        this.writeScript(this.compiler.getCode().getBasePath()+"vpl_run.sh");
    }
    
    protected void writeScript(String to) {
        Util.writeToFile(to, this.scriptify());
    }    

    // implement for every runner
    protected abstract String scriptify();
}
