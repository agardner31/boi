package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Crop;
import model.CropStage;
import model.Inventory;
import model.Player;
import model.Plot;

public class FarmScreen implements IScreen {
    private int width;
    private int height;
    private Player player;
    private Label displayDateLabel;
    private Label moneyLabel;
    private Plot[] plots;
    private Inventory inventory;
    private GridPane inventoryPane;
    private Button inventoryButton;
    private Button marketButton;
    private HBox plotBox;
    private Button incrementTimeButton;

    public FarmScreen(int width, int height, String difficulty, Player player) {
        this.width = width;
        this.height = height;
        this.player = player;
        displayDateLabel = new Label("Day 1");
        moneyLabel = new Label("Money: $" + player.getMoney() + ".00");
        plots = player.getFarm().getPlots();
        inventory = player.getInventory();
        inventoryPane = getInventoryPane();
        inventoryButton = new Button("Inventory");
        incrementTimeButton = new Button("Next Day");
        plotBox = fillPlotPane();
    }

    private GridPane getInventoryPane() {
        inventoryPane = new GridPane();
        int j = -1;
        for (int i = 0; i < Inventory.getCapacity(); i++) {
            Crop crop;
            Label cropLabel = new Label("");
            try {
                crop = inventory.getInventoryList().get(i);
                if (crop != null) {
                    cropLabel = new Label(crop.toString("sell"));
                }
            } catch (IndexOutOfBoundsException e) { }
            cropLabel.getStyleClass().add("cropBox");
            if (i % 10 == 0) {
                j++;
            }
            /*final int targetCrop = i;
            cropLabel.setOnMouseClicked((e) -> {
                inventory.removeItem(targetCrop); //how to get the specific inventory item
            });*/

            inventoryPane.add(cropLabel, i % 10, j);
        }
        inventory.setInventoryPane(inventoryPane);
        inventoryPane.getStyleClass().add("inventoryPane");
        return inventoryPane;
    }

    public Scene getScene() {
        // farm scene

        Label inventoryLabel = new Label("Items");

        VBox inventoryWithLabel = new VBox(inventoryLabel, inventoryPane);
        inventoryWithLabel.setVisible(false);

        incrementTimeButton.setOnAction((e) -> {
            for (int i = 0; i < plots.length; i++) {
                if (plots[i].getCrop() != null) {
                    plots[i].getCrop().grow();
                }
            }
            Controller.enterFarm(player, player.getDifficulty());
        });

        inventoryButton.setOnAction((e) -> {
            if (!inventoryWithLabel.isVisible()) {
                inventoryWithLabel.setVisible(true);
            } else {
                inventoryWithLabel.setVisible(false);
            }
        });

        //moves to market scene
        marketButton = new Button("Market");
        marketButton.setVisible(true);
        marketButton.setOnAction((e) -> {
            Controller.enterMarket(player, player.getDifficulty());
        });

        VBox vbox = new VBox(moneyLabel, displayDateLabel, plotBox, inventoryWithLabel);

        inventoryButton.getStyleClass().add("inventoryButton");
        inventoryLabel.getStyleClass().add("inventoryLabel");
        moneyLabel.getStyleClass().add("moneyLabel");
        displayDateLabel.getStyleClass().add("displayDateLabel");
        plotBox.getStyleClass().add("plotBox");
        vbox.getStyleClass().add("vBox");

        HBox buttonRow = new HBox(inventoryButton, marketButton, incrementTimeButton);
        buttonRow.setSpacing(10);
        VBox finalScene = new VBox(buttonRow, vbox);
        finalScene.setStyle("-fx-background-color: #658E6E; -fx-padding: 15");

        return new Scene(finalScene, width, height);
    }

