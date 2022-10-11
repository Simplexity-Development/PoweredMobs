package adhdmc.poweredmobs.config;

public enum ConfigSetting {

    SKELETON_SPECIAL_ARROW_TYPES("skeleton.special-arrow-types"),
    SKELETON_SPECIAL_ARROW_CHANCE("skeleton.special-arrow-chance"),

    ZOMBIE_RESURRECT_ENABLED("zombie.resurrect.enabled"),
    ZOMBIE_RESURRECT_CHANCE("zombie.resurrect.chance"),
    ZOMBIE_RESURRECT_DURATION("zombie.resurrect.duration");

    public final String path;
    ConfigSetting(String path) {
        this.path = path;
    }

}
