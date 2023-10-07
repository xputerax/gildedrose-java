package com.gildedrose;

import org.junit.jupiter.api.Disabled;
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
    @Disabled("not sure if the original code is wrong, or i understood it incorrectly")
    void updateQuality_agedBrieQualityShouldNotExceed50() {
        Item agedBrie = new Item("Aged Brie", 0, 49);
        Item[] items = new Item[] { agedBrie };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        assertEquals(50, agedBrie.quality);
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
        Item item = new Item("regular item", 10, 49);
        Item[] items = new Item[] { item };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        gildedRose.updateQuality();

        assertTrue(item.quality <= 50);
    }

    @Test
    void updateQuality_backStagePassQualityShouldIncreaseBy1_Normally() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 10);
        Item[] items = new Item[] { backstagePass };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(11, backstagePass.quality);
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

    @Test
    void updateQuality_backStagePassQualityShouldMaxAt50() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 48);
        Item[] items = new Item[] { backstagePass };
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(50, backstagePass.quality);
    }

    @Test
    void normalItemUpdater_shouldDecreaseBothQualityAndSellIn() {
        Item normalItem = new Item("beras", 10, 10);
        GildedRose gildedRose = new GildedRose(
            new Item[] { normalItem }
        );
        gildedRose.updateQuality();
        assertEquals(9, normalItem.sellIn);
        assertEquals(9, normalItem.quality);
    }

    @Test
    void normalItemUpdater_shouldNotMakeQualityNegative() {
        Item normalItem = new Item("beras", 10, 1);
        GildedRose gildedRose = new GildedRose(
            new Item[] {
                normalItem
            }
        );
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        assertEquals(10 - 1 - 1, normalItem.sellIn);
        assertEquals(0, normalItem.quality);
    }

    @Test
    void conjuredItemUpdater_shouldDecreaseNonExpiredItemQualityTwiceAsFast() {
        Item item = new Item("Conjured", 10, 10);
        GildedRose gildedRose = new GildedRose(new Item[] {
            item
        });
        gildedRose.updateQuality();
        assertEquals(10 - 1, item.sellIn);
        assertEquals(10 - 2, item.quality);
    }

    @Test
    void conjuredItemUpdater_shouldDecreaseExpiredItemQualityQuadAsFast() {
        // TODO: faham balik requirement dia
        Item item = new Item("Conjured", 0, 10);
        GildedRose gildedRose = new GildedRose(new Item[] {
            item
        });
        gildedRose.updateQuality();
        assertEquals(10 - 2 - 2, item.quality);
    }
}
