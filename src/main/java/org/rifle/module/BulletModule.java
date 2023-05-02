package org.rifle.module;

import org.bullet.CompiledBullet;
import org.bullet.Reporter;
import org.bullet.base.components.BtFunction;
import org.bullet.base.types.BtList;
import org.bullet.base.types.BtDictionary;
import org.bullet.exceptions.BulletException;
import org.bullet.exceptions.RuntimeException;
import org.bullet.exceptions.common.ParsingException;
import org.rifle.Rifle;
import org.rifle.command.Command;
import org.rifle.command.arguments.Argument;
import org.rifle.utils.TextFormat;

import java.io.File;
import java.util.Map;

/**
 * @author Huyemt
 */

public class BulletModule extends Module {

    private final String name;
    private final String version;
    private final String[] authors;
    private final String description;
    private final String website;
    private final boolean canBeSelect;
    private final CompiledBullet bullet;


    public BulletModule(String name, String version, String[] authors, String description, String website, boolean canBeSelect, CompiledBullet bullet) {
        this.name = name;
        this.version = version;
        this.authors = authors;
        this.description = description;
        this.website = website;
        this.bullet = bullet;
        this.canBeSelect = canBeSelect;
    }

    @Override
    protected String getModuleName() {
        return name;
    }

    @Override
    protected String getModuleVersion() {
        return version;
    }

    @Override
    protected String[] getModuleAuthors() {
        return authors;
    }

    @Override
    protected String getModuleStringDescription() {
        return description == null ? super.getModuleWebsite() : description;
    }

    @Override
    protected String getModuleWebsite() {
        return website == null ? super.getModuleWebsite() : website;
    }

    @Override
    public void onLoad() {
        try {
            if (bullet.existsFunction("onLoad")) {
                bullet.invokeFunction("onLoad");
            }

            // 有指令需要注册
            if (bullet.existsAttribute("commands")) {
                Object obj = bullet.findAttribute("commands");

                if (!(obj instanceof BtDictionary)) {
                    throw new BulletException("Commands property must be a dictionary");
                }

                BtDictionary commands = (BtDictionary) obj;
                for (Map.Entry<String, Object> entry : commands.entrys()) {
                    if (!(entry.getValue() instanceof BtList)) {
                        throw new BulletException("A command message must correspond to a list");
                    }

                    BtList command = (BtList) entry.getValue();

                    if (command.size() < 3) {
                        throw new BulletException("Missing command information (name, description, usage)");
                    }

                    BtFunction cmdFunc = bullet.findFunction(command.get(0).toString());
                    String description = command.get(1).toString();
                    String[] usages = sArr(((BtList) command.get(2)).toArray());

                    getCommandManager().register(new Command(entry.getKey(), description, usages) {
                        private final BtFunction function = cmdFunc;
                        @Override
                        public void execute(Argument argument) {
                            try {
                                function.invoke((Object) argument.asOrderArgument().getArgs());
                            } catch (Exception e) {
                                if (e instanceof BulletException) {
                                    if (e instanceof RuntimeException) {
                                        Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, Reporter.report(e.getClass().getName(), ((RuntimeException) e).position, e.getMessage())));
                                        return;
                                    }
                                }

                                Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, e.getMessage()));
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            if (e instanceof BulletException) {
                if (e instanceof ParsingException) {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, Reporter.report(e.getClass().getName(), ((ParsingException) e).position, e.getMessage())));
                    return;
                }

                Rifle.getInstance().getLogger().error(TextFormat.FONT_RED + e.getMessage());
            } else e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            if (bullet.existsFunction("onDisable")) {
                bullet.invokeFunction("onDisable");
            }
        } catch (Exception e) {
            if (e instanceof BulletException) {
                if (e instanceof ParsingException) {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, Reporter.report(e.getClass().getName(), ((ParsingException) e).position, e.getMessage())));
                    return;
                }

                Rifle.getInstance().getLogger().error(TextFormat.FONT_RED + e.getMessage());
            } else e.printStackTrace();
        }
    }

    @Override
    public void onQuit() {
        try {
            if (bullet.existsFunction("onQuit")) {
                bullet.invokeFunction("onQuit");
            }
        } catch (Exception e) {
            if (e instanceof BulletException) {
                if (e instanceof ParsingException) {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, Reporter.report(e.getClass().getName(), ((ParsingException) e).position, e.getMessage())));
                    return;
                }

                Rifle.getInstance().getLogger().error(TextFormat.FONT_RED + e.getMessage());
            } else e.printStackTrace();
        }
    }

    @Override
    public void onSelected() {
        try {
            if (bullet.existsFunction("onSelected")) {
                bullet.invokeFunction("onSelected");
            }
        } catch (Exception e) {
            if (e instanceof BulletException) {
                if (e instanceof ParsingException) {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, Reporter.report(e.getClass().getName(), ((ParsingException) e).position, e.getMessage())));
                    return;
                }

                Rifle.getInstance().getLogger().error(TextFormat.FONT_RED + e.getMessage());
            } else e.printStackTrace();
        }
    }

    @Override
    public boolean isUserCanSelect() {
        return canBeSelect;
    }

    @Override
    public void init(String main, File file) {
        super.init(main, file);
        bullet.runtime.logger = getLogger();
    }

    private <T> String[] sArr(T[] data) {
        String[] a = new String[data.length];

        for (int i = 0; i < data.length; i++) {
            a[i] = data[i].toString();
        }

        return a;
    }
}

