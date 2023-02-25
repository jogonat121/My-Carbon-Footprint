package ui.menu;

public abstract class Menu {
    private final String name;

    public Menu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
