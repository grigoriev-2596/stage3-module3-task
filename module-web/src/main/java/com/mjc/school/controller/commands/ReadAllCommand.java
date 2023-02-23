package com.mjc.school.controller.commands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.Command;

public class ReadAllCommand<T, R, K> implements Command {

    private final BaseController<T, R, K> controller;

    public ReadAllCommand(BaseController<T, R, K> controller) {
        this.controller = controller;
    }


    @Override
    public Object execute() {
        return controller.readAll();
    }
}
