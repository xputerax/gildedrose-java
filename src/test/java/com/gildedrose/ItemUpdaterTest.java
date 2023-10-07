package com.gildedrose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemUpdaterTest {
    AgedBrieUpdater agedBrieUpdater;
    SulfurasUpdater sulfurasUpdater;
    BackstagePassUpdater backstagePassUpdater;
    NormalItemUpdater normalItemUpdater;

    @BeforeEach
    public void setAgedBrieUpdater() {
        this.agedBrieUpdater = new AgedBrieUpdater();
        this.sulfurasUpdater = new SulfurasUpdater();
        this.backstagePassUpdater = new BackstagePassUpdater();
        this.normalItemUpdater = new NormalItemUpdater();
    }

    @Test
    void agedBrieUpdater_shouldIncreaseAgedBrieQuality() {
        Item agedBrie = new Item("Aged Brie", 10, 10);
        this.agedBrieUpdater.update(agedBrie);
        this.agedBrieUpdater.update(agedBrie);
        this.agedBrieUpdater.update(agedBrie);
        assertEquals(10 + 3, agedBrie.quality);
    }

    @Test
    @Disabled("not sure if i misunderstood the requirement")
    void agedBrieUpdater_qualityShouldNotExceed50() {
        Item agedBrie = new Item("Aged Brie", 0, 49);
        this.agedBrieUpdater.update(agedBrie);
        this.agedBrieUpdater.update(agedBrie);
        this.agedBrieUpdater.update(agedBrie);
        this.agedBrieUpdater.update(agedBrie);
        assertEquals(50, agedBrie.quality);
    }

    @Test
    void agedBrieUpdater_shouldIncreaseExpiredAgedBrieQuality() {
        Item agedBrie = new Item("Aged Brie", 1, 10);
        this.agedBrieUpdater.update(agedBrie); // sellin = 0, quality + 1
        this.agedBrieUpdater.update(agedBrie); // sellin = -1, quality += 2
        this.agedBrieUpdater.update(agedBrie); // sellin = -2, quality += 2
        assertEquals(10 + 1 + 2 + 2, agedBrie.quality);
    }

    @Test
    void sulfurasUpdater_shouldNotDecreaseSulfurasQuality() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 10, 10);
        this.sulfurasUpdater.update(sulfuras);
        assertEquals(10, sulfuras.sellIn);
        assertEquals(10, sulfuras.quality);
    }

    @Test
    void backstagePassUpdater_backStagePassQualityShouldIncreaseBy1_Normally() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 10);
        this.backstagePassUpdater.update(backstagePass);
        assertEquals(11, backstagePass.quality);
    }

    @Test
    void backstagePassUpdater_backStagePassQualityShouldIncreaseBy2_10daysBeforeConcert() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10);

        this.backstagePassUpdater.update(backstagePass);
        this.backstagePassUpdater.update(backstagePass);

        assertEquals(10 + 2 + 2, backstagePass.quality);
    }

    @Test
    void backstagePassUpdater_backStagePassQualityShouldIncreaseBy3_5daysBeforeConcert() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10);
        Item[] items = new Item[] { backstagePass1, backstagePass2 };

        for (Item item: items) {
            this.backstagePassUpdater.update(item);
        }

        assertEquals(10 + 3, backstagePass1.quality);
        assertEquals(10 + 3, backstagePass2.quality);
    }

    @Test
    void backstagePassUpdater_backStagePassQualityShouldZeroAfterConcert() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10);
        this.backstagePassUpdater.update(backstagePass);
        this.backstagePassUpdater.update(backstagePass);
        assertEquals(0, backstagePass.quality);
    }

    @Test
    void backstagePassUpdater_backStagePassQualityShouldMaxAt50() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 48);
        this.backstagePassUpdater.update(backstagePass);
        assertEquals(50, backstagePass.quality);
    }

    @Test
    void normalItemUpdater_shouldDecreaseBothQualityAndSellIn() {
        Item normalItem = new Item("beras", 10, 10);
        this.normalItemUpdater.update(normalItem);
        assertEquals(9, normalItem.sellIn);
        assertEquals(9, normalItem.quality);
    }

    @Test
    void normalItemUpdater_shouldNotMakeQualityNegative() {
        Item normalItem = new Item("beras", 10, 1);
        this.normalItemUpdater.update(normalItem);
        this.normalItemUpdater.update(normalItem);
        assertEquals(10 - 1 - 1, normalItem.sellIn);
        assertEquals(0, normalItem.quality);
    }

    @Test
    void normalItemUpdater_shouldDecreaseItemQualityTwiceAsFastAfterSellByDateHasPassed() {
        // TODO: tak sure test case ni sepatutnya apply kat semua item or what
        Item item = new Item("some item", 1, 10);
        this.normalItemUpdater.update(item);
        assertEquals(10 - 1, item.quality);
        this.normalItemUpdater.update(item);
        assertEquals(10 - 1 - 2, item.quality);
    }
}
