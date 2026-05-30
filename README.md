<h1 align="center" style="font-weight: bold;">Material Color Utilities for Kotlin 🎨</h1>

<p align="center">
    <a href="https://github.com/JetBrains/kotlin">
        <img alt="Kotlin version" src="https://img.shields.io/badge/dynamic/toml?url=https%3A%2F%2Fraw.githubusercontent.com%2Fyaroslavzghoba%2Fmaterial-color-utilities-kotlin%2Frefs%2Fheads%2Fmain%2Fgradle%2Flibs.versions.toml&query=%24.versions.kotlin&logo=kotlin&logoColor=%237F52FF&label=Kotlin&color=slateblue"/>
    </a>
    <a href="https://mvnrepository.com/artifact/space.zghoba/material-color-utilities-kotlin">
        <img alt="Maven Central release" src="https://img.shields.io/badge/dynamic/toml?url=https%3A%2F%2Fraw.githubusercontent.com%2Fyaroslavzghoba%2Fmaterial-color-utilities-kotlin%2Frefs%2Fheads%2Fmain%2Fgradle%2Flibs.versions.toml&query=%24%5B'versions'%5D%5B'project'%5D&label=Maven%20Central&color=darksalmon&cacheSeconds=https%3A%2F%2Fmvnrepository.com%2Fartifact%2Fspace.zghoba%2Fmaterial-color-utilities-kotlin"/>
    </a>
    <a href="https://github.com/yaroslavzghoba/material-color-utilities-kotlin/blob/main/LICENSE">
        <img alt="License" src="https://img.shields.io/badge/license-MIT-cadetblue"/>
    </a>
</p>

**Material Color Utilities for Kotlin** is a *cross-platform* wrapper written entirely in Kotlin around [Google's Material Color Utilities (MCU)](https://github.com/material-foundation/material-color-utilities), 
making it easy to generate beautiful, accessible, and user-driven color schemes (including Material 3) using advanced color algorithms.

<p align="center">
    <img width="665" height="375" alt="100% Kotlin" src="https://github.com/user-attachments/assets/9b386ef8-1781-45b7-8376-185e9639a0c0"/>
</p>

---

## 🚀 Getting Started

1. Add the library dependency to your `build.gradle.kts` file:

```kts
dependencies {
    implementation("space.zghoba:material-color-utilities-kotlin:2.1.0")
}
```

2. Add a simple example of using the HCT color model. When using HCT, only the hue changes. The tone and chroma remain constant, so colors appear equally bright regardless of their hue.

```kt
import hct.Hct
import utils.StringUtils

fun main() {
    // Same perceived brightness.
    // Different hue.
    val red   = Hct.from(hue = 20.0, 80.0, 60.0).toInt()
    val green = Hct.from(hue = 140.0, 80.0, 60.0).toInt()
    val blue  = Hct.from(hue = 260.0, 80.0, 60.0).toInt()
}
```
> [!NOTE]
> The HCT color model isn't the only feature offered by the Material Color Utilities repository. See [Capabilities Overview](https://github.com/material-foundation/material-color-utilities#capabilities-overview) for more information.

---

## 📄 License

This project is licensed under the **MIT License** — see the [`LICENSE`](https://github.com/yaroslavzghoba/material-color-utilities-kotlin/blob/main/LICENSE) file.