    //TO DO: ADD ABILITY TO SELECT CROPS TO PLANT FROM INVENTORY
    private HBox fillPlotPane() {
        HBox plotBox = new HBox(20);
        for (int i = 0; i < plots.length; i++) {
            Plot temp = plots[i];
            Label plotNumber = new Label("Plot#" + (i + 1));
            Label plotType;
            Label growStage;
            if (temp.getCrop() == null) {
                plotType = new Label("Empty");
                growStage = new Label("Dirt");
            } else {
                plotType = new Label(temp.getCrop().getType());
                growStage = new Label((temp.getCrop()).getStage().toString());
            }
            VBox boxOfLabels = new VBox(plotNumber, plotType, growStage);
            boxOfLabels.setOnMouseEntered((e) -> {
                boxOfLabels.setScaleX(1.5);
                boxOfLabels.setScaleY(1.5);
            });
            boxOfLabels.setOnMouseExited((e) -> {
                boxOfLabels.setScaleX(1);
                boxOfLabels.setScaleY(1);
            });

            final int index = i;
            boxOfLabels.getStyleClass().add("plotLabel");
            ImageView img = new ImageView(temp.getImg());
            img.setPreserveRatio(true);
            img.setFitHeight(120);
            Button plantAndWaterButton = new Button(getPlantAndWaterButton(temp));
            plantAndWaterButton.setOnAction((e) -> {
                if (temp.getCrop() != null) {
                    if (temp.getCrop().getStage().toString().equals("Mature")) {
                        if (!inventory.isFull()) {
                            harvestCrop(temp);
                            growStage.setText("Dirt");
                            plotType.setText("Empty");
                            plantAndWaterButton.setText("Plant");
                            img.setImage(temp.getImg());
                        }
                    } else if ((temp.getCrop()).getStage().toString().equals("Seed")) {
                        temp.getCrop().water();
                        plantAndWaterButton.setText(getPlantAndWaterButton(temp));
                        growStage.setText(temp.getCrop().getStage().toString());
                        img.setImage(temp.getImg());
                    } else if ((temp.getCrop()).getStage().toString().equals("Immature")) {
                        temp.getCrop().water();
                        plantAndWaterButton.setText(getPlantAndWaterButton(temp));
                        growStage.setText(temp.getCrop().getStage().toString());
                        img.setImage(temp.getImg());
                    } else if (temp.getCrop().getStage().equals(CropStage.DEAD)) {
                        growStage.setText("Dirt");
                        plotType.setText("Empty");
                        temp.setCrop(null);
                        plantAndWaterButton.setText("Plant");
                        img.setImage(temp.getImg());
                    }
                } else {
                    plantAndWaterButton.setText("Water");
                    plant(temp);
                    growStage.setText(temp.getCrop().getStage().toString());
                    plotType.setText(temp.getCrop().getType());
                    img.setImage(temp.getImg());
                }
            });
            VBox onePlot = new VBox(boxOfLabels, img, plantAndWaterButton);
            plotBox.getChildren().add(onePlot);
        }
        return plotBox;
    }

    private void plant(Plot temp) {
        //NOT YET FULLY IMPLEMENTED
        //TO DO: GET CROP SELECTED FROM INVENTORY, SET TEMP'S CROP TO THAT. REMOVE THAT CROP FROM INVENTORY.
        temp.setCrop(new Crop("Pumpkin", player.getDifficulty()));
    }

    private String getPlantAndWaterButton(Plot plot) {
        String string;
        if (plot.getCrop() == null || plot.getCrop().getStage().equals(CropStage.DIRT)) {
            string = "Plant";
        } else if (plot.getCrop().getStage().equals(CropStage.SEED) || plot.getCrop().getStage().equals(CropStage.IMMATURE)) {
            string  = "Water";
        } else if (plot.getCrop().getStage().equals(CropStage.MATURE)) {
            string = "Harvest";
        } else if (plot.getCrop().getStage().equals(CropStage.DEAD)) {
            string = "Remove dead crop";
        } else {
            throw new IllegalArgumentException("Illegal crop stage");
        }
        return string;
    }



    public void harvestCrop(Plot plot) {
        inventory.addToPane(plot.getCrop());
        inventory.addItem(plot.getCrop());
        plot.setCrop(null);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public Label getDisplayDateLabel() {
        return displayDateLabel;
    }

    public void setDisplayDateLabel(int day) {
        displayDateLabel = new Label("Day " + day);
    }

    public Label getMoneyLabel() {
        return moneyLabel;
    }

    public void setMoneyLabel() {
        this.moneyLabel = new Label("Money: $" + player.getMoney() + ".00");
    }

    public Plot[] getPlots() {
        return plots;
    }
}
