package com.gildedrose;

class GildedRose {
    Item[] items;
    AgedBrieUpdater agedBrieUpdater;
    SulfurasUpdater sulfurasUpdater;
    BackstagePassUpdater backstagePassUpdater;
    NormalItemUpdater normalItemUpdater;
    ConjuredItemUpdater conjuredItemUpdater;

    public GildedRose(Item[] items) {
        this.items = items;
        this.agedBrieUpdater = new AgedBrieUpdater();
        this.sulfurasUpdater = new SulfurasUpdater();
        this.backstagePassUpdater = new BackstagePassUpdater();
        this.normalItemUpdater = new NormalItemUpdater();
        this.conjuredItemUpdater = new ConjuredItemUpdater();
    }

    public void updateQuality() {
        for (Item item: items) {
            if (item.name.equals("Aged Brie")) {
                this.agedBrieUpdater.update(item);
            } else if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
                this.sulfurasUpdater.update(item);
            } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                this.backstagePassUpdater.update(item);
            } else if (item.name.equals("Conjured")) {
                this.conjuredItemUpdater.update(item);
            } else {
                this.normalItemUpdater.update(item);
            }
        }
    }
}
