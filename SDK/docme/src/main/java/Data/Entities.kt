package Data

enum class State(val state: String) {
    NOT_INITIALIZED("NOT_INITIALIZED"),
    INITIALIZED("INITIALIZED"),
    PROCESSING("PROCESSING"),
    SUCCESS("SUCCESS"),
    ERROR("ERROR")
}

data class Patient(var id: String = "NO ID YET") { }

data class Measurement(
    var id: String = "NO MEASUREMENT ID YET",
    var patientId: String = "NO PATIENT ID YET",
    var status: State = State.NOT_INITIALIZED,
    var timestamp: Long = -1,
    var details: String = "No details now",
    //var errorDetails: Error
)