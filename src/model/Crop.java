package model;

import controller.Controller;

import java.util.HashMap;

public class Crop {
    private String type;

    private CropStage stage;

    private int buyPrice;

    private int sellPrice;

    public Crop(String type, String difficulty) {
        this(type, difficulty, CropStage.SEED);
    }

    public Crop(String type, String difficulty, CropStage stage) {
        this.type = type;
        this.stage = stage;
        setPrice(difficulty, type);
    }

    private void setPrice(String difficulty, String type) {
        if (difficulty.equals("Apprentice")) {
            setPriceHelper(type, 3);
        } else if (difficulty.equals("Ordinary Joe")) {
            setPriceHelper(type, 2);
        } else {
            setPriceHelper(type, 1);
        }
    }

    private void setPriceHelper(String type, int difficulty) {
        if (type.equals("Pumpkin")) {
            buyPrice = 8 / difficulty;
            sellPrice = 16 * difficulty;
        } else if (type.equals("Corn")) {
            buyPrice = 5 / difficulty;
            sellPrice = 10 * difficulty;
        } else if (type.equals("Tomato")) {
            buyPrice = 3 / difficulty;
            sellPrice = 6 * difficulty;
        }
        sellPrice = (int) (sellPrice + Math.random() * .5 * sellPrice - .25 * sellPrice);
        buyPrice = (int) (buyPrice + Math.random() * .5 * buyPrice - .25 * buyPrice);
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getType() {
        return type;
    }

    public CropStage getStage() {
        return stage;
    }

    public CropStage incrementStage() { //make sure to change label price by calling to String again
        if (stage.equals(CropStage.SEED)) {
            stage = CropStage.IMMATURE;
        } else if (stage.equals(CropStage.IMMATURE)) {
            stage = CropStage.MATURE;
        }
        return stage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Crop) {
            Crop temp = (Crop) obj;
            boolean typeCheck = type.equals(temp.getType());
            boolean stageCheck = stage.equals(temp.getStage());
            return typeCheck && stageCheck;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return toString("sell");
    }

    public String toString(String type) {
        if (type.equals("buy")) {
            return this.type + "\n" + stage.toString() + "\n" + "$" + buyPrice + ".00";
        } else if (stage.equals(CropStage.MATURE)) {
            return this.type + "\n" + stage.toString() + "\n" + "$" + sellPrice + ".00";
        } else {
            return this.type + "\n" + stage.toString() + "\n" + "$0.00";
        }
    }
}
