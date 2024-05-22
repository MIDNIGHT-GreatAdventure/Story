
package net.creatorsyndrome.storyengine.entity;

import net.creatorsyndrome.storyengine.network.StoryengineModVariables;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.creatorsyndrome.storyengine.procedures.OpenDialogWindowProcedure;
import net.creatorsyndrome.storyengine.procedures.NPCOnInitialEntitySpawnProcedure;
import net.creatorsyndrome.storyengine.procedures.NPCEntityIsHurtProcedure;
import net.creatorsyndrome.storyengine.procedures.IfModeWalkerProcedure;
import net.creatorsyndrome.storyengine.procedures.IfModeFollowerProcedure;
import net.creatorsyndrome.storyengine.procedures.IfModeAttackerProcedure;
import net.creatorsyndrome.storyengine.init.StoryengineModEntities;
import net.creatorsyndrome.storyengine.network.StoryengineModVariables;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;

import java.util.List;

@Mod.EventBusSubscriber
public class NPCEntity extends TamableAnimal {
	@SubscribeEvent
	public static void addLivingEntityToBiomes(BiomeLoadingEvent event) {
		event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(StoryengineModEntities.NPC.get(), 20, 4, 4));
	}

	public NPCEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(StoryengineModEntities.NPC.get(), world);
	}

	public NPCEntity(EntityType<NPCEntity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1, (float) 5, (float) 2, false) {
			@Override
			public boolean canUse() {
				double x = NPCEntity.this.getX();
				double y = NPCEntity.this.getY();
				double z = NPCEntity.this.getZ();
				Entity entity = NPCEntity.this;
				Level world = NPCEntity.this.level;
				return super.canUse() && IfModeFollowerProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}

			@Override
			public boolean canUse() {
				double x = NPCEntity.this.getX();
				double y = NPCEntity.this.getY();
				double z = NPCEntity.this.getZ();
				Entity entity = NPCEntity.this;
				Level world = NPCEntity.this.level;
				return super.canUse() && IfModeAttackerProcedure.execute(entity);
			}

		});
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = NPCEntity.this.getX();
				double y = NPCEntity.this.getY();
				double z = NPCEntity.this.getZ();
				Entity entity = NPCEntity.this;
				Level world = NPCEntity.this.level;
				return super.canUse() && IfModeWalkerProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, (float) 6) {
			@Override
			public boolean canUse() {
				double x = NPCEntity.this.getX();
				double y = NPCEntity.this.getY();
				double z = NPCEntity.this.getZ();
				Entity entity = NPCEntity.this;
				Level world = NPCEntity.this.level;
				return super.canUse() && IfModeFollowerProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = NPCEntity.this.getX();
				double y = NPCEntity.this.getY();
				double z = NPCEntity.this.getZ();
				Entity entity = NPCEntity.this;
				Level world = NPCEntity.this.level;
				return super.canUse() && IfModeWalkerProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(7, new FloatGoal(this));
		this.goalSelector.addGoal(8, new OpenDoorGoal(this, false));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public double getMyRidingOffset() {
		return -0.35D;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		NPCEntityIsHurtProcedure.execute(this.level, this);
		return super.hurt(source, amount);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		NPCOnInitialEntitySpawnProcedure.execute(this);
		return retval;
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
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		NPCEntity retval = StoryengineModEntities.NPC.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of().contains(stack.getItem());
	}

	public static void init() {
		SpawnPlacements.register(StoryengineModEntities.NPC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		return builder;
	}
}
