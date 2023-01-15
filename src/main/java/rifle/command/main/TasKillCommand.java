package rifle.command.main;

import rifle.Rifle;
import rifle.command.Command;
import rifle.command.others.CommandArguments;
import rifle.utils.TextFormat;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class TasKillCommand extends Command {
    public TasKillCommand() {
        super("tkill", "End a task", new String[]{"tkill <taskId>..."});
    }

    @Override
    public void execute(CommandArguments commandArguments) {
        if (commandArguments.getOrigin().length() > 0) {
            String[] tids = commandArguments.toOrderMode().getArguments();

            ArrayList<String> success = new ArrayList<>();
            ArrayList<String> already = new ArrayList<>();
            for (String tid : tids) {
                if (already.contains(tid))
                    continue;
                already.add(tid);
                if (!Rifle.getInstance().getTaskMap().existsTask(tid))
                    continue;
                success.add(tid);
                Rifle.getInstance().getTaskMap().killTask(tid);
            }

            if (success.size() == 0)
                Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "Rifle cannot end the tasks you specified because they do not exist.");
            else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tasks that have been ended ({})".replace("{}", TextFormat.FONT_RED + String.valueOf(success.size()) + TextFormat.STYLE_RESET)).append(": ");

                int i = 0;
                for (String tid : tids) {
                    stringBuilder.append(TextFormat.FONT_GREEN).append(tid).append(TextFormat.STYLE_RESET);
                    if ((i + 1) < success.size())
                        stringBuilder.append(",");
                }
                Rifle.getInstance().getLogger().println(stringBuilder.toString());
            }
        } else
            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "Please enter the ID of the task that needs to be ended.");
    }
}
