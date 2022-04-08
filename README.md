# helpcrunch

alpha-stage helpcrunch plugin for flutter

Uses pigeon

To update the generated code, run

```bash

flutter pub get;

flutter pub run pigeon --input pigeon/host_api.dart \
   --dart_out lib/helpcrunch.dart \
   --objc_header_out ios/Classes/Pigeon.h \
   --objc_source_out ios/Classes/Pigeon.m \
   --java_package "com.helloinside.helpcrunch_plugin" \
   --java_out android/src/main/java/com/helloinside/helpcrunch_plugin/Pigeon.java
```