package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.*;

public class Main {

    private int numberOfMessage = 0;
    private static final String TEMPLATE_FOR_CREATING_USERNAME = "Message[%d]";
    static Main main = new Main();
    Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        Runnable taskWithUsernames = () -> {
            try {
                while (true) {
                    System.out.println(main.createUser() + "--- Thread: " + currentThread().getName());
                    sleep(500);
                }
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        };

        Thread firstThread = new Thread(taskWithUsernames);
        Thread secondThread = new Thread(taskWithUsernames);

        firstThread.start();
        secondThread.start();

    }

    public String createUser() {
        return String.format(TEMPLATE_FOR_CREATING_USERNAME, returntIncrementNumber());
    }
    private int returntIncrementNumber() {
        lock.lock();
        try {
            return numberOfMessage++;
        } finally {
            lock.unlock();
        }
    }
}