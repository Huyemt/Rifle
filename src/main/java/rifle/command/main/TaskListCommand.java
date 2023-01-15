package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.utils.TextFormat;

import java.util.Set;

/**
 * @author Huyemt
 */

public class TaskListCommand extends Command {
    public TaskListCommand() {
        super("tl", "Lists the ids of all tasks", new String[]{"tl"});
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        Set<String> tids = Rifle.getInstance().getTaskMap().getAllTasks().keySet();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tasks ({})".replace("{}", TextFormat.FONT_RED + String.valueOf(tids.size()) + TextFormat.STYLE_RESET)).append(": ");

        if (tids.size() > 0) {
            int i = 0;
            for (String tid : tids) {
                stringBuilder.append(TextFormat.FONT_GREEN).append(tid).append(TextFormat.STYLE_RESET);
                if ((i + 1) < tids.size())
                    stringBuilder.append(",");
            }
        }
        Rifle.getInstance().getLogger().println(stringBuilder.toString());
    }
}
