package com.freedsuniverse.sidecraft.world;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import com.freedsuniverse.sidecraft.Settings;
import com.freedsuniverse.sidecraft.Sidecraft;
import com.freedsuniverse.sidecraft.entity.Entity;
import com.freedsuniverse.sidecraft.material.Material;

public class World {
    private HashMap<String, Block> blocks;

    private LinkedList<Entity> entities;
    private LinkedList<String> actives;
    
    private String name;

    private int xLength;
    private int yLength;
    private Random rnd = new Random();

    public World(String n) {
        this.name = n;
        this.blocks = new HashMap<String, Block>();
        this.xLength = Sidecraft.width / Settings.BLOCK_SIZE;
        this.yLength = Sidecraft.height / Settings.BLOCK_SIZE;

        actives = new LinkedList<String>();
        entities = new LinkedList<Entity>();
        draw();
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Block> getBlocks() {
        return blocks;
    }

    public void setBlockAt(Location loc, Block b){
        int x = (int)Math.floor(loc.getX());
        int y = (int)Math.ceil(loc.getY());

        
        blocks.put(x + "," + y, b);
        blocks.get(x + "," + y).setLocation(loc);
        
        if(b instanceof ActiveBlock){
            actives.add(x + "," + y);
        }
    }
    
    public Block getBlockAt(Location coordinates) {
        int x = (int)Math.floor(coordinates.getX());
        int y = (int)Math.ceil(coordinates.getY());

        if (!blocks.containsKey(x + "," + y)) {
            if (coordinates.getY() > -1) {
                blocks.put(x + "," + y, new Block(Material.AIR));
            }
            else if (coordinates.getY() == -1) {
                blocks.put(x + "," + y, new Block(Material.GRASS));
            }
            else if (coordinates.getY() == -2) {

                if (rnd.nextInt(2) == 0) {
                    blocks.put(x + "," + y, new Block(Material.DIRT));
                }
                else {
                    blocks.put(x + "," + y, new Block(Material.STONE));
                }
            }
            else if (coordinates.getY() < -2) {
                boolean hasOreAround = hasOreAround(x, y);
                if (hasOreAround && rnd.nextInt(3) == 0 || rnd.nextInt(64) == 0) {
                    blocks.put(x + "," + y, new Block(getRandomOreMaterial()));
                } else {
                    blocks.put(x + "," + y, new Block(Material.STONE));
                }
            }
            blocks.get(x + "," + y).setLocation(coordinates);
        }

        return blocks.get(x + "," + y);
    }

    private boolean hasOreAround(int x, int y) {
        Block left = blocks.get((x - 1) + "," + y);
        if (left != null && isOre(left.getType())) return true;
        Block right = blocks.get((x + 1) + "," + y);
        if (right != null && isOre(right.getType())) return true;
        Block top = blocks.get(x + "," + (y + 1));
        if (top != null && isOre(top.getType())) return true;
        Block bottom = blocks.get(x + "," + (y - 1));
        if (bottom != null && isOre(bottom.getType())) return true;
        return false;
    }

    private boolean isOre(Material mat) {
        return mat == Material.COAL_ORE || mat == Material.SILVER_ORE || mat == Material.GOLD_ORE;
    }

    private Material getRandomOreMaterial() {
        int oreType = rnd.nextInt(5);
        switch (oreType) {
            case 0:
            case 1:
            case 2:
                return Material.COAL_ORE;
            case 3:
                return Material.SILVER_ORE;
            case 4:
                return Material.GOLD_ORE;
            default:
                return Material.COAL_ORE; //should never get here
        }
    }

    public void update() {
      updateEntities();
      updateBlocks();
    }

    private void updateBlocks() {
        for(int x = 0; x < actives.size(); x++){
            ActiveBlock b = (ActiveBlock)blocks.get(actives.get(x));
            b.update();
        }
        
    }

    public void generateWorld(){
        Location upperExtreme = new Location(Math.floor(Sidecraft.player.coordinates.getX()) + xLength / 2 + 1, Math.ceil((Sidecraft.player.coordinates.getY() + yLength / 2) + 1), this.getName());
        Location lowerExtreme = new Location(Math.floor(Sidecraft.player.coordinates.getX()) - xLength / 2 - 1, Math.ceil((Sidecraft.player.coordinates.getY() - yLength / 2) - 1), this.getName());
        
        int xDistance = (int)Math.abs(upperExtreme.getX() - lowerExtreme.getX());
        int yDistance = (int)Math.abs(upperExtreme.getY() - lowerExtreme.getY());
        
        for (int x = 0; x <= xDistance; x++) {
            for (int y = 0; y <= yDistance; y++) {
                double xCoord = upperExtreme.getX() + increment(x, (int)upperExtreme.getX(), (int)lowerExtreme.getX());
                double yCoord = upperExtreme.getY() + increment(y, (int)upperExtreme.getY(), (int)lowerExtreme.getY());

                getBlockAt(new Location(xCoord, yCoord, getName()));
            }
        }
    }
    
    public void updateEntities() {
        for (int x = 0; x < entities.size(); x++) {
            entities.get(x).update();
        }
    }

    public void draw() {
        Location upperExtreme = new Location(Math.floor(Sidecraft.player.coordinates.getX()) + xLength / 2 + 1, Math.ceil((Sidecraft.player.coordinates.getY() + yLength / 2) + 1), this.getName());
        Location lowerExtreme = new Location(Math.floor(Sidecraft.player.coordinates.getX()) - xLength / 2 - 1, Math.ceil((Sidecraft.player.coordinates.getY() - yLength / 2) - 1), this.getName());

        int xDistance = (int)Math.abs(upperExtreme.getX() - lowerExtreme.getX());
        int yDistance = (int)Math.abs(upperExtreme.getY() - lowerExtreme.getY());

        for (int x = 0; x <= xDistance; x++) {
            for (int y = 0; y <= yDistance; y++) {
                double xCoord = upperExtreme.getX() + increment(x, (int)upperExtreme.getX(), (int)lowerExtreme.getX());
                double yCoord = upperExtreme.getY() + increment(y, (int)upperExtreme.getY(), (int)lowerExtreme.getY());

                getBlockAt(new Location(xCoord, yCoord, getName())).draw();
            }
        }

        for (int x = 0; x < entities.size(); x++) {
            entities.get(x).draw();
        }
    }
    
    private int increment(int x, int compare1, int compare2) {
        if (compare1 > compare2) {
            return -1 * x;
        }
        else {
            return x;
        }
    }

    public void unregisterEntity(Entity e) {
        if(entities.contains(e))
            entities.remove(e);
    }

    public void registerEntity(Entity e) {
        entities.add(e);
    }
}
