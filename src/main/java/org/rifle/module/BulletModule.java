package org.rifle.module;

import org.bullet.CompiledBullet;

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
            if (bullet.existsInterface("onLoad")) {
                bullet.invokeInterface("onLoad");
            }
        } catch (Exception ignored) {
            super.onLoad();
        }
    }

    @Override
    public void onDisable() {
        try {
            if (bullet.existsInterface("onDisable")) {
                bullet.invokeInterface("onDisable");
            }
        } catch (Exception ignored) {
            super.onDisable();
        }
    }

    @Override
    public void onQuit() {
        try {
            if (bullet.existsInterface("onQuit")) {
                bullet.invokeInterface("onQuit");
            }
        } catch (Exception ignored) {
            super.onQuit();
        }
    }

    @Override
    public void onSelected() {
        try {
            if (bullet.existsInterface("onSelected")) {
                bullet.invokeInterface("onSelected");
            }
        } catch (Exception ignored) {
            super.onSelected();
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

