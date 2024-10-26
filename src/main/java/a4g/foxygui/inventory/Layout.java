package a4g.foxygui.inventory;

import a4g.foxygui.util.validator.NumberValidator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Virtual representation of a bukkit inventory
 */
public class Layout {
    private final HashMap<Integer, Item> items = new HashMap<>();
    private int size = InventoryType.CHEST.getDefaultSize();
    private InventoryType type = InventoryType.CHEST;

    private static final int MAX_COLUMN_NUMBER = 9;
    private static final int MAX_ROW_NUMBER = 6;

    /**
     * Creates an empty chest layout with 3 rows (27 slots).
     * <p>Default values:</p>
     * <li>inventorySize = 27</li>
     * <li>inventoryType = <b>InventoryType.CHEST</b></li>
     */
    public Layout(){}

    /**
     * Creates a chest layout with 3 rows (27 slots), populated with the items in Map.
     * @param items A map of index->item pairs for this layout.
     */
    public Layout(@Nonnull Map<Integer, Item> items) {
        this.items.putAll(items);
    }

    /**
     * Creates a chest layout with n rows (n * 9 slots), populated with the items in Map.
     * @param items A map of index->item pairs for this layout.
     * @param rows Number of rows (9 columns each) that the layout should have.
     * @throws IllegalArgumentException if the number of rows is not a value between 1 and {@value MAX_ROW_NUMBER}.
     */
    public Layout(@Nonnull Map<Integer, Item> items, int rows) throws IllegalArgumentException {
        this(items);
        this.size = NumberValidator.Range(rows, 1, MAX_ROW_NUMBER)*9;
    }

    /**
     * Creates a layout of type, populated with the items in Map.
     * @param inventoryType The {@link InventoryType} to use for this layout.
     * @apiNote For any {@link InventoryType} other than <b>InventoryType.CHEST</b> the inventory size will be ignored.
     */
    public Layout(@Nonnull Map<Integer, Item> items, @Nonnull InventoryType inventoryType) {
        this(items);
        this.type = inventoryType;
    }


    public Map<Integer, Item> getItems () {
        return Map.copyOf(items);
    }

    /**
     * Adds the items in Map to the layout, replacing any slot that already has an item.
     * <p>To ignore any filled slots use {@link Layout#addItems(Map)}.</p>
     * @param items The items to add to the layout
     */
    public void setItems(@Nonnull Map<Integer, Item> items) {
        this.items.putAll(items);
    }
    /**
     * Adds the items in Map to the layout, ignoring any slot that already has an item.
     * <p>To replace any filled slots use {@link Layout#setItems(Map)}.</p>
     * @param items The items to add to the layout
     */
    public void addItems(@Nonnull Map<Integer, Item> items) {
        items.forEach(this.items::putIfAbsent);
    }

    /**
     * Add the item to the specified index, replacing the slot if it has any item.
     * <p>To add the item only if the slot is empty use {@link Layout#addItem(int, Item)}.</p>
     * @param index The index of the slot to add the item.
     * @param item The item to add to the layout.
     * @throws IllegalArgumentException if the index is outside the range [0,53].
     */
    public void setItem(int index, @Nonnull Item item) {
        items.put(NumberValidator.Range(index, 1, MAX_ROW_NUMBER*MAX_COLUMN_NUMBER),item);
    }

    /**
     * Add the item to the specified row-column pair, replacing the slot if it has any item.
     * <p>To add the item only if the slot is empty use {@link Layout#addItem(int, Item)}.</p>
     * @param row The row of the slot you want. Starts on 1.
     * @param column The column of the slot you want. Starts on 1.
     * @param item The item to add to the layout.
     * @throws IllegalArgumentException if the row parameter is outside the range [1,{@value MAX_ROW_NUMBER}] or the column parameter is outside the range [1,{@value MAX_COLUMN_NUMBER}].
     */
    public void setItem(int row, int column, @Nonnull Item item) throws IllegalArgumentException {
        setItem((NumberValidator.Range(row, 1, MAX_ROW_NUMBER) - 1) * MAX_COLUMN_NUMBER + NumberValidator.Range(column, 1, MAX_COLUMN_NUMBER) - 1, item);
    }

