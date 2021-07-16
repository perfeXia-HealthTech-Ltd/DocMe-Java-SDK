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
Documentation is avaliable in docs directory. Run index.html from your web browser.

Video requirements for uploading:
- Format mp4/mov
- Duration 10 - 20 seconds

## Tests
You may build and run test app from `app` directory
