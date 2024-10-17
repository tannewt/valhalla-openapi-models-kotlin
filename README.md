# Valhalla Mobile OpenAPI Kotlin

Kotlin serializable [Valhalla](https://github.com/valhalla/valhalla) API and configuration models.

- **Valhalla Models** - Serializable models used for interacting with Valhalla's JSON APIs.
- **Valhalla Config Models** - Serializable models used for generating Valhalla's JSON config object.

## Usage

### Kotlin Gradle

Using a [`libs.versions.toml`](https://developer.android.com/build/migrate-to-catalogs) 

```toml
[versions]
valhallaModels = "0.0.8"
[libraries]
valhalla-models-config = { group = "io.github.rallista", name = "valhalla-models-config", version.ref = "valhallaModels" }
valhalla-models-api = { group = "io.github.rallista", name = "valhalla-models", version.ref = "valhallaModels" }
```

```kt
implementation(libs.valhalla.models.api)
implementation(libs.valhalla.models.config)
```

### Kotlin Gradle

```
implementation("io.github.rallista:valhalla-models-config:0.0.8")
implementation("io.github.rallista:valhalla-models:0.0.8")
```

### Gradle

```groovy
implementation 'io.github.rallista:valhalla-models-config:0.0.8'
implementation 'io.github.rallista:valhalla-models:0.0.8'
```

## What's Using It

- [valhalla-mobile](https://github.com/Rallista/valhalla-mobile) - A valhalla wrapper library built for Android & iOS.