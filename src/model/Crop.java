package model;

public class Crop {
    private String type;

    private CropStage stage;

    private int buyPrice;

    private int sellPrice;

    private int baseBuyPrice;

    private int baseSellPrice;

    public Crop(String type, String difficulty) {
        this(type, difficulty, CropStage.DIRT);
    }

    public Crop(String type, String difficulty, CropStage stage) {
        this.type = type;
        this.stage = stage;
        setPrice(difficulty, type);
    }

    private void setPrice(String difficulty, String type) {
        if (difficulty.equals("Apprentice")) {
            setPriceHelper(type, 2);
        } else if (difficulty.equals("Ordinary Joe")) {
            setPriceHelper(type, 1);
        } else {
            setPriceHelper(type, .5);
        }
    }

    private void setPriceHelper(String type, double difficultyMultiplier) {
        if (type.equals("Pumpkin")) {
            buyPrice = 20;
            baseBuyPrice = buyPrice;
            sellPrice = (int) (buyPrice * difficultyMultiplier);
            baseSellPrice = sellPrice;
        } else if (type.equals("Corn")) {
            buyPrice = 10;
            baseBuyPrice = buyPrice;
            sellPrice = (int) (buyPrice * difficultyMultiplier);
            baseSellPrice = sellPrice;
        } else if (type.equals("Tomato")) {
            buyPrice = 5;
            baseBuyPrice = buyPrice;
            sellPrice = (int) (buyPrice * difficultyMultiplier);
            baseSellPrice = buyPrice;
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

    public int getBaseBuyPrice() {
        return baseBuyPrice;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public String getType() {
        return type;
    }

    public CropStage getStage() {
        return stage;
    }

    public void setStage(CropStage stage) {
        this.stage = stage;
    }

    public CropStage incrementStage() { //make sure to change label price by calling to String again
        if (stage.equals(CropStage.DIRT)) {
            stage = CropStage.SEED;
        } else if (stage.equals(CropStage.SEED)) {
            stage = CropStage.IMMATURE;
        } else if (stage.equals(CropStage.IMMATURE)) {
            stage = CropStage.MATURE;
        } else if (stage.equals(CropStage.MATURE)) {
            stage = CropStage.HARVESTED;
            //inventory.addItem()
            //
            //
            ///
            //
            //




        } else if (stage.equals(CropStage.HARVESTED)) {
            stage = CropStage.DIRT;
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
        } else if (type.equals("sell")) {
            return this.type + "\n" + stage.toString() + "\n" + "$" + sellPrice + ".00";
        } else {
            return this.type + "\n" + stage.toString() + "\n" + "$0.00";
        }
    }
}