    /**
     * Add the item to the specified index, replacing the slot if it has any item.
     * <p>To add the item independent if it is filled use {@link Layout#setItem(int, Item)}.</p>
     * @param index The index of the slot to add the item.
     * @param item The item to add to the layout.
     * @throws IllegalArgumentException if the index is outside the range [0,53].
     */
    public void addItem(int index, @Nonnull Item item) throws IllegalArgumentException {
        items.putIfAbsent(NumberValidator.Range(index, 1, MAX_ROW_NUMBER),item);
    }
    /**
     * Add the item to the specified row-column pair,  the slot if it has any item.
     * <p>To add the item independent if it is filled use {@link Layout#setItem(int, Item)}.</p>
     * @param row The row of the slot you want. Starts on 1.
     * @param column The column of the slot you want. Starts on 1.
     * @param item The item to add to the layout.
     * @throws IllegalArgumentException if the row parameter is outside the range [1,{@value MAX_ROW_NUMBER}] or the column parameter is outside the range [1,{@value MAX_COLUMN_NUMBER}].
     */
    public void addItem(int row, int column, @Nonnull Item item) throws IllegalArgumentException {
        addItem((NumberValidator.Range(row, 1, MAX_ROW_NUMBER) - 1) * MAX_COLUMN_NUMBER + NumberValidator.Range(column, 1, MAX_COLUMN_NUMBER) - 1, item);
    }


    public int getSize() {
        return size;
    }
    /**
     * @param rows Number of rows (9 columns each) that the layout should have.
     * @throws IllegalArgumentException if the rows parameter is not a number between 1 and {@value MAX_ROW_NUMBER}.
     */
    public void setSize(int rows) throws IllegalArgumentException {
        size = NumberValidator.Range(rows,1, MAX_ROW_NUMBER)*9;
    }

    public InventoryType getType() {
        return type;
    }
    /**
     * @apiNote For any {@link InventoryType} other than <b>InventoryType.CHEST</b> the inventory size value will be ignored.
     */
    public void setType(@Nonnull InventoryType type) {
        this.type = type;
    }


    /**
     * Transforms this layout configuration into an {@link Inventory}.
     * Do note that any out of range slot indexes will be passed through a mod operation with inventory size; In simpler terms, independent of the given index the actual index used is somewhere between 0 and (n-1), n being the size of the inventory.
     * @param title The title to use for {@link Inventory}.
     * @return An {@link Inventory} rendered with this layout.
     */
    public Inventory renderLayout(String title) {
        Inventory inventory;

        if (type == InventoryType.CHEST) {
            inventory = Bukkit.createInventory(null, size, title);
        } else {
            inventory = Bukkit.createInventory(null, type, title);
        }

        return inventory;
    }

    public void handleClick(int index) {
        items.get(index).runCallback();
    }

    /**
     * Converts a pair of row and column values to an index value.
     * Use this method if you do not want to count slots.
     * @param row The row of the slot you want. Starts on 1.
     * @param column The column of the slot you want. Starts on 1.
     * @return The converted index value.
     * @throws IllegalArgumentException if the row value is outside [1,54] or the column value is outside [1,9]
     */
    public static int convertToIndex(int row, int column) throws IllegalArgumentException {
        return (NumberValidator.Range(row, 1, MAX_ROW_NUMBER * MAX_COLUMN_NUMBER) - 1) * MAX_COLUMN_NUMBER + NumberValidator.Range(column,1,MAX_COLUMN_NUMBER);
    }



    public static final class Item {
        private final ItemStack item;
        private Runnable onClickMethod = null;


        public Item(@Nonnull ItemStack item, @Nullable Runnable onClickMethod) {
            this.item = item;
            this.onClickMethod = onClickMethod;
        }
        public Item(ItemStack item) {
            this(item, null);
        }


        public Item(@Nonnull Material material, int amount, Runnable onClickMethod) {
            this(new ItemStack(material, amount), onClickMethod);
        }
        public Item(@Nonnull Material item, Runnable onClickMethod) {
            this(item, 1, onClickMethod);
        }
        public Item(@Nonnull Material item, int amount) {
            this(item, amount, null);
        }
        public Item(@Nonnull Material item) {
            this(item, 1, null);
        }

        /**
         * Useful method to set the click event for this Item while retaining this object reference.
         * Use this if you want to separate the instantiation of the Item from the method attribution.
         * A quick example of the use of this method:
         * <code>Item item = new Item(...).setOnclickEvent(Class::method)</code>
         * @param callbackMethod The method that will be called when this item is clicked.
         * @return This Item reference.
         */
        public Item setOnClickMethod(@Nonnull Runnable callbackMethod) {
            onClickMethod = callbackMethod;
            return this;
        }


        private void runCallback() {
            if (onClickMethod == null)
                return;
            onClickMethod.run();
        }
    }
}