#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint helpcrunch.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'helpcrunch'
  s.version          = '0.0.4'
  s.summary          = 'Helpcrunch SDK for flutter'
  s.description      = <<-DESC
        Helpcrunch SDK for flutter
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Rootshealth GmbH' => 'support@helloinside.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.dependency 'Flutter'
  s.platform = :ios, '15.0'

  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
  s.swift_version = '5.0'

  s.dependency 'HelpCrunchSDK', '~> "4.4.1'

end
