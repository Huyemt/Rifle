package org.rifle.module;

import org.bullet.CompiledBullet;
import org.bullet.Reporter;
import org.bullet.exceptions.BulletException;
import org.bullet.exceptions.RuntimeException;
import org.rifle.Rifle;
import org.rifle.utils.TextFormat;

import java.io.File;

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
        } catch (Exception e) {
            if (e instanceof BulletException) {
                if (e instanceof RuntimeException) {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, Reporter.report(e.getClass().getName(), ((RuntimeException) e).position, e.getMessage())));
                } else {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, e.getMessage()));
                }
                return;
            }

            Rifle.getInstance().getLogger().error(String.format("\n%s", e.getMessage()));
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
                if (e instanceof RuntimeException) {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, Reporter.report(e.getClass().getName(), ((RuntimeException) e).position, e.getMessage())));
                } else {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, e.getMessage()));
                }
                return;
            }

            Rifle.getInstance().getLogger().error(String.format("\n%s", e.getMessage()));
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
                if (e instanceof RuntimeException) {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, Reporter.report(e.getClass().getName(), ((RuntimeException) e).position, e.getMessage())));
                } else {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, e.getMessage()));
                }
                return;
            }

            Rifle.getInstance().getLogger().error(String.format("\n%s", e.getMessage()));
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
                if (e instanceof RuntimeException) {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, Reporter.report(e.getClass().getName(), ((RuntimeException) e).position, e.getMessage())));
                } else {
                    Rifle.getInstance().getLogger().error(String.format("\n%s%s", TextFormat.FONT_RED, e.getMessage()));
                }
                return;
            }

            Rifle.getInstance().getLogger().error(String.format("\n%s", e.getMessage()));
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
}

