package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrinterTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenRootAndChildrenMenu() {
        Printer printer = new Printer();
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        printer.print(menu);
        String expected = """
                1. Сходить в магазин
                --1.1. Купить продукты
                ---1.1.1. Купить хлеб
                ---1.1.2. Купить молоко
                2. Покормить собаку
                """;
        assertThat(printer.getPrintedMenu()).isEqualTo(expected);
    }

    @Test
    public void whenSecondPositionHasMoreChildrenThanFirst() {
        Printer printer = new Printer();
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Покормить собаку", "Дать сухой корм", STUB_ACTION);
        menu.add("Покормить собаку", "Налить воды", STUB_ACTION);
        printer.print(menu);
        String expected = """
                1. Сходить в магазин
                2. Покормить собаку
                --2.1. Дать сухой корм
                --2.2. Налить воды
                """;
        assertThat(printer.getPrintedMenu()).isEqualTo(expected);
    }

    @Test
    public void whenNoChildren() {
        Printer printer = new Printer();
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add(Menu.ROOT, "Погулять с собакой", STUB_ACTION);
        printer.print(menu);
        String expected = """
                1. Сходить в магазин
                2. Покормить собаку
                3. Погулять с собакой
                """;
        assertThat(printer.getPrintedMenu()).isEqualTo(expected);
    }

}