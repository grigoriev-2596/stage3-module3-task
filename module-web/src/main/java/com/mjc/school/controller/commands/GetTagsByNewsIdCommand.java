package com.mjc.school.controller.commands;

import com.mjc.school.controller.Command;
import com.mjc.school.controller.implementation.TagController;

public class GetTagsByNewsIdCommand implements Command {

    private final TagController tagController;
    private final Long id;

    public GetTagsByNewsIdCommand(TagController tagController, Long id) {
        this.tagController = tagController;
        this.id = id;
    }

    @Override
    public Object execute() {
        return tagController.getTagByNewsId(id);
    }
}
