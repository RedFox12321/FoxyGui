package a4g.foxygui.menu;

import a4g.foxygui.inventory.Layout;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public abstract class Menu {
    private static Layout layout = new Layout();
    private String title = "";

    public Menu(String title) {
        layout = createLayout(layout);
        this.title = title;
    }
    public Menu() {}

    public void open(@Nonnull Player player) {
        player.openInventory(layout.renderLayout(title));
    }
    public void close(@Nonnull Player player) {
        player.closeInventory();
    }

    public void onInventoryClick(int index) {
        layout.handleClick(index);
    }

    public abstract Layout createLayout(Layout menuLayout);
}
