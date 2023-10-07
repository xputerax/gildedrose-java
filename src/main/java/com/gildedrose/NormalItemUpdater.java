package com.gildedrose;

public class NormalItemUpdater implements ItemUpdater {
    @Override
    public void update(Item item) {
        if (item.sellIn <= 0) {
            item.quality -= 1;
        }

        item.quality -= 1;
        item.sellIn -= 1;

        if (item.quality < 0) {
            item.quality = 0;
        }
    }
}
