# Kotlin Factorio Prototypes

This project contains [Factorio prototype definitions](https://lua-api.factorio.com/latest/index-prototype.html)
represented as Kotlin classes, providing read-only view of `data-raw-dump.json`, from [the --dump-data]
option (https://wiki.factorio.com/Command_line_parameters).

This isn't published yet; you can clone the repository and use it as a local dependency.

Union types are represented using some fun stuff with sealed interfaces and classes.
