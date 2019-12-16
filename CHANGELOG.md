# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
- Support for texts attributes from style by [Ilya Ghirici](https://github.com/Ilya-Gh)
- Improve how Text Resources Ids are read from attributes creating the new extension `Context.getStringResourceId()` by [Valerii Turcan](https://github.com/ffelini)

### Breaking changes
- `ViewTransformer#setTextIfExists` has been removed

## [2.0.1]
_2019-11-07_ [Github Diff](https://github.com/jcminarro/Philology/compare/v-2.0.0...v-2.0.1)
### Added
- Add `Resources#getText(id: Int, def: CharSequence)` support

## [2.0.0]
_2019-09-03_ [Github Diff](https://github.com/jcminarro/Philology/compare/v-1.1.2...v-2.0.0)
### Added
- AppCompact from AndroidX Project

### Changed
- Migrate to AndroidX
- Upgrade ViewPump to version 2.0.3

### Removed
- AppCompact from the old Android Support library

## [1.1.2]
_2019-09-03_ [Github Diff](https://github.com/jcminarro/Philology/compare/v-1.1.1...v-1.1.2)

### Added
- A `CHANGELOG.md` File to track every new change on the library by [Jc Miñarro](https://github.com/jcminarro)
- Add String Array Support by [Maxim Bircu](https://github.com/mbircu-ellation)

## [1.1.1]
_2019-08-08_ [Github Diff](https://github.com/jcminarro/Philology/compare/v-1.1.0...v-1.1.1)

### Added
- Custom implementation to support Plurals on Old Android API Versions by [Serghei Oleinicenco](https://github.com/pr0t3us)

### Removed
- `com.ibm.icu:icu4j` Dependency

## [1.1.0]
_2019-07-18_ [Github Diff](https://github.com/jcminarro/Philology/compare/v-1.0.1...v-1.1.0)

### Added
- Android Plurals support by [Serghei Oleinicenco](https://github.com/pr0t3us)
- `com.ibm.icu:icu4j` Dependency to support plural on Old Android API Versions

## [1.0.1]
_2018-08-04_ [Github Diff](https://github.com/jcminarro/Philology/compare/v-1.0.0...v-1.0.1)

### Added
- Robolectric framework to test cross multiple Android API Versions by [Jc Miñarro](https://github.com/jcminarro)

### Fixed
- Crash when checking if a view is Toolbar in Android version lower than Lolipop by [Jc Miñarro](https://github.com/jcminarro)

## [1.0.0]
_2018-07-05_ Initial version

[Unreleased]: https://github.com/jcminarro/Philology/compare/v-2.0.1...HEAD
[2.0.1]: https://github.com/jcminarro/Philology/releases/tag/v-2.0.1
[2.0.0]: https://github.com/jcminarro/Philology/releases/tag/v-2.0.0
[1.1.2]: https://github.com/jcminarro/Philology/releases/tag/v-1.1.2
[1.1.1]: https://github.com/jcminarro/Philology/releases/tag/v-1.1.1
[1.1.0]: https://github.com/jcminarro/Philology/releases/tag/v-1.1.0
[1.0.1]: https://github.com/jcminarro/Philology/releases/tag/v-1.0.1
[1.0.0]: https://github.com/jcminarro/Philology/releases/tag/v-1.0.0