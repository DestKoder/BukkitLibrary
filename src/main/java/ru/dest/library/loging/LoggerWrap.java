package ru.dest.library.loging;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class LoggerWrap implements ILogger {

    private final Logger logger;

    public LoggerWrap(Logger logger) {
        this.logger = logger;
    }

    private boolean printErrorStacktrace;

    @Override
    public void info(String msg){
        logger.info(msg);
    }

    @Override
    public void warning(String msg) {
        logger.warning(msg);
    }

    @Override
    public void error(@NotNull Exception e) {
        logger.warning(ConsoleLogger.RED + e.getMessage() + ConsoleLogger.RESET);
        if(printErrorStacktrace) e.printStackTrace();
    }

    @Override
    public void info(String @NotNull ... msg){
        for(String s : msg) info(s);
    }

    @Override
    public void warning(String @NotNull ... msg){
        for(String s : msg) warning(s);
    }

    @Override
    public void error(Exception @NotNull ... ex){
        for(Exception e : ex) error(e);
    }

    @Override
    public void setPrintErrorStacktrace(boolean printErrorStacktrace) {
        this.printErrorStacktrace = printErrorStacktrace;
    }
}
