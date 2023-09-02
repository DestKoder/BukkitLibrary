package ru.dest.library.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Utils {

    /**
     * Transforms string array to a single string
     * @param args - array of strings
     * @return resulting string
     */
    @NotNull
    public static String argsToString(String @NotNull [] args){
        StringBuilder sb = new StringBuilder();

        for (String arg : args) {
            sb.append(arg);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    /**
     * Transforms string array to a single string with start position
     * @param args - array of strings
     * @param startPos - number of the element from which need start
     * @return resulting string
     */
    public static @NotNull String argsToString(String @NotNull [] args, int startPos){
        StringBuilder sb = new StringBuilder();

        for(int i =startPos ;i < args.length; i ++){
            sb.append(args[i]);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    public static @NotNull String argsToString(String @NotNull [] args, String separator) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if(i != args.length-1) sb.append(separator);
        }

        return sb.toString().trim();
    }

    public static @NotNull String argsToString(String @NotNull [] args, int startPos, String separator) {
        StringBuilder sb = new StringBuilder();

        for(int i =startPos ;i < args.length; i ++){
            sb.append(args[i]);
            if(i != args.length-1) sb.append(separator);
        }

        return sb.toString().trim();
    }

    /**
     * Transforms {@link Collection} of {@link String} to a single string
     * @param collection - collection of strings
     * @return resulting string
     */
    @NotNull
    public static String collectionToString(@NotNull Collection<String> collection){
        StringBuilder sb = new StringBuilder();

        for (String arg : collection) {
            sb.append(arg);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    /**
     * Transforms {@link Collection} of {@link String} to a single string with specified separator
     * @param collection - collection of strings
     * @param separator - separator
     * @return resulting string
     */
    @NotNull
    public static String collectionToString(@NotNull Collection<String> collection, String separator){
        StringBuilder sb = new StringBuilder();

        for (String arg : collection) {
            sb.append(arg);
            sb.append(separator);
        }
        String s = sb.toString().trim();
        return s.substring(0, s.length()-separator.length()).trim();
    }

    /**
     * Transforms {@link List} of {@link String} to a single string with start position
     * @param collection - collection of strings
     * @param startPos - number of the element from which need start
     * @return resulting string
     */
    public static @NotNull String collectionToString(@NotNull List<String> collection, int startPos){
        return collectionToString(collection, startPos, " ");
    }

    /**
     * Transforms {@link List} of {@link String} to a single string with specified separator
     * @param collection - collection of strings
     * @param separator - separator
     * @return resulting string
     */
    public static @NotNull String collectionToString(@NotNull List<String> collection, String separator) {
        return collectionToString(collection, 0, separator);
    }
    /**
     * Transforms {@link List} of {@link String} to a single string with specified separator and start position
     * @param collection - collection of strings
     * @param separator - separator
     * @param startPos - number of element from which we need to start
     * @return resulting string
     */
    public static @NotNull String collectionToString(@NotNull List<String> collection, int startPos, String separator) {
        StringBuilder sb = new StringBuilder();

        for(int i =startPos ;i < collection.size(); i ++){
            sb.append(collection.get(i));
            if(i != collection.size()-1) sb.append(separator);
        }

        return sb.toString().trim();
    }

    /**
     * Convert given objects array to list of objects;
     * @param values - list of objects
     * @return list of given objects;
     * @param <T> type of objects;
     */
    @SafeVarargs
    @Contract("_ -> new")
    public static <T> @NotNull List<T> newList(T... values){
        return Arrays.asList(values);
    }

    /**
     * Call consumer if result of given {@link Predicate} equals true
     * @param t object which will be checked
     * @param c consumer which will be called
     * @param p checking condition
     * @param <T> type of checking object
     */
    public static <T> void executeIf(@NotNull T t, @NotNull Consumer<T> c, @NotNull Predicate<T> p){
        if(p.test(t)){
            c.accept(t);
        }
    }
    /**
     * Call consumer if result of given {@link Predicate} equals true
     * @param collection {@link Collection} of objects which will be checked
     * @param c consumer which will be called
     * @param p checking condition
     * @param <T> type of checking object
     */
    public static <T> void forEachIf(@NotNull Collection<T> collection, @NotNull Consumer<T> c, @NotNull Predicate<T> p){
        for(T t : collection){
            executeIf(t, c, p);
        }
    }
}
