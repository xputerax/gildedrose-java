package com.gildedrose;

public class ConjuredItemUpdater implements ItemUpdater {
    @Override
    public void update(Item item) {
        if (item.sellIn <= 0) {
            item.quality -= 2;
        }

        item.quality -= 2;
        item.sellIn -= 1;

        if (item.quality < 0) {
            item.quality = 0;
        }
    }
}
