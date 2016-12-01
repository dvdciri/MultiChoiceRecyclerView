# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased]
### Added
- Possibility to disable some items from the selection mode with the method isSelectableInMultiChoiceMode(int position)
### Changed
- Clean and polish
### Fixed
- Kept order of selected items when retrieved back with "getSelectedItems"
- Performance improvements
### Deleted
- Application tag from library manifest
- Removed unnecessary methods like setColumnNumber ad setRowNumber

## [1.1.8]
### Added
- Changelog file
- Class MultiChoiceToolbar for wrapping all the multi choice toolbar related fields
- Toolbar icon support for MultiChoiceToolbar (Builder)

### Changed
- Method setMultiChoiceToolbar() now accept a MultiChoiceToolbar class with its builder for a simpler construction
- Internal file structure in order to allow package-protected classes/methods
- Updated sample with new toolbar implementation

## [1.1.7]
### Fixed
- Circle CI build failing because of upgrade needed to JDK8

## [1.1.6]
### Fixed
- Wrong callback on deselectAll