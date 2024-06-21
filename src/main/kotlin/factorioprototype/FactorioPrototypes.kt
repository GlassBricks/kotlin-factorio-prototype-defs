// Automatically generated file, do not edit
@file:OptIn(ExperimentalSerializationApi::class)

package factorioprototype

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Entity with energy source with specialised animation for charging/discharging. Used for the
 * [accumulator](https://wiki.factorio.com/Accumulator) entity.
 */
@Serializable(AccumulatorPrototype.Serializer::class)
@SerialName("accumulator")
public open class AccumulatorPrototype : EntityWithOwnerPrototype() {
  /**
   * The capacity of the energy source buffer specifies the capacity of the accumulator.
   */
  public val energy_source: ElectricEnergySource by fromJson()

  public val picture: Sprite? by fromJson()

  /**
   * Count of ticks to preserve the animation even when the charging ends. Used to prevent rapid
   * blinking of the accumulator with unstable need to use it.
   */
  public val charge_cooldown: UShort by fromJson()

  /**
   * How long (in ticks) the animation will last after discharge has been initialized.
   */
  public val discharge_cooldown: UShort by fromJson()

  public val charge_animation: Animation? by fromJson()

  /**
   * Only loaded if `charge_animation` is defined.
   */
  public val charge_light: LightDefinition? by fromJson()

  public val discharge_animation: Animation? by fromJson()

  /**
   * Only loaded if `discharge_animation` is defined.
   */
  public val discharge_light: LightDefinition? by fromJson()

  /**
   * Defines how wires visually connect to this accumulator.
   */
  public val circuit_wire_connection_point: WireConnectionPoint? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * The pictures displayed for circuit connections to this accumulator.
   */
  public val circuit_connector_sprites: CircuitConnectorSprites? by fromJson()

  /**
   * The name of the signal that is the default for when an accumulator is connected to the circuit
   * network.
   */
  public val default_output_signal: SignalIDConnector? by fromJson()

  public object Serializer : JsonReaderSerializer<AccumulatorPrototype>(AccumulatorPrototype::class)
}

/**
 * This prototype definition is used for the in-game achievements.
 */
@Serializable(AchievementPrototype.Serializer::class)
@SerialName("achievement")
public open class AchievementPrototype : PrototypeBase() {
  /**
   * Can't be an empty array.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * Path to the icon file.
   *
   * Mandatory if `icons` is not defined.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Mandatory if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  /**
   * Unusable by mods, as this refers to unlocking the achievement through Steam.
   */
  public val steam_stats_name: String? by fromJson()

  /**
   * If this is set to `false`, it is not possible to complete the achievement on the peaceful
   * difficulty setting or when the enemy base generation settings have been changed.
   */
  public val allowed_without_fight: Boolean? by fromJson()

  public val hidden: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<AchievementPrototype>(AchievementPrototype::class)
}

/**
 * Used by [discharge defense](https://wiki.factorio.com/Discharge_defense) and [personal laser
 * defense](https://wiki.factorio.com/Personal_laser_defense).
 */
@Serializable(ActiveDefenseEquipmentPrototype.Serializer::class)
@SerialName("active-defense-equipment")
public open class ActiveDefenseEquipmentPrototype : EquipmentPrototype() {
  public val automatic: Boolean by fromJson()

  public val attack_parameters: AttackParameters by fromJson()

  public object Serializer :
      JsonReaderSerializer<ActiveDefenseEquipmentPrototype>(ActiveDefenseEquipmentPrototype::class)
}

@Serializable
public enum class AmbientSoundTrackType {
  /**
   * This track is only played in the main menu.
   */
  `menu-track`,
  /**
   * This track is played interleaved (alternating) with `"interlude"` tracks.
   */
  `main-track`,
  /**
   * This track is considered a main track, with no functional difference to `"main-track"`.
   */
  `early-game`,
  /**
   * This track is considered a main track, with no functional difference to `"main-track"`.
   */
  `late-game`,
  /**
   * This track is played in between the main tracks.
   */
  interlude,
}

/**
 * This prototype is used to make sound while playing the game. This includes the game's
 * [music](https://store.steampowered.com/app/436090/Factorio__Soundtrack/), composed by Daniel James
 * Taylor.
 */
@Serializable(AmbientSound.Serializer::class)
@SerialName("ambient-sound")
public open class AmbientSound : JsonReader(), AnyPrototype {
  /**
   * Specification of the type of the prototype.
   */
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Unique textual identification of the prototype.
   */
  public val name: String by fromJson()

  /**
   * The sound file and volume.
   */
  public val sound: Sound by fromJson()

  /**
   * Lets the game know in what instances the audio file is played.
   */
  public val track_type: AmbientSoundTrackType by fromJson()

  public val weight: Double? by fromJson()

  public object Serializer : JsonReaderSerializer<AmbientSound>(AmbientSound::class)
}

/**
 * An ammo category. Each weapon has an ammo category, and can use any ammo with the same ammo
 * category. Ammo categories can also be upgraded by technologies.
 */
@Serializable(AmmoCategory.Serializer::class)
@SerialName("ammo-category")
public open class AmmoCategory : PrototypeBase() {
  public val bonus_gui_order: Order? by fromJson()

  public object Serializer : JsonReaderSerializer<AmmoCategory>(AmmoCategory::class)
}

/**
 * Ammo used for a gun.
 */
@Serializable(AmmoItemPrototype.Serializer::class)
@SerialName("ammo")
public open class AmmoItemPrototype : ItemPrototype() {
  /**
   * When using a plain [AmmoType](prototype:AmmoType) (no array), the ammo type applies to
   * everything (`"default"`).
   *
   * When using an array of AmmoTypes, they have the additional
   * [AmmoType::source_type](prototype:AmmoType::source_type) property.
   */
  public val ammo_type: ItemOrList<AmmoType> by fromJson()

  /**
   * Number of shots before ammo item is consumed. Must be >= `1`.
   */
  public val magazine_size: Float? by fromJson()

  /**
   * Amount of extra time (in ticks) it takes to reload the weapon after depleting the magazine.
   * Must be >= `0`.
   */
  public val reload_time: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<AmmoItemPrototype>(AmmoItemPrototype::class)
}

/**
 * A turret that consumes [ammo items](prototype:AmmoItemPrototype).
 */
@Serializable(AmmoTurretPrototype.Serializer::class)
@SerialName("ammo-turret")
public open class AmmoTurretPrototype : TurretPrototype() {
  public val inventory_size: ItemStackIndex by fromJson()

  public val automated_ammo_count: ItemCountType by fromJson()

  /**
   * Shift of the "alt-mode icon" relative to the turret's position.
   */
  public val entity_info_icon_shift: Vector? by fromJson()

  public object Serializer : JsonReaderSerializer<AmmoTurretPrototype>(AmmoTurretPrototype::class)
}

@Serializable
public enum class AnimationPrototypeRunMode {
  forward,
  backward,
  `forward-then-backward`,
}

/**
 * Specifies an animation that can be used with
 * [LuaRendering::draw_animation](runtime:LuaRendering::draw_animation) at runtime.
 */
@Serializable(AnimationPrototype.Serializer::class)
@SerialName("animation")
public open class AnimationPrototype : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the animation. Can be used with
   * [LuaRendering::draw_animation](runtime:LuaRendering::draw_animation) at runtime.
   */
  public val name: String by fromJson()

  /**
   * If this property is present, all Animation definitions have to be placed as entries in the
   * array, and they will all be loaded from there. `layers` may not be an empty table. Each definition
   * in the array may also have the `layers` property.
   *
   * `animation_speed` and `max_advance` of the first layer are used for all layers. All layers will
   * run at the same speed.
   *
   * If this property is present, all other properties besides `name` and `type` are ignored.
   */
  public val layers: List<Animation>? by fromJson()

  /**
   * Only loaded if `layers` is not defined. Mandatory if `stripes` is not defined.
   *
   * The path to the sprite file to use.
   */
  public val filename: FileName? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * If this property exists and high resolution sprites are turned on, this is used to load the
   * Animation.
   */
  public val hr_version: Animation? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val priority: SpritePriority? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val flags: SpriteFlags? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * The width and height of one frame. If this is a tuple, the first member of the tuple is the
   * width and the second is the height. Otherwise the size is both width and height. Width and height
   * may only be in the range of 0-8192.
   */
  public val size: ItemOrTuple2<SpriteSizeType>? by fromJson()

  /**
   * Only loaded if `layers` is not defined. Mandatory if `size` is not defined.
   *
   * Width of one frame in pixels, from 0-8192.
   */
  public val width: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `layers` is not defined. Mandatory if `size` is not defined.
   *
   * Height of one frame in pixels, from 0-8192.
   */
  public val height: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Horizontal position of the animation in the source file in pixels.
   */
  public val x: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Vertical position of the animation in the source file in pixels.
   */
  public val y: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Loaded only when `x` and `y` are both `0`. The first member of the tuple is `x` and the second
   * is `y`.
   */
  public val position: Tuple2<SpriteSizeType>? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * The shift in tiles. `util.by_pixel()` can be used to divide the shift by 32 which is the usual
   * pixel height/width of 1 tile in normal resolution. Note that 32 pixel tile height/width is not
   * enforced anywhere - any other tile height or width is also possible.
   */
  public val shift: Vector? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Values other than `1` specify the scale of the sprite on default zoom. A scale of `2` means
   * that the picture will be two times bigger on screen (and thus more pixelated).
   */
  public val scale: Double? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Only one of `draw_as_shadow`, `draw_as_glow` and `draw_as_light` can be true. This takes
   * precedence over `draw_as_glow` and `draw_as_light`.
   */
  public val draw_as_shadow: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Only one of `draw_as_shadow`, `draw_as_glow` and `draw_as_light` can be true. This takes
   * precedence over `draw_as_light`.
   *
   * Draws first as a normal sprite, then again as a light layer. See
   * [https://forums.factorio.com/91682](https://forums.factorio.com/91682).
   */
  public val draw_as_glow: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Only one of `draw_as_shadow`, `draw_as_glow` and `draw_as_light` can be true.
   */
  public val draw_as_light: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Only loaded if this is an icon, that is it has the flag `"group=icon"` or `"group=gui"`.
   *
   * Note that `mipmap_count` doesn't make sense in an animation, as it is not possible to layout
   * mipmaps in a way that would load both the animation and the mipmaps correctly (besides animations
   * with just one frame). See [here](https://forums.factorio.com/viewtopic.php?p=549058#p549058).
   */
  public val mipmap_count: UByte? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val apply_runtime_tint: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val tint: Color? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val blend_mode: BlendMode? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Minimal mode is entered when mod loading fails. You are in it when you see the gray box after
   * (part of) the loading screen that tells you a mod error
   * ([Example](https://cdn.discordapp.com/attachments/340530709712076801/532315796626472972/unknown.png)).
   * Modders can ignore this property.
   */
  public val load_in_minimal_mode: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Whether alpha should be pre-multiplied.
   */
  public val premul_alpha: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Unused.
   */
  public val generate_sdf: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val run_mode: AnimationPrototypeRunMode? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Can't be `0`.
   */
  public val frame_count: UInt? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Once the specified number of pictures is loaded, other pictures are loaded on other line. This
   * is to allow having longer animations in matrix, to input files with too high width. The game
   * engine limits the width of any input files to 8192px, so it is compatible with most graphics
   * cards. `0` means that all the pictures are in one horizontal line.
   */
  public val line_length: UInt? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Modifier of the animation playing speed, the default of `1` means one animation frame per tick
   * (60 fps). The speed of playing can often vary depending on the usage (output of steam engine for
   * example). Has to be greater than `0`.
   *
   * If `layers` are used, the `animation_speed` only has to be defined in one layer. All layers
   * will run at the same speed.
   */
  public val animation_speed: Float? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * If `layers` are used, `max_advance` of the first layer is used for all layers.
   *
   * Maximum amount of frames the animation can move forward in one update.
   */
  public val max_advance: Float? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * How many times to repeat the animation to complete an animation cycle. E.g. if one layer is 10
   * frames, a second layer of 1 frame would need `repeat_count = 10` to match the complete cycle.
   */
  public val repeat_count: UByte? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val dice: UByte? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val dice_x: UByte? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val dice_y: UByte? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val frame_sequence: AnimationFrameSequence? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val stripes: List<Stripe>? by fromJson()

  public object Serializer : JsonReaderSerializer<AnimationPrototype>(AnimationPrototype::class)
}

/**
 * An [arithmetic combinator](https://wiki.factorio.com/Arithmetic_combinator).
 */
@Serializable(ArithmeticCombinatorPrototype.Serializer::class)
@SerialName("arithmetic-combinator")
public open class ArithmeticCombinatorPrototype : CombinatorPrototype() {
  public val plus_symbol_sprites: Sprite4Way? by fromJson()

  public val minus_symbol_sprites: Sprite4Way? by fromJson()

  public val multiply_symbol_sprites: Sprite4Way? by fromJson()

  public val divide_symbol_sprites: Sprite4Way? by fromJson()

  public val modulo_symbol_sprites: Sprite4Way? by fromJson()

  public val power_symbol_sprites: Sprite4Way? by fromJson()

  public val left_shift_symbol_sprites: Sprite4Way? by fromJson()

  public val right_shift_symbol_sprites: Sprite4Way? by fromJson()

  public val and_symbol_sprites: Sprite4Way? by fromJson()

  public val or_symbol_sprites: Sprite4Way? by fromJson()

  public val xor_symbol_sprites: Sprite4Way? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ArithmeticCombinatorPrototype>(ArithmeticCombinatorPrototype::class)
}

/**
 * Armor to wear on your in-game [character](prototype:CharacterPrototype) for defense and buffs.
 */
@Serializable(ArmorPrototype.Serializer::class)
@SerialName("armor")
public open class ArmorPrototype : ToolPrototype() {
  /**
   * Name of the [EquipmentGridPrototype](prototype:EquipmentGridPrototype) that this armor has.
   */
  public val equipment_grid: EquipmentGridID? by fromJson()

  /**
   * What amount of damage the armor takes on what type of damage is incoming.
   */
  public val resistances: List<Resistance>? by fromJson()

  /**
   * By how many slots the inventory of the player is expanded when the armor is worn.
   */
  public val inventory_size_bonus: ItemStackIndex? by fromJson()

  public object Serializer : JsonReaderSerializer<ArmorPrototype>(ArmorPrototype::class)
}

/**
 * The arrows used for example in the campaign, they are literally just arrows.
 */
@Serializable(ArrowPrototype.Serializer::class)
@SerialName("arrow")
public open class ArrowPrototype : EntityPrototype() {
  public val arrow_picture: Sprite by fromJson()

  public val circle_picture: Sprite? by fromJson()

  public val blinking: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<ArrowPrototype>(ArrowPrototype::class)
}

/**
 * The entity spawned by the [artillery targeting
 * remote](https://wiki.factorio.com/Artillery_targeting_remote).
 */
@Serializable(ArtilleryFlarePrototype.Serializer::class)
@SerialName("artillery-flare")
public open class ArtilleryFlarePrototype : EntityPrototype() {
  /**
   * Picture variation count and individual frame count must be equal to shadow variation count.
   */
  public val pictures: AnimationVariations by fromJson()

  public val life_time: UShort by fromJson()

  /**
   * Shadow variation variation count and individual frame count must be equal to picture variation
   * count.
   */
  public val shadows: AnimationVariations? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public val render_layer_when_on_ground: RenderLayer? by fromJson()

  public val regular_trigger_effect: TriggerEffect? by fromJson()

  public val regular_trigger_effect_frequency: UInt? by fromJson()

  public val ended_in_water_trigger_effect: TriggerEffect? by fromJson()

  public val movement_modifier_when_on_ground: Double? by fromJson()

  public val movement_modifier: Double? by fromJson()

  public val creation_shift: Vector? by fromJson()

  public val initial_speed: Vector? by fromJson()

  public val initial_height: Float? by fromJson()

  public val initial_vertical_speed: Float? by fromJson()

  public val initial_frame_speed: Float? by fromJson()

  /**
   * How many artillery shots should be fired at the position of this flare.
   */
  public val shots_per_flare: UInt? by fromJson()

  /**
   * How long this flare stays alive after `shots_per_flare` amount of shots have been shot at it.
   */
  public val early_death_ticks: UInt? by fromJson()

  public val shot_category: AmmoCategoryID? by fromJson()

  override val map_color: Color by fromJson()

  /**
   * The entity with the higher number is selectable before the entity with the lower number. When
   * two entities have the same selection priority, the one with the highest
   * [CollisionMask](prototype:CollisionMask) (as determined by the order on that page) is selected.
   */
  override val selection_priority: UByte? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ArtilleryFlarePrototype>(ArtilleryFlarePrototype::class)
}

/**
 * The projectile shot by [artillery](https://wiki.factorio.com/Artillery).
 */
@Serializable(ArtilleryProjectilePrototype.Serializer::class)
@SerialName("artillery-projectile")
public open class ArtilleryProjectilePrototype : EntityPrototype() {
  public val reveal_map: Boolean by fromJson()

  public val picture: Sprite? by fromJson()

  public val shadow: Sprite? by fromJson()

  public val chart_picture: Sprite? by fromJson()

  public val action: Trigger? by fromJson()

  public val final_action: Trigger? by fromJson()

  public val height_from_ground: Float? by fromJson()

  /**
   * Whether the picture of the projectile is rotated to match the direction of travel.
   */
  public val rotatable: Boolean? by fromJson()

  override val map_color: Color by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  /**
   * Must have a collision box size of zero.
   */
  override val collision_box: BoundingBox? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ArtilleryProjectilePrototype>(ArtilleryProjectilePrototype::class)
}

/**
 * An [artillery turret](https://wiki.factorio.com/Artillery_turret).
 */
@Serializable(ArtilleryTurretPrototype.Serializer::class)
@SerialName("artillery-turret")
public open class ArtilleryTurretPrototype : EntityWithOwnerPrototype() {
  /**
   * Name of a [GunPrototype](prototype:GunPrototype).
   */
  public val gun: ItemID by fromJson()

  /**
   * Must be > 0.
   */
  public val inventory_size: ItemStackIndex by fromJson()

  /**
   * Must be > 0.
   */
  public val ammo_stack_limit: ItemCountType by fromJson()

  public val automated_ammo_count: ItemCountType by fromJson()

  public val turret_rotation_speed: Double by fromJson()

  /**
   * Must be positive.
   */
  public val manual_range_modifier: Double by fromJson()

  public val alert_when_attacking: Boolean? by fromJson()

  public val disable_automatic_firing: Boolean? by fromJson()

  public val base_picture_secondary_draw_order: UByte? by fromJson()

  public val base_picture_render_layer: RenderLayer? by fromJson()

  public val base_shift: Vector? by fromJson()

  public val base_picture: Animation4Way? by fromJson()

  public val cannon_base_pictures: RotatedSprite? by fromJson()

  public val cannon_barrel_pictures: RotatedSprite? by fromJson()

  public val rotating_sound: InterruptibleSound? by fromJson()

  public val rotating_stopped_sound: Sound? by fromJson()

  public val turn_after_shooting_cooldown: UShort? by fromJson()

  public val cannon_parking_frame_count: UShort? by fromJson()

  public val cannon_parking_speed: Float? by fromJson()

  public val cannon_barrel_recoil_shiftings: List<Vector3D>? by fromJson()

  /**
   * Only loaded if `cannon_barrel_recoil_shiftings` is loaded.
   */
  public val cannon_barrel_recoil_shiftings_load_correction_matrix: List<Vector3D>? by fromJson()

  /**
   * Only loaded if `cannon_barrel_recoil_shiftings` is loaded.
   */
  public val cannon_barrel_light_direction: Vector3D? by fromJson()

  /**
   * Whether this prototype should be a high priority target for enemy forces. See [Military units
   * and structures](https://wiki.factorio.com/Military_units_and_structures).
   */
  override val is_military_target: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ArtilleryTurretPrototype>(ArtilleryTurretPrototype::class)
}

/**
 * An [artillery wagon](https://wiki.factorio.com/Artillery_wagon).
 */
@Serializable(ArtilleryWagonPrototype.Serializer::class)
@SerialName("artillery-wagon")
public open class ArtilleryWagonPrototype : RollingStockPrototype() {
  /**
   * Name of a [GunPrototype](prototype:GunPrototype).
   */
  public val gun: ItemID by fromJson()

  /**
   * Must be > 0.
   */
  public val inventory_size: ItemStackIndex by fromJson()

  /**
   * Must be > 0.
   */
  public val ammo_stack_limit: ItemCountType by fromJson()

  public val turret_rotation_speed: Double by fromJson()

  /**
   * Must be > 0.
   */
  public val manual_range_modifier: Double by fromJson()

  public val disable_automatic_firing: Boolean? by fromJson()

  public val cannon_base_pictures: RotatedSprite? by fromJson()

  public val cannon_barrel_pictures: RotatedSprite? by fromJson()

  public val rotating_sound: InterruptibleSound? by fromJson()

  public val rotating_stopped_sound: Sound? by fromJson()

  public val turn_after_shooting_cooldown: UShort? by fromJson()

  public val cannon_parking_frame_count: UShort? by fromJson()

  public val cannon_parking_speed: Float? by fromJson()

  /**
   * Must match `cannon_base_pictures` frame count.
   */
  public val cannon_base_shiftings: List<Vector>? by fromJson()

  public val cannon_barrel_recoil_shiftings: List<Vector3D>? by fromJson()

  /**
   * Only loaded if `cannon_barrel_recoil_shiftings` is loaded.
   */
  public val cannon_barrel_recoil_shiftings_load_correction_matrix: List<Vector3D>? by fromJson()

  /**
   * Only loaded if `cannon_barrel_recoil_shiftings` is loaded.
   */
  public val cannon_barrel_light_direction: Vector3D? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ArtilleryWagonPrototype>(ArtilleryWagonPrototype::class)
}

/**
 * An assembling machine - like the assembling machines 1/2/3 in the game, but you can use your own
 * recipe categories.
 */
@Serializable(AssemblingMachinePrototype.Serializer::class)
@SerialName("assembling-machine")
public open class AssemblingMachinePrototype : CraftingMachinePrototype() {
  /**
   * The preset recipe of this machine. This machine does not show a recipe selection if this is
   * set. The base game uses this for the [rocket silo](https://wiki.factorio.com/Rocket_silo).
   */
  public val fixed_recipe: RecipeID? by fromJson()

  /**
   * The locale key of the title of the GUI that is shown when the player opens the assembling
   * machine. May not be longer than 200 characters.
   */
  public val gui_title_key: String? by fromJson()

  /**
   * Sets the maximum number of ingredients this machine can craft with. Any recipe with more
   * ingredients than this will be unavailable in this machine.
   *
   * This only counts item ingredients, not fluid ingredients! This means if ingredient count is 2,
   * and the recipe has 2 item ingredients and 1 fluid ingredient, it can still be crafted in the
   * machine.
   */
  public val ingredient_count: UByte? by fromJson()

  /**
   * Shift of the "alt-mode icon" relative to the machine's center.
   */
  override val entity_info_icon_shift: Vector? by fromJson()

  public object Serializer :
      JsonReaderSerializer<AssemblingMachinePrototype>(AssemblingMachinePrototype::class)
}

@Serializable
public enum class AutoplaceControlCategory {
  resource,
  terrain,
  enemy,
}

/**
 * A setting in the map creation GUI. Used by the [autoplace
 * system](prototype:AutoplaceSpecification::control).
 */
@Serializable(AutoplaceControl.Serializer::class)
@SerialName("autoplace-control")
public open class AutoplaceControl : PrototypeBase() {
  /**
   * Controls in what tab the autoplace is shown in the map generator GUI.
   */
  public val category: AutoplaceControlCategory by fromJson()

  /**
   * Sets whether this control's richness can be changed. The map generator GUI will only show the
   * richness slider when the `category` is `"resource"`.
   *
   * If the autoplace control is used to generate ores, you probably want this to be true.
   */
  public val richness: Boolean? by fromJson()

  /**
   * Whether there is an "enable" checkbox for the autoplace control in the map generator GUI. If
   * this is false, the autoplace control cannot be disabled from the GUI.
   */
  public val can_be_disabled: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<AutoplaceControl>(AutoplaceControl::class)
}

/**
 * Used by [personal battery](https://wiki.factorio.com/Personal_battery).
 */
@Serializable(BatteryEquipmentPrototype.Serializer::class)
@SerialName("battery-equipment")
public open class BatteryEquipmentPrototype : EquipmentPrototype() {
  public object Serializer :
      JsonReaderSerializer<BatteryEquipmentPrototype>(BatteryEquipmentPrototype::class)
}

/**
 * Entity with the ability to transfer [module](prototype:ModulePrototype) effects to its
 * neighboring entities.
 */
@Serializable(BeaconPrototype.Serializer::class)
@SerialName("beacon")
public open class BeaconPrototype : EntityWithOwnerPrototype() {
  /**
   * The constant power usage of this beacon.
   */
  public val energy_usage: Energy by fromJson()

  public val energy_source: UnknownUnion by fromJson()

  /**
   * The maximum distance that this beacon can supply its neighbors with its module's effects. Max
   * distance is 64.
   */
  public val supply_area_distance: Double by fromJson()

  /**
   * The multiplier of the module's effects, when shared between neighbors.
   */
  public val distribution_effectivity: Double by fromJson()

  /**
   * The number of module slots in this beacon and their icon positions.
   */
  public val module_specification: ModuleSpecification by fromJson()

  /**
   * The graphics for the beacon.
   */
  public val graphics_set: BeaconGraphicsSet? by fromJson()

  /**
   * Only loaded if `graphics_set` is not defined.
   *
   * The animation for the beacon, when in use.
   */
  public val animation: Animation? by fromJson()

  /**
   * Only loaded if `graphics_set` is not defined.
   *
   * The picture of the beacon when it is not on.
   */
  public val base_picture: Animation? by fromJson()

  public val radius_visualisation_picture: Sprite? by fromJson()

  /**
   * The types of [modules](prototype:ModulePrototype) that a player can place inside of the beacon.
   */
  public val allowed_effects: EffectTypeLimitation? by fromJson()

  public object Serializer : JsonReaderSerializer<BeaconPrototype>(BeaconPrototype::class)
}

/**
 * Used as a laser beam.
 */
@Serializable(BeamPrototype.Serializer::class)
@SerialName("beam")
public open class BeamPrototype : EntityPrototype() {
  public val width: Double by fromJson()

  /**
   * Damage interval can't be 0. A value of 1 will cause the attack to be applied each tick.
   */
  public val damage_interval: UInt by fromJson()

  /**
   * Head segment of the beam.
   */
  public val head: Animation by fromJson()

  /**
   * Tail segment of the beam.
   *
   * All animations must have the same number of frames: Tail must have same number of frames as
   * start, ending, head, body, start_light, ending_light, head_light, tail_light and body_light.
   */
  public val tail: Animation by fromJson()

  /**
   * Body segment of the beam. Must have at least 1 variation.
   */
  public val body: AnimationVariations by fromJson()

  public val action: Trigger? by fromJson()

  public val target_offset: Vector? by fromJson()

  public val random_target_offset: Boolean? by fromJson()

  /**
   * Whether this beams should trigger its action every `damage_interval`. If false, the action is
   * instead triggered when its owner triggers shooting.
   */
  public val action_triggered_automatically: Boolean? by fromJson()

  public val random_end_animation_rotation: Boolean? by fromJson()

  public val transparent_start_end_animations: Boolean? by fromJson()

  /**
   * Start point of the beam.
   */
  public val start: Animation? by fromJson()

  /**
   * End point of the beam.
   */
  public val ending: Animation? by fromJson()

  /**
   * Only loaded if `start_light`, `ending_light`, `head_light`, `tail_light` and `body_light` are
   * not defined.
   *
   * Lights are additively accumulated onto a light-map, which is [multiplicatively
   * rendered](https://forums.factorio.com/viewtopic.php?p=435042#p435042) on the game world.
   */
  public val light_animations: BeamAnimationSet? by fromJson()

  /**
   * Only loaded if `start_light`, `ending_light`, `head_light`, `tail_light` and `body_light` are
   * not defined.
   */
  public val ground_light_animations: BeamAnimationSet? by fromJson()

  public val start_light: Animation? by fromJson()

  public val ending_light: Animation? by fromJson()

  public val head_light: Animation? by fromJson()

  public val tail_light: Animation? by fromJson()

  public val body_light: AnimationVariations? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<BeamPrototype>(BeamPrototype::class)
}

/**
 * Used by [belt immunity equipment](https://wiki.factorio.com/Belt_immunity_equipment).
 */
@Serializable(BeltImmunityEquipmentPrototype.Serializer::class)
@SerialName("belt-immunity-equipment")
public open class BeltImmunityEquipmentPrototype : EquipmentPrototype() {
  /**
   * The continuous power consumption of the belt immunity equipment.
   */
  public val energy_consumption: Energy by fromJson()

  public object Serializer :
      JsonReaderSerializer<BeltImmunityEquipmentPrototype>(BeltImmunityEquipmentPrototype::class)
}

/**
 * A [blueprint book](https://wiki.factorio.com/Blueprint_book).
 */
@Serializable(BlueprintBookPrototype.Serializer::class)
@SerialName("blueprint-book")
public open class BlueprintBookPrototype : ItemWithInventoryPrototype() {
  /**
   * The inventory size of the item.
   */
  override val inventory_size: UnknownOverriddenType by fromJson()

  /**
   * If the item will draw its label when held in the cursor in place of the item count.
   */
  override val draw_label_for_cursor_render: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<BlueprintBookPrototype>(BlueprintBookPrototype::class)
}

@Serializable
public enum class EntityFilterMode {
  whitelist,
  blacklist,
}

/**
 * A [blueprint](https://wiki.factorio.com/Blueprint).
 */
@Serializable(BlueprintItemPrototype.Serializer::class)
@SerialName("blueprint")
public open class BlueprintItemPrototype : SelectionToolPrototype() {
  /**
   * Whether the item will draw its label when held in the cursor in place of the item count.
   */
  override val draw_label_for_cursor_render: Boolean? by fromJson()

  /**
   * This property is hardcoded to `"blueprint"`.
   */
  override val selection_mode: SelectionModeFlags? by fromJson()

  /**
   * This property is hardcoded to `"blueprint"`.
   */
  override val alt_selection_mode: SelectionModeFlags? by fromJson()

  /**
   * This property is hardcoded to `false`.
   */
  override val always_include_tiles: Boolean? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_filters: List<EntityID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_filters: List<EntityID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_type_filters: List<String>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_type_filters: List<String>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val tile_filters: List<TileID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_tile_filters: List<TileID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val tile_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_tile_filter_mode: EntityFilterMode? by fromJson()

  public object Serializer :
      JsonReaderSerializer<BlueprintItemPrototype>(BlueprintItemPrototype::class)
}

@Serializable
public enum class BoilerPrototypeMode {
  `heat-water-inside`,
  `output-to-separate-pipe`,
}

/**
 * A [boiler](https://wiki.factorio.com/Boiler). It heats fluid and optionally outputs it as a
 * different fluid.
 */
@Serializable(BoilerPrototype.Serializer::class)
@SerialName("boiler")
public open class BoilerPrototype : EntityWithOwnerPrototype() {
  public val energy_source: EnergySource by fromJson()

  /**
   * The input fluid box.
   *
   * If `mode` is `"heat-water-inside"`, the fluid is heated up directly in this fluidbox.
   */
  public val fluid_box: FluidBox by fromJson()

  /**
   * The output fluid box.
   *
   * If `mode` is `"output-to-separate-pipe"` and this has a [filter](prototype:FluidBox::filter),
   * the heated input fluid is converted to the output fluid that is set in the filter (in a 1:1
   * ratio).
   *
   * If `mode` is `"heat-water-inside"`, this fluidbox is unused.
   */
  public val output_fluid_box: FluidBox by fromJson()

  public val energy_consumption: Energy by fromJson()

  /**
   * Controls for how many ticks the boiler will show the fire and fire_glow after the energy source
   * runs out of energy.
   *
   * Note that `fire` and `fire_glow` alpha is set to the light intensity of the energy source, so 0
   * light intensity means the fire is invisible. For burner energy sources, the light intensity will
   * reach zero rather quickly after the boiler runs out of fuel, effectively capping the time that
   * `fire` and `fire_glow` will be shown after the boiler runs out of fuel.
   */
  public val burning_cooldown: UShort by fromJson()

  /**
   * When `mode` is `"output-to-separate-pipe"`, this is the temperature that the input fluid must
   * reach to be moved to the output fluid box.
   *
   * When `mode` is `"heat-water-inside"` this is unused. Instead, the fluid
   * [max_temperature](prototype:FluidPrototype::max_temperature) is the target temperature for heating
   * the fluid.
   */
  public val target_temperature: Double by fromJson()

  public val structure: BoilerStructure by fromJson()

  /**
   * Animation that is drawn on top of the `structure` when `burning_cooldown` is larger than 1. The
   * animation alpha can be controlled by the energy source light intensity, depending on
   * `fire_flicker_enabled`.
   *
   * The secondary draw order of this is higher than the secondary draw order of `fire_glow`, so
   * this is drawn above `fire_glow`.
   */
  public val fire: BoilerFire by fromJson()

  /**
   * Animation that is drawn on top of the `structure` when `burning_cooldown` is larger than 1. The
   * animation alpha can be controlled by the energy source light intensity, depending on
   * `fire_glow_flicker_enabled`.
   *
   * The secondary draw order of this is lower than the secondary draw order of `fire`, so this is
   * drawn below `fire`.
   */
  public val fire_glow: BoilerFireGlow by fromJson()

  /**
   * If this is set to false, `fire_glow` alpha is always 1 instead of being controlled by the light
   * intensity of the energy source.
   */
  public val fire_glow_flicker_enabled: Boolean? by fromJson()

  /**
   * If this is set to false, `fire` alpha is always 1 instead of being controlled by the light
   * intensity of the energy source.
   */
  public val fire_flicker_enabled: Boolean? by fromJson()

  /**
   * In the `"heat-water-inside"` mode, fluid in the `fluid_box` is continuously heated from the
   * input temperature up to its
   * [FluidPrototype::max_temperature](prototype:FluidPrototype::max_temperature).
   *
   * In the `"output-to-separate-pipe"` mode, fluid is transferred from the `fluid_box` to the
   * `output_fluid_box` when enough energy is available to
   * [heat](prototype:FluidPrototype::heat_capacity) the input fluid to the `target_temperature`.
   * Setting a filter on the `output_fluid_box` means that instead of the heated input fluid getting
   * moved to the output, it is converted to the filtered fluid in a 1:1 ratio.
   */
  public val mode: BoilerPrototypeMode? by fromJson()

  /**
   * Drawn above the `structure`, in the "higher-object-under" [RenderLayer](prototype:RenderLayer).
   * May be useful to correct problems with neighboring pipes overlapping the structure graphics.
   */
  public val patch: BoilerPatch? by fromJson()

  public object Serializer : JsonReaderSerializer<BoilerPrototype>(BoilerPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player builds an entity.
 */
@Serializable(BuildEntityAchievementPrototype.Serializer::class)
@SerialName("build-entity-achievement")
public open class BuildEntityAchievementPrototype : AchievementPrototype() {
  /**
   * This will trigger the achievement, if this entity is placed.
   */
  public val to_build: EntityID by fromJson()

  /**
   * How many entities need to be built.
   */
  public val amount: UInt? by fromJson()

  /**
   * If this is false, the player carries over their statistics from this achievement through all
   * their saves.
   */
  public val limited_to_one_game: Boolean? by fromJson()

  /**
   * This lets the game know how long into a game, before you can no longer complete the
   * achievement. 0 means infinite time.
   */
  public val until_second: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<BuildEntityAchievementPrototype>(BuildEntityAchievementPrototype::class)
}

/**
 * An entity that produces power from a burner energy source.
 */
@Serializable(BurnerGeneratorPrototype.Serializer::class)
@SerialName("burner-generator")
public open class BurnerGeneratorPrototype : EntityWithOwnerPrototype() {
  /**
   * The output energy source of the generator. Any emissions specified on this energy source are
   * ignored, they must be specified on `burner`.
   */
  public val energy_source: ElectricEnergySource by fromJson()

  /**
   * The input energy source of the generator.
   */
  public val burner: BurnerEnergySource by fromJson()

  /**
   * Plays when the generator is active. `idle_animation` must have the same frame count as
   * animation.
   */
  public val animation: Animation4Way? by fromJson()

  /**
   * How much energy this generator can produce.
   */
  public val max_power_output: Energy by fromJson()

  /**
   * Plays when the generator is inactive. Idle animation must have the same frame count as
   * `animation`.
   */
  public val idle_animation: Animation4Way? by fromJson()

  /**
   * Whether the `idle_animation` should also play when the generator is active.
   */
  public val always_draw_idle_animation: Boolean? by fromJson()

  /**
   * Animation runs at least this fast.
   */
  public val min_perceived_performance: Double? by fromJson()

  public val performance_to_sound_speedup: Double? by fromJson()

  public object Serializer :
      JsonReaderSerializer<BurnerGeneratorPrototype>(BurnerGeneratorPrototype::class)
}

/**
 * A capsule, for example a [combat robot capsule](https://wiki.factorio.com/Combat_robot_capsules)
 * or the [raw fish](https://wiki.factorio.com/Raw_fish).
 */
@Serializable(CapsulePrototype.Serializer::class)
@SerialName("capsule")
public open class CapsulePrototype : ItemPrototype() {
  public val capsule_action: CapsuleAction by fromJson()

  /**
   * Color of the range radius that is shown around the player when they hold the capsule.
   */
  public val radius_color: Color? by fromJson()

  public object Serializer : JsonReaderSerializer<CapsulePrototype>(CapsulePrototype::class)
}

/**
 * Entity with specialized properties for acceleration, braking, and turning.
 */
@Serializable(CarPrototype.Serializer::class)
@SerialName("car")
public open class CarPrototype : VehiclePrototype() {
  /**
   * Animation speed 1 means 1 frame per tile.
   */
  public val animation: RotatedAnimation by fromJson()

  /**
   * Modifies the efficiency of energy transfer from burner output to wheels.
   */
  public val effectivity: Double by fromJson()

  public val consumption: Energy by fromJson()

  public val rotation_speed: Double by fromJson()

  /**
   * Must be a burner energy source when using `"burner"`, otherwise it can also be a void energy
   * source.
   */
  public val energy_source: UnknownUnion by fromJson("burner")

  /**
   * Size of the car inventory.
   */
  public val inventory_size: ItemStackIndex by fromJson()

  /**
   * Animation speed 1 means 1 frame per tile.
   */
  public val turret_animation: RotatedAnimation? by fromJson()

  /**
   * Must have the same frame count as `animation`.
   */
  public val light_animation: RotatedAnimation? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  /**
   * If this car prototype uses tank controls to drive.
   */
  public val tank_driving: Boolean? by fromJson()

  /**
   * If this car is immune to movement by belts.
   */
  public val has_belt_immunity: Boolean? by fromJson()

  /**
   * If this car gets damaged by driving over/against [trees](prototype:TreePrototype).
   */
  public val immune_to_tree_impacts: Boolean? by fromJson()

  /**
   * If this car gets damaged by driving over/against
   * [rocks](prototype:SimpleEntityPrototype::count_as_rock_for_filtered_deconstruction).
   */
  public val immune_to_rock_impacts: Boolean? by fromJson()

  /**
   * If this car gets damaged by driving against [cliffs](prototype:CliffPrototype).
   */
  public val immune_to_cliff_impacts: Boolean? by fromJson()

  public val turret_rotation_speed: Float? by fromJson()

  /**
   * Timeout in ticks specifying how long the turret must be inactive to return to the default
   * position.
   */
  public val turret_return_timeout: UInt? by fromJson()

  public val light: LightDefinition? by fromJson()

  public val sound_no_fuel: Sound? by fromJson()

  public val darkness_to_render_light_animation: Float? by fromJson()

  public val track_particle_triggers: FootstepTriggerEffectList? by fromJson()

  /**
   * The names of the  [GunPrototype](prototype:GunPrototype)s this car prototype uses.
   */
  public val guns: List<ItemID>? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<CarPrototype>(CarPrototype::class)
}

/**
 * A [cargo wagon](https://wiki.factorio.com/Cargo_wagon).
 */
@Serializable(CargoWagonPrototype.Serializer::class)
@SerialName("cargo-wagon")
public open class CargoWagonPrototype : RollingStockPrototype() {
  /**
   * Size of the inventory of the wagon. The inventory can be limited using the red bar and
   * filtered. This functionality cannot be turned off.
   */
  public val inventory_size: ItemStackIndex by fromJson()

  public object Serializer : JsonReaderSerializer<CargoWagonPrototype>(CargoWagonPrototype::class)
}

/**
 * The corpse of a [CharacterPrototype](prototype:CharacterPrototype).
 */
@Serializable(CharacterCorpsePrototype.Serializer::class)
@SerialName("character-corpse")
public open class CharacterCorpsePrototype : EntityPrototype() {
  public val time_to_live: UInt by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  /**
   * Mandatory if `picture` is not defined.
   */
  public val pictures: AnimationVariations? by fromJson()

  /**
   * Mandatory if `pictures` is not defined.
   */
  public val picture: Animation? by fromJson()

  /**
   * Table of key value pairs, the keys are armor names and the values are numbers. The number is
   * the Animation that is associated with the armor, e.g. using `1` will associate the armor with the
   * first Animation in the pictures table.
   */
  public val armor_picture_mapping: Map<ItemID, Int>? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterCorpsePrototype>(CharacterCorpsePrototype::class)
}

/**
 * Entity that you move around on the screen during the campaign and freeplay.
 */
@Serializable(CharacterPrototype.Serializer::class)
@SerialName("character")
public open class CharacterPrototype : EntityWithOwnerPrototype() {
  public val mining_speed: Double by fromJson()

  public val running_speed: Double by fromJson()

  public val distance_per_frame: Double by fromJson()

  public val maximum_corner_sliding_distance: Double by fromJson()

  /**
   * The sound played when the character's health is low.
   */
  public val heartbeat: Sound by fromJson()

  /**
   * The sound played when the character eats (fish for example).
   */
  public val eat: Sound by fromJson()

  /**
   * Number of slots in the main inventory. May be 0.
   */
  public val inventory_size: ItemStackIndex by fromJson()

  public val build_distance: UInt by fromJson()

  public val drop_item_distance: UInt by fromJson()

  public val reach_distance: UInt by fromJson()

  public val reach_resource_distance: Double by fromJson()

  public val item_pickup_distance: Double by fromJson()

  public val loot_pickup_distance: Double by fromJson()

  public val ticks_to_keep_gun: UInt by fromJson()

  public val ticks_to_keep_aiming_direction: UInt by fromJson()

  public val ticks_to_stay_in_combat: UInt by fromJson()

  public val damage_hit_tint: Color by fromJson()

  /**
   * List of positions in the running animation when the walking sound is played.
   */
  public val running_sound_animation_positions: List<Float> by fromJson()

  /**
   * List of positions in the mining with tool animation when the mining sound and mining particles
   * are created.
   */
  public val mining_with_tool_particles_animation_positions: List<Float> by fromJson()

  public val animations: List<CharacterArmorAnimation> by fromJson()

  /**
   * Names of the crafting categories the character can craft recipes from. The built-in categories
   * can be found [here](https://wiki.factorio.com/Data.raw#recipe-category). See also
   * [RecipeCategory](prototype:RecipeCategory).
   */
  public val crafting_categories: List<RecipeCategoryID>? by fromJson()

  /**
   * Names of the resource categories the character can mine resources from.
   */
  public val mining_categories: List<ResourceCategoryID>? by fromJson()

  public val light: LightDefinition? by fromJson()

  /**
   * Must be >= 0.
   */
  public val enter_vehicle_distance: Double? by fromJson()

  public val tool_attack_distance: Double? by fromJson()

  /**
   * Time in seconds. Must be positive
   */
  public val respawn_time: UInt? by fromJson()

  /**
   * Whether this character is moved by belts when standing on them.
   */
  public val has_belt_immunity: Boolean? by fromJson()

  /**
   * Name of the character corpse that is spawned when this character dies.
   */
  public val character_corpse: EntityID? by fromJson()

  /**
   * Triggered every tick of the running animation.
   */
  public val footstep_particle_triggers: FootstepTriggerEffectList? by fromJson()

  /**
   * Triggered when the running animation (`animations`) rolls over the frames defined in
   * `right_footprint_frames` and `left_footprint_frames`.
   */
  public val synced_footstep_particle_triggers: FootstepTriggerEffectList? by fromJson()

  /**
   * Triggered when the running animation (`animations`) rolls over the frames defined in
   * `right_footprint_frames` and `left_footprint_frames`.
   */
  public val footprint_particles: List<FootprintParticle>? by fromJson()

  /**
   * Offset from the center of the entity for the left footprint. Used by `footprint_particles`.
   */
  public val left_footprint_offset: Vector? by fromJson()

  /**
   * Offset from the center of the entity for the right footprint. Used by `footprint_particles`.
   */
  public val right_footprint_offset: Vector? by fromJson()

  /**
   * The frames in the running animation (`animations`) where the right foot touches the ground.
   */
  public val right_footprint_frames: List<Float>? by fromJson()

  /**
   * The frames in the running animation (`animations`) where the left foot touches the ground.
   */
  public val left_footprint_frames: List<Float>? by fromJson()

  public val tool_attack_result: Trigger? by fromJson()

  /**
   * Whether this prototype should be a high priority target for enemy forces. See [Military units
   * and structures](https://wiki.factorio.com/Military_units_and_structures).
   */
  override val is_military_target: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<CharacterPrototype>(CharacterPrototype::class)
}

/**
 * A [cliff](https://wiki.factorio.com/Cliff).
 */
@Serializable(CliffPrototype.Serializer::class)
@SerialName("cliff")
public open class CliffPrototype : EntityPrototype() {
  public val orientations: OrientedCliffPrototypeSet by fromJson()

  public val grid_size: Vector by fromJson()

  public val grid_offset: Vector by fromJson()

  public val cliff_height: Float? by fromJson()

  /**
   * Name of a capsule that has a robot_action to explode cliffs.
   */
  public val cliff_explosive: ItemID? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<CliffPrototype>(CliffPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player has a certain robot follower
 * count.
 */
@Serializable(CombatRobotCountAchievementPrototype.Serializer::class)
@SerialName("combat-robot-count")
public open class CombatRobotCountAchievementPrototype : AchievementPrototype() {
  /**
   * This will trigger the achievement, if player's current robot count is over this amount.
   */
  public val count: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CombatRobotCountAchievementPrototype>(CombatRobotCountAchievementPrototype::class)
}

/**
 * A combat robot. Can attack enemies.
 */
@Serializable(CombatRobotPrototype.Serializer::class)
@SerialName("combat-robot")
public open class CombatRobotPrototype : FlyingRobotPrototype() {
  public val time_to_live: UInt by fromJson()

  public val attack_parameters: AttackParameters by fromJson()

  public val idle: RotatedAnimation by fromJson()

  public val shadow_idle: RotatedAnimation by fromJson()

  public val in_motion: RotatedAnimation by fromJson()

  public val shadow_in_motion: RotatedAnimation by fromJson()

  public val range_from_player: Double? by fromJson()

  public val friction: Double? by fromJson()

  /**
   * Applied when the combat robot expires (runs out of `time_to_live`).
   */
  public val destroy_action: Trigger? by fromJson()

  public val follows_player: Boolean? by fromJson()

  public val light: LightDefinition? by fromJson()

  public object Serializer : JsonReaderSerializer<CombatRobotPrototype>(CombatRobotPrototype::class)
}

/**
 * Abstract base type for decider and arithmetic combinators.
 */
public sealed class CombinatorPrototype : EntityWithOwnerPrototype() {
  public val energy_source: UnknownUnion by fromJson()

  public val active_energy_usage: Energy by fromJson()

  public val sprites: Sprite4Way? by fromJson()

  public val activity_led_sprites: Sprite4Way? by fromJson()

  public val input_connection_bounding_box: BoundingBox by fromJson()

  public val output_connection_bounding_box: BoundingBox by fromJson()

  public val activity_led_light_offsets: Tuple4<Vector> by fromJson()

  public val screen_light_offsets: Tuple4<Vector> by fromJson()

  public val input_connection_points: Tuple4<WireConnectionPoint> by fromJson()

  public val output_connection_points: Tuple4<WireConnectionPoint> by fromJson()

  public val activity_led_light: LightDefinition? by fromJson()

  public val screen_light: LightDefinition? by fromJson()

  public val activity_led_hold_time: UByte? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()
}

/**
 * A [constant combinator](https://wiki.factorio.com/Constant_combinator).
 */
@Serializable(ConstantCombinatorPrototype.Serializer::class)
@SerialName("constant-combinator")
public open class ConstantCombinatorPrototype : EntityWithOwnerPrototype() {
  public val item_slot_count: UInt by fromJson()

  public val sprites: Sprite4Way? by fromJson()

  public val activity_led_sprites: Sprite4Way? by fromJson()

  public val activity_led_light_offsets: Tuple4<Vector> by fromJson()

  public val circuit_wire_connection_points: Tuple4<WireConnectionPoint> by fromJson()

  public val activity_led_light: LightDefinition? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ConstantCombinatorPrototype>(ConstantCombinatorPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player constructs enough entities
 * with construction robots.
 */
@Serializable(ConstructWithRobotsAchievementPrototype.Serializer::class)
@SerialName("construct-with-robots-achievement")
public open class ConstructWithRobotsAchievementPrototype : AchievementPrototype() {
  /**
   * If this is false, the player carries over their statistics from this achievement through all
   * their saves.
   */
  public val limited_to_one_game: Boolean by fromJson()

  /**
   * This will trigger the achievement, if enough entities were placed using construction robots.
   */
  public val amount: UInt? by fromJson()

  public val more_than_manually: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ConstructWithRobotsAchievementPrototype>(ConstructWithRobotsAchievementPrototype::class)
}

/**
 * A [construction robot](https://wiki.factorio.com/Construction_robot).
 */
@Serializable(ConstructionRobotPrototype.Serializer::class)
@SerialName("construction-robot")
public open class ConstructionRobotPrototype : RobotWithLogisticInterfacePrototype() {
  public val construction_vector: Vector by fromJson()

  public val working: RotatedAnimation? by fromJson()

  public val shadow_working: RotatedAnimation? by fromJson()

  public val smoke: Animation? by fromJson()

  public val sparks: AnimationVariations? by fromJson()

  public val repairing_sound: Sound? by fromJson()

  public val working_light: LightDefinition? by fromJson()

  /**
   * Must have a collision box size of zero.
   */
  override val collision_box: BoundingBox? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ConstructionRobotPrototype>(ConstructionRobotPrototype::class)
}

@Serializable
public enum class ContainerPrototypeInventoryType {
  with_bar,
  with_filters_and_bar,
}

/**
 * A generic container, such as a chest. Cannot be rotated.
 */
@Serializable(ContainerPrototype.Serializer::class)
@SerialName("container")
public open class ContainerPrototype : EntityWithOwnerPrototype() {
  /**
   * The number of slots in this container.
   */
  public open val inventory_size: ItemStackIndex by fromJson()

  /**
   * The picture displayed for this entity.
   */
  public open val picture: Sprite? by fromJson()

  /**
   * Whether the inventory of this container can be filtered (like cargo wagons) or not.
   */
  public val inventory_type: ContainerPrototypeInventoryType? by fromJson()

  /**
   * If the inventory limiter (red X) is visible in the chest's GUI. This does not change the
   * inventory itself ([LuaInventory::supports_bar](runtime:LuaInventory::supports_bar) will not change
   * and the bar can still be modified by script).
   */
  public val enable_inventory_bar: Boolean? by fromJson()

  /**
   * If the icons of items shown in alt-mode should be scaled to the containers size.
   */
  public val scale_info_icons: Boolean? by fromJson()

  /**
   * Defines how wires visually connect to this container.
   */
  public val circuit_wire_connection_point: WireConnectionPoint? by fromJson()

  /**
   * The maximum circuit wire distance for this container.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * The pictures displayed for circuit connections to this container.
   */
  public val circuit_connector_sprites: CircuitConnectorSprites? by fromJson()

  public object Serializer : JsonReaderSerializer<ContainerPrototype>(ContainerPrototype::class)
}

/**
 * A copy-paste or cut-paste tool.
 */
@Serializable(CopyPasteToolPrototype.Serializer::class)
@SerialName("copy-paste-tool")
public open class CopyPasteToolPrototype : SelectionToolPrototype() {
  public val cuts: Boolean? by fromJson()

  /**
   * This property is hardcoded to `false`.
   */
  override val always_include_tiles: Boolean? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_filters: List<EntityID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_filters: List<EntityID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_type_filters: List<String>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_type_filters: List<String>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val tile_filters: List<TileID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_tile_filters: List<TileID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val tile_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_tile_filter_mode: EntityFilterMode? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CopyPasteToolPrototype>(CopyPasteToolPrototype::class)
}

/**
 * Used for corpses, for example the remnants when destroying buildings.
 */
@Serializable(CorpsePrototype.Serializer::class)
@SerialName("corpse")
public open class CorpsePrototype : EntityPrototype() {
  /**
   * Multiplier for `time_before_shading_off` and `time_before_removed`. Must be positive.
   *
   * Controls the speed of the animation: `1 ÷ dying_speed = duration of the animation`
   */
  public val dying_speed: Float? by fromJson()

  /**
   * Controls the speed of the splash animation: `1 ÷ splash_speed = duration of the splash
   * animation`
   */
  public val splash_speed: Float? by fromJson()

  /**
   * Controls how long the corpse takes to fade, as in how long it takes to get from no transparency
   * to full transparency/removed. This time is ''not'' added to `time_before_removed`, it is instead
   * subtracted from it. So by default, the corpse starts fading about 15 seconds before it gets
   * removed.
   */
  public val time_before_shading_off: UInt? by fromJson()

  /**
   * Time in ticks this corpse lasts. May not be 0.
   */
  public val time_before_removed: UInt? by fromJson()

  public val remove_on_entity_placement: Boolean? by fromJson()

  public val remove_on_tile_placement: Boolean? by fromJson()

  public val final_render_layer: RenderLayer? by fromJson()

  public val ground_patch_render_layer: RenderLayer? by fromJson()

  public val animation_render_layer: RenderLayer? by fromJson()

  public val splash_render_layer: RenderLayer? by fromJson()

  public val animation_overlay_render_layer: RenderLayer? by fromJson()

  public val animation_overlay_final_render_layer: RenderLayer? by fromJson()

  public val shuffle_directions_at_frame: UByte? by fromJson()

  public val use_tile_color_for_ground_patch_tint: Boolean? by fromJson()

  public val ground_patch_fade_in_delay: Float? by fromJson()

  public val ground_patch_fade_in_speed: Float? by fromJson()

  public val ground_patch_fade_out_start: Float? by fromJson()

  /**
   * The dying animation.
   */
  public val animation: RotatedAnimationVariations? by fromJson()

  /**
   * Variation count must be the same as `animation` variation count. Direction count must be the
   * same as `animation` direction count. Frame count must be the same as `animation` frame count.
   */
  public val animation_overlay: RotatedAnimationVariations? by fromJson()

  public val splash: AnimationVariations? by fromJson()

  public val ground_patch: AnimationVariations? by fromJson()

  public val ground_patch_higher: AnimationVariations? by fromJson()

  public val ground_patch_fade_out_duration: Float? by fromJson()

  /**
   * An array of arrays of integers. The inner arrays are called "groups" and must all have the same
   * size.
   */
  public val direction_shuffle: List<List<UShort>>? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<CorpsePrototype>(CorpsePrototype::class)
}

/**
 * The abstract basis of the assembling machines and furnaces. Contains the properties that both of
 * them have.
 *
 * Note that a crafting machine cannot be rotated unless it has at least one of the following: a
 * fluid box, a heat energy source, a fluid energy source, or a non-square collision box. Crafting
 * machines with non-square collision boxes can only be rotated before placement, not after.
 */
public sealed class CraftingMachinePrototype : EntityWithOwnerPrototype() {
  /**
   * Sets how much energy this machine uses while crafting. Energy usage has to be positive.
   */
  public val energy_usage: Energy by fromJson()

  /**
   * How fast this crafting machine can craft. 1 means that for example a 1 second long recipe take
   * 1 second to craft. 0.5 means it takes 2 seconds, and 2 means it takes 0.5 seconds.
   *
   * Crafting speed has to be positive.
   */
  public val crafting_speed: Double by fromJson()

  /**
   * A list of [recipe categories](prototype:RecipeCategory) this crafting machine can use.
   */
  public val crafting_categories: List<RecipeCategoryID> by fromJson()

  /**
   * Defines how the crafting machine is powered.
   *
   * When using an electric energy source and `drain` is not specified, it will be set to
   * `energy_usage ÷ 30` automatically.
   */
  public val energy_source: EnergySource by fromJson()

  /**
   * Can have `off_when_no_fluid_recipe` key that has a [bool](prototype:bool) value.
   * `off_when_no_fluid_recipe` defaults to false. `off_when_no_fluid_recipe` is ignored by
   * [FurnacePrototype](prototype:FurnacePrototype) and considered to always be false.
   *
   * If a crafting machine has fluid boxes *and* `off_when_no_fluid_recipe` is true, the crafting
   * machine can only be rotated when a recipe consuming or producing fluid is set, or it has one of
   * the other properties listed at the top of the page.
   */
  public val fluid_boxes: List<FluidBox>? by fromJson()

  /**
   * Sets the [modules](prototype:ModulePrototype) and [beacon](prototype:BeaconPrototype) effects
   * that are allowed to be used on this machine.
   *
   * Note: If the time to complete a recipe is shorter than one tick, only one craft can be
   * completed per tick, but productivity bonus is applied to the non-limited ''completable'' work. For
   * a simple example, if a recipe were to take half a tick, only one recipe would be completed, but
   * twice the productivity bonus would occur. The surplus production from productivity is **not**
   * limited to one craft per tick.
   */
  public val allowed_effects: EffectTypeLimitation? by fromJson()

  /**
   * Whether the "alt-mode icon" should be scaled to the size of the machine.
   */
  public val scale_entity_info_icon: Boolean? by fromJson()

  /**
   * Whether the "alt-mode icon" should be drawn at all.
   */
  public val show_recipe_icon: Boolean? by fromJson()

  /**
   * Controls whether the ingredients of an in-progress recipe are destroyed when mining the
   * machine/changing the recipe. If set to true, the ingredients do not get destroyed. This affects
   * only the ingredients of the recipe that is currently in progress, so those that visually have
   * already been consumed while their resulting product has not yet been produced.
   */
  public val return_ingredients_on_change: Boolean? by fromJson()

  /**
   * The animation played when crafting. When the crafting machine is idle, the animation will be
   * paused.
   *
   * When a crafting machine cannot be rotated, only the north rotation of the animation will be
   * used.
   *
   * The `animation_speed` of the animation is divided by 2 by the game. For example, the default
   * animation speed of 1 means one animation frame per 2 ticks (30 fps) instead of the usual 60 fps.
   */
  public val animation: Animation4Way? by fromJson()

  /**
   * Idle animation must have the same frame count as `animation`. It is used for drawing the
   * machine in the idle state. The animation is frozen on a single frame when the machine is idle.
   *
   * This is an animation and not just sprite to make it possible for idle state and working state
   * to match their visuals when the machine switches from one state to another.
   *
   * When a crafting machine cannot be rotated, only the north rotation of the idle animation will
   * be used.
   *
   * The `animation_speed` of the animation is divided by 2 by the game. For example, the default
   * animation speed of 1 means one animation frame per 2 ticks (30 fps) instead of the usual 60 fps.
   */
  public val idle_animation: Animation4Way? by fromJson()

  /**
   * Only loaded if `idle_animation` is defined.
   */
  public val always_draw_idle_animation: Boolean? by fromJson()

  public val default_recipe_tint: DefaultRecipeTint? by fromJson()

  /**
   * Only loaded if one of `shift_animation_waypoint_stop_duration` or
   * `shift_animation_transition_duration` is not 0.
   */
  public val shift_animation_waypoints: ShiftAnimationWaypoints? by fromJson()

  /**
   * Only loaded if `shift_animation_waypoints` is defined.
   */
  public val shift_animation_waypoint_stop_duration: UShort? by fromJson()

  /**
   * Only loaded if `shift_animation_waypoints` is defined.
   */
  public val shift_animation_transition_duration: UShort? by fromJson()

  /**
   * Used by [WorkingVisualisation::apply_tint](prototype:WorkingVisualisation::apply_tint).
   */
  public val status_colors: StatusColors? by fromJson()

  /**
   * Shift of the "alt-mode icon" relative to the machine's center.
   */
  public open val entity_info_icon_shift: Vector? by fromJson()

  /**
   * Whether the "alt-mode icon" should have a black background.
   */
  public val draw_entity_info_icon_background: Boolean? by fromJson()

  /**
   * Whether the speed of the animation and working visualization should be based on the machine's
   * speed (boosted or slowed by modules).
   */
  public val match_animation_speed_to_activity: Boolean? by fromJson()

  /**
   * Whether the recipe icon should be shown on the map.
   */
  public val show_recipe_icon_on_map: Boolean? by fromJson()

  /**
   * Productivity bonus that this machine always has.
   */
  public val base_productivity: Float? by fromJson()

  /**
   * The number of module slots in this machine, and their icon positions.
   */
  public val module_specification: ModuleSpecification? by fromJson()

  /**
   * Used to display different animations when the machine is running, for example tinted based on
   * the current recipe.
   *
   * The `animation_speed` of the animation is divided by 2 by the game. For example, the default
   * animation speed of 1 means one animation frame per 2 ticks (30 fps) instead of the usual 60 fps.
   */
  public val working_visualisations: List<WorkingVisualisation>? by fromJson()
}

/**
 * A curved rail.
 */
@Serializable(CurvedRailPrototype.Serializer::class)
@SerialName("curved-rail")
public open class CurvedRailPrototype : RailPrototype() {
  public val bending_type: UnknownStringLiteral? by fromJson()

  public object Serializer : JsonReaderSerializer<CurvedRailPrototype>(CurvedRailPrototype::class)
}

@Serializable
public enum class CustomInputPrototypeAction {
  lua,
  `spawn-item`,
  `toggle-personal-roboport`,
  `toggle-personal-logistic-requests`,
  `toggle-equipment-movement-bonus`,
}

/**
 * Used for custom keyboard shortcuts/key bindings in mods. The key associated with the custom input
 * can be changed in the options. This means that `key_sequence` is simply the default key binding.
 */
@Serializable(CustomInputPrototype.Serializer::class)
@SerialName("custom-input")
public open class CustomInputPrototype : PrototypeBase() {
  /**
   * Unique textual identification of the prototype. May not contain a dot, nor exceed a length of
   * 200 characters.
   *
   * For a list of all names used in vanilla, see [data.raw](https://wiki.factorio.com/Data.raw).
   *
   * It is also the name for the event that is raised when they key (combination) is pressed and
   * action is `"lua"`, see [Tutorial:Script
   * interfaces](https://wiki.factorio.com/Tutorial:Script_interfaces#Custom_input).
   */
  override val name: String by fromJson()

  /**
   * The default key sequence for this custom input. Use "" (empty string) for unassigned.
   *
   * Use "mouse-button-2" etc for mouse buttons, mouse-button-3 for middle mouse button. Use
   * "mouse-wheel-up", "mouse-wheel-down", "mouse-wheel-left", "mouse-wheel-right" for mouse wheel.
   *
   * " + " is used to separate modifier keys from normal keys: <code>"ALT + G"</code>.
   *
   * For modifier keys, the following names are used: "CONTROL", "SHIFT", "ALT", "COMMAND".
   *
   * A key binding can contain an unlimited amount of modifier keys (listed above) but only one
   * normal key (listed below).
   */
  public val key_sequence: String by fromJson()

  /**
   * The alternative key binding for this control. See `key_sequence` for the format.
   */
  public val alternative_key_sequence: String? by fromJson()

  /**
   * The controller (game pad) keybinding for this control. Use "" (empty string) for unassigned.
   *
   * " + " is used to separate modifier buttons from normal buttons:
   * <code>"controller-righttrigger + controller-a"</code>.
   *
   * For modifier buttons, the following names are used: "controller-righttrigger",
   * "controller-lefttrigger".
   *
   * A key binding can contain an unlimited amount of modifier buttons (listed above) but only one
   * normal button (listed below).
   */
  public val controller_key_sequence: String? by fromJson()

  /**
   * The alternative controller (game pad) keybinding for this control. See
   * `controller_key_sequence` for the format.
   */
  public val controller_alternative_key_sequence: String? by fromJson()

  /**
   * When a custom-input is linked to a game control it won't show up in the control-settings GUI
   * and will fire when the linked control is pressed.
   */
  public val linked_game_control: LinkedGameControl? by fromJson()

  /**
   * Sets whether internal game events associated with the same key sequence should be fired or
   * blocked. If they are fired ("none"), then the custom input event will happen before the internal
   * game event.
   */
  public val consuming: ConsumingType? by fromJson()

  /**
   * If this custom input is enabled. Disabled custom inputs exist but are not used by the game. If
   * disabled, no event is raised when the input is used.
   */
  public val enabled: Boolean? by fromJson()

  public val enabled_while_spectating: Boolean? by fromJson()

  public val enabled_while_in_cutscene: Boolean? by fromJson()

  /**
   * If true, the type and name of the currently selected prototype will be provided as
   * "selected_prototype" in the raised [Lua event](runtime:CustomInputEvent). [This also works in
   * GUIs](https://forums.factorio.com/96125), not just the game world.
   *
   * This will also return an item in the cursor such as copper-wire or rail-planner, if nothing is
   * beneath the cursor.
   */
  public val include_selected_prototype: Boolean? by fromJson()

  /**
   * The item will be created when this input is pressed and action is set to "spawn-item". The item
   * must have the [spawnable](prototype:ItemPrototypeFlags::spawnable) flag set.
   */
  public val item_to_spawn: ItemID? by fromJson()

  /**
   * A [Lua event](runtime:CustomInputEvent) is only raised if the action is "lua".
   */
  public val action: CustomInputPrototypeAction? by fromJson()

  public object Serializer : JsonReaderSerializer<CustomInputPrototype>(CustomInputPrototype::class)
}

/**
 * A damage type. This is used in the [damage system](https://wiki.factorio.com/Damage). [A list of
 * built-in damage types can be found here](https://wiki.factorio.com/Damage#Damage_types).
 */
@Serializable(DamageType.Serializer::class)
@SerialName("damage-type")
public open class DamageType : PrototypeBase() {
  public val hidden: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<DamageType>(DamageType::class)
}

/**
 * A [decider combinator](https://wiki.factorio.com/Decider_combinator).
 */
@Serializable(DeciderCombinatorPrototype.Serializer::class)
@SerialName("decider-combinator")
public open class DeciderCombinatorPrototype : CombinatorPrototype() {
  public val equal_symbol_sprites: Sprite4Way? by fromJson()

  public val greater_symbol_sprites: Sprite4Way? by fromJson()

  public val less_symbol_sprites: Sprite4Way? by fromJson()

  public val not_equal_symbol_sprites: Sprite4Way? by fromJson()

  public val greater_or_equal_symbol_sprites: Sprite4Way? by fromJson()

  public val less_or_equal_symbol_sprites: Sprite4Way? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DeciderCombinatorPrototype>(DeciderCombinatorPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player deconstructs enough entities
 * with construction robots.
 */
@Serializable(DeconstructWithRobotsAchievementPrototype.Serializer::class)
@SerialName("deconstruct-with-robots-achievement")
public open class DeconstructWithRobotsAchievementPrototype : AchievementPrototype() {
  /**
   * This will trigger the achievement, if enough entities were deconstructed using construction
   * robots.
   */
  public val amount: UInt by fromJson()

  public object Serializer :
      JsonReaderSerializer<DeconstructWithRobotsAchievementPrototype>(DeconstructWithRobotsAchievementPrototype::class)
}

/**
 * Entity used to signify that the tile below it should be deconstructed.
 */
@Serializable(DeconstructibleTileProxyPrototype.Serializer::class)
@SerialName("deconstructible-tile-proxy")
public open class DeconstructibleTileProxyPrototype : EntityPrototype() {
  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DeconstructibleTileProxyPrototype>(DeconstructibleTileProxyPrototype::class)
}

/**
 * A [deconstruction planner](https://wiki.factorio.com/Deconstruction_planner).
 */
@Serializable(DeconstructionItemPrototype.Serializer::class)
@SerialName("deconstruction-item")
public open class DeconstructionItemPrototype : SelectionToolPrototype() {
  /**
   * Can't be > 255.
   */
  public val entity_filter_count: ItemStackIndex? by fromJson()

  /**
   * Can't be > 255.
   */
  public val tile_filter_count: ItemStackIndex? by fromJson()

  /**
   * This property is hardcoded to `"deconstruct"`.
   */
  override val selection_mode: SelectionModeFlags? by fromJson()

  /**
   * This property is hardcoded to `"cancel-deconstruct"`.
   */
  override val alt_selection_mode: SelectionModeFlags? by fromJson()

  /**
   * This property is hardcoded to `false`.
   */
  override val always_include_tiles: Boolean? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_filters: List<EntityID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_filters: List<EntityID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_type_filters: List<String>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_type_filters: List<String>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val tile_filters: List<TileID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_tile_filters: List<TileID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val tile_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_tile_filter_mode: EntityFilterMode? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DeconstructionItemPrototype>(DeconstructionItemPrototype::class)
}

/**
 * Simple decorative purpose objects on the map, they have no health and some of them are removed
 * when the player builds over. Usually used for grass patches, roots, small plants etc.
 */
@Serializable(DecorativePrototype.Serializer::class)
@SerialName("optimized-decorative")
public open class DecorativePrototype : PrototypeBase() {
  /**
   * Must contain at least 1 picture.
   */
  public val pictures: SpriteVariations by fromJson()

  /**
   * Must contain the [0,0] point. Max radius of the collision box is 8.
   */
  public val collision_box: BoundingBox? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public val grows_through_rail_path: Boolean? by fromJson()

  /**
   * Mandatory if `render_layer` = "decals". This int16 is converted to a
   * [RenderLayer](prototype:RenderLayer) internally.
   */
  public val tile_layer: Short? by fromJson()

  /**
   * Loaded only if `render_layer` = "decals".
   */
  public val decal_overdraw_priority: UShort? by fromJson()

  public val walking_sound: Sound? by fromJson()

  /**
   * Called by [DestroyDecorativesTriggerEffectItem](prototype:DestroyDecorativesTriggerEffectItem).
   */
  public val trigger_effect: TriggerEffect? by fromJson()

  public val autoplace: AutoplaceSpecification? by fromJson()

  public val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<DecorativePrototype>(DecorativePrototype::class)
}

/**
 * This prototype is used for receiving an achievement, when the player requests and receives enough
 * items using logistic robots.
 */
@Serializable(DeliverByRobotsAchievementPrototype.Serializer::class)
@SerialName("deliver-by-robots-achievement")
public open class DeliverByRobotsAchievementPrototype : AchievementPrototype() {
  /**
   * This will trigger the achievement, when the player receives enough items through logistic
   * robots.
   */
  public val amount: MaterialAmountType by fromJson()

  public object Serializer :
      JsonReaderSerializer<DeliverByRobotsAchievementPrototype>(DeliverByRobotsAchievementPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player finishes the game without
 * building a specific entity.
 */
@Serializable(DontBuildEntityAchievementPrototype.Serializer::class)
@SerialName("dont-build-entity-achievement")
public open class DontBuildEntityAchievementPrototype : AchievementPrototype() {
  /**
   * This will disable the achievement, if this entity is placed. If you finish the game without
   * building this entity, you receive the achievement.
   */
  public val dont_build: ItemOrList<EntityID> by fromJson()

  public val amount: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DontBuildEntityAchievementPrototype>(DontBuildEntityAchievementPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player finishes the game without
 * crafting more than a set amount.
 */
@Serializable(DontCraftManuallyAchievementPrototype.Serializer::class)
@SerialName("dont-craft-manually-achievement")
public open class DontCraftManuallyAchievementPrototype : AchievementPrototype() {
  /**
   * This will disable the achievement, if the player crafts more than this.
   */
  public val amount: MaterialAmountType by fromJson()

  public object Serializer :
      JsonReaderSerializer<DontCraftManuallyAchievementPrototype>(DontCraftManuallyAchievementPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player finishes the game without
 * receiving energy from a specific energy source.
 */
@Serializable(DontUseEntityInEnergyProductionAchievementPrototype.Serializer::class)
@SerialName("dont-use-entity-in-energy-production-achievement")
public open class DontUseEntityInEnergyProductionAchievementPrototype : AchievementPrototype() {
  /**
   * This will **not** disable the achievement, if this entity is placed, and you have received any
   * amount of power from it.
   */
  public val excluded: ItemOrList<EntityID> by fromJson()

  /**
   * This will disable the achievement, if this entity is placed, and you have received any amount
   * of power from it. If you finish the game without receiving power from this entity, you receive the
   * achievement.
   */
  public val included: ItemOrList<EntityID>? by fromJson()

  public val last_hour_only: Boolean? by fromJson()

  public val minimum_energy_produced: Energy? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DontUseEntityInEnergyProductionAchievementPrototype>(DontUseEntityInEnergyProductionAchievementPrototype::class)
}

/**
 * Properties of the editor controller.
 */
@Serializable(EditorControllerPrototype.Serializer::class)
@SerialName("editor-controller")
public open class EditorControllerPrototype : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the editor controller. Base game uses "default".
   */
  public val name: String by fromJson()

  public val inventory_size: ItemStackIndex by fromJson()

  public val gun_inventory_size: ItemStackIndex by fromJson()

  /**
   * Must be >= 0.34375.
   */
  public val movement_speed: Double by fromJson()

  public val item_pickup_distance: Double by fromJson()

  public val loot_pickup_distance: Double by fromJson()

  public val mining_speed: Double by fromJson()

  public val enable_flash_light: Boolean by fromJson()

  public val adjust_speed_based_off_zoom: Boolean by fromJson()

  public val render_as_day: Boolean by fromJson()

  public val instant_blueprint_building: Boolean by fromJson()

  public val instant_deconstruction: Boolean by fromJson()

  public val instant_upgrading: Boolean by fromJson()

  public val instant_rail_planner: Boolean by fromJson()

  public val show_status_icons: Boolean by fromJson()

  public val show_hidden_entities: Boolean by fromJson()

  public val show_entity_tags: Boolean by fromJson()

  public val show_entity_health_bars: Boolean by fromJson()

  public val show_additional_entity_info_gui: Boolean by fromJson()

  public val generate_neighbor_chunks: Boolean by fromJson()

  public val fill_built_entity_energy_buffers: Boolean by fromJson()

  public val show_character_tab_in_controller_gui: Boolean by fromJson()

  public val show_infinity_filters_in_controller_gui: Boolean by fromJson()

  public val placed_corpses_never_expire: Boolean by fromJson()

  public object Serializer :
      JsonReaderSerializer<EditorControllerPrototype>(EditorControllerPrototype::class)
}

@Serializable
public enum class GuiMode {
  all,
  none,
  admins,
}

/**
 * Entity with electric energy source with that can have some of its values changed runtime. Useful
 * for modding in energy consumers/producers.
 */
@Serializable(ElectricEnergyInterfacePrototype.Serializer::class)
@SerialName("electric-energy-interface")
public open class ElectricEnergyInterfacePrototype : EntityWithOwnerPrototype() {
  public val energy_source: ElectricEnergySource by fromJson()

  public val energy_production: Energy? by fromJson()

  public val energy_usage: Energy? by fromJson()

  public val gui_mode: GuiMode? by fromJson()

  /**
   * Whether the electric energy interface animation always runs instead of being scaled to
   * activity.
   */
  public val continuous_animation: Boolean? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  /**
   * The light that this electric energy interface emits.
   */
  public val light: LightDefinition? by fromJson()

  public val picture: Sprite? by fromJson()

  /**
   * Only loaded if `picture` is not defined.
   */
  public val pictures: Sprite4Way? by fromJson()

  /**
   * Only loaded if both `picture` and `pictures` are not defined.
   */
  public val animation: Animation? by fromJson()

  /**
   * Only loaded if `picture`, `pictures`, and `animation` are not defined.
   */
  public val animations: Animation4Way? by fromJson()

  override val allow_copy_paste: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ElectricEnergyInterfacePrototype>(ElectricEnergyInterfacePrototype::class)
}

/**
 * An electric pole - part of the [electric system](https://wiki.factorio.com/Electric_system).
 */
@Serializable(ElectricPolePrototype.Serializer::class)
@SerialName("electric-pole")
public open class ElectricPolePrototype : EntityWithOwnerPrototype() {
  public val pictures: RotatedSprite by fromJson()

  /**
   * The "radius" of this pole's supply area. Corresponds to *half* of the "supply area" in the item
   * tooltip. If this is 3.5, the pole will have a 7x7 supply area.
   *
   * Max value is 64.
   */
  public val supply_area_distance: Double by fromJson()

  public val connection_points: List<WireConnectionPoint> by fromJson()

  public val radius_visualisation_picture: Sprite? by fromJson()

  /**
   * Drawn above the `pictures` when the electric pole is connected to an electric network.
   */
  public val active_picture: Sprite? by fromJson()

  /**
   * The maximum distance between this pole and any other connected pole - if two poles are farther
   * apart than this, they cannot be connected together directly. Corresponds to "wire reach" in the
   * item tooltip.
   *
   * Max value is 64.
   */
  public val maximum_wire_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * Drawn when the electric pole is connected to an electric network.
   */
  public val light: LightDefinition? by fromJson()

  public val track_coverage_during_build_by_moving: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ElectricPolePrototype>(ElectricPolePrototype::class)
}

/**
 * A turret that uses electricity as ammunition.
 */
@Serializable(ElectricTurretPrototype.Serializer::class)
@SerialName("electric-turret")
public open class ElectricTurretPrototype : TurretPrototype() {
  public val energy_source: UnknownUnion by fromJson()

  public object Serializer :
      JsonReaderSerializer<ElectricTurretPrototype>(ElectricTurretPrototype::class)
}

/**
 * Can spawn entities. Used for biter/spitter nests.
 */
@Serializable(EnemySpawnerPrototype.Serializer::class)
@SerialName("unit-spawner")
public open class EnemySpawnerPrototype : EntityWithOwnerPrototype() {
  public val animations: AnimationVariations by fromJson()

  /**
   * Count of enemies this spawner can sustain.
   */
  public val max_count_of_owned_units: UInt by fromJson()

  /**
   * How many friendly units are required within the
   * [EnemySpawnerPrototype::spawning_radius](prototype:EnemySpawnerPrototype::spawning_radius) of this
   * spawner for it to stop producing more units.
   */
  public val max_friends_around_to_spawn: UInt by fromJson()

  /**
   * Ticks for cooldown after unit is spawned. The first member of the tuple is min, the second
   * member of the tuple is max.
   */
  public val spawning_cooldown: Tuple2<Double> by fromJson()

  /**
   * How far from the spawner can the units be spawned.
   */
  public val spawning_radius: Double by fromJson()

  /**
   * What spaces should be between the spawned units.
   */
  public val spawning_spacing: Double by fromJson()

  /**
   * Max richness to determine spawn shift. Spawn shift is linear interpolation between 0 and
   * max_spawn_shift.
   */
  public val max_richness_for_spawn_shift: Double by fromJson()

  /**
   * Caps how much richness can be added on top of evolution when spawning units. [See
   * also](https://www.reddit.com/r/factorio/comments/8pjscm/friday_facts_246_the_gui_update_part_3/e0bttnp/)
   */
  public val max_spawn_shift: Double by fromJson()

  public val pollution_absorption_absolute: Double by fromJson()

  public val pollution_absorption_proportional: Double by fromJson()

  public val call_for_help_radius: Double by fromJson()

  /**
   * Array of the [entities](prototype:EntityPrototype) that this spawner can spawn and their spawn
   * probabilities. The sum of probabilities is expected to be 1.0. The array must not be empty.
   */
  public val result_units: List<UnitSpawnDefinition> by fromJson()

  public val dying_sound: Sound? by fromJson()

  public val integration: SpriteVariations? by fromJson()

  public val min_darkness_to_spawn: Float? by fromJson()

  public val max_darkness_to_spawn: Float? by fromJson()

  public val random_animation_offset: Boolean? by fromJson()

  /**
   * Whether `spawn_decoration` should be spawned when enemies
   * [expand](https://wiki.factorio.com/Enemies#Expansions).
   */
  public val spawn_decorations_on_expansion: Boolean? by fromJson()

  /**
   * Decoratives to be created when the spawner is created by the [map
   * generator](https://wiki.factorio.com/Map_generator). Placed when enemies expand if
   * `spawn_decorations_on_expansion` is set to true.
   */
  public val spawn_decoration: ItemOrList<CreateDecorativesTriggerEffectItem>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<EnemySpawnerPrototype>(EnemySpawnerPrototype::class)
}

/**
 * Used by [energy shield](https://wiki.factorio.com/Energy_shield).
 */
@Serializable(EnergyShieldEquipmentPrototype.Serializer::class)
@SerialName("energy-shield-equipment")
public open class EnergyShieldEquipmentPrototype : EquipmentPrototype() {
  public val max_shield_value: Float by fromJson()

  public val energy_per_shield: Energy by fromJson()

  public object Serializer :
      JsonReaderSerializer<EnergyShieldEquipmentPrototype>(EnergyShieldEquipmentPrototype::class)
}

/**
 * The entity used for ghosts of entities. In-game, the inner entity (the entity this is a ghost of)
 * is rendered with a [UtilityConstants::ghost_tint](prototype:UtilityConstants::ghost_tint).
 */
@Serializable(EntityGhostPrototype.Serializer::class)
@SerialName("entity-ghost")
public open class EntityGhostPrototype : EntityPrototype() {
  public val medium_build_sound: Sound? by fromJson()

  public val large_build_sound: Sound? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<EntityGhostPrototype>(EntityGhostPrototype::class)
}

/**
 * Deprecated in 0.18. The type "particle" has been obsoleted and cannot be created. See
 * [ParticlePrototype](prototype:ParticlePrototype) for particles.
 */
@Serializable(EntityParticlePrototype.Serializer::class)
@SerialName("particle")
public open class EntityParticlePrototype : EntityPrototype() {
  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<EntityParticlePrototype>(EntityParticlePrototype::class)
}

/**
 * Abstract base of all entities in the game. Entity is nearly everything that can be on the map
 * (except tiles).
 *
 * For in game script access to entity, take a look at [LuaEntity](runtime:LuaEntity).
 */
public sealed class EntityPrototype : PrototypeBase() {
  /**
   * This will be used in the electric network statistics, editor building selection, and the bonus
   * gui. Can't be an empty array.
   *
   * Either this or `icon` is mandatory for entities that have at least one of these flags active:
   * `"placeable-neutral"`, `"placeable-player`", `"placeable-enemy"`.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * Path to the icon file.
   *
   * Either this or `icons` is mandatory for entities that have at least one of these flags active:
   * `"placeable-neutral"`, `"placeable-player`", `"placeable-enemy"`.
   *
   * Only loaded if `icons` is not defined.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Only loaded if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  /**
   * Specification of the entity collision boundaries. Empty collision box means no collision and is
   * used for smoke, projectiles, particles, explosions etc.
   *
   * The `{0,0}` coordinate in the collision box will match the entity position. It should be near
   * the center of the collision box, to keep correct entity drawing order. The bounding box must
   * include the `{0,0}` coordinate.
   *
   * Note, that for buildings, it is customary to leave 0.1 wide border between the edge of the tile
   * and the edge of the building, this lets the player move between the building and electric
   * poles/inserters etc.
   */
  public open val collision_box: BoundingBox? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  public open val collision_mask: CollisionMask? by fromJson()

  /**
   * Used instead of the collision box during map generation. Allows space entities differently
   * during map generation, for example if the box is bigger, the entities will be placed farther
   * apart.
   */
  public val map_generator_bounding_box: BoundingBox? by fromJson()

  /**
   * Specification of the entity selection area. When empty the entity will have no selection area
   * (and thus is not selectable).
   *
   * The selection box is usually a little bit bigger than the collision box. For tileable entities
   * (like buildings) it should match the tile size of the building.
   */
  public open val selection_box: BoundingBox? by fromJson()

  /**
   * Specification of space needed to see the whole entity in GUIs. This is used to calculate the
   * correct zoom and positioning in the entity info gui, for example in the entity tooltip.
   */
  public val drawing_box: BoundingBox? by fromJson()

  /**
   * Used to set the area of the entity that can have stickers on it, currently only used for units
   * to specify the area where the green slow down stickers can appear.
   */
  public val sticker_box: BoundingBox? by fromJson()

  /**
   * Where beams should hit the entity. Useful if the bounding box only covers part of the entity
   * (e.g. feet of the character) and beams only hitting there would look weird.
   */
  public val hit_visualization_box: BoundingBox? by fromJson()

  /**
   * Defaults to the mask from
   * [UtilityConstants::default_trigger_target_mask_by_type](prototype:UtilityConstants::default_trigger_target_mask_by_type).
   */
  public val trigger_target_mask: TriggerTargetMask? by fromJson()

  public open val flags: EntityPrototypeFlags? by fromJson()

  /**
   * The item given to the player when they mine the entity and other properties relevant to mining
   * this entity.
   */
  public val minable: MinableProperties? by fromJson()

  /**
   * The name of the [subgroup](prototype:ItemSubGroup) this entity should be sorted into in the map
   * editor building selection.
   */
  public val subgroup: ItemSubGroupID? by fromJson()

  public open val allow_copy_paste: Boolean? by fromJson()

  public val selectable_in_game: Boolean? by fromJson()

  /**
   * The entity with the higher number is selectable before the entity with the lower number. When
   * two entities have the same selection priority, the one with the highest [collision
   * mask](prototype:CollisionMask) (as determined by the order on that page) is selected.
   */
  public open val selection_priority: UByte? by fromJson()

  /**
   * Supported values are 1 (for 1x1 grid) and 2 (for 2x2 grid, like rails).
   *
   * Internally forced to be `2` for [RailPrototype](prototype:RailPrototype),
   * [RailRemnantsPrototype](prototype:RailRemnantsPrototype) and
   * [TrainStopPrototype](prototype:TrainStopPrototype).
   */
  public open val build_grid_size: UByte? by fromJson()

  /**
   * Whether this entity should remove decoratives that collide with it when this entity is built.
   * When set to "automatic", if the entity type is considered [a
   * building](runtime:LuaEntityPrototype::is_building) (e.g. an assembling machine or a wall) it will
   * remove decoratives.
   *
   * Using boolean values for this property is deprecated, however they have the same meaning as the
   * corresponding strings.
   */
  public val remove_decoratives: UnknownUnion? by fromJson()

  /**
   * Amount of emissions created (positive number) or cleaned (negative number) every second by the
   * entity. This is passive and currently used just for trees and fires. This is independent of the
   * [emissions of energy sources](prototype:BaseEnergySource::emissions_per_minute) used by machines,
   * which are created actively depending on the power consumption.
   */
  public val emissions_per_second: Double? by fromJson()

  /**
   * The cursor size used when shooting at this entity.
   */
  public val shooting_cursor_size: Float? by fromJson()

  /**
   * The smoke that is shown when the entity is placed.
   */
  public val created_smoke: CreateTrivialSmokeEffectItem? by fromJson()

  /**
   * Will also work on entities that don't actually do work.
   */
  public val working_sound: WorkingSound? by fromJson()

  /**
   * The effect/trigger that happens when the entity is placed.
   */
  public val created_effect: Trigger? by fromJson()

  public val build_sound: Sound? by fromJson()

  public val mined_sound: Sound? by fromJson()

  public val mining_sound: Sound? by fromJson()

  public val rotated_sound: Sound? by fromJson()

  /**
   * When playing this sound, the volume is scaled by the speed of the vehicle when colliding with
   * this entity.
   */
  public val vehicle_impact_sound: Sound? by fromJson()

  public open val open_sound: Sound? by fromJson()

  public open val close_sound: Sound? by fromJson()

  public val radius_visualisation_specification: RadiusVisualisationSpecification? by fromJson()

  public val build_base_evolution_requirement: Double? by fromJson()

  public val alert_icon_shift: Vector? by fromJson()

  public val alert_icon_scale: Float? by fromJson()

  /**
   * This allows you to replace an entity that's already placed, with a different one in your
   * inventory. For example, replacing a burner inserter with a fast inserter. The replacement entity
   * can be a different rotation to the replaced entity and you can replace an entity with the same
   * type.
   *
   * This is simply a string, so any string can be used here. The entity that should be replaced
   * simply has to use the same string here.
   */
  public val fast_replaceable_group: String? by fromJson()

  /**
   * Name of the entity that will be automatically selected as the upgrade of this entity when using
   * the [upgrade planner](https://wiki.factorio.com/Upgrade_planner) without configuration.
   *
   * This entity may not have "not-upgradable" flag set and must be minable. This entity mining
   * result must not contain item product with "hidden" flag set. Mining results with no item products
   * are allowed. This entity may not be a [RollingStockPrototype](prototype:RollingStockPrototype).
   *
   * The upgrade target entity needs to have the same bounding box, collision mask, and fast
   * replaceable group as this entity. The upgrade target entity must have least 1 item that builds it
   * that isn't hidden.
   */
  public val next_upgrade: EntityID? by fromJson()

  /**
   * When this is true, this entity prototype should be included during tile collision checks with
   * tiles that have
   * [TilePrototype::check_collision_with_entities](prototype:TilePrototype::check_collision_with_entities)
   * set to true.
   */
  public val protected_from_tile_building: Boolean? by fromJson()

  /**
   * Item that when placed creates this entity. Determines which item is picked when "Q" (smart
   * pipette) is used on this entity. Determines which item and item amount is needed in a blueprint of
   * this entity and to revive a ghost of this entity.
   *
   * The item count specified here can't be larger than the stack size of that item.
   */
  public val placeable_by: ItemOrList<ItemToPlace>? by fromJson()

  /**
   * The entity that remains when this one is mined, deconstructed or fast-replaced. The entity wont
   * actually be spawned if it would collide with the entity that is in the process of being mined.
   */
  public val remains_when_mined: ItemOrList<EntityID>? by fromJson()

  /**
   * Names of the entity prototypes this entity prototype can be pasted on to in addition to the
   * standard supported types.
   *
   * This is used to allow copying between types that aren't compatible on the C++ code side, by
   * allowing mods to receive the [on_entity_settings_pasted](runtime:on_entity_settings_pasted) event
   * for the given entity and do the setting pasting via script.
   */
  public val additional_pastable_entities: List<EntityID>? by fromJson()

  /**
   * Used to determine how the center of the entity should be positioned when building (unless the
   * off-grid [flag](prototype:EntityPrototypeFlags) is specified).
   *
   * When the tile width is odd, the center will be in the center of the tile, when it is even, the
   * center is on the tile transition.
   */
  public val tile_width: UInt? by fromJson()

  public val tile_height: UInt? by fromJson()

  /**
   * Used to specify the rules for placing this entity during map generation.
   */
  public val autoplace: AutoplaceSpecification? by fromJson()

  public open val map_color: Color? by fromJson()

  public val friendly_map_color: Color? by fromJson()

  public val enemy_map_color: Color? by fromJson()

  /**
   * May also be defined inside `graphics_set` instead of directly in the entity prototype. This is
   * useful for entities that use a `graphics_set` property to define their graphics, because then all
   * graphics can be defined in one place.
   *
   * [Currently only renders](https://forums.factorio.com/100703) for
   * [EntityWithHealthPrototype](prototype:EntityWithHealthPrototype).
   */
  public val water_reflection: WaterReflectionDefinition? by fromJson()

  /**
   * Used to order prototypes in inventory, recipes and GUIs. May not exceed a length of 200
   * characters.
   *
   * The order string is taken from the items in `placeable_by` if they exist, or from an item that
   * has its [place_result](prototype:ItemPrototype::place_result) set to this entity.
   */
  override val order: Order? by fromJson()
}

/**
 * Abstract base of all entities with health in the game.
 */
public sealed class EntityWithHealthPrototype : EntityPrototype() {
  /**
   * The unit health can never go over the maximum. Default health of units on creation is set to
   * max. Must be greater than 0.
   */
  public val max_health: Float? by fromJson()

  /**
   * The amount of health automatically regenerated per tick. The entity must be active for this to
   * work.
   */
  public open val healing_per_tick: Float? by fromJson()

  /**
   * Multiplier of [RepairToolPrototype::speed](prototype:RepairToolPrototype::speed) for this
   * entity prototype.
   */
  public val repair_speed_modifier: Float? by fromJson()

  /**
   * The entities that are spawned in place of this one when it dies.
   */
  public val dying_explosion: ItemOrList<ExplosionDefinition>? by fromJson()

  public val dying_trigger_effect: TriggerEffect? by fromJson()

  public val damaged_trigger_effect: TriggerEffect? by fromJson()

  /**
   * The loot is dropped on the ground when the entity is killed.
   */
  public val loot: List<LootItem>? by fromJson()

  /**
   * See [damage](https://wiki.factorio.com/Damage).
   */
  public val resistances: List<Resistance>? by fromJson()

  public val attack_reaction: ItemOrList<AttackReactionItem>? by fromJson()

  /**
   * Played when this entity is repaired with a
   * [RepairToolPrototype](prototype:RepairToolPrototype).
   */
  public val repair_sound: Sound? by fromJson()

  public val alert_when_damaged: Boolean? by fromJson()

  /**
   * Whether the resistances of this entity should be hidden in the entity tooltip.
   */
  public val hide_resistances: Boolean? by fromJson()

  public val create_ghost_on_death: Boolean? by fromJson()

  public val random_corpse_variation: Boolean? by fromJson()

  public val integration_patch_render_layer: RenderLayer? by fromJson()

  /**
   * Specifies the names of the [CorpsePrototype](prototype:CorpsePrototype) to be used when this
   * entity dies.
   */
  public open val corpse: ItemOrList<EntityID>? by fromJson()

  /**
   * Sprite drawn on ground under the entity to make it feel more integrated into the ground.
   */
  public val integration_patch: Sprite4Way? by fromJson()
}

/**
 * Abstract base of all entities with a force in the game. These entities have a
 * [LuaEntity::unit_number](runtime:LuaEntity::unit_number) during runtime. Can be high priority
 * [military targets](https://wiki.factorio.com/Military_units_and_structures).
 */
public sealed class EntityWithOwnerPrototype : EntityWithHealthPrototype() {
  /**
   * Whether this prototype should be a high priority target for enemy forces. See [Military units
   * and structures](https://wiki.factorio.com/Military_units_and_structures).
   */
  public open val is_military_target: Boolean? by fromJson()

  /**
   * If this is true, this entity's `is_military_target` property can be changed during runtime (on
   * the entity, not on the prototype itself).
   */
  public open val allow_run_time_change_of_is_military_target: Boolean? by fromJson()
}

/**
 * Defines a category to be available to [equipment](prototype:EquipmentPrototype) and [equipment
 * grids](prototype:EquipmentGridPrototype).
 */
@Serializable(EquipmentCategory.Serializer::class)
@SerialName("equipment-category")
public open class EquipmentCategory : PrototypeBase() {
  public object Serializer : JsonReaderSerializer<EquipmentCategory>(EquipmentCategory::class)
}

/**
 * The prototype of an equipment grid, for example the one used in a [power
 * armor](https://wiki.factorio.com/Power_armor).
 */
@Serializable(EquipmentGridPrototype.Serializer::class)
@SerialName("equipment-grid")
public open class EquipmentGridPrototype : PrototypeBase() {
  /**
   * Only [equipment](prototype:EquipmentPrototype) with at least one of these
   * [categories](prototype:EquipmentCategory) can be inserted into the grid.
   */
  public val equipment_categories: List<EquipmentCategoryID> by fromJson()

  public val width: UInt by fromJson()

  public val height: UInt by fromJson()

  /**
   * Whether this locked from user interaction which means that the user cannot put equipment into
   * or take equipment from this equipment grid.
   */
  public val locked: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<EquipmentGridPrototype>(EquipmentGridPrototype::class)
}

/**
 * Abstract base of all equipment modules. Equipment modules can be inserted into [equipment
 * grids](prototype:EquipmentGridPrototype).
 */
public sealed class EquipmentPrototype : PrototypeBase() {
  /**
   * The graphics to use when this equipment is shown inside an equipment grid.
   */
  public val sprite: Sprite by fromJson()

  /**
   * How big this equipment should be in the grid and whether it should be one solid rectangle or of
   * a custom shape.
   */
  public val shape: EquipmentShape by fromJson()

  /**
   * Sets the categories of the equipment. It can only be inserted into
   * [grids](prototype:EquipmentGridPrototype::equipment_categories) with at least one matching
   * category.
   */
  public val categories: List<EquipmentCategoryID> by fromJson()

  public val energy_source: ElectricEnergySource by fromJson()

  /**
   * Name of the item prototype that should be returned to the player when they remove this
   * equipment from an equipment grid.
   */
  public val take_result: ItemID? by fromJson()

  /**
   * The color that the background of this equipment should have when shown inside an equipment
   * grid.
   */
  public val background_color: Color? by fromJson()

  /**
   * The color that the border of the background of this equipment should have when shown inside an
   * equipment grid.
   */
  public val background_border_color: Color? by fromJson()

  /**
   * The color that the background of this equipment should have when held in the players hand and
   * hovering over an equipment grid.
   */
  public val grabbed_background_color: Color? by fromJson()
}

/**
 * Used to play an animation and a sound.
 */
@Serializable(ExplosionPrototype.Serializer::class)
@SerialName("explosion")
public open class ExplosionPrototype : EntityPrototype() {
  public val animations: AnimationVariations by fromJson()

  public val sound: Sound? by fromJson()

  /**
   * Mandatory if `smoke_count` > 0.
   */
  public val smoke: TrivialSmokeID? by fromJson()

  public open val height: Float? by fromJson()

  public val smoke_slow_down_factor: Float? by fromJson()

  public val smoke_count: UShort? by fromJson()

  public val rotate: Boolean? by fromJson()

  public val beam: Boolean? by fromJson()

  public val correct_rotation: Boolean? by fromJson()

  public val scale_animation_speed: Boolean? by fromJson()

  public val fade_in_duration: UByte? by fromJson()

  public val fade_out_duration: UByte? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public val scale_in_duration: UByte? by fromJson()

  public val scale_out_duration: UByte? by fromJson()

  public val scale_end: Float? by fromJson()

  public val scale_increment_per_tick: Float? by fromJson()

  /**
   * Silently clamped to be between 0 and 1.
   */
  public val light_intensity_factor_initial: Float? by fromJson()

  /**
   * Silently clamped to be between 0 and 1.
   */
  public val light_intensity_factor_final: Float? by fromJson()

  /**
   * Silently clamped to be between 0 and 1.
   */
  public val light_size_factor_initial: Float? by fromJson()

  /**
   * Silently clamped to be between 0 and 1.
   */
  public val light_size_factor_final: Float? by fromJson()

  public val light: LightDefinition? by fromJson()

  public val light_intensity_peak_start_progress: Float? by fromJson()

  public val light_intensity_peak_end_progress: Float? by fromJson()

  public val light_size_peak_start_progress: Float? by fromJson()

  public val light_size_peak_end_progress: Float? by fromJson()

  public val scale_initial: Float? by fromJson()

  public val scale_initial_deviation: Float? by fromJson()

  public val scale: Float? by fromJson()

  public val scale_deviation: Float? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<ExplosionPrototype>(ExplosionPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player finishes the game.
 */
@Serializable(FinishTheGameAchievementPrototype.Serializer::class)
@SerialName("finish-the-game-achievement")
public open class FinishTheGameAchievementPrototype : AchievementPrototype() {
  /**
   * This lets the game know how long into a game, before you can no longer complete the
   * achievement. 0 means infinite time.
   */
  public val until_second: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FinishTheGameAchievementPrototype>(FinishTheGameAchievementPrototype::class)
}

/**
 * A fire.
 */
@Serializable(FireFlamePrototype.Serializer::class)
@SerialName("fire")
public open class FireFlamePrototype : EntityPrototype() {
  public val damage_per_tick: DamagePrototype by fromJson()

  public val spread_delay: UInt by fromJson()

  public val spread_delay_deviation: UInt by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public val initial_render_layer: RenderLayer? by fromJson()

  public val secondary_render_layer: RenderLayer? by fromJson()

  public val small_tree_fire_pictures: AnimationVariations? by fromJson()

  public val pictures: AnimationVariations? by fromJson()

  public val smoke_source_pictures: AnimationVariations? by fromJson()

  public val secondary_pictures: AnimationVariations? by fromJson()

  public val burnt_patch_pictures: SpriteVariations? by fromJson()

  public val secondary_picture_fade_out_start: UInt? by fromJson()

  public val secondary_picture_fade_out_duration: UInt? by fromJson()

  public val spawn_entity: EntityID? by fromJson()

  public val smoke: List<SmokeSource>? by fromJson()

  public val maximum_spread_count: UShort? by fromJson()

  /**
   * Spawns this many `secondary_pictures` around the entity when it first spawns. It waits
   * `delay_between_initial_flames` between each spawned `secondary_pictures`. This can be used to make
   * fires look less repetitive.
   *
   * For example, spitters use this to make several smaller splashes around the main one.
   */
  public val initial_flame_count: UByte? by fromJson()

  /**
   * If `false`, then all animations loop. If `true`, they run once and stay on the final frame.
   * Also changes the behavior of several other fire properties as mentioned in their descriptions.
   *
   * For example, spitters use alternate behavior, flamethrower flames don't.
   */
  public val uses_alternative_behavior: Boolean? by fromJson()

  public val limit_overlapping_particles: Boolean? by fromJson()

  public val tree_dying_factor: Float? by fromJson()

  public val fade_in_duration: UInt? by fromJson()

  public val fade_out_duration: UInt? by fromJson()

  public val initial_lifetime: UInt? by fromJson()

  public val damage_multiplier_decrease_per_tick: Float? by fromJson()

  public val damage_multiplier_increase_per_added_fuel: Float? by fromJson()

  public val maximum_damage_multiplier: Float? by fromJson()

  public val lifetime_increase_by: UInt? by fromJson()

  public val lifetime_increase_cooldown: UInt? by fromJson()

  public val maximum_lifetime: UInt? by fromJson()

  public val add_fuel_cooldown: UInt? by fromJson()

  public val delay_between_initial_flames: UInt? by fromJson()

  public val smoke_fade_in_duration: UInt? by fromJson()

  public val smoke_fade_out_duration: UInt? by fromJson()

  public val on_fuel_added_action: Trigger? by fromJson()

  public val on_damage_tick_effect: Trigger? by fromJson()

  public val light: LightDefinition? by fromJson()

  public val particle_alpha_blend_duration: UShort? by fromJson()

  public val burnt_patch_lifetime: UInt? by fromJson()

  public val burnt_patch_alpha_default: Float? by fromJson()

  /**
   * Only loaded if `uses_alternative_behavior` is true.
   */
  public val particle_alpha: Float? by fromJson()

  /**
   * Only loaded if `uses_alternative_behavior` is true.
   */
  public val particle_alpha_deviation: Float? by fromJson()

  /**
   * Only loaded if `uses_alternative_behavior` is false.
   */
  public val flame_alpha: Float? by fromJson()

  /**
   * Only loaded if `uses_alternative_behavior` is false.
   */
  public val flame_alpha_deviation: Float? by fromJson()

  public val burnt_patch_alpha_variations: List<TileAndAlpha>? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<FireFlamePrototype>(FireFlamePrototype::class)
}

/**
 * Entity that spawns in water tiles, which can be mined. Moves around unless deactivated with
 * [LuaEntity::active](runtime:LuaEntity::active) = false.
 */
@Serializable(FishPrototype.Serializer::class)
@SerialName("fish")
public open class FishPrototype : EntityWithHealthPrototype() {
  public val pictures: SpriteVariations by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<FishPrototype>(FishPrototype::class)
}

/**
 * Explosion that can deal damage.
 */
@Serializable(FlameThrowerExplosionPrototype.Serializer::class)
@SerialName("flame-thrower-explosion")
public open class FlameThrowerExplosionPrototype : ExplosionPrototype() {
  public val damage: DamagePrototype by fromJson()

  public val slow_down_factor: Double by fromJson()

  override val height: Float? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FlameThrowerExplosionPrototype>(FlameThrowerExplosionPrototype::class)
}

/**
 * A fluid.
 */
@Serializable(FluidPrototype.Serializer::class)
@SerialName("fluid")
public open class FluidPrototype : PrototypeBase() {
  /**
   * Can't be an empty array.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * Path to the icon file.
   *
   * Mandatory if `icons` is not defined.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Mandatory if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  /**
   * Also the minimum temperature of the fluid. Has to be lower than `max_temperature`.
   */
  public val default_temperature: Double by fromJson()

  /**
   * Used by bars that show the fluid color, like the flamethrower turret fill bar in the tooltip,
   * or the fill bar for the fluid wagon tooltip; and for the pipe windows and storage tank fill
   * gauges.
   */
  public val base_color: Color by fromJson()

  /**
   * Used only for pipe windows or storage tank fill gauges.
   */
  public val flow_color: Color by fromJson()

  public val max_temperature: Double? by fromJson()

  /**
   * Joule needed to heat 1 Unit by 1 °C.
   */
  public val heat_capacity: Energy? by fromJson()

  public val fuel_value: Energy? by fromJson()

  /**
   * Scales pollution generated when the fluid is consumed.
   */
  public val emissions_multiplier: Double? by fromJson()

  /**
   * The name of the [subgroup](prototype:ItemSubGroup) of this fluid. The value of this property
   * may not be an empty string. It either has to be `nil`, or a non-empty string.
   */
  public val subgroup: ItemSubGroupID? by fromJson()

  /**
   * Above this temperature the `gas_flow` animation is used to display the fluid inside storage
   * tanks and pipes.
   */
  public val gas_temperature: Double? by fromJson()

  /**
   * Hides the fluid from the signal selection screen.
   */
  public val hidden: Boolean? by fromJson()

  /**
   * Whether the fluid should be included in the barrel recipes automatically generated by the base
   * mod.
   *
   * This property is not read by the game engine itself, but the base mod's data-updates.lua file.
   * This means it is discarded by the game engine after loading finishes.
   */
  public val auto_barrel: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<FluidPrototype>(FluidPrototype::class)
}

/**
 * Used for example for the handheld flamethrower.
 */
@Serializable(FluidStreamPrototype.Serializer::class)
@SerialName("stream")
public open class FluidStreamPrototype : EntityPrototype() {
  /**
   * The stream will spawn one particle every `particle_spawn_interval` ticks until the
   * `particle_spawn_timeout` is reached. The first particle will trigger an `initial_action` upon
   * landing. Each particle triggers an `action` upon landing. Particles spawned within a single
   * `particle_spawn_timeout` interval will be connected by a stretched `spine_animation`.
   */
  public val particle_spawn_interval: UShort by fromJson()

  /**
   * Must be larger than 0. `particle_horizontal_speed` has to be greater than
   * `particle_horizontal_speed_deviation`.
   */
  public val particle_horizontal_speed: Float by fromJson()

  public val particle_horizontal_speed_deviation: Float by fromJson()

  public val particle_vertical_acceleration: Float by fromJson()

  /**
   * Action that is triggered when the first particle lands.
   */
  public val initial_action: Trigger? by fromJson()

  /**
   * Action that is triggered every time a particle lands. Not triggered for the first particle if
   * `initial_action` is non-empty.
   */
  public val action: Trigger? by fromJson()

  public val special_neutral_target_damage: DamagePrototype? by fromJson()

  public val width: Float? by fromJson()

  /**
   * Number of spawned child particles of the stream. Must be greater than 0 and less than 256.
   */
  public val particle_buffer_size: UInt? by fromJson()

  public val particle_spawn_timeout: UShort? by fromJson()

  public val particle_start_alpha: Float? by fromJson()

  public val particle_end_alpha: Float? by fromJson()

  public val particle_start_scale: Float? by fromJson()

  public val particle_alpha_per_part: Float? by fromJson()

  public val particle_scale_per_part: Float? by fromJson()

  /**
   * Value between 0 and 1.
   */
  public val particle_fade_out_threshold: Float? by fromJson()

  /**
   * Value between 0 and 1.
   */
  public val particle_loop_exit_threshold: Float? by fromJson()

  /**
   * Will be set to 1 by the game if less than 1.
   */
  public val particle_loop_frame_count: UShort? by fromJson()

  /**
   * Will be set to 1 by the game if less than 1.
   */
  public val particle_fade_out_duration: UShort? by fromJson()

  public val spine_animation: Animation? by fromJson()

  public val particle: Animation? by fromJson()

  public val shadow: Animation? by fromJson()

  /**
   * Smoke spawning is controlled by `progress_to_create_smoke`.
   */
  public val smoke_sources: List<SmokeSource>? by fromJson()

  /**
   * The point in the particles projectile arc to start spawning smoke. 0.5 (the default) starts
   * spawning smoke at the halfway point between the source and target.
   */
  public val progress_to_create_smoke: Float? by fromJson()

  public val stream_light: LightDefinition? by fromJson()

  public val ground_light: LightDefinition? by fromJson()

  public val target_position_deviation: Double? by fromJson()

  public val oriented_particle: Boolean? by fromJson()

  public val shadow_scale_enabled: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<FluidStreamPrototype>(FluidStreamPrototype::class)
}

/**
 * A turret that uses [fluid](prototype:FluidPrototype) as ammunition.
 */
@Serializable(FluidTurretPrototype.Serializer::class)
@SerialName("fluid-turret")
public open class FluidTurretPrototype : TurretPrototype() {
  public val fluid_buffer_size: Float by fromJson()

  public val fluid_buffer_input_flow: Float by fromJson()

  /**
   * Before an turret that was out of fluid ammunition is able to fire again, the
   * `fluid_buffer_size` must fill to this proportion.
   */
  public val activation_buffer_ratio: Float by fromJson()

  public val fluid_box: FluidBox by fromJson()

  public val muzzle_light: LightDefinition? by fromJson()

  public val enough_fuel_indicator_light: LightDefinition? by fromJson()

  public val not_enough_fuel_indicator_light: LightDefinition? by fromJson()

  public val muzzle_animation: Animation? by fromJson()

  public val folded_muzzle_animation_shift: AnimatedVector? by fromJson()

  public val preparing_muzzle_animation_shift: AnimatedVector? by fromJson()

  public val prepared_muzzle_animation_shift: AnimatedVector? by fromJson()

  public val starting_attack_muzzle_animation_shift: AnimatedVector? by fromJson()

  public val attacking_muzzle_animation_shift: AnimatedVector? by fromJson()

  public val ending_attack_muzzle_animation_shift: AnimatedVector? by fromJson()

  public val folding_muzzle_animation_shift: AnimatedVector? by fromJson()

  public val enough_fuel_indicator_picture: Sprite4Way? by fromJson()

  public val not_enough_fuel_indicator_picture: Sprite4Way? by fromJson()

  /**
   * The sprite will be drawn on top of fluid turrets that are out of fluid ammunition. If the
   * `out_of_ammo_alert_icon` is not set,
   * [UtilitySprites::fluid_icon](prototype:UtilitySprites::fluid_icon) will be used instead.
   */
  public val out_of_ammo_alert_icon: Sprite? by fromJson()

  /**
   * Requires ammo_type in attack_parameters.
   */
  override val attack_parameters: UnknownOverriddenType by fromJson()

  public object Serializer : JsonReaderSerializer<FluidTurretPrototype>(FluidTurretPrototype::class)
}

/**
 * A [fluid wagon](https://wiki.factorio.com/Fluid_wagon).
 */
@Serializable(FluidWagonPrototype.Serializer::class)
@SerialName("fluid-wagon")
public open class FluidWagonPrototype : RollingStockPrototype() {
  public val capacity: Double by fromJson()

  /**
   * Must be 1, 2 or 3.
   */
  public val tank_count: UByte? by fromJson()

  public object Serializer : JsonReaderSerializer<FluidWagonPrototype>(FluidWagonPrototype::class)
}

/**
 * Abstract base for construction/logistics and combat robots.
 */
public sealed class FlyingRobotPrototype : EntityWithOwnerPrototype() {
  /**
   * The flying speed of the robot, in tiles/tick.
   */
  public val speed: Double by fromJson()

  /**
   * The maximum flying speed of the robot, in tiles/tick. Useful to limit the impact of [worker
   * robot speed (research)](https://wiki.factorio.com/Worker_robot_speed_(research)).
   */
  public val max_speed: Double? by fromJson()

  /**
   * How much energy can be stored in the batteries.
   *
   * Used only by [robots with logistic interface](prototype:RobotWithLogisticInterfacePrototype).
   */
  public val max_energy: Energy? by fromJson()

  /**
   * How much energy does it cost to move 1 tile.
   *
   * Used only by [robots with logistic interface](prototype:RobotWithLogisticInterfacePrototype).
   */
  public val energy_per_move: Energy? by fromJson()

  /**
   * How much energy does it cost to fly for 1 tick.
   *
   * Used only by [robots with logistic interface](prototype:RobotWithLogisticInterfacePrototype).
   */
  public val energy_per_tick: Energy? by fromJson()

  /**
   * The robot will go to charge when its battery fill ratio is less than this.
   *
   * Used only by [robots with logistic interface](prototype:RobotWithLogisticInterfacePrototype).
   */
  public val min_to_charge: Float? by fromJson()

  /**
   * If the robot's battery fill ratio is more than this, it does not need to charge before
   * stationing.
   *
   * Used only by [robots with logistic interface](prototype:RobotWithLogisticInterfacePrototype).
   */
  public val max_to_charge: Float? by fromJson()

  /**
   * Some robots simply crash, some slowdown but keep going. 0 means crash.
   *
   * Used only by [robots with logistic interface](prototype:RobotWithLogisticInterfacePrototype).
   */
  public val speed_multiplier_when_out_of_energy: Float? by fromJson()

  /**
   * Whether this prototype should be a high priority target for enemy forces. See [Military units
   * and structures](https://wiki.factorio.com/Military_units_and_structures).
   */
  override val is_military_target: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()
}

@Serializable
public enum class FlyingTextPrototypeTextAlignment {
  left,
  center,
  right,
}

/**
 * An upwards flying text that disappears after a certain time (setting
 * [LuaEntity::active](runtime:LuaEntity::active) = false stops the flying and the disappearing.)
 */
@Serializable(FlyingTextPrototype.Serializer::class)
@SerialName("flying-text")
public open class FlyingTextPrototype : EntityPrototype() {
  /**
   * How fast the text flies up. Seems to be tiles/tick.
   */
  public val speed: Float by fromJson()

  /**
   * Time in ticks this flying-text lasts.
   */
  public val time_to_live: UInt by fromJson()

  public val text_alignment: FlyingTextPrototypeTextAlignment? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<FlyingTextPrototype>(FlyingTextPrototype::class)
}

/**
 * Fonts are used in all GUIs in the game.
 */
@Serializable(FontPrototype.Serializer::class)
@SerialName("font")
public open class FontPrototype : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the font.
   */
  public val name: String by fromJson()

  /**
   * Size of the font.
   */
  public val size: Int by fromJson()

  /**
   * The name of the fonts .ttf descriptor. This descriptor must be defined in the locale info.json.
   * Refer to `data/core/locale/_language_/info.json` for examples.
   */
  public val from: String by fromJson()

  public val spacing: Float? by fromJson()

  /**
   * Whether the font has a border.
   */
  public val border: Boolean? by fromJson()

  public val filtered: Boolean? by fromJson()

  /**
   * The color of the border, if enabled.
   */
  public val border_color: Color? by fromJson()

  public object Serializer : JsonReaderSerializer<FontPrototype>(FontPrototype::class)
}

/**
 * Each item which has a fuel_value must have a fuel category. The fuel categories are used to allow
 * only certain fuels to be used in [EnergySource](prototype:EnergySource).
 */
@Serializable(FuelCategory.Serializer::class)
@SerialName("fuel-category")
public open class FuelCategory : PrototypeBase() {
  public object Serializer : JsonReaderSerializer<FuelCategory>(FuelCategory::class)
}

/**
 * A furnace. Normal furnaces only process "smelting" category recipes, but you can make furnaces
 * that process other [recipe categories](prototype:RecipeCategory). The difference to assembling
 * machines is that furnaces automatically choose their recipe based on input.
 */
@Serializable(FurnacePrototype.Serializer::class)
@SerialName("furnace")
public open class FurnacePrototype : CraftingMachinePrototype() {
  /**
   * The number of output slots.
   */
  public val result_inventory_size: ItemStackIndex by fromJson()

  /**
   * The number of input slots, but not more than 1.
   */
  public val source_inventory_size: ItemStackIndex by fromJson()

  /**
   * Shift of the "alt-mode icon" relative to the machine's center.
   */
  override val entity_info_icon_shift: Vector? by fromJson()

  /**
   * The locale key of the message shown when the player attempts to insert an item into the furnace
   * that cannot be processed by that furnace. In-game, the locale is provided the `__1__` parameter,
   * which is the localised name of the item.
   */
  public val cant_insert_at_source_message_key: String? by fromJson()

  public object Serializer : JsonReaderSerializer<FurnacePrototype>(FurnacePrototype::class)
}

/**
 * A [gate](https://wiki.factorio.com/Gate).
 */
@Serializable(GatePrototype.Serializer::class)
@SerialName("gate")
public open class GatePrototype : EntityWithOwnerPrototype() {
  public val vertical_animation: Animation by fromJson()

  public val horizontal_animation: Animation by fromJson()

  public val vertical_rail_animation_left: Animation by fromJson()

  public val vertical_rail_animation_right: Animation by fromJson()

  public val horizontal_rail_animation_left: Animation by fromJson()

  public val horizontal_rail_animation_right: Animation by fromJson()

  public val vertical_rail_base: Animation by fromJson()

  public val horizontal_rail_base: Animation by fromJson()

  public val wall_patch: Animation by fromJson()

  public val opening_speed: Float by fromJson()

  public val activation_distance: Double by fromJson()

  public val timeout_to_close: UInt by fromJson()

  /**
   * Played when the gate opens.
   */
  override val open_sound: Sound by fromJson()

  /**
   * Played when the gate closes.
   */
  override val close_sound: Sound by fromJson()

  public val fadeout_interval: UInt? by fromJson()

  /**
   * This collision mask is used when the gate is open.
   */
  public val opened_collision_mask: CollisionMask? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<GatePrototype>(GatePrototype::class)
}

/**
 * Used by [portable fusion reactor](https://wiki.factorio.com/Portable_fusion_reactor). Provides
 * power in equipment grids. Can produce power for free or use a burner energy source.
 */
@Serializable(GeneratorEquipmentPrototype.Serializer::class)
@SerialName("generator-equipment")
public open class GeneratorEquipmentPrototype : EquipmentPrototype() {
  /**
   * The power output of this equipment.
   */
  public val power: Energy by fromJson()

  /**
   * If not defined, this equipment produces power for free.
   */
  public val burner: BurnerEnergySource? by fromJson()

  public object Serializer :
      JsonReaderSerializer<GeneratorEquipmentPrototype>(GeneratorEquipmentPrototype::class)
}

/**
 * An entity that produces power from fluids, for example a [steam
 * engine](https://wiki.factorio.com/Steam_engine).
 */
@Serializable(GeneratorPrototype.Serializer::class)
@SerialName("generator")
public open class GeneratorPrototype : EntityWithOwnerPrototype() {
  public val energy_source: ElectricEnergySource by fromJson()

  /**
   * This must have a filter if `max_power_output` is not defined.
   */
  public val fluid_box: FluidBox by fromJson()

  public val horizontal_animation: Animation? by fromJson()

  public val vertical_animation: Animation? by fromJson()

  /**
   * How much energy the generator produces compared to how much energy it consumes. For example, an
   * effectivity of 0.5 means that half of the consumed energy is output as power.
   */
  public val effectivity: Double? by fromJson()

  /**
   * The number of fluid units the generator uses per tick.
   */
  public val fluid_usage_per_tick: Double by fromJson()

  /**
   * The maximum temperature to which the efficiency can increase. At this temperature the generator
   * will run at 100% efficiency. Note: Higher temperature fluid can still be consumed.
   *
   * Used to calculate the `max_power_output` if it is not defined and `burns_fluid` is false. Then,
   * the max power output is `(min(fluid_max_temp, maximum_temperature) - fluid_default_temp) ×
   * fluid_usage_per_tick × fluid_heat_capacity × effectivity`, the fluid is the filter specified on
   * the `fluid_box`.
   */
  public val maximum_temperature: Double by fromJson()

  public val smoke: List<SmokeSource>? by fromJson()

  /**
   * If set to true, the available power output is based on the
   * [FluidPrototype::fuel_value](prototype:FluidPrototype::fuel_value). Otherwise, the available power
   * output will be based on the fluid temperature.
   */
  public val burns_fluid: Boolean? by fromJson()

  /**
   * Scales the generator's fluid usage to its maximum power output.
   *
   * Setting this to true prevents the generator from overconsuming fluid, for example when higher
   * than`maximum_temperature` fluid is fed to the generator.
   *
   * If scale_fluid_usage is false, the generator consumes the full `fluid_usage_per_tick` and any
   * of the extra energy in the fluid (in the form of higher temperature) is wasted. The [steam
   * engine](https://wiki.factorio.com/Steam_engine) exhibits this behavior when fed steam from [heat
   * exchangers](https://wiki.factorio.com/Heat_exchanger).
   */
  public val scale_fluid_usage: Boolean? by fromJson()

  /**
   * This property is used when `burns_fluid` is true and the fluid has a
   * [fuel_value](prototype:FluidPrototype::fuel_value) of 0.
   *
   * This property is also used when `burns_fluid` is false and the fluid is at default temperature.
   *
   * In these cases, this property determines whether the fluid should be destroyed, meaning that
   * the fluid is consumed at the rate of `fluid_usage_per_tick`, without producing any power.
   */
  public val destroy_non_fuel_fluid: Boolean? by fromJson()

  /**
   * Animation runs at least this fast. This corresponds to the sound.
   */
  public val min_perceived_performance: Double? by fromJson()

  public val performance_to_sound_speedup: Double? by fromJson()

  /**
   * The power production of the generator is capped to this value. This is also the value that is
   * shown as the maximum power output in the tooltip of the generator.
   *
   * `fluid_box` must have a filter if this is not defined.
   */
  public val max_power_output: Energy? by fromJson()

  public object Serializer : JsonReaderSerializer<GeneratorPrototype>(GeneratorPrototype::class)
}

/**
 * Properties of the god controller.
 */
@Serializable(GodControllerPrototype.Serializer::class)
@SerialName("god-controller")
public open class GodControllerPrototype : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the god-controller. Base game uses "default".
   */
  public val name: String by fromJson()

  public val inventory_size: ItemStackIndex by fromJson()

  /**
   * Must be >= 0.34375.
   */
  public val movement_speed: Double by fromJson()

  public val item_pickup_distance: Double by fromJson()

  public val loot_pickup_distance: Double by fromJson()

  public val mining_speed: Double by fromJson()

  /**
   * Names of the crafting categories the player can craft recipes from.
   */
  public val crafting_categories: List<RecipeCategoryID>? by fromJson()

  /**
   * Names of the resource categories the player can mine resources from.
   */
  public val mining_categories: List<ResourceCategoryID>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<GodControllerPrototype>(GodControllerPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player gets attacked due to
 * pollution.
 */
@Serializable(GroupAttackAchievementPrototype.Serializer::class)
@SerialName("group-attack-achievement")
public open class GroupAttackAchievementPrototype : AchievementPrototype() {
  /**
   * This will trigger the achievement, if the player receives this amount of attacks. **Note**: The
   * default achievement "it stinks and they don't like it" uses the amount of 1. (As in getting
   * attacked once.)
   */
  public val amount: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<GroupAttackAchievementPrototype>(GroupAttackAchievementPrototype::class)
}

/**
 * The available GUI styles.
 */
@Serializable(GuiStyle.Serializer::class)
@SerialName("gui-style")
public open class GuiStyle : PrototypeBase() {
  public val default_tileset: FileName? by fromJson()

  public val default_sprite_scale: Double? by fromJson()

  public val default_sprite_priority: SpritePriority? by fromJson()

  public object Serializer : JsonReaderSerializer<GuiStyle>(GuiStyle::class)
}

/**
 * A gun. A weapon to deal damage to entities.
 */
@Serializable(GunPrototype.Serializer::class)
@SerialName("gun")
public open class GunPrototype : ItemPrototype() {
  /**
   * The information the item needs to know in order to know what ammo it requires, the sounds, and
   * range.
   */
  public val attack_parameters: AttackParameters by fromJson()

  public object Serializer : JsonReaderSerializer<GunPrototype>(GunPrototype::class)
}

/**
 * This entity produces or consumes heat. Its heat settings can be changed runtime.
 */
@Serializable(HeatInterfacePrototype.Serializer::class)
@SerialName("heat-interface")
public open class HeatInterfacePrototype : EntityWithOwnerPrototype() {
  public val heat_buffer: HeatBuffer by fromJson()

  public val picture: Sprite? by fromJson()

  public val gui_mode: GuiMode? by fromJson()

  public object Serializer :
      JsonReaderSerializer<HeatInterfacePrototype>(HeatInterfacePrototype::class)
}

/**
 * A [heat pipe](https://wiki.factorio.com/Heat_pipe).
 */
@Serializable(HeatPipePrototype.Serializer::class)
@SerialName("heat-pipe")
public open class HeatPipePrototype : EntityWithOwnerPrototype() {
  public val connection_sprites: ConnectableEntityGraphics by fromJson()

  public val heat_glow_sprites: ConnectableEntityGraphics by fromJson()

  public val heat_buffer: HeatBuffer by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<HeatPipePrototype>(HeatPipePrototype::class)
}

/**
 * Used to attach graphics for [cursor boxes](prototype:CursorBoxType) to entities during runtime.
 * HighlightBoxEntity can also be independent from entities so it is simply drawn somewhere in the
 * world. See [LuaSurface::create_entity](runtime:LuaSurface::create_entity) for the available options
 * for type "highlight-box".
 *
 * The [collision_box](prototype:EntityPrototype::collision_box) of the highlight box prototype is
 * ignored during runtime, instead the "bounding_box" given in create_entity() or the selection box of
 * the target entity is used.
 */
@Serializable(HighlightBoxEntityPrototype.Serializer::class)
@SerialName("highlight-box")
public open class HighlightBoxEntityPrototype : EntityPrototype() {
  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<HighlightBoxEntityPrototype>(HighlightBoxEntityPrototype::class)
}

@Serializable
public enum class LogisticMode {
  `active-provider`,
  `passive-provider`,
  requester,
  storage,
  buffer,
}

/**
 * A generic container, such as a chest, that can spawn or void items and interact with the
 * logistics network.
 */
@Serializable(InfinityContainerPrototype.Serializer::class)
@SerialName("infinity-container")
public open class InfinityContainerPrototype : LogisticContainerPrototype() {
  public val erase_contents_when_mined: Boolean by fromJson()

  /**
   * Controls which players can control what the chest spawns.
   */
  public val gui_mode: GuiMode? by fromJson()

  /**
   * The way this chest interacts with the logistic network.
   */
  override val logistic_mode: LogisticMode? by fromJson()

  /**
   * Whether the "no network" icon should be rendered on this entity if the entity is not within a
   * logistics network.
   */
  override val render_not_in_network_icon: Boolean? by fromJson()

  /**
   * The number of slots in this container. May not be zero.
   */
  override val inventory_size: ItemStackIndex by fromJson()

  public object Serializer :
      JsonReaderSerializer<InfinityContainerPrototype>(InfinityContainerPrototype::class)
}

/**
 * This entity produces or consumes fluids. Its fluid settings can be changed runtime.
 */
@Serializable(InfinityPipePrototype.Serializer::class)
@SerialName("infinity-pipe")
public open class InfinityPipePrototype : PipePrototype() {
  public val gui_mode: GuiMode? by fromJson()

  public object Serializer :
      JsonReaderSerializer<InfinityPipePrototype>(InfinityPipePrototype::class)
}

/**
 * An [inserter](https://wiki.factorio.com/Inserter).
 */
@Serializable(InserterPrototype.Serializer::class)
@SerialName("inserter")
public open class InserterPrototype : EntityWithOwnerPrototype() {
  public val extension_speed: Double by fromJson()

  public val rotation_speed: Double by fromJson()

  public val insert_position: Vector by fromJson()

  public val pickup_position: Vector by fromJson()

  public val platform_picture: Sprite4Way by fromJson()

  public val hand_base_picture: Sprite by fromJson()

  public val hand_open_picture: Sprite by fromJson()

  public val hand_closed_picture: Sprite by fromJson()

  /**
   * Defines how this inserter gets energy. The emissions set on the energy source are ignored so
   * inserters cannot produce pollution.
   */
  public val energy_source: EnergySource by fromJson()

  public val energy_per_movement: Energy? by fromJson()

  public val energy_per_rotation: Energy? by fromJson()

  /**
   * Whether this inserter is considered a stack inserter. Relevant for determining how [inserter
   * capacity bonus (research)](https://wiki.factorio.com/Inserter_capacity_bonus_(research)) applies
   * to the inserter.
   */
  public val stack: Boolean? by fromJson()

  /**
   * Whether pickup and insert position can be set run-time.
   */
  public val allow_custom_vectors: Boolean? by fromJson()

  /**
   * Whether this burner inserter can fuel itself from the fuel inventory of the entity it is
   * picking up items from.
   */
  public val allow_burner_leech: Boolean? by fromJson()

  /**
   * Whether the item that the inserter is holding should be drawn.
   */
  public val draw_held_item: Boolean? by fromJson()

  /**
   * Whether the inserter should be able to fish [fish](https://wiki.factorio.com/Raw_fish).
   */
  public val use_easter_egg: Boolean? by fromJson()

  /**
   * How many filters this inserter has. Maximum count of filtered items in inserter is 5.
   */
  public val filter_count: UByte? by fromJson()

  public val hand_base_shadow: Sprite by fromJson()

  public val hand_open_shadow: Sprite by fromJson()

  public val hand_closed_shadow: Sprite by fromJson()

  /**
   * Used to determine how long the arm of the inserter is when drawing it. Does not affect
   * gameplay. The lower the value, the straighter the arm. Increasing the value will give the inserter
   * a bigger bend due to its longer parts.
   */
  public val hand_size: Double? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  public val default_stack_control_input_signal: SignalIDConnector? by fromJson()

  /**
   * Whether the yellow arrow that indicates the drop point of the inserter and the line that
   * indicates the pickup position should be drawn.
   */
  public val draw_inserter_arrow: Boolean? by fromJson()

  /**
   * Whether the inserter hand should move to the items it picks up from belts, leading to item
   * chasing behaviour. If this is off, the inserter hand will stay in the center of the belt and any
   * items picked up from the edges of the belt "teleport" to the inserter hand.
   */
  public val chases_belt_items: Boolean? by fromJson()

  /**
   * Stack size bonus that is inherent to the prototype without having to be researched.
   */
  public val stack_size_bonus: UByte? by fromJson()

  public val circuit_wire_connection_points: Tuple4<WireConnectionPoint>? by fromJson()

  public val circuit_connector_sprites: Tuple4<CircuitConnectorSprites>? by fromJson()

  public object Serializer : JsonReaderSerializer<InserterPrototype>(InserterPrototype::class)
}

/**
 * The entity used for items on the ground.
 */
@Serializable(ItemEntityPrototype.Serializer::class)
@SerialName("item-entity")
public open class ItemEntityPrototype : EntityPrototype() {
  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  /**
   * Item entity collision box has to have same width as height.
   *
   * Specification of the entity collision boundaries. Empty collision box means no collision and is
   * used for smoke, projectiles, particles, explosions etc.
   *
   * The `{0,0}` coordinate in the collision box will match the entity position. It should be near
   * the center of the collision box, to keep correct entity drawing order. The bounding box must
   * include the `{0,0}` coordinate.
   *
   * Note, that for buildings, it is customary to leave 0.1 wide border between the edge of the tile
   * and the edge of the building, this lets the player move between the building and electric
   * poles/inserters etc.
   */
  override val collision_box: BoundingBox? by fromJson()

  public object Serializer : JsonReaderSerializer<ItemEntityPrototype>(ItemEntityPrototype::class)
}

/**
 * An item group. Item groups are the tabs shown above the list of craftable items in the player's
 * inventory GUI. The built-in groups are "logistics", "production", "intermediate-products" and
 * "combat" but mods can define their own.
 *
 * Items are sorted into item groups by sorting them into a
 * [subgroup](prototype:ItemPrototype::subgroup) which then belongs to an [item
 * group](prototype:ItemSubGroup::group).
 */
@Serializable(ItemGroup.Serializer::class)
@SerialName("item-group")
public open class ItemGroup : PrototypeBase() {
  /**
   * The icon that is shown to represent this item group. Can't be an empty array.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * Path to the icon that is shown to represent this item group.
   *
   * Mandatory if `icons` is not defined.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Mandatory if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  /**
   * Item ingredients in recipes are ordered by item group. The `order_in_recipe` property can be
   * used to specify the ordering in recipes without affecting the inventory order.
   */
  public val order_in_recipe: Order? by fromJson()

  public object Serializer : JsonReaderSerializer<ItemGroup>(ItemGroup::class)
}

/**
 * Possible configuration for all items.
 */
@Serializable(ItemPrototype.Serializer::class)
@SerialName("item")
public open class ItemPrototype : PrototypeBase() {
  /**
   * Count of items of the same name that can be stored in one inventory slot. Must be 1 when the
   * `"not-stackable"` flag is set.
   */
  public open val stack_size: ItemCountType by fromJson()

  /**
   * Can't be an empty array.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * Path to the icon file.
   *
   * Mandatory if `icons` is not defined.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * This definition applies to all icon-type properties, both on here and on any children.
   *
   * Mandatory if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   *
   * This definition applies to all icon-type properties, both on here and on any children.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  /**
   * Inside IconData, the property for the file path is `dark_background_icon` instead of `icon`.
   * Can't be an empty array.
   *
   * Uses the basic `icon_size` and `icon_mipmaps` properties.
   */
  public val dark_background_icons: List<IconData>? by fromJson()

  /**
   * If this is set, it is used to show items in alt-mode instead of the normal item icon. This can
   * be useful to increase the contrast of the icon with the dark alt-mode [icon
   * outline](prototype:UtilityConstants::item_outline_color).
   *
   * Path to the icon file.
   *
   * Only loaded if `dark_background_icons` is not defined.
   *
   * Uses the basic `icon_size` and `icon_mipmaps` properties.
   */
  public val dark_background_icon: FileName? by fromJson()

  /**
   * Name of the [EntityPrototype](prototype:EntityPrototype) that can be built using this item. If
   * this item should be the one that construction bots use to build the specified `place_result`, set
   * the `"primary-place-result"` [item flag](prototype:ItemPrototypeFlags).
   *
   * The localised name of the entity will be used as the in-game item name. This behavior can be
   * overwritten by specifying `localised_name` on this item, it will be used instead.
   */
  public val place_result: EntityID? by fromJson()

  public val placed_as_equipment_result: EquipmentID? by fromJson()

  /**
   * The name of the [subgroup](prototype:ItemSubGroup) this item should be sorted into in item
   * selection GUIs like logistic requests.
   *
   * Empty text of subgroup is not allowed. (You can omit the definition to get the default
   * "other").
   */
  public val subgroup: ItemSubGroupID? by fromJson()

  /**
   * Must exist when a nonzero fuel_value is defined.
   */
  public val fuel_category: FuelCategoryID? by fromJson()

  /**
   * The item that is the result when this item gets burned as fuel.
   */
  public val burnt_result: ItemID? by fromJson()

  public val place_as_tile: PlaceAsTile? by fromJson()

  /**
   * Used to give the item multiple different icons so that they look less uniform on belts. For
   * inventory icons and similar, `icon/icons` will be used. Maximum number of variations is 16.
   *
   * The expected size for icons of items on belts is 16px. So when using sprites of size `64` (same
   * as base game icons), the `scale` should be set to 0.25.
   */
  public val pictures: SpriteVariations? by fromJson()

  /**
   * Specifies some properties of the item.
   */
  public val flags: ItemPrototypeFlags? by fromJson()

  public val default_request_amount: ItemCountType? by fromJson()

  /**
   * The number of items needed to connect 2 entities with this as wire. In the base game, [green
   * wire](https://wiki.factorio.com/Green_wire), [red wire](https://wiki.factorio.com/Red_wire) and
   * [copper cable](https://wiki.factorio.com/Copper_cable) have this set to 1.
   */
  public val wire_count: ItemCountType? by fromJson()

  /**
   * Amount of energy the item gives when used as fuel.
   *
   * Mandatory if `fuel_acceleration_multiplier`, `fuel_top_speed_multiplier` or
   * `fuel_emissions_multiplier` or `fuel_glow_color` are used.
   */
  public val fuel_value: Energy? by fromJson()

  public val fuel_acceleration_multiplier: Double? by fromJson()

  public val fuel_top_speed_multiplier: Double? by fromJson()

  public val fuel_emissions_multiplier: Double? by fromJson()

  /**
   * Colors the glow of the burner energy source when this fuel is burned. Can also be used to color
   * the glow of reactors burning the fuel, see
   * [ReactorPrototype::use_fuel_glow_color](prototype:ReactorPrototype::use_fuel_glow_color).
   */
  public val fuel_glow_color: Color? by fromJson()

  public val open_sound: Sound? by fromJson()

  public val close_sound: Sound? by fromJson()

  public val rocket_launch_products: List<ItemProductPrototype>? by fromJson()

  /**
   * Only loaded if `rocket_launch_products` is not defined.
   */
  public val rocket_launch_product: ItemProductPrototype? by fromJson()

  public object Serializer : JsonReaderSerializer<ItemPrototype>(ItemPrototype::class)
}

/**
 * Entity used to signify that an entity is requesting items, for example modules for an assembling
 * machine after it was blueprinted with modules inside.
 */
@Serializable(ItemRequestProxyPrototype.Serializer::class)
@SerialName("item-request-proxy")
public open class ItemRequestProxyPrototype : EntityPrototype() {
  public val picture: Sprite by fromJson()

  public val use_target_entity_alert_icon_shift: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ItemRequestProxyPrototype>(ItemRequestProxyPrototype::class)
}

/**
 * An item subgroup. Item subgroups are the rows in the recipe list in the player's inventory GUI.
 * The subgroup of a prototype also determines its item [group](prototype:ItemGroup::group) (tab in the
 * recipe list).
 *
 * The built-in subgroups can be found [here](https://wiki.factorio.com/Data.raw#item-subgroup). See
 * [ItemPrototype::subgroup](prototype:ItemPrototype::subgroup) for setting the subgroup of an item.
 */
@Serializable(ItemSubGroup.Serializer::class)
@SerialName("item-subgroup")
public open class ItemSubGroup : PrototypeBase() {
  /**
   * The item group this subgroup is located in.
   */
  public val group: ItemGroupID by fromJson()

  public object Serializer : JsonReaderSerializer<ItemSubGroup>(ItemSubGroup::class)
}

/**
 * ItemWithEntityData saves data associated with the entity that it represents, for example the
 * content of the equipment grid of a car.
 */
@Serializable(ItemWithEntityDataPrototype.Serializer::class)
@SerialName("item-with-entity-data")
public open class ItemWithEntityDataPrototype : ItemPrototype() {
  /**
   * Inside IconData, the property for the file path is `icon_tintable_mask` instead of `icon`.
   * Can't be an empty array.
   *
   * Only loaded if `icon_tintable` is defined.
   *
   * Uses `icon_size` and `icon_mipmaps` from its [ItemPrototype](prototype:ItemPrototype) parent.
   */
  public val icon_tintable_masks: List<IconData>? by fromJson()

  /**
   * Path to the icon file.
   *
   * Only loaded if `icon_tintable_masks` is not defined and `icon_tintable` is defined.
   *
   * Uses `icon_size` and `icon_mipmaps` from its [ItemPrototype](prototype:ItemPrototype) parent.
   */
  public val icon_tintable_mask: FileName? by fromJson()

  /**
   * Inside IconData, the property for the file path is `icon_tintable` instead of `icon`. Can't be
   * an empty array.
   *
   * Only loaded if `icon_tintable` is defined (`icon_tintables` takes precedence over
   * `icon_tintable`).
   *
   * Uses `icon_size` and `icon_mipmaps` from its [ItemPrototype](prototype:ItemPrototype) parent.
   */
  public val icon_tintables: List<IconData>? by fromJson()

  /**
   * Path to the icon file.
   *
   * Only loaded if `icon_tintables` is not defined.
   *
   * Uses `icon_size` and `icon_mipmaps` from its [ItemPrototype](prototype:ItemPrototype) parent.
   */
  public val icon_tintable: FileName? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ItemWithEntityDataPrototype>(ItemWithEntityDataPrototype::class)
}

@Serializable
public enum class ItemWithInventoryPrototypeInsertionPriorityMode {
  /**
   * Items are inserted into this item-with-inventory only if they match the whitelist defined in
   * the prototype for the item and whitelist is used.
   */
  default,
  /**
   * Items are never inserted into this item-with-inventory except explicitly by the player or
   * script.
   */
  never,
  /**
   * All items first try to be inserted into this item-with-inventory.
   */
  always,
  /**
   * When the inventory contains filters that match the item-to-be-inserted then try to put it into
   * this item before the inventory this item resides in.
   */
  `when-manually-filtered`,
}

/**
 * The inventory allows setting player defined filters similar to cargo wagon inventories.
 */
@Serializable(ItemWithInventoryPrototype.Serializer::class)
@SerialName("item-with-inventory")
public open class ItemWithInventoryPrototype : ItemWithLabelPrototype() {
  /**
   * The inventory size of the item.
   */
  public open val inventory_size: UnknownOverriddenType by fromJson()

  /**
   * A list of explicit item names to be used as filters.
   */
  public val item_filters: List<ItemID>? by fromJson()

  /**
   * A list of explicit item group names to be used as filters.
   */
  public val item_group_filters: List<ItemGroupID>? by fromJson()

  /**
   * A list of explicit [item subgroup](prototype:ItemSubGroup) names to be used as filters.
   */
  public val item_subgroup_filters: List<ItemSubGroupID>? by fromJson()

  /**
   * This determines how filters are applied. If no filters are defined this is automatically set to
   * "none".
   */
  public val filter_mode: EntityFilterMode? by fromJson()

  /**
   * The locale key used when the player attempts to put an item that doesn't match the filter rules
   * into the item-with-inventory.
   */
  public val filter_message_key: String? by fromJson()

  /**
   * When true, this item-with-inventory will extend the inventory it sits in by default. This is a
   * runtime property on the result item that can be changed through the Lua interface and only
   * determines the initial value.
   */
  public val extends_inventory_by_default: Boolean? by fromJson()

  /**
   * The insertion priority mode for this item. This determines if items are first attempted to be
   * put into this items inventory if the item extends the inventory it sits in when items are put into
   * the parent inventory.
   */
  public val insertion_priority_mode: ItemWithInventoryPrototypeInsertionPriorityMode? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ItemWithInventoryPrototype>(ItemWithInventoryPrototype::class)
}

/**
 * Like a normal item but with the ability to have a colored label.
 */
@Serializable(ItemWithLabelPrototype.Serializer::class)
@SerialName("item-with-label")
public open class ItemWithLabelPrototype : ItemPrototype() {
  /**
   * The default label color the item will use.
   */
  public val default_label_color: Color? by fromJson()

  /**
   * If the item will draw its label when held in the cursor in place of the item count.
   */
  public open val draw_label_for_cursor_render: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ItemWithLabelPrototype>(ItemWithLabelPrototype::class)
}

/**
 * Item type that can store any basic arbitrary Lua data, see
 * [LuaItemStack::tags](runtime:LuaItemStack::tags).
 */
@Serializable(ItemWithTagsPrototype.Serializer::class)
@SerialName("item-with-tags")
public open class ItemWithTagsPrototype : ItemWithLabelPrototype() {
  public object Serializer :
      JsonReaderSerializer<ItemWithTagsPrototype>(ItemWithTagsPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player destroys a certain amount of
 * an entity, with a specific damage type.
 */
@Serializable(KillAchievementPrototype.Serializer::class)
@SerialName("kill-achievement")
public open class KillAchievementPrototype : AchievementPrototype() {
  /**
   * This defines which entity needs to be destroyed in order to receive the achievement.
   */
  public val to_kill: EntityID? by fromJson()

  /**
   * This defines what entity type needs to be destroyed in order to receive the achievement.
   */
  public val type_to_kill: String? by fromJson()

  /**
   * This defines how the player needs to destroy the specific entity.
   */
  public val damage_type: DamageTypeID? by fromJson()

  /**
   * This is the amount of entity of the specified type the player needs to destroy to receive the
   * achievement.
   */
  public val amount: UInt? by fromJson()

  /**
   * This defines if the player needs to be in a vehicle.
   */
  public val in_vehicle: Boolean? by fromJson()

  /**
   * This defines to make sure you are the one driving, for instance, in a tank rather than an
   * automated train.
   */
  public val personally: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<KillAchievementPrototype>(KillAchievementPrototype::class)
}

/**
 * A [lab](https://wiki.factorio.com/Lab). It consumes [science packs](prototype:ToolPrototype) to
 * research [technologies](prototype:TechnologyPrototype).
 */
@Serializable(LabPrototype.Serializer::class)
@SerialName("lab")
public open class LabPrototype : EntityWithOwnerPrototype() {
  /**
   * The amount of energy this lab uses.
   */
  public val energy_usage: Energy by fromJson()

  /**
   * Defines how this lab gets energy.
   */
  public val energy_source: EnergySource by fromJson()

  /**
   * The animation that plays when the lab is active.
   */
  public val on_animation: Animation by fromJson()

  /**
   * The animation that plays when the lab is idle.
   */
  public val off_animation: Animation by fromJson()

  /**
   * A list of the names of science packs that can be used in this lab.
   *
   * If a technology requires other types of science packs, it cannot be researched in this lab.
   */
  public val inputs: List<ItemID> by fromJson()

  public val researching_speed: Double? by fromJson()

  /**
   * Sets the [modules](prototype:ModulePrototype) and [beacon](prototype:BeaconPrototype) effects
   * that are allowed to be used on this lab.
   */
  public val allowed_effects: EffectTypeLimitation? by fromJson()

  public val light: LightDefinition? by fromJson()

  /**
   * Productivity bonus that this machine always has.
   */
  public val base_productivity: Float? by fromJson()

  /**
   * Shift of the "alt-mode icon" relative to the lab's center.
   */
  public val entity_info_icon_shift: Vector? by fromJson()

  /**
   * The number of module slots.
   */
  public val module_specification: ModuleSpecification? by fromJson()

  public object Serializer : JsonReaderSerializer<LabPrototype>(LabPrototype::class)
}

@Serializable
public enum class LampPrototypeGlowRenderMode {
  additive,
  multiplicative,
}

/**
 * A [lamp](https://wiki.factorio.com/Lamp) to provide light, using energy.
 */
@Serializable(LampPrototype.Serializer::class)
@SerialName("lamp")
public open class LampPrototype : EntityWithOwnerPrototype() {
  /**
   * The lamps graphics when it's on.
   */
  public val picture_on: Sprite by fromJson()

  /**
   * The lamps graphics when it's off.
   */
  public val picture_off: Sprite by fromJson()

  /**
   * The amount of energy the lamp uses. Must be greater than > 0.
   */
  public val energy_usage_per_tick: Energy by fromJson()

  /**
   * The emissions set on the energy source are ignored so lamps cannot produce pollution.
   */
  public val energy_source: UnknownUnion by fromJson()

  /**
   * What color the lamp will be when it is on, and receiving power.
   */
  public val light: LightDefinition? by fromJson()

  /**
   * This refers to when the light is in a circuit network, and is lit a certain color based on a
   * signal value.
   */
  public val light_when_colored: LightDefinition? by fromJson()

  /**
   * Defines how wires visually connect to this lamp.
   */
  public val circuit_wire_connection_point: WireConnectionPoint? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * The pictures displayed for circuit connections to this lamp.
   */
  public val circuit_connector_sprites: CircuitConnectorSprites? by fromJson()

  public val glow_size: Float? by fromJson()

  public val glow_color_intensity: Float? by fromJson()

  /**
   * darkness_for_all_lamps_on must be > darkness_for_all_lamps_off. Values must be between 0 and 1.
   */
  public val darkness_for_all_lamps_on: Float? by fromJson()

  /**
   * darkness_for_all_lamps_on must be > darkness_for_all_lamps_off. Values must be between 0 and 1.
   */
  public val darkness_for_all_lamps_off: Float? by fromJson()

  /**
   * Whether the lamp should always be on.
   */
  public val always_on: Boolean? by fromJson()

  public val signal_to_color_mapping: List<SignalColorMapping>? by fromJson()

  public val glow_render_mode: LampPrototypeGlowRenderMode? by fromJson()

  public object Serializer : JsonReaderSerializer<LampPrototype>(LampPrototype::class)
}

/**
 * A [land mine](https://wiki.factorio.com/Land_mine).
 */
@Serializable(LandMinePrototype.Serializer::class)
@SerialName("land-mine")
public open class LandMinePrototype : EntityWithOwnerPrototype() {
  /**
   * The sprite of the landmine before it is armed (just after placing).
   */
  public val picture_safe: Sprite by fromJson()

  /**
   * The sprite of the landmine of a friendly force when it is armed.
   */
  public val picture_set: Sprite by fromJson()

  public val trigger_radius: Double by fromJson()

  /**
   * The sprite of the landmine of an enemy force when it is armed.
   */
  public val picture_set_enemy: Sprite? by fromJson()

  /**
   * Time between placing and the landmine being armed, in ticks.
   */
  public val timeout: UInt? by fromJson()

  public val action: Trigger? by fromJson()

  public val ammo_category: AmmoCategoryID? by fromJson()

  /**
   * Force the landmine to kill itself when exploding.
   */
  public val force_die_on_attack: Boolean? by fromJson()

  public val trigger_force: ForceCondition? by fromJson()

  /**
   * Collision mask that another entity must collide with to make this landmine blow up.
   */
  public val trigger_collision_mask: CollisionMask? by fromJson()

  /**
   * Whether this prototype should be a high priority target for enemy forces. See [Military units
   * and structures](https://wiki.factorio.com/Military_units_and_structures).
   */
  override val is_military_target: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<LandMinePrototype>(LandMinePrototype::class)
}

/**
 * Deprecated in 0.18. Particles that are exclusively meant for leaves of trees. The type
 * "leaf-particle" has been obsoleted and cannot be created. See
 * [ParticlePrototype](prototype:ParticlePrototype) for particles.
 */
@Serializable(LeafParticlePrototype.Serializer::class)
@SerialName("leaf-particle")
public open class LeafParticlePrototype : EntityParticlePrototype() {
  public object Serializer :
      JsonReaderSerializer<LeafParticlePrototype>(LeafParticlePrototype::class)
}

/**
 * A belt that can be connected to a belt anywhere else, including on a different surface. The
 * linked belts have to be [connected with console
 * commands](https://wiki.factorio.com/Console#Connect_linked_belts) or runtime scripting in mods or
 * scenarios. [LuaEntity::connect_linked_belts](runtime:LuaEntity::connect_linked_belts) and other
 * runtime functions.
 */
@Serializable(LinkedBeltPrototype.Serializer::class)
@SerialName("linked-belt")
public open class LinkedBeltPrototype : TransportBeltConnectablePrototype() {
  public val structure: LinkedBeltStructure by fromJson()

  public val structure_render_layer: RenderLayer? by fromJson()

  public val allow_clone_connection: Boolean? by fromJson()

  public val allow_blueprint_connection: Boolean? by fromJson()

  public val allow_side_loading: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<LinkedBeltPrototype>(LinkedBeltPrototype::class)
}

/**
 * A container that shares its inventory with containers with the same
 * [link_id](runtime:LuaEntity::link_id), which can be set via the GUI. The link IDs are per prototype
 * and force, so only containers with the **same ID**, **same prototype name** and **same force** will
 * share inventories.
 */
@Serializable(LinkedContainerPrototype.Serializer::class)
@SerialName("linked-container")
public open class LinkedContainerPrototype : EntityWithOwnerPrototype() {
  /**
   * Must be > 0.
   */
  public val inventory_size: ItemStackIndex by fromJson()

  public val picture: Sprite? by fromJson()

  /**
   * Whether the inventory of this container can be filtered (like cargo wagons) or not.
   */
  public val inventory_type: ContainerPrototypeInventoryType? by fromJson()

  /**
   * Players that can access the GUI to change the link ID.
   */
  public val gui_mode: GuiMode? by fromJson()

  public val scale_info_icons: Boolean? by fromJson()

  /**
   * Defines how wires visually connect to this linked container.
   */
  public val circuit_wire_connection_point: WireConnectionPoint? by fromJson()

  /**
   * The maximum circuit wire distance for this linked container.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * The pictures displayed for circuit connections to this linked container.
   */
  public val circuit_connector_sprites: CircuitConnectorSprites? by fromJson()

  public object Serializer :
      JsonReaderSerializer<LinkedContainerPrototype>(LinkedContainerPrototype::class)
}

/**
 * Continuously loads and unloads machines, as an alternative to inserters.
 *
 * This loader type is identical to [Loader1x2Prototype](prototype:Loader1x2Prototype) with the
 * exception of its hardcoded belt_distance. The belt_distance of the loader determines the distance
 * between the position of this loader and the tile of the loader's belt target.
 *
 * This loader type always has a belt_distance of 0, meaning by default it is 1 tile long in total.
 * For a loader type with a belt_distance of 0.5, see
 * [Loader1x2Prototype](prototype:Loader1x2Prototype).
 */
@Serializable(Loader1x1Prototype.Serializer::class)
@SerialName("loader-1x1")
public open class Loader1x1Prototype : LoaderPrototype() {
  public object Serializer : JsonReaderSerializer<Loader1x1Prototype>(Loader1x1Prototype::class)
}

/**
 * Continuously loads and unloads machines, as an alternative to inserters.
 *
 * This loader type is identical to [Loader1x1Prototype](prototype:Loader1x1Prototype) with the
 * exception of its hardcoded belt_distance. The belt_distance of the loader determines the distance
 * between the position of this loader and the tile of the loader's belt target.
 *
 * This loader type always has a belt_distance of 0.5, meaning by default it is 2 tiles long in
 * total. For a loader type with a belt_distance of 0, see
 * [Loader1x1Prototype](prototype:Loader1x1Prototype).
 */
@Serializable(Loader1x2Prototype.Serializer::class)
@SerialName("loader")
public open class Loader1x2Prototype : LoaderPrototype() {
  public object Serializer : JsonReaderSerializer<Loader1x2Prototype>(Loader1x2Prototype::class)
}

/**
 * Continuously loads and unloads machines, as an alternative to inserters.
 */
public sealed class LoaderPrototype : TransportBeltConnectablePrototype() {
  public val structure: LoaderStructure by fromJson()

  /**
   * How many item filters this loader has. Maximum count of filtered items in loader is 5.
   */
  public val filter_count: UByte by fromJson()

  public val structure_render_layer: RenderLayer? by fromJson()

  /**
   * The distance between the position of this loader and the tile of the loader's container target.
   */
  public val container_distance: Double? by fromJson()

  /**
   * Whether this loader can load and unload
   * [RollingStockPrototype](prototype:RollingStockPrototype).
   */
  public val allow_rail_interaction: Boolean? by fromJson()

  /**
   * Whether this loader can load and unload stationary inventories such as containers and crafting
   * machines.
   */
  public val allow_container_interaction: Boolean? by fromJson()

  /**
   * How long this loader's belt is. Should be the same as belt_distance, which is hardcoded to
   * `0.5` for [Loader1x2Prototype](prototype:Loader1x2Prototype) and to 0 for
   * [Loader1x1Prototype](prototype:Loader1x1Prototype). See the linked prototypes for an explanation
   * of belt_distance.
   */
  public val belt_length: Double? by fromJson()

  public val energy_source: UnknownUnion? by fromJson()

  /**
   * Energy in Joules. Can't be negative.
   */
  public val energy_per_item: Energy? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()
}

/**
 * A [locomotive](https://wiki.factorio.com/Locomotive).
 */
@Serializable(LocomotivePrototype.Serializer::class)
@SerialName("locomotive")
public open class LocomotivePrototype : RollingStockPrototype() {
  public val max_power: Energy by fromJson()

  public val reversing_power_modifier: Double by fromJson()

  /**
   * Must be a burner energy source when using "burner", otherwise it can also be a void energy
   * source.
   */
  public val energy_source: UnknownUnion by fromJson("burner")

  public val front_light: LightDefinition? by fromJson()

  public val front_light_pictures: RotatedSprite? by fromJson()

  public val darkness_to_render_light_animation: Float? by fromJson()

  /**
   * In tiles. A locomotive will snap to a nearby train stop when the player places it within this
   * distance to the stop.
   */
  public val max_snap_to_train_stop_distance: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<LocomotivePrototype>(LocomotivePrototype::class)
}

/**
 * A generic container, such as a chest, that interacts with the logistics network.
 */
@Serializable(LogisticContainerPrototype.Serializer::class)
@SerialName("logistic-container")
public open class LogisticContainerPrototype : ContainerPrototype() {
  /**
   * The way this chest interacts with the logistic network.
   */
  public open val logistic_mode: LogisticMode? by fromJson()

  /**
   * The number of request slots this logistics container has. Requester-type containers must have >
   * 0 slots and can have a maximum of 1000 slots. Storage-type containers must have <= 1 slot.
   */
  public val max_logistic_slots: UShort? by fromJson()

  /**
   * Whether the "no network" icon should be rendered on this entity if the entity is not within a
   * logistics network.
   */
  public open val render_not_in_network_icon: Boolean? by fromJson()

  public val opened_duration: UByte? by fromJson()

  /**
   * Drawn when a robot brings/takes items from this container.
   */
  public val animation: Animation? by fromJson()

  /**
   * The offset from the center of this container where a robot visually brings/takes items.
   */
  public val landing_location_offset: Vector? by fromJson()

  /**
   * Whether logistic robots have to deliver the exact amount of items requested to this logistic
   * container instead of over-delivering (within their cargo size).
   */
  public val use_exact_mode: Boolean? by fromJson()

  /**
   * Played when a robot brings/takes items from this container. Ignored if `animation` is not
   * defined.
   */
  public val animation_sound: Sound? by fromJson()

  /**
   * The picture displayed for this entity.
   */
  override val picture: Sprite? by fromJson()

  public object Serializer :
      JsonReaderSerializer<LogisticContainerPrototype>(LogisticContainerPrototype::class)
}

/**
 * A [logistic robot](https://wiki.factorio.com/Logistic_robot).
 */
@Serializable(LogisticRobotPrototype.Serializer::class)
@SerialName("logistic-robot")
public open class LogisticRobotPrototype : RobotWithLogisticInterfacePrototype() {
  /**
   * Only the first frame of the animation is drawn. This means that the graphics for the idle state
   * cannot be animated.
   */
  public val idle_with_cargo: RotatedAnimation? by fromJson()

  /**
   * Only the first frame of the animation is drawn. This means that the graphics for the in_motion
   * state cannot be animated.
   */
  public val in_motion_with_cargo: RotatedAnimation? by fromJson()

  /**
   * Only the first frame of the animation is drawn. This means that the graphics for the idle state
   * cannot be animated.
   */
  public val shadow_idle_with_cargo: RotatedAnimation? by fromJson()

  /**
   * Only the first frame of the animation is drawn. This means that the graphics for the in_motion
   * state cannot be animated.
   */
  public val shadow_in_motion_with_cargo: RotatedAnimation? by fromJson()

  /**
   * Must have a collision box size of zero.
   */
  override val collision_box: BoundingBox? by fromJson()

  public object Serializer :
      JsonReaderSerializer<LogisticRobotPrototype>(LogisticRobotPrototype::class)
}

/**
 * The available map gen presets.
 */
@Serializable(MapGenPresets.Serializer::class)
@SerialName("map-gen-presets")
public open class MapGenPresets : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the map gen presets. Base game uses "default".
   */
  public val name: String by fromJson()

  public object Serializer : JsonReaderSerializer<MapGenPresets>(MapGenPresets::class)
}

/**
 * The default map settings.
 */
@Serializable(MapSettings.Serializer::class)
@SerialName("map-settings")
public open class MapSettings : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the map-settings. Base game uses "map-settings".
   */
  public val name: String by fromJson()

  public val pollution: PollutionSettings by fromJson()

  public val steering: SteeringSettings by fromJson()

  public val enemy_evolution: EnemyEvolutionSettings by fromJson()

  public val enemy_expansion: EnemyExpansionSettings by fromJson()

  public val unit_group: UnitGroupSettings by fromJson()

  public val path_finder: PathFinderSettings by fromJson()

  /**
   * If a behavior fails this many times, the enemy (or enemy group) is destroyed. This solves
   * biters stuck within their own base.
   */
  public val max_failed_behavior_count: UInt by fromJson()

  public val difficulty_settings: DifficultySettings by fromJson()

  public object Serializer : JsonReaderSerializer<MapSettings>(MapSettings::class)
}

/**
 * Offers can be added to a market and they are shown when opening the entity. Offers allow to spend
 * items to get research bonuses or items.
 */
@Serializable(MarketPrototype.Serializer::class)
@SerialName("market")
public open class MarketPrototype : EntityWithOwnerPrototype() {
  public val picture: Sprite by fromJson()

  /**
   * Whether all forces are allowed to open this market.
   */
  public val allow_access_to_all_forces: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<MarketPrototype>(MarketPrototype::class)
}

/**
 * A mining drill for automatically extracting resources from [resource
 * entities](prototype:ResourceEntityPrototype). This prototype type is used by [burner mining
 * drill](https://wiki.factorio.com/Burner_mining_drill), [electric mining
 * drill](https://wiki.factorio.com/Electric_mining_drill) and
 * [pumpjack](https://wiki.factorio.com/Pumpjack) in vanilla.
 */
@Serializable(MiningDrillPrototype.Serializer::class)
@SerialName("mining-drill")
public open class MiningDrillPrototype : EntityWithOwnerPrototype() {
  /**
   * The position where any item results are placed, when the mining drill is facing north (default
   * direction). If the drill does not produce any solid items but uses a fluidbox output instead (e.g.
   * pumpjacks), a vector of `{0,0}` disables the yellow arrow alt-mode indicator for the placed item
   * location.
   */
  public val vector_to_place_result: Vector by fromJson()

  /**
   * The distance from the centre of the mining drill to search for resources in.
   *
   * This is 2.49 for electric mining drills (a 5x5 area) and 0.99 for burner mining drills (a 2x2
   * area). The drill searches resource outside its natural boundary box, which is 0.01 (the middle of
   * the entity); making it 2.5 and 1.0 gives it another block radius.
   */
  public val resource_searching_radius: Double by fromJson()

  /**
   * The amount of energy used by the drill while mining. Can't be less than or equal to 0.
   */
  public val energy_usage: Energy by fromJson()

  /**
   * The speed of this drill.
   */
  public val mining_speed: Double by fromJson()

  /**
   * The energy source of this mining drill.
   */
  public val energy_source: EnergySource by fromJson()

  /**
   * The names of the [ResourceCategory](prototype:ResourceCategory) that can be mined by this
   * drill. For a list of built-in categories, see
   * [here](https://wiki.factorio.com/Data.raw#resource-category).
   *
   * Note: Categories containing resources which produce items, fluids, or items+fluids may be
   * combined on the same entity, but may not work as expected. Examples: Miner does not rotate
   * fluid-resulting resources until depletion. Fluid isn't output (fluid resource change and fluidbox
   * matches previous fluid). Miner with no `vector_to_place_result` can't output an item result and
   * halts.
   */
  public val resource_categories: List<ResourceCategoryID> by fromJson()

  public val output_fluid_box: FluidBox? by fromJson()

  public val input_fluid_box: FluidBox? by fromJson()

  /**
   * Only loaded if `graphics_set` is not defined.
   */
  public val animations: Animation4Way? by fromJson()

  public val graphics_set: MiningDrillGraphicsSet? by fromJson()

  public val wet_mining_graphics_set: MiningDrillGraphicsSet? by fromJson()

  /**
   * Used by the [pumpjack](https://wiki.factorio.com/Pumpjack) to have a static 4 way sprite.
   */
  public val base_picture: Sprite4Way? by fromJson()

  /**
   * Sets the [modules](prototype:ModulePrototype) and [beacon](prototype:BeaconPrototype) effects
   * that are allowed to be used on this mining drill.
   */
  public val allowed_effects: EffectTypeLimitation? by fromJson()

  /**
   * The sprite used to show the range of the mining drill.
   */
  public val radius_visualisation_picture: Sprite? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  public val base_render_layer: RenderLayer? by fromJson()

  /**
   * Productivity bonus that this machine always has. Values below `0` are allowed, however the sum
   * of the resulting effect together with modules and research is limited to be at least 0%, see
   * [Effect](prototype:Effect).
   */
  public val base_productivity: Float? by fromJson()

  /**
   * When this mining drill is connected to the circuit network, the resource that it is reading
   * (either the entire resource patch, or the resource in the mining area of the drill, depending on
   * circuit network setting), is tinted in this color when mousing over the mining drill.
   */
  public val monitor_visualization_tint: Color? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance  > 0.
   */
  public val circuit_wire_connection_points: Tuple4<WireConnectionPoint>? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance  > 0.
   */
  public val circuit_connector_sprites: Tuple4<CircuitConnectorSprites>? by fromJson()

  public val module_specification: ModuleSpecification? by fromJson()

  public object Serializer : JsonReaderSerializer<MiningDrillPrototype>(MiningDrillPrototype::class)
}

/**
 * Exists only for migration, cannot be used by mods.
 */
@Serializable(MiningToolPrototype.Serializer::class)
@SerialName("mining-tool")
public open class MiningToolPrototype : ToolPrototype() {
  public object Serializer : JsonReaderSerializer<MiningToolPrototype>(MiningToolPrototype::class)
}

/**
 * A module category. The built-in categories can be found
 * [here](https://wiki.factorio.com/Data.raw#module-category). See
 * [ModulePrototype::category](prototype:ModulePrototype::category).
 */
@Serializable(ModuleCategory.Serializer::class)
@SerialName("module-category")
public open class ModuleCategory : PrototypeBase() {
  public object Serializer : JsonReaderSerializer<ModuleCategory>(ModuleCategory::class)
}

/**
 * A [module](https://wiki.factorio.com/Module). They are used to affect the capabilities of
 * existing machines, for example by increasing the crafting speed of a [crafting
 * machine](prototype:CraftingMachinePrototype).
 */
@Serializable(ModulePrototype.Serializer::class)
@SerialName("module")
public open class ModulePrototype : ItemPrototype() {
  /**
   * Used when upgrading modules: Ctrl + click modules into an entity and it will replace lower tier
   * modules of the same category with higher tier modules.
   */
  public val category: ModuleCategoryID by fromJson()

  /**
   * Tier of the module inside its category. Used when upgrading modules: Ctrl + click modules into
   * an entity and it will replace lower tier modules with higher tier modules if they have the same
   * category.
   */
  public val tier: UInt by fromJson()

  /**
   * The effect of the module on the machine it's inserted in, such as increased pollution.
   */
  public val effect: Effect by fromJson()

  public val requires_beacon_alt_mode: Boolean? by fromJson()

  /**
   * Array of [recipe names](prototype:RecipePrototype) this module can be used on. If empty, the
   * module can be used on all recipes.
   */
  public val limitation: List<RecipeID>? by fromJson()

  /**
   * Array of [recipe names](prototype:RecipePrototype) this module can **not** be used on,
   * implicitly allowing its use on all other recipes. This property has no effect if set to an empty
   * table.
   *
   * Note that the game converts this into a normal list of limitations internally, so reading
   * [LuaItemPrototype::limitations](runtime:LuaItemPrototype::limitations) at runtime will be the
   * product of both ways of defining limitations.
   */
  public val limitation_blacklist: List<RecipeID>? by fromJson()

  /**
   * The locale key of the message that is shown when the player attempts to use the module on a
   * recipe it can't be used on. The locale key will be prefixed with `item-limitation.` (the
   * "category" of the locale) by the game.
   */
  public val limitation_message_key: String? by fromJson()

  /**
   * Chooses with what art style the module is shown inside [beacons](prototype:BeaconPrototype).
   * See [BeaconModuleVisualizations::art_style](prototype:BeaconModuleVisualizations::art_style).
   * Vanilla uses `"vanilla"` here.
   */
  public val art_style: String? by fromJson()

  public val beacon_tint: BeaconVisualizationTints? by fromJson()

  public object Serializer : JsonReaderSerializer<ModulePrototype>(ModulePrototype::class)
}

@Serializable
public enum class MouseCursorSystemCursor {
  arrow,
  `i-beam`,
  crosshair,
  `wait-arrow`,
  `size-all`,
  no,
  hand,
}

/**
 * Used by [SelectionToolPrototype::mouse_cursor](prototype:SelectionToolPrototype::mouse_cursor).
 */
@Serializable(MouseCursor.Serializer::class)
@SerialName("mouse-cursor")
public open class MouseCursor : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the prototype.
   */
  public val name: String by fromJson()

  /**
   * Either this or the other three properties have to be present.
   */
  public val system_cursor: MouseCursorSystemCursor? by fromJson()

  /**
   * Mandatory if `system_cursor` is not defined.
   */
  public val filename: FileName? by fromJson()

  /**
   * Mandatory if `system_cursor` is not defined.
   */
  public val hot_pixel_x: Short? by fromJson()

  /**
   * Mandatory if `system_cursor` is not defined.
   */
  public val hot_pixel_y: Short? by fromJson()

  public object Serializer : JsonReaderSerializer<MouseCursor>(MouseCursor::class)
}

/**
 * Used by [exoskeleton](https://wiki.factorio.com/Exoskeleton). Increases max speed of characters
 * or acceleration of vehicles if they have this equipment in their grid.
 */
@Serializable(MovementBonusEquipmentPrototype.Serializer::class)
@SerialName("movement-bonus-equipment")
public open class MovementBonusEquipmentPrototype : EquipmentPrototype() {
  public val energy_consumption: Energy by fromJson()

  /**
   * Multiplier of the character speed/vehicle acceleration.
   */
  public val movement_bonus: Double by fromJson()

  public object Serializer :
      JsonReaderSerializer<MovementBonusEquipmentPrototype>(MovementBonusEquipmentPrototype::class)
}

/**
 * A [NoiseExpression](prototype:NoiseExpression) with a name. The base game uses named noise
 * expressions to specify functions for many map properties to be used in map generation; e.g. the
 * "elevation" expression is used to calculate elevation for every point on a map. For a list of the
 * built-in named noise expressions, see
 * [data.raw](https://wiki.factorio.com/Data.raw#noise-expression).
 *
 * Named noise expressions can be used by [MapGenSettings](prototype:MapGenSettings) and
 * [MapGenPreset](prototype:MapGenPreset) to override which named expression is used to calculate a
 * given property by having an entry in `property_expression_names`, e.g. `elevation =
 * "0.16-elevation"`.
 *
 * Alternate expressions can be made available in the map generator GUI by setting their
 * `intended_property` to the name of the property they should override.
 *
 * Named noise expressions can also be used by [noise variables](prototype:NoiseVariable), e.g.
 * `noise.var("my-named-expression")`.
 */
@Serializable(NamedNoiseExpression.Serializer::class)
@SerialName("noise-expression")
public open class NamedNoiseExpression : PrototypeBase() {
  /**
   * The noise expression itself. This is where most of the noise magic happens.
   */
  public val expression: NoiseExpression by fromJson()

  /**
   * Names the property that this expression is intended to provide a value for, if any. This will
   * make the expression show up as an option in the map generator GUI, unless it is the only
   * expression with that intended property, in which case it will be hidden and selected by default.
   *
   * Note that the "Map type" dropdown in the map generation GUI is actually a selector for
   * "elevation" generators. If generators are available for other properties, the "Map type" dropdown
   * in the GUI will be renamed to "elevation" and shown along with selectors for the other selectable
   * properties.
   *
   * For example if a noise expression is intended to be used as an alternative temperature
   * generator, `intended_property` should be "temperature". The base game uses the intended_properties
   * elevation, temperature, moisture and aux. For how the named noise expression with those
   * intended_properties are used in the base game see the notable named noise expression list on
   * [BaseNamedNoiseExpressions](prototype:BaseNamedNoiseExpressions). Mods may add any other
   * intended_property or modify the existing noise expressions to change/remove their intended
   * properties. Furthermore, mods may remove the use of those named noise expressions from the map
   * generation code or change what they affect.
   *
   * **intended_property in the base game:** The base game defines two named noise expressions that
   * have the `intended_property` "elevation" so that are selectable via the "Map type" dropdown (which
   * actually selects elevation generators)
   *
   * ```
   * local noise = require("noise")
   * data:extend{
   *   {
   *     type = "noise-expression",
   *     name = "elevation",
   *     intended_property = "elevation",
   *     expression = noise.var("0_17-lakes-elevation") -- "0_17-lakes-elevation" is another named
   * noise expression. Noise variables may reference named noise expressions.
   *   },
   *   {
   *     type = "noise-expression",
   *     name = "0_17-island",
   *     intended_property = "elevation",
   *     -- A large island surrounded by an endless ocean
   *     -- expression =  [...]
   *   }
   * }
   * ```
   *
   * **Mods can define any intended_property with any name**. This examples aims to show what this
   * is useful for.
   *
   * A [NoiseVariable](prototype:NoiseVariable) can reference a named noise expression, so by
   * defining the "test" named noise expression, `noise.var("test")` may be used in other [noise
   * expressions](prototype:NoiseExpression). Intended_property allows to override what the variable
   * references: With the example, if "more-test" is selected in the dropdown in the map generator GUI,
   * its `expression` (`noise.ridge(noise.var("y"), -10, 6`) will provide the value for the noise
   * variable "test" instead.
   *
   * For easy demonstration, that value is assigned to the "elevation" named noise expression, so
   * changing the "test" generator changes the `noise.var("test")` which in turn is used by the
   * "elevation" named noise expression. The "elevation" noise variable is used by water generation, so
   * changing the test generators is immediately visible in the map generation preview.
   *
   * Note that the player can select the "Island" elevation generator in the Elevation dropdown
   * (previously named Map type), which means the 0_17-island named noise expression is selected and
   * `noise.var("test")` isn't used anymore so changing the test generator no longer has an effect.
   *
   * ```
   * local noise = require("noise")
   * data:extend{
   *   {
   *     type = "noise-expression",
   *     name = "test",
   *     intended_property = "test",
   *     expression = noise.ridge(noise.var("x"), -80, 8),
   *   },
   *   {
   *     type = "noise-expression",
   *     name = "more-test",
   *     intended_property = "test", -- override the "test" noise variable when selected by the
   * player
   *     expression = noise.ridge(noise.var("y"), -10, 6),
   *   }
   * }
   * data.raw["noise-expression"]["elevation"].expression = noise.var("test") -- the noise variable
   * "test"
   * ```
   */
  public val intended_property: String? by fromJson()

  /**
   * Used to order alternative expressions in the map generator GUI. For a given property (e.g.
   * 'temperature'), the NamedNoiseExpression with that property's name as its `intended_property` with
   * the lowest order will be chosen as the default in the GUI.
   *
   * If no order is specified, it defaults to "2000" if the property name matches the expression
   * name (making it the 'technical default' generator for the property if none is specified in
   * MapGenSettings), or "3000" otherwise. A generator defined with an order less than "2000" but with
   * a unique name can thereby override the default generator used when creating a new map through the
   * GUI without automatically overriding the 'technical default' generator, which is probably used by
   * existing maps.
   */
  override val order: Order? by fromJson()

  public object Serializer : JsonReaderSerializer<NamedNoiseExpression>(NamedNoiseExpression::class)
}

/**
 * Used by [nightvision](https://wiki.factorio.com/Nightvision).
 */
@Serializable(NightVisionEquipmentPrototype.Serializer::class)
@SerialName("night-vision-equipment")
public open class NightVisionEquipmentPrototype : EquipmentPrototype() {
  public val energy_input: Energy by fromJson()

  public val color_lookup: DaytimeColorLookupTable by fromJson()

  /**
   * Must be >= 0 and <= 1.
   */
  public val darkness_to_turn_on: Float? by fromJson()

  public val activate_sound: Sound? by fromJson()

  public val deactivate_sound: Sound? by fromJson()

  public object Serializer :
      JsonReaderSerializer<NightVisionEquipmentPrototype>(NightVisionEquipmentPrototype::class)
}

/**
 * A noise layer used by the [autoplace system](prototype:AutoplacePeak::noise_layer). For a list of
 * built-in layers, see [here](https://wiki.factorio.com/Data.raw#noise-layer).
 */
@Serializable(NoiseLayer.Serializer::class)
@SerialName("noise-layer")
public open class NoiseLayer : PrototypeBase() {
  public object Serializer : JsonReaderSerializer<NoiseLayer>(NoiseLayer::class)
}

/**
 * An [offshore pump](https://wiki.factorio.com/Offshore_pump).
 */
@Serializable(OffshorePumpPrototype.Serializer::class)
@SerialName("offshore-pump")
public open class OffshorePumpPrototype : EntityWithOwnerPrototype() {
  public val fluid_box: FluidBox by fromJson()

  /**
   * How many units of fluid are produced per tick. Must be > 0.
   */
  public val pumping_speed: Float by fromJson()

  /**
   * The name of the fluid that is produced by the pump.
   */
  public val fluid: FluidID by fromJson()

  public val graphics_set: OffshorePumpGraphicsSet? by fromJson()

  /**
   * Mandatory if `graphics_set` is not defined.
   *
   * Deprecated, use `graphics_set` instead.
   */
  public val picture: Animation4Way? by fromJson()

  /**
   * Animation runs at least this fast.
   */
  public val min_perceived_performance: Float? by fromJson()

  public val fluid_box_tile_collision_test: CollisionMask? by fromJson()

  /**
   * Tiles colliding with `adjacent_tile_collision_box` must collide with this collision mask
   * (unless it's empty).
   */
  public val adjacent_tile_collision_test: CollisionMask? by fromJson()

  /**
   * Tiles colliding with `adjacent_tile_collision_box` must NOT collide with this collision mask.
   */
  public val adjacent_tile_collision_mask: CollisionMask? by fromJson()

  /**
   * Tile at placement position must NOT collide with this collision mask.
   */
  public val center_collision_mask: CollisionMask? by fromJson()

  public val adjacent_tile_collision_box: BoundingBox? by fromJson()

  public val placeable_position_visualization: Sprite? by fromJson()

  public val remove_on_tile_collision: Boolean? by fromJson()

  /**
   * If false, the offshore pump will not show fluid present (visually) before there is an output
   * connected. The pump will also animate yet not show fluid when the fluid is 100% extracted (e.g.
   * such as with a pump).
   */
  public val always_draw_fluid: Boolean? by fromJson()

  /**
   * If not set (=default), the offshore pump does not collide with tiles if it has none of these
   * collision masks: "water-tile", "ground-tile", "resource-layer", "player-layer", "item-layer",
   * "doodad-layer". If it has at least one of the six collision masks, it does collide with tiles.
   *
   * If set, this specifies whether collision with tiles should (true) or should not (false) be
   * performed on an offshore pump.
   */
  public val check_bounding_box_collides_with_tiles: Boolean? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance > 0.
   */
  public val circuit_wire_connection_points: Tuple4<WireConnectionPoint>? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance > 0.
   */
  public val circuit_connector_sprites: Tuple4<CircuitConnectorSprites>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<OffshorePumpPrototype>(OffshorePumpPrototype::class)
}

/**
 * An entity with a limited lifetime that can use trigger effects.
 */
@Serializable(ParticlePrototype.Serializer::class)
@SerialName("optimized-particle")
public open class ParticlePrototype : PrototypeBase() {
  /**
   * Picture variation count and individual frame count must be equal to shadow variation count.
   */
  public val pictures: AnimationVariations by fromJson()

  /**
   * Can't be 1.
   */
  public val life_time: UShort by fromJson()

  /**
   * Shadow variation variation count and individual frame count must be equal to picture variation
   * count.
   */
  public val shadows: AnimationVariations? by fromJson()

  public val draw_shadow_when_on_ground: Boolean? by fromJson()

  public val regular_trigger_effect: TriggerEffect? by fromJson()

  public val ended_in_water_trigger_effect: TriggerEffect? by fromJson()

  public val ended_on_ground_trigger_effect: TriggerEffect? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public val render_layer_when_on_ground: RenderLayer? by fromJson()

  /**
   * Can't be 1.
   */
  public val regular_trigger_effect_frequency: UInt? by fromJson()

  public val movement_modifier_when_on_ground: Float? by fromJson()

  public val movement_modifier: Float? by fromJson()

  /**
   * Has to be >= -0.01 and <= 0.01.
   */
  public val vertical_acceleration: Float? by fromJson()

  public val mining_particle_frame_speed: Float? by fromJson()

  /**
   * Defaults to `life_time` / 5, but at most 60. If this is 0, it is silently changed to 1.
   */
  public val fade_away_duration: UShort? by fromJson()

  public object Serializer : JsonReaderSerializer<ParticlePrototype>(ParticlePrototype::class)
}

/**
 * Creates particles.
 */
@Serializable(ParticleSourcePrototype.Serializer::class)
@SerialName("particle-source")
public open class ParticleSourcePrototype : EntityPrototype() {
  public val time_to_live: Float by fromJson()

  public val time_before_start: Float by fromJson()

  public val height: Float by fromJson()

  public val vertical_speed: Float by fromJson()

  public val horizontal_speed: Float by fromJson()

  /**
   * Mandatory if `smoke` is not defined.
   */
  public val particle: ParticleID? by fromJson()

  /**
   * Mandatory if `particle` is not defined.
   */
  public val smoke: List<SmokeSource>? by fromJson()

  public val time_to_live_deviation: Float? by fromJson()

  public val time_before_start_deviation: Float? by fromJson()

  public val height_deviation: Float? by fromJson()

  public val vertical_speed_deviation: Float? by fromJson()

  public val horizontal_speed_deviation: Float? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ParticleSourcePrototype>(ParticleSourcePrototype::class)
}

/**
 * An entity to transport fluids over a distance and between machines.
 */
@Serializable(PipePrototype.Serializer::class)
@SerialName("pipe")
public open class PipePrototype : EntityWithOwnerPrototype() {
  /**
   * The area of the entity where fluid/gas inputs, and outputs.
   */
  public val fluid_box: FluidBox by fromJson()

  public val horizontal_window_bounding_box: BoundingBox by fromJson()

  public val vertical_window_bounding_box: BoundingBox by fromJson()

  /**
   * All graphics for this pipe.
   */
  public val pictures: PipePictures by fromJson()

  public object Serializer : JsonReaderSerializer<PipePrototype>(PipePrototype::class)
}

/**
 * A [pipe to ground](https://wiki.factorio.com/Pipe_to_ground).
 */
@Serializable(PipeToGroundPrototype.Serializer::class)
@SerialName("pipe-to-ground")
public open class PipeToGroundPrototype : EntityWithOwnerPrototype() {
  public val fluid_box: FluidBox by fromJson()

  public val pictures: PipeToGroundPictures by fromJson()

  /**
   * Causes fluid icon to always be drawn, ignoring the usual pair requirement.
   */
  public val draw_fluid_icon_override: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<PipeToGroundPrototype>(PipeToGroundPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player receives damage.
 */
@Serializable(PlayerDamagedAchievementPrototype.Serializer::class)
@SerialName("player-damaged-achievement")
public open class PlayerDamagedAchievementPrototype : AchievementPrototype() {
  /**
   * This will trigger the achievement, if the amount of damage taken by the dealer, is more than
   * this.
   */
  public val minimum_damage: Float by fromJson()

  /**
   * This sets the achievement to only trigger, if you survive the minimum amount of damage. If you
   * don't need to survive, false.
   */
  public val should_survive: Boolean by fromJson()

  /**
   * This will trigger the achievement, if the player takes damage from this specific entity type.
   */
  public val type_of_dealer: String? by fromJson()

  public object Serializer :
      JsonReaderSerializer<PlayerDamagedAchievementPrototype>(PlayerDamagedAchievementPrototype::class)
}

/**
 * When a character dies, this entity will immediately respawn the character at the entities
 * location, so there is no respawn time. If there are multiple player ports in the world, the
 * character will respawn at the nearest player port to their death location.
 */
@Serializable(PlayerPortPrototype.Serializer::class)
@SerialName("player-port")
public open class PlayerPortPrototype : EntityWithOwnerPrototype() {
  public val animation: Animation by fromJson()

  /**
   * Whether this prototype should be a high priority target for enemy forces. See [Military units
   * and structures](https://wiki.factorio.com/Military_units_and_structures).
   */
  override val is_military_target: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<PlayerPortPrototype>(PlayerPortPrototype::class)
}

/**
 * A [power switch](https://wiki.factorio.com/Power_switch).
 */
@Serializable(PowerSwitchPrototype.Serializer::class)
@SerialName("power-switch")
public open class PowerSwitchPrototype : EntityWithOwnerPrototype() {
  public val power_on_animation: Animation by fromJson()

  public val overlay_start: Animation by fromJson()

  public val overlay_loop: Animation by fromJson()

  public val led_on: Sprite by fromJson()

  public val led_off: Sprite by fromJson()

  public val overlay_start_delay: UByte by fromJson()

  public val circuit_wire_connection_point: WireConnectionPoint by fromJson()

  public val left_wire_connection_point: WireConnectionPoint by fromJson()

  public val right_wire_connection_point: WireConnectionPoint by fromJson()

  public val wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<PowerSwitchPrototype>(PowerSwitchPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player produces more than the
 * specified amount of items.
 */
@Serializable(ProduceAchievementPrototype.Serializer::class)
@SerialName("produce-achievement")
public open class ProduceAchievementPrototype : AchievementPrototype() {
  /**
   * This will set the amount of items or fluids needed to craft, for the player to complete the
   * achievement.
   */
  public val amount: MaterialAmountType by fromJson()

  /**
   * If this is false, the player carries over their statistics from this achievement through all
   * their saves.
   */
  public val limited_to_one_game: Boolean by fromJson()

  /**
   * Mandatory if `fluid_product` is not defined.
   *
   * This will tell the achievement what item the player needs to craft, to get the achievement.
   */
  public val item_product: ItemID? by fromJson()

  /**
   * Mandatory if `item_product` is not defined.
   *
   * This will tell the achievement what fluid the player needs to craft, to get the achievement.
   */
  public val fluid_product: FluidID? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ProduceAchievementPrototype>(ProduceAchievementPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player crafts a specified item a
 * certain amount, in an hour.
 */
@Serializable(ProducePerHourAchievementPrototype.Serializer::class)
@SerialName("produce-per-hour-achievement")
public open class ProducePerHourAchievementPrototype : AchievementPrototype() {
  /**
   * This is how much the player has to craft in an hour, to receive the achievement.
   */
  public val amount: MaterialAmountType by fromJson()

  /**
   * Mandatory if `fluid_product` is not defined.
   *
   * This will tell the achievement what item the player needs to craft, to get the achievement.
   */
  public val item_product: ItemID? by fromJson()

  /**
   * Mandatory if `item_product` is not defined.
   *
   * This will tell the achievement what fluid the player needs to craft, to get the achievement.
   */
  public val fluid_product: FluidID? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ProducePerHourAchievementPrototype>(ProducePerHourAchievementPrototype::class)
}

/**
 * A [programmable speaker](https://wiki.factorio.com/Programmable_speaker).
 */
@Serializable(ProgrammableSpeakerPrototype.Serializer::class)
@SerialName("programmable-speaker")
public open class ProgrammableSpeakerPrototype : EntityWithOwnerPrototype() {
  public val energy_source: UnknownUnion by fromJson()

  public val energy_usage_per_tick: Energy by fromJson()

  public val sprite: Sprite by fromJson()

  public val maximum_polyphony: UInt by fromJson()

  public val instruments: List<ProgrammableSpeakerInstrument> by fromJson()

  public val audible_distance_modifier: Float? by fromJson()

  public val circuit_wire_connection_point: WireConnectionPoint? by fromJson()

  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  public val circuit_connector_sprites: CircuitConnectorSprites? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ProgrammableSpeakerPrototype>(ProgrammableSpeakerPrototype::class)
}

/**
 * Entity with limited lifetime that can hit other entities and has triggers when this happens.
 */
@Serializable(ProjectilePrototype.Serializer::class)
@SerialName("projectile")
public open class ProjectilePrototype : EntityPrototype() {
  /**
   * Must be != 0 if `turning_speed_increases_exponentially_with_projectile_speed` is true.
   */
  public val acceleration: Double by fromJson()

  public val animation: AnimationVariations? by fromJson()

  /**
   * Whether the animation of the projectile is rotated to match the direction of travel.
   */
  public val rotatable: Boolean? by fromJson()

  public val enable_drawing_with_mask: Boolean? by fromJson()

  /**
   * Setting this to true can be used to disable projectile homing behaviour.
   */
  public val direction_only: Boolean? by fromJson()

  /**
   * When true the entity is hit at the position on its collision box the projectile first collides
   * with. When false the entity is hit at its own position.
   */
  public val hit_at_collision_position: Boolean? by fromJson()

  public val force_condition: ForceCondition? by fromJson()

  /**
   * Whenever an entity is hit by the projectile, this number gets reduced by the health of the
   * entity. If the number is then below 0, the `final_action` is applied and the projectile destroyed.
   * Otherwise, the projectile simply continues to its destination.
   */
  public val piercing_damage: Float? by fromJson()

  /**
   * Must be greater than or equal to 0.
   */
  public val max_speed: Double? by fromJson()

  /**
   * Must be greater than or equal to 0.
   */
  public val turn_speed: Float? by fromJson()

  public val speed_modifier: Vector? by fromJson()

  public val height: Double? by fromJson()

  /**
   * Executed when the projectile hits something.
   */
  public val action: Trigger? by fromJson()

  /**
   * Executed when the projectile hits something, after `action` and only if the entity that was hit
   * was destroyed. The projectile is destroyed right after the final_action.
   */
  public val final_action: Trigger? by fromJson()

  public val light: LightDefinition? by fromJson()

  public val smoke: List<SmokeSource>? by fromJson()

  public val hit_collision_mask: CollisionMask? by fromJson()

  public val turning_speed_increases_exponentially_with_projectile_speed: Boolean? by fromJson()

  public val shadow: AnimationVariations? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<ProjectilePrototype>(ProjectilePrototype::class)
}

/**
 * The abstract base for prototypes. PrototypeBase defines the common features of prototypes, such
 * as localization and order.
 */
public sealed class PrototypeBase : JsonReader(), AnyPrototype {
  /**
   * Specifies the kind of prototype this is.
   *
   * For a list of all types used in vanilla, see [data.raw](https://wiki.factorio.com/Data.raw).
   */
  public val type: String by fromJson()

  /**
   * Unique textual identification of the prototype. May not contain a dot, nor exceed a length of
   * 200 characters.
   *
   * For a list of all names used in vanilla, see [data.raw](https://wiki.factorio.com/Data.raw).
   */
  public open val name: String by fromJson()

  /**
   * Used to order prototypes in inventory, recipes and GUIs. May not exceed a length of 200
   * characters.
   */
  public open val order: Order? by fromJson()

  /**
   * Overwrites the name set in the [locale file](https://wiki.factorio.com/Tutorial:Localisation).
   * Can be used to easily set a procedurally-generated name because the LocalisedString format allows
   * to insert parameters into the name directly from the Lua script.
   */
  public val localised_name: LocalisedString? by fromJson()

  /**
   * Overwrites the description set in the [locale
   * file](https://wiki.factorio.com/Tutorial:Localisation). The description is usually shown in the
   * tooltip of the prototype.
   */
  public val localised_description: LocalisedString? by fromJson()
}

/**
 * The pump is used to transfer fluids between tanks, fluid wagons and pipes.
 */
@Serializable(PumpPrototype.Serializer::class)
@SerialName("pump")
public open class PumpPrototype : EntityWithOwnerPrototype() {
  /**
   * The area of the entity where fluid inputs and outputs.
   */
  public val fluid_box: FluidBox by fromJson()

  /**
   * The type of energy the pump uses.
   */
  public val energy_source: EnergySource by fromJson()

  /**
   * The amount of energy the pump uses.
   */
  public val energy_usage: Energy by fromJson()

  /**
   * The amount of fluid this pump transfers per tick.
   */
  public val pumping_speed: Double by fromJson()

  /**
   * The animation for the pump.
   */
  public val animations: Animation4Way by fromJson()

  public val fluid_wagon_connector_speed: Double? by fromJson()

  public val fluid_wagon_connector_alignment_tolerance: Double? by fromJson()

  public val fluid_wagon_connector_frame_count: UByte? by fromJson()

  public val fluid_animation: Animation4Way? by fromJson()

  public val glass_pictures: Sprite4Way? by fromJson()

  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance  > 0.
   */
  public val circuit_wire_connection_points: Tuple4<WireConnectionPoint>? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance  > 0.
   */
  public val circuit_connector_sprites: Tuple4<CircuitConnectorSprites>? by fromJson()

  public val fluid_wagon_connector_graphics: FluidWagonConnectorGraphics? by fromJson()

  public object Serializer : JsonReaderSerializer<PumpPrototype>(PumpPrototype::class)
}

/**
 * A [radar](https://wiki.factorio.com/Radar).
 */
@Serializable(RadarPrototype.Serializer::class)
@SerialName("radar")
public open class RadarPrototype : EntityWithOwnerPrototype() {
  /**
   * The amount of energy this radar uses.
   */
  public val energy_usage: Energy by fromJson()

  /**
   * The amount of energy it takes to scan a sector. This value doesn't have any effect on nearby
   * scanning.
   */
  public val energy_per_sector: Energy by fromJson()

  /**
   * The amount of energy the radar has to consume for nearby scan to be performed. This value
   * doesn't have any effect on sector scanning.
   *
   * Performance warning: nearby scan causes re-charting of many chunks, which is expensive
   * operation. If you want to make a radar that updates map more in real time, you should keep its
   * range low. If you are making radar with high range, you should set this value such that nearby
   * scan is performed once a second or so. For example if you set `energy_usage` to 100kW, setting
   * `energy_per_nearby_scan` to 100kJ will cause nearby scan to happen once per second.
   */
  public val energy_per_nearby_scan: Energy by fromJson()

  /**
   * The energy source for this radar.
   */
  public val energy_source: EnergySource by fromJson()

  public val pictures: RotatedSprite by fromJson()

  /**
   * The radius of the area this radar can chart, in chunks.
   */
  public val max_distance_of_sector_revealed: UInt by fromJson()

  /**
   * The radius of the area constantly revealed by this radar, in chunks.
   */
  public val max_distance_of_nearby_sector_revealed: UInt by fromJson()

  public val radius_minimap_visualisation_color: Color? by fromJson()

  public val rotation_speed: Double? by fromJson()

  /**
   * Whether this prototype should be a high priority target for enemy forces. See [Military units
   * and structures](https://wiki.factorio.com/Military_units_and_structures).
   */
  override val is_military_target: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<RadarPrototype>(RadarPrototype::class)
}

/**
 * A [rail chain signal](https://wiki.factorio.com/Rail_chain_signal).
 */
@Serializable(RailChainSignalPrototype.Serializer::class)
@SerialName("rail-chain-signal")
public open class RailChainSignalPrototype : RailSignalBasePrototype() {
  /**
   * Array of 8 vectors.
   */
  public val selection_box_offsets: List<Vector> by fromJson()

  public val blue_light: LightDefinition? by fromJson()

  public val default_blue_output_signal: SignalIDConnector? by fromJson()

  public object Serializer :
      JsonReaderSerializer<RailChainSignalPrototype>(RailChainSignalPrototype::class)
}

/**
 * A [rail planner](https://wiki.factorio.com/Rail_planner).
 */
@Serializable(RailPlannerPrototype.Serializer::class)
@SerialName("rail-planner")
public open class RailPlannerPrototype : ItemPrototype() {
  /**
   * The name of an entity of the type "straight-rail". The first item to place of the rail must be
   * this rail planner.
   */
  public val straight_rail: EntityID by fromJson()

  /**
   * The name of an entity of the type "curved-rail". The first item to place of the rail must be
   * this rail planner.
   */
  public val curved_rail: EntityID by fromJson()

  public object Serializer : JsonReaderSerializer<RailPlannerPrototype>(RailPlannerPrototype::class)
}

/**
 * The abstract base of both rail prototypes.
 */
public sealed class RailPrototype : EntityWithOwnerPrototype() {
  public val pictures: RailPictureSet by fromJson()

  /**
   * Sound played when a character walks over this rail.
   */
  public val walking_sound: Sound? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  /**
   * All rail [collision_boxes](prototype:EntityPrototype::collision_box) are hardcoded and cannot
   * be modified.
   */
  override val collision_box: BoundingBox? by fromJson()

  /**
   * Furthermore, the rail [selection_boxes](prototype:EntityPrototype::selection_box) are
   * automatically calculated from the bounding boxes, so effectively also hardcoded.
   */
  override val selection_box: BoundingBox? by fromJson()
}

@Serializable
public enum class RailRemnantsPrototypeBendingType {
  straight,
  turn,
}

/**
 * Used for rail corpses.
 */
@Serializable(RailRemnantsPrototype.Serializer::class)
@SerialName("rail-remnants")
public open class RailRemnantsPrototype : CorpsePrototype() {
  public val bending_type: RailRemnantsPrototypeBendingType by fromJson()

  public val pictures: RailPictureSet by fromJson()

  /**
   * All rail remnant [collision_boxes](prototype:EntityPrototype::collision_box) are hardcoded and
   * cannot be modified.
   */
  override val collision_box: BoundingBox? by fromJson()

  /**
   * Furthermore, the rail remnant [selection_boxes](prototype:EntityPrototype::selection_box) are
   * automatically calculated from the bounding boxes, so effectively also hardcoded.
   */
  override val selection_box: BoundingBox? by fromJson()

  public object Serializer :
      JsonReaderSerializer<RailRemnantsPrototype>(RailRemnantsPrototype::class)
}

/**
 * The abstract base entity for both rail signals.
 */
public sealed class RailSignalBasePrototype : EntityWithOwnerPrototype() {
  public val animation: RotatedAnimation by fromJson()

  public val rail_piece: Animation? by fromJson()

  public val green_light: LightDefinition? by fromJson()

  public val orange_light: LightDefinition? by fromJson()

  public val red_light: LightDefinition? by fromJson()

  public val default_red_output_signal: SignalIDConnector? by fromJson()

  public val default_orange_output_signal: SignalIDConnector? by fromJson()

  public val default_green_output_signal: SignalIDConnector? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance > 0.
   */
  public val circuit_wire_connection_points: List<WireConnectionPoint>? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance > 0.
   */
  public val circuit_connector_sprites: List<CircuitConnectorSprites>? by fromJson()

  /**
   * Rail signals must collide with each other, this can be achieved by having the "rail-layer"
   * collision mask layer on all rail signals.
   *
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  /**
   * The [collision_box](prototype:EntityPrototype::collision_box) of rail signals is hardcoded to
   * `{{-0.2, -0.2}, {0.2, 0.2}}`.
   */
  override val collision_box: BoundingBox? by fromJson()

  /**
   * The "placeable-off-grid" flag will be ignored for rail signals.
   */
  override val flags: EntityPrototypeFlags? by fromJson()
}

/**
 * A [rail signal](https://wiki.factorio.com/Rail_signal).
 */
@Serializable(RailSignalPrototype.Serializer::class)
@SerialName("rail-signal")
public open class RailSignalPrototype : RailSignalBasePrototype() {
  public object Serializer : JsonReaderSerializer<RailSignalPrototype>(RailSignalPrototype::class)
}

/**
 * A [reactor](https://wiki.factorio.com/Reactor).
 */
@Serializable(ReactorPrototype.Serializer::class)
@SerialName("reactor")
public open class ReactorPrototype : EntityWithOwnerPrototype() {
  public val working_light_picture: Sprite by fromJson()

  /**
   * The energy output as heat.
   */
  public val heat_buffer: HeatBuffer by fromJson()

  /**
   * May not be a heat energy source.
   *
   * The input energy source, in vanilla it is a burner energy source.
   */
  public val energy_source: EnergySource by fromJson()

  /**
   * How much energy this reactor can consume (from the input energy source) and then output as
   * heat.
   */
  public val consumption: Energy by fromJson()

  /**
   * If defined, number of variations must be at least equal to count of
   * [connections](prototype:HeatBuffer::connections) defined in `heat_buffer`. Each variation
   * represents connected heat buffer connection of corresponding index.
   */
  public val connection_patches_connected: SpriteVariations? by fromJson()

  /**
   * If defined, number of variations must be at least equal to count of
   * [connections](prototype:HeatBuffer::connections) defined in `heat_buffer`. Each variation
   * represents unconnected heat buffer connection of corresponding index.
   */
  public val connection_patches_disconnected: SpriteVariations? by fromJson()

  /**
   * If defined, number of variations must be at least equal to count of
   * [connections](prototype:HeatBuffer::connections) defined in `heat_buffer`. When reactor is heated,
   * corresponding variations are drawn over `connection_patches_connected`.
   */
  public val heat_connection_patches_connected: SpriteVariations? by fromJson()

  /**
   * If defined, number of variations must be at least equal to count of
   * [connections](prototype:HeatBuffer::connections) defined in `heat_buffer`. When reactor is heated,
   * corresponding variations are drawn over `connection_patches_disconnected`.
   */
  public val heat_connection_patches_disconnected: SpriteVariations? by fromJson()

  public val lower_layer_picture: Sprite? by fromJson()

  public val heat_lower_layer_picture: Sprite? by fromJson()

  public val picture: Sprite? by fromJson()

  public val light: LightDefinition? by fromJson()

  /**
   * The action is triggered when the reactor dies (is destroyed) at over 90% of max temperature.
   */
  public val meltdown_action: Trigger? by fromJson()

  public val neighbour_bonus: Double? by fromJson()

  /**
   * When this is true, the reactor will stop consuming fuel/energy when the temperature has reached
   * the maximum.
   */
  public val scale_energy_usage: Boolean? by fromJson()

  /**
   * Whether the reactor should use [fuel_glow_color](prototype:ItemPrototype::fuel_glow_color) from
   * the fuel item prototype as light color and tint for `working_light_picture`. [Forum
   * post.](https://forums.factorio.com/71121)
   */
  public val use_fuel_glow_color: Boolean? by fromJson()

  /**
   * When `use_fuel_glow_color` is true, this is the color used as `working_light_picture` tint for
   * fuels that don't have glow color defined.
   */
  public val default_fuel_glow_color: Color? by fromJson()

  public object Serializer : JsonReaderSerializer<ReactorPrototype>(ReactorPrototype::class)
}

/**
 * A recipe category. The built-in categories can be found
 * [here](https://wiki.factorio.com/Data.raw#recipe-category). See
 * [RecipePrototype::category](prototype:RecipePrototype::category). Recipe categories can be used to
 * specify which [machine](prototype:CraftingMachinePrototype::crafting_categories) can craft which
 * [recipes](prototype:RecipePrototype).
 *
 * The recipe category with the name "crafting" cannot contain recipes with fluid ingredients or
 * products.
 */
@Serializable(RecipeCategory.Serializer::class)
@SerialName("recipe-category")
public open class RecipeCategory : PrototypeBase() {
  public object Serializer : JsonReaderSerializer<RecipeCategory>(RecipeCategory::class)
}

/**
 * A recipe. It can be a crafting recipe, a smelting recipe, or a custom type of recipe, see
 * [RecipeCategory](prototype:RecipeCategory).
 *
 * This prototype has two different formats that can be specified. If both `normal` and `expensive`
 * are not defined, the standard properties define this recipe. Otherwise, they are ignored, and the
 * `normal` and `expensive` properties are used exclusively to define this recipe.
 */
@Serializable(RecipePrototype.Serializer::class)
@SerialName("recipe")
public open class RecipePrototype : PrototypeBase() {
  /**
   * The [category](prototype:RecipeCategory) of this recipe. Controls which machines can craft this
   * recipe.
   *
   * The built-in categories can be found
   * [here](https://wiki.factorio.com/Data.raw#recipe-category). The base `"crafting"` category can not
   * contain recipes with fluid ingredients or products.
   */
  public val category: RecipeCategoryID? by fromJson()

  /**
   * The name of the [subgroup](prototype:ItemSubGroup) of this recipe. If not specified, it
   * defaults to the subgroup of the product if there is only one, or of the `main_product` if multiple
   * products exist.
   *
   * Mandatory if multiple products exist and no `main_product` is specified, or if there is no
   * product.
   */
  public val subgroup: ItemSubGroupID? by fromJson()

  /**
   * Used by
   * [CraftingMachinePrototype::working_visualisations](prototype:CraftingMachinePrototype::working_visualisations)
   * to tint certain layers with the recipe color.
   * [WorkingVisualisation::apply_recipe_tint](prototype:WorkingVisualisation::apply_recipe_tint)
   * determines which of the four colors is used for that layer, if any.
   */
  public val crafting_machine_tint: CraftingMachineTint? by fromJson()

  /**
   * Can't be an empty array.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * If given, this determines the recipe's icon. Otherwise, the icon of `main_product` or the
   * singular product is used.
   *
   * Mandatory if `icons` is not defined for a recipe with more than one product and no
   * `main_product`, or no product.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Only loaded if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  /**
   * Can be set to `false` if the `expensive` property is defined. This will disable this
   * difficulty, same as setting `enabled` to `false` would. If it's later enabled (by research, etc.),
   * it will use the data from `expensive`.
   *
   * If this property is not defined while `expensive` is, it will mirror its data.
   */
  public val normal: UnknownUnion? by fromJson()

  /**
   * Can be set to `false` if the `normal` property is defined. This will disable this difficulty,
   * same as setting `enabled` to `false` would. If it's later enabled (by research, etc.), it will use
   * the data from `normal`.
   *
   * If this property is not defined while `normal` is, it will mirror its data.
   */
  public val expensive: UnknownUnion? by fromJson()

  /**
   * A table containing ingredient names and counts. Can also contain information about fluid
   * temperature and catalyst amounts. The catalyst amounts are automatically calculated from the
   * recipe, or can be set manually in the IngredientPrototype (see
   * [here](https://factorio.com/blog/post/fff-256)).
   *
   * The maximum ingredient amount is 65 535. Can be set to an empty table to create a recipe that
   * needs no ingredients.
   *
   * Duplicate ingredients, e.g. two entries with the same name, are *not* allowed. In-game, the
   * item ingredients are ordered by
   * [ItemGroup::order_in_recipe](prototype:ItemGroup::order_in_recipe).
   *
   * Mandatory if neither `normal` nor `expensive` are defined.
   */
  public val ingredients: List<IngredientPrototype>? by fromJson()

  /**
   * A table containing result names and counts. Can also contain information about fluid
   * temperature and catalyst amounts. The catalyst amounts are automatically calculated from the
   * recipe, or can be set manually in the ProductPrototype (see
   * [here](https://factorio.com/blog/post/fff-256)).
   *
   * Can be set to an empty table to create a recipe that produces nothing. Duplicate results, e.g.
   * two entries with the same name, are allowed.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val results: List<ProductPrototype>? by fromJson()

  /**
   * The item created by this recipe. Must be the name of an [item](prototype:ItemPrototype), such
   * as `"iron-gear-wheel"`.
   *
   * Only loaded, and mandatory if neither `results`, `normal` nor `expensive` are defined.
   */
  public val result: ItemID? by fromJson()

  /**
   * The number of items created by this recipe.
   *
   * Only loaded if neither `results`, `normal` nor `expensive` are defined.
   */
  public val result_count: UShort? by fromJson()

  /**
   * For recipes with one or more products: Subgroup, localised_name and icon default to the values
   * of the singular/main product, but can be overwritten by the recipe. Setting the main_product to an
   * empty string (`""`) forces the title in the recipe tooltip to use the recipe's name (not that of
   * the product) and shows the products in the tooltip.
   *
   * If 1) there are multiple products and this property is nil, 2) this property is set to an empty
   * string (`""`), or 3) there are no products, the recipe will use the localised_name, icon, and
   * subgroup of the recipe. icon and subgroup become non-optional.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val main_product: String? by fromJson()

  /**
   * The amount of time it takes to make this recipe. Must be `> 0.001`. Equals the number of
   * seconds it takes to craft at crafting speed `1`.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val energy_required: Double? by fromJson()

  /**
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val emissions_multiplier: Double? by fromJson()

  /**
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val requester_paste_multiplier: UInt? by fromJson()

  /**
   * Used to determine how many extra items are put into an assembling machine before it's
   * considered "full enough". See [insertion
   * limits](https://wiki.factorio.com/Inserters#Insertion_limits).
   *
   * If set to `0`, it instead uses the following formula: `1.166 / (energy_required / the
   * assembler's crafting_speed)`, rounded up, and clamped to be between`2` and `100`. The numbers used
   * in this formula can be changed by the [UtilityConstants](prototype:UtilityConstants) properties
   * `dynamic_recipe_overload_factor`, `minimum_recipe_overload_multiplier`, and
   * `maximum_recipe_overload_multiplier`.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val overload_multiplier: UInt? by fromJson()

  /**
   * Whether the recipe is allowed to have the extra inserter overload bonus applied (4 * stack
   * inserter stack size).
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val allow_inserter_overload: Boolean? by fromJson()

  /**
   * This can be `false` to disable the recipe at the start of the game, or `true` to leave it
   * enabled.
   *
   * If a recipe is unlocked via technology, this should be set to `false`.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val enabled: Boolean? by fromJson()

  /**
   * Hides the recipe from crafting menus.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val hidden: Boolean? by fromJson()

  /**
   * Hides the recipe from item/fluid production statistics.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val hide_from_stats: Boolean? by fromJson()

  /**
   * Hides the recipe from the player's crafting screen. The recipe will still show up for selection
   * in machines.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val hide_from_player_crafting: Boolean? by fromJson()

  /**
   * Whether this recipe is allowed to be broken down for the recipe tooltip "Total raw"
   * calculations.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val allow_decomposition: Boolean? by fromJson()

  /**
   * Whether the recipe can be used as an intermediate recipe in hand-crafting.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val allow_as_intermediate: Boolean? by fromJson()

  /**
   * Whether the recipe is allowed to use intermediate recipes when hand-crafting.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val allow_intermediates: Boolean? by fromJson()

  /**
   * Whether the "Made in: <Machine>" part of the tool-tip should always be present, and not only
   * when the recipe can't be hand-crafted.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val always_show_made_in: Boolean? by fromJson()

  /**
   * Whether the recipe name should have the product amount in front of it. E.g. "2x Transport
   * belt".
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val show_amount_in_title: Boolean? by fromJson()

  /**
   * Whether the products are always shown in the recipe tooltip.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val always_show_products: Boolean? by fromJson()

  /**
   * Whether enabling this recipe unlocks its item products to show in selection lists (item
   * filters, logistic requests, etc.).
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val unlock_results: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<RecipePrototype>(RecipePrototype::class)
}

/**
 * A [repair pack](https://wiki.factorio.com/Repair_pack). Using the tool decreases durability to
 * restore entity health.
 */
@Serializable(RepairToolPrototype.Serializer::class)
@SerialName("repair-tool")
public open class RepairToolPrototype : ToolPrototype() {
  /**
   * Entity health repaired per used
   * [ToolPrototype::durability](prototype:ToolPrototype::durability). E.g. a repair tool with 5
   * durability and a repair speed of 2 will restore 10 health.
   *
   * This is then multiplied by the
   * [EntityWithHealthPrototype::repair_speed_modifier](prototype:EntityWithHealthPrototype::repair_speed_modifier)
   * of the entity.
   */
  public val speed: Float by fromJson()

  /**
   * This does nothing, it is never triggered.
   */
  public val repair_result: Trigger? by fromJson()

  public object Serializer : JsonReaderSerializer<RepairToolPrototype>(RepairToolPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player completes a specific
 * research.
 */
@Serializable(ResearchAchievementPrototype.Serializer::class)
@SerialName("research-achievement")
public open class ResearchAchievementPrototype : AchievementPrototype() {
  /**
   * Mandatory if `research_all` is not defined.
   *
   * Researching this technology will trigger the achievement.
   */
  public val technology: TechnologyID? by fromJson()

  /**
   * Mandatory if `technology` is not defined.
   *
   * This will only trigger if the player has learned every research in the game.
   */
  public val research_all: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ResearchAchievementPrototype>(ResearchAchievementPrototype::class)
}

/**
 * A resource category. The built-in categories can be found
 * [here](https://wiki.factorio.com/Data.raw#resource-category). See
 * [ResourceEntityPrototype::category](prototype:ResourceEntityPrototype::category).
 */
@Serializable(ResourceCategory.Serializer::class)
@SerialName("resource-category")
public open class ResourceCategory : PrototypeBase() {
  public object Serializer : JsonReaderSerializer<ResourceCategory>(ResourceCategory::class)
}

/**
 * A mineable/gatherable entity. Its [collision_mask](prototype:EntityPrototype::collision_mask)
 * must contain "resource-layer" if it should be minable with a
 * [MiningDrillPrototype](prototype:MiningDrillPrototype).
 */
@Serializable(ResourceEntityPrototype.Serializer::class)
@SerialName("resource")
public open class ResourceEntityPrototype : EntityPrototype() {
  /**
   * Entity's graphics, using a graphic sheet, with variation and depletion. At least one stage must
   * be defined.
   *
   * When using [AnimationVariations::sheet](prototype:AnimationVariations::sheet), `frame_count` is
   * the amount of frames per row in the spritesheet. `variation_count` is the amount of rows in the
   * spritesheet. Each row in the spritesheet is one stage of the animation.
   */
  public val stages: AnimationVariations by fromJson()

  /**
   * Number of stages the animation has.
   */
  public val stage_counts: List<UInt> by fromJson()

  /**
   * If the ore is infinitely minable, or if it will eventually run out of resource.
   */
  public val infinite: Boolean? by fromJson()

  /**
   * If the resource should be highlighted when holding a mining drill that can mine it (holding a
   * pumpjack highlights crude-oil in the base game).
   */
  public val highlight: Boolean? by fromJson()

  /**
   * Whether there should be a slight offset to graphics of the resource. Used to make patches a
   * little less uniform in appearance.
   */
  public val randomize_visual_position: Boolean? by fromJson()

  /**
   * Whether the resource should have a grid pattern on the map instead of a solid map color.
   */
  public val map_grid: Boolean? by fromJson()

  /**
   * Must be not 0 when `infinite = true`.
   */
  public val minimum: UInt? by fromJson()

  /**
   * Must be not 0 when `infinite = true`.
   */
  public val normal: UInt? by fromJson()

  /**
   * Every time an infinite-type resource "ticks" lower it's lowered by that amount. --
   * [Rseding91](https://forums.factorio.com/viewtopic.php?p=271115#p271115)
   */
  public val infinite_depletion_amount: UInt? by fromJson()

  /**
   * When hovering over this resource in the map view: How far to search for other resource patches
   * of this type to display as one (summing amount, white outline).
   */
  public val resource_patch_search_radius: UInt? by fromJson()

  /**
   * The category for the resource. Available categories in vanilla can be found
   * [here](https://wiki.factorio.com/Data.raw#resource-category).
   */
  public val category: ResourceCategoryID? by fromJson()

  /**
   * Sound played when the player walks over this resource.
   */
  public val walking_sound: Sound? by fromJson()

  /**
   * An effect that can be overlaid above the normal ore graphics. Used in the base game to make
   * [uranium ore](https://wiki.factorio.com/Uranium_ore) glow.
   */
  public val stages_effect: AnimationVariations? by fromJson()

  /**
   * How long it takes `stages_effect` to go from `min_effect_alpha` to `max_effect_alpha`.
   */
  public val effect_animation_period: Float? by fromJson()

  /**
   * How much `effect_animation_period` can deviate from its original value. Used to make the stages
   * effect alpha change look less uniform.
   */
  public val effect_animation_period_deviation: Float? by fromJson()

  /**
   * How much the surface darkness should affect the alpha of `stages_effect`.
   */
  public val effect_darkness_multiplier: Float? by fromJson()

  /**
   * Minimal alpha value of `stages_effect`.
   */
  public val min_effect_alpha: Float? by fromJson()

  /**
   * Maximal alpha value of `stages_effect`.
   */
  public val max_effect_alpha: Float? by fromJson()

  /**
   * Must be greater than or equal to `0`.
   */
  public val tree_removal_probability: Double? by fromJson()

  /**
   * Must be greater than or equal to `0`.
   */
  public val cliff_removal_probability: Double? by fromJson()

  /**
   * Must be positive when `tree_removal_probability` is set.
   */
  public val tree_removal_max_distance: Double? by fromJson()

  /**
   * Defaults to the resources map color if left unset and map color is set, otherwise defaults to
   * white if left unset.
   */
  public val mining_visualisation_tint: Color? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ResourceEntityPrototype>(ResourceEntityPrototype::class)
}

/**
 * Used by [personal roboport](https://wiki.factorio.com/Personal_roboport).
 */
@Serializable(RoboportEquipmentPrototype.Serializer::class)
@SerialName("roboport-equipment")
public open class RoboportEquipmentPrototype : EquipmentPrototype() {
  /**
   * The animation played at each charging point when a robot is charging there.
   */
  public val recharging_animation: Animation by fromJson()

  /**
   * Presumably states the height of the charging stations and thus an additive offset for the
   * charging_offsets.
   */
  public val spawn_and_station_height: Float by fromJson()

  /**
   * Presumably, the distance from the roboport at which robots will wait to charge.
   */
  public val charge_approach_distance: Float by fromJson()

  /**
   * Can't be negative.
   */
  public val construction_radius: Float by fromJson()

  public val charging_energy: Energy by fromJson()

  public val spawn_and_station_shadow_height_offset: Float? by fromJson()

  /**
   * Unused, as roboport equipment does not have a logistic radius that could be drawn.
   */
  public val draw_logistic_radius_visualization: Boolean? by fromJson()

  public val draw_construction_radius_visualization: Boolean? by fromJson()

  /**
   * The light emitted when charging a robot.
   */
  public val recharging_light: LightDefinition? by fromJson()

  /**
   * How many charging points this roboport has. If this is 0, the length of the charging_offsets
   * table is used to calculate the charging station count.
   */
  public val charging_station_count: UInt? by fromJson()

  public val charging_distance: Float? by fromJson()

  public val charging_station_shift: Vector? by fromJson()

  /**
   * Distance in tiles. This defines how far away a robot can be from the charging spot and still be
   * charged, however the bot is still required to reach a charging spot in the first place.
   */
  public val charging_threshold_distance: Float? by fromJson()

  public val robot_vertical_acceleration: Float? by fromJson()

  /**
   * The offset from the center of the roboport at which robots will enter and exit.
   */
  public val stationing_offset: Vector? by fromJson()

  /**
   * How many robots can exist in the network (cumulative).
   */
  public val robot_limit: ItemCountType? by fromJson()

  public val robots_shrink_when_entering_and_exiting: Boolean? by fromJson()

  /**
   * The offsets from the center of the roboport at which robots will charge. Only used if
   * `charging_station_count` is equal to 0.
   */
  public val charging_offsets: List<Vector>? by fromJson()

  /**
   * Minimum amount of energy that needs to available inside the roboport's buffer so that robots
   * can be spawned.
   */
  public val spawn_minimum: Energy? by fromJson()

  /**
   * Add this is if the roboport should be fueled directly instead of using power from the equipment
   * grid.
   */
  public val burner: BurnerEnergySource? by fromJson()

  /**
   * Mandatory if `burner` is defined.
   *
   * The size of the buffer of the burner energy source, so effectively the amount of power that the
   * energy source can produce per tick.
   */
  public val power: Energy? by fromJson()

  public object Serializer :
      JsonReaderSerializer<RoboportEquipmentPrototype>(RoboportEquipmentPrototype::class)
}

/**
 * A [roboport](https://wiki.factorio.com/Roboport).
 */
@Serializable(RoboportPrototype.Serializer::class)
@SerialName("roboport")
public open class RoboportPrototype : EntityWithOwnerPrototype() {
  /**
   * The roboport's energy source.
   */
  public val energy_source: UnknownUnion by fromJson()

  /**
   * The amount of energy the roboport uses when idle.
   */
  public val energy_usage: Energy by fromJson()

  /**
   * Minimum charge that the roboport has to have after a blackout (0 charge/buffered energy) to
   * begin working again. Additionally, freshly placed roboports will have their energy buffer filled
   * with `0.25 × recharge_minimum` energy.
   *
   * Must be larger than or equal to `energy_usage` otherwise during low power the roboport will
   * toggle on and off every tick.
   */
  public val recharge_minimum: Energy by fromJson()

  /**
   * The number of robot slots in the roboport.
   */
  public val robot_slots_count: ItemStackIndex by fromJson()

  /**
   * The number of repair pack slots in the roboport.
   */
  public val material_slots_count: ItemStackIndex by fromJson()

  public val base: Sprite by fromJson()

  public val base_patch: Sprite by fromJson()

  /**
   * The animation played when the roboport is idle.
   */
  public val base_animation: Animation by fromJson()

  public val door_animation_up: Animation by fromJson()

  public val door_animation_down: Animation by fromJson()

  public val request_to_open_door_timeout: UInt by fromJson()

  /**
   * The animation played at each charging point when a robot is charging there.
   */
  public val recharging_animation: Animation by fromJson()

  /**
   * Presumably states the height of the charging stations and thus an additive offset for the
   * charging_offsets.
   */
  public val spawn_and_station_height: Float by fromJson()

  /**
   * The distance (in tiles) from the roboport at which robots will wait to charge. Notably, if the
   * robot is already in range, then it will simply wait at its current position.
   */
  public val charge_approach_distance: Float by fromJson()

  /**
   * Can't be negative.
   */
  public val logistics_radius: Float by fromJson()

  /**
   * Can't be negative.
   */
  public val construction_radius: Float by fromJson()

  /**
   * The maximum power provided to each charging station.
   */
  public val charging_energy: Energy by fromJson()

  public val open_door_trigger_effect: TriggerEffect? by fromJson()

  public val close_door_trigger_effect: TriggerEffect? by fromJson()

  public val default_available_logistic_output_signal: SignalIDConnector? by fromJson()

  public val default_total_logistic_output_signal: SignalIDConnector? by fromJson()

  public val default_available_construction_output_signal: SignalIDConnector? by fromJson()

  public val default_total_construction_output_signal: SignalIDConnector? by fromJson()

  public val circuit_wire_connection_point: WireConnectionPoint? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  public val circuit_connector_sprites: CircuitConnectorSprites? by fromJson()

  public val spawn_and_station_shadow_height_offset: Float? by fromJson()

  public val draw_logistic_radius_visualization: Boolean? by fromJson()

  public val draw_construction_radius_visualization: Boolean? by fromJson()

  /**
   * The light emitted when charging a robot.
   */
  public val recharging_light: LightDefinition? by fromJson()

  /**
   * How many charging points this roboport has. If this is 0, the length of the charging_offsets
   * table is used to calculate the charging station count.
   */
  public val charging_station_count: UInt? by fromJson()

  public val charging_distance: Float? by fromJson()

  public val charging_station_shift: Vector? by fromJson()

  /**
   * Unused.
   */
  public val charging_threshold_distance: Float? by fromJson()

  public val robot_vertical_acceleration: Float? by fromJson()

  /**
   * The offset from the center of the roboport at which robots will enter and exit.
   */
  public val stationing_offset: Vector? by fromJson()

  /**
   * Unused.
   */
  public val robot_limit: ItemCountType? by fromJson()

  public val robots_shrink_when_entering_and_exiting: Boolean? by fromJson()

  /**
   * The offsets from the center of the roboport at which robots will charge. Only used if
   * `charging_station_count` is equal to 0.
   */
  public val charging_offsets: List<Vector>? by fromJson()

  /**
   * Must be >= `logistics_radius`.
   */
  public val logistics_connection_distance: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<RoboportPrototype>(RoboportPrototype::class)
}

/**
 * The common properties of logistic and construction robots represented by an abstract prototype.
 */
public sealed class RobotWithLogisticInterfacePrototype : FlyingRobotPrototype() {
  public val max_payload_size: ItemCountType by fromJson()

  public val cargo_centered: Vector by fromJson()

  /**
   * Only the first frame of the animation is drawn. This means that the graphics for the idle state
   * cannot be animated.
   */
  public val idle: RotatedAnimation? by fromJson()

  /**
   * Only the first frame of the animation is drawn. This means that the graphics for the in_motion
   * state cannot be animated.
   */
  public val in_motion: RotatedAnimation? by fromJson()

  /**
   * Only the first frame of the animation is drawn. This means that the graphics for the idle state
   * cannot be animated.
   */
  public val shadow_idle: RotatedAnimation? by fromJson()

  /**
   * Only the first frame of the animation is drawn. This means that the graphics for the in_motion
   * state cannot be animated.
   */
  public val shadow_in_motion: RotatedAnimation? by fromJson()

  /**
   * Applied when the robot expires (runs out of energy and
   * [FlyingRobotPrototype::speed_multiplier_when_out_of_energy](prototype:FlyingRobotPrototype::speed_multiplier_when_out_of_energy)
   * is 0).
   */
  public val destroy_action: Trigger? by fromJson()

  public val draw_cargo: Boolean? by fromJson()
}

/**
 * A [rocket silo](https://wiki.factorio.com/Rocket_silo).
 */
@Serializable(RocketSiloPrototype.Serializer::class)
@SerialName("rocket-silo")
public open class RocketSiloPrototype : AssemblingMachinePrototype() {
  /**
   * Additional energy used during the following parts of the [launch
   * sequence](runtime:defines.rocket_silo_status): doors_opening, rocket_rising, arms_advance,
   * engine_starting, arms_retract, doors_closing.
   */
  public val active_energy_usage: Energy by fromJson()

  /**
   * May be 0.
   *
   * Additional energy used during the night, that is when
   * [LuaSurface::darkness](runtime:LuaSurface::darkness) is larger than 0.3.
   */
  public val lamp_energy_usage: Energy by fromJson()

  /**
   * Name of a [RocketSiloRocketPrototype](prototype:RocketSiloRocketPrototype).
   */
  public val rocket_entity: EntityID by fromJson()

  public val arm_02_right_animation: Animation by fromJson()

  public val arm_01_back_animation: Animation by fromJson()

  public val arm_03_front_animation: Animation by fromJson()

  public val shadow_sprite: Sprite by fromJson()

  public val hole_sprite: Sprite by fromJson()

  public val hole_light_sprite: Sprite by fromJson()

  public val rocket_shadow_overlay_sprite: Sprite by fromJson()

  public val rocket_glow_overlay_sprite: Sprite by fromJson()

  public val door_back_sprite: Sprite by fromJson()

  public val door_front_sprite: Sprite by fromJson()

  public val base_day_sprite: Sprite by fromJson()

  public val base_front_sprite: Sprite by fromJson()

  /**
   * Drawn from the start of the
   * [lights_blinking_open](runtime:defines.rocket_silo_status.lights_blinking_open) state until the
   * end of the [lights_blinking_close](runtime:defines.rocket_silo_status.lights_blinking_close)
   * state.
   */
  public val red_lights_back_sprites: Sprite by fromJson()

  /**
   * Drawn from the start of the
   * [lights_blinking_open](runtime:defines.rocket_silo_status.lights_blinking_open) state until the
   * end of the [lights_blinking_close](runtime:defines.rocket_silo_status.lights_blinking_close)
   * state.
   */
  public val red_lights_front_sprites: Sprite by fromJson()

  public val hole_clipping_box: BoundingBox by fromJson()

  public val door_back_open_offset: Vector by fromJson()

  public val door_front_open_offset: Vector by fromJson()

  public val silo_fade_out_start_distance: Double by fromJson()

  public val silo_fade_out_end_distance: Double by fromJson()

  /**
   * How many times the `red_lights_back_sprites` and `red_lights_front_sprites` should blink during
   * [lights_blinking_open](runtime:defines.rocket_silo_status.lights_blinking_open) and
   * [lights_blinking_close](runtime:defines.rocket_silo_status.lights_blinking_close).
   *
   * Does not affect the duration of the launch sequence.
   */
  public val times_to_blink: UByte by fromJson()

  /**
   * The inverse of the duration in ticks of
   * [lights_blinking_open](runtime:defines.rocket_silo_status.lights_blinking_open) and
   * [lights_blinking_close](runtime:defines.rocket_silo_status.lights_blinking_close).
   */
  public val light_blinking_speed: Double by fromJson()

  /**
   * The inverse of the duration in ticks of
   * [doors_opening](runtime:defines.rocket_silo_status.doors_opening) and
   * [closing](runtime:defines.rocket_silo_status.doors_closing).
   */
  public val door_opening_speed: Double by fromJson()

  /**
   * The number of crafts that must complete to produce a rocket. This includes bonus crafts from
   * productivity. Recipe products are ignored.
   */
  public val rocket_parts_required: UInt by fromJson()

  public val satellite_animation: Animation? by fromJson()

  public val satellite_shadow_animation: Animation? by fromJson()

  /**
   * Drawn instead of `base_day_sprite` during the night, that is when
   * [LuaSurface::darkness](runtime:LuaSurface::darkness) is larger than 0.3.
   */
  public val base_night_sprite: Sprite? by fromJson()

  public val base_light: LightDefinition? by fromJson()

  public val base_engine_light: LightDefinition? by fromJson()

  /**
   * The time to wait in the [doors_opened](runtime:defines.rocket_silo_status.doors_opened) state
   * before switching to [rocket_rising](runtime:defines.rocket_silo_status.rocket_rising).
   */
  public val rocket_rising_delay: UByte? by fromJson()

  /**
   * The time to wait in the [launch_started](runtime:defines.rocket_silo_status.launch_started)
   * state before switching to [engine_starting](runtime:defines.rocket_silo_status.engine_starting).
   */
  public val launch_wait_time: UByte? by fromJson()

  /**
   * Applied when switching into the
   * [lights_blinking_open](runtime:defines.rocket_silo_status.lights_blinking_open) state.
   */
  public val alarm_trigger: TriggerEffect? by fromJson()

  /**
   * Applied when switching into the [arms_advance](runtime:defines.rocket_silo_status.arms_advance)
   * state.
   */
  public val clamps_on_trigger: TriggerEffect? by fromJson()

  /**
   * Applied when switching into the [arms_retract](runtime:defines.rocket_silo_status.arms_retract)
   * state.
   */
  public val clamps_off_trigger: TriggerEffect? by fromJson()

  /**
   * Applied when switching into the
   * [doors_opening](runtime:defines.rocket_silo_status.doors_opening) and
   * [doors_closing](runtime:defines.rocket_silo_status.doors_closing) states.
   */
  public val doors_trigger: TriggerEffect? by fromJson()

  /**
   * Applied when switching into the
   * [rocket_rising](runtime:defines.rocket_silo_status.rocket_rising) state.
   */
  public val raise_rocket_trigger: TriggerEffect? by fromJson()

  /**
   * Played when switching into the
   * [lights_blinking_open](runtime:defines.rocket_silo_status.lights_blinking_open) state.
   */
  public val alarm_sound: Sound? by fromJson()

  /**
   * Played when switching into the [arms_advance](runtime:defines.rocket_silo_status.arms_advance)
   * state.
   */
  public val clamps_on_sound: Sound? by fromJson()

  /**
   * Played when switching into the [arms_retract](runtime:defines.rocket_silo_status.arms_retract)
   * state.
   */
  public val clamps_off_sound: Sound? by fromJson()

  /**
   * Played when switching into the
   * [doors_opening](runtime:defines.rocket_silo_status.doors_opening) and
   * [doors_closing](runtime:defines.rocket_silo_status.doors_closing) states.
   */
  public val doors_sound: Sound? by fromJson()

  /**
   * Played when switching into the
   * [rocket_rising](runtime:defines.rocket_silo_status.rocket_rising) state.
   */
  public val raise_rocket_sound: Sound? by fromJson()

  /**
   * Played when switching into the
   * [engine_starting](runtime:defines.rocket_silo_status.engine_starting) state.
   */
  public val flying_sound: Sound? by fromJson()

  public val rocket_result_inventory_size: ItemStackIndex? by fromJson()

  public object Serializer : JsonReaderSerializer<RocketSiloPrototype>(RocketSiloPrototype::class)
}

/**
 * The rocket inside the rocket silo.
 */
@Serializable(RocketSiloRocketPrototype.Serializer::class)
@SerialName("rocket-silo-rocket")
public open class RocketSiloRocketPrototype : EntityPrototype() {
  public val rocket_sprite: Sprite by fromJson()

  public val rocket_shadow_sprite: Sprite by fromJson()

  public val rocket_glare_overlay_sprite: Sprite by fromJson()

  public val rocket_smoke_bottom1_animation: Animation by fromJson()

  public val rocket_smoke_bottom2_animation: Animation by fromJson()

  public val rocket_smoke_top1_animation: Animation by fromJson()

  public val rocket_smoke_top2_animation: Animation by fromJson()

  public val rocket_smoke_top3_animation: Animation by fromJson()

  public val rocket_flame_animation: Animation by fromJson()

  public val rocket_flame_left_animation: Animation by fromJson()

  public val rocket_flame_right_animation: Animation by fromJson()

  public val rocket_rise_offset: Vector by fromJson()

  public val rocket_flame_left_rotation: Float by fromJson()

  public val rocket_flame_right_rotation: Float by fromJson()

  public val rocket_render_layer_switch_distance: Double by fromJson()

  public val full_render_layer_switch_distance: Double by fromJson()

  public val rocket_launch_offset: Vector by fromJson()

  public val effects_fade_in_start_distance: Double by fromJson()

  public val effects_fade_in_end_distance: Double by fromJson()

  public val shadow_fade_out_start_ratio: Double by fromJson()

  public val shadow_fade_out_end_ratio: Double by fromJson()

  public val rocket_visible_distance_from_center: Float by fromJson()

  public val rising_speed: Double by fromJson()

  public val engine_starting_speed: Double by fromJson()

  public val flying_speed: Double by fromJson()

  public val flying_acceleration: Double by fromJson()

  public val inventory_size: ItemStackIndex by fromJson()

  public val shadow_slave_entity: EntityID? by fromJson()

  public val dying_explosion: EntityID? by fromJson()

  public val glow_light: LightDefinition? by fromJson()

  public val rocket_initial_offset: Vector? by fromJson()

  public val rocket_above_wires_slice_offset_from_center: Float? by fromJson()

  public val rocket_air_object_slice_offset_from_center: Float? by fromJson()

  public val flying_trigger: TriggerEffect? by fromJson()

  public object Serializer :
      JsonReaderSerializer<RocketSiloRocketPrototype>(RocketSiloRocketPrototype::class)
}

/**
 * The shadow of the rocket inside the rocket silo.
 */
@Serializable(RocketSiloRocketShadowPrototype.Serializer::class)
@SerialName("rocket-silo-rocket-shadow")
public open class RocketSiloRocketShadowPrototype : EntityPrototype() {
  public object Serializer :
      JsonReaderSerializer<RocketSiloRocketShadowPrototype>(RocketSiloRocketShadowPrototype::class)
}

/**
 * The abstract base of all rolling stock.
 */
public sealed class RollingStockPrototype : VehiclePrototype() {
  /**
   * Maximum speed of the rolling stock in tiles/tick.
   *
   * In-game, the max speed of a train is `min(all_rolling_stock_max_speeds) ×
   * average(all_fuel_modifiers_in_all_locomotives)`. This calculated train speed is then silently
   * capped to 7386.3km/h.
   */
  public val max_speed: Double by fromJson()

  public val air_resistance: Double by fromJson()

  /**
   * The length between this rolling stocks front and rear joints. Joints are the point where
   * connection_distance is measured from when rolling stock are connected to one another. Wheels
   * sprite are placed based on the joint position.
   *
   * Maximum joint distance is 15.
   *
   * Note: There needs to be border at least 0.2 between the [bounding
   * box](prototype:EntityPrototype::collision_box) edge and joint. This means that the collision_box
   * must be at least `{{-0,-0.2},{0,0.2}}`.
   */
  public val joint_distance: Double by fromJson()

  /**
   * The distance between the joint of this rolling stock and its connected rolling stocks joint.
   */
  public val connection_distance: Double by fromJson()

  public val pictures: RotatedSprite by fromJson()

  public val vertical_selection_shift: Double by fromJson()

  /**
   * Usually a sound to play when the rolling stock drives over a tie. The rolling stock is
   * considered to be driving over a tie every `tie_distance` tiles.
   */
  public val drive_over_tie_trigger: TriggerEffect? by fromJson()

  /**
   * In tiles. Used to determine how often `drive_over_tie_trigger` is triggered.
   */
  public val tie_distance: Double? by fromJson()

  public val back_light: LightDefinition? by fromJson()

  public val stand_by_light: LightDefinition? by fromJson()

  public val wheels: RotatedSprite? by fromJson()

  public val horizontal_doors: Animation? by fromJson()

  public val vertical_doors: Animation? by fromJson()

  public val color: Color? by fromJson()

  public val allow_manual_color: Boolean? by fromJson()

  public val allow_robot_dispatch_in_automatic_mode: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()
}

/**
 * Used in the base game as a base for the blueprint item and the deconstruction item.
 */
@Serializable(SelectionToolPrototype.Serializer::class)
@SerialName("selection-tool")
public open class SelectionToolPrototype : ItemWithLabelPrototype() {
  /**
   * A list of selection mode flags that define how the selection tool selects things in-game.
   */
  public open val selection_mode: SelectionModeFlags? by fromJson()

  /**
   * A list of selection mode flags that define how the selection tool alt-selects things in-game.
   */
  public open val alt_selection_mode: SelectionModeFlags? by fromJson()

  /**
   * If tiles should be included in the selection regardless of entities also being in the
   * selection. This is a visual only setting.
   */
  public open val always_include_tiles: Boolean? by fromJson()

  /**
   * The color of the rectangle used when standard selection is done in-game.
   */
  public val selection_color: Color by fromJson()

  /**
   * The color of the rectangle used when alt-selection is done in-game.
   */
  public val alt_selection_color: Color by fromJson()

  /**
   * The type of cursor box used to render selection of entities/tiles when standard selecting.
   */
  public val selection_cursor_box_type: CursorBoxType by fromJson()

  /**
   * The type of cursor box used to render selection of entities/tiles when alt selecting.
   */
  public val alt_selection_cursor_box_type: CursorBoxType by fromJson()

  public val reverse_selection_color: Color? by fromJson()

  public val alt_reverse_selection_color: Color? by fromJson()

  public val selection_count_button_color: Color? by fromJson()

  public val alt_selection_count_button_color: Color? by fromJson()

  public val reverse_selection_count_button_color: Color? by fromJson()

  public val alt_reverse_selection_count_button_color: Color? by fromJson()

  public val chart_selection_color: Color? by fromJson()

  public val chart_alt_selection_color: Color? by fromJson()

  public val chart_reverse_selection_color: Color? by fromJson()

  public val chart_alt_reverse_selection_color: Color? by fromJson()

  /**
   * A list of selection mode flags that define how the selection tool reverse-selects things
   * in-game.
   */
  public val reverse_selection_mode: SelectionModeFlags? by fromJson()

  /**
   * A list of selection mode flags that define how the selection tool alt-reverse-selects things
   * in-game (using SHIFT + Right mouse button).
   */
  public val alt_reverse_selection_mode: SelectionModeFlags? by fromJson()

  /**
   * The type of cursor box used to render selection of entities/tiles when reverse-selecting.
   */
  public val reverse_selection_cursor_box_type: CursorBoxType? by fromJson()

  /**
   * The type of cursor box used to render selection of entities/tiles when alt-reverse-selecting
   * (using SHIFT + Right mouse button
   */
  public val alt_reverse_selection_cursor_box_type: CursorBoxType? by fromJson()

  public val mouse_cursor: MouseCursorID? by fromJson()

  public open val entity_filters: List<EntityID>? by fromJson()

  public open val alt_entity_filters: List<EntityID>? by fromJson()

  public open val entity_type_filters: List<String>? by fromJson()

  public open val alt_entity_type_filters: List<String>? by fromJson()

  public open val tile_filters: List<TileID>? by fromJson()

  public open val alt_tile_filters: List<TileID>? by fromJson()

  public open val entity_filter_mode: EntityFilterMode? by fromJson()

  public open val alt_entity_filter_mode: EntityFilterMode? by fromJson()

  public open val tile_filter_mode: EntityFilterMode? by fromJson()

  public open val alt_tile_filter_mode: EntityFilterMode? by fromJson()

  public val reverse_entity_filters: List<EntityID>? by fromJson()

  public val alt_reverse_entity_filters: List<EntityID>? by fromJson()

  public val reverse_entity_type_filters: List<String>? by fromJson()

  public val alt_reverse_entity_type_filters: List<String>? by fromJson()

  public val reverse_tile_filters: List<TileID>? by fromJson()

  public val alt_reverse_tile_filters: List<TileID>? by fromJson()

  public val reverse_entity_filter_mode: EntityFilterMode? by fromJson()

  public val alt_reverse_entity_filter_mode: EntityFilterMode? by fromJson()

  public val reverse_tile_filter_mode: EntityFilterMode? by fromJson()

  public val alt_reverse_tile_filter_mode: EntityFilterMode? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SelectionToolPrototype>(SelectionToolPrototype::class)
}

@Serializable
public enum class ShortcutPrototypeAction {
  `toggle-alt-mode`,
  undo,
  copy,
  cut,
  paste,
  `import-string`,
  `toggle-personal-roboport`,
  `toggle-equipment-movement-bonus`,
  `spawn-item`,
  lua,
}

@Serializable
public enum class ShortcutPrototypeStyle {
  default,
  blue,
  red,
  green,
}

/**
 * Definition for a shortcut button in the [shortcut bar](https://wiki.factorio.com/Shortcut_bar).
 *
 * This is **not** a custom keybinding (keyboard shortcut), for that see
 * [CustomInputPrototype](prototype:CustomInputPrototype).
 */
@Serializable(ShortcutPrototype.Serializer::class)
@SerialName("shortcut")
public open class ShortcutPrototype : PrototypeBase() {
  /**
   * If this is `"lua"`, [on_lua_shortcut](runtime:on_lua_shortcut) is raised when the shortcut is
   * clicked.
   */
  public val action: ShortcutPrototypeAction by fromJson()

  /**
   * Scales to fit a 16x16-pixel square.
   *
   * Note: The scale that can be defined in the sprite may not behave as expected because the game
   * always scales the sprite to fill the GUI slot.
   */
  public val icon: Sprite by fromJson()

  /**
   * The item to create when clicking on a shortcut with the action set to `"spawn-item"`. The item
   * must have the [spawnable](prototype:ItemPrototypeFlags::spawnable) flag set.
   */
  public val item_to_spawn: ItemID? by fromJson()

  /**
   * The technology that must be researched before this shortcut can be used. Once a shortcut is
   * unlocked in one save file, it is unlocked for all future save files.
   */
  public val technology_to_unlock: TechnologyID? by fromJson()

  /**
   * Must be enabled for the Factorio API to be able to set the toggled state on the shortcut
   * button, see [LuaPlayer::set_shortcut_toggled](runtime:LuaPlayer::set_shortcut_toggled).
   */
  public val toggleable: Boolean? by fromJson()

  /**
   * Name of a custom input or vanilla control. This is **only** used to show the keybind in the
   * tooltip of the shortcut.
   */
  public val associated_control_input: String? by fromJson()

  /**
   * The icon used in the panel for visible shortcuts, when the shortcut is usable.
   *
   * Note: The scale that can be defined in the sprite may not behave as expected because the game
   * always scales the sprite to fill the GUI slot.
   */
  public val small_icon: Sprite? by fromJson()

  /**
   * The icon used when the shortcut is shown in the quickbar, and is not usable.
   *
   * Note: The scale that can be defined in the sprite may not behave as expected because the game
   * always scales the sprite to fill the GUI slot.
   */
  public val disabled_icon: Sprite? by fromJson()

  /**
   * The icon used in the panel for visible shortcuts, when the shortcut is not usable.
   *
   * Note: The scale that can be defined in the sprite may not behave as expected because the game
   * always scales the sprite to fill the GUI slot.
   */
  public val disabled_small_icon: Sprite? by fromJson()

  public val style: ShortcutPrototypeStyle? by fromJson()

  /**
   * Used to order the shortcuts in the [quick panel](https://wiki.factorio.com/Quick_panel), which
   * replaces the shortcut bar when using a controller (game pad). It [is
   * recommended](https://forums.factorio.com/106661) to order modded shortcuts after the vanilla
   * shortcuts.
   */
  override val order: Order? by fromJson()

  public object Serializer : JsonReaderSerializer<ShortcutPrototype>(ShortcutPrototype::class)
}

/**
 * An extremely basic entity with no special functionality. Used for minable rocks. Cannot be
 * rotated.
 */
@Serializable(SimpleEntityPrototype.Serializer::class)
@SerialName("simple-entity")
public open class SimpleEntityPrototype : EntityWithHealthPrototype() {
  /**
   * Whether this entity should be treated as a rock for the purpose of deconstruction and for
   * [CarPrototype::immune_to_rock_impacts](prototype:CarPrototype::immune_to_rock_impacts).
   */
  public val count_as_rock_for_filtered_deconstruction: Boolean? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  /**
   * Used to determine render order for entities with the same `render_layer` in the same position.
   * Entities with a higher `secondary_draw_order` are drawn on top.
   */
  public val secondary_draw_order: Byte? by fromJson()

  public val random_animation_offset: Boolean? by fromJson()

  /**
   * Whether a random graphics variation is chosen when placing the entity/creating it via
   * script/creating it via map generation. If this is `false`, the entity will use the first variation
   * instead of a random one.
   */
  public val random_variation_on_create: Boolean? by fromJson()

  /**
   * Takes priority over `picture` and `animations`.
   */
  public val pictures: SpriteVariations? by fromJson()

  /**
   * Takes priority over `animations`. Only the `north` sprite is used because this entity cannot be
   * rotated.
   */
  public val picture: Sprite4Way? by fromJson()

  public val animations: AnimationVariations? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SimpleEntityPrototype>(SimpleEntityPrototype::class)
}

/**
 * By default, this entity will be a priority target for units/turrets, who will choose to attack it
 * even if it does not block their path. Setting
 * [EntityWithOwnerPrototype::is_military_target](prototype:EntityWithOwnerPrototype::is_military_target)
 * to `false` will turn this off, which then makes this type equivalent to
 * [SimpleEntityWithOwnerPrototype](prototype:SimpleEntityWithOwnerPrototype).
 */
@Serializable(SimpleEntityWithForcePrototype.Serializer::class)
@SerialName("simple-entity-with-force")
public open class SimpleEntityWithForcePrototype : SimpleEntityWithOwnerPrototype() {
  /**
   * Whether this prototype should be a high priority target for enemy forces. See [Military units
   * and structures](https://wiki.factorio.com/Military_units_and_structures).
   */
  override val is_military_target: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SimpleEntityWithForcePrototype>(SimpleEntityWithForcePrototype::class)
}

/**
 * Has a force, but unlike
 * [SimpleEntityWithForcePrototype](prototype:SimpleEntityWithForcePrototype) it is only attacked if
 * the biters get stuck on it (or if
 * [EntityWithOwnerPrototype::is_military_target](prototype:EntityWithOwnerPrototype::is_military_target)
 * set to true to make the two entity types equivalent).
 *
 * Can be rotated in 4 directions. `picture` can be used to specify different graphics per
 * direction.
 */
@Serializable(SimpleEntityWithOwnerPrototype.Serializer::class)
@SerialName("simple-entity-with-owner")
public open class SimpleEntityWithOwnerPrototype : EntityWithOwnerPrototype() {
  public val render_layer: RenderLayer? by fromJson()

  /**
   * Used to determine render order for entities with the same `render_layer` in the same position.
   * Entities with a higher `secondary_draw_order` are drawn on top.
   */
  public val secondary_draw_order: Byte? by fromJson()

  public val random_animation_offset: Boolean? by fromJson()

  /**
   * Whether a random graphics variation is chosen when placing the entity/creating it via
   * script/creating it via map generation. If this is false, the entity will use the first variation
   * instead of a random one.
   */
  public val random_variation_on_create: Boolean? by fromJson()

  /**
   * Takes priority over `picture` and `animations`.
   */
  public val pictures: SpriteVariations? by fromJson()

  /**
   * Takes priority over `animations`.
   */
  public val picture: Sprite4Way? by fromJson()

  public val animations: AnimationVariations? by fromJson()

  /**
   * If the entity is not visible to a player, the player cannot select it.
   */
  public val force_visibility: ForceCondition? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SimpleEntityWithOwnerPrototype>(SimpleEntityWithOwnerPrototype::class)
}

/**
 * Deprecated in 0.16. The type "simple-smoke" has been obsoleted and cannot be created. Use
 * [TrivialSmokePrototype](prototype:TrivialSmokePrototype) or
 * [SmokeWithTriggerPrototype](prototype:SmokeWithTriggerPrototype) instead.
 */
@Serializable(SimpleSmokePrototype.Serializer::class)
@SerialName("smoke")
public open class SimpleSmokePrototype : SmokePrototype() {
  public object Serializer : JsonReaderSerializer<SimpleSmokePrototype>(SimpleSmokePrototype::class)
}

/**
 * Abstract entity that has an animation.
 */
public sealed class SmokePrototype : EntityPrototype() {
  public val animation: Animation by fromJson()

  public val cyclic: Boolean? by fromJson()

  /**
   * May not be 0 if cyclic is true.
   */
  public val duration: UInt? by fromJson()

  public val spread_duration: UInt? by fromJson()

  /**
   * `fade_in_duration` + `fade_away_duration` must be <= `duration`.
   */
  public val fade_away_duration: UInt? by fromJson()

  /**
   * `fade_in_duration` + `fade_away_duration` must be <= `duration`.
   */
  public val fade_in_duration: UInt? by fromJson()

  public val start_scale: Double? by fromJson()

  public val end_scale: Double? by fromJson()

  public val color: Color? by fromJson()

  /**
   * Smoke always moves randomly unless `movement_slow_down_factor` is 0. If `affected_by_wind` is
   * true, the smoke will also be moved by wind.
   */
  public val affected_by_wind: Boolean? by fromJson()

  public val show_when_smoke_off: Boolean? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  /**
   * Value between 0 and 1, with 0 being no movement.
   */
  public val movement_slow_down_factor: Double? by fromJson()

  public val glow_fade_away_duration: UInt? by fromJson()

  public val glow_animation: Animation? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  /**
   * Must have a collision box size of zero.
   */
  override val collision_box: BoundingBox? by fromJson()
}

/**
 * An entity with animation and a trigger.
 */
@Serializable(SmokeWithTriggerPrototype.Serializer::class)
@SerialName("smoke-with-trigger")
public open class SmokeWithTriggerPrototype : SmokePrototype() {
  public val action: Trigger? by fromJson()

  /**
   * 0 means never apply.
   */
  public val action_cooldown: UInt? by fromJson()

  public val particle_count: UByte? by fromJson()

  public val particle_distance_scale_factor: Float? by fromJson()

  public val spread_duration_variation: UInt? by fromJson()

  public val particle_duration_variation: UInt? by fromJson()

  public val particle_spread: Vector? by fromJson()

  public val particle_scale_factor: Vector? by fromJson()

  public val wave_distance: Vector? by fromJson()

  public val wave_speed: Vector? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SmokeWithTriggerPrototype>(SmokeWithTriggerPrototype::class)
}

/**
 * A [portable solar panel](https://wiki.factorio.com/Portable_solar_panel).
 */
@Serializable(SolarPanelEquipmentPrototype.Serializer::class)
@SerialName("solar-panel-equipment")
public open class SolarPanelEquipmentPrototype : EquipmentPrototype() {
  /**
   * How much power should be provided.
   */
  public val power: Energy by fromJson()

  public object Serializer :
      JsonReaderSerializer<SolarPanelEquipmentPrototype>(SolarPanelEquipmentPrototype::class)
}

/**
 * A [solar panel](https://wiki.factorio.com/Solar_panel).
 */
@Serializable(SolarPanelPrototype.Serializer::class)
@SerialName("solar-panel")
public open class SolarPanelPrototype : EntityWithOwnerPrototype() {
  /**
   * Sets how this solar panel connects to the energy network. The most relevant property seems to
   * be the output_priority.
   */
  public val energy_source: ElectricEnergySource by fromJson()

  /**
   * The picture displayed for this solar panel.
   */
  public val picture: SpriteVariations by fromJson()

  /**
   * The maximum amount of power this solar panel can produce.
   */
  public val production: Energy by fromJson()

  /**
   * Overlay has to be empty or have same number of variations as `picture`.
   */
  public val overlay: SpriteVariations? by fromJson()

  public object Serializer : JsonReaderSerializer<SolarPanelPrototype>(SolarPanelPrototype::class)
}

/**
 * Specifies a sound that can be used with [SoundPath](runtime:SoundPath) at runtime.
 */
@Serializable(SoundPrototype.Serializer::class)
@SerialName("sound")
public open class SoundPrototype : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the sound. Can be used as a [SoundPath](runtime:SoundPath) at runtime.
   */
  public val name: String by fromJson()

  public val category: SoundType? by fromJson()

  public val aggregation: AggregationSpecification? by fromJson()

  public val allow_random_repeat: Boolean? by fromJson()

  /**
   * Modifies how far a sound can be heard. Must be between `0` and `1` inclusive.
   */
  public val audible_distance_modifier: Double? by fromJson()

  public val game_controller_vibration_data: GameControllerVibrationData? by fromJson()

  public val variations: List<SoundDefinition>? by fromJson()

  /**
   * Supported sound file formats are `.ogg` (Vorbis) and `.wav`.
   *
   * Only loaded, and mandatory if `variations` is not defined.
   */
  public val filename: FileName? by fromJson()

  /**
   * Only loaded if `variations` is not defined.
   */
  public val volume: Float? by fromJson()

  /**
   * Only loaded if `variations` is not defined.
   */
  public val preload: Boolean? by fromJson()

  /**
   * Speed must be `>= 1 / 64`. This sets both min and max speeds.
   *
   * Only loaded if `variations` is not defined.
   */
  public val speed: Float? by fromJson()

  /**
   * Must be `>= 1 / 64`.
   *
   * Only loaded if both `variations` and `speed` are not defined.
   */
  public val min_speed: Float? by fromJson()

  /**
   * Must be `>= min_speed`.
   *
   * Only loaded if `variations` is not defined. Only loaded, and mandatory if `min_speed` is
   * defined.
   */
  public val max_speed: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<SoundPrototype>(SoundPrototype::class)
}

/**
 * Properties of the spectator controller.
 */
@Serializable(SpectatorControllerPrototype.Serializer::class)
@SerialName("spectator-controller")
public open class SpectatorControllerPrototype : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the spectator controller. Base game uses "default".
   */
  public val name: String by fromJson()

  /**
   * Must be >= 0.34375.
   */
  public val movement_speed: Double by fromJson()

  public object Serializer :
      JsonReaderSerializer<SpectatorControllerPrototype>(SpectatorControllerPrototype::class)
}

/**
 * A speech bubble. It floats in the world and can display text.
 */
@Serializable(SpeechBubblePrototype.Serializer::class)
@SerialName("speech-bubble")
public open class SpeechBubblePrototype : EntityPrototype() {
  /**
   * Needs a style of the type "speech_bubble_style", defined inside the gui styles.
   */
  public val style: String by fromJson()

  /**
   * Needs a style of the type "flow_style", defined inside the gui styles.
   */
  public val wrapper_flow_style: String? by fromJson()

  public val y_offset: Double? by fromJson()

  public val fade_in_out_ticks: UInt? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SpeechBubblePrototype>(SpeechBubblePrototype::class)
}

/**
 * Used by [SpiderLegSpecification](prototype:SpiderLegSpecification) for
 * [SpiderVehiclePrototype](prototype:SpiderVehiclePrototype), also known as
 * [spidertron](https://wiki.factorio.com/Spidertron).
 */
@Serializable(SpiderLegPrototype.Serializer::class)
@SerialName("spider-leg")
public open class SpiderLegPrototype : EntityWithHealthPrototype() {
  /**
   * Must be larger than 0.
   */
  public val part_length: Double by fromJson()

  public val initial_movement_speed: Double by fromJson()

  public val movement_acceleration: Double by fromJson()

  public val target_position_randomisation_distance: Double by fromJson()

  public val minimal_step_size: Double by fromJson()

  public val movement_based_position_selection_distance: Double by fromJson()

  public val graphics_set: SpiderLegGraphicsSet by fromJson()

  public val walking_sound_volume_modifier: Double? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<SpiderLegPrototype>(SpiderLegPrototype::class)
}

/**
 * A [spidertron](https://wiki.factorio.com/Spidertron).
 */
@Serializable(SpiderVehiclePrototype.Serializer::class)
@SerialName("spider-vehicle")
public open class SpiderVehiclePrototype : VehiclePrototype() {
  /**
   * Must be a burner energy source when using "burner", otherwise it can also be a void energy
   * source.
   */
  public val energy_source: UnknownUnion by fromJson("burner")

  public val inventory_size: ItemStackIndex by fromJson()

  public val graphics_set: SpiderVehicleGraphicsSet by fromJson()

  public val spider_engine: SpiderEnginePrototype by fromJson()

  /**
   * The height of the spider affects the shooting height and the drawing of the graphics and
   * lights.
   */
  public val height: Float by fromJson()

  public val chunk_exploration_radius: UInt by fromJson()

  public val movement_energy_consumption: Energy by fromJson()

  public val automatic_weapon_cycling: Boolean by fromJson()

  /**
   * This is applied whenever the spider shoots (manual and automatic targeting),
   * `automatic_weapon_cycling` is true and the next gun in line (which is then selected) has ammo.
   * When all of the above is the case, the chain_shooting_cooldown_modifier is a multiplier on the
   * remaining shooting cooldown: `cooldown = (remaining_cooldown × chain_shooting_cooldown_modifier)`.
   *
   * chain_shooting_cooldown_modifier is intended to be in the range of 0 to 1. This means that
   * setting chain_shooting_cooldown_modifier to 0 reduces the remaining shooting cooldown to 0 while a
   * chain_shooting_cooldown_modifier of 1 does not affect the remaining shooting cooldown at all.
   */
  public val chain_shooting_cooldown_modifier: Float by fromJson()

  /**
   * The orientation of the torso of the spider affects the shooting direction and the drawing of
   * the graphics and lights.
   */
  public val torso_rotation_speed: Float? by fromJson()

  /**
   * Cannot be negative.
   */
  public val torso_bob_speed: Float? by fromJson()

  /**
   * If set to 0 then the spider will not have a Logistics tab.
   */
  public val trash_inventory_size: ItemStackIndex? by fromJson()

  /**
   * The guns this spider vehicle uses.
   */
  public val guns: List<ItemID>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SpiderVehiclePrototype>(SpiderVehiclePrototype::class)
}

/**
 * The [spidertron remote](https://wiki.factorio.com/Spidertron_remote). This remote can only be
 * connected to entities of type [SpiderVehiclePrototype](prototype:SpiderVehiclePrototype).
 */
@Serializable(SpidertronRemotePrototype.Serializer::class)
@SerialName("spidertron-remote")
public open class SpidertronRemotePrototype : ItemPrototype() {
  /**
   * Inside IconData, the property for the file path is `icon_color_indicator_mask` instead of
   * `icon`. Can't be an empty array.
   *
   * Uses `icon_size` and `icon_mipmaps` from its [ItemPrototype](prototype:ItemPrototype) parent.
   */
  public val icon_color_indicator_masks: List<IconData>? by fromJson()

  /**
   * Path to the icon file.
   *
   * Mandatory if `icon_color_indicator_masks` is not defined.
   *
   * Uses `icon_size` and `icon_mipmaps` from its [ItemPrototype](prototype:ItemPrototype) parent.
   */
  public val icon_color_indicator_mask: FileName? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SpidertronRemotePrototype>(SpidertronRemotePrototype::class)
}

/**
 * A [splitter](https://wiki.factorio.com/Splitter).
 */
@Serializable(SplitterPrototype.Serializer::class)
@SerialName("splitter")
public open class SplitterPrototype : TransportBeltConnectablePrototype() {
  public val structure: Animation4Way by fromJson()

  /**
   * Drawn 1 tile north of `structure` when the splitter is facing east or west.
   */
  public val structure_patch: Animation4Way? by fromJson()

  public val structure_animation_speed_coefficient: Double? by fromJson()

  public val structure_animation_movement_cooldown: UInt? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<SplitterPrototype>(SplitterPrototype::class)
}

/**
 * Specifies an image that can be used with [SpritePath](runtime:SpritePath) at runtime.
 */
@Serializable(SpritePrototype.Serializer::class)
@SerialName("sprite")
public open class SpritePrototype : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the sprite. Can be used as a [SpritePath](runtime:SpritePath) at runtime.
   */
  public val name: String by fromJson()

  /**
   * If this property is present, all Sprite definitions have to be placed as entries in the array,
   * and they will all be loaded from there. `layers` may not be an empty table. Each definition in the
   * array may also have the `layers` property.
   *
   * If this property is present, all other properties besides `name` and `type` are ignored.
   */
  public val layers: List<Sprite>? by fromJson()

  /**
   * Only loaded, and mandatory if `layers` is not defined.
   *
   * The path to the sprite file to use.
   */
  public val filename: FileName? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * If this property exists and high resolution sprites are turned on, this is used to load the
   * Sprite.
   */
  public val hr_version: Sprite? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Number of slices this is sliced into when using the "optimized atlas packing" option. If you
   * are a modder, you can just ignore this property. As an example, if this is `4`, the sprite will be
   * sliced into a `4x4` grid.
   */
  public val slice: SpriteSizeType? by fromJson("dice")

  /**
   * Only loaded if `layers` is not defined.
   *
   * Same as `slice`, but this specifies only how many slices there are on the x-axis.
   */
  public val slice_x: SpriteSizeType? by fromJson("dice_x")

  /**
   * Only loaded if `layers` is not defined.
   *
   * Same as `slice`, but this specifies only how many slices there are on the y-axis.
   */
  public val slice_y: SpriteSizeType? by fromJson("dice_y")

  /**
   * Only loaded if `layers` is not defined.
   */
  public val priority: SpritePriority? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val flags: SpriteFlags? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * The width and height of the sprite. If this is a tuple, the first member of the tuple is the
   * width and the second is the height. Otherwise the size is both width and height. Width and height
   * may only be in the range of 0-8192.
   */
  public val size: ItemOrTuple2<SpriteSizeType>? by fromJson()

  /**
   * Only loaded if `layers` is not defined. Mandatory if `size` is not defined.
   *
   * Width of the picture in pixels, from 0-8192.
   */
  public val width: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `layers` is not defined. Mandatory if `size` is not defined.
   *
   * Height of the picture in pixels, from 0-8192.
   */
  public val height: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Horizontal position of the sprite in the source file in pixels.
   */
  public val x: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Vertical position of the sprite in the source file in pixels.
   */
  public val y: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Loaded only when `x` and `y` are both `0`. The first member of the tuple is `x` and the second
   * is `y`.
   */
  public val position: Tuple2<SpriteSizeType>? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * The shift in tiles. `util.by_pixel()` can be used to divide the shift by 32 which is the usual
   * pixel height/width of 1 tile in normal resolution. Note that 32 pixel tile height/width is not
   * enforced anywhere - any other tile height or width is also possible.
   */
  public val shift: Vector? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Values other than `1` specify the scale of the sprite on default zoom. A scale of `2` means
   * that the picture will be two times bigger on screen (and thus more pixelated).
   */
  public val scale: Double? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Only one of `draw_as_shadow`, `draw_as_glow` and `draw_as_light` can be true. This takes
   * precedence over `draw_as_glow` and `draw_as_light`.
   */
  public val draw_as_shadow: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Only one of `draw_as_shadow`, `draw_as_glow` and `draw_as_light` can be true. This takes
   * precedence over `draw_as_light`.
   *
   * Draws first as a normal sprite, then again as a light layer. See
   * [https://forums.factorio.com/91682](https://forums.factorio.com/91682).
   */
  public val draw_as_glow: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Only one of `draw_as_shadow`, `draw_as_glow` and `draw_as_light` can be true.
   */
  public val draw_as_light: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Only loaded if this is an icon, that is it has the flag `"group=icon"` or `"group=gui"`.
   */
  public val mipmap_count: UByte? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val apply_runtime_tint: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val tint: Color? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val blend_mode: BlendMode? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Minimal mode is entered when mod loading fails. You are in it when you see the gray box after
   * (part of) the loading screen that tells you a mod error
   * ([Example](https://cdn.discordapp.com/attachments/340530709712076801/532315796626472972/unknown.png)).
   * Modders can ignore this property.
   */
  public val load_in_minimal_mode: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Whether alpha should be pre-multiplied.
   */
  public val premul_alpha: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Unused.
   */
  public val generate_sdf: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<SpritePrototype>(SpritePrototype::class)
}

/**
 * Entity that sticks to another entity, and damages/slows it. Stickers can only be attached to
 * [UnitPrototype](prototype:UnitPrototype), [CharacterPrototype](prototype:CharacterPrototype),
 * [CarPrototype](prototype:CarPrototype) and
 * [SpiderVehiclePrototype](prototype:SpiderVehiclePrototype).
 */
@Serializable(StickerPrototype.Serializer::class)
@SerialName("sticker")
public open class StickerPrototype : EntityPrototype() {
  /**
   * Must be > 0.
   */
  public val duration_in_ticks: UInt by fromJson()

  public val animation: Animation? by fromJson()

  /**
   * Interval between application of `damage_per_tick`, in ticks.
   */
  public val damage_interval: UInt? by fromJson()

  /**
   * If this is given, this sticker is considered a "fire sticker" for some functions, such as
   * [BaseAttackParameters::fire_penalty](prototype:BaseAttackParameters::fire_penalty) and
   * [EntityPrototypeFlags::not-flammable](prototype:EntityPrototypeFlags::not_flammable).
   */
  public val spread_fire_entity: EntityID? by fromJson()

  public val fire_spread_cooldown: UByte? by fromJson()

  public val fire_spread_radius: Float? by fromJson()

  public val stickers_per_square_meter: Float? by fromJson()

  public val force_visibility: ForceCondition? by fromJson()

  public val single_particle: Boolean? by fromJson()

  /**
   * Applied every `damage_interval` ticks, so may not necessarily be "per tick".
   */
  public val damage_per_tick: DamagePrototype? by fromJson()

  /**
   * Less than 1 to reduce movement speed, more than 1 to increase it.
   */
  public val target_movement_modifier: Float? by fromJson()

  /**
   * The modifier value when the sticker is attached. It linearly changes over time to reach
   * `target_movement_modifier_to`.
   */
  public val target_movement_modifier_from: Float? by fromJson()

  /**
   * The modifier value when the sticker expires. It linearly changes over time starting from
   * `target_movement_modifier_from`.
   */
  public val target_movement_modifier_to: Float? by fromJson()

  /**
   * Less than 1 to reduce vehicle speed, more than 1 to increase it.
   */
  public val vehicle_speed_modifier: Float? by fromJson()

  /**
   * Works similarly to `target_movement_modifier_from`.
   */
  public val vehicle_speed_modifier_from: Float? by fromJson()

  /**
   * Works similarly to `target_movement_modifier_to`.
   */
  public val vehicle_speed_modifier_to: Float? by fromJson()

  public val vehicle_friction_modifier: Float? by fromJson()

  /**
   * Works similarly to `target_movement_modifier_from`.
   */
  public val vehicle_friction_modifier_from: Float? by fromJson()

  /**
   * Works similarly to `target_movement_modifier_to`.
   */
  public val vehicle_friction_modifier_to: Float? by fromJson()

  /**
   * Using this property marks the sticker as a "selection sticker", meaning that the selection box
   * will be rendered around the entity when the sticker is on it.
   */
  public val selection_box_type: CursorBoxType? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<StickerPrototype>(StickerPrototype::class)
}

/**
 * A [storage tank](https://wiki.factorio.com/Storage_tank).
 */
@Serializable(StorageTankPrototype.Serializer::class)
@SerialName("storage-tank")
public open class StorageTankPrototype : EntityWithOwnerPrototype() {
  public val fluid_box: FluidBox by fromJson()

  /**
   * The location of the window showing the contents. Note that for `window_background` the width
   * and height are determined by the sprite and window_bounding_box only determines the drawing
   * location. For `fluid_background` the width is determined by the sprite and the height and drawing
   * location are determined by window_bounding_box.
   */
  public val window_bounding_box: BoundingBox by fromJson()

  public val pictures: StorageTankPictures by fromJson()

  /**
   * Must be positive.
   *
   * Used for determining the x position inside the `flow_sprite` when drawing the storage tank.
   * Does not affect gameplay.
   *
   * The x position of the sprite will be `((game.tick % flow_length_in_ticks) ÷
   * flow_length_in_ticks) × (flow_sprite.width - 32)`. This means, that over `flow_length_in_ticks`
   * ticks, the part of the `flow_sprite` that is drawn in-game is incrementally moved from most-left
   * to most-right inside the actual sprite, that part always has a width of 32px. After
   * `flow_length_in_ticks`, the part of the `flow_sprite` that is drawn will start from the left
   * again.
   */
  public val flow_length_in_ticks: UInt by fromJson()

  public val two_direction_only: Boolean? by fromJson()

  /**
   * If the icons of fluids shown in alt-mode should be scaled to the storage tank's size.
   */
  public val scale_info_icons: Boolean? by fromJson()

  /**
   * Whether the "alt-mode icon" should be drawn at all.
   */
  public val show_fluid_icon: Boolean? by fromJson()

  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance  > 0.
   */
  public val circuit_wire_connection_points: Tuple4<WireConnectionPoint>? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance  > 0.
   */
  public val circuit_connector_sprites: Tuple4<CircuitConnectorSprites>? by fromJson()

  public object Serializer : JsonReaderSerializer<StorageTankPrototype>(StorageTankPrototype::class)
}

/**
 * A straight rail.
 */
@Serializable(StraightRailPrototype.Serializer::class)
@SerialName("straight-rail")
public open class StraightRailPrototype : RailPrototype() {
  public val bending_type: UnknownStringLiteral? by fromJson()

  public object Serializer :
      JsonReaderSerializer<StraightRailPrototype>(StraightRailPrototype::class)
}

/**
 * A [technology](https://wiki.factorio.com/Technologies).
 *
 * This prototype has two different formats that can be specified. If both `normal` and `expensive`
 * are not defined, the standard properties define this technology. Otherwise, they are ignored, and
 * the `normal` and `expensive` properties are used exclusively to define this technology.
 */
@Serializable(TechnologyPrototype.Serializer::class)
@SerialName("technology")
public open class TechnologyPrototype : PrototypeBase() {
  /**
   * If this name ends with `-<number>`, that number is ignored for localization purposes. E.g. if
   * the name is `technology-3`, the game looks for the `technology-name.technology` localization. The
   * technology tree will also show the number on the technology icon.
   */
  override val name: String by fromJson()

  /**
   * Can't be an empty array.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * Path to the icon file.
   *
   * Mandatory if `icons` is not defined.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Mandatory if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  /**
   * Can be set to `false` if the `expensive` property is defined. This will disable this
   * difficulty, same as setting `enabled` to `false` would. If it's later manually enabled by script,
   * it will use the data from `expensive`.
   *
   * If this property is not defined while `expensive` is, it will mirror its data.
   */
  public val normal: UnknownUnion? by fromJson()

  /**
   * Can be set to `false` if the `normal` property is defined. This will disable this difficulty,
   * same as setting `enabled` to `false` would. If it's later manually enabled by script, it will use
   * the data from `normal`.
   *
   * If this property is not defined while `normal` is, it will mirror its data.
   */
  public val expensive: UnknownUnion? by fromJson()

  /**
   * When set to true, and the technology contains several levels, only the relevant one is
   * displayed in the technology screen.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val upgrade: Boolean? by fromJson()

  /**
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val enabled: Boolean? by fromJson()

  /**
   * Hides the technology from the tech screen.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val hidden: Boolean? by fromJson()

  /**
   * Controls whether the technology is shown in the tech GUI when it is not `enabled`.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val visible_when_disabled: Boolean? by fromJson()

  /**
   * Controls whether the technology cost ignores the tech cost multiplier set in the
   * [DifficultySettings](runtime:DifficultySettings). E.g. `4` for the default expensive difficulty.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val ignore_tech_cost_multiplier: Boolean? by fromJson()

  /**
   * Determines the cost in items and time of the technology.
   *
   * Mandatory if neither `normal` nor `expensive` are defined.
   */
  public val unit: TechnologyUnit? by fromJson()

  /**
   * `"infinite"` for infinite technologies, otherwise `uint32`.
   *
   * Defaults to the same level as the technology, which is `0` for non-upgrades, and the level of
   * the upgrade for upgrades.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val max_level: UnknownUnion? by fromJson()

  /**
   * List of technologies needed to be researched before this one can be researched.
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val prerequisites: List<TechnologyID>? by fromJson()

  /**
   * List of effects of the technology (applied when the technology is researched).
   *
   * Only loaded if neither `normal` nor `expensive` are defined.
   */
  public val effects: List<Modifier>? by fromJson()

  public object Serializer : JsonReaderSerializer<TechnologyPrototype>(TechnologyPrototype::class)
}

/**
 * Used to define the parameters for the water shader.
 */
@Serializable(TileEffectDefinition.Serializer::class)
@SerialName("tile-effect")
public open class TileEffectDefinition : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the tile-effect. Base game uses "water".
   */
  public val name: String by fromJson()

  public val specular_lightness: Color by fromJson()

  public val foam_color: Color by fromJson()

  public val foam_color_multiplier: Float by fromJson()

  public val tick_scale: Float by fromJson()

  public val animation_speed: Float by fromJson()

  public val animation_scale: ItemOrTuple2<Float> by fromJson()

  public val dark_threshold: ItemOrTuple2<Float> by fromJson()

  public val reflection_threshold: ItemOrTuple2<Float> by fromJson()

  public val specular_threshold: ItemOrTuple2<Float> by fromJson()

  /**
   * Sprite size must be 512x512.
   */
  public val texture: Sprite by fromJson()

  public val near_zoom: Float? by fromJson()

  public val far_zoom: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<TileEffectDefinition>(TileEffectDefinition::class)
}

/**
 * The entity used for tile ghosts.
 */
@Serializable(TileGhostPrototype.Serializer::class)
@SerialName("tile-ghost")
public open class TileGhostPrototype : EntityPrototype() {
  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<TileGhostPrototype>(TileGhostPrototype::class)
}

/**
 * A [tile](https://wiki.factorio.com/Tile).
 */
@Serializable(TilePrototype.Serializer::class)
@SerialName("tile")
public open class TilePrototype : PrototypeBase() {
  public val collision_mask: CollisionMask by fromJson()

  /**
   * Specifies transition drawing priority.
   */
  public val layer: UByte by fromJson()

  /**
   * Graphics for this tile.
   */
  public val variants: TileTransitionsVariants by fromJson()

  public val map_color: Color by fromJson()

  /**
   * Emissions absorbed per second by this tile. Use a negative value if pollution is created
   * instead of removed.
   */
  public val pollution_absorption_per_second: Double by fromJson()

  /**
   * Can't be an empty array. If this and `icon` is not set, the `material_background` in `variants`
   * is used as the icon.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * Path to the icon file. If this and `icon` is not set, the `material_background` in `variants`
   * is used as the icon.
   *
   * Only loaded if `icons` is not defined.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Only loaded if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  public val transition_overlay_layer_offset: Byte? by fromJson()

  public val layer_group: TileRenderLayer? by fromJson()

  /**
   * Used only for the `layer_group` default, see above.
   */
  public val draw_in_water_layer: Boolean? by fromJson()

  public val transition_merges_with_tile: TileID? by fromJson()

  public val effect_color: Color? by fromJson()

  public val tint: Color? by fromJson()

  public val walking_sound: Sound? by fromJson()

  /**
   * If this is loaded as one Sound, it is loaded as the "small" build sound.
   */
  public val build_sound: UnknownUnion? by fromJson()

  public val mined_sound: Sound? by fromJson()

  public val walking_speed_modifier: Double? by fromJson()

  public val vehicle_friction_modifier: Double? by fromJson()

  public val decorative_removal_probability: Float? by fromJson()

  /**
   * Array of tile names that are allowed next to this one.
   */
  public val allowed_neighbors: List<TileID>? by fromJson()

  /**
   * Whether the tile needs tile correction logic applied when it's generated in the world, to
   * prevent graphical artifacts. The tile correction logic disallows 1-wide stripes of the tile, see
   * [Friday Facts #346](https://factorio.com/blog/post/fff-346).
   */
  public val needs_correction: Boolean? by fromJson()

  /**
   * If you want the tile to not be mineable, don't specify the minable property. Only non-mineable
   * tiles become hidden tiles when placing mineable tiles on top of them.
   */
  public val minable: MinableProperties? by fromJson()

  public val next_direction: TileID? by fromJson()

  public val can_be_part_of_blueprint: Boolean? by fromJson()

  /**
   * Name of a [TileEffectDefinition](prototype:TileEffectDefinition).
   */
  public val effect: String? by fromJson()

  /**
   * Called by [InvokeTileEffectTriggerEffectItem](prototype:InvokeTileEffectTriggerEffectItem).
   */
  public val trigger_effect: TriggerEffect? by fromJson()

  public val scorch_mark_color: Color? by fromJson()

  /**
   * If set to true, the game will check for collisions with entities before building or mining the
   * tile. If entities are in the way it is not possible to mine/build the tile.
   */
  public val check_collision_with_entities: Boolean? by fromJson()

  /**
   * Used by the [pollution](https://wiki.factorio.com/Pollution) shader.
   */
  public val effect_color_secondary: Color? by fromJson()

  public val effect_is_opaque: Boolean? by fromJson()

  /**
   * Extra transitions.
   */
  public val transitions: List<TileTransitionsToTiles>? by fromJson()

  public val transitions_between_transitions: List<TileTransitionsBetweenTransitions>? by fromJson()

  public val autoplace: AutoplaceSpecification? by fromJson()

  public val placeable_by: ItemOrList<ItemToPlace>? by fromJson()

  public object Serializer : JsonReaderSerializer<TilePrototype>(TilePrototype::class)
}

/**
 * A tips and tricks entry.
 */
@Serializable(TipsAndTricksItem.Serializer::class)
@SerialName("tips-and-tricks-item")
public open class TipsAndTricksItem : PrototypeBase() {
  public val image: FileName? by fromJson()

  public val simulation: SimulationDefinition? by fromJson()

  /**
   * String to add in front of the tips and trick entries name. Can be anything, the base game tends
   * to use [rich text](https://wiki.factorio.com/Rich_text) tags for items, e.g. `[item=wooden-chest]`
   * here.
   */
  public val tag: String? by fromJson()

  /**
   * Name of a [TipsAndTricksItemCategory](prototype:TipsAndTricksItemCategory), used for the
   * sorting of this tips and tricks entry. Tips and trick entries are sorted first by category and
   * then by their `order` within that category.
   */
  public val category: String? by fromJson()

  /**
   * The tips and tricks entry is indented by `indent`×6 spaces.
   */
  public val indent: UByte? by fromJson()

  /**
   * Whether the tip title on the left in the tips and tricks GUI should use the "title_tip_item"
   * style (semi bold font).
   */
  public val is_title: Boolean? by fromJson()

  /**
   * Condition for when the tip notification should be shown to the player.
   */
  public val trigger: TipTrigger? by fromJson()

  /**
   * Condition for never showing the tip notification to the player.
   */
  public val skip_trigger: TipTrigger? by fromJson()

  /**
   * Name of a [TutorialDefinition](prototype:TutorialDefinition).
   */
  public val tutorial: String? by fromJson()

  public val starting_status: TipStatus? by fromJson()

  /**
   * An array of names of other tips and tricks items. This tips and tricks entry is only shown to
   * the player once they have marked all dependencies as read.
   */
  public val dependencies: List<String>? by fromJson()

  public val player_input_method_filter: PlayerInputMethodFilter? by fromJson()

  public object Serializer : JsonReaderSerializer<TipsAndTricksItem>(TipsAndTricksItem::class)
}

/**
 * A [TipsAndTricksItem](prototype:TipsAndTricksItem) category, used for sorting of tips and tricks
 * entries: Tips and trick entries are sorted first by category and then by their order within that
 * category.
 */
@Serializable(TipsAndTricksItemCategory.Serializer::class)
@SerialName("tips-and-tricks-item-category")
public open class TipsAndTricksItemCategory : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  public val name: String by fromJson()

  /**
   * Tips and trick categories are sorted by `order`, and then the tips and tips entries are sorted
   * by their own order within those categories.
   */
  public val order: Order by fromJson()

  public object Serializer :
      JsonReaderSerializer<TipsAndTricksItemCategory>(TipsAndTricksItemCategory::class)
}

/**
 * Items with a "durability". Used for [science packs](https://wiki.factorio.com/Science_pack).
 */
@Serializable(ToolPrototype.Serializer::class)
@SerialName("tool")
public open class ToolPrototype : ItemPrototype() {
  /**
   * The durability of this tool. Must be positive. Mandatory if `infinite` is false. Ignored if
   * <code>infinite</code> is true.
   */
  public val durability: Double? by fromJson()

  /**
   * May not be longer than 200 characters.
   */
  public val durability_description_key: String? by fromJson()

  /**
   * May not be longer than 200 characters.
   *
   * In-game, the game provides the locale with three
   * [parameters](https://wiki.factorio.com/Tutorial:Localisation#Localising_with_parameters):
   *
   * `__1__`: remaining durability
   *
   * `__2__`: total durability
   *
   * `__3__`: durability as a percentage
   *
   * So when a locale key that has the following translation
   *
   * `Remaining durability is __1__ out of __2__ which is __3__ %`
   *
   * is applied to a tool with 2 remaining durability out of 8 it will be displayed as
   *
   * `Remaining durability is 2 out of 8 which is 25 %`
   */
  public val durability_description_value: String? by fromJson()

  /**
   * Whether this tool has infinite durability. If this is false, `durability` must be specified.
   */
  public val infinite: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<ToolPrototype>(ToolPrototype::class)
}

/**
 * This prototype is used for receiving an achievement when the player has a specified train path
 * length.
 */
@Serializable(TrainPathAchievementPrototype.Serializer::class)
@SerialName("train-path-achievement")
public open class TrainPathAchievementPrototype : AchievementPrototype() {
  /**
   * The achievement will trigger if a train path is longer than this.
   */
  public val minimum_distance: Double by fromJson()

  public object Serializer :
      JsonReaderSerializer<TrainPathAchievementPrototype>(TrainPathAchievementPrototype::class)
}

/**
 * A [train stop](https://wiki.factorio.com/Train_stop).
 */
@Serializable(TrainStopPrototype.Serializer::class)
@SerialName("train-stop")
public open class TrainStopPrototype : EntityWithOwnerPrototype() {
  public val animation_ticks_per_frame: UInt by fromJson()

  public val rail_overlay_animations: Animation4Way? by fromJson()

  public val animations: Animation4Way? by fromJson()

  public val top_animations: Animation4Way? by fromJson()

  public val default_train_stopped_signal: SignalIDConnector? by fromJson()

  public val default_trains_count_signal: SignalIDConnector? by fromJson()

  public val default_trains_limit_signal: SignalIDConnector? by fromJson()

  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  public val color: Color? by fromJson()

  public val chart_name: Boolean? by fromJson()

  public val light1: TrainStopLight? by fromJson()

  public val light2: TrainStopLight? by fromJson()

  public val drawing_boxes: TrainStopDrawingBoxes? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance > 0.
   */
  public val circuit_wire_connection_points: Tuple4<WireConnectionPoint>? by fromJson()

  /**
   * Mandatory if circuit_wire_max_distance > 0.
   */
  public val circuit_connector_sprites: Tuple4<CircuitConnectorSprites>? by fromJson()

  public object Serializer : JsonReaderSerializer<TrainStopPrototype>(TrainStopPrototype::class)
}

/**
 * Abstract class that anything that is a belt or can connect to belts uses.
 */
public sealed class TransportBeltConnectablePrototype : EntityWithOwnerPrototype() {
  /**
   * The speed of the belt: `speed × 480 = x Items/second`.
   *
   * The raw value is expressed as the number of tiles traveled by each item on the belt per tick,
   * relative to the belt's maximum density - e.g. `x items/second ÷ (4 items/lane × 2 lanes/belt × 60
   * ticks/second) = <speed> belts/tick` where a "belt" is the size of one tile. See
   * [Transport_belts/Physics](https://wiki.factorio.com/Transport_belts/Physics) for more details.
   *
   * Must be a positive non-infinite number. The number is a fixed point number with 8 bits reserved
   * for decimal precision, meaning the smallest value step is `1/2^8 = 0.00390625`. In the simple case
   * of a non-curved belt, the rate is multiples of `1.875` items/s, even though the entity tooltip may
   * show a different rate.
   */
  public val speed: Double by fromJson()

  public val animation_speed_coefficient: Double? by fromJson()

  /**
   * This is the preferred way to specify transport belt animations.
   */
  public open val belt_animation_set: UnknownOverriddenType? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   */
  public val belt_horizontal: Animation? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   */
  public val belt_vertical: Animation? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   */
  public val ending_top: Animation? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   */
  public val ending_bottom: Animation? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   */
  public val ending_side: Animation? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   */
  public val starting_top: Animation? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   */
  public val starting_bottom: Animation? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   */
  public val starting_side: Animation? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   */
  public val ending_patch: Sprite4Way? by fromJson()

  public val ends_with_stopper: Boolean? by fromJson()

  /**
   * Transport belt connectable entities must collide with "transport-belt-layer". Transport belt
   * connectable entities must have collision_mask that collides with itself. Transport belt
   * connectable entities cannot have collision mask that collides only with tiles (must collide with
   * entities in some way).
   */
  open override val collision_mask: CollisionMask? by fromJson()

  /**
   * Transport belt connectable entities must have
   * [collision_box](prototype:EntityPrototype::collision_box) of an appropriate minimal size, they
   * should occupy more than half of every tile the entity covers.
   */
  override val collision_box: BoundingBox? by fromJson()

  /**
   * Transport belt connectable entities cannot have the `"building-direction-8-way"` flag.
   */
  override val flags: EntityPrototypeFlags? by fromJson()
}

/**
 * A [transport belt](https://wiki.factorio.com/Transport_belt).
 */
@Serializable(TransportBeltPrototype.Serializer::class)
@SerialName("transport-belt")
public open class TransportBeltPrototype : TransportBeltConnectablePrototype() {
  public val connector_frame_sprites: TransportBeltConnectorFrame by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  /**
   * Defines how wires visually connect to this transport belt.
   */
  public val circuit_wire_connection_points: List<WireConnectionPoint>? by fromJson()

  /**
   * The pictures displayed for circuit connections to this transport belt.
   */
  public val circuit_connector_sprites: List<CircuitConnectorSprites>? by fromJson()

  /**
   * This is the preferred way to specify transport belt animations.
   */
  override val belt_animation_set: UnknownOverriddenType? by fromJson()

  /**
   * Mandatory if `belt_animation_set` is not defined.
   *
   * Transport belts must have 12 animations.
   */
  public val animations: RotatedAnimation? by fromJson()

  /**
   * The name of the [UndergroundBeltPrototype](prototype:UndergroundBeltPrototype) which is used in
   * quick-replace fashion when the smart belt dragging behavior is triggered.
   */
  public val related_underground_belt: EntityID? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TransportBeltPrototype>(TransportBeltPrototype::class)
}

/**
 * A [tree](https://wiki.factorio.com/Tree).
 */
@Serializable(TreePrototype.Serializer::class)
@SerialName("tree")
public open class TreePrototype : EntityWithHealthPrototype() {
  public val variation_weights: List<Float>? by fromJson()

  public val darkness_of_burnt_tree: Float? by fromJson()

  /**
   * Mandatory if `variations` is not defined.
   */
  public val pictures: SpriteVariations? by fromJson()

  /**
   * Can't be empty.
   */
  public val variations: List<TreeVariation>? by fromJson()

  /**
   * Mandatory if `variations` is defined.
   */
  public val colors: List<Color>? by fromJson()

  /**
   * The amount of health automatically regenerated. Trees will regenerate every 60 ticks with
   * `healing_per_tick × 60`.
   */
  override val healing_per_tick: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<TreePrototype>(TreePrototype::class)
}

/**
 * The base game always internally defines a "common" trigger target type. See [Design discussion:
 * Trigger target type](https://forums.factorio.com/71657).
 */
@Serializable(TriggerTargetType.Serializer::class)
@SerialName("trigger-target-type")
public open class TriggerTargetType : JsonReader(), AnyPrototype {
  public val type: UnknownStringLiteral by fromJson()

  public val name: String by fromJson()

  public object Serializer : JsonReaderSerializer<TriggerTargetType>(TriggerTargetType::class)
}

/**
 * Smoke, but it's not an entity for optimization purposes.
 */
@Serializable(TrivialSmokePrototype.Serializer::class)
@SerialName("trivial-smoke")
public open class TrivialSmokePrototype : PrototypeBase() {
  public val animation: Animation by fromJson()

  /**
   * Can't be 0 - the smoke will never render.
   */
  public val duration: UInt by fromJson()

  public val glow_animation: Animation? by fromJson()

  public val color: Color? by fromJson()

  public val start_scale: Float? by fromJson()

  public val end_scale: Float? by fromJson()

  /**
   * Value between 0 and 1, with 1 being no slowdown and 0 being no movement.
   */
  public val movement_slow_down_factor: Double? by fromJson()

  public val spread_duration: UInt? by fromJson()

  /**
   * `fade_in_duration` + `fade_away_duration` must be <= `duration`.
   */
  public val fade_away_duration: UInt? by fromJson()

  /**
   * `fade_in_duration` + `fade_away_duration` must be <= `duration`.
   */
  public val fade_in_duration: UInt? by fromJson()

  public val glow_fade_away_duration: UInt? by fromJson()

  public val cyclic: Boolean? by fromJson()

  /**
   * Smoke always moves randomly unless `movement_slow_down_factor` is 0. If `affected_by_wind` is
   * true, the smoke will also be moved by wind.
   */
  public val affected_by_wind: Boolean? by fromJson()

  public val show_when_smoke_off: Boolean? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TrivialSmokePrototype>(TrivialSmokePrototype::class)
}

/**
 * A turret that needs no extra ammunition. See the children for turrets that need some kind of
 * ammunition.
 */
@Serializable(TurretPrototype.Serializer::class)
@SerialName("turret")
public open class TurretPrototype : EntityWithOwnerPrototype() {
  /**
   * Requires ammo_type in attack_parameters unless this is a
   * [AmmoTurretPrototype](prototype:AmmoTurretPrototype).
   */
  public open val attack_parameters: UnknownOverriddenType by fromJson()

  public val folded_animation: RotatedAnimation4Way by fromJson()

  public val call_for_help_radius: Double by fromJson()

  public val attack_target_mask: TriggerTargetMask? by fromJson()

  public val ignore_target_mask: TriggerTargetMask? by fromJson()

  public val shoot_in_prepare_state: Boolean? by fromJson()

  public open val turret_base_has_direction: Boolean? by fromJson()

  public val random_animation_offset: Boolean? by fromJson()

  /**
   * Whether the secondary (animation) speeds should always be used.
   */
  public val secondary_animation: Boolean? by fromJson()

  public val attack_from_start_frame: Boolean? by fromJson()

  public val allow_turning_when_starting_attack: Boolean? by fromJson()

  public val base_picture_secondary_draw_order: UByte? by fromJson()

  public val gun_animation_secondary_draw_order: UByte? by fromJson()

  public val base_picture_render_layer: RenderLayer? by fromJson()

  public val gun_animation_render_layer: RenderLayer? by fromJson()

  public val base_picture: Animation4Way? by fromJson()

  public val preparing_animation: RotatedAnimation4Way? by fromJson()

  public val prepared_animation: RotatedAnimation4Way? by fromJson()

  public val prepared_alternative_animation: RotatedAnimation4Way? by fromJson()

  public val starting_attack_animation: RotatedAnimation4Way? by fromJson()

  public val attacking_animation: RotatedAnimation4Way? by fromJson()

  public val energy_glow_animation: RotatedAnimation4Way? by fromJson()

  public val ending_attack_animation: RotatedAnimation4Way? by fromJson()

  public val folding_animation: RotatedAnimation4Way? by fromJson()

  public val integration: Sprite? by fromJson()

  /**
   * The intensity of light in the form of `energy_glow_animation` drawn on top of
   * `energy_glow_animation`.
   */
  public val glow_light_intensity: Float? by fromJson()

  /**
   * The range of the flickering of the alpha of `energy_glow_animation`. Default is range 0.2, so
   * animation alpha can be anywhere between 0.8 and 1.0.
   */
  public val energy_glow_animation_flicker_strength: Float? by fromJson()

  public val starting_attack_sound: Sound? by fromJson()

  public val dying_sound: Sound? by fromJson()

  public val preparing_sound: Sound? by fromJson()

  public val folding_sound: Sound? by fromJson()

  public val prepared_sound: Sound? by fromJson()

  public val prepared_alternative_sound: Sound? by fromJson()

  public val rotation_speed: Float? by fromJson()

  /**
   * Controls the speed of the preparing_animation: `1 ÷ preparing_speed = duration of the
   * preparing_animation`
   */
  public val preparing_speed: Float? by fromJson()

  /**
   * It's randomized whether a particular turret uses the primary or the secondary speed for its
   * animations.
   *
   * Controls the speed of the folded_animation: `1 ÷ folded_speed = duration of the
   * folded_animation`
   */
  public val folded_speed: Float? by fromJson()

  /**
   * It's randomized whether a particular turret uses the primary or the secondary speed for its
   * animations.
   *
   * Controls the speed of the folded_animation: `1 ÷ folded_speed_secondary = duration of the
   * folded_animation`
   */
  public val folded_speed_secondary: Float? by fromJson()

  /**
   * It's randomized whether a particular turret uses the primary or the secondary speed for its
   * animations.
   *
   * Controls the speed of the prepared_animation: `1 ÷ prepared_speed = duration of the
   * prepared_animation`
   */
  public val prepared_speed: Float? by fromJson()

  /**
   * It's randomized whether a particular turret uses the primary or the secondary speed for its
   * animations.
   *
   * Controls the speed of the prepared_animation: `1 ÷ prepared_speed_secondary = duration of the
   * prepared_animation`
   */
  public val prepared_speed_secondary: Float? by fromJson()

  /**
   * It's randomized whether a particular turret uses the primary or the secondary speed for its
   * animations.
   *
   * Controls the speed of the prepared_alternative_animation: `1 ÷ prepared_alternative_speed =
   * duration of the prepared_alternative_animation`
   */
  public val prepared_alternative_speed: Float? by fromJson()

  /**
   * It's randomized whether a particular turret uses the primary or the secondary speed for its
   * animations.
   *
   * Controls the speed of the prepared_alternative_animation: `1 ÷
   * prepared_alternative_speed_secondary = duration of the prepared_alternative_animation`
   */
  public val prepared_alternative_speed_secondary: Float? by fromJson()

  /**
   * The chance for `prepared_alternative_animation` to be used.
   */
  public val prepared_alternative_chance: Float? by fromJson()

  /**
   * Controls the speed of the starting_attack_animation: `1 ÷ starting_attack_speed = duration of
   * the starting_attack_animation`
   */
  public val starting_attack_speed: Float? by fromJson()

  /**
   * Controls the speed of the attacking_animation: `1 ÷ attacking_speed = duration of the
   * attacking_animation`
   */
  public val attacking_speed: Float? by fromJson()

  /**
   * Controls the speed of the ending_attack_animation: `1 ÷ ending_attack_speed = duration of the
   * ending_attack_animation`
   */
  public val ending_attack_speed: Float? by fromJson()

  /**
   * Controls the speed of the folding_animation: `1 ÷ folding_speed = duration of the
   * folding_animation`
   */
  public val folding_speed: Float? by fromJson()

  public val prepare_range: Double? by fromJson()

  public val alert_when_attacking: Boolean? by fromJson()

  /**
   * Whether `spawn_decoration` should be spawned when this turret is created through [enemy
   * expansion](https://wiki.factorio.com/Enemies#Expansions).
   */
  public val spawn_decorations_on_expansion: Boolean? by fromJson()

  /**
   * Decoratives to be created when the spawner is created by the [map
   * generator](https://wiki.factorio.com/Map_generator). Placed when enemies expand if
   * `spawn_decorations_on_expansion` is set to true.
   */
  public val spawn_decoration: ItemOrList<CreateDecorativesTriggerEffectItem>? by fromJson()

  /**
   * Whether this prototype should be a high priority target for enemy forces. See [Military units
   * and structures](https://wiki.factorio.com/Military_units_and_structures).
   */
  override val is_military_target: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<TurretPrototype>(TurretPrototype::class)
}

/**
 * The definition of the tutorial to be used in the tips and tricks, see
 * [TipsAndTricksItem](prototype:TipsAndTricksItem). The actual tutorial scripting code is defined in
 * the tutorial scenario. The scenario must be placed in the `tutorials` folder in the mod.
 */
@Serializable(TutorialDefinition.Serializer::class)
@SerialName("tutorial")
public open class TutorialDefinition : PrototypeBase() {
  /**
   * Name of the folder for this tutorial scenario in the [`tutorials`
   * folder](https://wiki.factorio.com/Tutorial:Mod_structure#Subfolders).
   */
  public val scenario: String by fromJson()

  public object Serializer : JsonReaderSerializer<TutorialDefinition>(TutorialDefinition::class)
}

/**
 * An [underground belt](https://wiki.factorio.com/Underground_belt).
 */
@Serializable(UndergroundBeltPrototype.Serializer::class)
@SerialName("underground-belt")
public open class UndergroundBeltPrototype : TransportBeltConnectablePrototype() {
  public val max_distance: UByte by fromJson()

  public val structure: UndergroundBeltStructure by fromJson()

  public val underground_sprite: Sprite by fromJson()

  public val underground_remove_belts_sprite: Sprite? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<UndergroundBeltPrototype>(UndergroundBeltPrototype::class)
}

/**
 * Entity that moves around and attacks players, for example [biters and
 * spitters](https://wiki.factorio.com/Enemies#Creatures).
 */
@Serializable(UnitPrototype.Serializer::class)
@SerialName("unit")
public open class UnitPrototype : EntityWithOwnerPrototype() {
  public val run_animation: RotatedAnimation by fromJson()

  /**
   * Requires animation in attack_parameters. Requires ammo_type in attack_parameters.
   */
  public val attack_parameters: AttackParameters by fromJson()

  /**
   * Movement speed of the unit in the world, in tiles per tick. Must be equal to or greater than 0.
   */
  public val movement_speed: Float by fromJson()

  /**
   * How fast the `run_animation` frames are advanced. The animations are advanced animation_speed
   * frames per `distance_per_frame` that the unit moves.
   *
   * `frames_advanced = (distance_moved ÷ distance_per_frame) * animation_speed`
   */
  public val distance_per_frame: Float by fromJson()

  /**
   * The amount of pollution that has to be absorbed by the unit's spawner before the unit will
   * leave the spawner and attack the source of the pollution.
   */
  public val pollution_to_join_attack: Float by fromJson()

  public val distraction_cooldown: UInt by fromJson()

  /**
   * Max is 100.
   *
   * Note: Setting to 50 or above can lead to undocumented behavior of individual units creating
   * groups on their own when attacking or being attacked.
   */
  public val vision_distance: Double by fromJson()

  public val rotation_speed: Float? by fromJson()

  /**
   * The sound file to play when entity dies.
   */
  public val dying_sound: Sound? by fromJson()

  /**
   * In ticks.
   */
  public val min_pursue_time: UInt? by fromJson()

  /**
   * If the unit is immune to movement by belts.
   */
  public val has_belt_immunity: Boolean? by fromJson()

  public val spawning_time_modifier: Double? by fromJson()

  public val max_pursue_distance: Double? by fromJson()

  public val radar_range: UInt? by fromJson()

  public val ai_settings: UnitAISettings? by fromJson()

  public val move_while_shooting: Boolean? by fromJson()

  public val can_open_gates: Boolean? by fromJson()

  public val affected_by_tiles: Boolean? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public val light: LightDefinition? by fromJson()

  public val walking_sound: Sound? by fromJson()

  public val alternative_attacking_frame_sequence: UnitAlternativeFrameSequence? by fromJson()

  /**
   * Only loaded if `walking_sound` is defined.
   */
  public val running_sound_animation_positions: List<Float>? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  override val collision_mask: CollisionMask? by fromJson()

  public object Serializer : JsonReaderSerializer<UnitPrototype>(UnitPrototype::class)
}

/**
 * An [upgrade planner](https://wiki.factorio.com/Upgrade_planner).
 */
@Serializable(UpgradeItemPrototype.Serializer::class)
@SerialName("upgrade-item")
public open class UpgradeItemPrototype : SelectionToolPrototype() {
  /**
   * Can't be > 255.
   */
  public val mapper_count: ItemStackIndex? by fromJson()

  /**
   * This property is hardcoded to `"upgrade"`.
   */
  override val selection_mode: SelectionModeFlags? by fromJson()

  /**
   * This property is hardcoded to `"cancel-upgrade"`.
   */
  override val alt_selection_mode: SelectionModeFlags? by fromJson()

  /**
   * This property is hardcoded to `false`.
   */
  override val always_include_tiles: Boolean? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_filters: List<EntityID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_filters: List<EntityID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_type_filters: List<String>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_type_filters: List<String>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val tile_filters: List<TileID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_tile_filters: List<TileID>? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val entity_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_entity_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val tile_filter_mode: EntityFilterMode? by fromJson()

  /**
   * This property is parsed, but then ignored.
   */
  override val alt_tile_filter_mode: EntityFilterMode? by fromJson()

  public object Serializer : JsonReaderSerializer<UpgradeItemPrototype>(UpgradeItemPrototype::class)
}

/**
 * Constants used by the game that are not specific to certain prototypes. See
 * [utility-constants.lua](https://github.com/wube/factorio-data/blob/master/core/prototypes/utility-constants.lua)
 * for the values used by the base game.
 */
@Serializable(UtilityConstants.Serializer::class)
@SerialName("utility-constants")
public open class UtilityConstants : PrototypeBase() {
  public val entity_button_background_color: Color by fromJson()

  public val building_buildable_too_far_tint: Color by fromJson()

  public val building_buildable_tint: Color by fromJson()

  public val building_not_buildable_tint: Color by fromJson()

  public val building_ignorable_tint: Color by fromJson()

  public val building_no_tint: Color by fromJson()

  public val ghost_tint: Color by fromJson()

  public val tile_ghost_tint: Color by fromJson()

  public val equipment_default_background_color: Color by fromJson()

  public val equipment_default_background_border_color: Color by fromJson()

  public val equipment_default_grabbed_background_color: Color by fromJson()

  public val turret_range_visualization_color: Color by fromJson()

  public val capsule_range_visualization_color: Color by fromJson()

  public val artillery_range_visualization_color: Color by fromJson()

  public val train_no_path_color: Color by fromJson()

  public val train_destination_full_color: Color by fromJson()

  /**
   * Chart = map + minimap.
   */
  public val chart: ChartUtilityConstants by fromJson()

  public val gui_remark_color: Color by fromJson()

  public val default_player_force_color: Color by fromJson()

  public val default_enemy_force_color: Color by fromJson()

  public val default_other_force_color: Color by fromJson()

  public val deconstruct_mark_tint: Color by fromJson()

  public val rail_planner_count_button_color: Color by fromJson()

  public val count_button_size: Int by fromJson()

  public val zoom_to_world_can_use_nightvision: Boolean by fromJson()

  public val zoom_to_world_effect_strength: Float by fromJson()

  public val max_terrain_building_size: UByte by fromJson()

  public val small_area_size: Float by fromJson()

  public val medium_area_size: Float by fromJson()

  public val small_blueprint_area_size: Float by fromJson()

  public val medium_blueprint_area_size: Float by fromJson()

  public val enabled_recipe_slot_tint: Color by fromJson()

  public val disabled_recipe_slot_tint: Color by fromJson()

  public val disabled_recipe_slot_background_tint: Color by fromJson()

  public val forced_enabled_recipe_slot_background_tint: Color by fromJson()

  public val rail_segment_colors: List<Color> by fromJson()

  /**
   * The table with `name = "default"` must exist and be the first member of the array.
   */
  public val player_colors: List<PlayerColorData> by fromJson()

  public val server_command_console_chat_color: Color by fromJson()

  public val script_command_console_chat_color: Color by fromJson()

  public val default_alert_icon_scale: Float by fromJson()

  public val default_alert_icon_shift_by_type: Map<String, Vector>? by fromJson()

  public val default_alert_icon_scale_by_type: Map<String, Float>? by fromJson()

  public val daytime_color_lookup: DaytimeColorLookupTable by fromJson()

  public val zoom_to_world_daytime_color_lookup: DaytimeColorLookupTable by fromJson()

  public val checkerboard_white: Color by fromJson()

  public val checkerboard_black: Color by fromJson()

  public val item_outline_color: Color by fromJson()

  public val item_outline_radius: Float by fromJson()

  public val item_outline_inset: Float by fromJson()

  public val item_outline_sharpness: Float by fromJson()

  public val filter_outline_color: Color by fromJson()

  public val icon_shadow_radius: Float by fromJson()

  public val icon_shadow_inset: Float by fromJson()

  public val icon_shadow_sharpness: Float by fromJson()

  public val icon_shadow_color: Color by fromJson()

  public val clipboard_history_size: UInt by fromJson()

  public val recipe_step_limit: UInt by fromJson()

  public val manual_rail_building_reach_modifier: Double by fromJson()

  public val train_temporary_stop_wait_time: UInt by fromJson()

  public val train_time_wait_condition_default: UInt by fromJson()

  public val train_inactivity_wait_condition_default: UInt by fromJson()

  /**
   * The strings are entity types.
   */
  public val default_trigger_target_mask_by_type: Map<String, TriggerTargetMask>? by fromJson()

  public val unit_group_pathfind_resolution: Byte by fromJson()

  public val unit_group_max_pursue_distance: Double by fromJson()

  public val dynamic_recipe_overload_factor: Double by fromJson()

  public val minimum_recipe_overload_multiplier: UInt by fromJson()

  public val maximum_recipe_overload_multiplier: UInt by fromJson()

  public val tree_leaf_distortion_strength_far: Vector by fromJson()

  public val tree_leaf_distortion_distortion_far: Vector by fromJson()

  public val tree_leaf_distortion_speed_far: Vector by fromJson()

  public val tree_leaf_distortion_strength_near: Vector by fromJson()

  public val tree_leaf_distortion_distortion_near: Vector by fromJson()

  public val tree_leaf_distortion_speed_near: Vector by fromJson()

  public val tree_shadow_roughness: Float by fromJson()

  public val tree_shadow_speed: Float by fromJson()

  public val missing_preview_sprite_location: FileName by fromJson()

  public val main_menu_background_image_location: FileName by fromJson()

  /**
   * The strings represent the names of the simulations.
   */
  public val main_menu_simulations: Map<String, SimulationDefinition> by fromJson()

  public val main_menu_background_vignette_intensity: Float by fromJson()

  public val main_menu_background_vignette_sharpness: Float by fromJson()

  public val default_scorch_mark_color: Color by fromJson()

  public val train_button_hovered_tint: Color by fromJson()

  /**
   * Must be in range [1, 100].
   */
  public val select_group_row_count: UByte by fromJson()

  /**
   * Must be in range [1, 100].
   */
  public val select_slot_row_count: UInt by fromJson()

  /**
   * Must be in range [1, 100].
   */
  public val inventory_width: UInt by fromJson()

  /**
   * Must be in range [1, 100].
   */
  public val module_inventory_width: UInt by fromJson()

  /**
   * Must be >= 1.
   */
  public val tooltip_monitor_edge_border: Int by fromJson()

  /**
   * Must be >= 1.
   */
  public val normalised_achievement_icon_size: UInt by fromJson()

  /**
   * Must be >= 1.
   */
  public val tutorial_notice_icon_size: UInt by fromJson()

  /**
   * Must be >= 1
   */
  public val flying_text_ttl: UInt by fromJson()

  /**
   * The base game uses more entries here that are applied via the [ammo
   * categories](https://github.com/wube/factorio-data/blob/master/base/prototypes/categories/ammo-category.lua#L72-L76).
   */
  public val bonus_gui_ordering: BonusGuiOrdering by fromJson()

  public val train_path_finding: TrainPathFinderConstants by fromJson()

  public val map_editor: MapEditorConstants by fromJson()

  public val color_filters: List<ColorFilterData> by fromJson()

  public val entity_renderer_search_box_limits: EntityRendererSearchBoxLimits by fromJson()

  /**
   * Can be set to anything from range 0 to 255, but larger values will be clamped to 160. Setting
   * it to larger values can have performance impact (growing geometrically).
   */
  public val light_renderer_search_distance_limit: UByte by fromJson()

  public object Serializer : JsonReaderSerializer<UtilityConstants>(UtilityConstants::class)
}

/**
 * Sounds used by the game that are not specific to certain prototypes.
 */
@Serializable(UtilitySounds.Serializer::class)
@SerialName("utility-sounds")
public open class UtilitySounds : PrototypeBase() {
  public val gui_click: Sound by fromJson()

  public val list_box_click: Sound by fromJson()

  public val build_small: Sound by fromJson()

  public val build_medium: Sound by fromJson()

  public val build_large: Sound by fromJson()

  public val cannot_build: Sound by fromJson()

  public val build_blueprint_small: Sound by fromJson()

  public val build_blueprint_medium: Sound by fromJson()

  public val build_blueprint_large: Sound by fromJson()

  public val deconstruct_small: Sound by fromJson()

  public val deconstruct_medium: Sound by fromJson()

  public val deconstruct_big: Sound by fromJson()

  public val deconstruct_robot: Sound by fromJson()

  public val rotated_small: Sound by fromJson()

  public val rotated_medium: Sound by fromJson()

  public val rotated_big: Sound by fromJson()

  public val axe_mining_ore: Sound by fromJson()

  public val mining_wood: Sound by fromJson()

  public val axe_fighting: Sound by fromJson()

  public val alert_destroyed: Sound by fromJson()

  public val console_message: Sound by fromJson()

  public val scenario_message: Sound by fromJson()

  public val new_objective: Sound by fromJson()

  public val game_lost: Sound by fromJson()

  public val game_won: Sound by fromJson()

  public val metal_walking_sound: Sound by fromJson()

  public val research_completed: Sound by fromJson()

  public val default_manual_repair: Sound by fromJson()

  public val crafting_finished: Sound by fromJson()

  public val inventory_click: Sound by fromJson()

  public val inventory_move: Sound by fromJson()

  public val clear_cursor: Sound by fromJson()

  public val armor_insert: Sound by fromJson()

  public val armor_remove: Sound by fromJson()

  public val achievement_unlocked: Sound by fromJson()

  public val wire_connect_pole: Sound by fromJson()

  public val wire_disconnect: Sound by fromJson()

  public val wire_pickup: Sound by fromJson()

  public val tutorial_notice: Sound by fromJson()

  public val smart_pipette: Sound by fromJson()

  public val switch_gun: Sound by fromJson()

  public val picked_up_item: Sound by fromJson()

  public val blueprint_selection_ended: Sound by fromJson()

  public val blueprint_selection_started: Sound by fromJson()

  public val deconstruction_selection_started: Sound by fromJson()

  public val deconstruction_selection_ended: Sound by fromJson()

  public val cancel_deconstruction_selection_started: Sound by fromJson()

  public val cancel_deconstruction_selection_ended: Sound by fromJson()

  public val upgrade_selection_started: Sound by fromJson()

  public val upgrade_selection_ended: Sound by fromJson()

  public val copy_activated: Sound by fromJson()

  public val cut_activated: Sound by fromJson()

  public val paste_activated: Sound by fromJson()

  public val item_deleted: Sound by fromJson()

  public val entity_settings_pasted: Sound by fromJson()

  public val entity_settings_copied: Sound by fromJson()

  public val item_spawned: Sound by fromJson()

  public val confirm: Sound by fromJson()

  public val undo: Sound by fromJson()

  public val drop_item: Sound by fromJson()

  public val rail_plan_start: Sound by fromJson()

  public object Serializer : JsonReaderSerializer<UtilitySounds>(UtilitySounds::class)
}

/**
 * Sprites used by the game that are not specific to certain prototypes.
 */
@Serializable(UtilitySprites.Serializer::class)
@SerialName("utility-sprites")
public open class UtilitySprites : PrototypeBase() {
  public val cursor_box: CursorBoxSpecification by fromJson()

  public val bookmark: Sprite by fromJson()

  public val center: Sprite by fromJson()

  public val check_mark: Sprite by fromJson()

  public val check_mark_white: Sprite by fromJson()

  public val check_mark_green: Sprite by fromJson()

  public val check_mark_dark_green: Sprite by fromJson()

  public val not_played_yet_green: Sprite by fromJson()

  public val not_played_yet_dark_green: Sprite by fromJson()

  public val played_green: Sprite by fromJson()

  public val played_dark_green: Sprite by fromJson()

  public val close_fat: Sprite by fromJson()

  public val close_white: Sprite by fromJson()

  public val close_black: Sprite by fromJson()

  public val close_map_preview: Sprite by fromJson()

  public val color_picker: Sprite by fromJson()

  public val change_recipe: Sprite by fromJson()

  public val dropdown: Sprite by fromJson()

  public val downloading: Sprite by fromJson()

  public val downloading_white: Sprite by fromJson()

  public val downloaded: Sprite by fromJson()

  public val downloaded_white: Sprite by fromJson()

  public val equipment_grid: Sprite by fromJson()

  public val expand_dots: Sprite by fromJson()

  public val expand_dots_white: Sprite by fromJson()

  public val export: Sprite by fromJson()

  public val `import`: Sprite by fromJson()

  public val map: Sprite by fromJson()

  public val map_exchange_string: Sprite by fromJson()

  public val missing_mod_icon: Sprite by fromJson()

  public val not_available: Sprite by fromJson()

  public val play: Sprite by fromJson()

  public val stop: Sprite by fromJson()

  public val preset: Sprite by fromJson()

  public val refresh: Sprite by fromJson()

  public val reset: Sprite by fromJson()

  public val reset_white: Sprite by fromJson()

  public val shuffle: Sprite by fromJson()

  public val station_name: Sprite by fromJson()

  public val search_black: Sprite by fromJson()

  public val search_white: Sprite by fromJson()

  public val sync_mods: Sprite by fromJson()

  public val trash: Sprite by fromJson()

  public val trash_white: Sprite by fromJson()

  public val copy: Sprite by fromJson()

  public val reassign: Sprite by fromJson()

  public val warning: Sprite by fromJson()

  public val warning_white: Sprite by fromJson()

  public val list_view: Sprite by fromJson()

  public val grid_view: Sprite by fromJson()

  public val reference_point: Sprite by fromJson()

  public val mouse_cursor: Sprite by fromJson()

  public val mod_dependency_arrow: Sprite by fromJson()

  public val add: Sprite by fromJson()

  public val clone: Sprite by fromJson()

  public val go_to_arrow: Sprite by fromJson()

  public val pause: Sprite by fromJson()

  public val speed_down: Sprite by fromJson()

  public val speed_up: Sprite by fromJson()

  public val editor_speed_down: Sprite by fromJson()

  public val editor_pause: Sprite by fromJson()

  public val editor_play: Sprite by fromJson()

  public val editor_speed_up: Sprite by fromJson()

  public val tick_once: Sprite by fromJson()

  public val tick_sixty: Sprite by fromJson()

  public val tick_custom: Sprite by fromJson()

  public val search_icon: Sprite by fromJson()

  public val too_far: Sprite by fromJson()

  public val shoot_cursor_green: Sprite by fromJson()

  public val shoot_cursor_red: Sprite by fromJson()

  public val electricity_icon: Sprite by fromJson()

  public val fuel_icon: Sprite by fromJson()

  public val ammo_icon: Sprite by fromJson()

  /**
   * The sprite will be drawn on top of [fluid turrets](prototype:FluidTurretPrototype) that are out
   * of fluid ammunition and don't have
   * [FluidTurretPrototype::out_of_ammo_alert_icon](prototype:FluidTurretPrototype::out_of_ammo_alert_icon)
   * set.
   */
  public val fluid_icon: Sprite by fromJson()

  public val warning_icon: Sprite by fromJson()

  public val danger_icon: Sprite by fromJson()

  public val destroyed_icon: Sprite by fromJson()

  public val recharge_icon: Sprite by fromJson()

  public val too_far_from_roboport_icon: Sprite by fromJson()

  public val pump_cannot_connect_icon: Sprite by fromJson()

  public val not_enough_repair_packs_icon: Sprite by fromJson()

  public val not_enough_construction_robots_icon: Sprite by fromJson()

  public val no_building_material_icon: Sprite by fromJson()

  public val no_storage_space_icon: Sprite by fromJson()

  public val electricity_icon_unplugged: Sprite by fromJson()

  public val game_stopped_visualization: Sprite by fromJson()

  public val health_bar_green_pip: Sprite by fromJson()

  public val health_bar_yellow_pip: Sprite by fromJson()

  public val health_bar_red_pip: Sprite by fromJson()

  public val ghost_bar_pip: Sprite by fromJson()

  public val bar_gray_pip: Sprite by fromJson()

  public val shield_bar_pip: Sprite by fromJson()

  public val hand: Sprite by fromJson()

  public val hand_black: Sprite by fromJson()

  public val entity_info_dark_background: Sprite by fromJson()

  public val medium_gui_arrow: Sprite by fromJson()

  public val small_gui_arrow: Sprite by fromJson()

  public val light_medium: Sprite by fromJson()

  public val light_small: Sprite by fromJson()

  public val light_cone: Sprite by fromJson()

  public val color_effect: Sprite by fromJson()

  public val clock: Sprite by fromJson()

  public val default_ammo_damage_modifier_icon: Sprite by fromJson()

  public val default_gun_speed_modifier_icon: Sprite by fromJson()

  public val default_turret_attack_modifier_icon: Sprite by fromJson()

  public val hint_arrow_up: Sprite by fromJson()

  public val hint_arrow_down: Sprite by fromJson()

  public val hint_arrow_right: Sprite by fromJson()

  public val hint_arrow_left: Sprite by fromJson()

  public val fluid_indication_arrow: Sprite by fromJson()

  public val fluid_indication_arrow_both_ways: Sprite by fromJson()

  public val heat_exchange_indication: Sprite by fromJson()

  public val indication_arrow: Sprite by fromJson()

  public val rail_planner_indication_arrow: Sprite by fromJson()

  public val rail_planner_indication_arrow_too_far: Sprite by fromJson()

  public val rail_path_not_possible: Sprite by fromJson()

  public val indication_line: Sprite by fromJson()

  public val short_indication_line: Sprite by fromJson()

  public val short_indication_line_green: Sprite by fromJson()

  public val slot_icon_module: Sprite by fromJson()

  public val slot_icon_module_black: Sprite by fromJson()

  public val slot_icon_armor: Sprite by fromJson()

  public val slot_icon_armor_black: Sprite by fromJson()

  public val slot_icon_gun: Sprite by fromJson()

  public val slot_icon_gun_black: Sprite by fromJson()

  public val slot_icon_ammo: Sprite by fromJson()

  public val slot_icon_ammo_black: Sprite by fromJson()

  public val slot_icon_resource: Sprite by fromJson()

  public val slot_icon_resource_black: Sprite by fromJson()

  public val slot_icon_fuel: Sprite by fromJson()

  public val slot_icon_fuel_black: Sprite by fromJson()

  public val slot_icon_result: Sprite by fromJson()

  public val slot_icon_result_black: Sprite by fromJson()

  public val slot_icon_robot: Sprite by fromJson()

  public val slot_icon_robot_black: Sprite by fromJson()

  public val slot_icon_robot_material: Sprite by fromJson()

  public val slot_icon_robot_material_black: Sprite by fromJson()

  public val slot_icon_inserter_hand: Sprite by fromJson()

  public val slot_icon_inserter_hand_black: Sprite by fromJson()

  public val upgrade_blueprint: Sprite by fromJson()

  public val slot: Sprite by fromJson()

  public val equipment_slot: Sprite by fromJson()

  public val equipment_collision: Sprite by fromJson()

  public val battery: Sprite by fromJson()

  public val green_circle: Sprite by fromJson()

  public val green_dot: Sprite by fromJson()

  public val robot_slot: Sprite by fromJson()

  public val set_bar_slot: Sprite by fromJson()

  public val missing_icon: Sprite by fromJson()

  public val deconstruction_mark: Sprite by fromJson()

  public val upgrade_mark: Sprite by fromJson()

  public val confirm_slot: Sprite by fromJson()

  public val export_slot: Sprite by fromJson()

  public val import_slot: Sprite by fromJson()

  public val none_editor_icon: Sprite by fromJson()

  public val cable_editor_icon: Sprite by fromJson()

  public val tile_editor_icon: Sprite by fromJson()

  public val decorative_editor_icon: Sprite by fromJson()

  public val resource_editor_icon: Sprite by fromJson()

  public val entity_editor_icon: Sprite by fromJson()

  public val item_editor_icon: Sprite by fromJson()

  public val force_editor_icon: Sprite by fromJson()

  public val clone_editor_icon: Sprite by fromJson()

  public val scripting_editor_icon: Sprite by fromJson()

  public val paint_bucket_icon: Sprite by fromJson()

  public val surface_editor_icon: Sprite by fromJson()

  public val time_editor_icon: Sprite by fromJson()

  public val cliff_editor_icon: Sprite by fromJson()

  public val brush_icon: Sprite by fromJson()

  public val spray_icon: Sprite by fromJson()

  public val cursor_icon: Sprite by fromJson()

  public val area_icon: Sprite by fromJson()

  public val line_icon: Sprite by fromJson()

  public val variations_tool_icon: Sprite by fromJson()

  public val lua_snippet_tool_icon: Sprite by fromJson()

  public val editor_selection: Sprite by fromJson()

  public val brush_square_shape: Sprite by fromJson()

  public val brush_circle_shape: Sprite by fromJson()

  public val player_force_icon: Sprite by fromJson()

  public val neutral_force_icon: Sprite by fromJson()

  public val enemy_force_icon: Sprite by fromJson()

  public val nature_icon: Sprite by fromJson()

  public val no_nature_icon: Sprite by fromJson()

  public val multiplayer_waiting_icon: Sprite by fromJson()

  public val spawn_flag: Sprite by fromJson()

  public val questionmark: Sprite by fromJson()

  public val copper_wire: Sprite by fromJson()

  public val green_wire: Sprite by fromJson()

  public val red_wire: Sprite by fromJson()

  public val green_wire_hightlight: Sprite by fromJson()

  public val red_wire_hightlight: Sprite by fromJson()

  public val wire_shadow: Sprite by fromJson()

  public val and_or: Sprite by fromJson()

  public val left_arrow: Sprite by fromJson()

  public val right_arrow: Sprite by fromJson()

  public val down_arrow: Sprite by fromJson()

  public val enter: Sprite by fromJson()

  public val side_menu_blueprint_library_icon: Sprite by fromJson()

  public val side_menu_production_icon: Sprite by fromJson()

  public val side_menu_bonus_icon: Sprite by fromJson()

  public val side_menu_tutorials_icon: Sprite by fromJson()

  public val side_menu_train_icon: Sprite by fromJson()

  public val side_menu_achievements_icon: Sprite by fromJson()

  public val side_menu_menu_icon: Sprite by fromJson()

  public val side_menu_map_icon: Sprite by fromJson()

  public val side_menu_blueprint_library_hover_icon: Sprite by fromJson()

  public val side_menu_production_hover_icon: Sprite by fromJson()

  public val side_menu_bonus_hover_icon: Sprite by fromJson()

  public val side_menu_tutorials_hover_icon: Sprite by fromJson()

  public val side_menu_train_hover_icon: Sprite by fromJson()

  public val side_menu_achievements_hover_icon: Sprite by fromJson()

  public val side_menu_menu_hover_icon: Sprite by fromJson()

  public val side_menu_map_hover_icon: Sprite by fromJson()

  public val side_menu_technology_hover_icon: Sprite by fromJson()

  public val side_menu_logistic_network_hover_icon: Sprite by fromJson()

  public val circuit_network_panel_black: Sprite by fromJson()

  public val circuit_network_panel_white: Sprite by fromJson()

  public val logistic_network_panel_black: Sprite by fromJson()

  public val logistic_network_panel_white: Sprite by fromJson()

  public val rename_icon_small_black: Sprite by fromJson()

  public val rename_icon_small_white: Sprite by fromJson()

  public val rename_icon_normal: Sprite by fromJson()

  public val achievement_label_locked: Sprite by fromJson()

  public val achievement_label_unlocked_off: Sprite by fromJson()

  public val achievement_label_unlocked: Sprite by fromJson()

  public val achievement_label_failed: Sprite by fromJson()

  public val rail_signal_placement_indicator: Sprite by fromJson()

  public val train_stop_placement_indicator: Sprite by fromJson()

  public val placement_indicator_leg: Sprite by fromJson()

  public val grey_rail_signal_placement_indicator: Sprite by fromJson()

  public val grey_placement_indicator_leg: Sprite by fromJson()

  public val logistic_radius_visualization: Sprite by fromJson()

  public val construction_radius_visualization: Sprite by fromJson()

  public val track_button: Sprite by fromJson()

  public val show_logistics_network_in_map_view: Sprite by fromJson()

  public val show_electric_network_in_map_view: Sprite by fromJson()

  public val show_turret_range_in_map_view: Sprite by fromJson()

  public val show_pollution_in_map_view: Sprite by fromJson()

  public val show_train_station_names_in_map_view: Sprite by fromJson()

  public val show_player_names_in_map_view: Sprite by fromJson()

  public val show_tags_in_map_view: Sprite by fromJson()

  public val show_worker_robots_in_map_view: Sprite by fromJson()

  public val show_rail_signal_states_in_map_view: Sprite by fromJson()

  public val show_recipe_icons_in_map_view: Sprite by fromJson()

  public val show_logistics_network_in_map_view_black: Sprite by fromJson()

  public val show_electric_network_in_map_view_black: Sprite by fromJson()

  public val show_turret_range_in_map_view_black: Sprite by fromJson()

  public val show_pollution_in_map_view_black: Sprite by fromJson()

  public val show_train_station_names_in_map_view_black: Sprite by fromJson()

  public val show_player_names_in_map_view_black: Sprite by fromJson()

  public val show_tags_in_map_view_black: Sprite by fromJson()

  public val show_worker_robots_in_map_view_black: Sprite by fromJson()

  public val show_rail_signal_states_in_map_view_black: Sprite by fromJson()

  public val show_recipe_icons_in_map_view_black: Sprite by fromJson()

  public val train_stop_in_map_view: Sprite by fromJson()

  public val train_stop_disabled_in_map_view: Sprite by fromJson()

  public val train_stop_full_in_map_view: Sprite by fromJson()

  public val custom_tag_in_map_view: Sprite by fromJson()

  public val covered_chunk: Sprite by fromJson()

  public val white_square: Sprite by fromJson()

  public val white_mask: Sprite by fromJson()

  public val favourite_server_icon: Sprite by fromJson()

  public val crafting_machine_recipe_not_unlocked: Sprite by fromJson()

  public val gps_map_icon: Sprite by fromJson()

  public val custom_tag_icon: Sprite by fromJson()

  public val underground_remove_belts: Sprite by fromJson()

  public val underground_remove_pipes: Sprite by fromJson()

  public val underground_pipe_connection: Sprite by fromJson()

  public val ghost_cursor: Sprite by fromJson()

  public val tile_ghost_cursor: Sprite by fromJson()

  public val cross_select: Sprite by fromJson()

  public val expand: Sprite by fromJson()

  public val expand_dark: Sprite by fromJson()

  public val collapse: Sprite by fromJson()

  public val collapse_dark: Sprite by fromJson()

  public val status_working: Sprite by fromJson()

  public val status_not_working: Sprite by fromJson()

  public val status_yellow: Sprite by fromJson()

  public val gradient: Sprite by fromJson()

  public val output_console_gradient: Sprite by fromJson()

  public val select_icon_black: Sprite by fromJson()

  public val select_icon_white: Sprite by fromJson()

  public val notification: Sprite by fromJson()

  public val alert_arrow: Sprite by fromJson()

  public val technology_black: Sprite by fromJson()

  public val technology_white: Sprite by fromJson()

  public val controller_joycon_a: Sprite by fromJson()

  public val controller_joycon_b: Sprite by fromJson()

  public val controller_joycon_x: Sprite by fromJson()

  public val controller_joycon_y: Sprite by fromJson()

  public val controller_joycon_back: Sprite by fromJson()

  public val controller_joycon_start: Sprite by fromJson()

  public val controller_joycon_leftstick: Sprite by fromJson()

  public val controller_joycon_rightstick: Sprite by fromJson()

  public val controller_joycon_leftshoulder: Sprite by fromJson()

  public val controller_joycon_rightshoulder: Sprite by fromJson()

  public val controller_joycon_dpup: Sprite by fromJson()

  public val controller_joycon_dpdown: Sprite by fromJson()

  public val controller_joycon_dpleft: Sprite by fromJson()

  public val controller_joycon_dpright: Sprite by fromJson()

  public val controller_joycon_paddle1: Sprite by fromJson()

  public val controller_joycon_paddle2: Sprite by fromJson()

  public val controller_joycon_paddle3: Sprite by fromJson()

  public val controller_joycon_paddle4: Sprite by fromJson()

  public val controller_joycon_righttrigger: Sprite by fromJson()

  public val controller_joycon_lefttrigger: Sprite by fromJson()

  public val controller_joycon_left_stick: Sprite by fromJson()

  public val controller_joycon_right_stick: Sprite by fromJson()

  public val controller_joycon_black_a: Sprite by fromJson()

  public val controller_joycon_black_b: Sprite by fromJson()

  public val controller_joycon_black_x: Sprite by fromJson()

  public val controller_joycon_black_y: Sprite by fromJson()

  public val controller_joycon_black_back: Sprite by fromJson()

  public val controller_joycon_black_start: Sprite by fromJson()

  public val controller_joycon_black_leftstick: Sprite by fromJson()

  public val controller_joycon_black_rightstick: Sprite by fromJson()

  public val controller_joycon_black_leftshoulder: Sprite by fromJson()

  public val controller_joycon_black_rightshoulder: Sprite by fromJson()

  public val controller_joycon_black_dpup: Sprite by fromJson()

  public val controller_joycon_black_dpdown: Sprite by fromJson()

  public val controller_joycon_black_dpleft: Sprite by fromJson()

  public val controller_joycon_black_dpright: Sprite by fromJson()

  public val controller_joycon_black_paddle1: Sprite by fromJson()

  public val controller_joycon_black_paddle2: Sprite by fromJson()

  public val controller_joycon_black_paddle3: Sprite by fromJson()

  public val controller_joycon_black_paddle4: Sprite by fromJson()

  public val controller_joycon_black_righttrigger: Sprite by fromJson()

  public val controller_joycon_black_lefttrigger: Sprite by fromJson()

  public val controller_joycon_black_left_stick: Sprite by fromJson()

  public val controller_joycon_black_right_stick: Sprite by fromJson()

  public val controller_xbox_a: Sprite by fromJson()

  public val controller_xbox_b: Sprite by fromJson()

  public val controller_xbox_x: Sprite by fromJson()

  public val controller_xbox_y: Sprite by fromJson()

  public val controller_xbox_back: Sprite by fromJson()

  public val controller_xbox_start: Sprite by fromJson()

  public val controller_xbox_leftstick: Sprite by fromJson()

  public val controller_xbox_rightstick: Sprite by fromJson()

  public val controller_xbox_leftshoulder: Sprite by fromJson()

  public val controller_xbox_rightshoulder: Sprite by fromJson()

  public val controller_xbox_dpup: Sprite by fromJson()

  public val controller_xbox_dpdown: Sprite by fromJson()

  public val controller_xbox_dpleft: Sprite by fromJson()

  public val controller_xbox_dpright: Sprite by fromJson()

  public val controller_xbox_righttrigger: Sprite by fromJson()

  public val controller_xbox_lefttrigger: Sprite by fromJson()

  public val controller_xbox_left_stick: Sprite by fromJson()

  public val controller_xbox_right_stick: Sprite by fromJson()

  public val controller_xbox_black_a: Sprite by fromJson()

  public val controller_xbox_black_b: Sprite by fromJson()

  public val controller_xbox_black_x: Sprite by fromJson()

  public val controller_xbox_black_y: Sprite by fromJson()

  public val controller_xbox_black_back: Sprite by fromJson()

  public val controller_xbox_black_start: Sprite by fromJson()

  public val controller_xbox_black_leftstick: Sprite by fromJson()

  public val controller_xbox_black_rightstick: Sprite by fromJson()

  public val controller_xbox_black_leftshoulder: Sprite by fromJson()

  public val controller_xbox_black_rightshoulder: Sprite by fromJson()

  public val controller_xbox_black_dpup: Sprite by fromJson()

  public val controller_xbox_black_dpdown: Sprite by fromJson()

  public val controller_xbox_black_dpleft: Sprite by fromJson()

  public val controller_xbox_black_dpright: Sprite by fromJson()

  public val controller_xbox_black_righttrigger: Sprite by fromJson()

  public val controller_xbox_black_lefttrigger: Sprite by fromJson()

  public val controller_xbox_black_left_stick: Sprite by fromJson()

  public val controller_xbox_black_right_stick: Sprite by fromJson()

  public val controller_ps_a: Sprite by fromJson()

  public val controller_ps_b: Sprite by fromJson()

  public val controller_ps_x: Sprite by fromJson()

  public val controller_ps_y: Sprite by fromJson()

  public val controller_ps_back: Sprite by fromJson()

  public val controller_ps_start: Sprite by fromJson()

  public val controller_ps_leftstick: Sprite by fromJson()

  public val controller_ps_rightstick: Sprite by fromJson()

  public val controller_ps_leftshoulder: Sprite by fromJson()

  public val controller_ps_rightshoulder: Sprite by fromJson()

  public val controller_ps_dpup: Sprite by fromJson()

  public val controller_ps_dpdown: Sprite by fromJson()

  public val controller_ps_dpleft: Sprite by fromJson()

  public val controller_ps_dpright: Sprite by fromJson()

  public val controller_ps_righttrigger: Sprite by fromJson()

  public val controller_ps_lefttrigger: Sprite by fromJson()

  public val controller_ps_left_stick: Sprite by fromJson()

  public val controller_ps_right_stick: Sprite by fromJson()

  public val controller_ps_black_a: Sprite by fromJson()

  public val controller_ps_black_b: Sprite by fromJson()

  public val controller_ps_black_x: Sprite by fromJson()

  public val controller_ps_black_y: Sprite by fromJson()

  public val controller_ps_black_back: Sprite by fromJson()

  public val controller_ps_black_start: Sprite by fromJson()

  public val controller_ps_black_leftstick: Sprite by fromJson()

  public val controller_ps_black_rightstick: Sprite by fromJson()

  public val controller_ps_black_leftshoulder: Sprite by fromJson()

  public val controller_ps_black_rightshoulder: Sprite by fromJson()

  public val controller_ps_black_dpup: Sprite by fromJson()

  public val controller_ps_black_dpdown: Sprite by fromJson()

  public val controller_ps_black_dpleft: Sprite by fromJson()

  public val controller_ps_black_dpright: Sprite by fromJson()

  public val controller_ps_black_righttrigger: Sprite by fromJson()

  public val controller_ps_black_lefttrigger: Sprite by fromJson()

  public val controller_ps_black_left_stick: Sprite by fromJson()

  public val controller_ps_black_right_stick: Sprite by fromJson()

  public val controller_steamdeck_a: Sprite by fromJson()

  public val controller_steamdeck_b: Sprite by fromJson()

  public val controller_steamdeck_x: Sprite by fromJson()

  public val controller_steamdeck_y: Sprite by fromJson()

  public val controller_steamdeck_back: Sprite by fromJson()

  public val controller_steamdeck_start: Sprite by fromJson()

  public val controller_steamdeck_leftstick: Sprite by fromJson()

  public val controller_steamdeck_rightstick: Sprite by fromJson()

  public val controller_steamdeck_leftshoulder: Sprite by fromJson()

  public val controller_steamdeck_rightshoulder: Sprite by fromJson()

  public val controller_steamdeck_dpup: Sprite by fromJson()

  public val controller_steamdeck_dpdown: Sprite by fromJson()

  public val controller_steamdeck_dpleft: Sprite by fromJson()

  public val controller_steamdeck_dpright: Sprite by fromJson()

  public val controller_steamdeck_paddle1: Sprite by fromJson()

  public val controller_steamdeck_paddle2: Sprite by fromJson()

  public val controller_steamdeck_paddle3: Sprite by fromJson()

  public val controller_steamdeck_paddle4: Sprite by fromJson()

  public val controller_steamdeck_righttrigger: Sprite by fromJson()

  public val controller_steamdeck_lefttrigger: Sprite by fromJson()

  public val controller_steamdeck_left_stick: Sprite by fromJson()

  public val controller_steamdeck_right_stick: Sprite by fromJson()

  public val controller_steamdeck_black_a: Sprite by fromJson()

  public val controller_steamdeck_black_b: Sprite by fromJson()

  public val controller_steamdeck_black_x: Sprite by fromJson()

  public val controller_steamdeck_black_y: Sprite by fromJson()

  public val controller_steamdeck_black_back: Sprite by fromJson()

  public val controller_steamdeck_black_start: Sprite by fromJson()

  public val controller_steamdeck_black_leftstick: Sprite by fromJson()

  public val controller_steamdeck_black_rightstick: Sprite by fromJson()

  public val controller_steamdeck_black_leftshoulder: Sprite by fromJson()

  public val controller_steamdeck_black_rightshoulder: Sprite by fromJson()

  public val controller_steamdeck_black_dpup: Sprite by fromJson()

  public val controller_steamdeck_black_dpdown: Sprite by fromJson()

  public val controller_steamdeck_black_dpleft: Sprite by fromJson()

  public val controller_steamdeck_black_dpright: Sprite by fromJson()

  public val controller_steamdeck_black_paddle1: Sprite by fromJson()

  public val controller_steamdeck_black_paddle2: Sprite by fromJson()

  public val controller_steamdeck_black_paddle3: Sprite by fromJson()

  public val controller_steamdeck_black_paddle4: Sprite by fromJson()

  public val controller_steamdeck_black_righttrigger: Sprite by fromJson()

  public val controller_steamdeck_black_lefttrigger: Sprite by fromJson()

  public val controller_steamdeck_black_left_stick: Sprite by fromJson()

  public val controller_steamdeck_black_right_stick: Sprite by fromJson()

  public val clouds: Animation by fromJson()

  public val arrow_button: Animation by fromJson()

  public val explosion_chart_visualization: Animation by fromJson()

  public val refresh_white: Animation by fromJson()

  public val inserter_stack_size_bonus_modifier_icon: Sprite by fromJson()

  public val inserter_stack_size_bonus_modifier_constant: Sprite? by fromJson()

  public val stack_inserter_capacity_bonus_modifier_icon: Sprite by fromJson()

  public val stack_inserter_capacity_bonus_modifier_constant: Sprite? by fromJson()

  public val laboratory_speed_modifier_icon: Sprite by fromJson()

  public val laboratory_speed_modifier_constant: Sprite? by fromJson()

  public val character_logistic_slots_modifier_icon: Sprite by fromJson()

  public val character_logistic_slots_modifier_constant: Sprite? by fromJson()

  public val character_logistic_trash_slots_modifier_icon: Sprite by fromJson()

  public val character_logistic_trash_slots_modifier_constant: Sprite? by fromJson()

  public val maximum_following_robots_count_modifier_icon: Sprite by fromJson()

  public val maximum_following_robots_count_modifier_constant: Sprite? by fromJson()

  public val worker_robot_speed_modifier_icon: Sprite by fromJson()

  public val worker_robot_speed_modifier_constant: Sprite? by fromJson()

  public val worker_robot_storage_modifier_icon: Sprite by fromJson()

  public val worker_robot_storage_modifier_constant: Sprite? by fromJson()

  public val ghost_time_to_live_modifier_icon: Sprite by fromJson()

  public val ghost_time_to_live_modifier_constant: Sprite? by fromJson()

  public val turret_attack_modifier_icon: Sprite by fromJson()

  public val turret_attack_modifier_constant: Sprite? by fromJson()

  public val ammo_damage_modifier_icon: Sprite by fromJson()

  public val ammo_damage_modifier_constant: Sprite? by fromJson()

  public val give_item_modifier_icon: Sprite by fromJson()

  public val give_item_modifier_constant: Sprite? by fromJson()

  public val gun_speed_modifier_icon: Sprite by fromJson()

  public val gun_speed_modifier_constant: Sprite? by fromJson()

  public val unlock_recipe_modifier_icon: Sprite by fromJson()

  public val unlock_recipe_modifier_constant: Sprite? by fromJson()

  public val character_crafting_speed_modifier_icon: Sprite by fromJson()

  public val character_crafting_speed_modifier_constant: Sprite? by fromJson()

  public val character_mining_speed_modifier_icon: Sprite by fromJson()

  public val character_mining_speed_modifier_constant: Sprite? by fromJson()

  public val character_running_speed_modifier_icon: Sprite by fromJson()

  public val character_running_speed_modifier_constant: Sprite? by fromJson()

  public val character_build_distance_modifier_icon: Sprite by fromJson()

  public val character_build_distance_modifier_constant: Sprite? by fromJson()

  public val character_item_drop_distance_modifier_icon: Sprite by fromJson()

  public val character_item_drop_distance_modifier_constant: Sprite? by fromJson()

  public val character_reach_distance_modifier_icon: Sprite by fromJson()

  public val character_reach_distance_modifier_constant: Sprite? by fromJson()

  public val character_resource_reach_distance_modifier_icon: Sprite by fromJson()

  public val character_resource_reach_distance_modifier_constant: Sprite? by fromJson()

  public val character_item_pickup_distance_modifier_icon: Sprite by fromJson()

  public val character_item_pickup_distance_modifier_constant: Sprite? by fromJson()

  public val character_loot_pickup_distance_modifier_icon: Sprite by fromJson()

  public val character_loot_pickup_distance_modifier_constant: Sprite? by fromJson()

  public val character_inventory_slots_bonus_modifier_icon: Sprite by fromJson()

  public val character_inventory_slots_bonus_modifier_constant: Sprite? by fromJson()

  public val deconstruction_time_to_live_modifier_icon: Sprite by fromJson()

  public val deconstruction_time_to_live_modifier_constant: Sprite? by fromJson()

  public val max_failed_attempts_per_tick_per_construction_queue_modifier_icon: Sprite by fromJson()

  public val max_failed_attempts_per_tick_per_construction_queue_modifier_constant: Sprite? by
      fromJson()

  public val max_successful_attempts_per_tick_per_construction_queue_modifier_icon: Sprite by
      fromJson()

  public val max_successful_attempts_per_tick_per_construction_queue_modifier_constant: Sprite? by
      fromJson()

  public val character_health_bonus_modifier_icon: Sprite by fromJson()

  public val character_health_bonus_modifier_constant: Sprite? by fromJson()

  public val mining_drill_productivity_bonus_modifier_icon: Sprite by fromJson()

  public val mining_drill_productivity_bonus_modifier_constant: Sprite? by fromJson()

  public val train_braking_force_bonus_modifier_icon: Sprite by fromJson()

  public val train_braking_force_bonus_modifier_constant: Sprite? by fromJson()

  public val zoom_to_world_enabled_modifier_icon: Sprite by fromJson()

  public val zoom_to_world_enabled_modifier_constant: Sprite? by fromJson()

  public val zoom_to_world_ghost_building_enabled_modifier_icon: Sprite by fromJson()

  public val zoom_to_world_ghost_building_enabled_modifier_constant: Sprite? by fromJson()

  public val zoom_to_world_blueprint_enabled_modifier_icon: Sprite by fromJson()

  public val zoom_to_world_blueprint_enabled_modifier_constant: Sprite? by fromJson()

  public val zoom_to_world_deconstruction_planner_enabled_modifier_icon: Sprite by fromJson()

  public val zoom_to_world_deconstruction_planner_enabled_modifier_constant: Sprite? by fromJson()

  public val zoom_to_world_upgrade_planner_enabled_modifier_icon: Sprite by fromJson()

  public val zoom_to_world_upgrade_planner_enabled_modifier_constant: Sprite? by fromJson()

  public val zoom_to_world_selection_tool_enabled_modifier_icon: Sprite by fromJson()

  public val zoom_to_world_selection_tool_enabled_modifier_constant: Sprite? by fromJson()

  public val worker_robot_battery_modifier_icon: Sprite by fromJson()

  public val worker_robot_battery_modifier_constant: Sprite? by fromJson()

  public val laboratory_productivity_modifier_icon: Sprite by fromJson()

  public val laboratory_productivity_modifier_constant: Sprite? by fromJson()

  public val follower_robot_lifetime_modifier_icon: Sprite by fromJson()

  public val follower_robot_lifetime_modifier_constant: Sprite? by fromJson()

  public val artillery_range_modifier_icon: Sprite by fromJson()

  public val artillery_range_modifier_constant: Sprite? by fromJson()

  public val nothing_modifier_icon: Sprite by fromJson()

  public val nothing_modifier_constant: Sprite? by fromJson()

  public val character_additional_mining_categories_modifier_icon: Sprite by fromJson()

  public val character_additional_mining_categories_modifier_constant: Sprite? by fromJson()

  public val character_logistic_requests_modifier_icon: Sprite by fromJson()

  public val character_logistic_requests_modifier_constant: Sprite? by fromJson()

  public object Serializer : JsonReaderSerializer<UtilitySprites>(UtilitySprites::class)
}

/**
 * Abstract base of all vehicles.
 */
public sealed class VehiclePrototype : EntityWithOwnerPrototype() {
  /**
   * Must be positive. Weight of the entity used for physics calculation when car hits something.
   */
  public val weight: Double by fromJson()

  /**
   * Must be positive. There is no functional difference between the two ways to set braking
   * power/force.
   */
  public val braking_power: UnknownUnion by fromJson("braking_force")

  /**
   * Must be positive. There is no functional difference between the two ways to set friction force.
   */
  public val friction: Double by fromJson("friction_force")

  /**
   * The (movement) energy used per hit point (1 hit point = 1 health damage) taken and dealt for
   * this vehicle during collisions. The smaller the number, the more damage this vehicle and the
   * rammed entity take during collisions: `damage = energy / energy_per_hit_point`.
   */
  public val energy_per_hit_point: Double by fromJson()

  /**
   * Must be in the [0, 1] interval.
   */
  public val terrain_friction_modifier: Float? by fromJson()

  /**
   * Must be positive. Sound is scaled by speed.
   */
  public val sound_minimum_speed: Double? by fromJson()

  /**
   * Must be positive. Sound is scaled by speed.
   */
  public val sound_scaling_ratio: Double? by fromJson()

  public val stop_trigger_speed: Double? by fromJson()

  public val crash_trigger: TriggerEffect? by fromJson()

  public val stop_trigger: TriggerEffect? by fromJson()

  /**
   * The name of the [EquipmentGridPrototype](prototype:EquipmentGridPrototype) this vehicle has.
   */
  public val equipment_grid: EquipmentGridID? by fromJson()

  /**
   * The sprite that represents this vehicle on the map/minimap.
   */
  public val minimap_representation: Sprite? by fromJson()

  /**
   * The sprite that represents this vehicle on the map/minimap when it is selected.
   */
  public val selected_minimap_representation: Sprite? by fromJson()

  /**
   * Determines whether this vehicle accepts passengers. This includes both drivers and gunners, if
   * applicable.
   */
  public val allow_passengers: Boolean? by fromJson()

  /**
   * Two entities can collide only if they share a layer from the collision mask.
   */
  open override val collision_mask: CollisionMask? by fromJson()
}

/**
 * A [virtual signal](https://wiki.factorio.com/Circuit_network#Virtual_signals).
 */
@Serializable(VirtualSignalPrototype.Serializer::class)
@SerialName("virtual-signal")
public open class VirtualSignalPrototype : PrototypeBase() {
  /**
   * The icon that is used to represent this virtual signal. Can't be an empty array.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * Path to the icon file that is used to represent this virtual signal.
   *
   * Mandatory if `icons` is not defined.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Mandatory if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  /**
   * The name of an [ItemSubGroup](prototype:ItemSubGroup).
   */
  public val subgroup: ItemSubGroupID? by fromJson()

  public object Serializer :
      JsonReaderSerializer<VirtualSignalPrototype>(VirtualSignalPrototype::class)
}

/**
 * A [wall](https://wiki.factorio.com/Wall).
 */
@Serializable(WallPrototype.Serializer::class)
@SerialName("wall")
public open class WallPrototype : EntityWithOwnerPrototype() {
  public val pictures: WallPictures by fromJson()

  /**
   * Different walls will visually connect to each other if their merge group is the same.
   */
  public val visual_merge_group: UInt? by fromJson()

  public val circuit_wire_connection_point: WireConnectionPoint? by fromJson()

  /**
   * The maximum circuit wire distance for this entity.
   */
  public val circuit_wire_max_distance: Double? by fromJson()

  public val draw_copper_wires: Boolean? by fromJson()

  public val draw_circuit_wires: Boolean? by fromJson()

  public val circuit_connector_sprites: CircuitConnectorSprites? by fromJson()

  public val default_output_signal: SignalIDConnector? by fromJson()

  public val wall_diode_green: Sprite4Way? by fromJson()

  public val wall_diode_red: Sprite4Way? by fromJson()

  public val wall_diode_green_light_top: LightDefinition? by fromJson()

  public val wall_diode_green_light_right: LightDefinition? by fromJson()

  public val wall_diode_green_light_bottom: LightDefinition? by fromJson()

  public val wall_diode_green_light_left: LightDefinition? by fromJson()

  public val wall_diode_red_light_top: LightDefinition? by fromJson()

  public val wall_diode_red_light_right: LightDefinition? by fromJson()

  public val wall_diode_red_light_bottom: LightDefinition? by fromJson()

  public val wall_diode_red_light_left: LightDefinition? by fromJson()

  public val connected_gate_visualization: Sprite? by fromJson()

  public object Serializer : JsonReaderSerializer<WallPrototype>(WallPrototype::class)
}

/**
 * A wind sound.
 */
@Serializable(WindSound.Serializer::class)
@SerialName("wind-sound")
public open class WindSound : JsonReader(), AnyPrototype {
  /**
   * Specification of the type of the prototype.
   */
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Unique textual identification of the prototype.
   */
  public val name: String by fromJson()

  /**
   * The sound file and volume.
   */
  public val sound: Sound by fromJson()

  public object Serializer : JsonReaderSerializer<WindSound>(WindSound::class)
}

@Serializable(ActivateEquipmentCapsuleAction.Serializer::class)
@SerialName("equipment-remote")
public open class ActivateEquipmentCapsuleAction : JsonReader(), CapsuleAction {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Activation is only implemented for
   * [ActiveDefenseEquipmentPrototype](prototype:ActiveDefenseEquipmentPrototype).
   */
  public val equipment: EquipmentID by fromJson()

  public object Serializer :
      JsonReaderSerializer<ActivateEquipmentCapsuleAction>(ActivateEquipmentCapsuleAction::class)
}

@Serializable(ActivityBarStyleSpecification.Serializer::class)
@SerialName("activity_bar_style")
public open class ActivityBarStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val speed: Float? by fromJson()

  public val bar_width: UInt? by fromJson()

  public val color: Color? by fromJson()

  public val bar_background: Sprite? by fromJson()

  public val bar: Sprite? by fromJson()

  public val bar_size_ratio: Float? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ActivityBarStyleSpecification>(ActivityBarStyleSpecification::class)
}

@Serializable(AdvancedMapGenSettings.Serializer::class)
public open class AdvancedMapGenSettings : JsonReader() {
  public val pollution: MapGenPresetPollutionSettings? by fromJson()

  public val enemy_evolution: MapGenPresetEnemyEvolutionSettings? by fromJson()

  public val enemy_expansion: MapGenPresetEnemyExpansionSettings? by fromJson()

  public val difficulty_settings: MapGenPresetDifficultySettings? by fromJson()

  public object Serializer :
      JsonReaderSerializer<AdvancedMapGenSettings>(AdvancedMapGenSettings::class)
}

@Serializable(AggregationSpecification.Serializer::class)
public open class AggregationSpecification : JsonReader() {
  public val max_count: UInt by fromJson()

  /**
   * If `count_already_playing` is `true`, this will determine maximum progress when instance is
   * counted toward playing sounds.
   */
  public val progress_threshold: Float? by fromJson()

  public val remove: Boolean by fromJson()

  /**
   * If `true`, already playing sounds are taken into account when checking `max_count`.
   */
  public val count_already_playing: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<AggregationSpecification>(AggregationSpecification::class)
}

/**
 * The name of an [AmmoCategory](prototype:AmmoCategory).
 */
public typealias AmmoCategoryID = String

@Serializable(AmmoDamageModifier.Serializer::class)
@SerialName("ammo-damage")
public open class AmmoDamageModifier : BaseModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  /**
   * Name of the [AmmoCategory](prototype:AmmoCategory) that is affected.
   */
  public val ammo_category: AmmoCategoryID by fromJson()

  /**
   * Modification value, which will be added to the current ammo damage modifier upon researching.
   */
  public val modifier: Double by fromJson()

  public object Serializer : JsonReaderSerializer<AmmoDamageModifier>(AmmoDamageModifier::class)
}

/**
 * Used to allow specifying different ammo effects depending on which kind of entity the ammo is
 * used in.
 *
 * If ammo is used in an entity that isn't covered by the defined source_types, e.g. only `"player"`
 * and `"vehicle"` are defined and the ammo is used by a turret, the first defined AmmoType in the
 * [AmmoItemPrototype::ammo_type](prototype:AmmoItemPrototype::ammo_type) array is used.
 */
@Serializable
public enum class AmmoSourceType {
  default,
  player,
  turret,
  vehicle,
}

@Serializable
public enum class AmmoTypeTargetType {
  entity,
  position,
  direction,
}

/**
 * Definition of actual parameters used in attack.
 */
@Serializable(AmmoType.Serializer::class)
public open class AmmoType : JsonReader() {
  /**
   * Name of a [AmmoCategory](prototype:AmmoCategory). Defines whether the attack will be affected
   * by upgrades.
   */
  public val category: AmmoCategoryID by fromJson()

  /**
   * Describes actions taken upon attack happening.
   */
  public val action: Trigger? by fromJson()

  /**
   * When true, the gun will be able to shoot even when the target is out of range. Only applies
   * when `target_type` equals `"position"`. The gun will fire at the maximum range in the direction of
   * the target position.
   */
  public val clamp_position: Boolean? by fromJson()

  /**
   * Energy consumption of a single shot, if applicable.
   */
  public val energy_consumption: Energy? by fromJson()

  /**
   * Affects the `range` value of the shooting gun prototype's
   * [BaseAttackParameters](prototype:BaseAttackParameters) to give a modified maximum range. The
   * `min_range` value of the gun is unaffected.
   *
   * This has no effect on artillery turrets and wagons even though the bonus appears in the GUI.
   * [Forum thread](https://forums.factorio.com/103658).
   */
  public val range_modifier: Double? by fromJson()

  public val cooldown_modifier: Double? by fromJson()

  public val consumption_modifier: Float? by fromJson()

  /**
   * `"entity"` fires at an entity, `"position"` fires directly at a position, `"direction"` fires
   * in a direction.
   *
   * If this is `"entity"`, `clamp_position` is forced to be `false`.
   */
  public val target_type: AmmoTypeTargetType? by fromJson()

  /**
   * Only exists (and is then mandatory) if the
   * [AmmoItemPrototype::ammo_type](prototype:AmmoItemPrototype::ammo_type) this AmmoType is defined on
   * has multiple ammo types.
   *
   * Defines for which kind of entity this ammo type applies. Each entity kind can only be used once
   * per array.
   */
  public val source_type: AmmoSourceType? by fromJson()

  public object Serializer : JsonReaderSerializer<AmmoType>(AmmoType::class)
}

@Serializable(AndTipTrigger.Serializer::class)
@SerialName("and")
public open class AndTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If all of the triggers are fulfilled, this trigger is considered fulfilled.
   */
  public val triggers: List<TipTrigger> by fromJson()

  public object Serializer : JsonReaderSerializer<AndTipTrigger>(AndTipTrigger::class)
}

@Serializable(AnimatedVector.Serializer::class)
public open class AnimatedVector : JsonReader() {
  public val rotations: List<VectorRotation> by fromJson()

  /**
   * Default render layer for the rotations.
   */
  public val render_layer: RenderLayer? by fromJson()

  public val direction_shift: DirectionShift? by fromJson()

  public object Serializer : JsonReaderSerializer<AnimatedVector>(AnimatedVector::class)
}

/**
 * Specifies an animation that can be used in the game.
 *
 * Note that if any frame of the animation is specified from the same source as any other
 * [Sprite](prototype:Sprite) or frame of other animation, it will be shared.
 */
@Serializable(Animation.Serializer::class)
public open class Animation : AnimationParameters() {
  /**
   * If this property is present, all Animation definitions have to be placed as entries in the
   * array, and they will all be loaded from there. `layers` may not be an empty table. Each definition
   * in the array may also have the `layers` property.
   *
   * `animation_speed` and `max_advance` of the first layer are used for all layers. All layers will
   * run at the same speed.
   *
   * If this property is present, all other properties, including those inherited from
   * AnimationParameters, are ignored.
   */
  public val layers: List<Animation>? by fromJson()

  /**
   * Only loaded if `layers` is not defined. Mandatory if `stripes` is not defined.
   *
   * The path to the sprite file to use.
   */
  override val filename: FileName? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * If this property exists and high resolution sprites are turned on, this is used to load the
   * Animation.
   */
  public val hr_version: Animation? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val stripes: List<Stripe>? by fromJson()

  public object Serializer : JsonReaderSerializer<Animation>(Animation::class)
}

/**
 * If this is loaded as a single Animation, it applies to all directions.
 */
@Serializable(Animation4WayValues.Serializer::class)
public open class Animation4WayValues : JsonReader() {
  public val north: Animation by fromJson()

  /**
   * Defaults to the north animation.
   */
  public val east: Animation? by fromJson()

  /**
   * Defaults to the north animation.
   */
  public val south: Animation? by fromJson()

  /**
   * Defaults to the east animation.
   */
  public val west: Animation? by fromJson()

  public object Serializer : JsonReaderSerializer<Animation4WayValues>(Animation4WayValues::class)
}

/**
 * If this is loaded as a single Animation, it applies to all directions.
 */
public typealias Animation4Way = UnknownUnion

@Serializable(AnimationElement.Serializer::class)
public open class AnimationElement : JsonReader() {
  public val render_layer: RenderLayer? by fromJson()

  /**
   * Used to determine render order for sprites with the same `render_layer` in the same position.
   * Sprites with a higher `secondary_draw_order` are drawn on top.
   */
  public val secondary_draw_order: Byte? by fromJson()

  public val draw_as_sprite: Boolean? by fromJson()

  public val draw_as_light: Boolean? by fromJson()

  public val apply_tint: Boolean? by fromJson()

  public val always_draw: Boolean? by fromJson()

  public val animation: Animation? by fromJson()

  public object Serializer : JsonReaderSerializer<AnimationElement>(AnimationElement::class)
}

/**
 * This is a list of 1-based frame indices into the spritesheet. The actual length of the animation
 * will then be the length of the frame_sequence (times `repeat_count`, times two if `run_mode` is
 * `"forward-then-backward"`). There is a limit for (actual) animation length of 255 frames.
 *
 * Indices can be used in any order, repeated or not used at all. Unused frames are not loaded into
 * VRAM at all, frames referenced multiple times are loaded just once, see
 * [here](https://forums.factorio.com/53202).
 */
public typealias AnimationFrameSequence = List<UShort>

public sealed class AnimationParameters : SpriteParameters() {
  /**
   * The width and height of one frame. If this is a tuple, the first member of the tuple is the
   * width and the second is the height. Otherwise the size is both width and height. Width and height
   * may only be in the range of 0-8192.
   */
  override val size: ItemOrTuple2<SpriteSizeType>? by fromJson()

  /**
   * Mandatory if `size` is not defined.
   *
   * Width of one frame in pixels, from 0-8192.
   */
  override val width: SpriteSizeType? by fromJson()

  /**
   * Mandatory if `size` is not defined.
   *
   * Height of one frame in pixels, from 0-8192.
   */
  override val height: SpriteSizeType? by fromJson()

  public val run_mode: AnimationPrototypeRunMode? by fromJson()

  /**
   * Can't be `0`.
   */
  public open val frame_count: UInt? by fromJson()

  /**
   * Specifies how many pictures are on each horizontal line in the image file. `0` means that all
   * the pictures are in one horizontal line. Once the specified number of pictures are loaded from a
   * line, the pictures from the next line are loaded. This is to allow having longer animations loaded
   * in to Factorio's graphics matrix than the game engine's width limit of 8192px per input file. The
   * restriction on input files is to be compatible with most graphics cards.
   */
  public open val line_length: UInt? by fromJson()

  /**
   * Modifier of the animation playing speed, the default of `1` means one animation frame per tick
   * (60 fps). The speed of playing can often vary depending on the usage (output of steam engine for
   * example). Has to be greater than `0`.
   */
  public val animation_speed: Float? by fromJson()

  /**
   * Maximum amount of frames the animation can move forward in one update. Useful to cap the
   * animation speed on entities where it is variable, such as car animations.
   */
  public val max_advance: Float? by fromJson()

  /**
   * How many times to repeat the animation to complete an animation cycle. E.g. if one layer is 10
   * frames, a second layer of 1 frame would need `repeat_count = 10` to match the complete cycle.
   */
  public val repeat_count: UByte? by fromJson()

  /**
   * Number of slices this is sliced into when using the "optimized atlas packing" option. If you
   * are a modder, you can just ignore this property. Example: If this is 4, the sprite will be sliced
   * into a 4×4 grid.
   */
  public val dice: UByte? by fromJson()

  /**
   * Same as `dice` above, but this specifies only how many slices there are on the x axis.
   */
  public val dice_x: UByte? by fromJson()

  /**
   * Same as `dice` above, but this specifies only how many slices there are on the y axis.
   */
  public val dice_y: UByte? by fromJson()

  public val frame_sequence: AnimationFrameSequence? by fromJson()

  /**
   * Only loaded if this is an icon, that is it has the flag `"group=icon"` or `"group=gui"`.
   *
   * Note that `mipmap_count` doesn't make sense in an animation, as it is not possible to layout
   * mipmaps in a way that would load both the animation and the mipmaps correctly (besides animations
   * with just one frame). See [here](https://forums.factorio.com/viewtopic.php?p=549058#p549058).
   */
  override val mipmap_count: UByte? by fromJson()

  /**
   * Unused.
   */
  override val generate_sdf: Boolean? by fromJson()
}

@Serializable(AnimationSheet.Serializer::class)
public open class AnimationSheet : AnimationParameters() {
  /**
   * If this property exists and high resolution sprites are turned on, this is used to load the
   * AnimationSheet.
   */
  public val hr_version: AnimationSheet? by fromJson()

  public val variation_count: UInt by fromJson()

  override val frame_count: UInt? by fromJson()

  override val line_length: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<AnimationSheet>(AnimationSheet::class)
}

@Serializable(AnimationVariationsValues.Serializer::class)
public open class AnimationVariationsValues : JsonReader() {
  /**
   * The variations are arranged vertically in the file, one row for each variation.
   */
  public val sheet: AnimationSheet? by fromJson()

  /**
   * Only loaded if `sheet` is not defined.
   */
  public val sheets: List<AnimationSheet>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<AnimationVariationsValues>(AnimationVariationsValues::class)
}

public typealias AnimationVariations = UnknownUnion

/**
 * A union of all prototypes. A specific prototype is loaded based on the value of the `type` key.
 *
 * See the [Prototypes page](prototype:prototypes) for more information.
 */
@Serializable
public sealed interface AnyPrototype

@Serializable
public enum class AreaTriggerItemCollisionMode {
  `distance-from-collision-box`,
  `distance-from-center`,
}

@Serializable(AreaTriggerItem.Serializer::class)
@SerialName("area")
public open class AreaTriggerItem : TriggerItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val radius: Double by fromJson()

  public val trigger_from_target: Boolean? by fromJson()

  public val target_entities: Boolean? by fromJson()

  public val show_in_tooltip: Boolean? by fromJson()

  public val collision_mode: AreaTriggerItemCollisionMode? by fromJson()

  public object Serializer : JsonReaderSerializer<AreaTriggerItem>(AreaTriggerItem::class)
}

@Serializable(ArtilleryRangeModifier.Serializer::class)
@SerialName("artillery-range")
public open class ArtilleryRangeModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ArtilleryRangeModifier>(ArtilleryRangeModifier::class)
}

@Serializable(ArtilleryRemoteCapsuleAction.Serializer::class)
@SerialName("artillery-remote")
public open class ArtilleryRemoteCapsuleAction : JsonReader(), CapsuleAction {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of an [ArtilleryFlarePrototype](prototype:ArtilleryFlarePrototype).
   */
  public val flare: EntityID by fromJson()

  public val play_sound_on_failure: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ArtilleryRemoteCapsuleAction>(ArtilleryRemoteCapsuleAction::class)
}

@Serializable(ArtilleryTriggerDelivery.Serializer::class)
@SerialName("artillery")
public open class ArtilleryTriggerDelivery : TriggerDeliveryItem(), TriggerDelivery {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [ArtilleryProjectilePrototype](prototype:ArtilleryProjectilePrototype).
   */
  public val projectile: EntityID by fromJson()

  public val starting_speed: Float by fromJson()

  public val starting_speed_deviation: Float? by fromJson()

  /**
   * Maximum deviation of the projectile from source orientation, in +/- (`x radians / 2`). Example:
   * `3.14 radians -> +/- (180° / 2)`, meaning up to 90° deviation in either direction of rotation.
   */
  public val direction_deviation: Float? by fromJson()

  public val range_deviation: Float? by fromJson()

  public val trigger_fired_artillery: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ArtilleryTriggerDelivery>(ArtilleryTriggerDelivery::class)
}

/**
 * Loaded as one of the [BaseAttackParameters](prototype:BaseAttackParameters) extensions, based on
 * the value of the `type` key.
 */
@Serializable
public sealed interface AttackParameters

@Serializable(AttackReactionItem.Serializer::class)
public open class AttackReactionItem : JsonReader() {
  public val range: Float by fromJson()

  public val action: Trigger? by fromJson()

  public val reaction_modifier: Float? by fromJson()

  public val damage_type: DamageTypeID? by fromJson()

  public object Serializer : JsonReaderSerializer<AttackReactionItem>(AttackReactionItem::class)
}

/**
 * The name of an [AutoplaceControl](prototype:AutoplaceControl).
 */
public typealias AutoplaceControlID = String

@Serializable(AutoplacePeak.Serializer::class)
public open class AutoplacePeak : JsonReader() {
  /**
   * Influence multiplier.
   *
   * Influence is calculated as a sum of influences of peaks. Influence of a peak is obtained by
   * calculating a distance from each of its dimensions and sum of these individual distances is used
   * as a distance from optimal conditions. Based on this distance a peak gets influence between -1 and
   * 1. This is then multiplied by the noise function, if it is specified, and by the `influence`
   * constant (or by `influence` + `richness_influence` if calculating richness). Finally this value is
   * clamped to a range between `min_influence` and `max_influence`.
   *
   * When
   * [AutoplaceSpecification::starting_area_amount](prototype:AutoplaceSpecification::starting_area_amount)
   * is non-zero a position in starting area is selected and a blob is placed centered on this
   * position. Influence is then a maximum of the default calculated value and a value obtained from
   * this blob.
   */
  public val influence: Double? by fromJson()

  /**
   * Minimal influence (after all calculations) of current peak. See `influence`.
   */
  public val min_influence: Double? by fromJson()

  /**
   * Maximal influence (after all calculations) of current peak. See `influence`.
   */
  public val max_influence: Double? by fromJson()

  /**
   * Bonus for influence multiplier when calculating richness. See `influence`.
   */
  public val richness_influence: Double? by fromJson()

  /**
   * Name of [NoiseLayer](prototype:NoiseLayer) to use for this peak. If empty, then no noise is
   * added to this peak.
   *
   * A peak may have a noise multiplied with its influence. Intended use is to have noise layers
   * separate for different types of objects that might appear (trees-12 vs enemy-base).
   */
  public val noise_layer: NoiseLayerID? by fromJson()

  /**
   * Must be between 0 and 1. Persistence of the noise.
   */
  public val noise_persistence: Double? by fromJson()

  /**
   * Difference between number of octaves of the world and of the noise.
   */
  public val noise_octaves_difference: Double? by fromJson()

  public val noise_scale: Double? by fromJson()

  /**
   * Optimal value of starting_area_weight. If starting_area_weight is close to this value, peak
   * influence is 1.
   *
   * starting_area_weight corresponds to the `starting_area_weight` [noise
   * expression](prototype:BaseNamedNoiseExpressions).
   */
  public val starting_area_weight_optimal: Double? by fromJson()

  /**
   * Distance from the optimal parameters that is still considered optimal.
   *
   * Only loaded if `starting_area_weight_optimal` is defined.
   */
  public val starting_area_weight_range: Double? by fromJson()

  /**
   * Distance from the optimal parameters that get influence of -1.
   *
   * Only loaded if `starting_area_weight_optimal` is defined.
   */
  public val starting_area_weight_max_range: Double? by fromJson()

  /**
   * Limit distance from the optimum on a single (positive) side. This is pure magic.
   *
   * Only loaded if `starting_area_weight_optimal` is defined.
   */
  public val starting_area_weight_top_property_limit: Double? by fromJson()

  /**
   * Optimal value of elevation. If elevation is close to this value, peak influence is 1.
   *
   * elevation corresponds to the `elevation` [noise
   * expression](prototype:BaseNamedNoiseExpressions).
   */
  public val elevation_optimal: Double? by fromJson()

  /**
   * Distance from the optimal parameters that is still considered optimal.
   *
   * Only loaded if `elevation_optimal` is defined.
   */
  public val elevation_range: Double? by fromJson()

  /**
   * Distance from the optimal parameters that get influence of -1.
   *
   * Only loaded if `elevation_optimal` is defined.
   */
  public val elevation_max_range: Double? by fromJson()

  /**
   * Limit distance from the optimum on a single (positive) side. This is pure magic.
   *
   * Only loaded if `elevation_optimal` is defined.
   */
  public val elevation_top_property_limit: Double? by fromJson()

  /**
   * Optimal value of water. If water is close to this value, peak influence is 1.
   *
   * water corresponds to the `moisture` [noise expression](prototype:BaseNamedNoiseExpressions).
   */
  public val water_optimal: Double? by fromJson()

  /**
   * Distance from the optimal parameters that is still considered optimal.
   *
   * Only loaded if `water_optimal` is defined.
   */
  public val water_range: Double? by fromJson()

  /**
   * Distance from the optimal parameters that get influence of -1.
   *
   * Only loaded if `water_optimal` is defined.
   */
  public val water_max_range: Double? by fromJson()

  /**
   * Limit distance from the optimum on a single (positive) side. This is pure magic.
   *
   * Only loaded if `water_optimal` is defined.
   */
  public val water_top_property_limit: Double? by fromJson()

  /**
   * Optimal value of temperature. If temperature is close to this value, peak influence is 1.
   *
   * temperature corresponds to the `temperature` [noise
   * expression](prototype:BaseNamedNoiseExpressions).
   */
  public val temperature_optimal: Double? by fromJson()

  /**
   * Distance from the optimal parameters that is still considered optimal.
   *
   * Only loaded if `temperature_optimal` is defined.
   */
  public val temperature_range: Double? by fromJson()

  /**
   * Distance from the optimal parameters that get influence of -1.
   *
   * Only loaded if `temperature_optimal` is defined.
   */
  public val temperature_max_range: Double? by fromJson()

  /**
   * Limit distance from the optimum on a single (positive) side. This is pure magic.
   *
   * Only loaded if `temperature_optimal` is defined.
   */
  public val temperature_top_property_limit: Double? by fromJson()

  /**
   * Optimal value of aux. If aux is close to this value, peak influence is 1.
   *
   * aux corresponds to the `aux` [noise expression](prototype:BaseNamedNoiseExpressions).
   */
  public val aux_optimal: Double? by fromJson()

  /**
   * Distance from the optimal parameters that is still considered optimal.
   *
   * Only loaded if `aux_optimal` is defined.
   */
  public val aux_range: Double? by fromJson()

  /**
   * Distance from the optimal parameters that get influence of -1.
   *
   * Only loaded if `aux_optimal` is defined.
   */
  public val aux_max_range: Double? by fromJson()

  /**
   * Limit distance from the optimum on a single (positive) side. This is pure magic.
   *
   * Only loaded if `aux_optimal` is defined.
   */
  public val aux_top_property_limit: Double? by fromJson()

  /**
   * Optimal value of tier_from_start. If tier_from_start is close to this value, peak influence is
   * 1.
   *
   * tier_from_start corresponds to the `tier_from_start` [noise
   * expression](prototype:BaseNamedNoiseExpressions).
   */
  public val tier_from_start_optimal: Double? by fromJson()

  /**
   * Distance from the optimal parameters that is still considered optimal.
   *
   * Only loaded if `tier_from_start_optimal` is defined.
   */
  public val tier_from_start_range: Double? by fromJson()

  /**
   * Distance from the optimal parameters that get influence of -1.
   *
   * Only loaded if `tier_from_start_optimal` is defined.
   */
  public val tier_from_start_max_range: Double? by fromJson()

  /**
   * Limit distance from the optimum on a single (positive) side. This is pure magic.
   *
   * Only loaded if `tier_from_start_optimal` is defined.
   */
  public val tier_from_start_top_property_limit: Double? by fromJson()

  /**
   * Optimal value of distance. If distance is close to this value, peak influence is 1.
   *
   * distance corresponds to the `distance` [noise expression](prototype:BaseNamedNoiseExpressions).
   */
  public val distance_optimal: Double? by fromJson()

  /**
   * Distance from the optimal parameters that is still considered optimal.
   *
   * Only loaded if `distance_optimal` is defined.
   */
  public val distance_range: Double? by fromJson()

  /**
   * Distance from the optimal parameters that get influence of -1.
   *
   * Only loaded if `distance_optimal` is defined.
   */
  public val distance_max_range: Double? by fromJson()

  /**
   * Limit distance from the optimum on a single (positive) side. This is pure magic.
   *
   * Only loaded if `distance_optimal` is defined.
   */
  public val distance_top_property_limit: Double? by fromJson()

  public object Serializer : JsonReaderSerializer<AutoplacePeak>(AutoplacePeak::class)
}

@Serializable(AutoplaceSettings.Serializer::class)
public open class AutoplaceSettings : JsonReader() {
  /**
   * Whether missing autoplace names for this type should be default enabled.
   */
  public val treat_missing_as_default: Boolean? by fromJson()

  /**
   * Overrides the FrequencySizeRichness provided to the
   * [AutoplaceSpecification](prototype:AutoplaceSpecification) of the entity/tile/decorative. Takes
   * priority over the FrequencySizeRichness set in the [autoplace
   * control](prototype:AutoplaceSpecification::control).
   */
  public val settings: Map<UnknownUnion, FrequencySizeRichness>? by fromJson()

  public object Serializer : JsonReaderSerializer<AutoplaceSettings>(AutoplaceSettings::class)
}

/**
 * Autoplace specification is used to determine which entities are placed when generating map.
 * Currently it is used for enemy bases, tiles, resources and other entities (trees, fishes, etc.).
 *
 * Autoplace specification describe conditions for placing tiles, entities, and decoratives during
 * surface generation. Autoplace specification defines probability of placement on any given tile and
 * richness, which has different meaning depending on the thing being placed.
 *
 * There are two entirely separate ways to specify the probability and richness:
 *
 * - The newer noise expression-based system using `probability_expression` and
 * `richness_expression`.
 *
 * - The older peaks-based system using `peaks` and the properties listed below it.
 */
@Serializable(AutoplaceSpecificationValues.Serializer::class)
public open class AutoplaceSpecificationValues : JsonReader() {
  /**
   * Name of the [AutoplaceControl](prototype:AutoplaceControl) (row in the map generator GUI) that
   * applies to this entity.
   */
  public val control: AutoplaceControlID? by fromJson()

  /**
   * Indicates whether the thing should be placed even if [MapGenSettings](runtime:MapGenSettings)
   * do not provide frequency/size/richness for it. (either for the specific prototype or for the
   * control named by AutoplaceSpecification.control).
   *
   * If true, normal frequency/size/richness (`value=1`) are used in that case. Otherwise it is
   * treated as if 'none' were selected.
   */
  public val default_enabled: Boolean? by fromJson()

  /**
   * Force of the placed entity. Can be a custom force name. Only relevant for
   * [EntityWithOwnerPrototype](prototype:EntityWithOwnerPrototype).
   */
  public val force: UnknownUnion? by fromJson()

  /**
   * Order for placing the entity (has no effect when placing tiles). Entities whose order compares
   * less are placed earlier (this influences placing multiple entities which collide with itself),
   * from entities with equal order string only one with the highest probability is placed.
   */
  public val order: Order? by fromJson()

  /**
   * For entities and decoratives, how many times to attempt to place on each tile. Probability and
   * collisions are taken into account each attempt.
   */
  public val placement_density: UInt? by fromJson()

  /**
   * Restricts tiles or tile transitions the entity can appear on.
   */
  public val tile_restriction: List<TileIDRestriction>? by fromJson()

  /**
   * If specified, provides a noise expression that will be evaluated at every point on the map to
   * determine probability.
   *
   * If left blank, probability is determined by the `peaks` system based on the properties listed
   * below.
   */
  public val probability_expression: NoiseExpression? by fromJson()

  /**
   * If specified, provides a noise expression that will be evaluated to determine richness.
   *
   * If probability_expression is specified and `richness_expression` is not, then
   * `probability_expression` will be used as the richness expression.
   *
   * If neither are specified, then probability and richness are both determined by the `peaks`
   * system based on the properties listed below.
   */
  public val richness_expression: NoiseExpression? by fromJson()

  public val peaks: List<AutoplacePeak>? by fromJson()

  /**
   * Parameter of the sharpness filter for post-processing probability of entity placement. Value of
   * `0` disables the filter, with value `1`, the filter is a step function centered around `0.5`.
   */
  public val sharpness: Double? by fromJson()

  /**
   * Multiplier for output of the sharpness filter.
   *
   * Probability is calculated as `max_probability * sharpness_filter(sum of influences and size
   * modifier from GUI) - random(0, random_probability_penalty)`.
   */
  public val max_probability: Double? by fromJson()

  /**
   * Base Richness. It is calculated as `sum of influences * (richness_multiplier + distance *
   * richness_multiplier_distance_bonus) + richness_base`.
   *
   * Note, that when calculating richness, influences of individual peaks use
   * [AutoplacePeak::richness_influence](prototype:AutoplacePeak::richness_influence) bonus.
   */
  public val richness_base: Double? by fromJson()

  /**
   * See `richness_base`.
   */
  public val richness_multiplier: Double? by fromJson()

  /**
   * Bonus to richness multiplier per tile of distance from starting point. See `richness_base`.
   */
  public val richness_multiplier_distance_bonus: Double? by fromJson()

  /**
   * A random value between `0` and this number is subtracted from a probability after sharpness
   * filter. Only works for entities.
   */
  public val random_probability_penalty: Double? by fromJson()

  /**
   * Sets a fraction of surface that should be covered by this item.
   */
  public val coverage: Double? by fromJson()

  /**
   * If this value is non zero, influence of this entity will be calculated differently in starting
   * area: For each entity with this parameter a position in starting area is selected and a blob is
   * placed centered on this position. The central tile of this blob will have approximately amount of
   * resources selected by this value.
   *
   * See [AutoplacePeak::influence](prototype:AutoplacePeak::influence) for the general influence
   * calculation.
   */
  public val starting_area_amount: UInt? by fromJson()

  /**
   * See `starting_area_amount`. Controls approximate radius of the blob in tiles.
   */
  public val starting_area_size: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<AutoplaceSpecificationValues>(AutoplaceSpecificationValues::class)
}

/**
 * Autoplace specification is used to determine which entities are placed when generating map.
 * Currently it is used for enemy bases, tiles, resources and other entities (trees, fishes, etc.).
 *
 * Autoplace specification describe conditions for placing tiles, entities, and decoratives during
 * surface generation. Autoplace specification defines probability of placement on any given tile and
 * richness, which has different meaning depending on the thing being placed.
 *
 * There are two entirely separate ways to specify the probability and richness:
 *
 * - The newer noise expression-based system using `probability_expression` and
 * `richness_expression`.
 *
 * - The older peaks-based system using `peaks` and the properties listed below it.
 */
public typealias AutoplaceSpecification = UnknownUnion

@Serializable
public enum class BaseAttackParametersRangeMode {
  `center-to-center`,
  `bounding-box-to-bounding-box`,
}

@Serializable
public enum class BaseAttackParametersActivationType {
  shoot,
  `throw`,
  consume,
  activate,
}

/**
 * The abstract base of all [AttackParameters](prototype:AttackParameters).
 */
public sealed class BaseAttackParameters : JsonReader() {
  /**
   * Before an entity can attack, the distance (in tiles) between the entity and target must be less
   * than or equal to this.
   */
  public val range: Float by fromJson()

  /**
   * Number of ticks in which it will be possible to shoot again. If < 1, multiple shots can be
   * performed in one tick.
   */
  public val cooldown: Float by fromJson()

  /**
   * The minimum distance (in tiles) between an entity and target. If a unit's target is less than
   * this, the unit will attempt to move away before attacking. A [flamethrower
   * turret](https://wiki.factorio.com/Flamethrower_turret) does not move, but has a minimum range.
   * Less than this, it is unable to target an enemy.
   */
  public val min_range: Float? by fromJson()

  /**
   * If this is <= 0, it is set to 1. Arc from 0 to 1, so for example 0.25 is 90°. Used by the
   * [flamethrower turret](https://wiki.factorio.com/Flamethrower_turret) in the base game. Arcs
   * greater than 0.5 but less than 1 will be clamped to 0.5 as targeting in arcs larger than half
   * circle is [not implemented](https://forums.factorio.com/94654).
   */
  public val turn_range: Float? by fromJson()

  /**
   * Used when searching for the nearest enemy, when this is > 0, enemies that aren't burning are
   * preferred over burning enemies. Definition of "burning" for this: Entity has sticker attached to
   * it, and the sticker has a [spread_fire_entity](prototype:StickerPrototype::spread_fire_entity)
   * set.
   */
  public val fire_penalty: Float? by fromJson()

  /**
   * A higher penalty will discourage turrets from targeting units that would take longer to turn to
   * face.
   */
  public val rotate_penalty: Float? by fromJson()

  /**
   * A higher penalty will discourage turrets from targeting units with higher health. A negative
   * penalty will encourage turrets to target units with higher health.
   */
  public val health_penalty: Float? by fromJson()

  public val range_mode: BaseAttackParametersRangeMode? by fromJson()

  /**
   * If less than `range`, the entity will choose a random distance between `range` and
   * `min_attack_distance` and attack from that distance.
   */
  public val min_attack_distance: Float? by fromJson()

  public val damage_modifier: Float? by fromJson()

  /**
   * Must be greater than or equal to `0`.
   */
  public val ammo_consumption_modifier: Float? by fromJson()

  /**
   * Must be between `0` and `1`.
   */
  public val cooldown_deviation: Float? by fromJson()

  /**
   * Number of ticks it takes for the weapon to actually shoot after the order for shooting has been
   * made. This also allows to "adjust" the shooting animation to the effect of shooting.
   *
   * [CapsuleActions](prototype:CapsuleAction) cannot have attack parameters with non-zero warmup.
   */
  public val warmup: UInt? by fromJson()

  /**
   * Setting this to anything but zero causes homing projectiles to aim for the predicted location
   * based on enemy movement instead of the current enemy location.
   */
  public val lead_target_for_projectile_speed: Float? by fromJson()

  public val movement_slow_down_cooldown: Float? by fromJson()

  public val movement_slow_down_factor: Double? by fromJson()

  /**
   * Can be mandatory.
   */
  public val ammo_type: AmmoType? by fromJson()

  /**
   * Used in tooltips to set the tooltip category. It is also used to get the locale keys for
   * activation instructions and speed of the action for the tooltip.
   *
   * For example, an activation_type of "throw" will result in the tooltip category "thrown" and the
   * tooltip locale keys "gui.instruction-to-throw" and "description.throwing-speed".
   */
  public val activation_type: BaseAttackParametersActivationType? by fromJson()

  /**
   * Played once at the start of the attack if these are
   * [ProjectileAttackParameters](prototype:ProjectileAttackParameters).
   */
  public val sound: LayeredSound? by fromJson()

  public val animation: RotatedAnimation? by fromJson()

  /**
   * Played during the attack.
   */
  public val cyclic_sound: CyclicSound? by fromJson()

  public val use_shooter_direction: Boolean? by fromJson()

  public val ammo_categories: List<AmmoCategoryID>? by fromJson()

  /**
   * Mandatory if both `ammo_type` and `ammo_categories` are not defined.
   */
  public val ammo_category: AmmoCategoryID? by fromJson()
}

/**
 * The abstract base of all [EnergySources](prototype:EnergySource). Specifies the way an entity
 * gets its energy.
 */
public sealed class BaseEnergySource : JsonReader() {
  /**
   * The pollution an entity emits per minute at full energy consumption. This is exactly the value
   * that is shown in the entity tooltip.
   */
  public open val emissions_per_minute: Double? by fromJson()

  /**
   * Whether to render the "no power" icon if the entity is low on power. Also applies to the "no
   * fuel" icon when using burner energy sources.
   */
  public val render_no_power_icon: Boolean? by fromJson()

  /**
   * Whether to render the "no network" icon if the entity is not connected to an electric network.
   */
  public val render_no_network_icon: Boolean? by fromJson()
}

/**
 * The abstract base of all [Modifiers](prototype:Modifier).
 */
public sealed class BaseModifier : JsonReader() {
  /**
   * Can't be an empty array.
   */
  public val icons: List<IconData>? by fromJson()

  /**
   * Path to the icon file.
   *
   * Only loaded if `icons` is not defined.
   */
  public val icon: FileName? by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Only loaded if `icons` is not defined, or if `icon_size` is not specified for all instances of
   * `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()
}

/**
 * A list of notable [NamedNoiseExpressions](prototype:NamedNoiseExpression) defined in the base
 * game. A list of all named noise expression defined in the base game can be found
 * [here](https://wiki.factorio.com/Data.raw#noise-expression).
 *
 * Note that the named noise expressions are all defined in Lua, so mods may remove or change the
 * notable expressions listed here, or change how they are used in the map generation.
 */
@Serializable
public enum class BaseNamedNoiseExpressions {
  distance,
  tier_from_start,
  tier,
  starting_area_weight,
  moisture,
  aux,
  temperature,
  elevation,
  cliffiness,
  `enemy-base-intensity`,
  `enemy-base-frequency`,
  `enemy-base-radius`,
}

@Serializable
public enum class BaseStyleSpecificationEffect {
  `compilatron-hologram`,
}

/**
 * The abstract base of all [StyleSpecifications](prototype:StyleSpecification).
 */
public sealed class BaseStyleSpecification : JsonReader() {
  /**
   * Name of a [StyleSpecification](prototype:StyleSpecification). This style inherits all property
   * values from its parent.
   *
   * Styles without a parent property default to the root style for their type. The exception to
   * this are the root styles themselves, as they cannot have a parent set. Due to this, for root
   * styles, some style properties are mandatory and behavior may be unexpected, such as an element not
   * showing up because its size defaults to `0`.
   */
  public val parent: String? by fromJson()

  public val horizontal_align: HorizontalAlign? by fromJson()

  public val vertical_align: VerticalAlign? by fromJson()

  public val ignored_by_search: Boolean? by fromJson()

  public val never_hide_by_search: Boolean? by fromJson()

  public val horizontally_stretchable: StretchRule? by fromJson()

  public val vertically_stretchable: StretchRule? by fromJson()

  public val horizontally_squashable: StretchRule? by fromJson()

  public val vertically_squashable: StretchRule? by fromJson()

  /**
   * If this is a tuple, the first member sets `natural_width` and the second sets `natural_height`.
   * Otherwise, both `natural_width` and `natural_height` are set to the same value.
   */
  public val natural_size: ItemOrTuple2<UInt>? by fromJson()

  /**
   * If this is a tuple, the first member sets `width`, and the second sets `height`. Otherwise,
   * both `width` and `height` are set to the same value.
   */
  public val size: ItemOrTuple2<UInt>? by fromJson()

  /**
   * Sets `minimal_width`, `maximal_width` and `natural_width` to the same value.
   */
  public val width: UInt? by fromJson()

  /**
   * Minimal width ensures that the widget will never be smaller than than that size. It can't be
   * squashed to be smaller.
   */
  public val minimal_width: UInt? by fromJson()

  /**
   * Maximal width ensures that the widget will never be bigger than than that size. It can't be
   * stretched to be bigger.
   */
  public val maximal_width: UInt? by fromJson()

  /**
   * Natural width specifies the width of the element tries to have, but it can still be
   * squashed/stretched to have a different size.
   */
  public val natural_width: UInt? by fromJson()

  /**
   * Sets `minimal_height`, `maximal_height` and `natural_height` to the same value.
   */
  public val height: UInt? by fromJson()

  /**
   * Minimal height ensures that the widget will never be smaller than than that size. It can't be
   * squashed to be smaller.
   */
  public val minimal_height: UInt? by fromJson()

  /**
   * Maximal height ensures that the widget will never be bigger than than that size. It can't be
   * stretched to be bigger.
   */
  public val maximal_height: UInt? by fromJson()

  /**
   * Natural height specifies the height of the element tries to have, but it can still be
   * squashed/stretched to have a different size.
   */
  public val natural_height: UInt? by fromJson()

  /**
   * Sets `top_padding`, `right_padding`, `bottom_padding` and `left_padding` to the same value.
   */
  public val padding: Short? by fromJson()

  public val top_padding: Short? by fromJson()

  public val right_padding: Short? by fromJson()

  public val bottom_padding: Short? by fromJson()

  public val left_padding: Short? by fromJson()

  /**
   * Sets `top_margin`, `right_margin`, `bottom_margin` and `left_margin` to the same value.
   */
  public val margin: Short? by fromJson()

  public val top_margin: Short? by fromJson()

  public val right_margin: Short? by fromJson()

  public val bottom_margin: Short? by fromJson()

  public val left_margin: Short? by fromJson()

  /**
   * Name of a custom GUI effect, which are hard-coded in the game's engine. Only has one option
   * currently.
   */
  public val effect: BaseStyleSpecificationEffect? by fromJson()

  public val effect_opacity: Float? by fromJson()

  public val tooltip: LocalisedString? by fromJson()
}

@Serializable
public enum class BeaconGraphicsSetModuleTintMode {
  `single-module`,
  mix,
}

@Serializable(BeaconGraphicsSet.Serializer::class)
public open class BeaconGraphicsSet : JsonReader() {
  public val draw_animation_when_idle: Boolean? by fromJson()

  public val draw_light_when_idle: Boolean? by fromJson()

  public val random_animation_offset: Boolean? by fromJson()

  public val module_icons_suppressed: Boolean? by fromJson()

  public val base_layer: RenderLayer? by fromJson()

  public val animation_layer: RenderLayer? by fromJson()

  public val top_layer: RenderLayer? by fromJson()

  public val animation_progress: Float? by fromJson()

  public val min_animation_progress: Float? by fromJson()

  public val max_animation_progress: Float? by fromJson()

  /**
   * Which tint set in [ModulePrototype::beacon_tint](prototype:ModulePrototype::beacon_tint) should
   * be applied to this, if any.
   */
  public val apply_module_tint: ModuleTint? by fromJson()

  /**
   * Which tint set in [ModulePrototype::beacon_tint](prototype:ModulePrototype::beacon_tint) should
   * be applied to the light, if any.
   */
  public val apply_module_tint_to_light: ModuleTint? by fromJson()

  public val no_modules_tint: Color? by fromJson()

  public val animation_list: List<AnimationElement>? by fromJson()

  public val light: LightDefinition? by fromJson()

  /**
   * The visualisations available for displaying the modules in the beacon. The visualisation is
   * chosen based on art style, see
   * [BeaconModuleVisualizations::art_style](prototype:BeaconModuleVisualizations::art_style) and
   * [ModulePrototype::art_style](prototype:ModulePrototype::art_style).
   */
  public val module_visualisations: List<BeaconModuleVisualizations>? by fromJson()

  public val module_tint_mode: BeaconGraphicsSetModuleTintMode? by fromJson()

  public object Serializer : JsonReaderSerializer<BeaconGraphicsSet>(BeaconGraphicsSet::class)
}

@Serializable(BeaconModuleVisualization.Serializer::class)
public open class BeaconModuleVisualization : JsonReader() {
  public val has_empty_slot: Boolean? by fromJson()

  public val draw_as_light: Boolean? by fromJson()

  public val draw_as_sprite: Boolean? by fromJson()

  /**
   * Used to determine render order for sprites with the same `render_layer` in the same position.
   * Sprites with a higher `secondary_draw_order` are drawn on top.
   */
  public val secondary_draw_order: Byte? by fromJson()

  /**
   * Which tint set in [ModulePrototype::beacon_tint](prototype:ModulePrototype::beacon_tint) should
   * be applied to this, if any.
   */
  public val apply_module_tint: ModuleTint? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public val pictures: SpriteVariations? by fromJson()

  public object Serializer :
      JsonReaderSerializer<BeaconModuleVisualization>(BeaconModuleVisualization::class)
}

@Serializable(BeaconModuleVisualizations.Serializer::class)
public open class BeaconModuleVisualizations : JsonReader() {
  /**
   * The visualization is chosen based on the
   * [ModulePrototype::art_style](prototype:ModulePrototype::art_style), meaning if module art style
   * equals beacon module visualization art style then this visualization is chosen. Vanilla uses
   * `"vanilla"` here.
   */
  public val art_style: String by fromJson()

  public val use_for_empty_slots: Boolean? by fromJson()

  public val tier_offset: Int? by fromJson()

  /**
   * The outer array contains the different slots, the inner array contains the different layers for
   * those slots (with different tints etc). Example:
   */
  public val slots: List<List<BeaconModuleVisualization>>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<BeaconModuleVisualizations>(BeaconModuleVisualizations::class)
}

@Serializable(BeaconVisualizationTints.Serializer::class)
public open class BeaconVisualizationTints : JsonReader() {
  public val primary: Color? by fromJson()

  public val secondary: Color? by fromJson()

  public val tertiary: Color? by fromJson()

  public val quaternary: Color? by fromJson()

  public object Serializer :
      JsonReaderSerializer<BeaconVisualizationTints>(BeaconVisualizationTints::class)
}

@Serializable(BeamAnimationSet.Serializer::class)
public open class BeamAnimationSet : JsonReader() {
  public val start: Animation? by fromJson()

  public val ending: Animation? by fromJson()

  public val head: Animation? by fromJson()

  public val tail: Animation? by fromJson()

  public val body: AnimationVariations? by fromJson()

  public object Serializer : JsonReaderSerializer<BeamAnimationSet>(BeamAnimationSet::class)
}

@Serializable(BeamAttackParameters.Serializer::class)
@SerialName("beam")
public open class BeamAttackParameters : BaseAttackParameters(), AttackParameters {
  public val type: UnknownStringLiteral by fromJson()

  public val source_direction_count: UInt? by fromJson()

  public val source_offset: Vector? by fromJson()

  public object Serializer : JsonReaderSerializer<BeamAttackParameters>(BeamAttackParameters::class)
}

@Serializable(BeamTriggerDelivery.Serializer::class)
@SerialName("beam")
public open class BeamTriggerDelivery : TriggerDeliveryItem(), TriggerDelivery {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [BeamPrototype](prototype:BeamPrototype).
   */
  public val beam: EntityID by fromJson()

  public val add_to_shooter: Boolean? by fromJson()

  public val max_length: UInt? by fromJson()

  public val duration: UInt? by fromJson()

  public val source_offset: Vector? by fromJson()

  public object Serializer : JsonReaderSerializer<BeamTriggerDelivery>(BeamTriggerDelivery::class)
}

@Serializable(BeltTraverseTipTrigger.Serializer::class)
@SerialName("belt-traverse")
public open class BeltTraverseTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<BeltTraverseTipTrigger>(BeltTraverseTipTrigger::class)
}

/**
 * Determines how sprites/animations should blend with the background. The possible values are
 * listed below.
 *
 * Note that in most of Factorio it is assumed colors are in alpha pre-multiplied format, see [FFF
 * #172 - Blending and Rendering](https://www.factorio.com/blog/post/fff-172). Sprites get
 * pre-multiplied when loaded, unless `premul_alpha` is set to `false` on the sprite/animation itself.
 * Since generating mipmaps doesn't respect `premul_alpha`, lower mipmap levels will be in
 * pre-multiplied format regardless.
 */
@Serializable
public enum class BlendMode {
  normal,
  additive,
  `additive-soft`,
  multiplicative,
  `multiplicative-with-alpha`,
  overwrite,
}

/**
 * The table itself is required, but it can be empty.
 */
@Serializable(BoilerFire.Serializer::class)
public open class BoilerFire : JsonReader() {
  public val north: Animation? by fromJson()

  public val east: Animation? by fromJson()

  public val south: Animation? by fromJson()

  public val west: Animation? by fromJson()

  public object Serializer : JsonReaderSerializer<BoilerFire>(BoilerFire::class)
}

/**
 * The table itself is required, but it can be empty.
 */
@Serializable(BoilerFireGlow.Serializer::class)
public open class BoilerFireGlow : JsonReader() {
  public val north: Animation? by fromJson()

  public val east: Animation? by fromJson()

  public val south: Animation? by fromJson()

  public val west: Animation? by fromJson()

  public object Serializer : JsonReaderSerializer<BoilerFireGlow>(BoilerFireGlow::class)
}

@Serializable(BoilerPatch.Serializer::class)
public open class BoilerPatch : JsonReader() {
  public val north: Sprite? by fromJson()

  public val east: Sprite? by fromJson()

  public val south: Sprite? by fromJson()

  public val west: Sprite? by fromJson()

  public object Serializer : JsonReaderSerializer<BoilerPatch>(BoilerPatch::class)
}

@Serializable(BoilerStructure.Serializer::class)
public open class BoilerStructure : JsonReader() {
  public val north: Animation by fromJson()

  public val east: Animation by fromJson()

  public val south: Animation by fromJson()

  public val west: Animation by fromJson()

  public object Serializer : JsonReaderSerializer<BoilerStructure>(BoilerStructure::class)
}

@Serializable(BonusGuiOrdering.Serializer::class)
public open class BonusGuiOrdering : JsonReader() {
  public val artillery_range: Order by fromJson()

  public val worker_robots: Order by fromJson()

  public val character: Order by fromJson()

  public val follower_robots: Order by fromJson()

  public val research_speed: Order by fromJson()

  public val inserter: Order by fromJson()

  public val stack_inserter: Order by fromJson()

  public val turret_attack: Order by fromJson()

  public val mining_productivity: Order by fromJson()

  public val train_braking_force: Order by fromJson()

  public object Serializer : JsonReaderSerializer<BonusGuiOrdering>(BonusGuiOrdering::class)
}

public sealed class BoolModifier : BaseModifier() {
  /**
   * The value this modifier will have upon researching.
   */
  public val modifier: Boolean by fromJson()
}

@Serializable(BorderImageSet.Serializer::class)
public open class BorderImageSet : JsonReader() {
  public val scale: Double? by fromJson()

  public val border_width: UInt? by fromJson()

  public val vertical_line: Sprite? by fromJson()

  public val horizontal_line: Sprite? by fromJson()

  public val top_right_corner: Sprite? by fromJson()

  public val bottom_right_corner: Sprite? by fromJson()

  public val bottom_left_corner: Sprite? by fromJson()

  public val top_left_coner: Sprite? by fromJson()

  public val top_t: Sprite? by fromJson()

  public val right_t: Sprite? by fromJson()

  public val bottom_t: Sprite? by fromJson()

  public val left_t: Sprite? by fromJson()

  public val cross: Sprite? by fromJson()

  public val top_end: Sprite? by fromJson()

  public val right_end: Sprite? by fromJson()

  public val bottom_end: Sprite? by fromJson()

  public val left_end: Sprite? by fromJson()

  public object Serializer : JsonReaderSerializer<BorderImageSet>(BorderImageSet::class)
}

/**
 * A cursor box, for use in [UtilitySprites](prototype:UtilitySprites).
 */
@Serializable(BoxSpecification.Serializer::class)
public open class BoxSpecification : JsonReader() {
  public val sprite: Sprite by fromJson()

  /**
   * Whether this is a complete box or just the top left corner. If this is true, `side_length` and
   * `side_height` must be present. Otherwise `max_side_length` must be present.
   */
  public val is_whole_box: Boolean? by fromJson()

  /**
   * Only loaded, and mandatory if `is_whole_box` is `true`.
   */
  public val side_length: Double? by fromJson()

  /**
   * Only loaded, and mandatory if `is_whole_box` is `true`.
   */
  public val side_height: Double? by fromJson()

  /**
   * Only loaded, and mandatory if `is_whole_box` is `false`.
   */
  public val max_side_length: Double? by fromJson()

  public object Serializer : JsonReaderSerializer<BoxSpecification>(BoxSpecification::class)
}

@Serializable(BuildEntityTipTrigger.Serializer::class)
@SerialName("build-entity")
public open class BuildEntityTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public val entity: EntityID? by fromJson()

  public val match_type_only: Boolean? by fromJson()

  public val build_by_dragging: Boolean? by fromJson()

  /**
   * Building is considered consecutive when the built entity is the same as the last built entity.
   */
  public val consecutive: Boolean? by fromJson()

  public val linear_power_pole_line: Boolean? by fromJson()

  public val build_in_line: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<BuildEntityTipTrigger>(BuildEntityTipTrigger::class)
}

@Serializable(BurnerEnergySource.Serializer::class)
@SerialName("burner")
public open class BurnerEnergySource : BaseEnergySource(), EnergySource {
  /**
   * This is only loaded, and mandatory if the energy source can be loaded as multiple energy source
   * types.
   */
  public val type: UnknownStringLiteral? by fromJson()

  public val fuel_inventory_size: ItemStackIndex by fromJson()

  public val burnt_inventory_size: ItemStackIndex? by fromJson()

  public val smoke: List<SmokeSource>? by fromJson()

  public val light_flicker: LightFlickeringDefinition? by fromJson()

  /**
   * `1` means 100% effectivity. Must be greater than `0`. Multiplier of the energy output.
   */
  public val effectivity: Double? by fromJson()

  /**
   * The energy source can be used with fuel from this [fuel category](prototype:FuelCategory). For
   * a list of built-in categories, see [here](https://wiki.factorio.com/Data.raw#fuel-category).
   *
   * Only loaded if `fuel_categories` is not defined.
   */
  public val fuel_category: FuelCategoryID? by fromJson()

  /**
   * The energy source can be used with fuel from these [fuel categories](prototype:FuelCategory).
   */
  public val fuel_categories: List<FuelCategoryID>? by fromJson()

  public object Serializer : JsonReaderSerializer<BurnerEnergySource>(BurnerEnergySource::class)
}

@Serializable(ButtonStyleSpecification.Serializer::class)
@SerialName("button_style")
public open class ButtonStyleSpecification : StyleWithClickableGraphicalSetSpecification(),
    StyleSpecification {
  public open val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val font: String? by fromJson()

  public val default_font_color: Color? by fromJson()

  public val hovered_font_color: Color? by fromJson()

  public val clicked_font_color: Color? by fromJson()

  public val disabled_font_color: Color? by fromJson()

  public val selected_font_color: Color? by fromJson()

  public val selected_hovered_font_color: Color? by fromJson()

  public val selected_clicked_font_color: Color? by fromJson()

  public val strikethrough_color: Color? by fromJson()

  public val pie_progress_color: Color? by fromJson()

  public val clicked_vertical_offset: UInt? by fromJson()

  public val draw_shadow_under_picture: Boolean? by fromJson()

  public val draw_grayscale_picture: Boolean? by fromJson()

  public val icon_horizontal_align: HorizontalAlign? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ButtonStyleSpecification>(ButtonStyleSpecification::class)
}

@Serializable(CameraEffectTriggerEffectItem.Serializer::class)
@SerialName("camera-effect")
public open class CameraEffectTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Required, read by the game and then immediately discarded. In short: Does nothing.
   */
  public val effect: String by fromJson()

  public val duration: UByte by fromJson()

  public val ease_in_duration: UByte? by fromJson()

  public val ease_out_duration: UByte? by fromJson()

  public val delay: UByte? by fromJson()

  public val full_strength_max_distance: UShort? by fromJson()

  public val max_distance: UShort? by fromJson()

  public val strength: Float? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CameraEffectTriggerEffectItem>(CameraEffectTriggerEffectItem::class)
}

@Serializable(CameraStyleSpecification.Serializer::class)
@SerialName("camera_style")
public open class CameraStyleSpecification : EmptyWidgetStyleSpecification(), StyleSpecification {
  override val type: UnknownStringLiteral by fromJson()

  public object Serializer :
      JsonReaderSerializer<CameraStyleSpecification>(CameraStyleSpecification::class)
}

/**
 * Loaded as one of the capsule actions, based on the value of the `type` key.
 */
@Serializable
public sealed interface CapsuleAction

/**
 * The data for one variation of [character animations](prototype:CharacterPrototype::animations).
 */
@Serializable(CharacterArmorAnimation.Serializer::class)
public open class CharacterArmorAnimation : JsonReader() {
  public val idle: RotatedAnimation by fromJson()

  public val idle_with_gun: RotatedAnimation by fromJson()

  public val running: RotatedAnimation by fromJson()

  /**
   * Must contain exactly 18 directions, so all of the combination of gun direction and moving
   * direction can be covered. Some of these variations are used in reverse to save space. You can use
   * the character animation in the base game for reference.
   */
  public val running_with_gun: RotatedAnimation by fromJson()

  public val mining_with_tool: RotatedAnimation by fromJson()

  /**
   * flipped_shadow_running_with_gun must be nil or contain exactly 18 directions, so all of the
   * combination of gun direction and moving direction can be covered. Some of these variations are
   * used in reverse to save space. You can use the character animation in the base game for reference.
   * `flipped_shadow_running_with_gun` has to have same frame count as `running_with_gun`.
   */
  public val flipped_shadow_running_with_gun: RotatedAnimation? by fromJson()

  /**
   * The names of the armors this animation data is used for. Don't define this if you want the
   * animations to be used for the player without armor.
   */
  public val armors: List<ItemID>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterArmorAnimation>(CharacterArmorAnimation::class)
}

@Serializable(CharacterBuildDistanceModifier.Serializer::class)
@SerialName("character-build-distance")
public open class CharacterBuildDistanceModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterBuildDistanceModifier>(CharacterBuildDistanceModifier::class)
}

@Serializable(CharacterCraftingSpeedModifier.Serializer::class)
@SerialName("character-crafting-speed")
public open class CharacterCraftingSpeedModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterCraftingSpeedModifier>(CharacterCraftingSpeedModifier::class)
}

@Serializable(CharacterHealthBonusModifier.Serializer::class)
@SerialName("character-health-bonus")
public open class CharacterHealthBonusModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterHealthBonusModifier>(CharacterHealthBonusModifier::class)
}

@Serializable(CharacterInventorySlotsBonusModifier.Serializer::class)
@SerialName("character-inventory-slots-bonus")
public open class CharacterInventorySlotsBonusModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterInventorySlotsBonusModifier>(CharacterInventorySlotsBonusModifier::class)
}

@Serializable(CharacterItemDropDistanceModifier.Serializer::class)
@SerialName("character-item-drop-distance")
public open class CharacterItemDropDistanceModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterItemDropDistanceModifier>(CharacterItemDropDistanceModifier::class)
}

@Serializable(CharacterItemPickupDistanceModifier.Serializer::class)
@SerialName("character-item-pickup-distance")
public open class CharacterItemPickupDistanceModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterItemPickupDistanceModifier>(CharacterItemPickupDistanceModifier::class)
}

@Serializable(CharacterLogisticRequestsModifier.Serializer::class)
@SerialName("character-logistic-requests")
public open class CharacterLogisticRequestsModifier : BoolModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterLogisticRequestsModifier>(CharacterLogisticRequestsModifier::class)
}

@Serializable(CharacterLogisticTrashSlotsModifier.Serializer::class)
@SerialName("character-logistic-trash-slots")
public open class CharacterLogisticTrashSlotsModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterLogisticTrashSlotsModifier>(CharacterLogisticTrashSlotsModifier::class)
}

@Serializable(CharacterLootPickupDistanceModifier.Serializer::class)
@SerialName("character-loot-pickup-distance")
public open class CharacterLootPickupDistanceModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterLootPickupDistanceModifier>(CharacterLootPickupDistanceModifier::class)
}

@Serializable(CharacterMiningSpeedModifier.Serializer::class)
@SerialName("character-mining-speed")
public open class CharacterMiningSpeedModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterMiningSpeedModifier>(CharacterMiningSpeedModifier::class)
}

@Serializable(CharacterReachDistanceModifier.Serializer::class)
@SerialName("character-reach-distance")
public open class CharacterReachDistanceModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterReachDistanceModifier>(CharacterReachDistanceModifier::class)
}

@Serializable(CharacterResourceReachDistanceModifier.Serializer::class)
@SerialName("character-resource-reach-distance")
public open class CharacterResourceReachDistanceModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterResourceReachDistanceModifier>(CharacterResourceReachDistanceModifier::class)
}

@Serializable(CharacterRunningSpeedModifier.Serializer::class)
@SerialName("character-running-speed")
public open class CharacterRunningSpeedModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CharacterRunningSpeedModifier>(CharacterRunningSpeedModifier::class)
}

@Serializable(ChartUtilityConstants.Serializer::class)
public open class ChartUtilityConstants : JsonReader() {
  public val electric_lines_color: Color by fromJson()

  public val electric_lines_color_switch_enabled: Color by fromJson()

  public val electric_lines_color_switch_disabled: Color by fromJson()

  public val electric_power_pole_color: Color by fromJson()

  public val switch_color: Color by fromJson()

  public val electric_line_width: Float by fromJson()

  public val electric_line_minimum_absolute_width: Float by fromJson()

  public val turret_range_color: Color by fromJson()

  public val artillery_range_color: Color by fromJson()

  public val pollution_color: Color by fromJson()

  public val default_friendly_color: Color by fromJson()

  public val default_enemy_color: Color by fromJson()

  public val rail_color: Color by fromJson()

  public val entity_ghost_color: Color by fromJson()

  public val vehicle_outer_color: Color by fromJson()

  public val vehicle_outer_color_selected: Color by fromJson()

  public val vehicle_inner_color: Color by fromJson()

  public val vehicle_wagon_connection_color: Color by fromJson()

  public val resource_outline_selection_color: Color by fromJson()

  public val chart_train_stop_text_color: Color by fromJson()

  public val chart_train_stop_disabled_text_color: Color by fromJson()

  public val chart_train_stop_full_text_color: Color by fromJson()

  public val red_signal_color: Color by fromJson()

  public val green_signal_color: Color by fromJson()

  public val blue_signal_color: Color by fromJson()

  public val yellow_signal_color: Color by fromJson()

  public val chart_deconstruct_tint: Color by fromJson()

  /**
   * The strings are entity types.
   */
  public val default_friendly_color_by_type: Map<String, Color>? by fromJson()

  /**
   * The strings are entity types.
   */
  public val default_color_by_type: Map<String, Color>? by fromJson()

  public val explosion_visualization_duration: UInt by fromJson()

  public val train_path_color: Color by fromJson()

  public val train_preview_path_outline_color: Color by fromJson()

  public val train_current_path_outline_color: Color by fromJson()

  public val chart_logistic_robot_color: Color by fromJson()

  public val chart_construction_robot_color: Color by fromJson()

  public val chart_mobile_construction_robot_color: Color by fromJson()

  public val chart_personal_construction_robot_color: Color by fromJson()

  public val zoom_threshold_to_draw_spider_path: Double? by fromJson()

  public val custom_tag_scale: Float? by fromJson()

  public val custom_tag_selected_overlay_tint: Color by fromJson()

  public object Serializer :
      JsonReaderSerializer<ChartUtilityConstants>(ChartUtilityConstants::class)
}

@Serializable(CheckBoxStyleSpecification.Serializer::class)
@SerialName("checkbox_style")
public open class CheckBoxStyleSpecification : StyleWithClickableGraphicalSetSpecification(),
    StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val font: String? by fromJson()

  public val font_color: Color? by fromJson()

  public val disabled_font_color: Color? by fromJson()

  public val checkmark: Sprite? by fromJson()

  public val disabled_checkmark: Sprite? by fromJson()

  public val intermediate_mark: Sprite? by fromJson()

  public val text_padding: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CheckBoxStyleSpecification>(CheckBoxStyleSpecification::class)
}

@Serializable(CircuitConnectorLayer.Serializer::class)
public open class CircuitConnectorLayer : JsonReader() {
  public val north: RenderLayer? by fromJson()

  public val east: RenderLayer? by fromJson()

  public val south: RenderLayer? by fromJson()

  public val west: RenderLayer? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CircuitConnectorLayer>(CircuitConnectorLayer::class)
}

@Serializable(CircuitConnectorSecondaryDrawOrder.Serializer::class)
public open class CircuitConnectorSecondaryDrawOrder : JsonReader() {
  public val north: Byte? by fromJson()

  public val east: Byte? by fromJson()

  public val south: Byte? by fromJson()

  public val west: Byte? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CircuitConnectorSecondaryDrawOrder>(CircuitConnectorSecondaryDrawOrder::class)
}

@Serializable(CircuitConnectorSprites.Serializer::class)
public open class CircuitConnectorSprites : JsonReader() {
  public val led_red: Sprite by fromJson()

  public val led_green: Sprite by fromJson()

  public val led_blue: Sprite by fromJson()

  public val led_light: LightDefinition by fromJson()

  /**
   * Drawn when the entity is connected to a circuit network or a logistic network.
   */
  public val connector_main: Sprite? by fromJson()

  /**
   * Drawn when the entity is connected to a circuit network or a logistic network.
   */
  public val connector_shadow: Sprite? by fromJson()

  /**
   * Drawn when the entity is connected to a circuit network.
   */
  public val wire_pins: Sprite? by fromJson()

  /**
   * Drawn when the entity is connected to a circuit network.
   */
  public val wire_pins_shadow: Sprite? by fromJson()

  public val led_blue_off: Sprite? by fromJson()

  public val blue_led_light_offset: Vector? by fromJson()

  public val red_green_led_light_offset: Vector? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CircuitConnectorSprites>(CircuitConnectorSprites::class)
}

@Serializable(CircularParticleCreationSpecification.Serializer::class)
public open class CircularParticleCreationSpecification : JsonReader() {
  public val name: ParticleID by fromJson()

  public val starting_frame_speed: Float by fromJson()

  public val direction: Float? by fromJson()

  public val direction_deviation: Float? by fromJson()

  public val speed: Float? by fromJson()

  public val speed_deviation: Float? by fromJson()

  public val starting_frame_speed_deviation: Float? by fromJson()

  public val height: Float? by fromJson()

  public val height_deviation: Float? by fromJson()

  public val vertical_speed: Float? by fromJson()

  public val vertical_speed_deviation: Float? by fromJson()

  public val center: Vector? by fromJson()

  public val creation_distance: Double? by fromJson()

  public val creation_distance_orientation: Double? by fromJson()

  public val use_source_position: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CircularParticleCreationSpecification>(CircularParticleCreationSpecification::class)
}

public typealias CircularProjectileCreationSpecification = List<UnknownTuple>

@Serializable(ClearCursorTipTrigger.Serializer::class)
@SerialName("clear-cursor")
public open class ClearCursorTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ClearCursorTipTrigger>(ClearCursorTipTrigger::class)
}

@Serializable(CliffPlacementSettings.Serializer::class)
public open class CliffPlacementSettings : JsonReader() {
  /**
   * Name of the [CliffPrototype](prototype:CliffPrototype).
   */
  public val name: EntityID? by fromJson()

  /**
   * Elevation at which the first row of cliffs is placed. Can not be set from the map generation
   * GUI.
   */
  public val cliff_elevation_0: Float? by fromJson()

  /**
   * Elevation difference between successive rows of cliffs. This is inversely proportional to
   * 'frequency' in the map generation GUI. Specifically, when set from the GUI the value is `40 /
   * frequency`.
   */
  public val cliff_elevation_interval: Float? by fromJson()

  /**
   * Corresponds to 'continuity' in the GUI. This value is not used directly, but is used by the
   * 'cliffiness' noise expression, which in combination with elevation and the two cliff elevation
   * properties drives cliff placement (cliffs are placed when elevation crosses the elevation contours
   * defined by `cliff_elevation_0` and `cliff_elevation_interval` when 'cliffiness' is greater than
   * `0.5`). The default 'cliffiness' expression interprets this value such that larger values result
   * in longer unbroken walls of cliffs, and smaller values (between `0` and `1`) result in larger gaps
   * in cliff walls.
   */
  public val richness: MapGenSize? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CliffPlacementSettings>(CliffPlacementSettings::class)
}

@Serializable(ClusterTriggerItem.Serializer::class)
@SerialName("cluster")
public open class ClusterTriggerItem : TriggerItem() {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Must be at least `2`.
   */
  public val cluster_count: UInt by fromJson()

  public val distance: Float by fromJson()

  public val distance_deviation: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<ClusterTriggerItem>(ClusterTriggerItem::class)
}

/**
 * Every entry in the array is a specification of one layer the object collides with or a special
 * collision option. Supplying an empty table means that no layers and no collision options are set.
 *
 * The default collision masks of all entity types can be found
 * [here](prototype:EntityPrototype::collision_mask). The base game provides common collision mask
 * functions in a Lua file in the core
 * [lualib](https://github.com/wube/factorio-data/blob/master/core/lualib/collision-mask-util.lua).
 *
 * Supplying an empty array means that no layers and no collision options are set.
 *
 * The three options in addition to the standard layers are not collision masks, instead they
 * control other aspects of collision.
 */
public typealias CollisionMask = List<UnknownUnion>

/**
 * A string specifying a collision mask layer.
 *
 * In addition to the listed layers, there is `"layer-13"` through `"layer-55"`. These layers are
 * currently unused by the game but may change. If a mod is going to use one of the unused layers it's
 * recommended to use the `collision_mask_util.get_first_unused_layer()` method from the vanilla
 * [library](https://github.com/wube/factorio-data/blob/master/core/lualib/collision-mask-util.lua).
 * When not using the library, mods should start at the higher layers because the base game will take
 * from the lower ones.
 */
@Serializable
public enum class CollisionMaskLayer {
  `ground-tile`,
  `water-tile`,
  `resource-layer`,
  `doodad-layer`,
  `floor-layer`,
  `item-layer`,
  `ghost-layer`,
  `object-layer`,
  `player-layer`,
  `train-layer`,
  `rail-layer`,
  `transport-belt-layer`,
}

/**
 * Table of red, green, blue, and alpha float values between 0 and 1. Alternatively, values can be
 * from 0-255, they are interpreted as such if at least one value is `> 1`.
 *
 * Color allows the short-hand notation of passing an array of exactly 3 or 4 numbers. The array
 * items are r, g, b and optionally a, in that order.
 *
 * The game usually expects colors to be in pre-multiplied form (color channels are pre-multiplied
 * by alpha).
 */
@Serializable(ColorValues.Serializer::class)
public open class ColorValues : JsonReader() {
  /**
   * red value
   */
  public val r: Float? by fromJson()

  /**
   * green value
   */
  public val g: Float? by fromJson()

  /**
   * blue value
   */
  public val b: Float? by fromJson()

  /**
   * alpha value (opacity)
   */
  public val a: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<ColorValues>(ColorValues::class)
}

/**
 * Table of red, green, blue, and alpha float values between 0 and 1. Alternatively, values can be
 * from 0-255, they are interpreted as such if at least one value is `> 1`.
 *
 * Color allows the short-hand notation of passing an array of exactly 3 or 4 numbers. The array
 * items are r, g, b and optionally a, in that order.
 *
 * The game usually expects colors to be in pre-multiplied form (color channels are pre-multiplied
 * by alpha).
 */
public typealias Color = UnknownUnion

@Serializable(ColorFilterData.Serializer::class)
public open class ColorFilterData : JsonReader() {
  public val name: String by fromJson()

  public val localised_name: LocalisedString by fromJson()

  /**
   * 4 arrays of 4-length float arrays, essentially a 4x4 matrix.
   */
  public val matrix: List<List<Float>> by fromJson()

  public object Serializer : JsonReaderSerializer<ColorFilterData>(ColorFilterData::class)
}

@Serializable
public enum class ColumnAlignmentAlignment {
  center,
  left,
  right,
  `top-left`,
  `middle-left`,
  `bottom-left`,
  `top-center`,
  `middle-center`,
  `bottom-center`,
  `top-right`,
  `middle-right`,
  `bottom-right`,
}

@Serializable(ColumnAlignment.Serializer::class)
public open class ColumnAlignment : JsonReader() {
  /**
   * Column index.
   */
  public val column: UInt by fromJson()

  public val alignment: ColumnAlignmentAlignment by fromJson()

  public object Serializer : JsonReaderSerializer<ColumnAlignment>(ColumnAlignment::class)
}

@Serializable(ColumnWidth.Serializer::class)
public open class ColumnWidth : JsonReader() {
  /**
   * Column index.
   */
  public val column: UInt by fromJson()

  public val minimal_width: Int? by fromJson()

  public val maximal_width: Int? by fromJson()

  /**
   * Sets `minimal_width` and `maximal_width` to the same value.
   */
  public val width: Int? by fromJson()

  public object Serializer : JsonReaderSerializer<ColumnWidth>(ColumnWidth::class)
}

/**
 * Graphics for the heat pipe.
 */
@Serializable(ConnectableEntityGraphics.Serializer::class)
public open class ConnectableEntityGraphics : JsonReader() {
  public val single: SpriteVariations by fromJson()

  public val straight_vertical: SpriteVariations by fromJson()

  public val straight_horizontal: SpriteVariations by fromJson()

  public val corner_right_down: SpriteVariations by fromJson()

  public val corner_left_down: SpriteVariations by fromJson()

  public val corner_right_up: SpriteVariations by fromJson()

  public val corner_left_up: SpriteVariations by fromJson()

  public val t_up: SpriteVariations by fromJson()

  public val t_right: SpriteVariations by fromJson()

  public val t_down: SpriteVariations by fromJson()

  public val t_left: SpriteVariations by fromJson()

  public val ending_up: SpriteVariations by fromJson()

  public val ending_right: SpriteVariations by fromJson()

  public val ending_down: SpriteVariations by fromJson()

  public val ending_left: SpriteVariations by fromJson()

  public val cross: SpriteVariations by fromJson()

  public object Serializer :
      JsonReaderSerializer<ConnectableEntityGraphics>(ConnectableEntityGraphics::class)
}

/**
 * A constant boolean noise expression, such as a literal boolean. When using a constant number, it
 * evaluates to true for numbers bigger than zero, anything else evaluates to false.
 */
@Serializable
public sealed interface ConstantNoiseBoolean

/**
 * A constant numeric noise expression, such as a literal number, the result of addition of
 * constants or multioctave noise that uses only constant arguments.
 */
public typealias ConstantNoiseNumber = NoiseNumber

/**
 * Defines which other inputs a [CustomInputPrototype](prototype:CustomInputPrototype) consumes.
 */
@Serializable
public enum class ConsumingType {
  none,
  `game-only`,
}

@Serializable
public enum class CraftItemTipTriggerEventType {
  `crafting-of-single-item-ordered`,
  `crafting-of-multiple-items-ordered`,
  `crafting-finished`,
}

@Serializable(CraftItemTipTrigger.Serializer::class)
@SerialName("craft-item")
public open class CraftItemTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val item: ItemID? by fromJson()

  public val event_type: CraftItemTipTriggerEventType by fromJson()

  /**
   * Can only be used when `event_type` is `"crafting-finished"`.
   */
  public val consecutive: Boolean? by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<CraftItemTipTrigger>(CraftItemTipTrigger::class)
}

/**
 * If no tint is specified, the crafting machine falls back to
 * [CraftingMachinePrototype::default_recipe_tint](prototype:CraftingMachinePrototype::default_recipe_tint).
 */
@Serializable(CraftingMachineTint.Serializer::class)
public open class CraftingMachineTint : JsonReader() {
  public val primary: Color? by fromJson()

  public val secondary: Color? by fromJson()

  public val tertiary: Color? by fromJson()

  public val quaternary: Color? by fromJson()

  public object Serializer : JsonReaderSerializer<CraftingMachineTint>(CraftingMachineTint::class)
}

@Serializable(CreateDecorativesTriggerEffectItem.Serializer::class)
@SerialName("create-decorative")
public open class CreateDecorativesTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val decorative: DecorativeID by fromJson()

  public val spawn_max: UShort by fromJson()

  public val spawn_min_radius: Float by fromJson()

  /**
   * Must be less than 24.
   */
  public val spawn_max_radius: Float by fromJson()

  public val spawn_min: UShort? by fromJson()

  public val radius_curve: Float? by fromJson()

  public val apply_projection: Boolean? by fromJson()

  public val spread_evenly: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CreateDecorativesTriggerEffectItem>(CreateDecorativesTriggerEffectItem::class)
}

@Serializable(CreateEntityTriggerEffectItem.Serializer::class)
@SerialName("create-entity")
public open class CreateEntityTriggerEffectItem : TriggerEffectItem() {
  public open val type: UnknownStringLiteral by fromJson()

  /**
   * The name of the entity that should be created.
   */
  public val entity_name: EntityID by fromJson()

  public val offset_deviation: BoundingBox? by fromJson()

  /**
   * If `true`, the [on_trigger_created_entity](runtime:on_trigger_created_entity) event will be
   * raised.
   */
  public val trigger_created_entity: Boolean? by fromJson()

  public val check_buildability: Boolean? by fromJson()

  override val show_in_tooltip: Boolean? by fromJson()

  /**
   * Entity creation will not occur if any tile matches the collision condition. Defaults to no
   * collisions.
   */
  public val tile_collision_mask: CollisionMask? by fromJson()

  /**
   * If multiple offsets are specified, multiple entities are created. The projectile of the
   * [Distractor capsule](https://wiki.factorio.com/Distractor_capsule) uses this property to spawn
   * three Distractors.
   */
  public val offsets: ItemOrList<Vector>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CreateEntityTriggerEffectItem>(CreateEntityTriggerEffectItem::class)
}

@Serializable(CreateExplosionTriggerEffectItem.Serializer::class)
@SerialName("create-explosion")
public open class CreateExplosionTriggerEffectItem : CreateEntityTriggerEffectItem() {
  override val type: UnknownStringLiteral by fromJson()

  public val max_movement_distance: Float? by fromJson()

  public val max_movement_distance_deviation: Float? by fromJson()

  public val inherit_movement_distance_from_projectile: Boolean? by fromJson()

  public val cycle_while_moving: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CreateExplosionTriggerEffectItem>(CreateExplosionTriggerEffectItem::class)
}

@Serializable(CreateFireTriggerEffectItem.Serializer::class)
@SerialName("create-fire")
public open class CreateFireTriggerEffectItem : CreateEntityTriggerEffectItem() {
  override val type: UnknownStringLiteral by fromJson()

  public val initial_ground_flame_count: UByte? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CreateFireTriggerEffectItem>(CreateFireTriggerEffectItem::class)
}

@Serializable(CreateParticleTriggerEffectItem.Serializer::class)
@SerialName("create-particle")
public open class CreateParticleTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val particle_name: ParticleID by fromJson()

  public val initial_height: Float by fromJson()

  public val offset_deviation: BoundingBox? by fromJson()

  override val show_in_tooltip: Boolean? by fromJson()

  public val tile_collision_mask: CollisionMask? by fromJson()

  public val offsets: ItemOrList<Vector>? by fromJson()

  public val initial_height_deviation: Float? by fromJson()

  public val initial_vertical_speed: Float? by fromJson()

  public val initial_vertical_speed_deviation: Float? by fromJson()

  public val speed_from_center: Float? by fromJson()

  public val speed_from_center_deviation: Float? by fromJson()

  public val frame_speed: Float? by fromJson()

  public val frame_speed_deviation: Float? by fromJson()

  /**
   * Silently capped to a maximum of 100.
   */
  public val tail_length: UByte? by fromJson()

  /**
   * Silently capped to a maximum of 100.
   */
  public val tail_length_deviation: UByte? by fromJson()

  public val tail_width: Float? by fromJson()

  public val rotate_offsets: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CreateParticleTriggerEffectItem>(CreateParticleTriggerEffectItem::class)
}

@Serializable(CreateSmokeTriggerEffectItem.Serializer::class)
@SerialName("create-smoke")
public open class CreateSmokeTriggerEffectItem : CreateEntityTriggerEffectItem() {
  override val type: UnknownStringLiteral by fromJson()

  public val initial_height: Float? by fromJson()

  public val speed: Vector? by fromJson()

  public val speed_multiplier: Float? by fromJson()

  public val speed_multiplier_deviation: Float? by fromJson()

  public val starting_frame: Float? by fromJson()

  public val starting_frame_deviation: Float? by fromJson()

  public val starting_frame_speed: Float? by fromJson()

  public val starting_frame_speed_deviation: Float? by fromJson()

  public val speed_from_center: Float? by fromJson()

  public val speed_from_center_deviation: Float? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CreateSmokeTriggerEffectItem>(CreateSmokeTriggerEffectItem::class)
}

@Serializable(CreateStickerTriggerEffectItem.Serializer::class)
@SerialName("create-sticker")
public open class CreateStickerTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [StickerPrototype](prototype:StickerPrototype) that should be created.
   */
  public val sticker: EntityID by fromJson()

  override val show_in_tooltip: Boolean? by fromJson()

  /**
   * If `true`, [on_trigger_created_entity](runtime:on_trigger_created_entity) will be triggered
   * when the sticker is created.
   */
  public val trigger_created_entity: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CreateStickerTriggerEffectItem>(CreateStickerTriggerEffectItem::class)
}

@Serializable(CreateTrivialSmokeEffectItem.Serializer::class)
@SerialName("create-trivial-smoke")
public open class CreateTrivialSmokeEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val smoke_name: TrivialSmokeID by fromJson()

  public val offset_deviation: BoundingBox? by fromJson()

  public val offsets: ItemOrList<Vector>? by fromJson()

  public val initial_height: Float? by fromJson()

  public val max_radius: Float? by fromJson()

  public val speed: Vector? by fromJson()

  public val speed_multiplier: Float? by fromJson()

  public val speed_multiplier_deviation: Float? by fromJson()

  public val starting_frame: Float? by fromJson()

  public val starting_frame_deviation: Float? by fromJson()

  public val starting_frame_speed: Float? by fromJson()

  public val starting_frame_speed_deviation: Float? by fromJson()

  public val speed_from_center: Float? by fromJson()

  public val speed_from_center_deviation: Float? by fromJson()

  public object Serializer :
      JsonReaderSerializer<CreateTrivialSmokeEffectItem>(CreateTrivialSmokeEffectItem::class)
}

@Serializable(CursorBoxSpecification.Serializer::class)
public open class CursorBoxSpecification : JsonReader() {
  public val regular: List<BoxSpecification> by fromJson()

  public val not_allowed: List<BoxSpecification> by fromJson()

  public val copy: List<BoxSpecification> by fromJson()

  public val electricity: List<BoxSpecification> by fromJson()

  public val logistics: List<BoxSpecification> by fromJson()

  public val pair: List<BoxSpecification> by fromJson()

  public val train_visualization: List<BoxSpecification> by fromJson()

  public val blueprint_snap_rectangle: List<BoxSpecification> by fromJson()

  public object Serializer :
      JsonReaderSerializer<CursorBoxSpecification>(CursorBoxSpecification::class)
}

/**
 * One of the following values:
 */
@Serializable
public enum class CursorBoxType {
  entity,
  electricity,
  copy,
  `not-allowed`,
  pair,
  logistics,
  `train-visualization`,
  `blueprint-snap-rectangle`,
}

/**
 * Used by [BaseAttackParameters](prototype:BaseAttackParameters) to play a sound during the attack.
 */
@Serializable(CyclicSound.Serializer::class)
public open class CyclicSound : JsonReader() {
  /**
   * Played once at the beginning of the overall cyclic sound.
   */
  public val begin_sound: Sound? by fromJson()

  /**
   * Played repeatedly after the begin_sound was played.
   */
  public val middle_sound: Sound? by fromJson()

  /**
   * Played once when the overall cyclic sound is requested to end.
   */
  public val end_sound: Sound? by fromJson()

  public object Serializer : JsonReaderSerializer<CyclicSound>(CyclicSound::class)
}

/**
 * A property type, NOT a prototype. Used to specify what type of damage and how much damage
 * something deals.
 */
@Serializable(DamagePrototype.Serializer::class)
public open class DamagePrototype : JsonReader() {
  public val amount: Float by fromJson()

  /**
   * The type of damage. See [here](https://wiki.factorio.com/Data.raw#damage-type) for a list of
   * built-in types, and [DamageType](prototype:DamageType) for creating custom types.
   */
  public val type: DamageTypeID by fromJson()

  public object Serializer : JsonReaderSerializer<DamagePrototype>(DamagePrototype::class)
}

@Serializable(DamageTriggerEffectItem.Serializer::class)
@SerialName("damage")
public open class DamageTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val damage: DamagePrototype by fromJson()

  public val apply_damage_to_trees: Boolean? by fromJson()

  /**
   * If `true`, no corpse for killed entities will be created.
   */
  public val vaporize: Boolean? by fromJson()

  public val lower_distance_threshold: UShort? by fromJson()

  public val upper_distance_threshold: UShort? by fromJson()

  public val lower_damage_modifier: Float? by fromJson()

  public val upper_damage_modifier: Float? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DamageTriggerEffectItem>(DamageTriggerEffectItem::class)
}

@Serializable(DamageTypeFiltersValues.Serializer::class)
public open class DamageTypeFiltersValues : JsonReader() {
  /**
   * The damage types to filter for.
   */
  public val types: ItemOrList<DamageTypeID> by fromJson()

  /**
   * Whether this is a whitelist or a blacklist of damage types. Defaults to being a blacklist.
   */
  public val whitelist: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DamageTypeFiltersValues>(DamageTypeFiltersValues::class)
}

public typealias DamageTypeFilters = UnknownUnion

/**
 * The name of a [DamageType](prototype:DamageType).
 */
public typealias DamageTypeID = String

/**
 * The first member of the tuple states at which time of the day the LUT should be used. If the
 * current game time is between two values defined in the color lookup that have different LUTs, the
 * color is interpolated to create a smooth transition. (Sharp transition can be achieved by having the
 * two values differing only by a small fraction.)
 *
 * If there is only one tuple, it means that the LUT will be used all the time, regardless of the
 * value of the first member of the tuple.
 *
 * The second member of the tuple is a lookup table (LUT) for the color which maps the original
 * color to a position in the sprite where is the replacement color is found. The file pointed to by
 * the filename must be a sprite of size 256×16.
 */
public typealias DaytimeColorLookupTable = List<UnknownTuple>

@Serializable(DeconstructionTimeToLiveModifier.Serializer::class)
@SerialName("deconstruction-time-to-live")
public open class DeconstructionTimeToLiveModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DeconstructionTimeToLiveModifier>(DeconstructionTimeToLiveModifier::class)
}

/**
 * The name of a [DecorativePrototype](prototype:DecorativePrototype).
 */
public typealias DecorativeID = String

@Serializable(DefaultRecipeTint.Serializer::class)
public open class DefaultRecipeTint : JsonReader() {
  public val primary: Color? by fromJson()

  public val secondary: Color? by fromJson()

  public val tertiary: Color? by fromJson()

  public val quaternary: Color? by fromJson()

  public object Serializer : JsonReaderSerializer<DefaultRecipeTint>(DefaultRecipeTint::class)
}

/**
 * This trigger is considered fulfilled when the
 * [TipsAndTricksItem::dependencies](prototype:TipsAndTricksItem::dependencies) are fulfilled.
 */
@Serializable(DependenciesMetTipTrigger.Serializer::class)
@SerialName("dependencies-met")
public open class DependenciesMetTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public object Serializer :
      JsonReaderSerializer<DependenciesMetTipTrigger>(DependenciesMetTipTrigger::class)
}

@Serializable(DestroyCliffsCapsuleAction.Serializer::class)
@SerialName("destroy-cliffs")
public open class DestroyCliffsCapsuleAction : JsonReader(), CapsuleAction {
  public val type: UnknownStringLiteral by fromJson()

  public val attack_parameters: AttackParameters by fromJson()

  public val radius: Float by fromJson()

  public val timeout: UInt? by fromJson()

  public val play_sound_on_failure: Boolean? by fromJson()

  /**
   * Whether using the capsule consumes an item from the stack.
   */
  public val uses_stack: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DestroyCliffsCapsuleAction>(DestroyCliffsCapsuleAction::class)
}

@Serializable(DestroyCliffsTriggerEffectItem.Serializer::class)
@SerialName("destroy-cliffs")
public open class DestroyCliffsTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val radius: Float by fromJson()

  public val explosion: EntityID? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DestroyCliffsTriggerEffectItem>(DestroyCliffsTriggerEffectItem::class)
}

@Serializable(DestroyDecorativesTriggerEffectItem.Serializer::class)
@SerialName("destroy-decoratives")
public open class DestroyDecorativesTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val radius: Float by fromJson()

  public val from_render_layer: RenderLayer? by fromJson()

  public val to_render_layer: RenderLayer? by fromJson()

  /**
   * Soft decoratives are those where
   * [DecorativePrototype::grows_through_rail_path](prototype:DecorativePrototype::grows_through_rail_path)
   * is `true`.
   */
  public val include_soft_decoratives: Boolean? by fromJson()

  public val include_decals: Boolean? by fromJson()

  public val invoke_decorative_trigger: Boolean? by fromJson()

  /**
   * If `true`, only decoratives with a
   * [DecorativePrototype::trigger_effect](prototype:DecorativePrototype::trigger_effect) will be
   * destroyed.
   */
  public val decoratives_with_trigger_only: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DestroyDecorativesTriggerEffectItem>(DestroyDecorativesTriggerEffectItem::class)
}

@Serializable
public enum class ResearchQueueSetting {
  always,
  `after-victory`,
  never,
}

@Serializable(DifficultySettings.Serializer::class)
public open class DifficultySettings : JsonReader() {
  /**
   * A
   * [defines.difficulty_settings.recipe_difficulty](runtime:defines.difficulty_settings.recipe_difficulty).
   */
  public val recipe_difficulty: UByte by fromJson()

  /**
   * A
   * [defines.difficulty_settings.technology_difficulty](runtime:defines.difficulty_settings.technology_difficulty).
   */
  public val technology_difficulty: UByte by fromJson()

  /**
   * Must be >= 0.001 and <= 1000.
   */
  public val technology_price_multiplier: Double? by fromJson()

  public val research_queue_setting: ResearchQueueSetting? by fromJson()

  public object Serializer : JsonReaderSerializer<DifficultySettings>(DifficultySettings::class)
}

@Serializable(DirectTriggerItem.Serializer::class)
@SerialName("direct")
public open class DirectTriggerItem : TriggerItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val filter_enabled: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<DirectTriggerItem>(DirectTriggerItem::class)
}

/**
 * Usually specified by using [defines.direction](runtime:defines.direction).
 */
public typealias Direction = UnknownUnion

@Serializable(DirectionShift.Serializer::class)
public open class DirectionShift : JsonReader() {
  public val north: Vector? by fromJson()

  public val east: Vector? by fromJson()

  public val south: Vector? by fromJson()

  public val west: Vector? by fromJson()

  public object Serializer : JsonReaderSerializer<DirectionShift>(DirectionShift::class)
}

@Serializable(DistanceFromNearestPointArguments.Serializer::class)
public open class DistanceFromNearestPointArguments : JsonReader() {
  public val x: NoiseNumber by fromJson()

  public val y: NoiseNumber by fromJson()

  public val points: NoiseArray by fromJson()

  public val maximum_distance: ConstantNoiseNumber? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DistanceFromNearestPointArguments>(DistanceFromNearestPointArguments::class)
}

@Serializable(DoubleSliderStyleSpecification.Serializer::class)
@SerialName("double_slider_style")
public open class DoubleSliderStyleSpecification : SliderStyleSpecification(), StyleSpecification {
  override val type: UnknownStringLiteral by fromJson()

  public object Serializer :
      JsonReaderSerializer<DoubleSliderStyleSpecification>(DoubleSliderStyleSpecification::class)
}

@Serializable(DropDownStyleSpecification.Serializer::class)
@SerialName("dropdown_style")
public open class DropDownStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val button_style: ButtonStyleSpecification? by fromJson()

  public val icon: Sprite? by fromJson()

  public val list_box_style: ListBoxStyleSpecification? by fromJson()

  public val selector_and_title_spacing: Short? by fromJson()

  public val opened_sound: Sound? by fromJson()

  public object Serializer :
      JsonReaderSerializer<DropDownStyleSpecification>(DropDownStyleSpecification::class)
}

/**
 * When applied to [modules](prototype:ModulePrototype), the resulting effect is a sum of all module
 * effects, multiplied through calculations: `(1 + sum module effects)` or, for productivity `(0 +
 * sum)`.
 */
@Serializable(Effect.Serializer::class)
public open class Effect : JsonReader() {
  /**
   * Multiplier to energy used during operation (not idle/drain use). The minimum possible sum
   * is -80%.
   */
  public val consumption: EffectValue? by fromJson()

  /**
   * Modifier to crafting speed, research speed, etc. The minimum possible sum is -80%.
   */
  public val speed: EffectValue? by fromJson()

  /**
   * Multiplied against work completed, adds to the bonus results of operating. E.g. an extra
   * crafted recipe or immediate research bonus. The minimum possible sum is 0%.
   */
  public val productivity: EffectValue? by fromJson()

  /**
   * Multiplier to the pollution factor of an entity's pollution during use. The minimum possible
   * sum is -80%.
   */
  public val pollution: EffectValue? by fromJson()

  public object Serializer : JsonReaderSerializer<Effect>(Effect::class)
}

/**
 * A list of [module](prototype:ModulePrototype) effects, or just a single effect. Modules with
 * other effects cannot be used on the machine. This means that both effects from modules and from
 * surrounding beacons are restricted to the listed effects. If `allowed_effects` is an empty array,
 * the machine cannot be affected by modules or beacons.
 */
@Serializable
public enum class EffectTypeLimitationValues {
  speed,
  productivity,
  consumption,
  pollution,
}

/**
 * A list of [module](prototype:ModulePrototype) effects, or just a single effect. Modules with
 * other effects cannot be used on the machine. This means that both effects from modules and from
 * surrounding beacons are restricted to the listed effects. If `allowed_effects` is an empty array,
 * the machine cannot be affected by modules or beacons.
 */
public typealias EffectTypeLimitation = ItemOrList<EffectTypeLimitationValues>

@Serializable(EffectValue.Serializer::class)
public open class EffectValue : JsonReader() {
  /**
   * Precision is ignored beyond two decimals - `0.567` results in `0.56` and means 56% etc. Values
   * can range from `-327.68` to `327.67`. Numbers outside of this range will wrap around.
   */
  public val bonus: Double? by fromJson()

  public object Serializer : JsonReaderSerializer<EffectValue>(EffectValue::class)
}

@Serializable(ElectricEnergySource.Serializer::class)
@SerialName("electric")
public open class ElectricEnergySource : BaseEnergySource(), EnergySource {
  /**
   * This is only loaded, and mandatory if the energy source can be loaded as multiple energy source
   * types.
   */
  public val type: UnknownStringLiteral? by fromJson()

  /**
   * How much energy this entity can hold.
   */
  public val buffer_capacity: Energy? by fromJson()

  public val usage_priority: ElectricUsagePriority by fromJson()

  /**
   * The rate at which energy can be taken, from the network, to refill the energy buffer. `0` means
   * no transfer.
   */
  public val input_flow_limit: Energy? by fromJson()

  /**
   * The rate at which energy can be provided, to the network, from the energy buffer. `0` means no
   * transfer.
   */
  public val output_flow_limit: Energy? by fromJson()

  /**
   * How much energy (per second) will be continuously removed from the energy buffer. In-game, this
   * is shown in the tooltip as "Min. [Minimum] Consumption". Applied as a constant
   * consumption-per-tick, even when the entity has the property [active](runtime:LuaEntity::active)
   * set to `false`.
   */
  public val drain: Energy? by fromJson()

  public object Serializer : JsonReaderSerializer<ElectricEnergySource>(ElectricEnergySource::class)
}

/**
 * Used to specify priority of energy usage in the [electric
 * system](https://wiki.factorio.com/Electric_system).
 */
@Serializable
public enum class ElectricUsagePriority {
  `primary-input`,
  `primary-output`,
  `secondary-input`,
  `secondary-output`,
  tertiary,
  solar,
  lamp,
}

/**
 * If this is loaded as a single ElementImageSetLayer, it gets used as `base`.
 */
@Serializable(ElementImageSetValues.Serializer::class)
public open class ElementImageSetValues : JsonReader() {
  public val base: ElementImageSetLayer? by fromJson()

  public val shadow: ElementImageSetLayer? by fromJson()

  public val glow: ElementImageSetLayer? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ElementImageSetValues>(ElementImageSetValues::class)
}

/**
 * If this is loaded as a single ElementImageSetLayer, it gets used as `base`.
 */
public typealias ElementImageSet = UnknownUnion

@Serializable
public enum class ElementImageSetLayerDrawType {
  `inner`,
  outer,
}

@Serializable
public enum class ElementImageSetLayerType {
  none,
  composition,
}

/**
 * If this is loaded as a Sprite, it gets used as `center`.
 */
@Serializable(ElementImageSetLayerValues.Serializer::class)
public open class ElementImageSetLayerValues : JsonReader() {
  /**
   * Defines whether the border should be drawn inside the widget, which affects the padding and
   * content size of the widget, or outside of the widget which doesn't affect size. The outer draw
   * type is most commonly used for shadows, glows and insets.
   */
  public val draw_type: ElementImageSetLayerDrawType? by fromJson()

  public val type: ElementImageSetLayerType? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val tint: Color? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val center: Sprite? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val left: Sprite? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val left_top: Sprite? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val left_bottom: Sprite? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val right: Sprite? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val right_top: Sprite? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val right_bottom: Sprite? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val top: Sprite? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val bottom: Sprite? by fromJson()

  /**
   * If this is a tuple, the first member of the tuple is width and the second is height. Otherwise
   * the size is both width and height.
   *
   * Only loaded if `type` is `"composition"`.
   */
  public val corner_size: ItemOrTuple2<UShort>? by fromJson()

  /**
   * Only loaded if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val filename: FileName? by fromJson()

  /**
   * Mandatory if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val position: MapPosition? by fromJson()

  /**
   * Only loaded if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val load_in_minimal_mode: Boolean? by fromJson()

  /**
   * Only loaded if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val top_width: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val bottom_width: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val left_height: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val right_height: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val center_width: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val center_height: SpriteSizeType? by fromJson()

  /**
   * Only loaded if `corner_size` is defined. Only loaded if `type` is `"composition"`.
   */
  public val scale: Double? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val top_border: Int? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val right_border: Int? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val bottom_border: Int? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val left_border: Int? by fromJson()

  /**
   * Sets `top_border`, `right_border`, `bottom_border` and `left_border`.
   *
   * Only loaded if `corner_size` is not defined. Only loaded if `type` is `"composition"`.
   */
  public val border: Int? by fromJson()

  /**
   * Only loaded if `type` is `"composition"`.
   */
  public val stretch_monolith_image_to_size: Boolean? by fromJson()

  /**
   * Tiling is used to make a side (not corner) texture repeat instead of being stretched.
   */
  public val left_tiling: Boolean? by fromJson()

  public val right_tiling: Boolean? by fromJson()

  public val top_tiling: Boolean? by fromJson()

  public val bottom_tiling: Boolean? by fromJson()

  public val center_tiling_vertical: Boolean? by fromJson()

  public val center_tiling_horizontal: Boolean? by fromJson()

  /**
   * Overall tiling is used to make the overall texture repeat instead of being stretched.
   */
  public val overall_tiling_horizontal_size: UShort? by fromJson()

  public val overall_tiling_horizontal_spacing: UShort? by fromJson()

  public val overall_tiling_horizontal_padding: UShort? by fromJson()

  public val overall_tiling_vertical_size: UShort? by fromJson()

  public val overall_tiling_vertical_spacing: UShort? by fromJson()

  public val overall_tiling_vertical_padding: UShort? by fromJson()

  public val custom_horizontal_tiling_sizes: List<UInt>? by fromJson()

  public val opacity: Double? by fromJson()

  public val background_blur: Boolean? by fromJson()

  public val background_blur_sigma: Float? by fromJson()

  public val top_outer_border_shift: Int? by fromJson()

  public val bottom_outer_border_shift: Int? by fromJson()

  public val right_outer_border_shift: Int? by fromJson()

  public val left_outer_border_shift: Int? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ElementImageSetLayerValues>(ElementImageSetLayerValues::class)
}

/**
 * If this is loaded as a Sprite, it gets used as `center`.
 */
public typealias ElementImageSetLayer = UnknownUnion

@Serializable(EmptyWidgetStyleSpecification.Serializer::class)
@SerialName("empty_widget_style")
public open class EmptyWidgetStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public open val type: UnknownStringLiteral by fromJson()

  public val graphical_set: ElementImageSet? by fromJson()

  public object Serializer :
      JsonReaderSerializer<EmptyWidgetStyleSpecification>(EmptyWidgetStyleSpecification::class)
}

@Serializable(EnemyEvolutionSettings.Serializer::class)
public open class EnemyEvolutionSettings : JsonReader() {
  public val enabled: Boolean by fromJson()

  /**
   * Percentual increase in the evolution factor for every second (60 ticks)
   */
  public val time_factor: Double by fromJson()

  /**
   * Percentual increase in the evolution factor for every destroyed spawner
   */
  public val destroy_factor: Double by fromJson()

  /**
   * Percentual increase in the evolution factor for 1 pollution unit
   */
  public val pollution_factor: Double by fromJson()

  public object Serializer :
      JsonReaderSerializer<EnemyEvolutionSettings>(EnemyEvolutionSettings::class)
}

@Serializable(EnemyExpansionSettings.Serializer::class)
public open class EnemyExpansionSettings : JsonReader() {
  public val enabled: Boolean by fromJson()

  /**
   * Distance in chunks from the furthest base around. This prevents expansions from reaching too
   * far into the player's territory.
   */
  public val max_expansion_distance: UInt by fromJson()

  public val friendly_base_influence_radius: UInt by fromJson()

  public val enemy_building_influence_radius: UInt by fromJson()

  public val building_coefficient: Double by fromJson()

  public val other_base_coefficient: Double by fromJson()

  public val neighbouring_chunk_coefficient: Double by fromJson()

  public val neighbouring_base_chunk_coefficient: Double by fromJson()

  /**
   * A chunk has to have at most this much percent unbuildable tiles for it to be considered a
   * candidate. This is to avoid chunks full of water to be marked as candidates.
   */
  public val max_colliding_tiles_coefficient: Double by fromJson()

  /**
   * Size of the group that goes to build new base (the game interpolates between min size and max
   * size based on evolution factor).
   */
  public val settler_group_min_size: UInt by fromJson()

  public val settler_group_max_size: UInt by fromJson()

  /**
   * Ticks to expand to a single position for a base is used. Cooldown is calculated as follows:
   * `cooldown = lerp(max_expansion_cooldown, min_expansion_cooldown, -e^2 + 2 * e)` where `lerp` is
   * the linear interpolation function, and e is the current evolution factor.
   */
  public val min_expansion_cooldown: UInt by fromJson()

  public val max_expansion_cooldown: UInt by fromJson()

  public object Serializer :
      JsonReaderSerializer<EnemyExpansionSettings>(EnemyExpansionSettings::class)
}

/**
 * Specifies an amount of electric energy in joules, or electric energy per time in watts.
 *
 * Internally, the input in `Watt` or `Joule/second` is always converted into `Joule/tick`, where 1
 * second is equal to 60 ticks. This means it uses the following formula: `Power in Joule/tick = Power
 * in Watt / 60`. See [Power](https://wiki.factorio.com/Units#Power).
 *
 * Supported Multipliers:
 *
 * - `k/K`: 10^3, or 1 000
 *
 * - `M`: 10^6
 *
 * - `G`: 10^9
 *
 * - `T`: 10^12
 *
 * - `P`: 10^15
 *
 * - `E`: 10^18
 *
 * - `Z`: 10^21
 *
 * - `Y`: 10^24
 */
public typealias Energy = String

/**
 * Loaded as one of the [BaseEnergySource](prototype:BaseEnergySource) extensions, based on the
 * value of the `type` key.
 */
@Serializable
public sealed interface EnergySource

/**
 * The name of an [EntityPrototype](prototype:EntityPrototype).
 */
public typealias EntityID = String

/**
 * An array containing the following values.
 *
 * If an entity is a [building](runtime:LuaEntityPrototype::is_building) and has the
 * `"player-creation"` flag set, it is considered for multiple enemy/unit behaviors:
 *
 * - Autonomous enemy attacks (usually triggered by pollution) can only attack within chunks that
 * contain at least one entity that is both a building and a player-creation.
 *
 * - Enemy expansion considers entities that are both buildings and player-creations as "enemy"
 * entities that may block expansion.
 */
@Serializable
public enum class EntityPrototypeFlagsValues {
  `not-rotatable`,
  `placeable-neutral`,
  `placeable-player`,
  `placeable-enemy`,
  `placeable-off-grid`,
  `player-creation`,
  `building-direction-8-way`,
  `filter-directions`,
  `fast-replaceable-no-build-while-moving`,
  `breaths-air`,
  `not-repairable`,
  `not-on-map`,
  `not-deconstructable`,
  `not-blueprintable`,
  hidden,
  `hide-alt-info`,
  `fast-replaceable-no-cross-type-while-moving`,
  `no-gap-fill-while-building`,
  `not-flammable`,
  `no-automated-item-removal`,
  `no-automated-item-insertion`,
  `no-copy-paste`,
  `not-selectable-in-game`,
  `not-upgradable`,
  `not-in-kill-statistics`,
  `not-in-made-in`,
}

/**
 * An array containing the following values.
 *
 * If an entity is a [building](runtime:LuaEntityPrototype::is_building) and has the
 * `"player-creation"` flag set, it is considered for multiple enemy/unit behaviors:
 *
 * - Autonomous enemy attacks (usually triggered by pollution) can only attack within chunks that
 * contain at least one entity that is both a building and a player-creation.
 *
 * - Enemy expansion considers entities that are both buildings and player-creations as "enemy"
 * entities that may block expansion.
 */
public typealias EntityPrototypeFlags = List<EntityPrototypeFlagsValues>

/**
 * How far (in tiles) entities should be rendered outside the visible area of the screen.
 */
@Serializable(EntityRendererSearchBoxLimits.Serializer::class)
public open class EntityRendererSearchBoxLimits : JsonReader() {
  /**
   * Min value 6, max value 15. Min value 6 to compensate for shadows.
   */
  public val left: UByte by fromJson()

  /**
   * Min value 3, max value 15.
   */
  public val top: UByte by fromJson()

  /**
   * Min value 3, max value 15.
   */
  public val right: UByte by fromJson()

  /**
   * Min value 4, max value 15. Min value 4 to compensate for tall entities like electric poles.
   */
  public val bottom: UByte by fromJson()

  public object Serializer :
      JsonReaderSerializer<EntityRendererSearchBoxLimits>(EntityRendererSearchBoxLimits::class)
}

@Serializable
public enum class EntityTransferTipTriggerTransfer {
  `in`,
  `out`,
}

@Serializable(EntityTransferTipTrigger.Serializer::class)
@SerialName("entity-transfer")
public open class EntityTransferTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public val transfer: EntityTransferTipTriggerTransfer? by fromJson()

  public object Serializer :
      JsonReaderSerializer<EntityTransferTipTrigger>(EntityTransferTipTrigger::class)
}

/**
 * The name of an [EquipmentCategory](prototype:EquipmentCategory).
 */
public typealias EquipmentCategoryID = String

/**
 * The name of an [EquipmentGridPrototype](prototype:EquipmentGridPrototype).
 */
public typealias EquipmentGridID = String

/**
 * The name of an [EquipmentPrototype](prototype:EquipmentPrototype).
 */
public typealias EquipmentID = String

@Serializable
public enum class EquipmentShapeType {
  full,
  manual,
}

/**
 * The shape and dimensions of an equipment module.
 */
@Serializable(EquipmentShape.Serializer::class)
public open class EquipmentShape : JsonReader() {
  public val width: UInt by fromJson()

  public val height: UInt by fromJson()

  /**
   * The shape. When using "manual", `points` must be defined.
   */
  public val type: EquipmentShapeType by fromJson()

  /**
   * Only used when when `type` is `"manual"`. Each inner array is a "position" inside width×height
   * of the equipment. Each positions that is defined is a filled squares of the equipment shape. `{0,
   * 0}` is the upper left corner of the equipment.
   */
  public val points: List<List<UInt>>? by fromJson()

  public object Serializer : JsonReaderSerializer<EquipmentShape>(EquipmentShape::class)
}

@Serializable(ExplosionDefinitionValues.Serializer::class)
public open class ExplosionDefinitionValues : JsonReader() {
  public val name: EntityID by fromJson()

  public val offset: Vector? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ExplosionDefinitionValues>(ExplosionDefinitionValues::class)
}

public typealias ExplosionDefinition = UnknownUnion

@Serializable(FactorioBasisNoiseArguments.Serializer::class)
public open class FactorioBasisNoiseArguments : JsonReader() {
  public val x: NoiseNumber by fromJson()

  public val y: NoiseNumber by fromJson()

  /**
   * Integer between 0 and 4 294 967 295 (inclusive) used to populate the backing random noise.
   */
  public val seed0: ConstantNoiseNumber by fromJson()

  /**
   * Integer between 0 and 255 (inclusive) used to provide extra randomness when sampling.
   */
  public val seed1: ConstantNoiseNumber by fromJson()

  /**
   * `x` and `y` will be multiplied by this value before sampling.
   */
  public val input_scale: ConstantNoiseNumber? by fromJson()

  /**
   * The output will be multiplied by this value before returning.
   */
  public val output_scale: ConstantNoiseNumber? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FactorioBasisNoiseArguments>(FactorioBasisNoiseArguments::class)
}

@Serializable(FactorioMultioctaveNoiseArguments.Serializer::class)
public open class FactorioMultioctaveNoiseArguments : JsonReader() {
  public val x: NoiseNumber by fromJson()

  public val y: NoiseNumber by fromJson()

  /**
   * How strong is each layer compared to the next larger one.
   */
  public val persistence: ConstantNoiseNumber by fromJson()

  /**
   * Integer between 0 and 4 294 967 295 (inclusive) used to populate the backing random noise.
   */
  public val seed0: ConstantNoiseNumber by fromJson()

  /**
   * Integer between 0 and 255 (inclusive) used to provide extra randomness when sampling.
   */
  public val seed1: ConstantNoiseNumber by fromJson()

  /**
   * `x` and `y` will be multiplied by this value before sampling.
   */
  public val input_scale: ConstantNoiseNumber? by fromJson()

  /**
   * The output will be multiplied by this value before returning.
   */
  public val output_scale: ConstantNoiseNumber? by fromJson()

  /**
   * How many layers of noise at different scales to sum.
   */
  public val octaves: ConstantNoiseNumber by fromJson()

  public object Serializer :
      JsonReaderSerializer<FactorioMultioctaveNoiseArguments>(FactorioMultioctaveNoiseArguments::class)
}

@Serializable(FactorioQuickMultioctaveNoiseArguments.Serializer::class)
public open class FactorioQuickMultioctaveNoiseArguments : JsonReader() {
  public val x: NoiseNumber by fromJson()

  public val y: NoiseNumber by fromJson()

  public val seed0: ConstantNoiseNumber by fromJson()

  public val seed1: ConstantNoiseNumber by fromJson()

  public val input_scale: ConstantNoiseNumber? by fromJson()

  public val output_scale: ConstantNoiseNumber? by fromJson()

  public val octaves: ConstantNoiseNumber by fromJson()

  public val octave_input_scale_multiplier: ConstantNoiseNumber? by fromJson()

  public val octave_output_scale_multiplier: ConstantNoiseNumber? by fromJson()

  public val octave_seed0_shift: ConstantNoiseNumber? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FactorioQuickMultioctaveNoiseArguments>(FactorioQuickMultioctaveNoiseArguments::class)
}

@Serializable(FastBeltBendTipTrigger.Serializer::class)
@SerialName("fast-belt-bend")
public open class FastBeltBendTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FastBeltBendTipTrigger>(FastBeltBendTipTrigger::class)
}

@Serializable(FastReplaceTipTrigger.Serializer::class)
@SerialName("fast-replace")
public open class FastReplaceTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public val source: EntityID? by fromJson()

  public val target: EntityID? by fromJson()

  public val match_type_only: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FastReplaceTipTrigger>(FastReplaceTipTrigger::class)
}

/**
 * A slash `"/"` is always used as the directory delimiter. A path always begins with the
 * specification of a root, which can be one of three formats:
 *
 * - **core**: A path starting with `__core__` will access the resources in the data/core directory,
 * these resources are always accessible regardless of mod specifications.
 *
 * - **base**: A path starting with `__base__` will access the resources in the base mod in
 * data/base directory. These resources are usually available, as long as the base mod isn't
 * removed/deactivated.
 *
 * - **mod path**: The format `__<mod-name>__` is placeholder for root of any other mod
 * (mods/<mod-name>), and is accessible as long as the mod is active.
 */
public typealias FileName = String

@Serializable(FlameThrowerExplosionTriggerDelivery.Serializer::class)
@SerialName("flame-thrower")
public open class FlameThrowerExplosionTriggerDelivery : TriggerDeliveryItem(), TriggerDelivery {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [FlameThrowerExplosionPrototype](prototype:FlameThrowerExplosionPrototype).
   */
  public val explosion: EntityID by fromJson()

  public val starting_distance: Double by fromJson()

  public val direction_deviation: Double? by fromJson()

  public val speed_deviation: Double? by fromJson()

  public val starting_frame_fraciton_deviation: Double? by fromJson()

  public val projectile_starting_speed: Double? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FlameThrowerExplosionTriggerDelivery>(FlameThrowerExplosionTriggerDelivery::class)
}

@Serializable(FlowStyleSpecification.Serializer::class)
@SerialName("flow_style")
public open class FlowStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val max_on_row: Int? by fromJson()

  public val horizontal_spacing: Int? by fromJson()

  public val vertical_spacing: Int? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FlowStyleSpecification>(FlowStyleSpecification::class)
}

/**
 * Used to set the fluid amount an entity can hold, as well as the connection points for pipes
 * leading into and out of the entity.
 *
 * Entities can have multiple fluidboxes. These can be part of a
 * [FluidEnergySource](prototype:FluidEnergySource), or be specified directly in the entity prototype.
 *
 * A fluidbox can store only one type of fluid at a time. However, a fluid system (ie. multiple
 * connected fluid boxes) can contain multiple different fluids, see [Fluid
 * mixing](https://wiki.factorio.com/Fluid_system#Fluid_mixing).
 */
@Serializable(FluidBox.Serializer::class)
public open class FluidBox : JsonReader() {
  /**
   * Connection points to connect to other fluidboxes. This is also marked as blue arrows in alt
   * mode. Fluid may flow in or out depending on the `type` field of each connection.
   *
   * Connection points may depend on the direction the entity is facing. These connection points
   * cannot share positions with one another or the connection points of another fluid box belonging to
   * the same entity.
   *
   * Can't have more than 255 connections.
   */
  public val pipe_connections: List<PipeConnectionDefinition> by fromJson()

  /**
   * Must be greater than `0`. The total fluid capacity of the fluid box is `base_area × height ×
   * 100`.
   */
  public val base_area: Double? by fromJson()

  /**
   * Base level is the elevation of the invisible fluid box. `0` is ground level.
   *
   * For example, if the base level is `-1` and the height is `1`, it puts the top of the fluid box
   * at the bottom of a pipe connection with base_level `0` and height `1`. This means fluid "falls" in
   * to the fluid box, and can't get out.
   *
   * For example, if the base level is `1`, it puts the bottom of the fluid box at the top of a pipe
   * connection with base_level `0` and height `1`. This means fluid "falls" out of the fluid box, but
   * fluids already outside can't get into it.
   */
  public val base_level: Float? by fromJson()

  /**
   * Must be greater than `0`. The total fluid capacity of the fluid box is `base_area × height ×
   * 100`.
   */
  public val height: Double? by fromJson()

  /**
   * Can be used to specify which fluid is allowed to enter this fluid box. See
   * [here](https://forums.factorio.com/viewtopic.php?f=28&t=46302).
   */
  public val filter: FluidID? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  /**
   * Hides the blue input/output arrows and icons at each connection point.
   */
  public val hide_connection_info: Boolean? by fromJson()

  /**
   * The pictures to show when another fluid box connects to this one.
   */
  public val pipe_covers: Sprite4Way? by fromJson()

  public val pipe_picture: Sprite4Way? by fromJson()

  /**
   * The minimum temperature allowed into the fluidbox. Only applied if a `filter` is specified.
   */
  public val minimum_temperature: Double? by fromJson()

  /**
   * The maximum temperature allowed into the fluidbox. Only applied if a `filter` is specified.
   */
  public val maximum_temperature: Double? by fromJson()

  public val production_type: ProductionType? by fromJson()

  /**
   * Set the secondary draw order for all orientations. Used to determine render order for sprites
   * with the same `render_layer` in the same position. Sprites with a higher `secondary_draw_order`
   * are drawn on top.
   */
  public val secondary_draw_order: Byte? by fromJson()

  /**
   * Set the secondary draw order for each orientation. Used to determine render order for sprites
   * with the same `render_layer` in the same position. Sprites with a higher `secondary_draw_order`
   * are drawn on top.
   */
  public val secondary_draw_orders: FluidBoxSecondaryDrawOrders? by fromJson()

  public object Serializer : JsonReaderSerializer<FluidBox>(FluidBox::class)
}

@Serializable(FluidBoxSecondaryDrawOrders.Serializer::class)
public open class FluidBoxSecondaryDrawOrders : JsonReader() {
  public val north: Byte? by fromJson()

  public val east: Byte? by fromJson()

  public val south: Byte? by fromJson()

  public val west: Byte? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FluidBoxSecondaryDrawOrders>(FluidBoxSecondaryDrawOrders::class)
}

@Serializable(FluidEnergySource.Serializer::class)
@SerialName("fluid")
public open class FluidEnergySource : BaseEnergySource(), EnergySource {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * All standard fluid box configurations are acceptable, but the type must be `"input"` or
   * `"input-output"` to function correctly. `scale_fluid_usage = true`, `fluid_usage_per_tick`, or a
   * filter on the fluidbox must be set to be able to calculate the fluid usage of the energy source.
   */
  public val fluid_box: FluidBox by fromJson()

  public val smoke: List<SmokeSource>? by fromJson()

  public val light_flicker: LightFlickeringDefinition? by fromJson()

  /**
   * `1` means 100% effectivity. Must be greater than `0`. Multiplier of the energy output.
   */
  public val effectivity: Double? by fromJson()

  /**
   * If set to `true`, the energy source will calculate power based on the fluid's `fuel_value`,
   * else it will calculate based on fluid temperature.
   */
  public val burns_fluid: Boolean? by fromJson()

  /**
   * If set to `true`, the energy source will consume as much fluid as required to produce the
   * desired power, otherwise it will consume as much as it is allowed to, wasting any excess.
   */
  public val scale_fluid_usage: Boolean? by fromJson()

  /**
   * Property is only used when `burns_fluid` is `true` and the fluid has a
   * [fuel_value](prototype:FluidPrototype::fuel_value) of `0`, or when `burns_fluid` is `false` and
   * the fluid is at its `default_temperature`.
   *
   * In those cases, this property determines whether the fluid should be destroyed, meaning that
   * the fluid is consumed at the rate of `fluid_usage_per_tick`, without producing any power.
   */
  public val destroy_non_fuel_fluid: Boolean? by fromJson()

  /**
   * The number of fluid units the energy source uses per tick. If used with `scale_fluid_usage`,
   * this specifies the maximum. If this value is not set, `scale_energy_usage` is `false` and a fluid
   * box filter is set, the game will attempt to calculate this value from the fluid box filter's
   * fluid's `fuel_value` or `heat_capacity` and the entity's `energy_usage`. If `burns_fluid` is
   * `false`, `maximum_temperature` will also be used. If the attempt of the game to calculate this
   * value fails (`scale_energy_usage` is `false` and a fluid box filter is set), then
   * `scale_energy_usage` will be forced to `true`, to prevent the energy source from being an infinite
   * fluid sink. More context [on the forums](https://forums.factorio.com/90613).
   */
  public val fluid_usage_per_tick: Double? by fromJson()

  /**
   * `0` means unlimited maximum temperature. If this is non-zero while `scale_fluid_usage` is
   * `false` and `fluid_usage_per_tick` is not specified, the game will use this value to calculate
   * `fluid_usage_per_tick`. To do that, the filter on the `fluid_box` must be set.
   *
   * Only loaded if `burns_fluid` is `false`.
   */
  public val maximum_temperature: Double? by fromJson()

  public object Serializer : JsonReaderSerializer<FluidEnergySource>(FluidEnergySource::class)
}

/**
 * The name of a [FluidPrototype](prototype:FluidPrototype).
 */
public typealias FluidID = String

/**
 * A fluid ingredient definition.
 */
@Serializable(FluidIngredientPrototype.Serializer::class)
@SerialName("fluid")
public open class FluidIngredientPrototype : JsonReader(), IngredientPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * The name of a [FluidPrototype](prototype:FluidPrototype).
   */
  public val name: FluidID by fromJson()

  /**
   * Can not be `<= 0`.
   */
  public val amount: Double by fromJson()

  /**
   * Sets the expected temperature of the fluid ingredient.
   */
  public val temperature: Double? by fromJson()

  /**
   * If `temperature` is not set, this sets the expected minimum temperature of the fluid
   * ingredient.
   */
  public val minimum_temperature: Double? by fromJson()

  /**
   * If `temperature` is not set, this sets the expected maximum temperature of the fluid
   * ingredient.
   */
  public val maximum_temperature: Double? by fromJson()

  /**
   * Amount of this ingredient that should not be included in the fluid consumption statistics.
   * Usually used together with an equal catalyst amount on the "product" of the catalyst in the
   * recipe.
   *
   * If this FluidIngredientPrototype is used in a recipe, the `catalyst_amount` is calculated
   * automatically based on the [RecipePrototype::ingredients](prototype:RecipePrototype::ingredients)
   * and [RecipePrototype::results](prototype:RecipePrototype::results). See
   * [here](https://factorio.com/blog/post/fff-256).
   */
  public val catalyst_amount: Double? by fromJson()

  /**
   * Used to specify which
   * [CraftingMachinePrototype::fluid_boxes](prototype:CraftingMachinePrototype::fluid_boxes) this
   * ingredient should use. It will use this one fluidbox. The index is 1-based and separate for input
   * and output fluidboxes.
   */
  public val fluidbox_index: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FluidIngredientPrototype>(FluidIngredientPrototype::class)
}

/**
 * A fluid product definition.
 */
@Serializable(FluidProductPrototype.Serializer::class)
@SerialName("fluid")
public open class FluidProductPrototype : JsonReader(), ProductPrototype {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * The name of a [FluidPrototype](prototype:FluidPrototype).
   */
  public val name: FluidID by fromJson()

  /**
   * Can not be `< 0`.
   */
  public val amount: Double? by fromJson()

  /**
   * Only loaded, and mandatory if `amount` is not defined.
   *
   * Can not be `< 0`.
   */
  public val amount_min: MaterialAmountType? by fromJson()

  /**
   * Only loaded, and mandatory if `amount` is not defined.
   *
   * If set to a number that is less than `amount_min`, the game will use `amount_min` instead.
   */
  public val amount_max: MaterialAmountType? by fromJson()

  /**
   * Value between 0 and 1, `0` for 0% chance and `1` for 100% chance.
   *
   * The effect of probability is no product, or a linear distribution on [min, max]. For a recipe
   * with probability `p`, amount_min `min`, and amount_max `max`, the Expected Value of this product
   * can be expressed as `p * (0.5 * (max + min))`. This is what will be shown in a recipe tooltip. The
   * effect of `catalyst_amount` on the product is not shown.
   *
   * When `amount_min` and `amount_max` are not provided, `amount` applies as min and max. The
   * Expected Value simplifies to `p * amount`, providing `0` product, or `amount` product, on recipe
   * completion.
   */
  public val probability: Double? by fromJson()

  /**
   * Amount that should not be affected by productivity modules (not yielded from bonus production)
   * and should not be included in the fluid production statistics.
   *
   * If this FluidProductPrototype is used in a recipe, the `catalyst_amount` is calculated
   * automatically based on the [RecipePrototype::ingredients](prototype:RecipePrototype::ingredients)
   * and [RecipePrototype::results](prototype:RecipePrototype::results). See
   * [here](https://factorio.com/blog/post/fff-256).
   */
  public val catalyst_amount: Double? by fromJson()

  /**
   * The temperature of the fluid product.
   */
  public val temperature: Double? by fromJson()

  /**
   * Used to specify which
   * [CraftingMachinePrototype::fluid_boxes](prototype:CraftingMachinePrototype::fluid_boxes) this
   * product should use. It will use this one fluidbox. The index is 1-based and separate for input and
   * output fluidboxes.
   */
  public val fluidbox_index: UInt? by fromJson()

  /**
   * When hovering over a recipe in the crafting menu the recipe tooltip will be shown. An
   * additional item tooltip will be shown for every product, as a separate tooltip, if the item
   * tooltip has a description and/or properties to show and if `show_details_in_recipe_tooltip` is
   * `true`.
   */
  public val show_details_in_recipe_tooltip: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FluidProductPrototype>(FluidProductPrototype::class)
}

@Serializable(FluidWagonConnectorGraphics.Serializer::class)
public open class FluidWagonConnectorGraphics : JsonReader() {
  public val load_animations: PumpConnectorGraphics by fromJson()

  public val unload_animations: PumpConnectorGraphics by fromJson()

  public object Serializer :
      JsonReaderSerializer<FluidWagonConnectorGraphics>(FluidWagonConnectorGraphics::class)
}

@Serializable(FollowerRobotLifetimeModifier.Serializer::class)
@SerialName("follower-robot-lifetime")
public open class FollowerRobotLifetimeModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FollowerRobotLifetimeModifier>(FollowerRobotLifetimeModifier::class)
}

@Serializable(FootprintParticle.Serializer::class)
public open class FootprintParticle : JsonReader() {
  /**
   * The tiles this footprint particle is shown on when the player walks over them.
   */
  public val tiles: List<TileID> by fromJson()

  /**
   * The name of the particle that should be created when the character walks on the defined tiles.
   */
  public val particle_name: ParticleID? by fromJson()

  /**
   * Whether this footprint particle should be the default particle that is used for `tiles` that
   * don't have an associated footprint particle.
   */
  public val use_as_default: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<FootprintParticle>(FootprintParticle::class)
}

@Serializable(FootstepTriggerEffectItem.Serializer::class)
public open class FootstepTriggerEffectItem : CreateParticleTriggerEffectItem() {
  public val tiles: List<TileID> by fromJson()

  /**
   * Can be used to specify multiple CreateParticleTriggerEffectItems. If this property is defined,
   * all properties inherited from CreateParticleTriggerEffectItem are ignored.
   */
  public val actions: List<CreateParticleTriggerEffectItem>? by fromJson()

  /**
   * When `true`, the trigger(s) defined in `actions` are the default triggers for tiles that don't
   * have an associated footstep particle trigger. (ie. don't show up in one of the "tiles" lists).
   */
  public val use_as_default: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FootstepTriggerEffectItem>(FootstepTriggerEffectItem::class)
}

public typealias FootstepTriggerEffectList = List<FootstepTriggerEffectItem>

/**
 * One of the following values:
 */
@Serializable
public enum class ForceCondition {
  all,
  enemy,
  ally,
  friend,
  `not-friend`,
  same,
  `not-same`,
}

@Serializable(FrameStyleSpecification.Serializer::class)
@SerialName("frame_style")
public open class FrameStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val graphical_set: ElementImageSet? by fromJson()

  public val horizontal_flow_style: HorizontalFlowStyleSpecification? by fromJson()

  public val vertical_flow_style: VerticalFlowStyleSpecification? by fromJson()

  public val header_flow_style: HorizontalFlowStyleSpecification? by fromJson()

  public val header_filler_style: EmptyWidgetStyleSpecification? by fromJson()

  public val title_style: LabelStyleSpecification? by fromJson()

  public val use_header_filler: Boolean? by fromJson()

  public val drag_by_title: Boolean? by fromJson()

  public val header_background: ElementImageSet? by fromJson()

  public val background_graphical_set: ElementImageSet? by fromJson()

  public val border: BorderImageSet? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FrameStyleSpecification>(FrameStyleSpecification::class)
}

@Serializable(FrequencySizeRichness.Serializer::class)
public open class FrequencySizeRichness : JsonReader() {
  public val frequency: MapGenSize? by fromJson()

  public val size: MapGenSize? by fromJson()

  public val richness: MapGenSize? by fromJson()

  public object Serializer :
      JsonReaderSerializer<FrequencySizeRichness>(FrequencySizeRichness::class)
}

/**
 * The name of a [FuelCategory](prototype:FuelCategory).
 */
public typealias FuelCategoryID = String

@Serializable(GameControllerVibrationData.Serializer::class)
public open class GameControllerVibrationData : JsonReader() {
  /**
   * Vibration intensity must be between 0 and 1.
   */
  public val low_frequency_vibration_intensity: Float? by fromJson()

  /**
   * Vibration intensity must be between 0 and 1.
   */
  public val high_frequency_vibration_intensity: Float? by fromJson()

  /**
   * Duration in milliseconds.
   */
  public val duration: UInt? by fromJson()

  public val play_for: PlayFor? by fromJson()

  public object Serializer :
      JsonReaderSerializer<GameControllerVibrationData>(GameControllerVibrationData::class)
}

@Serializable(GateOverRailBuildTipTrigger.Serializer::class)
@SerialName("gate-over-rail-build")
public open class GateOverRailBuildTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<GateOverRailBuildTipTrigger>(GateOverRailBuildTipTrigger::class)
}

@Serializable(GhostTimeToLiveModifier.Serializer::class)
@SerialName("ghost-time-to-live")
public open class GhostTimeToLiveModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<GhostTimeToLiveModifier>(GhostTimeToLiveModifier::class)
}

@Serializable(GiveItemModifier.Serializer::class)
@SerialName("give-item")
public open class GiveItemModifier : BaseModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public val item: ItemID by fromJson()

  /**
   * Must be `> 0`.
   */
  public val count: ItemCountType? by fromJson()

  public object Serializer : JsonReaderSerializer<GiveItemModifier>(GiveItemModifier::class)
}

@Serializable(GlowStyleSpecification.Serializer::class)
@SerialName("glow_style")
public open class GlowStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val image_set: ElementImageSet? by fromJson()

  public object Serializer :
      JsonReaderSerializer<GlowStyleSpecification>(GlowStyleSpecification::class)
}

@Serializable(GraphStyleSpecification.Serializer::class)
@SerialName("graph_style")
public open class GraphStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val background_color: Color? by fromJson()

  public val line_colors: List<Color>? by fromJson()

  public val horizontal_label_style: LabelStyleSpecification? by fromJson()

  public val vertical_label_style: LabelStyleSpecification? by fromJson()

  public val minimal_horizontal_label_spacing: UInt? by fromJson()

  public val minimal_vertical_label_spacing: UInt? by fromJson()

  public val horizontal_labels_margin: UInt? by fromJson()

  public val vertical_labels_margin: UInt? by fromJson()

  public val graph_top_margin: UInt? by fromJson()

  public val graph_right_margin: UInt? by fromJson()

  public val data_line_highlight_distance: UInt? by fromJson()

  public val selection_dot_radius: UInt? by fromJson()

  public val grid_lines_color: Color? by fromJson()

  public val guide_lines_color: Color? by fromJson()

  public object Serializer :
      JsonReaderSerializer<GraphStyleSpecification>(GraphStyleSpecification::class)
}

@Serializable(GroupAttackTipTrigger.Serializer::class)
@SerialName("group-attack")
public open class GroupAttackTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<GroupAttackTipTrigger>(GroupAttackTipTrigger::class)
}

@Serializable(GunShift4Way.Serializer::class)
public open class GunShift4Way : JsonReader() {
  public val north: Vector by fromJson()

  public val east: Vector? by fromJson()

  public val south: Vector? by fromJson()

  public val west: Vector? by fromJson()

  public object Serializer : JsonReaderSerializer<GunShift4Way>(GunShift4Way::class)
}

@Serializable(GunSpeedModifier.Serializer::class)
@SerialName("gun-speed")
public open class GunSpeedModifier : BaseModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  /**
   * Name of the [AmmoCategory](prototype:AmmoCategory) that is affected.
   */
  public val ammo_category: AmmoCategoryID by fromJson()

  /**
   * Modification value, which will be added to the current gun speed modifier upon researching.
   */
  public val modifier: Double by fromJson()

  public object Serializer : JsonReaderSerializer<GunSpeedModifier>(GunSpeedModifier::class)
}

/**
 * Used to specify heat capacity properties without a [heat energy
 * source](prototype:HeatEnergySource).
 */
@Serializable(HeatBuffer.Serializer::class)
public open class HeatBuffer : JsonReader() {
  /**
   * Must be >= `default_temperature`.
   */
  public val max_temperature: Double by fromJson()

  public val specific_heat: Energy by fromJson()

  public val max_transfer: Energy by fromJson()

  public val default_temperature: Double? by fromJson()

  public val min_temperature_gradient: Double? by fromJson()

  /**
   * Must be >= `default_temperature` and <= `max_temperature`.
   */
  public val min_working_temperature: Double? by fromJson()

  public val minimum_glow_temperature: Float? by fromJson()

  public val pipe_covers: Sprite4Way? by fromJson()

  public val heat_pipe_covers: Sprite4Way? by fromJson()

  public val heat_picture: Sprite4Way? by fromJson()

  public val heat_glow: Sprite4Way? by fromJson()

  /**
   * May contain up to 32 connections.
   */
  public val connections: List<HeatConnection>? by fromJson()

  public object Serializer : JsonReaderSerializer<HeatBuffer>(HeatBuffer::class)
}

/**
 * Defines the connections for [HeatEnergySource](prototype:HeatEnergySource) and
 * [HeatBuffer](prototype:HeatBuffer).
 */
@Serializable(HeatConnection.Serializer::class)
public open class HeatConnection : JsonReader() {
  /**
   * The location of the heat pipe connection, relative to the center of the entity in the
   * north-facing direction.
   */
  public val position: MapPosition by fromJson()

  /**
   * The "outward" direction of this heat connection. For a connection to succeed, the other heat
   * connection must face the opposite direction (a south-facing connection needs a north-facing
   * connection to succeed). A connection rotates with the entity.
   */
  public val direction: Direction by fromJson()

  public object Serializer : JsonReaderSerializer<HeatConnection>(HeatConnection::class)
}

@Serializable(HeatEnergySource.Serializer::class)
@SerialName("heat")
public open class HeatEnergySource : BaseEnergySource(), EnergySource {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Must be >= `default_temperature`.
   */
  public val max_temperature: Double by fromJson()

  public val specific_heat: Energy by fromJson()

  public val max_transfer: Energy by fromJson()

  public val default_temperature: Double? by fromJson()

  public val min_temperature_gradient: Double? by fromJson()

  /**
   * Must be >= `default_temperature` and <= `max_temperature`.
   */
  public val min_working_temperature: Double? by fromJson()

  public val minimum_glow_temperature: Float? by fromJson()

  public val pipe_covers: Sprite4Way? by fromJson()

  public val heat_pipe_covers: Sprite4Way? by fromJson()

  public val heat_picture: Sprite4Way? by fromJson()

  public val heat_glow: Sprite4Way? by fromJson()

  /**
   * May contain up to 32 connections.
   */
  public val connections: List<HeatConnection>? by fromJson()

  /**
   * Heat energy sources do not support producing pollution.
   */
  override val emissions_per_minute: Double? by fromJson()

  public object Serializer : JsonReaderSerializer<HeatEnergySource>(HeatEnergySource::class)
}

@Serializable
public enum class HorizontalAlign {
  left,
  center,
  right,
}

@Serializable(HorizontalFlowStyleSpecification.Serializer::class)
@SerialName("horizontal_flow_style")
public open class HorizontalFlowStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val horizontal_spacing: Int? by fromJson()

  public object Serializer :
      JsonReaderSerializer<HorizontalFlowStyleSpecification>(HorizontalFlowStyleSpecification::class)
}

@Serializable(HorizontalScrollBarStyleSpecification.Serializer::class)
@SerialName("horizontal_scrollbar_style")
public open class HorizontalScrollBarStyleSpecification : ScrollBarStyleSpecification(),
    StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public object Serializer :
      JsonReaderSerializer<HorizontalScrollBarStyleSpecification>(HorizontalScrollBarStyleSpecification::class)
}

/**
 * Icon layering follows the following rules:
 *
 * - The rendering order of the individual icons follows the array order: Later added icons (higher
 * index) are drawn on top of previously added icons (lower index).
 *
 * - Only the first icon layer will display a shadow.
 *
 * - When the final icon is displayed with transparency (e.g. a faded out alert), the icon layer
 * overlap may look [undesirable](https://forums.factorio.com/viewtopic.php?p=575844#p575844).
 *
 * - The final combination of icons will always be resized in GUI based on the first icon layer's
 * size, but won't be resized when displayed on machines in alt-mode. For example: recipe first icon
 * layer is size 128, scale 1, the icon group will be displayed at resolution /4 to fit the 32px GUI
 * boxes, but will be displayed 4 times as large on buildings.
 *
 * - Shift values are based on final size (`icon_size * scale`) of the first icon layer.
 */
@Serializable(IconData.Serializer::class)
public open class IconData : JsonReader() {
  /**
   * Path to the icon file.
   */
  public val icon: FileName by fromJson()

  /**
   * The size of the square icon, in pixels. E.g. `32` for a 32px by 32px icon.
   *
   * Mandatory if `icon_size` is not specified outside of `icons`.
   */
  public val icon_size: SpriteSizeType? by fromJson()

  /**
   * The tint to apply to the icon.
   */
  public val tint: Color? by fromJson()

  /**
   * Used to offset the icon "layer" from the overall icon. The shift is applied from the center (so
   * negative shifts are left and up, respectively). Shift values are based on final size (`icon_size *
   * scale`) of the first icon.
   */
  public val shift: Vector? by fromJson()

  /**
   * Defaults to `32/icon_size` for items and recipes, and `256/icon_size` for technologies.
   *
   * Specifies the scale of the icon on the GUI scale. A scale of `2` means that the icon will be
   * two times bigger on screen (and thus more pixelated).
   */
  public val scale: Double? by fromJson()

  /**
   * Icons of reduced size will be used at decreased scale.
   */
  public val icon_mipmaps: IconMipMapType? by fromJson()

  public object Serializer : JsonReaderSerializer<IconData>(IconData::class)
}

/**
 * Icons of reduced size will be used at decreased scale. 0 or 1 mipmaps is a single image. The file
 * must contain half-size images with a geometric-ratio, for each mipmap level. Each next level is
 * aligned to the upper-left corner, with no extra padding. Example sequence: `128x128@(0,0)`,
 * `64x64@(128,0)`, `32x32@(192,0)` is three mipmaps.
 *
 * See [here](https://factorio.com/blog/post/fff-291) for more about the visual effects of icon
 * mipmaps.
 */
public typealias IconMipMapType = UByte

@Serializable(ImageStyleSpecification.Serializer::class)
@SerialName("image_style")
public open class ImageStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val graphical_set: ElementImageSet? by fromJson()

  public val stretch_image_to_widget_size: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ImageStyleSpecification>(ImageStyleSpecification::class)
}

/**
 * Defaults to loading ingredients as items. This allows
 * [ItemIngredientPrototype](prototype:ItemIngredientPrototype) to load in a shorthand array format.
 */
@Serializable
public sealed interface IngredientPrototype

@Serializable(InsertItemTriggerEffectItem.Serializer::class)
@SerialName("insert-item")
public open class InsertItemTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of the [ItemPrototype](prototype:ItemPrototype) that should be created.
   */
  public val item: ItemID by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<InsertItemTriggerEffectItem>(InsertItemTriggerEffectItem::class)
}

@Serializable(InserterStackSizeBonusModifier.Serializer::class)
@SerialName("inserter-stack-size-bonus")
public open class InserterStackSizeBonusModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<InserterStackSizeBonusModifier>(InserterStackSizeBonusModifier::class)
}

@Serializable(InstantTriggerDelivery.Serializer::class)
@SerialName("instant")
public open class InstantTriggerDelivery : TriggerDeliveryItem(), TriggerDelivery {
  public val type: UnknownStringLiteral by fromJson()

  public object Serializer :
      JsonReaderSerializer<InstantTriggerDelivery>(InstantTriggerDelivery::class)
}

@Serializable(InterruptibleSound.Serializer::class)
public open class InterruptibleSound : JsonReader() {
  public val sound: Sound by fromJson()

  public val fade_ticks: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<InterruptibleSound>(InterruptibleSound::class)
}

@Serializable(InvokeTileEffectTriggerEffectItem.Serializer::class)
@SerialName("invoke-tile-trigger")
public open class InvokeTileEffectTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val tile_collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<InvokeTileEffectTriggerEffectItem>(InvokeTileEffectTriggerEffectItem::class)
}

public typealias ItemCountType = UInt

/**
 * The name of an [ItemGroup](prototype:ItemGroup).
 */
public typealias ItemGroupID = String

/**
 * The name of an [ItemPrototype](prototype:ItemPrototype).
 */
public typealias ItemID = String

/**
 * An item ingredient definition. It can be specified as a table with named or numbered keys, but
 * not a mix of both. If this is specified as a table with numbered keys then the first value is the
 * item name and the second is the amount.
 */
@Serializable(ItemIngredientPrototypeValues.Serializer::class)
@SerialName("item")
public open class ItemIngredientPrototypeValues : JsonReader(), IngredientPrototype {
  public val type: UnknownStringLiteral? by fromJson()

  public val name: ItemID by fromJson()

  public val amount: UShort by fromJson()

  /**
   * Amount of this ingredient that should not be included in the item consumption statistics.
   * Usually used together with an equal catalyst amount on the "product" of the catalyst in the
   * recipe.
   *
   * If this fluid is used in a recipe, the `catalyst_amount` is calculated automatically based on
   * the [RecipePrototype::ingredients](prototype:RecipePrototype::ingredients) and
   * [RecipePrototype::results](prototype:RecipePrototype::results). See
   * [here](https://factorio.com/blog/post/fff-256).
   */
  public val catalyst_amount: UShort? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ItemIngredientPrototypeValues>(ItemIngredientPrototypeValues::class)
}

/**
 * An item ingredient definition. It can be specified as a table with named or numbered keys, but
 * not a mix of both. If this is specified as a table with numbered keys then the first value is the
 * item name and the second is the amount.
 */
public typealias ItemIngredientPrototype = UnknownUnion

/**
 * An item product definition. It can be specified as a table with named or numbered keys, but not a
 * mix of both. If this is specified as a table with numbered keys then the first value is the item
 * name and the second is the amount.
 */
@Serializable(ItemProductPrototypeValues.Serializer::class)
@SerialName("item")
public open class ItemProductPrototypeValues : JsonReader(), ProductPrototype {
  public val type: UnknownStringLiteral? by fromJson()

  /**
   * The name of an [ItemPrototype](prototype:ItemPrototype).
   */
  public val name: ItemID by fromJson()

  public val amount: UShort? by fromJson()

  /**
   * Only loaded, and mandatory if `amount` is not defined.
   */
  public val amount_min: UShort? by fromJson()

  /**
   * Only loaded, and mandatory if `amount` is not defined.
   *
   * If set to a number that is less than `amount_min`, the game will use `amount_min` instead.
   */
  public val amount_max: UShort? by fromJson()

  /**
   * Value between 0 and 1, `0` for 0% chance and `1` for 100% chance.
   *
   * The effect of probability is no product, or a linear distribution on [min, max]. For a recipe
   * with probability `p`, amount_min `min`, and amount_max `max`, the Expected Value of this product
   * can be expressed as `p * (0.5 * (max + min))`. This is what will be shown in a recipe tooltip. The
   * effect of `catalyst_amount` on the product is not shown.
   *
   * When `amount_min` and `amount_max` are not provided, `amount` applies as min and max. The
   * Expected Value simplifies to `p * amount`, providing `0` product, or `amount` product, on recipe
   * completion.
   */
  public val probability: Double? by fromJson()

  /**
   * Amount that should not be affected by productivity modules (not yielded from bonus production)
   * and should not be included in the item production statistics.
   *
   * If this item is used in a recipe, the `catalyst_amount` is calculated automatically based on
   * the [RecipePrototype::ingredients](prototype:RecipePrototype::ingredients) and
   * [RecipePrototype::results](prototype:RecipePrototype::results). See
   * [here](https://factorio.com/blog/post/fff-256).
   */
  public val catalyst_amount: UShort? by fromJson()

  /**
   * When hovering over a recipe in the crafting menu the recipe tooltip will be shown. An
   * additional item tooltip will be shown for every product, as a separate tooltip, if the item
   * tooltip has a description and/or properties to show and if `show_details_in_recipe_tooltip` is
   * `true`.
   */
  public val show_details_in_recipe_tooltip: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ItemProductPrototypeValues>(ItemProductPrototypeValues::class)
}

/**
 * An item product definition. It can be specified as a table with named or numbered keys, but not a
 * mix of both. If this is specified as a table with numbered keys then the first value is the item
 * name and the second is the amount.
 */
public typealias ItemProductPrototype = UnknownUnion

/**
 * An array containing the following values.
 */
@Serializable
public enum class ItemPrototypeFlagsValues {
  `draw-logistic-overlay`,
  hidden,
  `always-show`,
  `hide-from-bonus-gui`,
  `hide-from-fuel-tooltip`,
  `not-stackable`,
  `can-extend-inventory`,
  `primary-place-result`,
  `mod-openable`,
  `only-in-cursor`,
  spawnable,
}

/**
 * An array containing the following values.
 */
public typealias ItemPrototypeFlags = List<ItemPrototypeFlagsValues>

public typealias ItemStackIndex = UShort

/**
 * The name of an [ItemSubGroup](prototype:ItemSubGroup).
 */
public typealias ItemSubGroupID = String

/**
 * Item that when placed creates this entity/tile.
 */
@Serializable(ItemToPlace.Serializer::class)
public open class ItemToPlace : JsonReader() {
  /**
   * The item used to place this entity/tile.
   */
  public val item: ItemID by fromJson()

  /**
   * How many items are used to place one of this entity/tile. Can't be larger than the stack size
   * of the item.
   */
  public val count: UInt by fromJson()

  public object Serializer : JsonReaderSerializer<ItemToPlace>(ItemToPlace::class)
}

@Serializable(LabelStyleSpecification.Serializer::class)
@SerialName("label_style")
public open class LabelStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val font: String? by fromJson()

  public val font_color: Color? by fromJson()

  public val hovered_font_color: Color? by fromJson()

  public val game_controller_hovered_font_color: Color? by fromJson()

  public val clicked_font_color: Color? by fromJson()

  public val disabled_font_color: Color? by fromJson()

  public val rich_text_setting: RichTextSetting? by fromJson()

  public val single_line: Boolean? by fromJson()

  public val underlined: Boolean? by fromJson()

  public val rich_text_highlight_error_color: Color? by fromJson()

  public val rich_text_highlight_warning_color: Color? by fromJson()

  public val rich_text_highlight_ok_color: Color? by fromJson()

  public object Serializer :
      JsonReaderSerializer<LabelStyleSpecification>(LabelStyleSpecification::class)
}

@Serializable(LaboratoryProductivityModifier.Serializer::class)
@SerialName("laboratory-productivity")
public open class LaboratoryProductivityModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<LaboratoryProductivityModifier>(LaboratoryProductivityModifier::class)
}

@Serializable(LaboratorySpeedModifier.Serializer::class)
@SerialName("laboratory-speed")
public open class LaboratorySpeedModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<LaboratorySpeedModifier>(LaboratorySpeedModifier::class)
}

@Serializable(LayeredSoundValues.Serializer::class)
public open class LayeredSoundValues : JsonReader() {
  public val layers: List<Sound> by fromJson()

  public object Serializer : JsonReaderSerializer<LayeredSoundValues>(LayeredSoundValues::class)
}

public typealias LayeredSound = UnknownUnion

@Serializable
public enum class LightDefinitionType {
  basic,
  oriented,
}

/**
 * Specifies a light source. This is loaded either as a single light source or as an array of light
 * sources.
 */
@Serializable(LightDefinitionValues.Serializer::class)
public open class LightDefinitionValues : JsonReader() {
  public val type: LightDefinitionType? by fromJson()

  /**
   * Only loaded, and mandatory if `type` is `"oriented"`.
   */
  public val picture: Sprite? by fromJson()

  /**
   * Only loaded if `type` is `"oriented"`.
   */
  public val rotation_shift: RealOrientation? by fromJson()

  /**
   * Brightness of the light in the range `[0, 1]`, where `0` is no light and `1` is the maximum
   * light.
   */
  public val intensity: Float by fromJson()

  /**
   * The radius of the light in tiles. Note that the light gets darker near the edges, so the
   * effective size of the light will appear to be smaller.
   */
  public val size: Float by fromJson()

  public val source_orientation_offset: RealOrientation? by fromJson()

  public val add_perspective: Boolean? by fromJson()

  public val shift: Vector? by fromJson()

  /**
   * Color of the light.
   */
  public val color: Color? by fromJson()

  public val minimum_darkness: Float? by fromJson()

  public object Serializer :
      JsonReaderSerializer<LightDefinitionValues>(LightDefinitionValues::class)
}

/**
 * Specifies a light source. This is loaded either as a single light source or as an array of light
 * sources.
 */
public typealias LightDefinition = ItemOrList<LightDefinitionValues>

/**
 * Specifies the light flicker. Note that this defaults to "showing a white light" instead of the
 * usually expected "showing nothing".
 */
@Serializable(LightFlickeringDefinition.Serializer::class)
public open class LightFlickeringDefinition : JsonReader() {
  /**
   * Brightness of the light in the range [0, 1] where 0 is no light and 1 is the maximum light.
   */
  public val minimum_intensity: Float? by fromJson()

  /**
   * Brightness of the light in the range [0, 1] where 0 is no light and 1 is the maximum light.
   */
  public val maximum_intensity: Float? by fromJson()

  public val derivation_change_frequency: Float? by fromJson()

  public val derivation_change_deviation: Float? by fromJson()

  public val border_fix_speed: Float? by fromJson()

  /**
   * The radius of the light in tiles. Note, that the light gets darker near the edges, so the
   * effective size of the light seems to be smaller.
   */
  public val minimum_light_size: Float? by fromJson()

  public val light_intensity_to_size_coefficient: Float? by fromJson()

  /**
   * Color of the light.
   */
  public val color: Color? by fromJson()

  public object Serializer :
      JsonReaderSerializer<LightFlickeringDefinition>(LightFlickeringDefinition::class)
}

@Serializable(LimitChestTipTrigger.Serializer::class)
@SerialName("limit-chest")
public open class LimitChestTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<LimitChestTipTrigger>(LimitChestTipTrigger::class)
}

@Serializable(LineStyleSpecification.Serializer::class)
@SerialName("line_style")
public open class LineStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val border: BorderImageSet? by fromJson()

  public object Serializer :
      JsonReaderSerializer<LineStyleSpecification>(LineStyleSpecification::class)
}

@Serializable(LineTriggerItem.Serializer::class)
@SerialName("line")
public open class LineTriggerItem : TriggerItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val range: Double by fromJson()

  public val width: Double by fromJson()

  public val range_effects: TriggerEffect? by fromJson()

  public object Serializer : JsonReaderSerializer<LineTriggerItem>(LineTriggerItem::class)
}

@Serializable(LinkedBeltStructure.Serializer::class)
public open class LinkedBeltStructure : JsonReader() {
  public val direction_in: Sprite4Way by fromJson()

  public val direction_out: Sprite4Way by fromJson()

  public val back_patch: Sprite4Way? by fromJson()

  public val front_patch: Sprite4Way? by fromJson()

  public val direction_in_side_loading: Sprite4Way? by fromJson()

  public val direction_out_side_loading: Sprite4Way? by fromJson()

  public object Serializer : JsonReaderSerializer<LinkedBeltStructure>(LinkedBeltStructure::class)
}

/**
 * The internal name of a game control (key binding).
 */
@Serializable
public enum class LinkedGameControl {
  `action-bar-select-page-1`,
  `action-bar-select-page-10`,
  `action-bar-select-page-2`,
  `action-bar-select-page-3`,
  `action-bar-select-page-4`,
  `action-bar-select-page-5`,
  `action-bar-select-page-6`,
  `action-bar-select-page-7`,
  `action-bar-select-page-8`,
  `action-bar-select-page-9`,
  `activate-tooltip`,
  `add-station`,
  `add-temporary-station`,
  `alt-zoom-in`,
  `alt-zoom-out`,
  build,
  `build-ghost`,
  `build-with-obstacle-avoidance`,
  `cancel-craft`,
  `cancel-craft-5`,
  `cancel-craft-all`,
  `clear-cursor`,
  `confirm-gui`,
  `confirm-message`,
  `connect-train`,
  `controller-gui-crafting-tab`,
  `controller-gui-logistics-tab`,
  copy,
  `copy-entity-settings`,
  craft,
  `craft-5`,
  `craft-all`,
  `cursor-split`,
  cut,
  `cycle-blueprint-backwards`,
  `cycle-blueprint-forwards`,
  `cycle-clipboard-backwards`,
  `cycle-clipboard-forwards`,
  `debug-reset-zoom`,
  `debug-reset-zoom-2x`,
  `debug-toggle-atlas-gui`,
  `debug-toggle-basic`,
  `debug-toggle-debug-settings`,
  `decrease-ui-scale`,
  `disconnect-train`,
  `drag-map`,
  `drop-cursor`,
  `editor-clone-item`,
  `editor-delete-item`,
  `editor-next-variation`,
  `editor-previous-variation`,
  `editor-remove-scripting-object`,
  `editor-reset-speed`,
  `editor-set-clone-brush-destination`,
  `editor-set-clone-brush-source`,
  `editor-speed-down`,
  `editor-speed-up`,
  `editor-switch-to-surface`,
  `editor-tick-once`,
  `editor-toggle-pause`,
  `fast-entity-split`,
  `fast-entity-transfer`,
  `flip-blueprint-horizontal`,
  `flip-blueprint-vertical`,
  `focus-search`,
  `increase-ui-scale`,
  `inventory-split`,
  `inventory-transfer`,
  `larger-terrain-building-area`,
  `logistic-networks`,
  mine,
  `move-down`,
  `move-left`,
  `move-right`,
  `move-up`,
  `next-active-quick-bar`,
  `next-player-in-replay`,
  `next-weapon`,
  `open-character-gui`,
  `open-gui`,
  `open-item`,
  `open-prototype-explorer-gui`,
  `open-prototypes-gui`,
  `open-technology-gui`,
  `open-trains-gui`,
  `order-to-follow`,
  paste,
  `paste-entity-settings`,
  `pause-game`,
  `pick-item`,
  `pick-items`,
  `place-in-chat`,
  `place-ping`,
  `previous-active-quick-bar`,
  `previous-mod`,
  `previous-technology`,
  `production-statistics`,
  `quick-bar-button-1`,
  `quick-bar-button-1-secondary`,
  `quick-bar-button-10`,
  `quick-bar-button-10-secondary`,
  `quick-bar-button-2`,
  `quick-bar-button-2-secondary`,
  `quick-bar-button-3`,
  `quick-bar-button-3-secondary`,
  `quick-bar-button-4`,
  `quick-bar-button-4-secondary`,
  `quick-bar-button-5`,
  `quick-bar-button-5-secondary`,
  `quick-bar-button-6`,
  `quick-bar-button-6-secondary`,
  `quick-bar-button-7`,
  `quick-bar-button-7-secondary`,
  `quick-bar-button-8`,
  `quick-bar-button-8-secondary`,
  `quick-bar-button-9`,
  `quick-bar-button-9-secondary`,
  `remove-pole-cables`,
  `reset-ui-scale`,
  `reverse-rotate`,
  `reverse-select`,
  `alt-reverse-select`,
  rotate,
  `rotate-active-quick-bars`,
  `select-for-blueprint`,
  `select-for-cancel-deconstruct`,
  `shoot-enemy`,
  `shoot-selected`,
  `show-info`,
  `smaller-terrain-building-area`,
  `smart-pipette`,
  `stack-split`,
  `stack-transfer`,
  `toggle-blueprint-library`,
  `toggle-console`,
  `toggle-driving`,
  `toggle-filter`,
  `toggle-gui-debug`,
  `toggle-gui-glows`,
  `toggle-gui-shadows`,
  `toggle-gui-style-view`,
  `toggle-map`,
  `toggle-menu`,
  undo,
  `zoom-in`,
  `zoom-out`,
}

@Serializable(ListBoxStyleSpecification.Serializer::class)
@SerialName("list_box_style")
public open class ListBoxStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val item_style: ButtonStyleSpecification? by fromJson()

  public val scroll_pane_style: ScrollPaneStyleSpecification? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ListBoxStyleSpecification>(ListBoxStyleSpecification::class)
}

@Serializable(LoaderStructure.Serializer::class)
public open class LoaderStructure : JsonReader() {
  public val direction_in: Sprite4Way by fromJson()

  public val direction_out: Sprite4Way by fromJson()

  public val back_patch: Sprite4Way? by fromJson()

  public val front_patch: Sprite4Way? by fromJson()

  public object Serializer : JsonReaderSerializer<LoaderStructure>(LoaderStructure::class)
}

/**
 * Localised strings are a way to support translation of in-game text. They offer a
 * language-independent code representation of the text that should be shown to players.
 *
 * It is an array where the first element is the key and the remaining elements are parameters that
 * will be substituted for placeholders in the template designated by the key.
 *
 * The key identifies the string template. For example, `"gui-alert-tooltip.attack"` (for the
 * template `"__1__ objects are being damaged"`; see the file `data/core/locale/en.cfg`). In the
 * settings and prototype stages, this key cannot be longer than 200 characters.
 *
 * The template can contain placeholders such as `__1__` or `__2__`. These will be replaced by the
 * respective parameter in the LocalisedString. The parameters themselves can be other localised
 * strings, which will be processed recursively in the same fashion. Localised strings can not be
 * recursed deeper than 20 levels and can not have more than 20 parameters.
 *
 * There are two special flags for the localised string, indicated by the key being a particular
 * string. First, if the key is the empty string (`""`), then all parameters will be concatenated
 * (after processing, if any are localised strings themselves). Second, if the key is a question mark
 * (`"?"`), then the first valid parameter will be used. A parameter can be invalid if its name doesn't
 * match any string template. If no parameters are valid, the last one is returned. This is useful to
 * implement a fallback for missing locale templates.
 *
 * Furthermore, when an API function expects a localised string, it will also accept a regular
 * string (i.e. not a table) which will not be translated, as well as a number or boolean, which will
 * be converted to their textual representation.
 *
 * See [Tutorial:Localisation](https://wiki.factorio.com/Tutorial:Localisation) for more
 * information.
 */
public typealias LocalisedString = UnknownUnion

/**
 * The items generated when an [EntityWithHealthPrototype](prototype:EntityWithHealthPrototype) is
 * killed.
 */
@Serializable(LootItem.Serializer::class)
public open class LootItem : JsonReader() {
  /**
   * The item to spawn.
   */
  public val item: ItemID by fromJson()

  /**
   * `0` is 0% and `1` is 100%. Must be `> 0`.
   */
  public val probability: Double? by fromJson()

  public val count_min: Double? by fromJson()

  /**
   * Must be `> 0`.
   */
  public val count_max: Double? by fromJson()

  public object Serializer : JsonReaderSerializer<LootItem>(LootItem::class)
}

@Serializable(LowPowerTipTrigger.Serializer::class)
@SerialName("low-power")
public open class LowPowerTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<LowPowerTipTrigger>(LowPowerTipTrigger::class)
}

@Serializable(ManualTransferTipTrigger.Serializer::class)
@SerialName("manual-transfer")
public open class ManualTransferTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ManualTransferTipTrigger>(ManualTransferTipTrigger::class)
}

@Serializable(ManualWireDragTipTrigger.Serializer::class)
@SerialName("manual-wire-drag")
public open class ManualWireDragTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ManualWireDragTipTrigger>(ManualWireDragTipTrigger::class)
}

@Serializable(MapEditorConstants.Serializer::class)
public open class MapEditorConstants : JsonReader() {
  public val clone_editor_copy_source_color: Color by fromJson()

  public val clone_editor_copy_destination_allowed_color: Color by fromJson()

  public val clone_editor_copy_destination_not_allowed_color: Color by fromJson()

  public val clone_editor_brush_source_color: Color by fromJson()

  public val clone_editor_brush_destination_color: Color by fromJson()

  public val clone_editor_brush_cursor_preview_tint: Color by fromJson()

  public val clone_editor_brush_world_preview_tint: Color by fromJson()

  public val script_editor_select_area_color: Color by fromJson()

  public val script_editor_drag_area_color: Color by fromJson()

  public val force_editor_select_area_color: Color by fromJson()

  public val cliff_editor_remove_cliffs_color: Color by fromJson()

  public val tile_editor_selection_preview_tint: Color by fromJson()

  public val tile_editor_area_selection_color: Color by fromJson()

  public val decorative_editor_selection_preview_tint: Color by fromJson()

  public val tile_editor_selection_preview_radius: UByte by fromJson()

  public val decorative_editor_selection_preview_radius: UByte by fromJson()

  public object Serializer : JsonReaderSerializer<MapEditorConstants>(MapEditorConstants::class)
}

@Serializable(MapGenPreset.Serializer::class)
public open class MapGenPreset : JsonReader() {
  /**
   * Specifies the ordering in the [map generator GUI](https://wiki.factorio.com/Map_generator).
   */
  public val order: Order by fromJson()

  /**
   * Whether this is the default preset. If `true`, this preset may not have any other properties
   * besides this and order.
   *
   * If no MapGenPreset has `default = true`, the preset selector will have a blank preset label,
   * with default settings. The "blank" preset goes away when another preset is selected.
   */
  public val default: Boolean? by fromJson()

  /**
   * If any setting is not set, it will use the default values.
   */
  public val basic_settings: MapGenSettings? by fromJson()

  /**
   * If any setting is not set, it will use the default values.
   */
  public val advanced_settings: AdvancedMapGenSettings? by fromJson()

  public object Serializer : JsonReaderSerializer<MapGenPreset>(MapGenPreset::class)
}

@Serializable(MapGenPresetDifficultySettings.Serializer::class)
public open class MapGenPresetDifficultySettings : JsonReader() {
  /**
   * A
   * [defines.difficulty_settings.recipe_difficulty](runtime:defines.difficulty_settings.recipe_difficulty).
   */
  public val recipe_difficulty: UByte? by fromJson()

  /**
   * A
   * [defines.difficulty_settings.technology_difficulty](runtime:defines.difficulty_settings.technology_difficulty).
   */
  public val technology_difficulty: UByte? by fromJson()

  public val technology_price_multiplier: Double? by fromJson()

  public val research_queue_setting: ResearchQueueSetting? by fromJson()

  public object Serializer :
      JsonReaderSerializer<MapGenPresetDifficultySettings>(MapGenPresetDifficultySettings::class)
}

@Serializable(MapGenPresetEnemyEvolutionSettings.Serializer::class)
public open class MapGenPresetEnemyEvolutionSettings : JsonReader() {
  public val enabled: Boolean? by fromJson()

  /**
   * Percentual increase in the evolution factor for every second (60 ticks)
   */
  public val time_factor: Double? by fromJson()

  /**
   * Percentual increase in the evolution factor for every destroyed spawner
   */
  public val destroy_factor: Double? by fromJson()

  /**
   * Percentual increase in the evolution factor for 1 pollution unit
   */
  public val pollution_factor: Double? by fromJson()

  public object Serializer :
      JsonReaderSerializer<MapGenPresetEnemyEvolutionSettings>(MapGenPresetEnemyEvolutionSettings::class)
}

@Serializable(MapGenPresetEnemyExpansionSettings.Serializer::class)
public open class MapGenPresetEnemyExpansionSettings : JsonReader() {
  public val enabled: Boolean? by fromJson()

  /**
   * Distance in chunks from the furthest base around. This prevents expansions from reaching too
   * far into the player's territory.
   */
  public val max_expansion_distance: UInt? by fromJson()

  /**
   * Size of the group that goes to build new base (the game interpolates between min size and max
   * size based on evolution factor).
   */
  public val settler_group_min_size: UInt? by fromJson()

  public val settler_group_max_size: UInt? by fromJson()

  /**
   * Ticks to expand to a single position for a base is used. Cooldown is calculated as follows:
   * `cooldown = lerp(max_expansion_cooldown, min_expansion_cooldown, -e^2 + 2 * e)` where `lerp` is
   * the linear interpolation function, and e is the current evolution factor.
   */
  public val min_expansion_cooldown: UInt? by fromJson()

  /**
   * In ticks.
   */
  public val max_expansion_cooldown: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<MapGenPresetEnemyExpansionSettings>(MapGenPresetEnemyExpansionSettings::class)
}

/**
 * The pollution settings, the values are for 60 ticks (1 second).
 */
@Serializable(MapGenPresetPollutionSettings.Serializer::class)
public open class MapGenPresetPollutionSettings : JsonReader() {
  public val enabled: Boolean? by fromJson()

  /**
   * Must be <= 0.25. Amount that is diffused to neighboring chunks.
   */
  public val diffusion_ratio: Double? by fromJson()

  /**
   * Must be >= 0.1. Also known as absorption modifier and previously dissipation rate.
   */
  public val ageing: Double? by fromJson()

  public val min_pollution_to_damage_trees: Double? by fromJson()

  /**
   * Must be >= 0.1.
   */
  public val enemy_attack_pollution_consumption_modifier: Double? by fromJson()

  public val pollution_restored_per_tree_damage: Double? by fromJson()

  public object Serializer :
      JsonReaderSerializer<MapGenPresetPollutionSettings>(MapGenPresetPollutionSettings::class)
}

@Serializable
public enum class MapGenSettingsAutoplaceSettings {
  entity,
  tile,
  decorative,
}

@Serializable(MapGenSettings.Serializer::class)
public open class MapGenSettings : JsonReader() {
  /**
   * This is the inverse of "water scale" in the map generator GUI. So a water scale that shows as
   * 50% in the GUI is a value of `1/0.5 = 2` for `terrain_segmentation`.
   */
  public val terrain_segmentation: MapGenSize? by fromJson()

  /**
   * Shown as water coverage in the map generator GUI.
   */
  public val water: MapGenSize? by fromJson()

  /**
   * Whether undefined `autoplace_controls` should fall back to the default controls or not.
   */
  public val default_enable_all_autoplace_controls: Boolean? by fromJson()

  public val autoplace_controls: Map<AutoplaceControlID, FrequencySizeRichness>? by fromJson()

  /**
   * Each setting in this table maps the string type to the settings for that type.
   */
  public val autoplace_settings: Map<MapGenSettingsAutoplaceSettings, AutoplaceSettings>? by
      fromJson()

  /**
   * Map of property name (`"elevation"`, etc) to name of noise expression that will provide it.
   * Entries may be omitted. A notable usage is changing autoplace behavior of an entity based on the
   * preset, which cannot be read from a noise expression.
   */
  public val property_expression_names: Map<String, UnknownUnion>? by fromJson()

  /**
   * Array of the positions of the starting areas.
   */
  public val starting_points: List<MapPosition>? by fromJson()

  /**
   * Read by the game, but not used or set in the GUI.
   */
  public val seed: UInt? by fromJson()

  /**
   * Width of the map in tiles. Silently limited to 2 000 000, ie. +/- 1 million tiles from the
   * center in both directions.
   */
  public val width: UInt? by fromJson()

  /**
   * Height of the map in tiles. Silently limited to 2 000 000, ie. +/- 1 million tiles from the
   * center in both directions.
   */
  public val height: UInt? by fromJson()

  /**
   * Size of the starting area. The starting area only effects enemy placement, and has no effect on
   * resources.
   */
  public val starting_area: MapGenSize? by fromJson()

  public val peaceful_mode: Boolean? by fromJson()

  public val cliff_settings: CliffPlacementSettings? by fromJson()

  public object Serializer : JsonReaderSerializer<MapGenSettings>(MapGenSettings::class)
}

/**
 * A floating point number specifying an amount.
 *
 * For backwards compatibility, MapGenSizes can also be specified as one of the following strings,
 * which will be converted to a number:
 *
 * Each of the values in a triplet (such as "low", "small", and "poor") are synonymous. In-game the
 * values can be set from `0.166` to `6` via the GUI (respective to the percentages), while `0` is used
 * to disable the autoplace control.
 */
public typealias MapGenSize = UnknownUnion

/**
 * Coordinates of a tile in a map. Positive x goes towards east, positive y goes towards south, and
 * x is the first dimension in the array format.
 *
 * The coordinates are saved as a fixed-size 32 bit integer, with 8 bits reserved for decimal
 * precision, meaning the smallest value step is `1/2^8 = 0.00390625` tiles.
 */
@Serializable(MapPositionValues.Serializer::class)
public open class MapPositionValues : JsonReader() {
  public val x: Double by fromJson()

  public val y: Double by fromJson()

  public object Serializer : JsonReaderSerializer<MapPositionValues>(MapPositionValues::class)
}

/**
 * Coordinates of a tile in a map. Positive x goes towards east, positive y goes towards south, and
 * x is the first dimension in the array format.
 *
 * The coordinates are saved as a fixed-size 32 bit integer, with 8 bits reserved for decimal
 * precision, meaning the smallest value step is `1/2^8 = 0.00390625` tiles.
 */
public typealias MapPosition = UnknownUnion

public typealias MaterialAmountType = Double

@Serializable(MaxFailedAttemptsPerTickPerConstructionQueueModifier.Serializer::class)
@SerialName("max-failed-attempts-per-tick-per-construction-queue")
public open class MaxFailedAttemptsPerTickPerConstructionQueueModifier : SimpleModifier(), Modifier
    {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<MaxFailedAttemptsPerTickPerConstructionQueueModifier>(MaxFailedAttemptsPerTickPerConstructionQueueModifier::class)
}

@Serializable(MaxSuccessfulAttemptsPerTickPerConstructionQueueModifier.Serializer::class)
@SerialName("max-successful-attempts-per-tick-per-construction-queue")
public open class MaxSuccessfulAttemptsPerTickPerConstructionQueueModifier : SimpleModifier(),
    Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<MaxSuccessfulAttemptsPerTickPerConstructionQueueModifier>(MaxSuccessfulAttemptsPerTickPerConstructionQueueModifier::class)
}

@Serializable(MaximumFollowingRobotsCountModifier.Serializer::class)
@SerialName("maximum-following-robots-count")
public open class MaximumFollowingRobotsCountModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<MaximumFollowingRobotsCountModifier>(MaximumFollowingRobotsCountModifier::class)
}

/**
 * The mining properties of objects. For formulas for the mining time, see
 * [mining](https://wiki.factorio.com/Mining).
 */
@Serializable(MinableProperties.Serializer::class)
public open class MinableProperties : JsonReader() {
  /**
   * How many seconds are required to mine this object at 1 mining speed.
   */
  public val mining_time: Double by fromJson()

  /**
   * The items that are returned when this object is mined.
   */
  public val results: List<ProductPrototype>? by fromJson()

  /**
   * Only loaded if `results` is not defined.
   *
   * Which item is dropped when this is mined. Cannot be empty. If you want the entity to not be
   * minable, don't specify the minable properties, if you want it to be minable with no result item,
   * don't specify the result at all.
   */
  public val result: ItemID? by fromJson()

  /**
   * The amount of fluid that is used up when this object is mined. If this is > 0, this object
   * cannot be mined by hand.
   */
  public val fluid_amount: Double? by fromJson()

  /**
   * Name of a [ParticlePrototype](prototype:ParticlePrototype). Which set of particles to use.
   */
  public val mining_particle: ParticleID? by fromJson()

  /**
   * Name of a [FluidPrototype](prototype:FluidPrototype). The fluid that is used up when this
   * object is mined.
   */
  public val required_fluid: FluidID? by fromJson()

  /**
   * Only loaded if `results` is not defined.
   *
   * How many of result are dropped.
   */
  public val count: UShort? by fromJson()

  public val mining_trigger: Trigger? by fromJson()

  public object Serializer : JsonReaderSerializer<MinableProperties>(MinableProperties::class)
}

@Serializable(MinimapStyleSpecification.Serializer::class)
@SerialName("minimap_style")
public open class MinimapStyleSpecification : EmptyWidgetStyleSpecification(), StyleSpecification {
  override val type: UnknownStringLiteral by fromJson()

  public object Serializer :
      JsonReaderSerializer<MinimapStyleSpecification>(MinimapStyleSpecification::class)
}

/**
 * Used by [MiningDrillPrototype](prototype:MiningDrillPrototype).
 */
@Serializable(MiningDrillGraphicsSet.Serializer::class)
public open class MiningDrillGraphicsSet : JsonReader() {
  public val animation: Animation4Way? by fromJson()

  /**
   * Idle animation must have the same frame count as animation.
   */
  public val idle_animation: Animation4Way? by fromJson()

  /**
   * Only loaded if `idle_animation` is defined.
   */
  public val always_draw_idle_animation: Boolean? by fromJson()

  public val default_recipe_tint: DefaultRecipeTint? by fromJson()

  public val working_visualisations: List<WorkingVisualisation>? by fromJson()

  /**
   * Only loaded if one of `shift_animation_waypoint_stop_duration` or
   * `shift_animation_transition_duration` is not `0`.
   */
  public val shift_animation_waypoints: ShiftAnimationWaypoints? by fromJson()

  /**
   * Only loaded if `shift_animation_waypoints` is defined.
   */
  public val shift_animation_waypoint_stop_duration: UShort? by fromJson()

  /**
   * Only loaded if `shift_animation_waypoints` is defined.
   */
  public val shift_animation_transition_duration: UShort? by fromJson()

  /**
   * Used by [WorkingVisualisation::apply_tint](prototype:WorkingVisualisation::apply_tint).
   */
  public val status_colors: StatusColors? by fromJson()

  public val drilling_vertical_movement_duration: UShort? by fromJson()

  public val animation_progress: Float? by fromJson()

  public val max_animation_progress: Float? by fromJson()

  public val min_animation_progress: Float? by fromJson()

  /**
   * Render layer(s) for all directions of the circuit connectors.
   */
  public val circuit_connector_layer: UnknownUnion? by fromJson()

  /**
   * Secondary draw order(s) for all directions of the circuit connectors.
   */
  public val circuit_connector_secondary_draw_order: UnknownUnion? by fromJson()

  public object Serializer :
      JsonReaderSerializer<MiningDrillGraphicsSet>(MiningDrillGraphicsSet::class)
}

@Serializable(MiningDrillProductivityBonusModifier.Serializer::class)
@SerialName("mining-drill-productivity-bonus")
public open class MiningDrillProductivityBonusModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<MiningDrillProductivityBonusModifier>(MiningDrillProductivityBonusModifier::class)
}

/**
 * The user-set value of a startup [mod setting](https://wiki.factorio.com/Tutorial:Mod_settings).
 */
@Serializable(ModSetting.Serializer::class)
public open class ModSetting : JsonReader() {
  /**
   * The value of the mod setting. The type depends on the kind of setting.
   */
  public val `value`: UnknownUnion by fromJson()

  public object Serializer : JsonReaderSerializer<ModSetting>(ModSetting::class)
}

/**
 * The effect that is applied when a [TechnologyPrototype](prototype:TechnologyPrototype) is
 * researched.
 *
 * Loaded as one of the [BaseModifier](prototype:BaseModifier) extensions, based on the value of the
 * `type` key.
 */
@Serializable
public sealed interface Modifier

/**
 * A dictionary of mod names to mod versions of all active mods. It can be used to adjust mod
 * functionality based on the presence of other mods.
 */
public typealias Mods = Map<String, String>

/**
 * The name of a [ModuleCategory](prototype:ModuleCategory).
 */
public typealias ModuleCategoryID = String

/**
 * The number of module slots in this entity, and their icon positions.
 */
@Serializable(ModuleSpecification.Serializer::class)
public open class ModuleSpecification : JsonReader() {
  /**
   * The number of module slots in this entity.
   */
  public val module_slots: ItemStackIndex? by fromJson()

  public val module_info_max_icons_per_row: UByte? by fromJson()

  public val module_info_max_icon_rows: UByte? by fromJson()

  public val module_info_icon_shift: Vector? by fromJson()

  public val module_info_icon_scale: Float? by fromJson()

  public val module_info_separation_multiplier: Float? by fromJson()

  public val module_info_multi_row_initial_height_modifier: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<ModuleSpecification>(ModuleSpecification::class)
}

@Serializable
public enum class ModuleTint {
  primary,
  secondary,
  tertiary,
  quaternary,
  none,
}

/**
 * The name of a [MouseCursor](prototype:MouseCursor).
 */
public typealias MouseCursorID = String

@Serializable(NestedTriggerEffectItem.Serializer::class)
@SerialName("nested-result")
public open class NestedTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val action: Trigger by fromJson()

  public object Serializer :
      JsonReaderSerializer<NestedTriggerEffectItem>(NestedTriggerEffectItem::class)
}

/**
 * An array-like noise expression, for example constructed with
 * [NoiseArrayConstruction](prototype:NoiseArrayConstruction) or a variable such as
 * `noise.var("starting_positions")`.
 */
@Serializable
public sealed interface NoiseArray

/**
 * `value_expressions` property should be a list of numeric expressions, each of which will be
 * evaluated to come up with the corresponding numeric value in the resulting array.
 *
 * Used to construct map positions (`{x, y}`) and map position lists (`{{x0,y0}, {y1,y1}, [...]}`)
 * for [offset-points](prototype:NoiseFunctionOffsetPoints) and
 * [distance-from-nearest-point](prototype:NoiseFunctionDistanceFromNearestPoint) functions.
 */
@Serializable(NoiseArrayConstruction.Serializer::class)
@SerialName("array-construction")
public open class NoiseArrayConstruction : JsonReader(), NoiseArray, NoiseExpression {
  public val type: UnknownStringLiteral by fromJson()

  public val value_expressions: List<NoiseExpression> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseArrayConstruction>(NoiseArrayConstruction::class)
}

/**
 * Loaded as one of the noise expressions listed in this union, based on the value of the `type`
 * key.
 *
 * A fragment of a functional program used to generate coherent noise, probably for purposes related
 * to terrain generation.
 *
 * Noise expressions can be provided as table literals or built using functions in the built-in
 * [noise library](https://github.com/wube/factorio-data/blob/master/core/lualib/noise.lua). The
 * built-in noise library allows writing much more concise code, so its usage will be shown in most
 * examples for noise expressions.
 *
 * [noise.define_noise_function](https://github.com/wube/factorio-data/blob/master/core/lualib/noise.lua#L272)
 * allows noise expressions to be defined using a shorthand that's a subset of Lua (see the example for
 * details).
 *
 * See [here](https://togos.github.io/togos-example-noise-programs/) for a tutorial on authoring
 * noise expressions.
 *
 * The most frequently used noise functions are loaded via
 * [NoiseFunctionApplication](prototype:NoiseFunctionApplication).
 */
@Serializable
public sealed interface NoiseExpression

/**
 * Takes a single argument and returns its absolute value. Ie. if the argument is negative, it is
 * inverted.
 */
@Serializable(NoiseFunctionAbsoluteValue.Serializer::class)
@SerialName("absolute-value")
public open class NoiseFunctionAbsoluteValue : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionAbsoluteValue>(NoiseFunctionAbsoluteValue::class)
}

/**
 * Takes between 0 and 32 numbers and adds them up.
 */
@Serializable(NoiseFunctionAdd.Serializer::class)
@SerialName("add")
public open class NoiseFunctionAdd : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: List<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionAdd>(NoiseFunctionAdd::class)
}

/**
 * Loaded as one of the NoiseFunctions listed in this union, based on the value of the
 * `function_name` key.
 *
 * Apply a function to a list or associative array of arguments. Some functions expect arguments to
 * be named and some expect them not to be.
 *
 * Function calls are their own class of expression (as opposed to every function just being its own
 * expression type) because function calls all have similar properties -- arguments are themselves
 * expressions, a function call with all-constant arguments can be constant-folded (due to [referential
 * transparency](http://en.wikipedia.org/wiki/Referential_transparency)), etc.
 */
@Serializable
@JsonClassDiscriminator("function_name")
public sealed interface NoiseFunctionApplication : NoiseNumber, NoiseExpression

/**
 * Returns the arc tangent of y/x using the signs of arguments to determine the correct quadrant.
 */
@Serializable(NoiseFunctionAtan2.Serializer::class)
@SerialName("atan2")
public open class NoiseFunctionAtan2 : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  /**
   * The first argument is y and the second is x.
   */
  public val arguments: Tuple2<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionAtan2>(NoiseFunctionAtan2::class)
}

@Serializable(NoiseFunctionAutoplaceProbability.Serializer::class)
@SerialName("autoplace-probability")
public open class NoiseFunctionAutoplaceProbability : JsonReader(), NoiseFunctionApplication,
    NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseLiteralObject> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionAutoplaceProbability>(NoiseFunctionAutoplaceProbability::class)
}

@Serializable(NoiseFunctionAutoplaceRichness.Serializer::class)
@SerialName("autoplace-richness")
public open class NoiseFunctionAutoplaceRichness : JsonReader(), NoiseFunctionApplication,
    NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseLiteralObject> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionAutoplaceRichness>(NoiseFunctionAutoplaceRichness::class)
}

/**
 * Casts between 0 and 32 numbers to 32-bit integers and performs a bitwise AND on them.
 */
@Serializable(NoiseFunctionBitwiseAnd.Serializer::class)
@SerialName("bitwise-and")
public open class NoiseFunctionBitwiseAnd : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: List<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionBitwiseAnd>(NoiseFunctionBitwiseAnd::class)
}

/**
 * Casts the single argument to a 32-bit integer and performs bitwise negates it.
 */
@Serializable(NoiseFunctionBitwiseNot.Serializer::class)
@SerialName("bitwise-not")
public open class NoiseFunctionBitwiseNot : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionBitwiseNot>(NoiseFunctionBitwiseNot::class)
}

/**
 * Casts between 0 and 32 numbers to 32-bit integers and performs a bitwise OR on them.
 */
@Serializable(NoiseFunctionBitwiseOr.Serializer::class)
@SerialName("bitwise-or")
public open class NoiseFunctionBitwiseOr : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: List<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionBitwiseOr>(NoiseFunctionBitwiseOr::class)
}

/**
 * Casts between 0 and 32 numbers to 32-bit integers and performs a bitwise EXCLUSIVE OR on them.
 */
@Serializable(NoiseFunctionBitwiseXor.Serializer::class)
@SerialName("bitwise-xor")
public open class NoiseFunctionBitwiseXor : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: List<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionBitwiseXor>(NoiseFunctionBitwiseXor::class)
}

/**
 * Takes a single argument and returns its ceil.
 */
@Serializable(NoiseFunctionCeil.Serializer::class)
@SerialName("ceil")
public open class NoiseFunctionCeil : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionCeil>(NoiseFunctionCeil::class)
}

/**
 * The first argument is clamped to be between the second and third. The second is treated as a
 * lower limit and the third the upper limit.
 */
@Serializable(NoiseFunctionClamp.Serializer::class)
@SerialName("clamp")
public open class NoiseFunctionClamp : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple3<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionClamp>(NoiseFunctionClamp::class)
}

/**
 * Prints between 0 and 32 arguments to the [log file](https://wiki.factorio.com/Log_file) when the
 * expression is compiled. For that it needs to part of another expression that is compiled. The last
 * argument of the `compile-time-log` is returned as the "result" of the `compile-time-log`.
 */
@Serializable(NoiseFunctionCompileTimeLog.Serializer::class)
@SerialName("compile-time-log")
public open class NoiseFunctionCompileTimeLog : JsonReader(), NoiseFunctionApplication, NoiseNumber
    {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: List<NoiseExpression> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionCompileTimeLog>(NoiseFunctionCompileTimeLog::class)
}

/**
 * Takes a single argument and returns its cosine.
 */
@Serializable(NoiseFunctionCos.Serializer::class)
@SerialName("cos")
public open class NoiseFunctionCos : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionCos>(NoiseFunctionCos::class)
}

/**
 * Computes the [euclidean distance](https://en.wikipedia.org/wiki/Euclidean_distance) of the
 * position `{x, y}` to all position listed in points and returns the shortest distance. The returned
 * distance can be `maximum_distance` at most.
 */
@Serializable(NoiseFunctionDistanceFromNearestPoint.Serializer::class)
@SerialName("distance-from-nearest-point")
public open class NoiseFunctionDistanceFromNearestPoint : JsonReader(), NoiseFunctionApplication,
    NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: DistanceFromNearestPointArguments by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionDistanceFromNearestPoint>(NoiseFunctionDistanceFromNearestPoint::class)
}

/**
 * Takes two arguments and divides the first by the second.
 */
@Serializable(NoiseFunctionDivide.Serializer::class)
@SerialName("divide")
public open class NoiseFunctionDivide : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple2<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionDivide>(NoiseFunctionDivide::class)
}

/**
 * Returns the result of first argument == second argument as a literal number that is `0` for false
 * and `1` for true.
 */
@Serializable(NoiseFunctionEquals.Serializer::class)
@SerialName("equals")
public open class NoiseFunctionEquals : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple2<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionEquals>(NoiseFunctionEquals::class)
}

/**
 * Takes two arguments and raises the first to the second power.
 */
@Serializable(NoiseFunctionExponentiate.Serializer::class)
@SerialName("exponentiate")
public open class NoiseFunctionExponentiate : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple2<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionExponentiate>(NoiseFunctionExponentiate::class)
}

/**
 * Scaling input and output can be accomplished other ways, but are done so commonly as to be built
 * into this function for performance reasons.
 */
@Serializable(NoiseFunctionFactorioBasisNoise.Serializer::class)
@SerialName("factorio-basis-noise")
public open class NoiseFunctionFactorioBasisNoise : JsonReader(), NoiseFunctionApplication,
    NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: FactorioBasisNoiseArguments by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionFactorioBasisNoise>(NoiseFunctionFactorioBasisNoise::class)
}

@Serializable(NoiseFunctionFactorioMultioctaveNoise.Serializer::class)
@SerialName("factorio-multioctave-noise")
public open class NoiseFunctionFactorioMultioctaveNoise : JsonReader(), NoiseFunctionApplication,
    NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: FactorioMultioctaveNoiseArguments by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionFactorioMultioctaveNoise>(NoiseFunctionFactorioMultioctaveNoise::class)
}

@Serializable(NoiseFunctionFactorioQuickMultioctaveNoise.Serializer::class)
@SerialName("factorio-quick-multioctave-noise")
public open class NoiseFunctionFactorioQuickMultioctaveNoise : JsonReader(),
    NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: FactorioQuickMultioctaveNoiseArguments by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionFactorioQuickMultioctaveNoise>(NoiseFunctionFactorioQuickMultioctaveNoise::class)
}

/**
 * Takes a single argument and returns its floor.
 */
@Serializable(NoiseFunctionFloor.Serializer::class)
@SerialName("floor")
public open class NoiseFunctionFloor : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionFloor>(NoiseFunctionFloor::class)
}

/**
 * Returns the result of first argument <= second argument as a literal number that is `0` for false
 * and `1` for true.
 */
@Serializable(NoiseFunctionLessOrEqual.Serializer::class)
@SerialName("less-or-equal")
public open class NoiseFunctionLessOrEqual : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple2<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionLessOrEqual>(NoiseFunctionLessOrEqual::class)
}

/**
 * Returns the result of first argument < second argument as a literal number that is `0` for false
 * and `1` for true.
 */
@Serializable(NoiseFunctionLessThan.Serializer::class)
@SerialName("less-than")
public open class NoiseFunctionLessThan : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple2<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionLessThan>(NoiseFunctionLessThan::class)
}

@Serializable(NoiseFunctionLog2.Serializer::class)
@SerialName("log2")
public open class NoiseFunctionLog2 : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionLog2>(NoiseFunctionLog2::class)
}

/**
 * Takes two arguments and divides the first by the second and returns the remainder. This is
 * implemented using [fmod(double, double)](https://en.cppreference.com/w/cpp/numeric/math/fmod).
 */
@Serializable(NoiseFunctionModulo.Serializer::class)
@SerialName("modulo")
public open class NoiseFunctionModulo : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple2<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionModulo>(NoiseFunctionModulo::class)
}

/**
 * Takes between 0 and 32 numbers and multiplies them.
 */
@Serializable(NoiseFunctionMultiply.Serializer::class)
@SerialName("multiply")
public open class NoiseFunctionMultiply : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: List<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionMultiply>(NoiseFunctionMultiply::class)
}

@Serializable(NoiseFunctionNoiseLayerNameToID.Serializer::class)
@SerialName("noise-layer-name-to-id")
public open class NoiseFunctionNoiseLayerNameToID : JsonReader(), NoiseFunctionApplication,
    NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseLiteralString> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionNoiseLayerNameToID>(NoiseFunctionNoiseLayerNameToID::class)
}

/**
 * The first argument represents a vector of how the positions should be shifted, and the second
 * argument lists the positions that should be shifted.
 */
@Serializable(NoiseFunctionOffsetPoints.Serializer::class)
@SerialName("offset-points")
public open class NoiseFunctionOffsetPoints : JsonReader(), NoiseFunctionApplication, NoiseArray {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple2<NoiseArray> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionOffsetPoints>(NoiseFunctionOffsetPoints::class)
}

/**
 * Subtracts a random value in the `[0, amplitude)` range from `source` if `source` is larger than
 * `0`.
 */
@Serializable(NoiseFunctionRandomPenalty.Serializer::class)
@SerialName("random-penalty")
public open class NoiseFunctionRandomPenalty : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: RandomPenaltyArguments by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionRandomPenalty>(NoiseFunctionRandomPenalty::class)
}

/**
 * Similar to [clamp](prototype:NoiseFunctionClamp), where the first argument is folded back across
 * the third and second limits until it lies between them.
 */
@Serializable(NoiseFunctionRidge.Serializer::class)
@SerialName("ridge")
public open class NoiseFunctionRidge : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  /**
   * The first argument is the number to be ridged, the second is the lower limit and the third is
   * the upper limit.
   */
  public val arguments: Tuple3<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionRidge>(NoiseFunctionRidge::class)
}

/**
 * Takes a single argument and returns its sine.
 */
@Serializable(NoiseFunctionSin.Serializer::class)
@SerialName("sin")
public open class NoiseFunctionSin : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple1<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionSin>(NoiseFunctionSin::class)
}

/**
 * Generates random conical spots. The map is divided into square regions, and within each region,
 * candidate points are chosen at random and target density, spot quantity, and radius are calculated
 * for each point (or one of every `skip_span` candidate points) by configured expressions. Each spot
 * contributes a quantity to a regional target total (which is the average of sampled target densities
 * times the area of the region) until the total has been reached or a maximum spot count is hit. The
 * output value of the function is the maximum height of any spot at a given point.
 *
 * The parameters that provide expressions to be evaluated for each point (all named
 * something_expression) need to actually return expression objects.
 *
 * The quantity of the spot is assumed to be the same as its volume. Since the volume of a cone is
 * `pi * radius^2 * height / 3`, the height ('peak value') of any given spot is calculated as `3 *
 * quantity / (pi * radius^2)`
 *
 * The infinite series of candidate points (of which `candidate_point_count` are actually
 * considered) generated by `spot-noise` expressions with the same `seed0`, `seed1`, `region_size`, and
 * `suggested_minimum_candidate_point_spacing` will be identical. This allows multiple spot-noise
 * expressions (e.g. for different ore patches) to avoid overlap by using different points from the
 * same list, determined by `skip_span` and `skip_offset`.
 */
@Serializable(NoiseFunctionSpotNoise.Serializer::class)
@SerialName("spot-noise")
public open class NoiseFunctionSpotNoise : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: SpotNoiseArguments by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionSpotNoise>(NoiseFunctionSpotNoise::class)
}

/**
 * Takes two arguments and subtracts the second from the first.
 */
@Serializable(NoiseFunctionSubtract.Serializer::class)
@SerialName("subtract")
public open class NoiseFunctionSubtract : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple2<NoiseNumber> by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseFunctionSubtract>(NoiseFunctionSubtract::class)
}

/**
 * The first argument is the value to be terraced. The second argument is the offset, the third the
 * width, and the fourth the strength.
 */
@Serializable(NoiseFunctionTerrace.Serializer::class)
@SerialName("terrace")
public open class NoiseFunctionTerrace : JsonReader(), NoiseFunctionApplication, NoiseNumber {
  public val type: UnknownStringLiteral by fromJson()

  public val function_name: UnknownStringLiteral by fromJson()

  public val arguments: Tuple4<NoiseNumber> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseFunctionTerrace>(NoiseFunctionTerrace::class)
}

/**
 * Has an `arguments` property that is a list of condition-result expression pairs followed by a
 * default result expression, like so:
 *
 * ```
 * {
 *   type = "if-else-chain",
 *   arguments = {
 *     condition1, result1,
 *     condition2, result2,
 *     ...
 *     defaultResult
 *   }
 * }
 * ```
 *
 * The result of the if-else-chain is the value of the first result expression whose condition
 * expression evaluated to true, or the value of the default result ('else') expression.
 */
@Serializable(NoiseIfElseChain.Serializer::class)
@SerialName("if-else-chain")
public open class NoiseIfElseChain : JsonReader(), NoiseNumber, NoiseExpression {
  public val type: UnknownStringLiteral by fromJson()

  public val arguments: List<NoiseExpression> by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseIfElseChain>(NoiseIfElseChain::class)
}

/**
 * The name of a [NoiseLayer](prototype:NoiseLayer).
 */
public typealias NoiseLayerID = String

/**
 * Evaluates to the same boolean value (true or false) every time, given by the `literal_value`
 * property. May be used as a number value, evaluates to `1` for true and `0` for false.
 */
@Serializable(NoiseLiteralBoolean.Serializer::class)
@SerialName("literal-boolean")
public open class NoiseLiteralBoolean : JsonReader(), ConstantNoiseBoolean, NoiseExpression {
  public val type: UnknownStringLiteral by fromJson()

  public val literal_value: Boolean by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseLiteralBoolean>(NoiseLiteralBoolean::class)
}

/**
 * Returns the expression represented by its `literal_value` property. Useful mostly for passing
 * expressions (to be evaluated later) to the [spot-noise](prototype:NoiseFunctionSpotNoise) function.
 */
@Serializable(NoiseLiteralExpression.Serializer::class)
@SerialName("literal-expression")
public open class NoiseLiteralExpression : JsonReader(), NoiseExpression {
  public val type: UnknownStringLiteral by fromJson()

  public val literal_value: NoiseExpression by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseLiteralExpression>(NoiseLiteralExpression::class)
}

/**
 * Evaluates to the same number every time, given by the `literal_value` property. All numbers are
 * treated as [float](prototype:float) internally unless otherwise specified. May be used as a boolean
 * value, evaluates to true for numbers bigger than zero, anything else evaluates to false.
 */
@Serializable(NoiseLiteralNumber.Serializer::class)
@SerialName("literal-number")
public open class NoiseLiteralNumber : JsonReader(), NoiseNumber, NoiseExpression {
  public val type: UnknownStringLiteral by fromJson()

  public val literal_value: Float by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseLiteralNumber>(NoiseLiteralNumber::class)
}

/**
 * Evaluates to the same object every time, given by the `literal_value` property. Since the noise
 * generation runtime has no notion of objects or use for them, this is useful only in constant
 * contexts, such as the argument of the `autoplace-probability` function (where the 'literal object'
 * is an [AutoplaceSpecification](prototype:AutoplaceSpecification)).
 */
@Serializable(NoiseLiteralObject.Serializer::class)
@SerialName("literal-object")
public open class NoiseLiteralObject : JsonReader(), NoiseExpression {
  public val type: UnknownStringLiteral by fromJson()

  public val literal_value: AutoplaceSpecification by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseLiteralObject>(NoiseLiteralObject::class)
}

/**
 * Evaluates to the same string every time, given by the `literal_value` property. Since the noise
 * generation runtime has no notion of strings or use for them, this is useful only in constant
 * contexts.
 */
@Serializable(NoiseLiteralString.Serializer::class)
@SerialName("literal-string")
public open class NoiseLiteralString : JsonReader(), NoiseExpression {
  public val type: UnknownStringLiteral by fromJson()

  public val literal_value: String by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseLiteralString>(NoiseLiteralString::class)
}

/**
 * A numeric noise expression, such as a literal number, the result of addition, or multioctave
 * noise.
 *
 * This encompasses all [NoiseExpressions](prototype:NoiseExpression), except for
 * [NoiseLiteralBoolean](prototype:NoiseLiteralBoolean),
 * [NoiseLiteralString](prototype:NoiseLiteralString),
 * [NoiseLiteralObject](prototype:NoiseLiteralObject),
 * [NoiseLiteralExpression](prototype:NoiseLiteralExpression),
 * [NoiseArrayConstruction](prototype:NoiseArrayConstruction), and
 * [NoiseFunctionOffsetPoints](prototype:NoiseFunctionOffsetPoints).
 */
@Serializable
public sealed interface NoiseNumber

/**
 * Evaluates and returns the value of its expression property, which is itself an expression.
 *
 * This hints to the compiler that it should break the subexpression into its own procedure so that
 * the result can be re-used in multiple places. For instance if you want to re-use the same
 * multioctave noise for determining probability of multiple tiles/entities, wrap the multioctave noise
 * expression in a procedure-delimiter. Alternatively, make the noise its own
 * [NamedNoiseExpression](prototype:NamedNoiseExpression) and reference it by name, using a
 * [NoiseVariable](prototype:NoiseVariable).
 */
@Serializable(NoiseProcedureDelimiter.Serializer::class)
@SerialName("procedure-delimiter")
public open class NoiseProcedureDelimiter : JsonReader(), NoiseNumber, NoiseExpression {
  public val type: UnknownStringLiteral by fromJson()

  public val expression: NoiseExpression by fromJson()

  public object Serializer :
      JsonReaderSerializer<NoiseProcedureDelimiter>(NoiseProcedureDelimiter::class)
}

/**
 * Variables referencing named noise expressions may have their reference overridden by other named
 * noise expression if their `intended_property` is the variable name and it is selected by the user in
 * the map generator GUI. See the second example on
 * [NamedNoiseExpression::intended_property](prototype:NamedNoiseExpression::intended_property).
 */
@Serializable(NoiseVariable.Serializer::class)
@SerialName("variable")
public open class NoiseVariable : JsonReader(), NoiseNumber, NoiseArray, NoiseExpression {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * A string referring to a pre-defined variable, constant, or
   * [NamedNoiseExpression](prototype:NamedNoiseExpression).
   *
   * The `"x"` or `"y"` variables refer to the current coordinates of the map position being
   * evaluated.
   *
   * The constants refer to a set of values mostly defined by
   * [MapGenSettings](prototype:MapGenSettings).
   *
   * The named noise expressions refer to one of the notable
   * [BaseNamedNoiseExpressions](prototype:BaseNamedNoiseExpressions), or any other existing one by
   * name.
   */
  public val variable_name: UnknownUnion by fromJson()

  public object Serializer : JsonReaderSerializer<NoiseVariable>(NoiseVariable::class)
}

/**
 * A set of constants largely determined by [MapGenSettings](prototype:MapGenSettings).
 */
public typealias NoiseVariableConstants = UnknownUnion

@Serializable(NothingModifier.Serializer::class)
@SerialName("nothing")
public open class NothingModifier : BaseModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public val effect_description: LocalisedString? by fromJson()

  public object Serializer : JsonReaderSerializer<NothingModifier>(NothingModifier::class)
}

@Serializable(OffshorePumpGraphicsSet.Serializer::class)
public open class OffshorePumpGraphicsSet : JsonReader() {
  /**
   * Rendered in "object" layer, with secondary draw order 0.
   */
  public val animation: Animation4Way by fromJson()

  public val base_render_layer: RenderLayer? by fromJson()

  public val underwater_layer_offset: Byte? by fromJson()

  /**
   * Rendered in "object" layer, with secondary draw order 20.
   */
  public val fluid_animation: Animation4Way? by fromJson()

  /**
   * Rendered in "object" layer, with secondary draw order 40.
   */
  public val glass_pictures: Sprite4Way? by fromJson()

  /**
   * Rendered in layer specified by `base_render_layer`, with secondary draw order 0.
   */
  public val base_pictures: Sprite4Way? by fromJson()

  /**
   * Drawn by tile renderer when water animation is enabled.
   */
  public val underwater_pictures: Sprite4Way? by fromJson()

  public object Serializer :
      JsonReaderSerializer<OffshorePumpGraphicsSet>(OffshorePumpGraphicsSet::class)
}

@Serializable(OrTipTrigger.Serializer::class)
@SerialName("or")
public open class OrTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If at least one of the triggers is fulfilled, this trigger is considered fulfilled.
   */
  public val triggers: List<TipTrigger> by fromJson()

  public object Serializer : JsonReaderSerializer<OrTipTrigger>(OrTipTrigger::class)
}

/**
 * The order property is a simple `string`. When the game needs to sort prototypes (of the same
 * type), it looks at their order properties and sorts those alphabetically. A prototype with an order
 * string of `"a"` will be listed before other prototypes with order string `"b"` or `"c"`. The `"-"`
 * or `"[]"` structures that can be found in vanilla order strings do *not* have any special meaning.
 *
 * The alphabetical sorting uses [lexicographical
 * comparison](https://en.wikipedia.org/wiki/Lexicographic_order) to determine if a given prototype is
 * shown before or after another. If the order strings are equal then the game falls back to comparing
 * the prototype names to determine order.
 */
public typealias Order = String

@Serializable(OrientedCliffPrototype.Serializer::class)
public open class OrientedCliffPrototype : JsonReader() {
  public val collision_bounding_box: BoundingBox by fromJson()

  public val pictures: SpriteVariations by fromJson()

  /**
   * Unused.
   */
  public val fill_volume: UInt by fromJson()

  public object Serializer :
      JsonReaderSerializer<OrientedCliffPrototype>(OrientedCliffPrototype::class)
}

@Serializable(OrientedCliffPrototypeSet.Serializer::class)
public open class OrientedCliffPrototypeSet : JsonReader() {
  public val west_to_east: OrientedCliffPrototype by fromJson()

  public val north_to_south: OrientedCliffPrototype by fromJson()

  public val east_to_west: OrientedCliffPrototype by fromJson()

  public val south_to_north: OrientedCliffPrototype by fromJson()

  public val west_to_north: OrientedCliffPrototype by fromJson()

  public val north_to_east: OrientedCliffPrototype by fromJson()

  public val east_to_south: OrientedCliffPrototype by fromJson()

  public val south_to_west: OrientedCliffPrototype by fromJson()

  public val west_to_south: OrientedCliffPrototype by fromJson()

  public val north_to_west: OrientedCliffPrototype by fromJson()

  public val east_to_north: OrientedCliffPrototype by fromJson()

  public val south_to_east: OrientedCliffPrototype by fromJson()

  public val west_to_none: OrientedCliffPrototype by fromJson()

  public val none_to_east: OrientedCliffPrototype by fromJson()

  public val north_to_none: OrientedCliffPrototype by fromJson()

  public val none_to_south: OrientedCliffPrototype by fromJson()

  public val east_to_none: OrientedCliffPrototype by fromJson()

  public val none_to_west: OrientedCliffPrototype by fromJson()

  public val south_to_none: OrientedCliffPrototype by fromJson()

  public val none_to_north: OrientedCliffPrototype by fromJson()

  public object Serializer :
      JsonReaderSerializer<OrientedCliffPrototypeSet>(OrientedCliffPrototypeSet::class)
}

@Serializable(OtherColors.Serializer::class)
public open class OtherColors : JsonReader() {
  public val less_than: Double by fromJson()

  public val color: Color? by fromJson()

  public val bar: ElementImageSet? by fromJson()

  public object Serializer : JsonReaderSerializer<OtherColors>(OtherColors::class)
}

/**
 * The name of a [ParticlePrototype](prototype:ParticlePrototype).
 */
public typealias ParticleID = String

@Serializable(PasteEntitySettingsTipTrigger.Serializer::class)
@SerialName("paste-entity-settings")
public open class PasteEntitySettingsTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public val source: EntityID? by fromJson()

  public val target: EntityID? by fromJson()

  public val match_type_only: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<PasteEntitySettingsTipTrigger>(PasteEntitySettingsTipTrigger::class)
}

@Serializable(PathFinderSettings.Serializer::class)
public open class PathFinderSettings : JsonReader() {
  /**
   * The pathfinder performs a step of the backward search every `fwd2bwd_ratio`'th step. The
   * minimum allowed value is 2, which means symmetric search.
   */
  public val fwd2bwd_ratio: UInt by fromJson()

  /**
   * When comparing nodes in open which one to check next, heuristic value is multiplied by this
   * ratio. The higher the number the more is the search directed directly towards the goal.
   */
  public val goal_pressure_ratio: Double by fromJson()

  public val use_path_cache: Boolean by fromJson()

  /**
   * When this is exhausted no more requests are allowed, at the moment the first path to exhaust
   * this will be finished (even if it is hundreds of steps).
   */
  public val max_steps_worked_per_tick: Double by fromJson()

  public val max_work_done_per_tick: UInt by fromJson()

  /**
   * Number of elements in the cache.
   */
  public val short_cache_size: UInt by fromJson()

  public val long_cache_size: UInt by fromJson()

  /**
   * Minimal distance to goal for path to be searched in short path cache.
   */
  public val short_cache_min_cacheable_distance: Double by fromJson()

  /**
   * Minimal number of algorithm steps for path to be inserted into the short path cache.
   */
  public val short_cache_min_algo_steps_to_cache: UInt by fromJson()

  /**
   * Minimal distance to goal for path to be searched in long path cache.
   */
  public val long_cache_min_cacheable_distance: Double by fromJson()

  /**
   * When searching for connection to path cache path, search at most for this number of steps times
   * the initial estimate.
   */
  public val cache_max_connect_to_cache_steps_multiplier: UInt by fromJson()

  /**
   * When looking for path from cache make sure it doesn't start too far from requested start in
   * relative distance terms.
   */
  public val cache_accept_path_start_distance_ratio: Double by fromJson()

  /**
   * When looking for path from cache make sure it doesn't end too far from requested end. This is
   * typically higher than accept value for the start because the end target can be moving.
   */
  public val cache_accept_path_end_distance_ratio: Double by fromJson()

  /**
   * Same as cache_accept_path_start_distance_ratio, but used for negative cache queries.
   */
  public val negative_cache_accept_path_start_distance_ratio: Double by fromJson()

  /**
   * Same as cache_accept_path_end_distance_ratio, but used for negative cache queries.
   */
  public val negative_cache_accept_path_end_distance_ratio: Double by fromJson()

  /**
   * When assigning rating to the best path this * start distances is considered.
   */
  public val cache_path_start_distance_rating_multiplier: Double by fromJson()

  /**
   * When assigning rating to the best path this * end distances is considered. This is typically
   * higher than value for the start to achieve better path end quality.
   */
  public val cache_path_end_distance_rating_multiplier: Double by fromJson()

  /**
   * Somewhere along the path is stuck enemy we need to avoid. This is mainly to handle situations
   * when units have arrived and are attacking the target then units further in the back will use this
   * and run around the target.
   */
  public val stale_enemy_with_same_destination_collision_penalty: Double by fromJson()

  /**
   * If there is a moving unit further than this we don't really care.
   */
  public val ignore_moving_enemy_collision_distance: Double by fromJson()

  /**
   * Enemy is not moving/or is too close and has different destination.
   */
  public val enemy_with_different_destination_collision_penalty: Double by fromJson()

  /**
   * Simplification for now; collision with everything else is this.
   */
  public val general_entity_collision_penalty: Double by fromJson()

  /**
   * Collision penalty for successors of positions that require destroy to reach.
   */
  public val general_entity_subsequent_collision_penalty: Double by fromJson()

  /**
   * Collision penalty for collisions in the extended bounding box but outside the entity's actual
   * bounding box.
   */
  public val extended_collision_penalty: Double by fromJson()

  /**
   * Up until this amount any client will be served by the path finder (no estimate on the path
   * length).
   */
  public val max_clients_to_accept_any_new_request: UInt by fromJson()

  /**
   * From max_clients_to_accept_any_new_request till this one only those that have a short estimate
   * will be served.
   */
  public val max_clients_to_accept_short_new_request: UInt by fromJson()

  /**
   * This is the "threshold" to decide what is short and what is not.
   */
  public val direct_distance_to_consider_short_request: UInt by fromJson()

  /**
   * If a short request takes more than this many steps, it will be rescheduled as a long request.
   */
  public val short_request_max_steps: UInt by fromJson()

  /**
   * How many steps will be allocated to short requests each tick, as a ratio of all available steps
   * per tick.
   */
  public val short_request_ratio: Double by fromJson()

  /**
   * Absolute minimum of steps that will be performed for every path find request no matter what.
   */
  public val min_steps_to_check_path_find_termination: UInt by fromJson()

  /**
   * If the current actual cost from start is higher than this times estimate of start to goal then
   * path finding is terminated.
   */
  public val start_to_goal_cost_multiplier_to_terminate_path_find: Double by fromJson()

  public val overload_levels: List<UInt> by fromJson()

  public val overload_multipliers: List<Double> by fromJson()

  public object Serializer : JsonReaderSerializer<PathFinderSettings>(PathFinderSettings::class)
}

@Serializable
public enum class PipeConnectionDefinitionType {
  input,
  `input-output`,
  output,
}

@Serializable(PipeConnectionDefinition.Serializer::class)
public open class PipeConnectionDefinition : JsonReader() {
  /**
   * Where pipes can connect to this fluidbox regardless the directions of entity.
   */
  public val position: Vector? by fromJson()

  /**
   * Only loaded, and mandatory if `position` is not defined.
   *
   * Where pipes can connect to this fluidbox, depending on the entity direction.
   *
   * Table must have 4 members, which are 4 explicit positions corresponding to the 4 directions of
   * entity. Positions must correspond to directions going one after another.
   */
  public val positions: List<Vector>? by fromJson()

  /**
   * `0` means not underground.
   */
  public val max_underground_distance: UInt? by fromJson()

  public val type: PipeConnectionDefinitionType? by fromJson()

  public object Serializer :
      JsonReaderSerializer<PipeConnectionDefinition>(PipeConnectionDefinition::class)
}

@Serializable(PipePictures.Serializer::class)
public open class PipePictures : JsonReader() {
  public val straight_vertical_single: Sprite by fromJson()

  public val straight_vertical: Sprite by fromJson()

  public val straight_vertical_window: Sprite by fromJson()

  public val straight_horizontal: Sprite by fromJson()

  public val straight_horizontal_window: Sprite by fromJson()

  public val corner_up_right: Sprite by fromJson()

  public val corner_up_left: Sprite by fromJson()

  public val corner_down_right: Sprite by fromJson()

  public val corner_down_left: Sprite by fromJson()

  public val t_up: Sprite by fromJson()

  public val t_down: Sprite by fromJson()

  public val t_right: Sprite by fromJson()

  public val t_left: Sprite by fromJson()

  public val cross: Sprite by fromJson()

  public val ending_up: Sprite by fromJson()

  public val ending_down: Sprite by fromJson()

  public val ending_right: Sprite by fromJson()

  public val ending_left: Sprite by fromJson()

  public val horizontal_window_background: Sprite by fromJson()

  public val vertical_window_background: Sprite by fromJson()

  public val fluid_background: Sprite by fromJson()

  /**
   * Visualizes the flow of the fluid in the pipe. Drawn when `(fluid_temp - fluid_min_temp) /
   * (fluid_max_temp - fluid_min_temp)` is less than or equal to `1/3` and the fluid's temperature is
   * below [FluidPrototype::gas_temperature](prototype:FluidPrototype::gas_temperature).
   */
  public val low_temperature_flow: Sprite by fromJson()

  /**
   * Visualizes the flow of the fluid in the pipe. Drawn when `(fluid_temp - fluid_min_temp) /
   * (fluid_max_temp - fluid_min_temp)` is larger than `1/3` and less than or equal to `2/3` and the
   * fluid's temperature is below
   * [FluidPrototype::gas_temperature](prototype:FluidPrototype::gas_temperature).
   */
  public val middle_temperature_flow: Sprite by fromJson()

  /**
   * Visualizes the flow of the fluid in the pipe. Drawn when `(fluid_temp - fluid_min_temp) /
   * (fluid_max_temp - fluid_min_temp)` is larger than `2/3` and the fluid's temperature is below
   * [FluidPrototype::gas_temperature](prototype:FluidPrototype::gas_temperature).
   */
  public val high_temperature_flow: Sprite by fromJson()

  /**
   * Visualizes the flow of the fluid in the pipe. Drawn when the fluid's temperature is above
   * [FluidPrototype::gas_temperature](prototype:FluidPrototype::gas_temperature).
   */
  public val gas_flow: Animation by fromJson()

  public object Serializer : JsonReaderSerializer<PipePictures>(PipePictures::class)
}

@Serializable(PipeToGroundPictures.Serializer::class)
public open class PipeToGroundPictures : JsonReader() {
  public val down: Sprite by fromJson()

  public val up: Sprite by fromJson()

  public val left: Sprite by fromJson()

  public val right: Sprite by fromJson()

  public object Serializer : JsonReaderSerializer<PipeToGroundPictures>(PipeToGroundPictures::class)
}

@Serializable(PlaceAsTile.Serializer::class)
public open class PlaceAsTile : JsonReader() {
  public val result: TileID by fromJson()

  public val condition: CollisionMask by fromJson()

  public val condition_size: Int by fromJson()

  public object Serializer : JsonReaderSerializer<PlaceAsTile>(PlaceAsTile::class)
}

@Serializable(PlaceEquipmentTipTrigger.Serializer::class)
@SerialName("place-equipment")
public open class PlaceEquipmentTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public val equipment: EquipmentID? by fromJson()

  public object Serializer :
      JsonReaderSerializer<PlaceEquipmentTipTrigger>(PlaceEquipmentTipTrigger::class)
}

/**
 * Defines when controller vibrations should be played.
 */
@Serializable
public enum class PlayFor {
  character_actions,
  everything,
}

@Serializable(PlaySoundTriggerEffectItem.Serializer::class)
@SerialName("play-sound")
public open class PlaySoundTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val sound: Sound by fromJson()

  /**
   * Negative values are silently clamped to 0.
   */
  public val min_distance: Float? by fromJson()

  /**
   * Negative values are silently clamped to 0.
   */
  public val max_distance: Float? by fromJson()

  /**
   * Negative values are silently clamped to 0.
   */
  public val volume_modifier: Float? by fromJson()

  /**
   * Negative values are silently clamped to 0.
   */
  public val audible_distance_modifier: Float? by fromJson()

  public val play_on_target_position: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<PlaySoundTriggerEffectItem>(PlaySoundTriggerEffectItem::class)
}

@Serializable(PlayerColorData.Serializer::class)
public open class PlayerColorData : JsonReader() {
  public val name: String by fromJson()

  public val player_color: Color by fromJson()

  public val chat_color: Color by fromJson()

  public object Serializer : JsonReaderSerializer<PlayerColorData>(PlayerColorData::class)
}

@Serializable
public enum class PlayerInputMethodFilter {
  all,
  keyboard_and_mouse,
  game_controller,
}

/**
 * The pollution settings, the values are for 60 ticks (1 second).
 */
@Serializable(PollutionSettings.Serializer::class)
public open class PollutionSettings : JsonReader() {
  public val enabled: Boolean by fromJson()

  /**
   * Amount that is diffused to neighboring chunks.
   */
  public val diffusion_ratio: Double by fromJson()

  /**
   * This much pollution units must be on the chunk to start diffusing.
   */
  public val min_to_diffuse: Double by fromJson()

  /**
   * Constant modifier a percentage of 1; the pollution eaten by a chunks tiles. Also known as
   * absorption modifier.
   */
  public val ageing: Double by fromJson()

  /**
   * Anything bigger than this is visualized as this value.
   */
  public val expected_max_per_chunk: Double by fromJson()

  /**
   * Anything lower than this (but > 0) is visualized as this value.
   */
  public val min_to_show_per_chunk: Double by fromJson()

  public val min_pollution_to_damage_trees: Double by fromJson()

  public val pollution_with_max_forest_damage: Double by fromJson()

  public val pollution_restored_per_tree_damage: Double by fromJson()

  public val pollution_per_tree_damage: Double by fromJson()

  public val max_pollution_to_restore_trees: Double by fromJson()

  public val enemy_attack_pollution_consumption_modifier: Double by fromJson()

  public object Serializer : JsonReaderSerializer<PollutionSettings>(PollutionSettings::class)
}

/**
 * Defaults to loading products as items.
 */
@Serializable
public sealed interface ProductPrototype

@Serializable
public enum class ProductionType {
  None,
  none,
  input,
  `input-output`,
  output,
}

@Serializable(ProgrammableSpeakerInstrument.Serializer::class)
public open class ProgrammableSpeakerInstrument : JsonReader() {
  public val name: String by fromJson()

  public val notes: List<ProgrammableSpeakerNote> by fromJson()

  public object Serializer :
      JsonReaderSerializer<ProgrammableSpeakerInstrument>(ProgrammableSpeakerInstrument::class)
}

@Serializable(ProgrammableSpeakerNote.Serializer::class)
public open class ProgrammableSpeakerNote : JsonReader() {
  public val name: String by fromJson()

  public val sound: Sound by fromJson()

  public object Serializer :
      JsonReaderSerializer<ProgrammableSpeakerNote>(ProgrammableSpeakerNote::class)
}

@Serializable(ProgressBarStyleSpecification.Serializer::class)
@SerialName("progressbar_style")
public open class ProgressBarStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val bar_width: UInt? by fromJson()

  public val color: Color? by fromJson()

  public val other_colors: List<OtherColors>? by fromJson()

  public val bar: ElementImageSet? by fromJson()

  public val bar_background: ElementImageSet? by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val font: String? by fromJson()

  public val font_color: Color? by fromJson()

  public val filled_font_color: Color? by fromJson()

  public val embed_text_in_bar: Boolean? by fromJson()

  public val side_text_padding: Short? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ProgressBarStyleSpecification>(ProgressBarStyleSpecification::class)
}

@Serializable(ProjectileAttackParameters.Serializer::class)
@SerialName("projectile")
public open class ProjectileAttackParameters : BaseAttackParameters(), AttackParameters {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * When used with `projectile_creation_parameters`, this offsets what the turret's sprite looks
   * at. Setting to `{0,1}` will cause the turret to aim one tile up from the target but the projectile
   * will still aim for the entity. Can be used to give the illusion of height but can also confuse aim
   * logic when set too high.
   *
   * When used without `projectile_creation_parameters`, this sets the turret's rotation axis.
   */
  public val projectile_center: Vector? by fromJson()

  public val projectile_creation_distance: Float? by fromJson()

  /**
   * Used to show bullet shells/casings being ejected from the gun, see [artillery shell
   * casings](https://factorio.com/blog/post/fff-345).
   */
  public val shell_particle: CircularParticleCreationSpecification? by fromJson()

  /**
   * Used to shoot projectiles from arbitrary points. Used by worms and multi-barreled weapons. Use
   * multiple points with the same angle to cause the turret to shoot from multiple barrels. If not set
   * then the launch positions are calculated using `projectile_center` and
   * `projectile_creation_distance`.
   */
  public val projectile_creation_parameters: CircularProjectileCreationSpecification? by fromJson()

  /**
   * Used to shoot from different sides of the turret. Setting to `0.25` shoots from the right side,
   * `0.5` shoots from the back, and `0.75` shoots from the left. The turret will look at the enemy as
   * normal but the bullet will spawn from the offset position. Can be used to create right-handed
   * weapons.
   */
  public val projectile_orientation_offset: Float? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ProjectileAttackParameters>(ProjectileAttackParameters::class)
}

@Serializable(ProjectileTriggerDelivery.Serializer::class)
@SerialName("projectile")
public open class ProjectileTriggerDelivery : TriggerDeliveryItem(), TriggerDelivery {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [ProjectilePrototype](prototype:ProjectilePrototype).
   */
  public val projectile: EntityID by fromJson()

  /**
   * Starting speed in tiles per tick.
   */
  public val starting_speed: Float by fromJson()

  public val starting_speed_deviation: Float? by fromJson()

  /**
   * Maximum deviation of the projectile from source orientation, in +/- (`x radians / 2`). Example:
   * `3.14 radians -> +/- (180° / 2)`, meaning up to 90° deviation in either direction of rotation.
   */
  public val direction_deviation: Float? by fromJson()

  /**
   * The maximum deviation of the projectile maximum range from `max_range` is `max_range ×
   * range_deviation ÷ 2`. This means a deviation of `0.5` will appear as a maximum of `0.25` (25%)
   * deviation of an initial range goal. Post-deviation range may exceed `max_range` or be less than
   * `min_range`.
   */
  public val range_deviation: Float? by fromJson()

  public val max_range: Double? by fromJson()

  public val min_range: Double? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ProjectileTriggerDelivery>(ProjectileTriggerDelivery::class)
}

/**
 * A mapping of arrays of
 * [PumpConnectorGraphicsAnimations](prototype:PumpConnectorGraphicsAnimation) to all 4 directions of
 * the pump connection (to a fluid wagon).
 */
@Serializable(PumpConnectorGraphics.Serializer::class)
public open class PumpConnectorGraphics : JsonReader() {
  /**
   * Size of the array must be 6 or more.
   */
  public val north: List<PumpConnectorGraphicsAnimation> by fromJson()

  /**
   * Size of the array must be 6 or more.
   */
  public val east: List<PumpConnectorGraphicsAnimation> by fromJson()

  /**
   * Size of the array must be 6 or more.
   */
  public val south: List<PumpConnectorGraphicsAnimation> by fromJson()

  /**
   * Size of the array must be 6 or more.
   */
  public val west: List<PumpConnectorGraphicsAnimation> by fromJson()

  public object Serializer :
      JsonReaderSerializer<PumpConnectorGraphics>(PumpConnectorGraphics::class)
}

@Serializable(PumpConnectorGraphicsAnimation.Serializer::class)
public open class PumpConnectorGraphicsAnimation : JsonReader() {
  public val standup_base: Animation? by fromJson()

  public val standup_top: Animation? by fromJson()

  public val standup_shadow: Animation? by fromJson()

  public val connector: Animation? by fromJson()

  public val connector_shadow: Animation? by fromJson()

  public object Serializer :
      JsonReaderSerializer<PumpConnectorGraphicsAnimation>(PumpConnectorGraphicsAnimation::class)
}

/**
 * The push back effect used by the [discharge
 * defense](https://wiki.factorio.com/Discharge_defense).
 *
 * Aims to push the target entity away from the source entity by the `distance` from the target
 * entity's current position. Searches within double the `distance` from the pushed to position for the
 * nearest non-colliding position for the target entity to be teleported too. If no valid non-colliding
 * position is found or the target is not teleportable, then no push back occurs.
 */
@Serializable(PushBackTriggerEffectItem.Serializer::class)
@SerialName("push-back")
public open class PushBackTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val distance: Float by fromJson()

  public object Serializer :
      JsonReaderSerializer<PushBackTriggerEffectItem>(PushBackTriggerEffectItem::class)
}

@Serializable(RadioButtonStyleSpecification.Serializer::class)
@SerialName("radiobutton_style")
public open class RadioButtonStyleSpecification : StyleWithClickableGraphicalSetSpecification(),
    StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val font: String? by fromJson()

  public val font_color: Color? by fromJson()

  public val disabled_font_color: Color? by fromJson()

  public val text_padding: UInt? by fromJson()

  public object Serializer :
      JsonReaderSerializer<RadioButtonStyleSpecification>(RadioButtonStyleSpecification::class)
}

/**
 * Sprite to be shown around the entity when it is selected/held in the cursor.
 */
@Serializable(RadiusVisualisationSpecification.Serializer::class)
public open class RadiusVisualisationSpecification : JsonReader() {
  public val sprite: Sprite? by fromJson()

  /**
   * Must be greater than or equal to 0.
   */
  public val distance: Double? by fromJson()

  public val offset: Vector? by fromJson()

  public val draw_in_cursor: Boolean? by fromJson()

  public val draw_on_selection: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<RadiusVisualisationSpecification>(RadiusVisualisationSpecification::class)
}

@Serializable(RailPictureSet.Serializer::class)
public open class RailPictureSet : JsonReader() {
  public val straight_rail_horizontal: RailPieceLayers by fromJson()

  public val straight_rail_vertical: RailPieceLayers by fromJson()

  public val straight_rail_diagonal_left_top: RailPieceLayers by fromJson()

  public val straight_rail_diagonal_right_top: RailPieceLayers by fromJson()

  public val straight_rail_diagonal_right_bottom: RailPieceLayers by fromJson()

  public val straight_rail_diagonal_left_bottom: RailPieceLayers by fromJson()

  public val curved_rail_vertical_left_top: RailPieceLayers by fromJson()

  public val curved_rail_vertical_right_top: RailPieceLayers by fromJson()

  public val curved_rail_vertical_right_bottom: RailPieceLayers by fromJson()

  public val curved_rail_vertical_left_bottom: RailPieceLayers by fromJson()

  public val curved_rail_horizontal_left_top: RailPieceLayers by fromJson()

  public val curved_rail_horizontal_right_top: RailPieceLayers by fromJson()

  public val curved_rail_horizontal_right_bottom: RailPieceLayers by fromJson()

  public val curved_rail_horizontal_left_bottom: RailPieceLayers by fromJson()

  public val rail_endings: Sprite8Way by fromJson()

  public object Serializer : JsonReaderSerializer<RailPictureSet>(RailPictureSet::class)
}

/**
 * Used for graphics by [RailPrototype](prototype:RailPrototype) and
 * [RailRemnantsPrototype](prototype:RailRemnantsPrototype).
 */
@Serializable(RailPieceLayers.Serializer::class)
public open class RailPieceLayers : JsonReader() {
  /**
   * Must have between 1 and 4 variations.
   */
  public val metals: SpriteVariations by fromJson()

  /**
   * Must have same number of variations as `metals`.
   */
  public val backplates: SpriteVariations by fromJson()

  /**
   * Must have between 1 and 4 variations.
   */
  public val ties: SpriteVariations by fromJson()

  /**
   * Must have between 1 and 4 variations.
   */
  public val stone_path: SpriteVariations by fromJson()

  /**
   * Must have less or equal than 4 variations.
   */
  public val stone_path_background: SpriteVariations? by fromJson()

  public val segment_visualisation_middle: Sprite? by fromJson()

  public val segment_visualisation_ending_front: Sprite? by fromJson()

  public val segment_visualisation_ending_back: Sprite? by fromJson()

  public val segment_visualisation_continuing_front: Sprite? by fromJson()

  public val segment_visualisation_continuing_back: Sprite? by fromJson()

  public object Serializer : JsonReaderSerializer<RailPieceLayers>(RailPieceLayers::class)
}

@Serializable(RandomPenaltyArguments.Serializer::class)
public open class RandomPenaltyArguments : JsonReader() {
  /**
   * Number used to seed the random generator.
   */
  public val x: NoiseNumber by fromJson()

  /**
   * Number used to seed the random generator.
   */
  public val y: NoiseNumber by fromJson()

  /**
   * Number that the penalty is applied to.
   */
  public val source: NoiseNumber by fromJson()

  /**
   * Integer used to seed the random generator.
   */
  public val seed: ConstantNoiseNumber? by fromJson()

  public val amplitude: ConstantNoiseNumber? by fromJson()

  public object Serializer :
      JsonReaderSerializer<RandomPenaltyArguments>(RandomPenaltyArguments::class)
}

/**
 * Specified by a [float](prototype:float) between 0 and 1, including 0 and excluding 1.
 */
public typealias RealOrientation = Float

/**
 * The name of a [RecipeCategory](prototype:RecipeCategory).
 */
public typealias RecipeCategoryID = String

/**
 * Used when defining a [RecipePrototype](prototype:RecipePrototype) that uses difficulty. For a
 * recipe without difficulty, these same properties are defined on the prototype itself.
 */
@Serializable(RecipeData.Serializer::class)
public open class RecipeData : JsonReader() {
  /**
   * A table containing ingredient names and counts. Can also contain information about fluid
   * temperature and catalyst amounts. The catalyst amounts are automatically calculated from the
   * recipe, or can be set manually in the IngredientPrototype (see
   * [here](https://factorio.com/blog/post/fff-256)).
   *
   * The maximum ingredient amount is 65 535. Can be set to an empty table to create a recipe that
   * needs no ingredients.
   *
   * Duplicate ingredients, e.g. two entries with the same name, are *not* allowed. In-game, the
   * item ingredients are ordered by
   * [ItemGroup::order_in_recipe](prototype:ItemGroup::order_in_recipe).
   */
  public val ingredients: List<IngredientPrototype> by fromJson()

  /**
   * A table containing result names and counts. Can also contain information about fluid
   * temperature and catalyst amounts. The catalyst amounts are automatically calculated from the
   * recipe, or can be set manually in the ProductPrototype (see
   * [here](https://factorio.com/blog/post/fff-256)).
   *
   * Can be set to an empty table to create a recipe that produces nothing. Duplicate results, e.g.
   * two entries with the same name, are allowed.
   */
  public val results: List<ProductPrototype>? by fromJson()

  /**
   * The item created by this recipe. Must be the name of an [item](prototype:ItemPrototype), such
   * as `"iron-gear-wheel"`.
   *
   * Only loaded, and mandatory if `results` is not defined.
   */
  public val result: ItemID? by fromJson()

  /**
   * The number of items created by this recipe.
   *
   * Only loaded if `results` is not defined.
   */
  public val result_count: UShort? by fromJson()

  /**
   * For recipes with one or more products: Subgroup, localised_name and icon default to the values
   * of the singular/main product, but can be overwritten by the recipe. Setting the main_product to an
   * empty string (`""`) forces the title in the recipe tooltip to use the recipe's name (not that of
   * the product) and shows the products in the tooltip.
   *
   * If 1) there are multiple products and this property is nil, 2) this property is set to an empty
   * string (`""`), or 3) there are no products, the recipe will use the localised_name, icon, and
   * subgroup of the recipe. icon and subgroup become non-optional.
   */
  public val main_product: String? by fromJson()

  /**
   * The amount of time it takes to make this recipe. Must be `> 0.001`. Equals the number of
   * seconds it takes to craft at crafting speed `1`.
   */
  public val energy_required: Double? by fromJson()

  public val emissions_multiplier: Double? by fromJson()

  public val requester_paste_multiplier: UInt? by fromJson()

  /**
   * Used to determine how many extra items are put into an assembling machine before it's
   * considered "full enough". See [insertion
   * limits](https://wiki.factorio.com/Inserters#Insertion_limits).
   *
   * If set to `0`, it instead uses the following formula: `1.166 / (energy_required / the
   * assembler's crafting_speed)`, rounded up, and clamped to be between`2` and `100`. The numbers used
   * in this formula can be changed by the [UtilityConstants](prototype:UtilityConstants) properties
   * `dynamic_recipe_overload_factor`, `minimum_recipe_overload_multiplier`, and
   * `maximum_recipe_overload_multiplier`.
   */
  public val overload_multiplier: UInt? by fromJson()

  /**
   * Whether the recipe is allowed to have the extra inserter overload bonus applied (4 * stack
   * inserter stack size).
   */
  public val allow_inserter_overload: Boolean? by fromJson()

  /**
   * This can be `false` to disable the recipe at the start of the game, or `true` to leave it
   * enabled.
   *
   * If a recipe is unlocked via technology, this should be set to `false`.
   */
  public val enabled: Boolean? by fromJson()

  /**
   * Hides the recipe from crafting menus.
   */
  public val hidden: Boolean? by fromJson()

  /**
   * Hides the recipe from item/fluid production statistics.
   */
  public val hide_from_stats: Boolean? by fromJson()

  /**
   * Hides the recipe from the player's crafting screen. The recipe will still show up for selection
   * in machines.
   */
  public val hide_from_player_crafting: Boolean? by fromJson()

  /**
   * Whether this recipe is allowed to be broken down for the recipe tooltip "Total raw"
   * calculations.
   */
  public val allow_decomposition: Boolean? by fromJson()

  /**
   * Whether the recipe can be used as an intermediate recipe in hand-crafting.
   */
  public val allow_as_intermediate: Boolean? by fromJson()

  /**
   * Whether the recipe is allowed to use intermediate recipes when hand-crafting.
   */
  public val allow_intermediates: Boolean? by fromJson()

  /**
   * Whether the "Made in: <Machine>" part of the tool-tip should always be present, and not only
   * when the recipe can't be hand-crafted.
   */
  public val always_show_made_in: Boolean? by fromJson()

  /**
   * Whether the recipe name should have the product amount in front of it. E.g. "2x Transport belt"
   */
  public val show_amount_in_title: Boolean? by fromJson()

  /**
   * Whether the products are always shown in the recipe tooltip.
   */
  public val always_show_products: Boolean? by fromJson()

  /**
   * Whether enabling this recipe unlocks its item products to show in selection lists (item
   * filters, logistic requests, etc.).
   */
  public val unlock_results: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<RecipeData>(RecipeData::class)
}

/**
 * The name of a [RecipePrototype](prototype:RecipePrototype).
 */
public typealias RecipeID = String

/**
 * The render layer specifies the order of the sprite when rendering, most of the objects have it
 * hardcoded in the source, but some are configurable. The union contains valid values from lowest to
 * highest.
 */
@Serializable
public enum class RenderLayer {
  `water-tile`,
  `ground-tile`,
  `tile-transition`,
  decals,
  `lower-radius-visualization`,
  `radius-visualization`,
  `transport-belt-integration`,
  resource,
  `building-smoke`,
  decorative,
  `ground-patch`,
  `ground-patch-higher`,
  `ground-patch-higher2`,
  remnants,
  floor,
  `transport-belt`,
  `transport-belt-endings`,
  `transport-belt-circuit-connector`,
  `floor-mechanics-under-corpse`,
  corpse,
  `floor-mechanics`,
  item,
  `lower-object`,
  `lower-object-above-shadow`,
  `object`,
  `higher-object-under`,
  `higher-object-above`,
  `item-in-inserter-hand`,
  wires,
  `wires-above`,
  `entity-info-icon`,
  `entity-info-icon-above`,
  explosion,
  projectile,
  smoke,
  `air-object`,
  `air-entity-info-icon`,
  `light-effect`,
  `selection-box`,
  `higher-selection-box`,
  `collision-selection-box`,
  arrow,
  cursor,
}

@Serializable(ResearchTechnologyTipTrigger.Serializer::class)
@SerialName("research")
public open class ResearchTechnologyTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val technology: TechnologyID by fromJson()

  public object Serializer :
      JsonReaderSerializer<ResearchTechnologyTipTrigger>(ResearchTechnologyTipTrigger::class)
}

/**
 * Resistances to certain types of attacks from enemy, and physical damage. See
 * [Damage](https://wiki.factorio.com/Damage).
 */
@Serializable(Resistance.Serializer::class)
public open class Resistance : JsonReader() {
  public val type: DamageTypeID by fromJson()

  /**
   * The [flat resistance](https://wiki.factorio.com/Damage#Decrease.2C_or_.22flat.22_resistance) to
   * the given damage type. (Higher is better)
   */
  public val decrease: Float? by fromJson()

  /**
   * The [percentage resistance](https://wiki.factorio.com/Damage#Percentage_resistance) to the
   * given damage type. (Higher is better)
   */
  public val percent: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<Resistance>(Resistance::class)
}

/**
 * The name of a [ResourceCategory](prototype:ResourceCategory).
 */
public typealias ResourceCategoryID = String

@Serializable
public enum class RichTextSetting {
  enabled,
  disabled,
  highlight,
}

@Serializable(RotatedAnimation.Serializer::class)
public open class RotatedAnimation : AnimationParameters() {
  /**
   * If this property is present, all RotatedAnimation definitions have to be placed as entries in
   * the array, and they will all be loaded from there. `layers` may not be an empty table. Each
   * definition in the array may also have the `layers` property.
   *
   * If this property is present, all other properties, including those inherited from
   * AnimationParameters, are ignored.
   */
  public val layers: List<RotatedAnimation>? by fromJson()

  /**
   * Only loaded, and mandatory if `layers` is not defined.
   *
   * The sequential animation instance is loaded equal to the entities direction within the
   * `direction_count` setting.
   *
   * Direction count to [Direction](prototype:Direction) (animation sequence number):
   *
   * - `1`: North (1)
   *
   * - `2`: North (1), South (2)
   *
   * - `4`: North (1), East (2), South (3), West (4)
   *
   * - `8`: North (1), Northeast (2), East (3), Southeast (4), South (5), Southwest (6), West (7),
   * Northwest (8)
   */
  public val direction_count: UInt? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * If this property exists and high resolution sprites are turned on, this is used to load the
   * animation.
   */
  public val hr_version: RotatedAnimation? by fromJson()

  /**
   * Only loaded, and mandatory if `layers`, `stripes`, and `filenames` are not defined.
   *
   * The path to the sprite file to use.
   */
  override val filename: FileName? by fromJson()

  /**
   * Only loaded if both `layers` and `stripes` are not defined.
   */
  public val filenames: List<FileName>? by fromJson()

  /**
   * Only loaded if `layers` is not defined. Mandatory if `filenames` is defined.
   */
  public val lines_per_file: UInt? by fromJson()

  /**
   * Only loaded if `layers` is not defined. Mandatory if `filenames` is defined.
   */
  public val slice: UInt? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val still_frame: UInt? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * If `true`, `direction_count` must be greater than `1`.
   */
  public val axially_symmetrical: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val counterclockwise: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val middle_orientation: RealOrientation? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Automatically clamped to be between `0` and `1`.
   */
  public val orientation_range: Float? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val apply_projection: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val stripes: List<Stripe>? by fromJson()

  public object Serializer : JsonReaderSerializer<RotatedAnimation>(RotatedAnimation::class)
}

/**
 * A map of rotated animations for all 4 directions of the entity. If this is loaded as a single
 * RotatedAnimation, it applies to all directions.
 */
@Serializable(RotatedAnimation4WayValues.Serializer::class)
public open class RotatedAnimation4WayValues : JsonReader() {
  public val north: RotatedAnimation by fromJson()

  /**
   * Defaults to the north animation.
   */
  public val east: RotatedAnimation? by fromJson()

  /**
   * Defaults to the north animation.
   */
  public val south: RotatedAnimation? by fromJson()

  /**
   * Defaults to the east animation.
   */
  public val west: RotatedAnimation? by fromJson()

  public object Serializer :
      JsonReaderSerializer<RotatedAnimation4WayValues>(RotatedAnimation4WayValues::class)
}

/**
 * A map of rotated animations for all 4 directions of the entity. If this is loaded as a single
 * RotatedAnimation, it applies to all directions.
 */
public typealias RotatedAnimation4Way = UnknownUnion

public typealias RotatedAnimationVariations = ItemOrList<RotatedAnimation>

/**
 * Specifies series of sprites used to visualize different rotations of the object.
 */
@Serializable(RotatedSprite.Serializer::class)
public open class RotatedSprite : SpriteParameters() {
  /**
   * If this property is present, all RotatedSprite definitions have to be placed as entries in the
   * array, and they will all be loaded from there. `layers` may not be an empty table. Each definition
   * in the array may also have the `layers` property.
   *
   * If this property is present, all other properties, including those inherited from
   * SpriteParameters, are ignored.
   */
  public val layers: List<RotatedSprite>? by fromJson()

  /**
   * Only loaded, and mandatory if `layers` is not defined.
   *
   * Count of direction (frames) specified.
   */
  public val direction_count: UShort? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * If this property exists and high resolution sprites are turned on, this is used to load the
   * sprite.
   */
  public val hr_version: RotatedSprite? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * The path to the sprite file to use.
   */
  override val filename: FileName? by fromJson()

  /**
   * Only loaded, and mandatory if both `layers` and `filename` are not defined.
   */
  public val filenames: List<FileName>? by fromJson()

  /**
   * Only loaded if `layers` is not defined. Mandatory if `filenames` is defined.
   */
  public val lines_per_file: ULong? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Number of slices this is sliced into when using the "optimized atlas packing" option. If you
   * are a modder, you can just ignore this property. As an example, if this is `4`, the sprite will be
   * sliced into a `4x4` grid.
   */
  public val slice: SpriteSizeType? by fromJson("dice")

  /**
   * Only loaded if `layers` is not defined.
   *
   * Same as `slice`, but this specifies only how many slices there are on the x-axis.
   */
  public val slice_x: SpriteSizeType? by fromJson("dice_x")

  /**
   * Only loaded if `layers` is not defined.
   *
   * Same as `slice`, but this specifies only how many slices there are on the y-axis.
   */
  public val slice_y: SpriteSizeType? by fromJson("dice_y")

  /**
   * Only loaded if `layers` is not defined.
   *
   * Unused.
   */
  override val generate_sdf: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * When `true`, the same picture is used for left/right direction, just flipped, which can save
   * half of the space required, but is not usable once the picture contains shadows, etc.
   */
  public val axially_symmetrical: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val back_equals_front: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Used to fix the inconsistency of direction of the entity in 3d when rendered and direction on
   * the screen (where the 45 degree angle for projection is used).
   */
  public val apply_projection: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Set to `true` to indicate sprites in the spritesheet are in counterclockwise order.
   */
  public val counterclockwise: Boolean? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Once the specified number of pictures is loaded, other pictures are loaded on other line. This
   * is to allow having more sprites in matrix, to input files with too high width. The game engine
   * limits the width of any input files to 8192px, so it is compatible with most graphics cards. 0
   * means that all the pictures are in one horizontal line.
   */
  public val line_length: UInt? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   */
  public val allow_low_quality_rotation: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<RotatedSprite>(RotatedSprite::class)
}

@Serializable(ScriptTriggerEffectItem.Serializer::class)
@SerialName("script")
public open class ScriptTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * The effect ID that will be provided in
   * [on_script_trigger_effect](runtime:on_script_trigger_effect).
   */
  public val effect_id: String by fromJson()

  public object Serializer :
      JsonReaderSerializer<ScriptTriggerEffectItem>(ScriptTriggerEffectItem::class)
}

public sealed class ScrollBarStyleSpecification : BaseStyleSpecification() {
  public val background_graphical_set: ElementImageSet? by fromJson()

  public val thumb_button_style: ButtonStyleSpecification? by fromJson()
}

@Serializable(ScrollPaneStyleSpecification.Serializer::class)
@SerialName("scroll_pane_style")
public open class ScrollPaneStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val vertical_flow_style: VerticalFlowStyleSpecification? by fromJson()

  public val horizontal_scrollbar_style: HorizontalScrollBarStyleSpecification? by fromJson()

  public val vertical_scrollbar_style: VerticalScrollBarStyleSpecification? by fromJson()

  public val graphical_set: ElementImageSet? by fromJson()

  public val background_graphical_set: ElementImageSet? by fromJson()

  /**
   * Sets `extra_top_padding_when_activated`, `extra_bottom_padding_when_activated`,
   * `extra_left_padding_when_activated` and `extra_right_padding_when_activated`.
   */
  public val extra_padding_when_activated: Int? by fromJson()

  public val extra_top_padding_when_activated: Int? by fromJson()

  public val extra_bottom_padding_when_activated: Int? by fromJson()

  public val extra_left_padding_when_activated: Int? by fromJson()

  public val extra_right_padding_when_activated: Int? by fromJson()

  /**
   * Sets `extra_top_margin_when_activated`, `extra_bottom_margin_when_activated`,
   * `extra_left_margin_when_activated` and `extra_right_margin_when_activated`.
   */
  public val extra_margin_when_activated: Int? by fromJson()

  public val extra_top_margin_when_activated: Int? by fromJson()

  public val extra_bottom_margin_when_activated: Int? by fromJson()

  public val extra_left_margin_when_activated: Int? by fromJson()

  public val extra_right_margin_when_activated: Int? by fromJson()

  public val dont_force_clipping_rect_for_contents: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ScrollPaneStyleSpecification>(ScrollPaneStyleSpecification::class)
}

/**
 * An array containing the following values.
 */
@Serializable
public enum class SelectionModeFlagsValues {
  blueprint,
  deconstruct,
  `cancel-deconstruct`,
  items,
  trees,
  `buildable-type`,
  nothing,
  `items-to-place`,
  `any-entity`,
  `any-tile`,
  `same-force`,
  `not-same-force`,
  friend,
  enemy,
  upgrade,
  `cancel-upgrade`,
  downgrade,
  `entity-with-health`,
  `entity-with-force`,
  `is-military-target`,
  `entity-with-owner`,
  `avoid-rolling-stock`,
  `entity-ghost`,
  `tile-ghost`,
}

/**
 * An array containing the following values.
 */
public typealias SelectionModeFlags = ItemOrList<SelectionModeFlagsValues>

@Serializable(SequenceTipTrigger.Serializer::class)
@SerialName("sequence")
public open class SequenceTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * List of triggers to fulfill.
   */
  public val triggers: List<TipTrigger> by fromJson()

  public object Serializer : JsonReaderSerializer<SequenceTipTrigger>(SequenceTipTrigger::class)
}

@Serializable(SetFilterTipTrigger.Serializer::class)
@SerialName("set-filter")
public open class SetFilterTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public val entity: EntityID? by fromJson()

  public val match_type_only: Boolean? by fromJson()

  public val consecutive: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<SetFilterTipTrigger>(SetFilterTipTrigger::class)
}

@Serializable(SetLogisticRequestTipTrigger.Serializer::class)
@SerialName("set-logistic-request")
public open class SetLogisticRequestTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public val logistic_chest_only: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SetLogisticRequestTipTrigger>(SetLogisticRequestTipTrigger::class)
}

@Serializable(SetRecipeTipTrigger.Serializer::class)
@SerialName("set-recipe")
public open class SetRecipeTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val recipe: RecipeID? by fromJson()

  public val machine: EntityID? by fromJson()

  public val consecutive: Boolean? by fromJson()

  public val uses_fluid: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<SetRecipeTipTrigger>(SetRecipeTipTrigger::class)
}

@Serializable(SetTileTriggerEffectItem.Serializer::class)
@SerialName("set-tile")
public open class SetTileTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val tile_name: TileID by fromJson()

  public val radius: Float by fromJson()

  public val apply_projection: Boolean? by fromJson()

  public val tile_collision_mask: CollisionMask? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SetTileTriggerEffectItem>(SetTileTriggerEffectItem::class)
}

/**
 * A struct that provides access to the user-set values of startup [mod
 * settings](https://wiki.factorio.com/Tutorial:Mod_settings).
 */
public sealed class Settings : JsonReader() {
  /**
   * All startup mod settings, indexed by the name of the setting.
   */
  public val startup: Map<String, ModSetting> by fromJson()
}

@Serializable(ShiftAnimationWaypoints.Serializer::class)
public open class ShiftAnimationWaypoints : JsonReader() {
  public val north: List<Vector> by fromJson()

  public val east: List<Vector> by fromJson()

  public val south: List<Vector> by fromJson()

  public val west: List<Vector> by fromJson()

  public object Serializer :
      JsonReaderSerializer<ShiftAnimationWaypoints>(ShiftAnimationWaypoints::class)
}

@Serializable(ShiftBuildTipTrigger.Serializer::class)
@SerialName("shift-build")
public open class ShiftBuildTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<ShiftBuildTipTrigger>(ShiftBuildTipTrigger::class)
}

@Serializable(ShowExplosionOnChartTriggerEffectItem.Serializer::class)
@SerialName("show-explosion-on-chart")
public open class ShowExplosionOnChartTriggerEffectItem : TriggerEffectItem() {
  public val type: UnknownStringLiteral by fromJson()

  public val scale: Float by fromJson()

  public object Serializer :
      JsonReaderSerializer<ShowExplosionOnChartTriggerEffectItem>(ShowExplosionOnChartTriggerEffectItem::class)
}

@Serializable
public enum class SignalColorMappingType {
  virtual,
  item,
  fluid,
}

@Serializable(SignalColorMapping.Serializer::class)
public open class SignalColorMapping : JsonReader() {
  public val type: SignalColorMappingType by fromJson()

  /**
   * Name of the signal that shows this color.
   */
  public val name: UnknownUnion by fromJson()

  public val color: Color by fromJson()

  public object Serializer : JsonReaderSerializer<SignalColorMapping>(SignalColorMapping::class)
}

@Serializable(SignalIDConnector.Serializer::class)
public open class SignalIDConnector : JsonReader() {
  public val type: SignalColorMappingType by fromJson()

  /**
   * Name of the signal that shows this color.
   */
  public val name: UnknownUnion by fromJson()

  public object Serializer : JsonReaderSerializer<SignalIDConnector>(SignalIDConnector::class)
}

public sealed class SimpleModifier : BaseModifier() {
  /**
   * Modification value, which will be added to the variable it modifies.
   */
  public val modifier: Double by fromJson()
}

/**
 * Used by tips and tricks and main menu simulations.
 *
 * There are a few simulation-only APIs:
 *
 * ```
 * game.create_test_player{name=string}
 * game.activate_rail_planner{position=position,ghost_mode=bool}
 * game.deactivate_rail_planner()
 * game.move_cursor{position=position,speed=number}  -- should be called every tick; returns true
 * when target is reached
 * game.activate_selection()
 * game.finish_selection()
 * game.deactivate_selection()
 * game.scroll_clipboard_forwards()
 * game.scroll_clipboard_backwards()
 * game.camera_player_cursor_position [RW]
 * game.camera_position [RW]
 * game.camera_zoom [W]
 * game.camera_player [W]
 * game.camera_player_cursor_direction [W]
 * game.camera_alt_info [W]
 * game.smart_belt_building [W]
 * player.drag_start_position [W]
 * player.raw_build_from_cursor{ghost_mode=bool,created_by_moving=bool,position=position}
 * surface.create_entities_from_blueprint_string{string=string,position=position,force=force,direction=defines.direction,flip_horizontal=bool,flip_vertical=bool,by_player=player}
 * ```
 */
@Serializable(SimulationDefinition.Serializer::class)
public open class SimulationDefinition : JsonReader() {
  /**
   * The save file that is used for this simulation. If not given and `generate_map` is `true`, a
   * map is generated by the game.
   */
  public val save: FileName? by fromJson()

  /**
   * This code is run as a (silent) console command inside the simulation when it is first
   * initialized. Since this is run as a console command, the restrictions of console commands apply,
   * e.g. `require` is not available, see [here](runtime:libraries).
   */
  public val init_file: FileName? by fromJson()

  /**
   * Only loaded if `init_file` is not defined.
   *
   * This code is run as a (silent) console command inside the simulation when it is first
   * initialized. Since this is run as a console command, the restrictions of console commands apply,
   * e.g. `require` is not available, see [here](runtime:libraries).
   */
  public val `init`: String? by fromJson()

  /**
   * This code is run as a (silent) console command inside the simulation every time the simulation
   * is updated. Since this is run as a console command, the restrictions of console commands apply,
   * e.g. `require` is not available, see [here](runtime:libraries).
   */
  public val update_file: FileName? by fromJson()

  /**
   * Only loaded if `update_file` is not defined.
   *
   * This code is run as a (silent) console command inside the simulation every time the simulation
   * is updated. Since this is run as a console command, the restrictions of console commands apply,
   * e.g. `require` is not available, see [here](runtime:libraries).
   */
  public val update: String? by fromJson()

  /**
   * An array of mods that will be run in this simulation if they are present and enabled.
   */
  public val mods: List<String>? by fromJson()

  /**
   * Amount of ticks that this simulation should run for before the simulation is shown to the
   * player. These updates happen after init/init_file has been run and at the highest possible rate (>
   * 60 UPS).
   */
  public val init_update_count: UInt? by fromJson()

  /**
   * How long this simulation takes. In the main menu simulations, another simulation will start
   * after this simulation ends.
   */
  public val length: UInt? by fromJson()

  /**
   * If `save` is not given and this is true, a map gets generated by the game for use in the
   * simulation.
   */
  public val generate_map: Boolean? by fromJson()

  /**
   * If this is true, the map of the simulation is set to be a lab-tile checkerboard in the area of
   * `{{-20, -15},{20, 15}}` when the scenario is first initialized (before init/init_file run).
   */
  public val checkboard: Boolean? by fromJson()

  /**
   * Multiplier for the simulation volume set by the player in the sound settings.
   */
  public val volume_modifier: Float? by fromJson()

  /**
   * If true, overrides the simulation volume set by the player in the sound settings, simply
   * setting the volume modifier to `1`.
   */
  public val override_volume: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<SimulationDefinition>(SimulationDefinition::class)
}

@Serializable(SliderStyleSpecification.Serializer::class)
@SerialName("slider_style")
public open class SliderStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public open val type: UnknownStringLiteral by fromJson()

  public val full_bar: ElementImageSet? by fromJson()

  public val full_bar_disabled: ElementImageSet? by fromJson()

  public val empty_bar: ElementImageSet? by fromJson()

  public val empty_bar_disabled: ElementImageSet? by fromJson()

  public val draw_notches: Boolean? by fromJson()

  public val notch: ElementImageSet? by fromJson()

  public val button: ButtonStyleSpecification? by fromJson()

  public val high_button: ButtonStyleSpecification? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SliderStyleSpecification>(SliderStyleSpecification::class)
}

/**
 * Definition of the smoke of an entity.
 */
@Serializable(SmokeSource.Serializer::class)
public open class SmokeSource : JsonReader() {
  public val name: TrivialSmokeID by fromJson()

  /**
   * Can't be negative, NaN or infinite.
   */
  public val frequency: Double by fromJson()

  public val offset: Double? by fromJson()

  public val position: Vector? by fromJson()

  public val north_position: Vector? by fromJson()

  public val east_position: Vector? by fromJson()

  public val south_position: Vector? by fromJson()

  public val west_position: Vector? by fromJson()

  public val deviation: MapPosition? by fromJson()

  public val starting_frame_speed: UShort? by fromJson()

  public val starting_frame_speed_deviation: Double? by fromJson()

  public val starting_frame: UShort? by fromJson()

  public val starting_frame_deviation: Double? by fromJson()

  public val slow_down_factor: UByte? by fromJson()

  public val height: Float? by fromJson()

  public val height_deviation: Float? by fromJson()

  public val starting_vertical_speed: Float? by fromJson()

  public val starting_vertical_speed_deviation: Float? by fromJson()

  /**
   * A value between `0` and `1`.
   */
  public val vertical_speed_slowdown: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<SmokeSource>(SmokeSource::class)
}

@Serializable(SoundValues.Serializer::class)
public open class SoundValues : JsonReader() {
  public val aggregation: AggregationSpecification? by fromJson()

  public val allow_random_repeat: Boolean? by fromJson()

  /**
   * Modifies how far a sound can be heard. Must be between `0` and `1` inclusive.
   */
  public val audible_distance_modifier: Double? by fromJson()

  public val game_controller_vibration_data: GameControllerVibrationData? by fromJson()

  public val variations: List<SoundDefinition>? by fromJson()

  /**
   * Supported sound file formats are `.ogg` (Vorbis) and `.wav`.
   *
   * Only loaded, and mandatory if `variations` is not defined.
   */
  public val filename: FileName? by fromJson()

  /**
   * Only loaded if `variations` is not defined.
   */
  public val volume: Float? by fromJson()

  /**
   * Only loaded if `variations` is not defined.
   */
  public val preload: Boolean? by fromJson()

  /**
   * Speed must be `>= 1 / 64`. This sets both min and max speeds.
   *
   * Only loaded if `variations` is not defined.
   */
  public val speed: Float? by fromJson()

  /**
   * Must be `>= 1 / 64`.
   *
   * Only loaded if both `variations` and `speed` are not defined.
   */
  public val min_speed: Float? by fromJson()

  /**
   * Must be `>= min_speed`.
   *
   * Only loaded if `variations` is not defined. Only loaded, and mandatory if `min_speed` is
   * defined.
   */
  public val max_speed: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<SoundValues>(SoundValues::class)
}

public typealias Sound = UnknownUnion

@Serializable(SoundDefinition.Serializer::class)
public open class SoundDefinition : JsonReader() {
  /**
   * Supported sound file formats are `.ogg` (Vorbis) and `.wav`.
   */
  public val filename: FileName by fromJson()

  public val volume: Float? by fromJson()

  public val preload: Boolean? by fromJson()

  /**
   * Speed must be `>= 1 / 64`. This sets both min and max speeds.
   */
  public val speed: Float? by fromJson()

  /**
   * Only loaded if `speed` is not defined.
   *
   * Must be `>= 1 / 64`.
   */
  public val min_speed: Float? by fromJson()

  /**
   * Only loaded, and mandatory, if `min_speed` is defined.
   *
   * Must be `>= min_speed`.
   */
  public val max_speed: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<SoundDefinition>(SoundDefinition::class)
}

/**
 * This defines which slider in the sound settings affects the volume of this sound. Furthermore,
 * some sound types are mixed differently than others, e.g. zoom level effects are applied.
 */
@Serializable
public enum class SoundType {
  `game-effect`,
  `gui-effect`,
  ambient,
  environment,
  walking,
  alert,
  wind,
}

/**
 * The definition of a evolution and probability weights for a [spawnable
 * unit](prototype:UnitSpawnDefinition) for a [EnemySpawnerPrototype](prototype:EnemySpawnerPrototype).
 *
 * It can be specified as a table with named or numbered keys, but not a mix of both. If this is
 * specified as a table with numbered keys then the first value is the evolution factor and the second
 * is the spawn weight.
 */
@Serializable(SpawnPointValues.Serializer::class)
public open class SpawnPointValues : JsonReader() {
  public val evolution_factor: Double by fromJson()

  /**
   * Must be `>= 0`.
   */
  public val spawn_weight: Double by fromJson()

  public object Serializer : JsonReaderSerializer<SpawnPointValues>(SpawnPointValues::class)
}

/**
 * The definition of a evolution and probability weights for a [spawnable
 * unit](prototype:UnitSpawnDefinition) for a [EnemySpawnerPrototype](prototype:EnemySpawnerPrototype).
 *
 * It can be specified as a table with named or numbered keys, but not a mix of both. If this is
 * specified as a table with numbered keys then the first value is the evolution factor and the second
 * is the spawn weight.
 */
public typealias SpawnPoint = UnknownUnion

@Serializable(SpeechBubbleStyleSpecification.Serializer::class)
@SerialName("speech_bubble_style")
public open class SpeechBubbleStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val frame_style: FrameStyleSpecification? by fromJson()

  public val label_style: LabelStyleSpecification? by fromJson()

  public val arrow_graphical_set: ElementImageSet? by fromJson()

  public val close_color: Color? by fromJson()

  public val arrow_indent: Double? by fromJson()

  public val pass_through_mouse: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SpeechBubbleStyleSpecification>(SpeechBubbleStyleSpecification::class)
}

/**
 * Used by [SpiderVehiclePrototype](prototype:SpiderVehiclePrototype).
 */
@Serializable(SpiderEnginePrototype.Serializer::class)
public open class SpiderEnginePrototype : JsonReader() {
  public val legs: ItemOrList<SpiderLegSpecification> by fromJson()

  /**
   * The string content is irrelevant, if it is present at all then the
   * [SpiderVehiclePrototype](prototype:SpiderVehiclePrototype) is considered to have
   * [EntityWithOwnerPrototype::is_military_target](prototype:EntityWithOwnerPrototype::is_military_target)
   * set to true. This gets enemies interested in attacking the spider vehicle even when nobody is in
   * it.
   */
  public val military_target: String? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SpiderEnginePrototype>(SpiderEnginePrototype::class)
}

@Serializable(SpiderLegGraphicsSet.Serializer::class)
public open class SpiderLegGraphicsSet : JsonReader() {
  public val joint_turn_offset: Float? by fromJson()

  public val joint: Sprite? by fromJson()

  public val joint_shadow: Sprite? by fromJson()

  public val upper_part: SpiderLegPart? by fromJson()

  public val lower_part: SpiderLegPart? by fromJson()

  public val upper_part_shadow: SpiderLegPart? by fromJson()

  public val lower_part_shadow: SpiderLegPart? by fromJson()

  public val upper_part_water_reflection: SpiderLegPart? by fromJson()

  public val lower_part_water_reflection: SpiderLegPart? by fromJson()

  public object Serializer : JsonReaderSerializer<SpiderLegGraphicsSet>(SpiderLegGraphicsSet::class)
}

@Serializable(SpiderLegPart.Serializer::class)
public open class SpiderLegPart : JsonReader() {
  public val top_end: Sprite? by fromJson()

  public val middle: Sprite? by fromJson()

  public val bottom_end: Sprite? by fromJson()

  public val middle_offset_from_top: Float? by fromJson()

  public val middle_offset_from_bottom: Float? by fromJson()

  public val top_end_length: Float? by fromJson()

  public val bottom_end_length: Float? by fromJson()

  public object Serializer : JsonReaderSerializer<SpiderLegPart>(SpiderLegPart::class)
}

/**
 * Used by [SpiderEnginePrototype](prototype:SpiderEnginePrototype) for
 * [SpiderVehiclePrototype](prototype:SpiderVehiclePrototype).
 */
@Serializable(SpiderLegSpecification.Serializer::class)
public open class SpiderLegSpecification : JsonReader() {
  /**
   * Name of a [SpiderLegPrototype](prototype:SpiderLegPrototype).
   */
  public val leg: EntityID by fromJson()

  public val mount_position: Vector by fromJson()

  public val ground_position: Vector by fromJson()

  /**
   * The 1-based indices of the legs that should block this leg's movement.
   */
  public val blocking_legs: List<UInt> by fromJson()

  /**
   * For triggers, the source and target is the leg entity. Certain effects may not raise as
   * desired, e.g. `"push-back"` does nothing, and `"script"` has `leg` as the source and target of the
   * event.
   */
  public val leg_hit_the_ground_trigger: TriggerEffect? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SpiderLegSpecification>(SpiderLegSpecification::class)
}

/**
 * Used to specify the graphics for [SpiderVehiclePrototype](prototype:SpiderVehiclePrototype).
 */
@Serializable(SpiderVehicleGraphicsSet.Serializer::class)
public open class SpiderVehicleGraphicsSet : JsonReader() {
  public val base_animation: RotatedAnimation? by fromJson()

  public val shadow_base_animation: RotatedAnimation? by fromJson()

  public val animation: RotatedAnimation? by fromJson()

  public val shadow_animation: RotatedAnimation? by fromJson()

  public val base_render_layer: RenderLayer? by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public val autopilot_destination_visualisation_render_layer: RenderLayer? by fromJson()

  public val light: LightDefinition? by fromJson()

  /**
   * Placed in multiple positions, as determined by `light_positions`.
   */
  public val eye_light: LightDefinition? by fromJson()

  public val autopilot_destination_on_map_visualisation: Animation? by fromJson()

  public val autopilot_destination_queue_on_map_visualisation: Animation? by fromJson()

  public val autopilot_destination_visualisation: Animation? by fromJson()

  public val autopilot_destination_queue_visualisation: Animation? by fromJson()

  public val autopilot_path_visualisation_line_width: Float? by fromJson()

  public val autopilot_path_visualisation_on_map_line_width: Float? by fromJson()

  /**
   * Defines where each `eye_light` is placed. One array per eye and each of those arrays should
   * contain one position per body direction.
   */
  public val light_positions: List<List<Vector>>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SpiderVehicleGraphicsSet>(SpiderVehicleGraphicsSet::class)
}

@Serializable(SpotNoiseArguments.Serializer::class)
public open class SpotNoiseArguments : JsonReader() {
  public val x: NoiseNumber by fromJson()

  public val y: NoiseNumber by fromJson()

  /**
   * Integer. First random seed, usually the map seed is used.
   */
  public val seed0: ConstantNoiseNumber by fromJson()

  /**
   * Integer. Second random seed, usually chosen to identify the noise layer.
   */
  public val seed1: ConstantNoiseNumber by fromJson()

  /**
   * Integer. The width and height of each region.
   */
  public val region_size: ConstantNoiseNumber? by fromJson()

  /**
   * Integer. Offset of the first candidate point to use.
   */
  public val skip_offset: ConstantNoiseNumber? by fromJson()

  /**
   * Integer. Number of candidate points to skip over after each one used as a spot, including the
   * used one.
   */
  public val skip_span: ConstantNoiseNumber? by fromJson()

  /**
   * Integer. How many candidate points to generate.
   */
  public val candidate_point_count: ConstantNoiseNumber? by fromJson()

  /**
   * Integer. An alternative to `candidate_point_count`: number of spots to generate:
   * `candidate_spot_count = X` is equivalent to `candidate_point_count / skip_span = X`
   */
  public val candidate_spot_count: ConstantNoiseNumber? by fromJson()

  /**
   * The minimum spacing to *try* to achieve while randomly picking points. Spot noise may end up
   * placing spots closer than this in crowded regions.
   */
  public val suggested_minimum_candidate_point_spacing: ConstantNoiseNumber? by fromJson()

  /**
   * Whether to place a hard limit on the total quantity in each region by reducing the size of any
   * spot (which will be the last spot chosen) that would put it over the limit.
   */
  public val hard_region_target_quantity: ConstantNoiseBoolean? by fromJson()

  /**
   * A numeric expression that will be evaluated for each candidate spot to calculate density at
   * that point.
   */
  public val density_expression: NoiseLiteralExpression by fromJson()

  /**
   * A numeric expression that will be evaluated for each candidate spot to calculate the spot's
   * quantity.
   */
  public val spot_quantity_expression: NoiseLiteralExpression by fromJson()

  /**
   * A numeric expression that will be evaluated for each candidate spot to calculate the spot's
   * radius. This, together with quantity, will determine the spots peak value.
   */
  public val spot_radius_expression: NoiseLiteralExpression by fromJson()

  /**
   * A numeric expression that will be evaluated for each candidate spot to calculate the spot's
   * favorability. Spots with higher favorability will be considered first when building the final list
   * of spots for a region.
   */
  public val spot_favorability_expression: NoiseLiteralExpression by fromJson()

  public val basement_value: ConstantNoiseNumber by fromJson()

  public val maximum_spot_basement_radius: ConstantNoiseNumber by fromJson()

  public val comment: NoiseLiteralString? by fromJson()

  public object Serializer : JsonReaderSerializer<SpotNoiseArguments>(SpotNoiseArguments::class)
}

/**
 * Specifies one picture that can be used in the game.
 *
 * When there is more than one sprite or [Animation](prototype:Animation) frame with the same source
 * file and dimensions/position in the game, they all share the same memory.
 */
@Serializable(Sprite.Serializer::class)
public open class Sprite : SpriteParameters() {
  /**
   * If this property is present, all Sprite definitions have to be placed as entries in the array,
   * and they will all be loaded from there. `layers` may not be an empty table. Each definition in the
   * array may also have the `layers` property.
   *
   * If this property is present, all other properties, including those inherited from
   * SpriteParameters, are ignored.
   */
  public val layers: List<Sprite>? by fromJson()

  /**
   * Only loaded, and mandatory if `layers` is not defined.
   *
   * The path to the sprite file to use.
   */
  override val filename: FileName? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * If this property exists and high resolution sprites are turned on, this is used to load the
   * Sprite.
   */
  public val hr_version: Sprite? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * Number of slices this is sliced into when using the "optimized atlas packing" option. If you
   * are a modder, you can just ignore this property. As an example, if this is `4`, the sprite will be
   * sliced into a `4x4` grid.
   */
  public val slice: SpriteSizeType? by fromJson("dice")

  /**
   * Only loaded if `layers` is not defined.
   *
   * Same as `slice`, but this specifies only how many slices there are on the x-axis.
   */
  public val slice_x: SpriteSizeType? by fromJson("dice_x")

  /**
   * Only loaded if `layers` is not defined.
   *
   * Same as `slice`, but this specifies only how many slices there are on the y-axis.
   */
  public val slice_y: SpriteSizeType? by fromJson("dice_y")

  public object Serializer : JsonReaderSerializer<Sprite>(Sprite::class)
}

/**
 * A map of sprites for all 4 directions of the entity. If this is loaded as a single Sprite, it
 * applies to all directions.
 */
@Serializable(Sprite4WayValues.Serializer::class)
public open class Sprite4WayValues : JsonReader() {
  public val sheets: List<SpriteNWaySheet>? by fromJson()

  /**
   * Only loaded if `sheets` is not defined.
   */
  public val sheet: SpriteNWaySheet? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val north: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val east: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val south: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val west: Sprite? by fromJson()

  public object Serializer : JsonReaderSerializer<Sprite4WayValues>(Sprite4WayValues::class)
}

/**
 * A map of sprites for all 4 directions of the entity. If this is loaded as a single Sprite, it
 * applies to all directions.
 */
public typealias Sprite4Way = UnknownUnion

/**
 * A map of sprites for all 8 directions of the entity.
 */
@Serializable(Sprite8Way.Serializer::class)
public open class Sprite8Way : JsonReader() {
  public val sheets: List<SpriteNWaySheet>? by fromJson()

  /**
   * Only loaded if `sheets` is not defined.
   */
  public val sheet: SpriteNWaySheet? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val north: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val north_east: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val east: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val south_east: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val south: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val south_west: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val west: Sprite? by fromJson()

  /**
   * Only loaded, and mandatory if both `sheets` and `sheet` are not defined.
   */
  public val north_west: Sprite? by fromJson()

  public object Serializer : JsonReaderSerializer<Sprite8Way>(Sprite8Way::class)
}

/**
 * An array containing the following values.
 */
@Serializable
public enum class SpriteFlagsValues {
  `no-crop`,
  `not-compressed`,
  `always-compressed`,
  mipmap,
  `linear-minification`,
  `linear-magnification`,
  `linear-mip-level`,
  `alpha-mask`,
  `no-scale`,
  mask,
  icon,
  gui,
  `gui-icon`,
  light,
  terrain,
  `terrain-effect-map`,
  shadow,
  smoke,
  decal,
  `low-object`,
  `trilinear-filtering`,
  `group=none`,
  `group=terrain`,
  `group=shadow`,
  `group=smoke`,
  `group=decal`,
  `group=low-object`,
  `group=gui`,
  `group=icon`,
  `group=icon-background`,
  compressed,
}

/**
 * An array containing the following values.
 */
public typealias SpriteFlags = List<SpriteFlagsValues>

@Serializable(SpriteNWaySheet.Serializer::class)
public open class SpriteNWaySheet : SpriteParameters() {
  /**
   * Specifies how many of the directions of the SpriteNWay are filled up with this sheet.
   */
  public val frames: UInt? by fromJson()

  /**
   * If this property exists and high resolution sprites are turned on, this is used to load the
   * Sheet.
   */
  public val hr_version: SpriteNWaySheet? by fromJson()

  /**
   * Unused.
   */
  override val generate_sdf: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<SpriteNWaySheet>(SpriteNWaySheet::class)
}

public sealed class SpriteParameters : JsonReader() {
  /**
   * The path to the sprite file to use.
   */
  public open val filename: FileName? by fromJson()

  public val priority: SpritePriority? by fromJson()

  public val flags: SpriteFlags? by fromJson()

  /**
   * The width and height of the sprite. If this is a tuple, the first member of the tuple is the
   * width and the second is the height. Otherwise the size is both width and height. Width and height
   * may only be in the range of 0-8192.
   */
  public open val size: ItemOrTuple2<SpriteSizeType>? by fromJson()

  /**
   * Mandatory if `size` is not defined.
   *
   * Width of the picture in pixels, from 0-8192.
   */
  public open val width: SpriteSizeType? by fromJson()

  /**
   * Mandatory if `size` is not defined.
   *
   * Height of the picture in pixels, from 0-8192.
   */
  public open val height: SpriteSizeType? by fromJson()

  /**
   * Horizontal position of the sprite in the source file in pixels.
   */
  public val x: SpriteSizeType? by fromJson()

  /**
   * Vertical position of the sprite in the source file in pixels.
   */
  public val y: SpriteSizeType? by fromJson()

  /**
   * Loaded only when `x` and `y` are both `0`. The first member of the tuple is `x` and the second
   * is `y`.
   */
  public val position: Tuple2<SpriteSizeType>? by fromJson()

  /**
   * The shift in tiles. `util.by_pixel()` can be used to divide the shift by 32 which is the usual
   * pixel height/width of 1 tile in normal resolution. Note that 32 pixel tile height/width is not
   * enforced anywhere - any other tile height or width is also possible.
   */
  public val shift: Vector? by fromJson()

  /**
   * Values other than `1` specify the scale of the sprite on default zoom. A scale of `2` means
   * that the picture will be two times bigger on screen (and thus more pixelated).
   */
  public val scale: Double? by fromJson()

  /**
   * Only one of `draw_as_shadow`, `draw_as_glow` and `draw_as_light` can be true. This takes
   * precedence over `draw_as_glow` and `draw_as_light`.
   */
  public val draw_as_shadow: Boolean? by fromJson()

  /**
   * Only one of `draw_as_shadow`, `draw_as_glow` and `draw_as_light` can be true. This takes
   * precedence over `draw_as_light`.
   *
   * Draws first as a normal sprite, then again as a light layer. See
   * [https://forums.factorio.com/91682](https://forums.factorio.com/91682).
   */
  public val draw_as_glow: Boolean? by fromJson()

  /**
   * Only one of `draw_as_shadow`, `draw_as_glow` and `draw_as_light` can be true.
   */
  public val draw_as_light: Boolean? by fromJson()

  /**
   * Only loaded if this is an icon, that is it has the flag `"group=icon"` or `"group=gui"`.
   */
  public open val mipmap_count: UByte? by fromJson()

  public val apply_runtime_tint: Boolean? by fromJson()

  public val tint: Color? by fromJson()

  public val blend_mode: BlendMode? by fromJson()

  /**
   * Minimal mode is entered when mod loading fails. You are in it when you see the gray box after
   * (part of) the loading screen that tells you a mod error
   * ([Example](https://cdn.discordapp.com/attachments/340530709712076801/532315796626472972/unknown.png)).
   * Modders can ignore this property.
   */
  public val load_in_minimal_mode: Boolean? by fromJson()

  /**
   * Whether alpha should be pre-multiplied.
   */
  public val premul_alpha: Boolean? by fromJson()

  /**
   * This property is only used by sprites used in [UtilitySprites](prototype:UtilitySprites) that
   * have the `"icon"` flag set.
   *
   * If this is set to `true`, the game will generate an icon shadow (using signed distance fields)
   * for the sprite.
   */
  public open val generate_sdf: Boolean? by fromJson()
}

/**
 * This sets the "caching priority" of a sprite, so deciding priority of it being included in VRAM
 * instead of streaming it and is therefore a purely technical value. See
 * [here](https://forums.factorio.com/viewtopic.php?p=437380#p437380) and
 * [here](https://www.factorio.com/blog/post/fff-264). The possible values are listed below.
 */
@Serializable
public enum class SpritePriority {
  `extra-high-no-scale`,
  `extra-high`,
  high,
  medium,
  low,
  `very-low`,
  `no-atlas`,
}

@Serializable(SpriteSheet.Serializer::class)
public open class SpriteSheet : SpriteParameters() {
  /**
   * If this property is present, all SpriteSheet definitions have to be placed as entries in the
   * array, and they will all be loaded from there. `layers` may not be an empty table. Each definition
   * in the array may also have the `layers` property.
   *
   * If this property is present, all other properties, including those inherited from
   * SpriteParameters, are ignored.
   */
  public val layers: List<SpriteSheet>? by fromJson()

  /**
   * Only loaded if `layers` is not defined.
   *
   * If this property exists and high resolution sprites are turned on, this is used to load the
   * SpriteSheet.
   */
  public val hr_version: SpriteSheet? by fromJson()

  public val variation_count: UInt? by fromJson()

  public val repeat_count: UInt? by fromJson()

  public val line_length: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<SpriteSheet>(SpriteSheet::class)
}

public typealias SpriteSizeType = Short

@Serializable(SpriteVariationsValues.Serializer::class)
public open class SpriteVariationsValues : JsonReader() {
  public val sheet: SpriteSheet by fromJson()

  public object Serializer :
      JsonReaderSerializer<SpriteVariationsValues>(SpriteVariationsValues::class)
}

public typealias SpriteVariations = UnknownUnion

@Serializable(StackInserterCapacityBonusModifier.Serializer::class)
@SerialName("stack-inserter-capacity-bonus")
public open class StackInserterCapacityBonusModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<StackInserterCapacityBonusModifier>(StackInserterCapacityBonusModifier::class)
}

@Serializable
public enum class StackTransferTipTriggerTransfer {
  stack,
  inventory,
  `whole-inventory`,
}

@Serializable(StackTransferTipTrigger.Serializer::class)
@SerialName("stack-transfer")
public open class StackTransferTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public val transfer: StackTransferTipTriggerTransfer? by fromJson()

  public object Serializer :
      JsonReaderSerializer<StackTransferTipTrigger>(StackTransferTipTrigger::class)
}

@Serializable(StateSteeringSettings.Serializer::class)
public open class StateSteeringSettings : JsonReader() {
  /**
   * Not including the radius of the unit.
   */
  public val radius: Double by fromJson()

  public val separation_factor: Double by fromJson()

  public val separation_force: Double by fromJson()

  /**
   * Used only for special "to look good" purposes (like in trailer).
   */
  public val force_unit_fuzzy_goto_behavior: Boolean by fromJson()

  public object Serializer :
      JsonReaderSerializer<StateSteeringSettings>(StateSteeringSettings::class)
}

@Serializable(StatusColors.Serializer::class)
public open class StatusColors : JsonReader() {
  public val idle: Color? by fromJson()

  public val no_minable_resources: Color? by fromJson()

  public val full_output: Color? by fromJson()

  public val insufficient_input: Color? by fromJson()

  public val disabled: Color? by fromJson()

  public val no_power: Color? by fromJson()

  public val working: Color? by fromJson()

  public val low_power: Color? by fromJson()

  public object Serializer : JsonReaderSerializer<StatusColors>(StatusColors::class)
}

@Serializable(SteeringSettings.Serializer::class)
public open class SteeringSettings : JsonReader() {
  public val default: StateSteeringSettings by fromJson()

  public val moving: StateSteeringSettings by fromJson()

  public object Serializer : JsonReaderSerializer<SteeringSettings>(SteeringSettings::class)
}

@Serializable(StorageTankPictures.Serializer::class)
public open class StorageTankPictures : JsonReader() {
  public val picture: Sprite4Way by fromJson()

  public val window_background: Sprite by fromJson()

  public val fluid_background: Sprite by fromJson()

  public val flow_sprite: Sprite by fromJson()

  public val gas_flow: Animation by fromJson()

  public object Serializer : JsonReaderSerializer<StorageTankPictures>(StorageTankPictures::class)
}

@Serializable(StreamAttackParameters.Serializer::class)
@SerialName("stream")
public open class StreamAttackParameters : BaseAttackParameters(), AttackParameters {
  public val type: UnknownStringLiteral by fromJson()

  public val fluid_consumption: Float? by fromJson()

  public val gun_barrel_length: Float? by fromJson()

  public val projectile_creation_parameters: CircularProjectileCreationSpecification? by fromJson()

  public val gun_center_shift: UnknownUnion? by fromJson()

  /**
   * Controls which fluids can fuel this stream attack and their potential damage bonuses.
   */
  public val fluids: List<StreamFluidProperties>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<StreamAttackParameters>(StreamAttackParameters::class)
}

@Serializable(StreamFluidProperties.Serializer::class)
public open class StreamFluidProperties : JsonReader() {
  public val type: FluidID by fromJson()

  public val damage_modifier: Double? by fromJson()

  public object Serializer :
      JsonReaderSerializer<StreamFluidProperties>(StreamFluidProperties::class)
}

@Serializable(StreamTriggerDelivery.Serializer::class)
@SerialName("stream")
public open class StreamTriggerDelivery : TriggerDeliveryItem(), TriggerDelivery {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [FluidStreamPrototype](prototype:FluidStreamPrototype).
   */
  public val stream: EntityID by fromJson()

  public val source_offset: Vector? by fromJson()

  public object Serializer :
      JsonReaderSerializer<StreamTriggerDelivery>(StreamTriggerDelivery::class)
}

/**
 * Sets whether a GUI element can be stretched or squashed.
 */
@Serializable
public enum class StretchRule {
  on,
  off,
  auto,
  stretch_and_expand,
}

/**
 * Used as an alternative way to specify animations.
 */
@Serializable(Stripe.Serializer::class)
public open class Stripe : JsonReader() {
  public val width_in_frames: UInt by fromJson()

  /**
   * Mandatory when Stripe is used in [Animation](prototype:Animation).
   *
   * Optional when it is used in [RotatedAnimation](prototype:RotatedAnimation), where it defaults
   * to [RotatedAnimation::direction_count](prototype:RotatedAnimation::direction_count).
   */
  public val height_in_frames: UInt by fromJson()

  public val filename: FileName by fromJson()

  public val x: UInt? by fromJson()

  public val y: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<Stripe>(Stripe::class)
}

/**
 * Loaded as one of the [BaseStyleSpecification](prototype:BaseStyleSpecification) extensions, based
 * on the value of the `type` key.
 */
@Serializable
public sealed interface StyleSpecification

public sealed class StyleWithClickableGraphicalSetSpecification : BaseStyleSpecification() {
  public val default_graphical_set: ElementImageSet? by fromJson()

  public val hovered_graphical_set: ElementImageSet? by fromJson()

  public val clicked_graphical_set: ElementImageSet? by fromJson()

  public val disabled_graphical_set: ElementImageSet? by fromJson()

  public val selected_graphical_set: ElementImageSet? by fromJson()

  public val selected_hovered_graphical_set: ElementImageSet? by fromJson()

  public val selected_clicked_graphical_set: ElementImageSet? by fromJson()

  public val left_click_sound: Sound? by fromJson()
}

@Serializable(SwitchStyleSpecification.Serializer::class)
@SerialName("switch_style")
public open class SwitchStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val left_button_position: UInt? by fromJson()

  public val middle_button_position: UInt? by fromJson()

  public val right_button_position: UInt? by fromJson()

  public val default_background: Sprite? by fromJson()

  public val hover_background: Sprite? by fromJson()

  public val disabled_background: Sprite? by fromJson()

  public val button: ButtonStyleSpecification? by fromJson()

  public val active_label: LabelStyleSpecification? by fromJson()

  public val inactive_label: LabelStyleSpecification? by fromJson()

  public object Serializer :
      JsonReaderSerializer<SwitchStyleSpecification>(SwitchStyleSpecification::class)
}

@Serializable(TabStyleSpecification.Serializer::class)
@SerialName("tab_style")
public open class TabStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val font: String? by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val badge_font: String? by fromJson()

  public val badge_horizontal_spacing: Short? by fromJson()

  public val default_font_color: Color? by fromJson()

  public val selected_font_color: Color? by fromJson()

  public val disabled_font_color: Color? by fromJson()

  public val default_badge_font_color: Color? by fromJson()

  public val selected_badge_font_color: Color? by fromJson()

  public val disabled_badge_font_color: Color? by fromJson()

  public val default_graphical_set: ElementImageSet? by fromJson()

  public val selected_graphical_set: ElementImageSet? by fromJson()

  public val hover_graphical_set: ElementImageSet? by fromJson()

  public val game_controller_selected_hover_graphical_set: ElementImageSet? by fromJson()

  public val press_graphical_set: ElementImageSet? by fromJson()

  public val disabled_graphical_set: ElementImageSet? by fromJson()

  public val override_graphics_on_edges: Boolean? by fromJson()

  public val left_edge_selected_graphical_set: ElementImageSet? by fromJson()

  public val right_edge_selected_graphical_set: ElementImageSet? by fromJson()

  public val default_badge_graphical_set: ElementImageSet? by fromJson()

  public val selected_badge_graphical_set: ElementImageSet? by fromJson()

  public val hover_badge_graphical_set: ElementImageSet? by fromJson()

  public val press_badge_graphical_set: ElementImageSet? by fromJson()

  public val disabled_badge_graphical_set: ElementImageSet? by fromJson()

  public val draw_grayscale_picture: Boolean? by fromJson()

  public val left_click_sound: Sound? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TabStyleSpecification>(TabStyleSpecification::class)
}

@Serializable(TabbedPaneStyleSpecification.Serializer::class)
@SerialName("tabbed_pane_style")
public open class TabbedPaneStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val vertical_spacing: UInt? by fromJson()

  public val tab_content_frame: FrameStyleSpecification? by fromJson()

  public val tab_container: HorizontalFlowStyleSpecification? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TabbedPaneStyleSpecification>(TabbedPaneStyleSpecification::class)
}

@Serializable(TableStyleSpecification.Serializer::class)
@SerialName("table_style")
public open class TableStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val horizontal_spacing: Int? by fromJson()

  public val vertical_spacing: Int? by fromJson()

  /**
   * Sets `top_cell_padding`, `right_cell_padding`, `bottom_cell_padding` and `left_cell_padding` to
   * the same value.
   */
  public val cell_padding: Short? by fromJson()

  public val top_cell_padding: Short? by fromJson()

  public val right_cell_padding: Short? by fromJson()

  public val bottom_cell_padding: Short? by fromJson()

  public val left_cell_padding: Short? by fromJson()

  public val apply_row_graphical_set_per_column: Boolean? by fromJson()

  public val column_graphical_set: ElementImageSet? by fromJson()

  public val default_row_graphical_set: ElementImageSet? by fromJson()

  public val even_row_graphical_set: ElementImageSet? by fromJson()

  public val odd_row_graphical_set: ElementImageSet? by fromJson()

  public val hovered_graphical_set: ElementImageSet? by fromJson()

  public val clicked_graphical_set: ElementImageSet? by fromJson()

  public val selected_graphical_set: ElementImageSet? by fromJson()

  public val selected_hovered_graphical_set: ElementImageSet? by fromJson()

  public val selected_clicked_graphical_set: ElementImageSet? by fromJson()

  public val background_graphical_set: ElementImageSet? by fromJson()

  public val column_alignments: List<ColumnAlignment>? by fromJson()

  public val column_widths: List<ColumnWidth>? by fromJson()

  public val hovered_row_color: Color? by fromJson()

  public val selected_row_color: Color? by fromJson()

  public val vertical_line_color: Color? by fromJson()

  public val horizontal_line_color: Color? by fromJson()

  public val column_ordering_ascending_button_style: ButtonStyleSpecification? by fromJson()

  public val column_ordering_descending_button_style: ButtonStyleSpecification? by fromJson()

  public val inactive_column_ordering_ascending_button_style: ButtonStyleSpecification? by
      fromJson()

  public val inactive_column_ordering_descending_button_style: ButtonStyleSpecification? by
      fromJson()

  public val border: BorderImageSet? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TableStyleSpecification>(TableStyleSpecification::class)
}

/**
 * Used when defining a [TechnologyPrototype](prototype:TechnologyPrototype) that uses difficulty.
 * For a technology without difficulty, these same properties are defined on the prototype itself.
 */
@Serializable(TechnologyData.Serializer::class)
public open class TechnologyData : JsonReader() {
  /**
   * When set to true, and the technology contains several levels, only the relevant one is
   * displayed in the technology screen.
   */
  public val upgrade: Boolean? by fromJson()

  public val enabled: Boolean? by fromJson()

  /**
   * Hides the technology from the tech screen.
   */
  public val hidden: Boolean? by fromJson()

  /**
   * Controls whether the technology is shown in the tech GUI when it is not `enabled`.
   */
  public val visible_when_disabled: Boolean? by fromJson()

  /**
   * Controls whether the technology cost ignores the tech cost multiplier set in the
   * [DifficultySettings](runtime:DifficultySettings). E.g. `4` for the default expensive difficulty.
   */
  public val ignore_tech_cost_multiplier: Boolean? by fromJson()

  /**
   * Determines the cost in items and time of the technology.
   */
  public val unit: TechnologyUnit by fromJson()

  /**
   * `"infinite"` for infinite technologies, otherwise `uint32`.
   *
   * Defaults to the same level as the technology, which is `0` for non-upgrades, and the level of
   * the upgrade for upgrades.
   */
  public val max_level: UnknownUnion? by fromJson()

  /**
   * List of technologies needed to be researched before this one can be researched.
   */
  public val prerequisites: List<TechnologyID>? by fromJson()

  /**
   * List of effects of the technology (applied when the technology is researched).
   */
  public val effects: List<Modifier>? by fromJson()

  public object Serializer : JsonReaderSerializer<TechnologyData>(TechnologyData::class)
}

/**
 * The name of a [TechnologyPrototype](prototype:TechnologyPrototype).
 */
public typealias TechnologyID = String

@Serializable(TechnologySlotStyleSpecification.Serializer::class)
@SerialName("technology_slot_style")
public open class TechnologySlotStyleSpecification : ButtonStyleSpecification(), StyleSpecification
    {
  override val type: UnknownStringLiteral by fromJson()

  public val highlighted_graphical_set: ElementImageSet? by fromJson()

  public val default_background_shadow: ElementImageSet? by fromJson()

  public val level_band: ElementImageSet? by fromJson()

  public val hovered_level_band: ElementImageSet? by fromJson()

  public val level_offset_x: Int? by fromJson()

  public val level_offset_y: Int? by fromJson()

  public val level_band_width: UInt? by fromJson()

  public val level_band_height: UInt? by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val level_font: String? by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val level_range_font: String? by fromJson()

  public val level_font_color: Color? by fromJson()

  public val hovered_level_font_color: Color? by fromJson()

  public val level_range_font_color: Color? by fromJson()

  public val hovered_level_range_font_color: Color? by fromJson()

  public val level_range_band: ElementImageSet? by fromJson()

  public val hovered_level_range_band: ElementImageSet? by fromJson()

  public val level_range_offset_x: Int? by fromJson()

  public val level_range_offset_y: Int? by fromJson()

  public val ingredients_height: UInt? by fromJson()

  public val default_ingredients_background: ElementImageSet? by fromJson()

  public val hovered_ingredients_background: ElementImageSet? by fromJson()

  public val clicked_ingredients_background: ElementImageSet? by fromJson()

  public val disabled_ingredients_background: ElementImageSet? by fromJson()

  public val highlighted_ingredients_background: ElementImageSet? by fromJson()

  public val ingredients_padding: UInt? by fromJson()

  public val ingredient_icon_size: UInt? by fromJson()

  public val ingredient_icon_overlap: UInt? by fromJson()

  public val clicked_overlay: ElementImageSet? by fromJson()

  public val progress_bar_background: ElementImageSet? by fromJson()

  public val progress_bar: ElementImageSet? by fromJson()

  public val progress_bar_shadow: ElementImageSet? by fromJson()

  public val progress_bar_height: UInt? by fromJson()

  public val progress_bar_color: Color? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TechnologySlotStyleSpecification>(TechnologySlotStyleSpecification::class)
}

/**
 * Either `count` or `count_formula` must be defined, never both.
 */
@Serializable(TechnologyUnit.Serializer::class)
public open class TechnologyUnit : JsonReader() {
  /**
   * How many units are needed. Must be `> 0`.
   */
  public val count: ULong? by fromJson()

  /**
   * Formula that specifies how many units are needed per level of the technology.
   *
   * If the last characters of the prototype name are `-<number>`, the level is taken to be the
   * number, e.g. `physical-projectile-damage-2` implies a number of `2`. This defaults to `1`. There
   * does not need to be lower-level technologies for a technology to be detected as having a level,
   * meaning a technology or sequence of upgrade technologies can begin at any number.
   *
   * For an infinite technology, the level begins at the given suffix (or `1` by default) and gains
   * 1 level upon being researched, or if the `max_level` is reached, marked as completed. The initial
   * level of a technology can not be greater than its `max_level`.
   *
   * The formula is executed following the BODMAS order (also known as PEMDAS). It supports these
   * operators and characters:
   *
   * - `+`: Addition
   *
   * - `-`: Subtraction
   *
   * - `*`: Multiplication
   *
   * - `^`: Exponentiation
   *
   * - `()`: Brackets for order of operations; supports nested brackets
   *
   * - `l` or `L`: The current level of the technology
   *
   * - Digits: Treated as numbers
   *
   * - `.`: Decimal point in numbers
   *
   * - `SPACE`: Spaces are ignored
   *
   * Note that this formula can also be used at
   * [runtime](runtime:LuaGameScript::evaluate_expression).
   */
  public val count_formula: String? by fromJson()

  /**
   * How much time one unit takes to research. In a lab with a crafting speed of `1`, it corresponds
   * to the number of seconds.
   */
  public val time: Double by fromJson()

  /**
   * List of ingredients needed for one unit of research. The items must all be
   * [ToolPrototypes](prototype:ToolPrototype).
   */
  public val ingredients: List<IngredientPrototype> by fromJson()

  public object Serializer : JsonReaderSerializer<TechnologyUnit>(TechnologyUnit::class)
}

@Serializable(TextBoxStyleSpecification.Serializer::class)
@SerialName("textbox_style")
public open class TextBoxStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * Name of a [FontPrototype](prototype:FontPrototype).
   */
  public val font: String? by fromJson()

  public val font_color: Color? by fromJson()

  public val disabled_font_color: Color? by fromJson()

  public val selection_background_color: Color? by fromJson()

  public val default_background: ElementImageSet? by fromJson()

  public val active_background: ElementImageSet? by fromJson()

  public val game_controller_hovered_background: ElementImageSet? by fromJson()

  public val disabled_background: ElementImageSet? by fromJson()

  public val rich_text_setting: RichTextSetting? by fromJson()

  public val rich_text_highlight_error_color: Color? by fromJson()

  public val rich_text_highlight_warning_color: Color? by fromJson()

  public val rich_text_highlight_ok_color: Color? by fromJson()

  public val selected_rich_text_highlight_error_color: Color? by fromJson()

  public val selected_rich_text_highlight_warning_color: Color? by fromJson()

  public val selected_rich_text_highlight_ok_color: Color? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TextBoxStyleSpecification>(TextBoxStyleSpecification::class)
}

@Serializable(ThrowCapsuleAction.Serializer::class)
@SerialName("throw")
public open class ThrowCapsuleAction : JsonReader(), CapsuleAction {
  public val type: UnknownStringLiteral by fromJson()

  public val attack_parameters: AttackParameters by fromJson()

  /**
   * Whether using the capsule consumes an item from the stack.
   */
  public val uses_stack: Boolean? by fromJson()

  public object Serializer : JsonReaderSerializer<ThrowCapsuleAction>(ThrowCapsuleAction::class)
}

@Serializable(TileAndAlpha.Serializer::class)
public open class TileAndAlpha : JsonReader() {
  public val tile: TileID by fromJson()

  public val alpha: Float by fromJson()

  public object Serializer : JsonReaderSerializer<TileAndAlpha>(TileAndAlpha::class)
}

@Serializable(TileBuildSound.Serializer::class)
public open class TileBuildSound : JsonReader() {
  public val small: Sound? by fromJson()

  public val medium: Sound? by fromJson()

  public val large: Sound? by fromJson()

  public object Serializer : JsonReaderSerializer<TileBuildSound>(TileBuildSound::class)
}

/**
 * The name of a [TilePrototype](prototype:TilePrototype).
 */
public typealias TileID = String

/**
 * Name of an allowed tile, or a list of two tile names for entities allowed on transitions.
 */
public typealias TileIDRestriction = ItemOrTuple2<TileID>

@Serializable
public enum class TileRenderLayer {
  zero,
  water,
  `water-overlay`,
  ground,
  top,
}

/**
 * Used by [TilePrototype](prototype:TilePrototype).
 */
@Serializable(TileSprite.Serializer::class)
public open class TileSprite : JsonReader() {
  /**
   * Frame count.
   *
   * Optional if it is loaded inside of a `hr_version`, see that property for more information.
   */
  public val count: UInt by fromJson()

  public val picture: FileName by fromJson()

  /**
   * If this property exists and high resolution sprites are turned on, its contents are used to
   * load the tile sprite.
   *
   * `count` in `hr_version` has to be either unspecified or the same as for normal resolution
   * definition.
   */
  public val hr_version: TileSprite? by fromJson()

  public val scale: Float? by fromJson()

  /**
   * Horizontal position of the sprite in the source file in pixels.
   */
  public val x: SpriteSizeType? by fromJson()

  /**
   * Vertical position of the sprite in the source file in pixels.
   */
  public val y: SpriteSizeType? by fromJson()

  /**
   * Once the specified number of pictures is loaded, other pictures are loaded on other line. This
   * is to allow having longer animations in matrix, to input files with too high width. The game
   * engine limits the width of any input files to 8192px, so it is compatible with most graphics
   * cards. 0 means that all the pictures are in one horizontal line.
   */
  public val line_length: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<TileSprite>(TileSprite::class)
}

@Serializable(TileSpriteWithProbability.Serializer::class)
public open class TileSpriteWithProbability : TileSprite() {
  /**
   * Only powers of 2 from 1 to 128 can be used. Square size of the tile arrangement this sprite is
   * used for. Used to calculate the `width` and `height` of the sprite which cannot be set directly.
   * (width or height) = size * 32 / scale.
   */
  public val size: UInt by fromJson()

  /**
   * Probability of 1x1 (size = 1) version of tile must be 1.
   */
  public val probability: Double? by fromJson()

  public val weights: List<Double>? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TileSpriteWithProbability>(TileSpriteWithProbability::class)
}

@Serializable(TileTransitionSprite.Serializer::class)
public open class TileTransitionSprite : JsonReader() {
  /**
   * Frame count.
   */
  public val count: UInt by fromJson()

  public val picture: FileName by fromJson()

  /**
   * If this property exists and high resolution sprites are turned on, its contents are used to
   * load the tile transition sprite.
   */
  public val hr_version: TileTransitionSprite? by fromJson()

  /**
   * If this is true, the shift of the tile transition sprite is set to `{0, 0.5}`.
   */
  public val tall: Boolean? by fromJson()

  public val scale: Float? by fromJson()

  /**
   * Horizontal position of the sprite in the source file in pixels.
   */
  public val x: SpriteSizeType? by fromJson()

  /**
   * Vertical position of the sprite in the source file in pixels.
   */
  public val y: SpriteSizeType? by fromJson()

  public object Serializer : JsonReaderSerializer<TileTransitionSprite>(TileTransitionSprite::class)
}

/**
 * Used for [TilePrototype](prototype:TilePrototype) graphics.
 */
public sealed class TileTransitions : JsonReader() {
  /**
   * This or `side_mask` needs to be specified if `empty_transitions` is not true.
   */
  public val side: TileTransitionSprite? by fromJson()

  /**
   * This or `side` needs to be specified if `empty_transitions` is not true.
   */
  public val side_mask: TileTransitionSprite? by fromJson()

  /**
   * This or `inner_corner_mask` needs to be specified if `empty_transitions` is not true.
   */
  public val inner_corner: TileTransitionSprite? by fromJson()

  /**
   * This or `inner_corner` needs to be specified if `empty_transitions` is not true.
   */
  public val inner_corner_mask: TileTransitionSprite? by fromJson()

  /**
   * This or `outer_corner_mask` needs to be specified if `empty_transitions` is not true.
   */
  public val outer_corner: TileTransitionSprite? by fromJson()

  /**
   * This or `outer_corner` needs to be specified if `empty_transitions` is not true.
   */
  public val outer_corner_mask: TileTransitionSprite? by fromJson()

  public val empty_transitions: Boolean? by fromJson()

  public val side_background: TileTransitionSprite? by fromJson()

  public val side_background_mask: TileTransitionSprite? by fromJson()

  public val side_effect_map: TileTransitionSprite? by fromJson()

  public val side_weights: List<Float>? by fromJson()

  public val inner_corner_background: TileTransitionSprite? by fromJson()

  public val inner_corner_background_mask: TileTransitionSprite? by fromJson()

  public val inner_corner_effect_map: TileTransitionSprite? by fromJson()

  public val inner_corner_weights: List<Float>? by fromJson()

  public val outer_corner_background: TileTransitionSprite? by fromJson()

  public val outer_corner_background_mask: TileTransitionSprite? by fromJson()

  public val outer_corner_effect_map: TileTransitionSprite? by fromJson()

  public val outer_corner_weights: List<Float>? by fromJson()

  public val u_transition: TileTransitionSprite? by fromJson()

  public val u_transition_mask: TileTransitionSprite? by fromJson()

  public val u_transition_background: TileTransitionSprite? by fromJson()

  public val u_transition_background_mask: TileTransitionSprite? by fromJson()

  public val u_transition_effect_map: TileTransitionSprite? by fromJson()

  public val u_transition_weights: List<Float>? by fromJson()

  public val o_transition: TileSprite? by fromJson()

  public val o_transition_mask: TileSprite? by fromJson()

  public val o_transition_background: TileSprite? by fromJson()

  public val o_transition_background_mask: TileSprite? by fromJson()

  public val o_transition_effect_map: TileSprite? by fromJson()

  public val water_patch: Sprite? by fromJson()

  public val effect_mask: Animation? by fromJson()

  public val layer: UByte? by fromJson()

  public val overlay_layer_group: TileRenderLayer? by fromJson()

  public val background_layer_group: TileRenderLayer? by fromJson()

  public val overlay_layer_offset: Byte? by fromJson()

  public val masked_overlay_layer_offset: Byte? by fromJson()

  public val background_layer_offset: Byte? by fromJson()

  public val masked_background_layer_offset: Byte? by fromJson()

  public val apply_effect_color_to_overlay: Boolean? by fromJson()

  public val offset_background_layer_by_tile_layer: Boolean? by fromJson()
}

@Serializable(TileTransitionsBetweenTransitions.Serializer::class)
public open class TileTransitionsBetweenTransitions : TileTransitions() {
  public val transition_group1: UByte by fromJson()

  public val transition_group2: UByte by fromJson()

  public object Serializer :
      JsonReaderSerializer<TileTransitionsBetweenTransitions>(TileTransitionsBetweenTransitions::class)
}

@Serializable(TileTransitionsToTiles.Serializer::class)
public open class TileTransitionsToTiles : TileTransitions() {
  public val to_tiles: List<TileID> by fromJson()

  public val transition_group: UByte by fromJson()

  public object Serializer :
      JsonReaderSerializer<TileTransitionsToTiles>(TileTransitionsToTiles::class)
}

@Serializable(TileTransitionsVariants.Serializer::class)
public open class TileTransitionsVariants : TileTransitions() {
  public val main: List<TileSpriteWithProbability> by fromJson()

  /**
   * Width and height are given by the game, setting them will not have an effect. Width and height
   * are calculated from the expected size (32) and the scale. So, for HR tiles at a size of 64x64, the
   * scale needs to be 0.5.
   */
  public val material_background: TileSprite? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TileTransitionsVariants>(TileTransitionsVariants::class)
}

@Serializable(TimeElapsedTipTrigger.Serializer::class)
@SerialName("time-elapsed")
public open class TimeElapsedTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val ticks: UInt by fromJson()

  public object Serializer :
      JsonReaderSerializer<TimeElapsedTipTrigger>(TimeElapsedTipTrigger::class)
}

/**
 * This is used by [TipsAndTricksItem](prototype:TipsAndTricksItem) for the initial starting status.
 * One of the following values:
 */
@Serializable
public enum class TipStatus {
  locked,
  optional,
  `dependencies-not-met`,
  unlocked,
  suggested,
  `not-to-be-suggested`,
  `completed-without-tutorial`,
  completed,
}

/**
 * Loaded as one of the tip triggers, based on the value of the `type` key.
 */
@Serializable
public sealed interface TipTrigger

@Serializable(TrainBrakingForceBonusModifier.Serializer::class)
@SerialName("train-braking-force-bonus")
public open class TrainBrakingForceBonusModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TrainBrakingForceBonusModifier>(TrainBrakingForceBonusModifier::class)
}

@Serializable(TrainPathFinderConstants.Serializer::class)
public open class TrainPathFinderConstants : JsonReader() {
  public val train_stop_penalty: UInt by fromJson()

  public val stopped_manually_controlled_train_penalty: UInt by fromJson()

  public val stopped_manually_controlled_train_without_passenger_penalty: UInt by fromJson()

  public val signal_reserved_by_circuit_network_penalty: UInt by fromJson()

  public val train_in_station_penalty: UInt by fromJson()

  public val train_in_station_with_no_other_valid_stops_in_schedule: UInt by fromJson()

  public val train_arriving_to_station_penalty: UInt by fromJson()

  public val train_arriving_to_signal_penalty: UInt by fromJson()

  public val train_waiting_at_signal_penalty: UInt by fromJson()

  /**
   * Must be >= 0.
   */
  public val train_waiting_at_signal_tick_multiplier_penalty: Float by fromJson()

  public val train_with_no_path_penalty: UInt by fromJson()

  public val train_auto_without_schedule_penalty: UInt by fromJson()

  public object Serializer :
      JsonReaderSerializer<TrainPathFinderConstants>(TrainPathFinderConstants::class)
}

@Serializable(TrainStopDrawingBoxes.Serializer::class)
public open class TrainStopDrawingBoxes : JsonReader() {
  public val north: BoundingBox by fromJson()

  public val east: BoundingBox by fromJson()

  public val south: BoundingBox by fromJson()

  public val west: BoundingBox by fromJson()

  public object Serializer :
      JsonReaderSerializer<TrainStopDrawingBoxes>(TrainStopDrawingBoxes::class)
}

@Serializable(TrainStopLight.Serializer::class)
public open class TrainStopLight : JsonReader() {
  public val picture: Sprite4Way by fromJson()

  public val red_picture: Sprite4Way by fromJson()

  public val light: LightDefinition by fromJson()

  public object Serializer : JsonReaderSerializer<TrainStopLight>(TrainStopLight::class)
}

@Serializable(TransportBeltAnimationSet.Serializer::class)
public open class TransportBeltAnimationSet : JsonReader() {
  public val animation_set: RotatedAnimation by fromJson()

  public val east_index: UByte? by fromJson()

  public val west_index: UByte? by fromJson()

  public val north_index: UByte? by fromJson()

  public val south_index: UByte? by fromJson()

  public val starting_south_index: UByte? by fromJson()

  public val ending_south_index: UByte? by fromJson()

  public val starting_west_index: UByte? by fromJson()

  public val ending_west_index: UByte? by fromJson()

  public val starting_north_index: UByte? by fromJson()

  public val ending_north_index: UByte? by fromJson()

  public val starting_east_index: UByte? by fromJson()

  public val ending_east_index: UByte? by fromJson()

  public val ending_patch: Sprite4Way? by fromJson()

  public val ends_with_stopper: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TransportBeltAnimationSet>(TransportBeltAnimationSet::class)
}

@Serializable(TransportBeltAnimationSetWithCorners.Serializer::class)
public open class TransportBeltAnimationSetWithCorners : TransportBeltAnimationSet() {
  public val east_to_north_index: UByte? by fromJson()

  public val north_to_east_index: UByte? by fromJson()

  public val west_to_north_index: UByte? by fromJson()

  public val north_to_west_index: UByte? by fromJson()

  public val south_to_east_index: UByte? by fromJson()

  public val east_to_south_index: UByte? by fromJson()

  public val south_to_west_index: UByte? by fromJson()

  public val west_to_south_index: UByte? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TransportBeltAnimationSetWithCorners>(TransportBeltAnimationSetWithCorners::class)
}

/**
 * Used to define the graphics for the (in vanilla) yellow frame that is used when a
 * [TransportBeltPrototype](prototype:TransportBeltPrototype) is connected to the circuit network.
 */
@Serializable(TransportBeltConnectorFrame.Serializer::class)
public open class TransportBeltConnectorFrame : JsonReader() {
  public val frame_main: AnimationVariations by fromJson()

  public val frame_shadow: AnimationVariations by fromJson()

  public val frame_main_scanner: Animation by fromJson()

  public val frame_main_scanner_movement_speed: Float by fromJson()

  public val frame_main_scanner_horizontal_start_shift: Vector by fromJson()

  public val frame_main_scanner_horizontal_end_shift: Vector by fromJson()

  public val frame_main_scanner_horizontal_y_scale: Float by fromJson()

  public val frame_main_scanner_horizontal_rotation: RealOrientation by fromJson()

  public val frame_main_scanner_vertical_start_shift: Vector by fromJson()

  public val frame_main_scanner_vertical_end_shift: Vector by fromJson()

  public val frame_main_scanner_vertical_y_scale: Float by fromJson()

  public val frame_main_scanner_vertical_rotation: RealOrientation by fromJson()

  public val frame_main_scanner_cross_horizontal_start_shift: Vector by fromJson()

  public val frame_main_scanner_cross_horizontal_end_shift: Vector by fromJson()

  public val frame_main_scanner_cross_horizontal_y_scale: Float by fromJson()

  public val frame_main_scanner_cross_horizontal_rotation: RealOrientation by fromJson()

  public val frame_main_scanner_cross_vertical_start_shift: Vector by fromJson()

  public val frame_main_scanner_cross_vertical_end_shift: Vector by fromJson()

  public val frame_main_scanner_cross_vertical_y_scale: Float by fromJson()

  public val frame_main_scanner_cross_vertical_rotation: RealOrientation by fromJson()

  public val frame_main_scanner_nw_ne: Animation by fromJson()

  public val frame_main_scanner_sw_se: Animation by fromJson()

  public val frame_back_patch: SpriteVariations? by fromJson()

  public val frame_front_patch: SpriteVariations? by fromJson()

  public object Serializer :
      JsonReaderSerializer<TransportBeltConnectorFrame>(TransportBeltConnectorFrame::class)
}

@Serializable(TreeVariation.Serializer::class)
public open class TreeVariation : JsonReader() {
  /**
   * If `shadow` is not specified, this has to have one more frame than `leaves`.
   */
  public val trunk: Animation by fromJson()

  public val leaves: Animation by fromJson()

  public val leaf_generation: CreateParticleTriggerEffectItem by fromJson()

  public val branch_generation: CreateParticleTriggerEffectItem by fromJson()

  /**
   * Shadow must have 1 less `frame_count` than `leaves`.
   */
  public val shadow: Animation? by fromJson()

  /**
   * Only loaded if `shadow` is present. Defaults to `shadow.frame_count - 1`.
   */
  public val disable_shadow_distortion_beginning_at_frame: UInt? by fromJson()

  /**
   * Normal must have the same frame_count as `leaves`.
   */
  public val normal: Animation? by fromJson()

  /**
   * Overlay must have the same frame_count as `leaves`. Won't be tinted by the tree color unless
   * `apply_runtime_tint` is set to `true` in the sprite definition. See
   * [here](https://forums.factorio.com/viewtopic.php?p=547758#p547758).
   */
  public val overlay: Animation? by fromJson()

  public val water_reflection: WaterReflectionDefinition? by fromJson()

  public object Serializer : JsonReaderSerializer<TreeVariation>(TreeVariation::class)
}

/**
 * Loaded as one of the [TriggerItem](prototype:TriggerItem) extensions, based on the value of the
 * `type` key.
 */
public typealias Trigger = ItemOrList<UnknownUnion>

/**
 * Loaded as one of the [TriggerDeliveryItem](prototype:TriggerDeliveryItem) extensions, based on
 * the value of the `type` key.
 */
@Serializable
public sealed interface TriggerDelivery

/**
 * The abstract base of all [TriggerDeliveries](prototype:TriggerDelivery).
 */
public sealed class TriggerDeliveryItem : JsonReader() {
  /**
   * Provides the source of the TriggerDelivery as as both the source and target of the effect.
   */
  public val source_effects: TriggerEffect? by fromJson()

  public val target_effects: TriggerEffect? by fromJson()
}

/**
 * Loaded as one of the [TriggerEffectItem](prototype:TriggerEffectItem) extensions, based on the
 * value of the `type` key.
 */
public typealias TriggerEffect = ItemOrList<UnknownUnion>

/**
 * The abstract base of all [TriggerEffects](prototype:TriggerEffect).
 */
public sealed class TriggerEffectItem : JsonReader() {
  public val repeat_count: UShort? by fromJson()

  public val repeat_count_deviation: UShort? by fromJson()

  /**
   * Must be greater than `0` and less than or equal to `1`.
   */
  public val probability: Float? by fromJson()

  public val affects_target: Boolean? by fromJson()

  public open val show_in_tooltip: Boolean? by fromJson()

  /**
   * Guaranteed to work with
   * [EntityWithHealthPrototype::damaged_trigger_effect](prototype:EntityWithHealthPrototype::damaged_trigger_effect)
   * and
   * [EntityWithHealthPrototype::dying_trigger_effect](prototype:EntityWithHealthPrototype::dying_trigger_effect).
   * Unknown if it works with other properties that use [TriggerEffect](prototype:TriggerEffect).
   */
  public val damage_type_filters: DamageTypeFilters? by fromJson()
}

/**
 * The abstract base of all [Triggers](prototype:Trigger).
 */
public sealed class TriggerItem : JsonReader() {
  /**
   * Only prototypes with these flags are affected by the trigger item.
   */
  public val entity_flags: EntityPrototypeFlags? by fromJson()

  public val ignore_collision_condition: Boolean? by fromJson()

  /**
   * The trigger affects only prototypes with these masks.
   */
  public val trigger_target_mask: TriggerTargetMask? by fromJson()

  public val repeat_count: UInt? by fromJson()

  /**
   * Must be greater than 0 and less than or equal to 1.
   */
  public val probability: Float? by fromJson()

  /**
   * Only prototypes with these collision masks are affected by the trigger item.
   */
  public val collision_mask: CollisionMask? by fromJson()

  public val action_delivery: ItemOrList<TriggerDelivery>? by fromJson()

  /**
   * Only entities meeting the force condition are affected by the trigger item.
   */
  public val force: ForceCondition? by fromJson()
}

/**
 * An array of names of [TriggerTargetType](prototype:TriggerTargetType). See [Design discussion:
 * Trigger target type](https://forums.factorio.com/71657) and [Blacklist for prototypes turrets
 * shouldn't attack](https://forums.factorio.com/86164).
 */
public typealias TriggerTargetMask = List<String>

/**
 * The name of a [TrivialSmokePrototype](prototype:TrivialSmokePrototype).
 */
public typealias TrivialSmokeID = String

@Serializable(TurretAttackModifier.Serializer::class)
@SerialName("turret-attack")
public open class TurretAttackModifier : BaseModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  /**
   * Name of the [EntityPrototype](prototype:EntityPrototype) that is affected. This also works for
   * non-turrets such as tanks, however, the bonus does not appear in the entity's tooltips.
   */
  public val turret_id: EntityID by fromJson()

  /**
   * Modification value, which will be added to the current turret attack modifier upon researching.
   */
  public val modifier: Double by fromJson()

  public object Serializer : JsonReaderSerializer<TurretAttackModifier>(TurretAttackModifier::class)
}

@Serializable(UndergroundBeltStructure.Serializer::class)
public open class UndergroundBeltStructure : JsonReader() {
  public val direction_in: Sprite4Way by fromJson()

  public val direction_out: Sprite4Way by fromJson()

  public val back_patch: Sprite4Way? by fromJson()

  public val front_patch: Sprite4Way? by fromJson()

  public val direction_in_side_loading: Sprite4Way? by fromJson()

  public val direction_out_side_loading: Sprite4Way? by fromJson()

  public object Serializer :
      JsonReaderSerializer<UndergroundBeltStructure>(UndergroundBeltStructure::class)
}

/**
 * Used by [UnitPrototype](prototype:UnitPrototype).
 */
@Serializable(UnitAISettings.Serializer::class)
public open class UnitAISettings : JsonReader() {
  /**
   * If enabled, units that repeatedly fail to succeed at commands will be destroyed.
   */
  public val destroy_when_commands_fail: Boolean? by fromJson()

  /**
   * If enabled, units that have nothing else to do will attempt to return to a spawner.
   */
  public val allow_try_return_to_spawner: Boolean? by fromJson()

  /**
   * If enabled, units will try to separate themselves from nearby friendly units.
   */
  public val do_separation: Boolean? by fromJson()

  /**
   * Must be between -8 and 8.
   */
  public val path_resolution_modifier: Byte? by fromJson()

  public object Serializer : JsonReaderSerializer<UnitAISettings>(UnitAISettings::class)
}

@Serializable(UnitAlternativeFrameSequence.Serializer::class)
public open class UnitAlternativeFrameSequence : JsonReader() {
  /**
   * Indices of frames from the attack parameter animation.
   */
  public val warmup_frame_sequence: List<UShort> by fromJson()

  /**
   * Indices of frames from the attack parameter animation.
   */
  public val warmup2_frame_sequence: List<UShort> by fromJson()

  /**
   * Indices of frames from the attack parameter animation.
   */
  public val attacking_frame_sequence: List<UShort> by fromJson()

  /**
   * Indices of frames from the attack parameter animation.
   */
  public val cooldown_frame_sequence: List<UShort> by fromJson()

  /**
   * Indices of frames from the attack parameter animation.
   */
  public val prepared_frame_sequence: List<UShort> by fromJson()

  /**
   * Indices of frames from the attack parameter animation.
   */
  public val back_to_walk_frame_sequence: List<UShort> by fromJson()

  public val warmup_animation_speed: Float by fromJson()

  public val attacking_animation_speed: Float by fromJson()

  public val cooldown_animation_speed: Float by fromJson()

  public val prepared_animation_speed: Float by fromJson()

  public val back_to_walk_animation_speed: Float by fromJson()

  public object Serializer :
      JsonReaderSerializer<UnitAlternativeFrameSequence>(UnitAlternativeFrameSequence::class)
}

@Serializable(UnitGroupSettings.Serializer::class)
public open class UnitGroupSettings : JsonReader() {
  /**
   * Pollution triggered group waiting time is a random time between min and max gathering time
   */
  public val min_group_gathering_time: UInt by fromJson()

  public val max_group_gathering_time: UInt by fromJson()

  /**
   * After the gathering is finished the group can still wait for late members, but it doesn't
   * accept new ones anymore.
   */
  public val max_wait_time_for_late_members: UInt by fromJson()

  /**
   * Limits for group radius (calculated by number of numbers).
   */
  public val max_group_radius: Double by fromJson()

  public val min_group_radius: Double by fromJson()

  /**
   * When a member falls behind the group he can speedup up till this much of his regular speed.
   */
  public val max_member_speedup_when_behind: Double by fromJson()

  /**
   * When a member gets ahead of its group, it will slow down to at most this factor of its speed.
   */
  public val max_member_slowdown_when_ahead: Double by fromJson()

  /**
   * When members of a group are behind, the entire group will slow down to at most this factor of
   * its max speed.
   */
  public val max_group_slowdown_factor: Double by fromJson()

  /**
   * If a member falls behind more than this times the group radius, the group will slow down to
   * max_group_slowdown_factor.
   */
  public val max_group_member_fallback_factor: Double by fromJson()

  /**
   * If a member falls behind more than this time the group radius, it will be removed from the
   * group.
   */
  public val member_disown_distance: Double by fromJson()

  public val tick_tolerance_when_member_arrives: UInt by fromJson()

  /**
   * Maximum number of automatically created unit groups gathering for attack at any time.
   */
  public val max_gathering_unit_groups: UInt by fromJson()

  /**
   * Maximum size of an attack unit group. This only affects automatically-created unit groups;
   * manual groups created through the API are unaffected.
   */
  public val max_unit_group_size: UInt by fromJson()

  public object Serializer : JsonReaderSerializer<UnitGroupSettings>(UnitGroupSettings::class)
}

/**
 * It can be specified as a table with named or numbered keys, but not a mix of both. If this is
 * specified as a table with numbered keys then the first value is the unit and the second is the spawn
 * points.
 */
@Serializable(UnitSpawnDefinitionValues.Serializer::class)
public open class UnitSpawnDefinitionValues : JsonReader() {
  public val unit: EntityID by fromJson()

  /**
   * Array of evolution and probability info, with the following conditions:
   *
   * - The `evolution_factor` must be ascending from entry to entry.
   *
   * - The last entry's weight will be used when the `evolution_factor` is larger than the last
   * entry.
   *
   * - Weights are linearly interpolated between entries.
   *
   * - Individual weights are scaled linearly so that the cumulative weight is `1`.
   */
  public val spawn_points: List<SpawnPoint> by fromJson()

  public object Serializer :
      JsonReaderSerializer<UnitSpawnDefinitionValues>(UnitSpawnDefinitionValues::class)
}

/**
 * It can be specified as a table with named or numbered keys, but not a mix of both. If this is
 * specified as a table with numbered keys then the first value is the unit and the second is the spawn
 * points.
 */
public typealias UnitSpawnDefinition = UnknownUnion

@Serializable(UnlockRecipeModifier.Serializer::class)
@SerialName("unlock-recipe")
public open class UnlockRecipeModifier : BaseModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  /**
   * Prototype name of the [RecipePrototype](prototype:RecipePrototype) that is unlocked upon
   * researching.
   */
  public val recipe: RecipeID by fromJson()

  public object Serializer : JsonReaderSerializer<UnlockRecipeModifier>(UnlockRecipeModifier::class)
}

@Serializable(UnlockRecipeTipTrigger.Serializer::class)
@SerialName("unlock-recipe")
public open class UnlockRecipeTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val recipe: RecipeID by fromJson()

  public object Serializer :
      JsonReaderSerializer<UnlockRecipeTipTrigger>(UnlockRecipeTipTrigger::class)
}

@Serializable(UseConfirmTipTrigger.Serializer::class)
@SerialName("use-confirm")
public open class UseConfirmTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<UseConfirmTipTrigger>(UseConfirmTipTrigger::class)
}

@Serializable(UseOnSelfCapsuleAction.Serializer::class)
@SerialName("use-on-self")
public open class UseOnSelfCapsuleAction : JsonReader(), CapsuleAction {
  public val type: UnknownStringLiteral by fromJson()

  public val attack_parameters: AttackParameters by fromJson()

  /**
   * Whether using the capsule consumes an item from the stack.
   */
  public val uses_stack: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<UseOnSelfCapsuleAction>(UseOnSelfCapsuleAction::class)
}

@Serializable(UsePipetteTipTrigger.Serializer::class)
@SerialName("use-pipette")
public open class UsePipetteTipTrigger : JsonReader(), TipTrigger {
  public val type: UnknownStringLiteral by fromJson()

  public val count: UInt? by fromJson()

  public object Serializer : JsonReaderSerializer<UsePipetteTipTrigger>(UsePipetteTipTrigger::class)
}

@Serializable(VectorRotation.Serializer::class)
public open class VectorRotation : JsonReader() {
  /**
   * The size of all `frames` must be the same.
   */
  public val frames: List<Vector> by fromJson()

  public val render_layer: RenderLayer? by fromJson()

  public object Serializer : JsonReaderSerializer<VectorRotation>(VectorRotation::class)
}

@Serializable
public enum class VerticalAlign {
  top,
  center,
  bottom,
}

@Serializable(VerticalFlowStyleSpecification.Serializer::class)
@SerialName("vertical_flow_style")
public open class VerticalFlowStyleSpecification : BaseStyleSpecification(), StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public val vertical_spacing: Int? by fromJson()

  public object Serializer :
      JsonReaderSerializer<VerticalFlowStyleSpecification>(VerticalFlowStyleSpecification::class)
}

@Serializable(VerticalScrollBarStyleSpecification.Serializer::class)
@SerialName("vertical_scrollbar_style")
public open class VerticalScrollBarStyleSpecification : ScrollBarStyleSpecification(),
    StyleSpecification {
  public val type: UnknownStringLiteral by fromJson()

  public object Serializer :
      JsonReaderSerializer<VerticalScrollBarStyleSpecification>(VerticalScrollBarStyleSpecification::class)
}

/**
 * The name of a [VirtualSignalPrototype](prototype:VirtualSignalPrototype).
 */
public typealias VirtualSignalID = String

/**
 * Void energy sources provide unlimited free energy.
 */
@Serializable(VoidEnergySource.Serializer::class)
@SerialName("void")
public open class VoidEnergySource : BaseEnergySource(), EnergySource {
  public val type: UnknownStringLiteral by fromJson()

  public object Serializer : JsonReaderSerializer<VoidEnergySource>(VoidEnergySource::class)
}

@Serializable(WallPictures.Serializer::class)
public open class WallPictures : JsonReader() {
  public val single: SpriteVariations by fromJson()

  public val straight_vertical: SpriteVariations by fromJson()

  public val straight_horizontal: SpriteVariations by fromJson()

  public val corner_right_down: SpriteVariations by fromJson()

  public val corner_left_down: SpriteVariations by fromJson()

  public val t_up: SpriteVariations by fromJson()

  public val ending_right: SpriteVariations by fromJson()

  public val ending_left: SpriteVariations by fromJson()

  public val filling: SpriteVariations? by fromJson()

  public val water_connection_patch: Sprite4Way? by fromJson()

  public val gate_connection_patch: Sprite4Way? by fromJson()

  public object Serializer : JsonReaderSerializer<WallPictures>(WallPictures::class)
}

/**
 * Entity water reflection. [Currently only renders](https://forums.factorio.com/100703) for
 * [EntityWithHealthPrototype](prototype:EntityWithHealthPrototype).
 */
@Serializable(WaterReflectionDefinition.Serializer::class)
public open class WaterReflectionDefinition : JsonReader() {
  public val pictures: SpriteVariations? by fromJson()

  public val orientation_to_variation: Boolean? by fromJson()

  public val rotate: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<WaterReflectionDefinition>(WaterReflectionDefinition::class)
}

/**
 * Definition of a point where circuit network wires can be connected to an entity.
 */
@Serializable(WireConnectionPoint.Serializer::class)
public open class WireConnectionPoint : JsonReader() {
  public val wire: WirePosition by fromJson()

  public val shadow: WirePosition by fromJson()

  public object Serializer : JsonReaderSerializer<WireConnectionPoint>(WireConnectionPoint::class)
}

/**
 * Used by [WireConnectionPoint](prototype:WireConnectionPoint).
 */
@Serializable(WirePosition.Serializer::class)
public open class WirePosition : JsonReader() {
  public val copper: Vector? by fromJson()

  public val red: Vector? by fromJson()

  public val green: Vector? by fromJson()

  public object Serializer : JsonReaderSerializer<WirePosition>(WirePosition::class)
}

@Serializable(WorkerRobotBatteryModifier.Serializer::class)
@SerialName("worker-robot-battery")
public open class WorkerRobotBatteryModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<WorkerRobotBatteryModifier>(WorkerRobotBatteryModifier::class)
}

@Serializable(WorkerRobotSpeedModifier.Serializer::class)
@SerialName("worker-robot-speed")
public open class WorkerRobotSpeedModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<WorkerRobotSpeedModifier>(WorkerRobotSpeedModifier::class)
}

@Serializable(WorkerRobotStorageModifier.Serializer::class)
@SerialName("worker-robot-storage")
public open class WorkerRobotStorageModifier : SimpleModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If set to `false`, use the icon from [UtilitySprites](prototype:UtilitySprites) for this
   * technology effect icon.
   */
  public val infer_icon: Boolean? by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<WorkerRobotStorageModifier>(WorkerRobotStorageModifier::class)
}

/**
 * This type is used to produce sound from in-game entities when they are working/idle.
 */
@Serializable(WorkingSoundValues.Serializer::class)
public open class WorkingSoundValues : JsonReader() {
  /**
   * The sound to be played when the entity is working.
   */
  public val sound: Sound by fromJson()

  public val apparent_volume: Float? by fromJson()

  public val max_sounds_per_type: UByte? by fromJson()

  public val match_progress_to_activity: Boolean? by fromJson()

  public val match_volume_to_activity: Boolean? by fromJson()

  public val match_speed_to_activity: Boolean? by fromJson()

  public val persistent: Boolean? by fromJson()

  public val use_doppler_shift: Boolean? by fromJson()

  /**
   * Modifies how far a sound can be heard. Can only be 1 or lower, has to be a positive number.
   */
  public val audible_distance_modifier: Double? by fromJson()

  /**
   * Modifies how often the sound is played.
   */
  public val probability: Double? by fromJson()

  /**
   * Can't be used when `match_progress_to_activity` is true.
   */
  public val fade_in_ticks: UInt? by fromJson()

  /**
   * Can't be used when `match_progress_to_activity` is true.
   */
  public val fade_out_ticks: UInt? by fromJson()

  /**
   * The sound to be played when the entity is idle. Might not work with all entities that use
   * working_sound.
   */
  public val idle_sound: Sound? by fromJson()

  /**
   * Might not work with all entities that use working_sound.
   */
  public val activate_sound: Sound? by fromJson()

  /**
   * Might not work with all entities that use working_sound.
   */
  public val deactivate_sound: Sound? by fromJson()

  public object Serializer : JsonReaderSerializer<WorkingSoundValues>(WorkingSoundValues::class)
}

/**
 * This type is used to produce sound from in-game entities when they are working/idle.
 */
public typealias WorkingSound = UnknownUnion

@Serializable
public enum class WorkingVisualisationEffect {
  flicker,
  `uranium-glow`,
  none,
}

@Serializable
public enum class WorkingVisualisationApplyRecipeTint {
  primary,
  secondary,
  tertiary,
  quaternary,
  none,
}

@Serializable
public enum class WorkingVisualisationApplyTint {
  `resource-color`,
  `input-fluid-base-color`,
  `input-fluid-flow-color`,
  status,
  none,
}

/**
 * Used by crafting machines to display different graphics when the machine is running.
 */
@Serializable(WorkingVisualisation.Serializer::class)
public open class WorkingVisualisation : JsonReader() {
  public val render_layer: RenderLayer? by fromJson()

  public val fadeout: Boolean? by fromJson()

  public val synced_fadeout: Boolean? by fromJson()

  /**
   * Whether the animations are always played at the same speed, not adjusted to the machine speed.
   */
  public val constant_speed: Boolean? by fromJson()

  public val always_draw: Boolean? by fromJson()

  public val animated_shift: Boolean? by fromJson()

  public val align_to_waypoint: Boolean? by fromJson()

  /**
   * Used to determine render order for sprites with the same `render_layer` in the same position.
   * Sprites with a higher `secondary_draw_order` are drawn on top.
   */
  public val secondary_draw_order: Byte? by fromJson()

  public val draw_as_sprite: Boolean? by fromJson()

  public val draw_as_light: Boolean? by fromJson()

  public val light: LightDefinition? by fromJson()

  public val effect: WorkingVisualisationEffect? by fromJson()

  /**
   * Used by [CraftingMachinePrototype](prototype:CraftingMachinePrototype).
   */
  public val apply_recipe_tint: WorkingVisualisationApplyRecipeTint? by fromJson()

  /**
   * Used by [CraftingMachinePrototype](prototype:CraftingMachinePrototype) ("status" only) and
   * [MiningDrillPrototype](prototype:MiningDrillPrototype).
   *
   * For "status" on CraftingMachine, the colors are specified via
   * [CraftingMachinePrototype::status_colors](prototype:CraftingMachinePrototype::status_colors). For
   * "status" on MiningDrill, the colors are specified via
   * [MiningDrillGraphicsSet::status_colors](prototype:MiningDrillGraphicsSet::status_colors). For
   * "resource-color", the colors are specified via
   * [ResourceEntityPrototype::mining_visualisation_tint](prototype:ResourceEntityPrototype::mining_visualisation_tint).
   */
  public val apply_tint: WorkingVisualisationApplyTint? by fromJson()

  /**
   * Only loaded if `animation` is not defined.
   */
  public val north_animation: Animation? by fromJson()

  /**
   * Only loaded if `animation` is not defined.
   */
  public val west_animation: Animation? by fromJson()

  /**
   * Only loaded if `animation` is not defined.
   */
  public val south_animation: Animation? by fromJson()

  /**
   * Only loaded if `animation` is not defined.
   */
  public val east_animation: Animation? by fromJson()

  public val animation: Animation? by fromJson()

  public val north_position: Vector? by fromJson()

  public val west_position: Vector? by fromJson()

  public val south_position: Vector? by fromJson()

  public val east_position: Vector? by fromJson()

  public object Serializer : JsonReaderSerializer<WorkingVisualisation>(WorkingVisualisation::class)
}

@Serializable(ZoomToWorldBlueprintEnabledModifier.Serializer::class)
@SerialName("zoom-to-world-blueprint-enabled")
public open class ZoomToWorldBlueprintEnabledModifier : BoolModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ZoomToWorldBlueprintEnabledModifier>(ZoomToWorldBlueprintEnabledModifier::class)
}

@Serializable(ZoomToWorldDeconstructionPlannerEnabledModifier.Serializer::class)
@SerialName("zoom-to-world-deconstruction-planner-enabled")
public open class ZoomToWorldDeconstructionPlannerEnabledModifier : BoolModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ZoomToWorldDeconstructionPlannerEnabledModifier>(ZoomToWorldDeconstructionPlannerEnabledModifier::class)
}

@Serializable(ZoomToWorldEnabledModifier.Serializer::class)
@SerialName("zoom-to-world-enabled")
public open class ZoomToWorldEnabledModifier : BoolModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ZoomToWorldEnabledModifier>(ZoomToWorldEnabledModifier::class)
}

@Serializable(ZoomToWorldGhostBuildingEnabledModifier.Serializer::class)
@SerialName("zoom-to-world-ghost-building-enabled")
public open class ZoomToWorldGhostBuildingEnabledModifier : BoolModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ZoomToWorldGhostBuildingEnabledModifier>(ZoomToWorldGhostBuildingEnabledModifier::class)
}

@Serializable(ZoomToWorldSelectionToolEnabledModifier.Serializer::class)
@SerialName("zoom-to-world-selection-tool-enabled")
public open class ZoomToWorldSelectionToolEnabledModifier : BoolModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ZoomToWorldSelectionToolEnabledModifier>(ZoomToWorldSelectionToolEnabledModifier::class)
}

@Serializable(ZoomToWorldUpgradePlannerEnabledModifier.Serializer::class)
@SerialName("zoom-to-world-upgrade-planner-enabled")
public open class ZoomToWorldUpgradePlannerEnabledModifier : BoolModifier(), Modifier {
  public val type: UnknownStringLiteral by fromJson()

  /**
   * If `false`, do not draw the small "constant" icon over the technology effect icon.
   */
  public val use_icon_overlay_constant: Boolean? by fromJson()

  public object Serializer :
      JsonReaderSerializer<ZoomToWorldUpgradePlannerEnabledModifier>(ZoomToWorldUpgradePlannerEnabledModifier::class)
}

@Serializable(PrototypeData.Serializer::class)
public class PrototypeData : JsonReader() {
  public val accumulator: Map<String, AccumulatorPrototype>? by fromJson()

  public val achievement: Map<String, AchievementPrototype>? by fromJson()

  public val `active-defense-equipment`: Map<String, ActiveDefenseEquipmentPrototype>? by fromJson()

  public val `ambient-sound`: Map<String, AmbientSound>? by fromJson()

  public val `ammo-category`: Map<String, AmmoCategory>? by fromJson()

  public val ammo: Map<String, AmmoItemPrototype>? by fromJson()

  public val `ammo-turret`: Map<String, AmmoTurretPrototype>? by fromJson()

  public val animation: Map<String, AnimationPrototype>? by fromJson()

  public val `arithmetic-combinator`: Map<String, ArithmeticCombinatorPrototype>? by fromJson()

  public val armor: Map<String, ArmorPrototype>? by fromJson()

  public val arrow: Map<String, ArrowPrototype>? by fromJson()

  public val `artillery-flare`: Map<String, ArtilleryFlarePrototype>? by fromJson()

  public val `artillery-projectile`: Map<String, ArtilleryProjectilePrototype>? by fromJson()

  public val `artillery-turret`: Map<String, ArtilleryTurretPrototype>? by fromJson()

  public val `artillery-wagon`: Map<String, ArtilleryWagonPrototype>? by fromJson()

  public val `assembling-machine`: Map<String, AssemblingMachinePrototype>? by fromJson()

  public val `autoplace-control`: Map<String, AutoplaceControl>? by fromJson()

  public val `battery-equipment`: Map<String, BatteryEquipmentPrototype>? by fromJson()

  public val beacon: Map<String, BeaconPrototype>? by fromJson()

  public val beam: Map<String, BeamPrototype>? by fromJson()

  public val `belt-immunity-equipment`: Map<String, BeltImmunityEquipmentPrototype>? by fromJson()

  public val `blueprint-book`: Map<String, BlueprintBookPrototype>? by fromJson()

  public val blueprint: Map<String, BlueprintItemPrototype>? by fromJson()

  public val boiler: Map<String, BoilerPrototype>? by fromJson()

  public val `build-entity-achievement`: Map<String, BuildEntityAchievementPrototype>? by fromJson()

  public val `burner-generator`: Map<String, BurnerGeneratorPrototype>? by fromJson()

  public val capsule: Map<String, CapsulePrototype>? by fromJson()

  public val car: Map<String, CarPrototype>? by fromJson()

  public val `cargo-wagon`: Map<String, CargoWagonPrototype>? by fromJson()

  public val `character-corpse`: Map<String, CharacterCorpsePrototype>? by fromJson()

  public val character: Map<String, CharacterPrototype>? by fromJson()

  public val cliff: Map<String, CliffPrototype>? by fromJson()

  public val `combat-robot-count`: Map<String, CombatRobotCountAchievementPrototype>? by fromJson()

  public val `combat-robot`: Map<String, CombatRobotPrototype>? by fromJson()

  public val `constant-combinator`: Map<String, ConstantCombinatorPrototype>? by fromJson()

  public val `construct-with-robots-achievement`:
      Map<String, ConstructWithRobotsAchievementPrototype>? by fromJson()

  public val `construction-robot`: Map<String, ConstructionRobotPrototype>? by fromJson()

  public val container: Map<String, ContainerPrototype>? by fromJson()

  public val `copy-paste-tool`: Map<String, CopyPasteToolPrototype>? by fromJson()

  public val corpse: Map<String, CorpsePrototype>? by fromJson()

  public val `curved-rail`: Map<String, CurvedRailPrototype>? by fromJson()

  public val `custom-input`: Map<String, CustomInputPrototype>? by fromJson()

  public val `damage-type`: Map<String, DamageType>? by fromJson()

  public val `decider-combinator`: Map<String, DeciderCombinatorPrototype>? by fromJson()

  public val `deconstruct-with-robots-achievement`:
      Map<String, DeconstructWithRobotsAchievementPrototype>? by fromJson()

  public val `deconstructible-tile-proxy`: Map<String, DeconstructibleTileProxyPrototype>? by
      fromJson()

  public val `deconstruction-item`: Map<String, DeconstructionItemPrototype>? by fromJson()

  public val `optimized-decorative`: Map<String, DecorativePrototype>? by fromJson()

  public val `deliver-by-robots-achievement`: Map<String, DeliverByRobotsAchievementPrototype>? by
      fromJson()

  public val `dont-build-entity-achievement`: Map<String, DontBuildEntityAchievementPrototype>? by
      fromJson()

  public val `dont-craft-manually-achievement`: Map<String, DontCraftManuallyAchievementPrototype>?
      by fromJson()

  public val `dont-use-entity-in-energy-production-achievement`:
      Map<String, DontUseEntityInEnergyProductionAchievementPrototype>? by fromJson()

  public val `editor-controller`: Map<String, EditorControllerPrototype>? by fromJson()

  public val `electric-energy-interface`: Map<String, ElectricEnergyInterfacePrototype>? by
      fromJson()

  public val `electric-pole`: Map<String, ElectricPolePrototype>? by fromJson()

  public val `electric-turret`: Map<String, ElectricTurretPrototype>? by fromJson()

  public val `unit-spawner`: Map<String, EnemySpawnerPrototype>? by fromJson()

  public val `energy-shield-equipment`: Map<String, EnergyShieldEquipmentPrototype>? by fromJson()

  public val `entity-ghost`: Map<String, EntityGhostPrototype>? by fromJson()

  public val particle: Map<String, EntityParticlePrototype>? by fromJson()

  public val `equipment-category`: Map<String, EquipmentCategory>? by fromJson()

  public val `equipment-grid`: Map<String, EquipmentGridPrototype>? by fromJson()

  public val explosion: Map<String, ExplosionPrototype>? by fromJson()

  public val `finish-the-game-achievement`: Map<String, FinishTheGameAchievementPrototype>? by
      fromJson()

  public val fire: Map<String, FireFlamePrototype>? by fromJson()

  public val fish: Map<String, FishPrototype>? by fromJson()

  public val `flame-thrower-explosion`: Map<String, FlameThrowerExplosionPrototype>? by fromJson()

  public val fluid: Map<String, FluidPrototype>? by fromJson()

  public val stream: Map<String, FluidStreamPrototype>? by fromJson()

  public val `fluid-turret`: Map<String, FluidTurretPrototype>? by fromJson()

  public val `fluid-wagon`: Map<String, FluidWagonPrototype>? by fromJson()

  public val `flying-text`: Map<String, FlyingTextPrototype>? by fromJson()

  public val font: Map<String, FontPrototype>? by fromJson()

  public val `fuel-category`: Map<String, FuelCategory>? by fromJson()

  public val furnace: Map<String, FurnacePrototype>? by fromJson()

  public val gate: Map<String, GatePrototype>? by fromJson()

  public val `generator-equipment`: Map<String, GeneratorEquipmentPrototype>? by fromJson()

  public val generator: Map<String, GeneratorPrototype>? by fromJson()

  public val `god-controller`: Map<String, GodControllerPrototype>? by fromJson()

  public val `group-attack-achievement`: Map<String, GroupAttackAchievementPrototype>? by fromJson()

  public val `gui-style`: Map<String, GuiStyle>? by fromJson()

  public val gun: Map<String, GunPrototype>? by fromJson()

  public val `heat-interface`: Map<String, HeatInterfacePrototype>? by fromJson()

  public val `heat-pipe`: Map<String, HeatPipePrototype>? by fromJson()

  public val `highlight-box`: Map<String, HighlightBoxEntityPrototype>? by fromJson()

  public val `infinity-container`: Map<String, InfinityContainerPrototype>? by fromJson()

  public val `infinity-pipe`: Map<String, InfinityPipePrototype>? by fromJson()

  public val inserter: Map<String, InserterPrototype>? by fromJson()

  public val `item-entity`: Map<String, ItemEntityPrototype>? by fromJson()

  public val `item-group`: Map<String, ItemGroup>? by fromJson()

  public val item: Map<String, ItemPrototype>? by fromJson()

  public val `item-request-proxy`: Map<String, ItemRequestProxyPrototype>? by fromJson()

  public val `item-subgroup`: Map<String, ItemSubGroup>? by fromJson()

  public val `item-with-entity-data`: Map<String, ItemWithEntityDataPrototype>? by fromJson()

  public val `item-with-inventory`: Map<String, ItemWithInventoryPrototype>? by fromJson()

  public val `item-with-label`: Map<String, ItemWithLabelPrototype>? by fromJson()

  public val `item-with-tags`: Map<String, ItemWithTagsPrototype>? by fromJson()

  public val `kill-achievement`: Map<String, KillAchievementPrototype>? by fromJson()

  public val lab: Map<String, LabPrototype>? by fromJson()

  public val lamp: Map<String, LampPrototype>? by fromJson()

  public val `land-mine`: Map<String, LandMinePrototype>? by fromJson()

  public val `leaf-particle`: Map<String, LeafParticlePrototype>? by fromJson()

  public val `linked-belt`: Map<String, LinkedBeltPrototype>? by fromJson()

  public val `linked-container`: Map<String, LinkedContainerPrototype>? by fromJson()

  public val `loader-1x1`: Map<String, Loader1x1Prototype>? by fromJson()

  public val loader: Map<String, Loader1x2Prototype>? by fromJson()

  public val locomotive: Map<String, LocomotivePrototype>? by fromJson()

  public val `logistic-container`: Map<String, LogisticContainerPrototype>? by fromJson()

  public val `logistic-robot`: Map<String, LogisticRobotPrototype>? by fromJson()

  public val `map-gen-presets`: Map<String, MapGenPresets>? by fromJson()

  public val `map-settings`: Map<String, MapSettings>? by fromJson()

  public val market: Map<String, MarketPrototype>? by fromJson()

  public val `mining-drill`: Map<String, MiningDrillPrototype>? by fromJson()

  public val `mining-tool`: Map<String, MiningToolPrototype>? by fromJson()

  public val `module-category`: Map<String, ModuleCategory>? by fromJson()

  public val module: Map<String, ModulePrototype>? by fromJson()

  public val `mouse-cursor`: Map<String, MouseCursor>? by fromJson()

  public val `movement-bonus-equipment`: Map<String, MovementBonusEquipmentPrototype>? by fromJson()

  public val `noise-expression`: Map<String, NamedNoiseExpression>? by fromJson()

  public val `night-vision-equipment`: Map<String, NightVisionEquipmentPrototype>? by fromJson()

  public val `noise-layer`: Map<String, NoiseLayer>? by fromJson()

  public val `offshore-pump`: Map<String, OffshorePumpPrototype>? by fromJson()

  public val `optimized-particle`: Map<String, ParticlePrototype>? by fromJson()

  public val `particle-source`: Map<String, ParticleSourcePrototype>? by fromJson()

  public val pipe: Map<String, PipePrototype>? by fromJson()

  public val `pipe-to-ground`: Map<String, PipeToGroundPrototype>? by fromJson()

  public val `player-damaged-achievement`: Map<String, PlayerDamagedAchievementPrototype>? by
      fromJson()

  public val `player-port`: Map<String, PlayerPortPrototype>? by fromJson()

  public val `power-switch`: Map<String, PowerSwitchPrototype>? by fromJson()

  public val `produce-achievement`: Map<String, ProduceAchievementPrototype>? by fromJson()

  public val `produce-per-hour-achievement`: Map<String, ProducePerHourAchievementPrototype>? by
      fromJson()

  public val `programmable-speaker`: Map<String, ProgrammableSpeakerPrototype>? by fromJson()

  public val projectile: Map<String, ProjectilePrototype>? by fromJson()

  public val pump: Map<String, PumpPrototype>? by fromJson()

  public val radar: Map<String, RadarPrototype>? by fromJson()

  public val `rail-chain-signal`: Map<String, RailChainSignalPrototype>? by fromJson()

  public val `rail-planner`: Map<String, RailPlannerPrototype>? by fromJson()

  public val `rail-remnants`: Map<String, RailRemnantsPrototype>? by fromJson()

  public val `rail-signal`: Map<String, RailSignalPrototype>? by fromJson()

  public val reactor: Map<String, ReactorPrototype>? by fromJson()

  public val `recipe-category`: Map<String, RecipeCategory>? by fromJson()

  public val recipe: Map<String, RecipePrototype>? by fromJson()

  public val `repair-tool`: Map<String, RepairToolPrototype>? by fromJson()

  public val `research-achievement`: Map<String, ResearchAchievementPrototype>? by fromJson()

  public val `resource-category`: Map<String, ResourceCategory>? by fromJson()

  public val resource: Map<String, ResourceEntityPrototype>? by fromJson()

  public val `roboport-equipment`: Map<String, RoboportEquipmentPrototype>? by fromJson()

  public val roboport: Map<String, RoboportPrototype>? by fromJson()

  public val `rocket-silo`: Map<String, RocketSiloPrototype>? by fromJson()

  public val `rocket-silo-rocket`: Map<String, RocketSiloRocketPrototype>? by fromJson()

  public val `rocket-silo-rocket-shadow`: Map<String, RocketSiloRocketShadowPrototype>? by
      fromJson()

  public val `selection-tool`: Map<String, SelectionToolPrototype>? by fromJson()

  public val shortcut: Map<String, ShortcutPrototype>? by fromJson()

  public val `simple-entity`: Map<String, SimpleEntityPrototype>? by fromJson()

  public val `simple-entity-with-force`: Map<String, SimpleEntityWithForcePrototype>? by fromJson()

  public val `simple-entity-with-owner`: Map<String, SimpleEntityWithOwnerPrototype>? by fromJson()

  public val smoke: Map<String, SimpleSmokePrototype>? by fromJson()

  public val `smoke-with-trigger`: Map<String, SmokeWithTriggerPrototype>? by fromJson()

  public val `solar-panel-equipment`: Map<String, SolarPanelEquipmentPrototype>? by fromJson()

  public val `solar-panel`: Map<String, SolarPanelPrototype>? by fromJson()

  public val sound: Map<String, SoundPrototype>? by fromJson()

  public val `spectator-controller`: Map<String, SpectatorControllerPrototype>? by fromJson()

  public val `speech-bubble`: Map<String, SpeechBubblePrototype>? by fromJson()

  public val `spider-leg`: Map<String, SpiderLegPrototype>? by fromJson()

  public val `spider-vehicle`: Map<String, SpiderVehiclePrototype>? by fromJson()

  public val `spidertron-remote`: Map<String, SpidertronRemotePrototype>? by fromJson()

  public val splitter: Map<String, SplitterPrototype>? by fromJson()

  public val sprite: Map<String, SpritePrototype>? by fromJson()

  public val sticker: Map<String, StickerPrototype>? by fromJson()

  public val `storage-tank`: Map<String, StorageTankPrototype>? by fromJson()

  public val `straight-rail`: Map<String, StraightRailPrototype>? by fromJson()

  public val technology: Map<String, TechnologyPrototype>? by fromJson()

  public val `tile-effect`: Map<String, TileEffectDefinition>? by fromJson()

  public val `tile-ghost`: Map<String, TileGhostPrototype>? by fromJson()

  public val tile: Map<String, TilePrototype>? by fromJson()

  public val `tips-and-tricks-item`: Map<String, TipsAndTricksItem>? by fromJson()

  public val `tips-and-tricks-item-category`: Map<String, TipsAndTricksItemCategory>? by fromJson()

  public val tool: Map<String, ToolPrototype>? by fromJson()

  public val `train-path-achievement`: Map<String, TrainPathAchievementPrototype>? by fromJson()

  public val `train-stop`: Map<String, TrainStopPrototype>? by fromJson()

  public val `transport-belt`: Map<String, TransportBeltPrototype>? by fromJson()

  public val tree: Map<String, TreePrototype>? by fromJson()

  public val `trigger-target-type`: Map<String, TriggerTargetType>? by fromJson()

  public val `trivial-smoke`: Map<String, TrivialSmokePrototype>? by fromJson()

  public val turret: Map<String, TurretPrototype>? by fromJson()

  public val tutorial: Map<String, TutorialDefinition>? by fromJson()

  public val `underground-belt`: Map<String, UndergroundBeltPrototype>? by fromJson()

  public val unit: Map<String, UnitPrototype>? by fromJson()

  public val `upgrade-item`: Map<String, UpgradeItemPrototype>? by fromJson()

  public val `utility-constants`: Map<String, UtilityConstants>? by fromJson()

  public val `utility-sounds`: Map<String, UtilitySounds>? by fromJson()

  public val `utility-sprites`: Map<String, UtilitySprites>? by fromJson()

  public val `virtual-signal`: Map<String, VirtualSignalPrototype>? by fromJson()

  public val wall: Map<String, WallPrototype>? by fromJson()

  public val `wind-sound`: Map<String, WindSound>? by fromJson()

  public object Serializer : JsonReaderSerializer<PrototypeData>(PrototypeData::class)
}
