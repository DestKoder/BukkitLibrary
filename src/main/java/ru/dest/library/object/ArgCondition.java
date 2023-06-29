package ru.dest.library.object;

import ru.dest.library.Library;

import java.util.function.Predicate;

public class ArgCondition {

    private final String needed;
    private final Predicate<String> check;

    public ArgCondition(String needed, Predicate<String> check) {
        this.needed = needed;
        this.check = check;
    }

    public boolean test(String s){return check.test(s);}

    public Message getErrorMessage(){
        return Library.getInstance().getInvalidArgumentMessage(needed);
    }
}
