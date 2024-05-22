package net.creatorsyndrome.storyengine.entity;

import net.creatorsyndrome.storyengine.init.StoryengineModEntities;
import net.creatorsyndrome.storyengine.network.StoryengineModVariables;
import net.creatorsyndrome.storyengine.procedures.OpenDialogWindowProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;
import net.creatorsyndrome.storyengine.procedures.CurrentAnimationEqualsTo;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ModernNPCEntity extends TamableAnimal implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public ModernNPCEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (CurrentAnimationEqualsTo.execute(this, "Wave")) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.new2", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.new", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
        ItemStack itemstack = sourceentity.getItemInHand(hand);
        InteractionResult retval = InteractionResult.sidedSuccess(this.level.isClientSide());
        Item item = itemstack.getItem();
        if (itemstack.getItem() instanceof SpawnEggItem) {
            retval = super.mobInteract(sourceentity, hand);
        } else if (this.level.isClientSide()) {
            retval = (this.isTame() && this.isOwnedBy(sourceentity) || this.isFood(itemstack)) ? InteractionResult.sidedSuccess(this.level.isClientSide()) : InteractionResult.PASS;
        } else {
            if (this.isTame()) {
                if (this.isOwnedBy(sourceentity)) {
                    if (item.isEdible() && this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                        this.usePlayerItem(sourceentity, hand, itemstack);
                        this.heal((float) item.getFoodProperties().getNutrition());
                        retval = InteractionResult.sidedSuccess(this.level.isClientSide());
                    } else if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                        this.usePlayerItem(sourceentity, hand, itemstack);
                        this.heal(4);
                        retval = InteractionResult.sidedSuccess(this.level.isClientSide());
                    } else {
                        retval = super.mobInteract(sourceentity, hand);
                    }
                }
            } else if (this.isFood(itemstack)) {
                this.usePlayerItem(sourceentity, hand, itemstack);
                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, sourceentity)) {
                    this.tame(sourceentity);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }
                this.setPersistenceRequired();
                retval = InteractionResult.sidedSuccess(this.level.isClientSide());
            } else {
                retval = super.mobInteract(sourceentity, hand);
                if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME)
                    this.setPersistenceRequired();
            }
        }
        if (this.getPersistentData().getString("name").equals(StoryengineModVariables.MapVariables.get(this.level).whototalkwith)) {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            Entity entity = this;
            Level world = this.level;

            OpenDialogWindowProcedure.execute(world, x, y, z, sourceentity);
        }
        return retval;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_STRAY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DOLPHIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    public static void init() {
        SpawnPlacements.register(StoryengineModEntities.ModernNPC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
    }

}