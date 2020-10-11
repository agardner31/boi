package tests.M3;

import model.Crop;
import model.Market;
import model.Inventory;
import model.Player;
import model.CropStage;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class M3Test {

    private Player testPlayerApprenticePumpkin;
    private Player testPlayerApprenticeCorn;
    private Player testPlayerApprenticeTomato;
    private Player testPlayerOrdinaryPumpkin;
    private Player testPlayerOrdinaryCorn;
    private Player testPlayerOrdinaryTomato;
    private Player testPlayerMasterPumpkin;
    private Player testPlayerMasterCorn;
    private Player testPlayerMasterTomato;
    private Market testMarket;
    private Player player;
    private String difficulty;


    @Before
    public void setUp() {
        /*
        testPlayerApprenticePumpkin = new Player("Apprentice", "Pumpkin");
        testPlayerApprenticeCorn = new Player("Apprentice", "Corn");
        testPlayerApprenticeTomato = new Player("Apprentice", "Tomato");
        testPlayerOrdinaryPumpkin = new Player("Ordinary Joe", "Pumpkin");
        testPlayerOrdinaryCorn = new Player("Ordinary Joe", "Corn");
        testPlayerOrdinaryTomato = new Player("Ordinary Joe", "Tomato");
        testPlayerMasterPumpkin = new Player("Master Farmer", "Pumpkin");
        testPlayerMasterCorn = new Player("Master Farmer", "Corn");
        testPlayerMasterTomato = new Player("Master Farmer", "Tomato");
         */
    }

    @Test
    public void justinCropBuySellPrice() {
        Crop crop = new Crop("Pumpkin", "Apprentice", CropStage.SEED);
        Crop crop2 = new Crop("Tomato", "Apprentice", CropStage.SEED);
        Crop crop3 = new Crop("Pumpkin", "Ordinary Joe", CropStage.SEED);
        Crop crop4 = new Crop("Corn", "Ordinary Joe", CropStage.SEED);

        assertNotEquals(crop.getBuyPrice(),crop.getSellPrice());
        assertNotEquals(crop2.getBuyPrice(),crop.getSellPrice());
        assertNotEquals(crop3.getBuyPrice(),crop.getSellPrice());
        assertNotEquals(crop4.getBuyPrice(),crop.getSellPrice());
    }

    /*public void benTestCropArrayCreation() {
        Crop[] testArray1 = testPlayerApprenticeCorn.getInventory().getInventoryList();
        Crop[] testArray2 = testPlayerOrdinaryPumpkin.getInventory().getInventoryList();
        Crop[] testArray3 = testPlayerMasterTomato.getInventory().getInventoryList();

        Crop[] expected1 = new Crop[30];
        expected1[0] = new Crop("Corn", "Apprentice", CropStage.SEED);
        expected1[1] = new Crop("Corn", "Apprentice", CropStage.SEED);
        expected1[2] = new Crop("Corn", "Apprentice", CropStage.SEED);

        Crop[] expected2 = new Crop[30];
        expected2[0] = new Crop("Pumpkin", "Ordinary Joe", CropStage.SEED);
        expected2[1] = new Crop("Pumpkin", "Ordinary Joe", CropStage.SEED);
        expected2[2] = new Crop("Pumpkin", "Ordinary Joe", CropStage.SEED);

        Crop[] expected3 = new Crop[30];
        expected3[0] = new Crop("Tomato", "Master Farmer", CropStage.SEED);
        expected3[1] = new Crop("Tomato", "Master Farmer", CropStage.SEED);
        expected3[2] = new Crop("Tomato", "Master Farmer", CropStage.SEED);

        assertEquals(expected1, testArray1);
        assertEquals(expected2, testArray2);
        assertEquals(expected3, testArray3);
    }*/
}
