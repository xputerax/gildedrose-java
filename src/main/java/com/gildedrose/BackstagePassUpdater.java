package com.gildedrose;

public class BackstagePassUpdater implements ItemUpdater {
    @Override
    public void update(Item item) {
        ageItem(item);

        if (item.sellIn < 0) {
            item.quality = 0;
            return;
        }

        if (item.sellIn < 6) {
            incrementQuality(item, 3);
            return;
        }

        if (item.sellIn < 11) {
            incrementQuality(item, 2);
            return;
        }

        incrementQuality(item, 1);
    }

    private void ageItem(Item item) {
        item.sellIn -= 1;
    }

    private void incrementQuality(Item item, int increment) {
        item.quality += increment;

        if (item.quality > 50) {
            item.quality = 50;
        }
    }
}
