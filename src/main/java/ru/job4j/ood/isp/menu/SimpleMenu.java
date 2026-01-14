package ru.job4j.ood.isp.menu;

import java.util.*;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
   boolean rsl = false;
        if (parentName == null) {
            rootElements.add(new SimpleMenuItem(childName, actionDelegate));
        } else if (findItem(parentName).isPresent()) {
            findItem(parentName).get().menuItem.getChildren().add(new SimpleMenuItem(childName, actionDelegate));
            rsl = true;
        }
        return  rsl;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        Optional<MenuItemInfo> rsl = Optional.empty();
        if (findItem(itemName).isPresent()) {
            rsl = Optional.of(new MenuItemInfo(findItem(itemName).get().menuItem, findItem(itemName).get().number));
        }
        return rsl;
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        List<MenuItemInfo> list = new ArrayList<>();
        DFSIterator dfsIterator = new DFSIterator();
        while (dfsIterator.hasNext()) {
            ItemInfo itemInfo = dfsIterator.next();
            list.add(new MenuItemInfo(
                    itemInfo.menuItem.getName(),
                    itemInfo.menuItem.getChildren()
                            .stream()
                            .map(MenuItem::getName)
                            .toList(),
                    itemInfo.menuItem.getActionDelegate(),
                    itemInfo.number));
        }
        return list.iterator();
    }

    private Optional<ItemInfo> findItem(String name) {
        ItemInfo rsl = null;
        DFSIterator iterator = new DFSIterator();
        while (iterator.hasNext()) {
            String number = iterator.numbers.getFirst();
            MenuItem itemFromIterator = iterator.next().menuItem;
            if (itemFromIterator.getName().equals(name)) {
                rsl = new ItemInfo(itemFromIterator, number);
                break;
            }
        }
        return Optional.ofNullable(rsl);
    }

    private static class SimpleMenuItem implements MenuItem {

        private String name;
        private List<MenuItem> children = new ArrayList<>();
        private ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }
    }

    private class DFSIterator implements Iterator<ItemInfo> {

        Deque<MenuItem> stack = new LinkedList<>();

        Deque<String> numbers = new LinkedList<>();

        DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious();) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private class ItemInfo {

        MenuItem menuItem;
        String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }
    }
}