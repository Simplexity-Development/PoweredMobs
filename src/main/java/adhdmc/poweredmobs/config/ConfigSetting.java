package adhdmc.poweredmobs.config;

public enum ConfigSetting {

    SKELETON_SPECIAL_ARROW_TYPES("skeleton.special-arrow-types"),
    SKELETON_SPECIAL_ARROW_CHANCE("skeleton.special-arrow-chance");

    public final String path;
    ConfigSetting(String path) {
        this.path = path;
    }

}
