package org.rifle.command.builtIn;

import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.utils.TextFormat;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class TaskKillerCommand extends Command {
    public TaskKillerCommand() {
        super("tkill", "Kill a task through an existing tid", new String[]{"tkill <taskId>..."});
    }

    @Override
    public void execute(Argument argument) {
        if (argument.getOrigin().length() > 0) {
            String[] ids = argument.asOrderArgument().getArgs();
            int success = 0;
            ArrayList<String> already = new ArrayList<>();

            for (String id : ids) {
                if (already.contains(id))
                    continue;
                already.add(id);

                if (!Rifle.getInstance().getScheduler().exists(id))
                    continue;

                if (Rifle.getInstance().getScheduler().cancelTask(id))
                    success += 1;
            }

            if (success == 0)
                Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "The task ID you entered does not exist or has been run before executing this command.");
        } else
            Rifle.getInstance().getLogger().println(TextFormat.FONT_RED + "You haven't entered tid, so you can't end any tasks.");
    }
}
