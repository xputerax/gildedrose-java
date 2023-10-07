package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    @Test
    void updateQuality_shouldNotDecreaseSulfurasQuality() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 10, 10);
        Item[] items = new Item[] { sulfuras };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(10, sulfuras.sellIn);
        assertEquals(10, sulfuras.quality);
    }

    @Test
    void updateQuality_shouldIncreaseAgedBrieQuality() {
        Item agedBrie = new Item("Aged Brie", 10, 10);
        Item[] items = new Item[] { agedBrie };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        assertEquals(10 + 3, agedBrie.quality);
    }

    @Test
    void updateQuality_shouldIncreaseExpiredAgedBrieQuality() {
        Item agedBrie = new Item("Aged Brie", 1, 10);
        Item[] items = new Item[] { agedBrie };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality(); // sellin = 0, quality + 1
        gildedRose.updateQuality(); // sellin = -1, quality += 2
        gildedRose.updateQuality(); // sellin = -2, quality += 2
        assertEquals(10 + 1 + 2 + 2, agedBrie.quality);
    }

    @Test
    void updateQuality_shouldDecreaseItemQualityTwiceAsFastAfterSellByDateHasPassed() {
        Item item = new Item("some item", 1, 10);
        Item[] items = new Item[] { item };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(10 - 1, item.quality);
        gildedRose.updateQuality();
        assertEquals(10 - 1 - 2, item.quality);
    }

    @Test
    void updateQuality_shouldNotMakeQualityNegative() {
        Item item = new Item("regular item", 10, 1);
        Item[] items = new Item[] { item };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        gildedRose.updateQuality();

        assertEquals(0, item.quality);
    }

    @Test
    void updateQuality_shouldMaxQualityAt50() {
        Item item = new Item("Aged Brie", 10, 49);
        Item[] items = new Item[] { item };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        gildedRose.updateQuality();

        assertTrue(item.quality <= 50);
    }

    @Test
    void updateQuality_backStagePassQualityShouldIncreaseBy2_10daysBeforeConcert() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10);
        Item[] items = new Item[] { backstagePass };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        gildedRose.updateQuality();

        assertEquals(10 + 2 + 2, backstagePass.quality);
    }

    @Test
    void updateQuality_backStagePassQualityShouldIncreaseBy3_5daysBeforeConcert() {
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10);
        Item[] items = new Item[] { backstagePass1, backstagePass2 };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();

        assertEquals(10 + 3, backstagePass1.quality);
        assertEquals(10 + 3, backstagePass2.quality);
    }

    @Test
    void updateQuality_backStagePassQualityShouldZeroAfterConcert() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10);
        Item[] items = new Item[] { backstagePass };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        assertEquals(0, backstagePass.quality);
    }

}
