package com.mjc.school.controller.commands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.Command;

public class GetByIdCommand<T, R, K> implements Command {
    private final BaseController<T, R, K> controller;
    private final K id;

    public GetByIdCommand(BaseController<T, R, K> controller, K id) {
        this.controller = controller;
        this.id = id;
    }

    @Override
    public Object execute() {
        return controller.readById(id);
    }
}
