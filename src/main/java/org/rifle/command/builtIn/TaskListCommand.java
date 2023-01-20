package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.utils.TextFormat;

/**
 * @author Huyemt
 */

public class TaskListCommand extends Command {
    public TaskListCommand() {
        super("tl", "Query task list", new String[]{"tl"});
    }

    @Override
    public void execute(Argument argument) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] taskIds = Rifle.getInstance().getScheduler().getTaskIds();

        stringBuilder
                .append("Tasks").append("(").append(TextFormat.FONT_RED).append(TextFormat.STYLE_BOLD).append(taskIds.length).append(TextFormat.RESET).append("): ");
        if (taskIds.length > 0) {
            for (int i = 0; i < taskIds.length; i++) {
                stringBuilder.append(TextFormat.FONT_BLUE).append(TextFormat.STYLE_BOLD).append(taskIds[i]);
                if ((i + 1) < taskIds.length)
                    stringBuilder.append(TextFormat.RESET).append(",");
            }
        }
        Rifle.getInstance().getLogger().println(stringBuilder.toString());
    }
}
