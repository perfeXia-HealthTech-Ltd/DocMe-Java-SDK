# Docme SDK for Android

## How to use

1. Place `docme.aar` file in `libs` directory of your app.

2. In `build.gradle`:
```sh
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
implementation files('./libs/docme.aar')
```

3. In code:
```sh
Docme.initSDK("Your_Docme_API_Key")

// Call in new thread:
val p = Patient.newPatient()
p.newMeasurement("path_to_video_file")
val conclusion = p.getHM3()
```

4. How to build from sources:
Linux/Mac:
```sh
./gradlew assemble
```

Windows:
```sh
gradlew.bat assemble
```

## Docs
Full documentation is avaliable in docs directory. Run index.html from your web browser to open it.

**Notice!** All calls are synchronous so you should create new thread to make requests over HTTP.

### Init SDK
```sh
DocMe.initSDK("YOUR_DOCME_API_KEY")
```

### Manage patients
```sh
val p: Patient = Patient.newPatient() // Create new patient
val pById: Patient = Patient.getPatient("PATIENT_ID") // Get patient by Id
p.deletePatient() // Delete patient
```

### Manage measurements
```sh
val mByUri: Measurement = p.newMeasurent(context, timestamp, uri)
val mByPath: Measurement = p.newMeasurement(File("FILE_PATH"))
val mByPath: Measurement = p.newMeasurement("FILE_PATH")
```
where *context* is `Context` object, *timestamp* is time of taking measurement and has `Long` type, *uri* is `Uri` object which usually is come from `onActivityResult`. You can found usage of this in `MainActivity` class of the test app.

Your app should grant all privileges for taking video file. 
##### Working with result of measurement response
```sh
if (mByUri.status == Measurement.STATE.SUCCESS) {
// Proceed
}
```
Full *STATE* list:
- NOT_INITIALIZED
- INITIALIZED
- PROCESSING
- SUCCESS
- ERROR

```sh
if (mByUri.status == Measurement.STATE.ERROR) {
Log.d("Error", mByUri.errorDetails) // Not empty if only above condition is true
}
```

### Get HM3 Conclusions
```sh
val hm3: Conclusion = p.getHM3()
Log.d("Conclusion", "${hm3.state}, ${hm3.message}")
```

### Exceptions
`DocMeServerException` will be thrown on any non 200 return status

`NotAvailableFormatException` will be thrown on wrong video file format

`NotAppropriateDurationException` will be thrown on wrong video duration

`NotAppropriateSizeException` will be thrown on wrong video file size

### Other
Video requirements for uploading:
- Format mp4/mov
- Duration 10 - 20 seconds
- Maximum size 20Mb

## Tests
You may build and run test app from `app` directory and see the process from `logcat`
