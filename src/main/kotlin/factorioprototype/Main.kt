import java.net.URL

val defaultDataRaw: URL? get() = object {}.javaClass.getResource("data-raw-dump.json")
