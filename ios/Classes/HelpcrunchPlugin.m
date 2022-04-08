#import "HelpcrunchPlugin.h"
#if __has_include(<helpcrunch/helpcrunch-Swift.h>)
#import <helpcrunch/helpcrunch-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "helpcrunch-Swift.h"
#endif

@implementation HelpcrunchPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftHelpcrunchPlugin registerWithRegistrar:registrar];
}
@end
