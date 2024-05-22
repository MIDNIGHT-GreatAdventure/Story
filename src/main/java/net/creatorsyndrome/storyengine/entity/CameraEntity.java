package net.creatorsyndrome.storyengine.entity;

import net.creatorsyndrome.storyengine.init.StoryengineModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.TickEvent.ClientTickEvent;

public class CameraEntity extends Animal {
    private Entity targetEntity;
    public boolean active = false;

    public double tox = 0.0;
    public double toy = 0.0;
    public double toz = 0.0;

    public double stx = 0.0;
    public double sty = 0.0;
    public double stz = 0.0;


    public double px = 0.0;
    public double py = 0.0;
    public double pz = 0.0;


    public int duration = 0;
    public int cur_tick = 0;

    public double speed = 1.0;

    public CameraEntity(EntityType<CameraEntity> type, Level world) {
        super(type, world);
        setNoAi(true);
    }

    protected void registerGoals() {

    }

    public void setPlayerPosition(double a, double b, double c) {
        this.px = a;
        this.py = b;
        this.pz = c;
    }

    public void setDestination(double a, double b, double c) {
        this.tox = a;
        this.toy = b;
        this.toz = c;
    }

    public void setStartPosition(double a, double b, double c) {
        this.stx = a;
        this.sty = b;
        this.stz = c;
    }

    @Override
    public void tick() {
        super.tick();
        if (active) {
            this.level.players().get(0).setPos(this.position());
            this.level.players().get(0).setXRot(this.getXRot());
            this.level.players().get(0).setYRot(this.getYRot());
            this.setPos(stx + ((tox - stx) * cur_tick / duration), sty + ((toy - sty) * cur_tick / duration), stz + ((toz - stz) * cur_tick / duration));
            cur_tick += 1;
        }
        if (this.getX() == tox && this.getY() == toy && this.getZ() == toz) {
            active = false;
            cur_tick = 0;
            this.level.players().get(0).setPos(px, py, pz);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
        NPCEntity retval = StoryengineModEntities.NPC.get().create(serverWorld);
        retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
        return retval;
    }

    public static void init() {
        SpawnPlacements.register(StoryengineModEntities.CAMERA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
    }
}