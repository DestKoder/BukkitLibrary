package ru.dest.library.loging;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public interface ILogger {

    @Contract(value = "_ -> new", pure = true)
    static @NotNull ILogger wrap(Logger logger){return new LoggerWrap(logger);}

    /**
     * Print info message
     * @param msg - message
     */
    void info(String msg);
    /**
     * Print warning message
     * @param msg - message
     */
    void warning(String msg);
    /**
     * Print error
     * @param e - exception
     */
    void error(@NotNull Exception e);
    /**
     * Print info messages
     * @param msg - message
     */
    void info(String @NotNull ... msg);
    /**
     * Print warning messages
     * @param msg - message
     */
    void warning(String @NotNull ... msg);
    /**
     * Print error messages
     * @param ex - exceptions
     */
   void error(Exception @NotNull ... ex);
    void setPrintErrorStacktrace(boolean printErrorStacktrace);
}
