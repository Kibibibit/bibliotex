package thewindmills.com.au.utils;

public class StringBuilder {
    
    private String s;

    public StringBuilder(String s) {
        this.s = s;
    }

    public StringBuilder concat(String s) {
        this.s = String.format("%s%s", this.s, s);
        return this;
    }

    public String build() {
        return this.s;
    }

}
