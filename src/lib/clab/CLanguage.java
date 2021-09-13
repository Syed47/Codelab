package lib.clab;

import lib.core.Language;

public class CLanguage extends Language { 
    public CLanguage() {
        super(
            "gcc", 
            "./", 
            ".c",
            ".*int\\s+main\\s*" +
            "\\(\\s*(\\s*int\\s+\\w*\\s*\\,\\s*char\\s*" +
            "(\\*\\s*\\*\\s*|\\*\\s*\\[\\s*\\]\\s+)\\w+\\s*|\\s*)\\)\\s*\\{.*"
        ); 
    } 
}

