package ru.job4j.ood.isp.menu;

public class Printer implements MenuPrinter {

    private static final String INDENT = "-";
    private StringBuilder builderPrintedMenu = new StringBuilder();

    @Override
    public void print(Menu menu) {
        for (Menu.MenuItemInfo itemInfo : menu) {
            String number = itemInfo.getNumber();
            String name = itemInfo.getName();
            int i = number.length() / 2;
            if (number.length() / 2 > 1) {
                builderPrintedMenu.append(INDENT.repeat(i));
                builderPrintedMenu.append(number);
                builderPrintedMenu.append(" ");
                builderPrintedMenu.append(name);
                builderPrintedMenu.append(System.lineSeparator());
            } else {
                builderPrintedMenu.append(number);
                builderPrintedMenu.append(" ");
                builderPrintedMenu.append(name);
                builderPrintedMenu.append(System.lineSeparator());
            }
        }
        System.out.println(builderPrintedMenu);
    }

    public String getPrintedMenu() {
        return builderPrintedMenu.toString();
    }
}
