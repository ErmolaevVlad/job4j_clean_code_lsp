package ru.job4j.ood.isp.menu;

public class TodoApp {

    private Menu menu;
    private Printer printer;

    public TodoApp(Menu menu, Printer printer) {
        this.menu = menu;
        this.printer = printer;
    }

    public void addToRoot(String parentName, String childName, ActionDelegate actionDelegate) {
        menu.add(parentName, childName, actionDelegate);
    }

    public void getActionDelegate(String itemName) {
        menu.select(itemName).ifPresent(Menu.MenuItemInfo::getActionDelegate);
    }

    public void printedMenu() {
        printer.print(menu);
    }
}
