/*
 * Copyright 2025 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dynamiccolor

import dynamiccolor.ColorSpec.SpecVersion
import hct.Hct
import palettes.TonalPalette
import utils.MathUtils
import kotlin.jvm.JvmStatic
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Provides important settings for creating colors dynamically, and 6 color palettes. Requires: 1. A
 * color. (source color) 2. A theme. (Variant) 3. Whether or not its dark mode. 4. Contrast level.
 * (-1 to 1, currently contrast ratio 3.0 and 7.0)
 */
open class DynamicScheme(
  /** The source color of the scheme in HCT format. */
  val sourceColorHctList: List<Hct>,
  /** The variant of the scheme. */
  val variant: Variant,
  /** Whether or not the scheme is dark mode. */
  val isDark: Boolean,
  /**
   * Value from -1 to 1. -1 represents minimum contrast. 0 represents standard (i.e. the design as
   * spec'd), and 1 represents maximum contrast.
   */
  val contrastLevel: Double,
  /** The platform on which this scheme is intended to be used. */
  val platform: Platform = DEFAULT_PLATFORM,
  /** The spec version of the scheme. */
  specVersion: SpecVersion = DEFAULT_SPEC_VERSION,
  val primaryPalette: TonalPalette,
  val secondaryPalette: TonalPalette,
  val tertiaryPalette: TonalPalette,
  val neutralPalette: TonalPalette,
  val neutralVariantPalette: TonalPalette,
  val errorPalette: TonalPalette,
) {

  /** The spec version of the scheme. */
  constructor(
    sourceColorHct: Hct,
    variant: Variant,
    isDark: Boolean,
    contrastLevel: Double,
    platform: Platform = DEFAULT_PLATFORM,
    specVersion: SpecVersion = DEFAULT_SPEC_VERSION,
    primaryPalette: TonalPalette,
    secondaryPalette: TonalPalette,
    tertiaryPalette: TonalPalette,
    neutralPalette: TonalPalette,
    neutralVariantPalette: TonalPalette,
    errorPalette: TonalPalette,
  ) : this(
    listOf(sourceColorHct),
    variant,
    isDark,
    contrastLevel,
    platform,
    specVersion,
    primaryPalette,
    secondaryPalette,
    tertiaryPalette,
    neutralPalette,
    neutralVariantPalette,
    errorPalette,
  )

  val specVersion: SpecVersion

  init {
    require(sourceColorHctList.isNotEmpty()) { "sourceColorHctList cannot be empty" }
    this.specVersion = maybeFallbackSpecVersion(specVersion, variant)
  }

  /** The source color of the scheme in HCT format. */
  val sourceColorHct: Hct = sourceColorHctList.first()

  /** The source color of the scheme in ARGB format. */
  @Suppress("unused")
  val sourceColorArgb: Int = sourceColorHct.toInt()

  /** The platform on which this scheme is intended to be used. */
  enum class Platform {
    PHONE,
    WATCH,
  }

  @Suppress("unused")
  fun getHct(dynamicColor: DynamicColor): Hct {
    return dynamicColor.getHct(this)
  }

  fun getArgb(dynamicColor: DynamicColor): Int {
    return dynamicColor.getArgb(this)
  }

  override fun toString(): String {
    val mode = if (isDark) "dark" else "light"
    val platformName = platform.name.lowercase()
    val contrast = ((contrastLevel * 10).roundToInt() / 10.0).toString()
    val extraColors =
      if (sourceColorHctList.size <= 1) {
        ""
      } else {
        "sourceColorHctList=[${sourceColorHctList.joinToString(", ") { it.toString() }}], "
      }
    return "Scheme: variant=${variant.name}, mode=$mode, platform=$platformName, " +
      "contrastLevel=$contrast, seed=$sourceColorHct, ${extraColors}specVersion=$specVersion"
  }

  private val dynamicColors = MaterialDynamicColors()

  @Suppress("unused")
  val primaryPaletteKeyColor: Int
    get() = getArgb(dynamicColors.primaryPaletteKeyColor)

  @Suppress("unused")
  val secondaryPaletteKeyColor: Int
    get() = getArgb(dynamicColors.secondaryPaletteKeyColor)

  @Suppress("unused")
  val tertiaryPaletteKeyColor: Int
    get() = getArgb(dynamicColors.tertiaryPaletteKeyColor)

  @Suppress("unused")
  val neutralPaletteKeyColor: Int
    get() = getArgb(dynamicColors.neutralPaletteKeyColor)

  @Suppress("unused")
  val neutralVariantPaletteKeyColor: Int
    get() = getArgb(dynamicColors.neutralVariantPaletteKeyColor)

  @Suppress("unused")
  val background: Int
    get() = getArgb(dynamicColors.background)

  @Suppress("unused")
  val onBackground: Int
    get() = getArgb(dynamicColors.onBackground)

  @Suppress("unused")
  val surface: Int
    get() = getArgb(dynamicColors.surface)

  @Suppress("unused")
  val surfaceDim: Int
    get() = getArgb(dynamicColors.surfaceDim)

  @Suppress("unused")
  val surfaceBright: Int
    get() = getArgb(dynamicColors.surfaceBright)

  @Suppress("unused")
  val surfaceContainerLowest: Int
    get() = getArgb(dynamicColors.surfaceContainerLowest)

  @Suppress("unused")
  val surfaceContainerLow: Int
    get() = getArgb(dynamicColors.surfaceContainerLow)

  @Suppress("unused")
  val surfaceContainer: Int
    get() = getArgb(dynamicColors.surfaceContainer)

  @Suppress("unused")
  val surfaceContainerHigh: Int
    get() = getArgb(dynamicColors.surfaceContainerHigh)

  @Suppress("unused")
  val surfaceContainerHighest: Int
    get() = getArgb(dynamicColors.surfaceContainerHighest)

  @Suppress("unused")
  val onSurface: Int
    get() = getArgb(dynamicColors.onSurface)

  @Suppress("unused")
  val surfaceVariant: Int
    get() = getArgb(dynamicColors.surfaceVariant)

  @Suppress("unused")
  val onSurfaceVariant: Int
    get() = getArgb(dynamicColors.onSurfaceVariant)

  @Suppress("unused")
  val inverseSurface: Int
    get() = getArgb(dynamicColors.inverseSurface)

  @Suppress("unused")
  val inverseOnSurface: Int
    get() = getArgb(dynamicColors.inverseOnSurface)

  @Suppress("unused")
  val outline: Int
    get() = getArgb(dynamicColors.outline)

  @Suppress("unused")
  val outlineVariant: Int
    get() = getArgb(dynamicColors.outlineVariant)

  @Suppress("unused")
  val shadow: Int
    get() = getArgb(dynamicColors.shadow)

  @Suppress("unused")
  val scrim: Int
    get() = getArgb(dynamicColors.scrim)

  @Suppress("unused")
  val surfaceTint: Int
    get() = getArgb(dynamicColors.surfaceTint)

  @Suppress("unused")
  val primary: Int
    get() = getArgb(dynamicColors.primary)

  @Suppress("unused")
  val onPrimary: Int
    get() = getArgb(dynamicColors.onPrimary)

  @Suppress("unused")
  val primaryContainer: Int
    get() = getArgb(dynamicColors.primaryContainer)

  @Suppress("unused")
  val onPrimaryContainer: Int
    get() = getArgb(dynamicColors.onPrimaryContainer)

  @Suppress("unused")
  val inversePrimary: Int
    get() = getArgb(dynamicColors.inversePrimary)

  @Suppress("unused")
  val secondary: Int
    get() = getArgb(dynamicColors.secondary)

  @Suppress("unused")
  val onSecondary: Int
    get() = getArgb(dynamicColors.onSecondary)

  @Suppress("unused")
  val secondaryContainer: Int
    get() = getArgb(dynamicColors.secondaryContainer)

  @Suppress("unused")
  val onSecondaryContainer: Int
    get() = getArgb(dynamicColors.onSecondaryContainer)

  @Suppress("unused")
  val tertiary: Int
    get() = getArgb(dynamicColors.tertiary)

  @Suppress("unused")
  val onTertiary: Int
    get() = getArgb(dynamicColors.onTertiary)

  @Suppress("unused")
  val tertiaryContainer: Int
    get() = getArgb(dynamicColors.tertiaryContainer)

  @Suppress("unused")
  val onTertiaryContainer: Int
    get() = getArgb(dynamicColors.onTertiaryContainer)

  @Suppress("unused")
  val error: Int
    get() = getArgb(dynamicColors.error)

  @Suppress("unused")
  val onError: Int
    get() = getArgb(dynamicColors.onError)

  @Suppress("unused")
  val errorContainer: Int
    get() = getArgb(dynamicColors.errorContainer)

  @Suppress("unused")
  val onErrorContainer: Int
    get() = getArgb(dynamicColors.onErrorContainer)

  @Suppress("unused")
  val primaryFixed: Int
    get() = getArgb(dynamicColors.primaryFixed)

  @Suppress("unused")
  val primaryFixedDim: Int
    get() = getArgb(dynamicColors.primaryFixedDim)

  @Suppress("unused")
  val onPrimaryFixed: Int
    get() = getArgb(dynamicColors.onPrimaryFixed)

  @Suppress("unused")
  val onPrimaryFixedVariant: Int
    get() = getArgb(dynamicColors.onPrimaryFixedVariant)

  @Suppress("unused")
  val secondaryFixed: Int
    get() = getArgb(dynamicColors.secondaryFixed)

  @Suppress("unused")
  val secondaryFixedDim: Int
    get() = getArgb(dynamicColors.secondaryFixedDim)

  @Suppress("unused")
  val onSecondaryFixed: Int
    get() = getArgb(dynamicColors.onSecondaryFixed)

  @Suppress("unused")
  val onSecondaryFixedVariant: Int
    get() = getArgb(dynamicColors.onSecondaryFixedVariant)

  @Suppress("unused")
  val tertiaryFixed: Int
    get() = getArgb(dynamicColors.tertiaryFixed)

  @Suppress("unused")
  val tertiaryFixedDim: Int
    get() = getArgb(dynamicColors.tertiaryFixedDim)

  @Suppress("unused")
  val onTertiaryFixed: Int
    get() = getArgb(dynamicColors.onTertiaryFixed)

  @Suppress("unused")
  val onTertiaryFixedVariant: Int
    get() = getArgb(dynamicColors.onTertiaryFixedVariant)

  companion object {
    val DEFAULT_SPEC_VERSION = SpecVersion.SPEC_2021
    val DEFAULT_PLATFORM = Platform.PHONE

    @JvmStatic
    fun from(other: DynamicScheme, isDark: Boolean): DynamicScheme {
      return from(other, isDark, other.contrastLevel)
    }

    @JvmStatic
    fun from(other: DynamicScheme, isDark: Boolean, contrastLevel: Double): DynamicScheme {
      return DynamicScheme(
        other.sourceColorHctList,
        other.variant,
        isDark,
        contrastLevel,
        other.platform,
        other.specVersion,
        other.primaryPalette,
        other.secondaryPalette,
        other.tertiaryPalette,
        other.neutralPalette,
        other.neutralVariantPalette,
        other.errorPalette,
      )
    }

    /**
     * Returns a new hue based on a piecewise function and input color hue.
     *
     * For example, for the following function:
     * ```
     * result = 26, if 0 <= hue < 101;
     * result = 39, if 101 <= hue < 210;
     * result = 28, if 210 <= hue < 360.
     * ```
     *
     * call the function as:
     * ```
     * double[] hueBreakpoints = {0, 101, 210, 360};
     * double[] hues = {26, 39, 28};
     * double result = scheme.piecewise(sourceColor, hueBreakpoints, hues);
     * ```
     *
     * @param sourceColorHct The input value.
     * @param hueBreakpoints The breakpoints, in sorted order. No default lower or upper bounds are
     *   assumed.
     * @param hues The hues that should be applied when source color's hue is >= the same index in
     *   hueBreakpoints array, and < the hue at the next index in hueBreakpoints array. Otherwise,
     *   the source color's hue is returned.
     */
    @JvmStatic
    fun getPiecewiseValue(
      sourceColorHct: Hct,
      hueBreakpoints: DoubleArray,
      hues: DoubleArray,
    ): Double {
      val size = min(hueBreakpoints.size - 1, hues.size)
      val sourceHue = sourceColorHct.hue
      for (i in 0 until size) {
        if (sourceHue >= hueBreakpoints[i] && sourceHue < hueBreakpoints[i + 1]) {
          return MathUtils.sanitizeDegreesDouble(hues[i])
        }
      }
      // No condition matched, return the source value.
      return sourceHue
    }

    /**
     * Returns a shifted hue based on a piecewise function and input color hue.
     *
     * For example, for the following function:
     * ```
     * result = hue + 26, if 0 <= hue < 101;
     * result = hue - 39, if 101 <= hue < 210;
     * result = hue + 28, if 210 <= hue < 360.
     * ```
     *
     * call the function as:
     * ```
     * double[] hueBreakpoints = {0, 101, 210, 360};
     * double[] rotations = {26, -39, 28};
     * double result = scheme.getRotatedHue(sourceColor, hueBreakpoints, rotations);
     * ```
     *
     * @param sourceColorHct the source color of the theme, in HCT.
     * @param hueBreakpoints The "breakpoints", i.e. the hues at which a rotation should be apply.
     *   No default lower or upper bounds are assumed.
     * @param rotations The rotation that should be applied when source color's hue is >= the same
     *   index in hues array, and < the hue at the next index in hues array. Otherwise, the source
     *   color's hue is returned.
     */
    @JvmStatic
    fun getRotatedHue(
      sourceColorHct: Hct,
      hueBreakpoints: DoubleArray,
      rotations: DoubleArray,
    ): Double {
      var rotation = getPiecewiseValue(sourceColorHct, hueBreakpoints, rotations)
      if (min(hueBreakpoints.size - 1, rotations.size) <= 0) {
        // No condition matched, return the source hue.
        rotation = 0.0
      }
      return MathUtils.sanitizeDegreesDouble(sourceColorHct.hue + rotation)
    }

    /**
     * Returns the spec version to use for the given variant. If the variant is not supported by the
     * given spec version, the fallback spec version is returned.
     */
    private fun maybeFallbackSpecVersion(specVersion: SpecVersion, variant: Variant): SpecVersion {
      if (variant == Variant.CMF) {
        return specVersion
      }
      if (
        variant == Variant.EXPRESSIVE ||
          variant == Variant.VIBRANT ||
          variant == Variant.TONAL_SPOT ||
          variant == Variant.NEUTRAL
      ) {
        return if (specVersion == SpecVersion.SPEC_2026) SpecVersion.SPEC_2025 else specVersion
      }
      return SpecVersion.SPEC_2021
    }
  }
}
