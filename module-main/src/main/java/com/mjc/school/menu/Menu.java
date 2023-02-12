package com.mjc.school.menu;

import java.util.Scanner;

public abstract class Menu {
    protected Scanner scanner;

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract void create();

    public abstract void getAll();

    public abstract void getById();

    public abstract void update();

    public abstract void delete();

    protected long readId() {
        if (!scanner.hasNextLong()) {
            scanner.nextLine();
            throw new IllegalArgumentException("Id must be an integer");
        }
        long id = scanner.nextLong();
        scanner.nextLine();
        return id;
    }
}
